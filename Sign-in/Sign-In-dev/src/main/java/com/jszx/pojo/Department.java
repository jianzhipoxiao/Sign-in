package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName t_department
 */
@TableName(value ="t_department")
@Data
public class Department implements Serializable {
    @TableId
    private Integer id;

    private String dname;

    private Long dleader;

    private static final long serialVersionUID = 1L;
}