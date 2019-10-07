package com.cloud.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zf
 * @date 2019-09-28-18:00
 */
@Data
@Table(name = "tb_brand")
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;// 品牌名称
    private String image;// 品牌图片
    private Character letter;
}