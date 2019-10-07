package com.cloud.item.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.mapper.CategoryMapper;
import com.cloud.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-27-12:28
 */
@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(category);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new CloudException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categoryList;
    }


    public List<Category> queryByIds(List<Long> ids){
        List<Category> categories = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(categories)){
            throw new CloudException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categories;
    }





}
