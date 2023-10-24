package com.jszx.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @deprecated 用于接收用户签到信息
 */

@Data
public class SignIn {
    //用户
    private Integer user;
    //签入时间
    private Date InTime;
    //地点
    private String place;
    //类型
    private Integer type;
}
