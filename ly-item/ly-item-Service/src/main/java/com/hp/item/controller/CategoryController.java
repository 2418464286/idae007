package com.hp.item.controller;

import com.hp.item.service.CategoryService;
import com.leyou.item.pojo.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("category")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByparentId(@RequestParam("pid")Long id){
        List<Category> catelist=categoryService.queryByparentId(id);
        if(catelist!=null && catelist.size()>0){
            return ResponseEntity.ok(catelist);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("bid/{}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid")Long id){
        List<Category> categoryList=categoryService.queryByBrandId(id);
        if (categoryList!=null && categoryList.size()>0){
            return  ResponseEntity.ok(categoryList);

        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
    List<String> list=categoryService.queryNamesByIds(ids);
        if(list!=null && list.size()>0){
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
