package com.cloud.order.client;

import com.cloud.item.api.GoodsApi;
import com.cloud.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-07-13:20
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

    public Void decreaseStock(@RequestBody List<CartDTO> carts);

}
