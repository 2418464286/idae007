package com.hp.item.controller;

import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.po.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpuController {
    @Autowired
    private SpuService spuService;
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(@RequestParam(value = "key",required = false)String key,
                                                            @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                            @RequestParam(value = "rows",defaultValue = "5")Integer rows){
        PageResult<SpuBo> pageResult= spuService.querySpuByPage(key,saleable,page,rows);
        if (null != pageResult && pageResult.getItems().size()>0){
            return ResponseEntity.ok(pageResult);


        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        spuService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }

    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long id){
        SpuDetail spuDetail=spuService.querySpuDetailBySpuId(id);
        if(null!=spuDetail){
            return ResponseEntity.ok(spuDetail);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


    }

    ///sku/list?id=196
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
        List<Sku> skuList= spuService.querySkuBySpuId(id);
        if(skuList!=null&&skuList.size()>0){
            return ResponseEntity.ok(skuList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        spuService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }
}
