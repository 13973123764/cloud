package com.cloud.item.api;

import com.cloud.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zf
 * @date 2019-10-03-14:45
 */
public interface BrandApi {

    @GetMapping("brand/{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);

}
