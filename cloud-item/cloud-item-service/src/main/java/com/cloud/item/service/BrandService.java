package com.cloud.item.service;

import com.cloud.enums.ExceptionEnum;
import com.cloud.exception.CloudException;
import com.cloud.item.mapper.BrandMapper;
import com.cloud.item.mapper.CategoryMapper;
import com.cloud.item.pojo.Brand;
import com.cloud.item.pojo.Category;
import com.cloud.result.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-28-18:02
 */
@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;


    public PageResult<Brand> brandPageQuery(int page, int pageCount, String search) {
        // 分页
        PageHelper.startPage(page, pageCount);

        // 条件过滤
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(search)){
            //品牌名称
            criteria.andLike("name", "%"+search+"%");
        }
        // 排序
        example.setOrderByClause("id ASC");

        // 查询
        List<Brand> brandList = brandMapper.selectByExample(example);

        PageInfo<Brand> pageInfo = new PageInfo<>(brandList);
        if(pageInfo.getTotal() == 0){
            throw new CloudException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return new PageResult<>(pageInfo.getTotal(), brandList);
    }


    @Transactional
    public void saveBrand(List<Long> cids, Brand brand) {
        // 保存品牌
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if(count < 1){
            throw new CloudException(ExceptionEnum.SAVE_BRAND_ERROR);
        }

        // 保存分类
        for (Long cid : cids) {
            int num = brandMapper.saveCategoryBrand(cid, brand.getId());
            if(num < 1){
                throw new CloudException(ExceptionEnum.SAVE_BRAND_ERROR);
            }
        }
    }


    public void deleteBrandById(long id) {
        Brand brand = new Brand();
        brand.setId(id);
        int num = brandMapper.delete(brand);
        if(num < 1){
            throw new CloudException(ExceptionEnum.SYSTEM_ERROR);
        }

    }

    @Autowired
    CategoryMapper categoryMapper;

    public Category queryParentCategoryById(long id) {
        Category category = brandMapper.queryCategoryByBrandId(id);
        if(null == category){
            throw new CloudException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }

        Category parent1 = brandMapper.recursiveCategory(category);
        if(null != parent1){
            parent1.getChildren().add(category);
            Category parent2 = brandMapper.recursiveCategory(parent1);
            if(null != parent2){
                parent2.getChildren().add(parent1);
                return parent2;
            }else{
                return parent1;
            }
        }
        return category;
    }


    public void updateBrand(List<Long> cids, Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
        brandMapper.deleteCategoryBrand(brand.getId());
        // 保存分类
        int num = brandMapper.saveCategoryBrand(cids.get(cids.size()-1), brand.getId());
        if(num < 1){
            throw new CloudException(ExceptionEnum.SAVE_BRAND_ERROR);
        }
    }


    public Brand queryById(Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if(null == brand){
            throw new CloudException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }


    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> brandList = brandMapper.queryBrandByCid(cid);
        if(CollectionUtils.isEmpty(brandList)){
            throw new CloudException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brandList;
    }

    public List<Brand> queryBrandByIds(List<Long> ids) {
        List<Brand> brands = brandMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(brands)){
            throw new CloudException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }
}
