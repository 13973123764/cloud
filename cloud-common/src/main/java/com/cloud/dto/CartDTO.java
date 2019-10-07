package com.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zf
 * @date 2019-10-07-10:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long skuId;
    private Integer num;

}
