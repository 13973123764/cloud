package com.cloud.search.client;

import com.cloud.item.api.CategoryApi;
import com.cloud.item.api.GoodsApi;
import com.cloud.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-03-14:11
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {


}
