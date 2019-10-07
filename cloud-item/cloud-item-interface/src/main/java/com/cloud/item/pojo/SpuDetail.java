package com.cloud.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zf
 * @date 2019-10-01-18:15
 */
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {

    @Id
    private Long spuId;
    private String description;
    private String specialSpec;
    private String genericSpec;
    private String packingList;
    private String afterService;


}
