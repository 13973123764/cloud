package com.cloud.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zf
 * @date 2019-10-07-13:54
 */
@AllArgsConstructor
@Getter
public enum OrderEnum {

    UP_PAY(1, "未付款"),
    PAYED(2,"已付款,未发货"),
    DELIVERED(3,"已发货,未确认"),
    SUCCESS(4,"已确认，未评价"),
    CLOSED(5,"已关闭,交易失败"),
    RATED(6,"已评价")
    ;
    int code;
    String desc;
}
