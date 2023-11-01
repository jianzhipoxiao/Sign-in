package com.jszx.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jszx.pojo.vo.TestUser;
import org.springframework.web.bind.annotation.*;

@ApiSupport(author = "xiaoymin@foxmail.com",order = 284)
@RestController
@CrossOrigin
@RequestMapping("test")
public class testController {
    @PostMapping("/user")
    public String test(@RequestBody TestUser testUser){
        System.out.println("testUser = " + testUser);
        return "ok";
    }

    @GetMapping("ok")
    public String ok (){
        return "ok";
    }
}
