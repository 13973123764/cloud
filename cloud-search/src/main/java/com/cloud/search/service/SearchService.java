package com.cloud.search.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.pojo.*;
import com.cloud.result.PageResult;
import com.cloud.search.client.BrandClient;
import com.cloud.search.client.CategoryClient;
import com.cloud.search.client.GoodsClient;
import com.cloud.search.client.SpecificationClient;
import com.cloud.search.pojo.Goods;
import com.cloud.search.pojo.SearchRequest;
import com.cloud.search.pojo.SearchResult;
import com.cloud.search.repository.GoodsRepository;
import com.cloud.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zf
 * @date 2019-10-03-16:44
 */
@Slf4j
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
    @Autowired
    private ElasticsearchTemplate esTemplate;


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

    public PageResult<Goods> search(SearchRequest request) {
        int page = request.getPage() - 1;
        int size = request.getSize();

        // 创建查询构造器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 结果过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));
        // 分页
        queryBuilder.withPageable(PageRequest.of(page,size));
        // 过滤
        String basicQuery = request.getKey();

        QueryBuilder basQuery = buildBasicQuery(request);
        queryBuilder.withQuery(basQuery);

        // 聚合
        // 分类聚合
        String categoryAggName = "category_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 品牌聚合
        String brandAggName = "brand_agg";
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 查询
        AggregatedPage<Goods> result = esTemplate.queryForPage(queryBuilder.build(), Goods.class);

        // 解析结果
        long total = result.getTotalElements();
        int totalPages = result.getTotalPages();
        List<Goods> goodsList = result.getContent();

        Aggregations aggregations = result.getAggregations();
        List<Category> categories = parseCategoryAgg(aggregations.get(categoryAggName));
        List<Brand>  brands = parseBrandAgg(aggregations.get(brandAggName));

        // 完成规格参数聚合
        List<Map<String, Object>> specs = null;
        if(categories != null && categories.size() == 1){
            // 商品分类存在并且数量为1 ，可以聚合规格参数
            specs = buildSpecificationAgg(categories.get(0).getId(),basicQuery);
        }

        return new SearchResult(total, totalPages, goodsList,categories,brands,specs);
    }


    /**
     * 搜索 + 过滤
      * @param request
     * @return
     */
    private QueryBuilder buildBasicQuery(SearchRequest request) {
        // 创建bool 查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 查询条件
        boolQuery.must(QueryBuilders.matchQuery("all", request.getKey()));
        // 过滤条件
        Map<String, String> map = request.getFilter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 分类和品牌
            String key = entry.getKey();
            if(!"cid3".equals(key) && !"brandId".equals(key)){
                key = "specs."+key+".keyword";
            }
            String value = entry.getValue();
            boolQuery.filter(QueryBuilders.termQuery(key, value));
        }
        return boolQuery;
    }


    /**
     * 规格参数聚合
     * @param id
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> buildSpecificationAgg(Long id, String basicQuery) {
        List<Map<String, Object>> specs = new ArrayList<>();
        // 1 查询需要聚合的规格参数
        List<SpecParam> params = specClient.queryParamList(null, id, true);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 基本条件过滤
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", basicQuery));

        for (SpecParam param : params) {
            // 2 聚合
            queryBuilder.addAggregation(
                    AggregationBuilders.terms(param.getName())
                            .field("specs."+param.getName()+".keyword"));

        }
        AggregatedPage<Goods> goods = esTemplate.queryForPage(queryBuilder.build(), Goods.class);
        Aggregations result = goods.getAggregations();
        // 3 解析结果
        for (SpecParam param : params) {
            // 规格参数名
            StringTerms terms = result.get(param.getName());
            Map<String, Object> map = new HashMap<>();
            map.put("k", param.getName());
            map.put("options", terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString).collect(Collectors.toList()));
            specs.add(map);
        }
        return specs;
    }

    private List<Brand> parseBrandAgg(LongTerms terms) {
        try{
            List<Long> ids = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Brand> brandList = brandClient.queryBrandByIds(ids);
            return brandList;
        }catch (Exception e){
            log.error("品牌查询失败 =>  {} ", e);
            return null;
        }
    }

    private List<Category> parseCategoryAgg(LongTerms terms) {
        try{
            List<Long> ids = terms.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
            List<Category> categories = categoryClient.queryCategoryByIds(ids);
            return categories;
        }catch (Exception e){
            return null;
        }

    }
}
