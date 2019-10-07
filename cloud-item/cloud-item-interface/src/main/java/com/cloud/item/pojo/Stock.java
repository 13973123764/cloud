package com.cloud.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zf
 * @date 2019-10-02-13:25
 */
@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    private Long skuId;
    private Integer seckillStock;
    private Integer seckillTotal;
    private Integer stock;

}
