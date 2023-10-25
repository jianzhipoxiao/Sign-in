package com.jszx.service;

import com.jszx.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jszx.pojo.vo.LoginUser;
import com.jszx.utils.Result;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-10-22 23:23:55
*/
public interface UserService extends IService<User> {
    //用户登陆
    Result login(LoginUser user);

    //用户注册
    Result register(LoginUser user);
    //查询用户信息
    Result queryUsersById(Integer id);

}
