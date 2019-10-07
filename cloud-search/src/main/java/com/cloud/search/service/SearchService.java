package com.cloud.search.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.pojo.*;
import com.cloud.search.client.BrandClient;
import com.cloud.search.client.CategoryClient;
import com.cloud.search.client.GoodsClient;
import com.cloud.search.client.SpecificationClient;
import com.cloud.search.pojo.Goods;
import com.cloud.search.repository.GoodsRepository;
import com.cloud.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zf
 * @date 2019-10-03-16:44
 */
@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specClient;
    @Autowired
    private GoodsRepository goodsRepository;


    public Goods buildGoods(Spu spu){
        // spu id
        Long id = spu.getId();

        // 查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if(CollectionUtils.isEmpty(categories)){
            throw new CloudException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        List<String> categoryNames = categories.stream().map(Category::getName).collect(Collectors.toList());

        // 查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if(null == brand){
            throw new CloudException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        // 搜索字段
        String all = spu.getTitle() + StringUtils.join(categoryNames, " ") + brand.getName();

        // 查询sku
        List<Sku> skus = goodsClient.querySkuBySpuId(id);
        if(CollectionUtils.isEmpty(skus)){
            throw new CloudException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        // 处理sku
        List<Map<String, Object>> skuList = new ArrayList<>();
        Set<Long> priceSet = new HashSet<>();
        for (Sku sku : skus) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());
            map.put("images", StringUtils.substringBefore(sku.getImages(),","));
            priceSet.add(sku.getPrice());
            skuList.add(map);
        }

        // 查询规格参数
        List<SpecParam> specParams = specClient.queryParamList(null, spu.getCid3(), true);
        if(CollectionUtils.isEmpty(specParams)){
            throw new CloudException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }

        // 查询商品详情
        SpuDetail spuDetail = goodsClient.queryDetailById(id);
        if(null == spuDetail){
            return null;
        }
        // 获取通用规格参数
        Map<Long, String> genericMap = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, String.class);
        // 获取特有规格参数
        Map<Long, List<String>> specialMap = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});

        // 规格参数
        Map<String, Object> specs = new HashMap<>();
        for (SpecParam param : specParams) {
            String key = param.getName();
            Object value = "";
            if(param.getGeneric()){
                value = genericMap.get(param.getId());
                // 数值处理成段
                if(param.getNumeric()){
                    value = chooseSegment(value.toString(),param);
                }
            }else {
                specialMap.get(param.getId());
            }
            specs.put(key, value);
        }

        // 构建goods对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(id);
        goods.setAll(all);
        goods.setPrice(priceSet);
        goods.setSkus(JsonUtils.toString(skuList));    //sku 集合json格式
        goods.setSpecs(specs);
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }



    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    public void createOrUpdateIndex(Long spuId) {
        // 查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        // 构建goods
        Goods goods = buildGoods(spu);
        // 存入索引库
        goodsRepository.save(goods);
    }
}
