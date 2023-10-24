package com.jszx.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//TODO:设计ControllerAPI测试接口
@RestController
@CrossOrigin
@RequestMapping("test")
public class testController {
    @GetMapping("ok")
    public String test(){
        return "ok";
    }
}
