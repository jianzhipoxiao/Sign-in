package com.jszx.controller;


import com.jszx.pojo.vo.TestUser;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("test")
public class testController {
    @PostMapping("/user")
    public String test(@RequestBody TestUser testUser){
        System.out.println("testUser = " + testUser);
        return "ok";
    }
}
