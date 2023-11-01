package com.jszx.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.TestUser;
import com.jszx.service.UserService;
import com.jszx.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘林
 * @version 1.0
 */
@Api(tags = "2.0.3版本-20200312")
@ApiSupport(author = "Ll15934715895@126.com",order = 284)
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperationSupport(author = "Ll15934715895@126.com")
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
