package com.jszx.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘林
 * @version 1.0
 * @caeateDate 2023-11-20
 * @description 该类用于从前端接收admin查询用户签到情况条件
 */
@Data
public class RecoderVo extends PortalVo{
    private Integer department;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date selectTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date selectTimeEnd;
    //签到用户
    private String name;
    //签到类型
    private String type;




























}
