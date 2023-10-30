package com.jszx.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("签到传输数据模型")
@Data
public class SignIn {
    //用户
    @ApiModelProperty("用户id")
    private Integer user;
    //签出时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("签到时间")
    private Date inTime;
    //地点
    @ApiModelProperty("签到地点")
    private String place;
    //类型
    @ApiModelProperty("签到类型")
    private String type;
    //携带钥匙
    @ApiModelProperty("是否携带钥匙")
    private String key;
}