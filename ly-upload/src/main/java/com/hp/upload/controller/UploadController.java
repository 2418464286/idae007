package com.hp.upload.controller;

import com.hp.upload.service.UploadService;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        String url=uploadService.uploadImage(file);
        if (url!=null){
            return  ResponseEntity.ok(url);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
