package com.cloud.page.client;

import com.cloud.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zf
 * @date 2019-10-03-14:11
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {


}
