package com.cloud.page.service;

import com.cloud.item.pojo.*;
import com.cloud.page.client.BrandClient;
import com.cloud.page.client.CategoryClient;
import com.cloud.page.client.GoodsClient;
import com.cloud.page.client.SpecificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zf
 * @date 2019-10-03-21:16
 */
@Service
public class PageService {

    @Autowired
    BrandClient brandClient;
    @Autowired
    GoodsClient goodsClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    SpecificationClient specClient;



    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();

        // spu
        Spu spu = goodsClient.querySpuById(spuId);
        // skus
        List<Sku> skus = spu.getSkus();
        // spu明细
        SpuDetail spuDetail = spu.getSpuDetail();
        // 品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        // 分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        // 规格参数
        List<SpecGroup> specGroups = specClient.queryGroupByCid(spu.getCid3());


        model.put("title", spu.getTitle());
        model.put("subTitle", spu.getSubTitle());
        model.put("skus", skus);
        model.put("detail", spuDetail);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specGroups);
        return model;
    }

    @Autowired
    TemplateEngine templateEngine;

    /**
     *
     * @param spuId
     */
    public void createHtml(Long spuId){
        // 构建上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));

        // 输出流
        File file = new File("/Users/mac/Downloads/upload",spuId+".html");

        try(PrintWriter print = new PrintWriter(file,"UTF-8")){
            templateEngine.process("item",context, print);
        }catch (Exception e){
            
        }

    }



}
