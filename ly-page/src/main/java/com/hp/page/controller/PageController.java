package com.hp.page.controller;

import com.hp.page.service.FileService;
import com.hp.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {
    @Autowired
    private PageService pageService;
    @Autowired
    private FileService fileService;
@GetMapping("item/{id}.html")
    public String toPage(@PathVariable("id") Long spuId,Model model){
    model.addAllAttributes(pageService.loadData(spuId));
    //文件不存在
    if (!fileService.exists(spuId)){
        fileService.syncCreateHtml(spuId);
    }
    return  "item";
}
}
