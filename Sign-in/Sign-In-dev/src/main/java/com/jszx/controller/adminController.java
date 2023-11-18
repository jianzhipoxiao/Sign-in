package com.jszx.controller;

import com.jszx.pojo.Admin;
import com.jszx.pojo.dto.AdminUserDto;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.RecoderVo;
import com.jszx.service.AdminService;
import com.jszx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘林
 * @version 1.0
 */

@RestController
@RequestMapping("admin")
@CrossOrigin
public class adminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("login")
    public Result login(@RequestBody AdminUserDto admin){
        Result result = adminService.adminLogin(admin);
        return  result;
    }
    @GetMapping()
    private Result queryRecodrFromByPage(RecoderVo recoderVo){
        Result result = adminService.queryRecodrFromByPage(recoderVo);
        return result;
    }

    @PutMapping
    public Result updateAdmin(@RequestBody Admin admin){
        Result result = adminService.updateAdminMsg(admin);
        return result;
    }

    @GetMapping("/{department}")
    public Result queryUsersByDepartment(@PathVariable(name = "department")Integer department){
        Result result = adminService.queryAllUser(department);
        return result;
    }
}
