package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.LoginUser;
import com.jszx.service.UserService;
import com.jszx.mapper.UserMapper;
import com.jszx.utils.Result;
import com.jszx.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
//TODO login mapper 设计
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(LoginUser user) {
        if (user==null){
           return Result.build(null, ResultCodeEnum.USERNAME_ERROR_USER_IS_EMPTY);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LoginUser::getUsername)
        User loginUser = userMapper.selectByUsername();
        if (loginUser==null){
            return Result.build(null,ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }
        return Result.ok(user);
    }

    @Override
    public Result register(LoginUser user) {

        return null;
    }

    @Override
    public Result queryUsersById(Integer id) {
        return null;
    }
}




