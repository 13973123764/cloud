package com.cloud.item.service;

import com.cloud.dto.CartDTO;
import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.mapper.SkuMapper;
import com.cloud.item.mapper.SpuDetailMapper;
import com.cloud.item.mapper.SpuMapper;
import com.cloud.item.mapper.StockMapper;
import com.cloud.item.pojo.*;
import com.cloud.result.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zf
 * @date 2019-10-01-18:20
 */
@Service
public class GoodsService {

    @Autowired
    SpuMapper spuMapper;
    @Autowired
    SpuDetailMapper detailMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    AmqpTemplate amqpTemplate;


    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        // 分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title", "%"+key+"%");
        }
        if(saleable != null){
            criteria.andEqualTo("saleable", saleable);
        }
        example.setOrderByClause("last_update_time desc");

        List<Spu> spuList = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spuList)){
            throw new CloudException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        // 解析分类和品牌的名称
        loadCategoryAndBrandName(spuList);

        // 分页结果
        PageInfo<Spu> pageInfo = new PageInfo<>(spuList);
        return new PageResult(pageInfo.getTotal(), spuList);
    }

    private void loadCategoryAndBrandName(List<Spu> spuList) {
        for(Spu spu:spuList){
            // 处理分类名称
            List<String> categoryNames = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            String cname = StringUtils.join(categoryNames, "/");
            spu.setCname(cname);

            // 处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }

    @Transactional
    public void saveGoods(Spu spu) {
        // 新增spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

        int num = spuMapper.insert(spu);
        if(num < 1){
            throw new CloudException(ExceptionEnum.SAVE_GOODS_ERROR);
        }
        // 新增detail
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        detailMapper.insert(detail);

        // 新增sku
        List<Sku> skus = spu.getSkus();

        List<Stock> stockList = new ArrayList<>();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            int count = skuMapper.insert(sku);
            if(count < 1){
                throw new CloudException(ExceptionEnum.SAVE_GOODS_ERROR);
            }

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }

        stockMapper.insertList(stockList);

        amqpTemplate.convertAndSend("cloud.item.exchange","item.insert",spu.getId());

    }

    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail spuDetail = detailMapper.selectByPrimaryKey(spuId);
//        if(null == spuDetail){
//            throw new CloudException(ExceptionEnum.GOODS_NOT_FOUND);
//        }
        return spuDetail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            throw new CloudException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }

//        for(Sku s: skuList){
//            Stock stock = stockMapper.selectByPrimaryKey(s.getId());
//            if(null == stock){
//                throw new CloudException(ExceptionEnum.GOODS_STOCK_NOT_FOUND);
//            }
//            s.setStock(stock.getStock());
//        }
        // 查询库存
        List<Long> skuId = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(skuId);
        if(CollectionUtils.isEmpty(stockList)){
            throw new CloudException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }

        // 转换成map
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skuList.forEach(sk -> sk.setStock(stockMap.get(sk.getId())));

        return skuList;
    }

    @Transactional
    public void updateGoods(Spu spu) {
        if(spu.getId() == null){
            throw new CloudException(ExceptionEnum.GOODS_ID_ERROR);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        // 删除sku 和 stock
        List<Sku> skuList = skuMapper.select(sku);
        if(!CollectionUtils.isEmpty(skuList)){
            // 删除sku
            skuMapper.delete(sku);
            // 删除stock
            stockMapper.deleteByIdList(skuList.stream().map(Sku::getId).collect(Collectors.toList()));
        }

        // 修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);

        int num = spuMapper.updateByPrimaryKeySelective(spu);
        if(num < 1){
            throw new CloudException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        // 修改detail
        int update = detailMapper.updateByPrimaryKey(spu.getSpuDetail());

        //新增sku 和 stock
        //saveSkuAndStock(spu);

        // 发送 mq 消息
        amqpTemplate.convertAndSend("cloud.item.exchange","item.update",spu.getId());
    }


    public Spu querySpuById(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(null == spu){
            throw new CloudException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        spu.setSkus(querySkuBySpuId(id));
        spu.setSpuDetail(queryDetailById(id));
        return spu;
    }

    public List<Sku> querySkuBySpuIds(List<Long> spuIds) {
        List<Sku> skuList = skuMapper.selectByIdList(spuIds);
        if(CollectionUtils.isEmpty(skuList)){
            throw new CloudException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 库存
        List<Stock> stockList = stockMapper.selectByIdList(spuIds);
        if(CollectionUtils.isEmpty(stockList)){
            throw new CloudException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 转换成map
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skuList.forEach(sk -> sk.setStock(stockMap.get(sk.getId())));
        return skuList;
    }


    public void decreaseStock(List<CartDTO> carts) {
        for (CartDTO cart : carts) {
            int num = stockMapper.decreaseStock(cart.getSkuId(), cart.getNum());
            if(num != 1){
                throw new CloudException(ExceptionEnum.STOCK_NOT_ENOUGH);
            }
        }
    }
}
