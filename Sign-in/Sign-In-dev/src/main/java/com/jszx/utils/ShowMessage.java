package com.jszx.utils;

import com.jszx.pojo.Department;
import com.jszx.pojo.User;

import java.util.HashMap;

/**
 * @author 刘林
 * @version 1.0
 * 该类用于封装展示在前端页面的信息
 */

public class ShowMessage {
    public static HashMap<String, String> getShowUser(User user, Department department){

        HashMap<String, String> userMessages = new HashMap<>();
        userMessages.put("昵称",user.getName());
        userMessages.put("账号",user.getUsername());
        userMessages.put("电话",user.getPhone());
        userMessages.put("部门",department.getDname());
        userMessages.put("学号",user.getSno());
        userMessages.put("专业",user.getMajor());
        userMessages.put("性别",user.getSex());
        return userMessages;
    }

}
