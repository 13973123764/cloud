package com.cloud.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zf
 * @date 2019-09-27-12:17
 */
@Data
@Table(name = "tb_category")
public class Category {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "is_parent")
    private Boolean isParent;
    private Integer sort;

    @Transient
    List<Category> children = new ArrayList<>();


}
