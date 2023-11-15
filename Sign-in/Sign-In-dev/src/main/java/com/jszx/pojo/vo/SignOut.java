package com.jszx.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SignOut {
    //用户
    private Integer user;
//    //签出时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date outTime;
    @NotNull
    //地点
    private String place;
    @NotNull
    //携带钥匙
    private String key;
}