package com.jszx.pojo.vo;

import com.jszx.pojo.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘林
 * @version 1.0
 * @deprecated  该类用于存储转可以转交钥匙的的用户，以部门为单位
 * @createTime 2023-11-13
 */
@Data
public class CarryKeyUsersList {
    //所属部门
    private String department;
    //部门用户
    private List<String> dUsers = new ArrayList<>();

    public CarryKeyUsersList(String department) {
        this.department = department;
    }

    public void addUser(User user){
        this.dUsers.add(user.getName());
    }
}
