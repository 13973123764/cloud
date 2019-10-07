package com.cloud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zf
 * @date 2019-10-07-10:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull
    private Long addressId;
    @NotNull
    private Integer paymentType;
    @NotNull
    private List<CartDTO> carts;

}
