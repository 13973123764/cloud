package com.cloud.item.mapper;

import com.cloud.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zf
 * @date 2019-09-27-12:25
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {
}
