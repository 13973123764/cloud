package com.cloud.search.client;

import com.cloud.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zf
 * @date 2019-10-03-14:48
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
