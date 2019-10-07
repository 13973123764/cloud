package com.cloud.order.web;

import com.cloud.order.dto.OrderDTO;
import com.cloud.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zf
 * @date 2019-10-07-10:57
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderDTO orderDTO){
        Long num = orderService.createOrder(orderDTO);

        return null;
    }


}
