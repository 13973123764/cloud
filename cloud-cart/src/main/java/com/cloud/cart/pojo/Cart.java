package com.cloud.cart.pojo;

import lombok.Data;

/**
 * @author zf
 * @date 2019-10-06-19:09
 */
@Data
public class Cart {

    /** 商品id */
    private Long skuId;
    /** 标题 */
    private String title;
    /** 图片 */
    private String image;
    /** 价格 */
    private Long price;
    /** 购买数量 */
    private Integer num;
    /** 规格参数 */
    private String ownSpec;

}
