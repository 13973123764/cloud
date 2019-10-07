package com.cloud.item.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zf
 * @date 2019-10-01-18:33
 */
@Data
public class SpuVo {

    private Long id;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String title;
    private String subTitle;
    private Boolean saleable;
    private Date createTime;
}
