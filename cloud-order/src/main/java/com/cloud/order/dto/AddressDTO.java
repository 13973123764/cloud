package com.cloud.order.dto;

import lombok.Data;

/**
 * @author zf
 * @date 2019-10-07-12:53
 */
@Data
public class AddressDTO {

    private Long id;
    private String name;
    private String phone;
    private String state;
    private String city;
    private String district;
    private String address;
    private String zipCode;
    private Boolean isDefault;

}
