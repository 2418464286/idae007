package com.hp.controller;

import com.hp.pojo.Goods;

import com.hp.service.SearchService;
import com.hp.utils.SearchRequest;
import com.leyou.po.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
        PageResult<Goods> result=searchService.search(searchRequest);
        if(null!=result&&result.getItems().size()>0){
            return ResponseEntity.ok(result);


        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }


}
