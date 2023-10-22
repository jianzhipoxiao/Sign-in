package com.jszx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.Admin;
import com.jszx.service.AdminService;
import com.jszx.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_admin】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




