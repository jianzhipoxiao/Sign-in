package com.jszx.controller;

import com.jszx.pojo.vo.PortalVo;
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
@Api(tags = "签到接口")
@RestController
@CrossOrigin
@RequestMapping("Sign")
public class SignController {
    @Autowired
    private RecodrFromService recodrFromService;

    @ApiOperation("用户签到")
    @PostMapping("sginIn")
    private Result SignIn(@RequestBody SignIn signIn, @RequestHeader String token) {
        Result result = recodrFromService.signIn(signIn, token);
        return result;
    }

    @ApiOperation("用户签出")
    @PostMapping("signOut")
    private Result SignOut(@RequestBody SignOut signOut, @RequestHeader String token) {
        Result result = recodrFromService.signOut(signOut, token);
        return result;
    }

    @ApiOperation("用户查看工作室是否有人分页")
    @PostMapping("findOnlineUserPage")
    public Result findOnlineUserPage(@RequestBody PortalVo portalVo) {
        Result result =recodrFromService.findOnlineUserPage(portalVo);
        return result;
    }

    @ApiOperation("用户查看工作室是否有人")
    @GetMapping("/getRomeOnlineUsers")
    public Result queryRomeOnlienUsers() {
        Result result = recodrFromService.queryRoomOnlineUsers();
        return result;
    }

    @ApiOperation("用户查询谁携带钥匙")
    @GetMapping("/getCarryKeyUser")
    public Result queryCarryKeyUser() {
        Result result = recodrFromService.queryCarryKeyUser();
        return result;
    }

    @ApiOperation("用户转交钥匙")
    @PutMapping()
    public Result transmitKey(@RequestHeader String token, @RequestParam String userName) {
        Result result = recodrFromService.updateByUserName(token, userName);
        return result;
    }

}
