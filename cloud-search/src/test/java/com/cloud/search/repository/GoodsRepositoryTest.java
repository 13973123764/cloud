package com.cloud.search.repository;

import com.cloud.item.pojo.Spu;
import com.cloud.result.PageResult;
import com.cloud.search.client.GoodsClient;
import com.cloud.search.pojo.Goods;
import com.cloud.search.service.SearchService;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author zf
 * @date 2019-10-03-14:52
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    GoodsClient goodsClient;
    @Autowired
    SearchService searchService;

    @Test
    public void test1(){
        //template.deleteIndex(Goods.class);
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }


    @Test
    public void test2(){
        Iterable<Goods> all = goodsRepository.findAll();

        int i = 0;
        Iterator<Goods> it = all.iterator();
        while(it.hasNext()){
            i++;
        }
        System.out.println("i = " + i);

    }

    @Test
    public void loadData(){
        int page = 1;
        int rows = 100;
        int size = 0;

        do{

            PageResult<Spu> spuPage = goodsClient.querySpuByPage(page, rows, true, null);
            List<Spu> spuList = spuPage.getItems();
            if(CollectionUtils.isEmpty(spuList)){
                break;
            }
            List<Goods> goodsList = spuList.stream().map(searchService::buildGoods).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);
            page ++;
            size = spuList.size();

        }while (size == 100);

    }


}