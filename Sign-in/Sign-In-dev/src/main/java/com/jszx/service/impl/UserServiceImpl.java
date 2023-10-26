package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.TestUser;
import com.jszx.service.UserService;
import com.jszx.mapper.UserMapper;
import com.jszx.utils.Result;
import com.jszx.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(TestUser user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if (loginUser==null){
            return Result.build(null,ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }

        if (!loginUser.getPassword().equals(user.getUsername())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        return Result.ok(user);
    }

    @Override
    public Result register(User user) {
        int rows = userMapper.insert(user);
        if (rows<1){
            return Result.build(null,ResultCodeEnum.REGISTER_FAILED);
        }
        return Result.ok(user);
    }

    @Override
    public Result queryUsersBySno(Integer sno) {
        //id不存在默认查1号用户
        if (sno==null){
            sno=1;
        }
        User user = userMapper.selectById(sno);
        if (user==null){
            return Result.build(null,ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }

        return Result.ok(user);
    }

    @Override
    public Result updateUser(User user) {
        int row = userMapper.updateById(user);
        if (row<1){
            return Result.build(null,ResultCodeEnum.UPDATE_ERROR);
        }
        return Result.ok(user);
    }
}




