package com.hp.item.controller;

import com.hp.item.service.BrandService;
import com.leyou.item.pojo.Brand;
import com.leyou.po.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
   @Autowired
    private BrandService brandService;
   @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> pageQuery(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy",required = false) String sortBy,
                                                       @RequestParam(value = "desc",required =false) boolean desc,
                                                       @RequestParam(value = "key",required = false) String key) {
       PageResult<Brand> brandPageResult = brandService.pageQuery(page, rows, sortBy, desc, key);
       if (null != brandPageResult && brandPageResult.getItems().size()>0){
           return ResponseEntity.ok(brandPageResult);


       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
   }
@PostMapping
 public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids") List<Long> cids){
        brandService.addBrand(brand,cids);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
}
@PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids){
    brandService.updateBrand(brand,cids);
    return  ResponseEntity.status(HttpStatus.CREATED).build();
}

}
