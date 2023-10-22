package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName t_admin
 */
@TableName(value ="t_admin")
@Data
public class Admin implements Serializable {
    @TableId
    private Integer aid;

    private String username;

    private String password;

    private String nickname;

    private Integer department;

    private static final long serialVersionUID = 1L;
}