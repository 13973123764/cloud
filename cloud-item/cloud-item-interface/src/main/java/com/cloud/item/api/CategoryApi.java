package com.cloud.item.api;

import com.cloud.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-03-14:44
 */
public interface CategoryApi {

    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
