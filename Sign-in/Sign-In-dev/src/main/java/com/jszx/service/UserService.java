package com.jszx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.TestUser;
import com.jszx.utils.Result;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-10-22 23:23:55
*/
public interface UserService extends IService<User> {
    //用户登陆
    Result login(TestUser user);

    //用户注册
    Result register(User user);
    //查询用户信息
    Result queryUsersByToken(String token);

    //用户更新信息
    Result updateUser(String token,User user);

    //查询所有用户
    Result queryUsersAll();
}
