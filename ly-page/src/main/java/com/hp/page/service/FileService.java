package com.hp.page.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Service
public class FileService {
    @Autowired
    private PageService pageService;
    //模板引擎
    @Autowired
    private TemplateEngine templateEngine;
@Value("${ly.thymeleaf.destPath}")
private  String destPath;
    //判断盘目录下存储的静态文件路径是否有文件
    public boolean exists(Long spuId) {
        File file=new File(destPath);
        if (!file.exists()){
            //创建文件夹
            file.mkdirs();
        }
        return new  File(file,spuId+".html").exists();
    }

    public void syncCreateHtml(Long spuId) {
        //创建上下文
        Context context=new Context();
        //放数据
        context.setVariables(pageService.loadData(spuId));
        //创建文件对象
        File file=new File(destPath,spuId+".html");
        //打印流
        try {
            PrintWriter printWriter=new PrintWriter(file,"utf-8");
            //产生静态文件
            templateEngine.process("item",context,printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public void deleteHtml(Long id) {
       File file= new File(destPath,id+".html");
        file.deleteOnExit();
    }
}
