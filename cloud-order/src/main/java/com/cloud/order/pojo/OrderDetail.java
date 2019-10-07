package com.cloud.order.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zf
 * @date 2019-10-07-10:00
 */
@Data
@Table(name = "tb_order_detail")
public class OrderDetail {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long orderId;
    private Long skuId;
    private Integer num;
    private String title;
    private Long price;
    private String ownSpec;
    private String image;

}
