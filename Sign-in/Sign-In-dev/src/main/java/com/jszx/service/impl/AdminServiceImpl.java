package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.mapper.DepartmentMapper;
import com.jszx.mapper.RecodrFromMapper;
import com.jszx.mapper.UserMapper;
import com.jszx.pojo.Admin;
import com.jszx.pojo.Department;
import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.User;
import com.jszx.pojo.dto.AdminUserDto;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.RecoderVo;
import com.jszx.service.AdminService;
import com.jszx.mapper.AdminMapper;
import com.jszx.utils.JwtHelper;
import com.jszx.utils.MD5Util;
import com.jszx.utils.Result;
import com.jszx.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 * @description 针对表【t_admin】的数据库操作Service实现
 * @createDate 2023-10-22 23:23:55
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecodrFromMapper recodrFromMapper;
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据username取出admin再判断密码
     *
     * @param admin 登录管理员用户
     * @return 数据库忠的管理员
     */
    @Override
    public Result adminLogin(AdminUserDto admin) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getUsername, admin.getUsername());
        Admin currenAdmin = adminMapper.selectOne(lambdaQueryWrapper);
        if (currenAdmin == null) {
            return Result.build("用户不存在", ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }

        if (!currenAdmin.getPassword().equals(MD5Util.encrypt(admin.getPassword()))) {
            return Result.build("密码错误", ResultCodeEnum.PASSWORD_ERROR);
        }
        String token = jwtHelper.createToken(currenAdmin.getAid().longValue());
        return Result.ok(token);
    }


    /**
     * 根据部门id查询用户信息
     * @param department
     * @return
     */
    @Override
    public Result queryAllUser(Integer department) {
        //1根据部门id查询出部长
        LambdaQueryWrapper<Department> departmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        departmentLambdaQueryWrapper.eq(Department::getId,department);
        Department currenDepartment = departmentMapper.selectOne(departmentLambdaQueryWrapper);
        if (currenDepartment==null){
            return Result.build(null,ResultCodeEnum.DEPARTMENT_ERROR_NO_DEPARTMENT);
        }
        User dLeader = userMapper.selectById(currenDepartment.getId());

        //2查询部员
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getDepartment,currenDepartment.getId());
        List<User> departmentUsersMembers = userMapper.selectList(userLambdaQueryWrapper);

        //3将部长和部员分开封装到map中
        HashMap<String, User> departmentUsers = new HashMap<>();
        departmentUsers.put("部长",dLeader);
        Iterator<User> userIterator = departmentUsersMembers.iterator();
        while (userIterator.hasNext()) {
            User userMember = userIterator.next();
            if (departmentUsers.containsValue(userMember)){
                continue;
            }
            departmentUsers.put("部员",userMember);
        }
        return Result.ok(departmentUsers);
    }

    @Override
    public Result updateAdminMsg(Admin admin) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getAid, admin.getAid());
        Admin newAdmin = adminMapper.selectOne(adminLambdaQueryWrapper);
        newAdmin.setNickname(admin.getNickname());
        if (admin.getPassword() != "") {
            newAdmin.setPassword(MD5Util.encrypt(admin.getPassword()));
        }
        int rows = adminMapper.updateById(newAdmin);
        newAdmin.setPassword("");
        return Result.ok(newAdmin);
    }

    @Override
    public Result queryRecodrFromByPage(RecoderVo recoderVo) {
        IPage<Map> page = new Page<>(recoderVo.getPageNum(),recoderVo.getPageSize());
        recodrFromMapper.selectAllUserPageMap(page,recoderVo);
        //封装签到人员，
        HashMap<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());
        HashMap<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        return Result.ok(pageInfo);
    }

    @Override
    public Result checkLogin(String token) {
        //检查是否过期
        if (jwtHelper.isExpiration(token)){
            //过期
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //检测是否是已注册的信息
        int uid = jwtHelper.getUserId(token).intValue();
        Admin admin = adminMapper.selectById(uid);
        if (admin==null){
            //没有该用户
            return Result.build(null,ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }
        return Result.ok("已登录");
    }
}




