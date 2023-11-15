package com.jszx.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 */
@Data
public class ShowUser {
    private static final long serialVersionUID = 1L;
    @TableId
    private String name;
    @NotNull(message = "用户名不能为空")
    private String username;
    private String phone;
    private String department;
    private String grade;
    @NotNull(message = "学号不能为空")
    private String sno;
    private String major;
    private String sex;
    private String image;

}
