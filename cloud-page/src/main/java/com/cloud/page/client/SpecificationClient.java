package com.cloud.page.client;

import com.cloud.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zf
 * @date 2019-10-03-14:48
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
