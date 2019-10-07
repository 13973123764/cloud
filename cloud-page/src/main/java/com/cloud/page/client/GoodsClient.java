package com.cloud.page.client;

import com.cloud.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zf
 * @date 2019-10-03-14:44
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {




}
