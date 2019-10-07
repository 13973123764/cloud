package com.cloud.cart.service;

import com.cloud.auth.entity.UserInfo;
import com.cloud.cart.interceptor.UserInterceptor;
import com.cloud.cart.pojo.Cart;
import com.cloud.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zf
 * @date 2019-10-06-19:14
 */
@Service
public class CartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String PREFIX = "cart:user:id:";

    public void addCart(Cart cart) {
        // 获取登录用户
        UserInfo user = UserInterceptor.getUser();
        // key
        String key = PREFIX+user.getId();
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);

        // 记录要增加的数量
        int num = cart.getNum();

        // 查询商品是否存在
        Long carId = cart.getSkuId();
        if(operations.hasKey(carId.toString())){
            // 修改数量
            String json = operations.get(carId).toString();
            cart = JsonUtils.toBean(json, Cart.class);
            cart.setNum(num + cart.getNum());
        }
        operations.put(carId, JsonUtils.toString(cart));

    }
}
