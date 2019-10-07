package com.cloud.item.mapper;

import com.cloud.item.pojo.Brand;
import com.cloud.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zf
 * @date 2019-09-28-18:02
 */
public interface BrandMapper extends Mapper<Brand> {

    static final String  SQL = "b.id, b.name , b.parent_id as parentId, b.is_parent as isParent";

    @Insert("insert into tb_category_brand(category_id, brand_id) values(#{cid},#{bid})")
    int saveCategoryBrand(@Param("cid") Long cid,@Param("bid") Long id);

    @Select("select "+SQL+" from tb_category_brand a, tb_category b  where a.brand_id = #{id} and a.category_id = b.id")
    Category queryCategoryByBrandId(@Param("id") long id);

    @Select("SELECT "+SQL+"  FROM tb_category b WHERE b.id = #{parentId}")
    Category recursiveCategory(Category category);

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    int deleteCategoryBrand(@Param("id") Long id);

    @Select("select b.* from tb_category_brand a inner join tb_brand b on a.brand_id = b.id where a.category_id = #{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);

}
