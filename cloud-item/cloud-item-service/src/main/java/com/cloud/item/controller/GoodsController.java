package com.cloud.item.controller;

import com.cloud.dto.CartDTO;
import com.cloud.item.pojo.Sku;
import com.cloud.item.pojo.Spu;
import com.cloud.item.pojo.SpuDetail;
import com.cloud.item.pojo.Stock;
import com.cloud.item.service.GoodsService;
import com.cloud.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zf
 * @date 2019-10-01-18:21
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                        @RequestParam(value = "saleable", required = false) Boolean saleable,
                        @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }


    /**
     * 商品新增
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 商品修改
     * @param spu
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    /**
     * 根据spu id查询详情
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{id}")
    public ResponseEntity<SpuDetail> queryDetailById(@PathVariable("id") Long spuId){
        return ResponseEntity.ok(goodsService.queryDetailById(spuId));
    }


    /**
     * 根据spu查询下面所有的sku
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }

    /**
     * 查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        return ResponseEntity.ok(goodsService.querySpuById(id));
    }


    @GetMapping("sku/list/ids")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("ids") List<Long> spuIds){
        return ResponseEntity.ok(goodsService.querySkuBySpuIds(spuIds));
    }


    @PostMapping("stock/decreaseStock")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDTO> carts){
        goodsService.decreaseStock(carts);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
