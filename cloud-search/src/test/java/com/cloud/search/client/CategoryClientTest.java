package com.cloud.search.client;

import com.cloud.item.pojo.Brand;
import com.cloud.item.pojo.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zf
 * @date 2019-10-03-14:14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryClientTest {

    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;

    @Test
    public void queryCategoryByIds() {

        List<Brand> brands = brandClient.queryBrandByIds(Arrays.asList(2505L, 3177L));
        System.out.println("brands = " + brands);
    }
}