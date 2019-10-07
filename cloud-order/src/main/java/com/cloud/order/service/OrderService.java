package com.cloud.order.service;

import com.cloud.auth.entity.UserInfo;
import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.pojo.Sku;
import com.cloud.order.client.AddressClient;
import com.cloud.order.client.GoodsClient;
import com.cloud.order.dto.AddressDTO;
import com.cloud.order.dto.CartDTO;
import com.cloud.order.dto.OrderDTO;
import com.cloud.order.enums.OrderEnum;
import com.cloud.order.interceptor.UserInterceptor;
import com.cloud.order.mapper.OrderDetailMapper;
import com.cloud.order.mapper.OrderMapper;
import com.cloud.order.mapper.OrderStatusMapper;
import com.cloud.order.pojo.Order;
import com.cloud.order.pojo.OrderDetail;
import com.cloud.order.pojo.OrderStatus;
import com.cloud.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zf
 * @date 2019-10-07-11:00
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper detailMapper;
    @Autowired
    private OrderStatusMapper statusMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private GoodsClient goodsClient;


    @Transactional
    public Long createOrder(OrderDTO orderDTO) {
        // 新增订单
        Order order = new Order();
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setPaymentType(orderDTO.getPaymentType());

        // 用户信息
        UserInfo user = UserInterceptor.getUser();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getName());
        order.setBuyerRate(false);

        // 收货人信息
        AddressDTO address = AddressClient.findById(1L);
        order.setReceiver(address.getName());
        order.setReceiverAddress(address.getAddress());
        order.setReceiverCity(address.getCity());
        order.setReceiverDistrict(address.getDistrict());
        order.setReceiverMobile(address.getPhone());
        order.setReceiverState(address.getState());
        order.setReceiverZip(address.getZipCode());

        // 金额
        List<CartDTO> carts = orderDTO.getCarts();
        Map<Long, Integer> map = carts.stream().collect(Collectors.toMap(CartDTO::getSkuId, CartDTO::getNum));
        List<Sku> skuList = goodsClient.querySkuBySpuId(new ArrayList<>(map.keySet()));

        // orderDetail 集合
        List<OrderDetail> orderDetailList = new ArrayList<>();

        // 计算总价
        long totalPay = 0L;
        for (Sku sku : skuList) {
            totalPay += sku.getPrice() * map.get(sku.getId());

            // 订单详情
            OrderDetail detail = new OrderDetail();
            detail.setImage(StringUtils.substringBefore(sku.getImages(),","));
            detail.setNum(map.get(sku.getId()));
            detail.setOrderId(orderId);
            detail.setOwnSpec(sku.getOwnSpec());
            detail.setSkuId(sku.getId());
            detail.setTitle(sku.getTitle());
            orderDetailList.add(detail);
        }
        order.setTotalPay(totalPay);
        // 实际金额 = 总金额 - 优惠金额
        order.setActualPay(totalPay - order.getPostFee() - 0);

        // 写入数据库
        int num = orderMapper.insertSelective(order);
        if(num != 1){
            log.error("创建订单失败 orderId => {} ", orderId);
            throw new CloudException(ExceptionEnum.CREATE_ORDER_ERROR);
        }
        // 新增订单详情
        int count = detailMapper.insertList(orderDetailList);
        if(orderDetailList.size() != count){
            throw new CloudException(ExceptionEnum.CREATE_ORDER_ERROR);
        }

        // 新增订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreateTime(order.getCreateTime());
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderEnum.UP_PAY.getCode());
        int i = statusMapper.insert(orderStatus);
        if(i != 1){
            throw new CloudException(ExceptionEnum.CREATE_ORDER_ERROR);
        }
        // 减库存
        List<CartDTO> dtos = orderDTO.getCarts();
        goodsClient.decreaseStock(dtos);

        try {
            URL url = new URL("");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.set
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderId;
    }
}
