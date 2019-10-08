package com.cloud.search.pojo;

import com.cloud.item.pojo.Brand;
import com.cloud.item.pojo.Category;
import com.cloud.result.PageResult;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author zf
 * @date 2019-10-08-10:51
 */
public class SearchResult extends PageResult<Goods> {

    /** 分类 */
    private List<Category> categories;
    /** 品牌 */
    private List<Brand> brands;
    /** 规格参数 */
    private List<Map<String, Object>> specs;

    public SearchResult(long total, int pageTotal, List<Goods> items,
                        List<Category> categories, List<Brand> brands,
                        List<Map<String, Object>> specs) {
        super(total, pageTotal, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public SearchResult(int pageTotal, List<Goods> items) {
        super(pageTotal, items);
    }

    public SearchResult(long total, List<Goods> items) {
        super(total, items);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
