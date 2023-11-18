package com.jszx.service;

import com.jszx.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jszx.pojo.dto.AdminUserDto;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.RecoderVo;
import com.jszx.utils.Result;

/**
* @author lenovo
* @description 针对表【t_admin】的数据库操作Service
* @createDate 2023-10-22 23:23:55
*/
public interface AdminService extends IService<Admin> {
    // 管理员登陆
    Result adminLogin(AdminUserDto admin);

    //查看所有用户信息
    Result queryAllUser(Integer department);

    //修改管理员信息
    Result updateAdminMsg(Admin admin);

    //查看用户签到记录
    Result queryRecodrFromByPage(RecoderVo recoderVo);

}
