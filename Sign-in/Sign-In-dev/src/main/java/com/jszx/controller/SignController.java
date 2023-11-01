package com.jszx.controller;

import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.utils.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

/**
 * @author 刘林
 * @version 1.0
 */
@Slf4j
@Api(tags="签到接口")
@RestController
@CrossOrigin
@RequestMapping("Sign")
public class SignController {
    @Autowired
    private RecodrFromService recodrFromService;
    @ApiOperation("用户签到")
    @PostMapping("sginIn")
    private Result SignIn(@RequestBody SignIn signIn){
        Result result = recodrFromService.signIn(signIn);
        return  result;
    }

    @ApiOperation("用户签出")
    @PutMapping("signOut")
    private Result SignOut(@RequestBody SignOut signOut){
        Result result = recodrFromService.signOut(signOut);
        return result;
    }


    @ApiOperation("用户查看工作室是否有人")
    @GetMapping("/getRomeOnlineUsers")
    public Result queryRomeOnlienUsers(){
        Result result = recodrFromService.queryRoomOnlieUsers();
        return result;
    }

    @ApiOperation("用户查询谁携带钥匙")
    @GetMapping("/getCarryKeyUser")
    public Result queryCarryKeyUser(){
        Result result = recodrFromService.queryCarryKeyUser();
        return result;
    }

}
