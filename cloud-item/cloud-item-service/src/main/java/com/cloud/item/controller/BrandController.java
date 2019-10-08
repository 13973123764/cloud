package com.cloud.item.controller;

import com.cloud.item.pojo.Brand;
import com.cloud.item.pojo.Category;
import com.cloud.item.service.BrandService;
import com.cloud.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-28-18:03
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * 分页查询品牌列表
     * @param page         页码
     * @param pageCount    页面大小
     * @param search       品牌名称过滤
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> brandPageQuery(
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "pageCount", defaultValue = "5") int pageCount,
                                        @RequestParam(value = "search", required = false) String search
    ){
        return ResponseEntity.ok(brandService.brandPageQuery(page, pageCount, search));
    }


    /**
     * 新增品牌
     * @param cids     分类id集合
     * @param brand    品牌pojo对象
     * @return
     */
    @PostMapping("saveBrand")
    public ResponseEntity<Void> saveBrand(@RequestParam("cids") List<Long> cids, Brand brand){
        // id 为空新增， 不空修改
        if(null == brand.getId()){
            brandService.saveBrand(cids, brand);
        }else {
            brandService.updateBrand(cids,brand);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    };

    /**
     * 通过主键删除品牌
     * @param id
     * @return
     */
    @PostMapping("deleteBrandById")
    public ResponseEntity<Void> deleteBrandById(@RequestParam("id") long id){
        brandService.deleteBrandById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询父分类
     * @param id
     * @return
     */
    @GetMapping("queryParentCategoryById")
    public ResponseEntity<Category> queryParentCategoryById(@RequestParam("id") long id){
        return ResponseEntity.ok(brandService.queryParentCategoryById(id));
    }

    /**
     * 根据cid 查询品牌
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据id 查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }

    /**
     * 查询品牌集合
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(brandService.queryBrandByIds(ids));
    }




}
