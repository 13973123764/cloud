package com.cloud.order.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zf
 * @date 2019-10-07-10:00
 */
@Data
@Table(name = "tb_order_status")
public class OrderStatus {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long orderId;
    private Integer status;
    private Date createTime;
    private Date paymentTime;
    private Date consignTime;
    private Date endTime;
    private Date closeTime;
    private Date commentTime;




}
