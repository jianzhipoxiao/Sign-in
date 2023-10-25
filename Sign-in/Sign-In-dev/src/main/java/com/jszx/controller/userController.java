package com.jszx.controller;

import com.jszx.pojo.User;
import com.jszx.pojo.vo.LoginUser;
import com.jszx.service.UserService;
import com.jszx.utils.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘林
 * @version 1.0.0
 * @deprecated user controller
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginUser user) {
        Result result = userService.login(user);
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody LoginUser user){
        Result result = userService.register(user);
        return result;
    }

    @GetMapping("/{id}")
    public Result queryAll(@PathVariable(name = "id")Integer id){
        Result result = userService.queryUsersById(id);
        return result;
    }

}


