package com.jszx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.Department;
import com.jszx.service.DepartmentService;
import com.jszx.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_department】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




