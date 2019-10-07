package com.cloud.page.service;


import com.cloud.item.pojo.Spu;
import com.cloud.page.client.GoodsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zf
 * @date 2019-10-03-21:41
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    GoodsClient goodsClient;
    @Autowired
    PageService pageService;

    @Test
    public void test1(){
        Spu spu = goodsClient.querySpuById(144L);
        System.out.println("spu = " + spu);
    }


    @Test
    public void test2(){
        pageService.createHtml(124L);


    }


}