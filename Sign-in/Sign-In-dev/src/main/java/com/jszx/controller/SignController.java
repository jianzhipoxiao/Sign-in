package com.jszx.controller;

import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘林
 * @version 1.0
 */
@RestController
@RequestMapping("Sign")
public class SignController {
    @Autowired
    private RecodrFromService recodrFromService;
    @PostMapping("sginIn")
    private Result SignIn(@RequestBody SignIn signIn){
        Result result = recodrFromService.signIn(signIn);
        return  result;
    }

    @PutMapping("signOut")
    private Result SignOut(@RequestBody SignOut signOut){
        Result result = recodrFromService.signOut(signOut);
        return result;
    }


    @GetMapping("/getRomeOnlineUsers")
    public Result queryRomeOnlienUsers(){
        Result result = recodrFromService.queryRoomOnlieUsers();
        return result;
    }

    @GetMapping("/getCarryKeyUser")
    public Result queryCarryKeyUser(){
        Result result = recodrFromService.queryCarryKeyUser();
        return result;
    }

}
