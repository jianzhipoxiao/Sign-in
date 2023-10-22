package com.jszx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.User;
import com.jszx.service.UserService;
import com.jszx.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




