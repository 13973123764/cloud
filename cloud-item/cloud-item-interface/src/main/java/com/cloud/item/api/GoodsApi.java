package com.cloud.item.api;

import com.cloud.item.pojo.Category;
import com.cloud.item.pojo.Sku;
import com.cloud.item.pojo.Spu;
import com.cloud.item.pojo.SpuDetail;
import com.cloud.result.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-03-14:38
 */
public interface GoodsApi {

    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);

    @GetMapping("spu/detail/{id}")
    public SpuDetail queryDetailById(@PathVariable("id") Long spuId);


    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    );


    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);


    @GetMapping("sku/list/ids")
    public List<Sku> querySkuBySpuId(@RequestParam("ids") List<Long> spuIds);





}
