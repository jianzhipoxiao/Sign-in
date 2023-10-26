package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

/**
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private String name;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    private String phone;
    private Integer department;
    private String grade;
    @NotNull(message = "学号不能为空")
    private String sno;
    private String major;
    private Integer status;
    private String sex;
    private String image;
}