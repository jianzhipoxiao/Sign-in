package com.jszx.controller;

import com.jszx.pojo.User;
import com.jszx.pojo.vo.TestUser;
import com.jszx.service.UserService;
import com.jszx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘林
 * @version 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody TestUser testUser){
        Result result = userService.login(testUser);
        return result;
    }

    @PostMapping()
    public Result register(@RequestBody User user){
        Result result = userService.register(user);
        return result;
    }

    @GetMapping("{sno}")
    public Result queryUsersById(@PathVariable(name = "sno") Integer sno){
        Result result = userService.queryUsersBySno(sno);
        return result;
    }

    @PutMapping()
    public Result updateUser(@RequestBody User user){
        Result result =userService.updateUser(user);
        return result;
    }

}
