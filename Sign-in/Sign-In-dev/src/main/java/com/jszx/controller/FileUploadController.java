package com.jszx.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class FileUploadController {
    @Value("${user.headImagePath}")
    private String  userImagePath;
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {  
            try {  
                byte[] bytes = file.getBytes();
                String fileName = file.getName();
                // 使用bytes数组保存文件到磁盘或数据库等存储设备中
                return "文件上传成功！";  
            } catch (Exception e) {  
                return "文件上传失败：" + e.getMessage();  
            }  
        } else {  
            return "文件上传失败：文件为空！";  
        }  
    }  
}