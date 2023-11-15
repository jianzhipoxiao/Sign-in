package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.config.DepartmentMsg;
import com.jszx.mapper.DepartmentMapper;
import com.jszx.pojo.Department;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.CarryKeyUsersList;
import com.jszx.pojo.vo.TestUser;
import com.jszx.utils.ShowMessage;
import com.jszx.service.RecodrFromService;
import com.jszx.service.UserService;
import com.jszx.mapper.UserMapper;
import com.jszx.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lenovo
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2023-10-22 23:23:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RecodrFromService recodrFromService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 1验证账号密码
     * 2 返回用户携带的token
     * 3成功来到首页
     * @param user
     * @return
     */
    @Override
    public Result login(TestUser user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }

        if (!loginUser.getPassword().equals(MD5Util.encrypt(user.getPassword()))) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        long uid = loginUser.getId().longValue();
        String token = jwtHelper.createToken(uid);
        //获取在线的用户
        return Result.ok(token);
    }

    @Override
    public Result register(User user) {

        //检测密码是否未空
        if (user.getPassword() != null) {
            user.setPassword(MD5Util.encrypt(user.getPassword()));
        }
        //检测账号是否被注册过
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername,user.getUsername())
                        .eq(User::getSno,user.getSno());
        User tmpUser = userMapper.selectOne(userLambdaQueryWrapper);
        if (tmpUser!=null){
            //用户已存在
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        //注册账号
        int rows = userMapper.insert(user);
        if (rows < 1) {
            return Result.build(null, ResultCodeEnum.REGISTER_FAILED);
        }
        user.setPassword("");
        return Result.ok(user);
    }

    @Override
    public Result queryUsersByToken(String token) {

        int userId = jwtHelper.getUserId(token).intValue();
        //id不存在默认查1号用户

        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR_NO_USER);
        }
        LambdaQueryWrapper<Department> departmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        departmentLambdaQueryWrapper.eq(Department::getId,user.getDepartment());
        Department department = departmentMapper.selectOne(departmentLambdaQueryWrapper);
        HashMap<String, String> userMessages = ShowMessage.getShowUser(user, department);
        return Result.ok(userMessages);
    }

    @Override
    public Result updateUser(String token,User user) {
        int userId = jwtHelper.getUserId(token).intValue();
        user.setId(userId);
        int row = userMapper.updateById(user);
        if (row < 1) {
            return Result.build(null, ResultCodeEnum.UPDATE_ERROR);
        }
        User newUser = userMapper.selectById(userId);
        if (newUser.getDepartment()==null){
            newUser.setDepartment(1);
        }
        LambdaQueryWrapper<Department> departmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        departmentLambdaQueryWrapper.eq(Department::getId,newUser.getDepartment());
        Department department = departmentMapper.selectOne(departmentLambdaQueryWrapper);
        HashMap<String, String> userMessages = ShowMessage.getShowUser(newUser, department);
        return Result.ok(userMessages);
    }

    @Override
    public Result queryUsersAll() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getGrade,"大一")
                        .or().eq(User::getGrade,"大二");
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);
        if (userList==null){
            return Result.build("查询失败",ResultCodeEnum.SELECT_ERROR);
        }
        List<String> userListNames = new ArrayList<>();
        Iterator<User> iterator = userList.iterator();
        CarryKeyUsersList kaifa = new CarryKeyUsersList(DepartmentMsg.kaifa);
        CarryKeyUsersList sheji = new CarryKeyUsersList(DepartmentMsg.sheji);
        CarryKeyUsersList sheying = new CarryKeyUsersList(DepartmentMsg.sheying);

        //遍历查询到的所有用户，按部门分类
        while (iterator.hasNext()) {
            User user = iterator.next();
            Department department = departmentMapper.selectById(user.getDepartment());
            String dname = department.getDname();
            if (dname.equals(DepartmentMsg.kaifa)){
                kaifa.addUser(user);
            } else if (dname.equals(DepartmentMsg.sheji)){
                sheji.addUser(user);
            } else if (dname.equals(DepartmentMsg.sheying)){
                sheying.addUser(user);
            }
        }
        List<CarryKeyUsersList> carryKeyUsersLists = new ArrayList<>();
        carryKeyUsersLists.add(kaifa);
        carryKeyUsersLists.add(sheji);
        carryKeyUsersLists.add(sheying);
        return Result.ok(carryKeyUsersLists);
    }
}




