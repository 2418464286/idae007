package com.hp.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import javafx.scene.paint.Stop;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {
    @Autowired
      private FastFileStorageClient fastFileStorageClient;

 public String uploadImage(MultipartFile file) {
//        //创建file对象
//        File f=new File("D:\\a");
//        if (!f.exists()){
//            f.mkdirs();
//        }
//        //保存图片
//        try {
//            file.transferTo(new File(f,file.getOriginalFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  "http://image.leyou.com/"+file.getOriginalFilename();
  //获取浏览器传递的图片后缀
     String url=null;
     String originalFilename=file.getOriginalFilename();
    String ext=StringUtils.substringAfter(originalFilename,",");
   //上传
     try {
        StorePath storePath= fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(),ext,null);
     url="http://image.leyou.com/"+storePath.getFullPath();
     } catch (IOException e) {
         e.printStackTrace();
     }
     return  url;
 }

}
