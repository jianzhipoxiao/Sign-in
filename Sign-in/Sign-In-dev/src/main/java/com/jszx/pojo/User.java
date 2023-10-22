package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class User implements Serializable {
    @TableId
    private Integer id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private Integer department;

    private String grade;

    private String sno;

    private String major;

    private Integer status;

    private String sex;

    private String image;

    private static final long serialVersionUID = 1L;
}