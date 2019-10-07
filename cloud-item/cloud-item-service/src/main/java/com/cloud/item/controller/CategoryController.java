package com.cloud.item.controller;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.pojo.Category;
import com.cloud.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-27-12:28
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 根据父id 查询商品分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam(defaultValue = "0") Long pid){
        return ResponseEntity.ok(categoryService.queryCategoryByPid(pid));
    }


    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}
