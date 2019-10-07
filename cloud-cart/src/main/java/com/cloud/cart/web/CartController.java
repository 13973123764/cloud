package com.cloud.cart.web;

import com.cloud.cart.pojo.Cart;
import com.cloud.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zf
 * @date 2019-10-06-19:12
 */
@Api(description = "购物车")
@RestController
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 新增购物车
     * @param cart
     * @return
     */
    @ApiOperation(value = "新增购物车", httpMethod = "POST")
    @PostMapping("addCart")
    public ResponseEntity<Void> addCart(Cart cart){
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
