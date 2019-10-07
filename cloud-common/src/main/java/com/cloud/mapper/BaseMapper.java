package com.cloud.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author zf
 * @date 2019-10-02-13:52
 */
public interface BaseMapper<T> extends Mapper<T>, IdListMapper<T, Long>, InsertListMapper<T> {
}
