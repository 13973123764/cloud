package com.cloud.item.mapper;

import com.cloud.item.pojo.Stock;
import com.cloud.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * @author zf
 * @date 2019-10-02-13:28
 */
public interface StockMapper extends BaseMapper<Stock> {


    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{skuId} and stock > #{num}")
    int decreaseStock(@Param("skuId") Long skuId,@Param("num") Integer num);

}
