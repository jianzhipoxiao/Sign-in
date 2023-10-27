package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName t_recodr_from
 */
@TableName(value ="t_recodr_from")
@Data
public class
RecodrFrom implements Serializable {
    @TableId
    private String rid;

    private Integer user;

    private Date inTime;

    private String type;

    private Date outTime;

    private String carryKey;
    private String transmitKey;
    @Version
    private Integer version;
    //签到记录 0 未签出
    private Integer state =0;

    private static final long serialVersionUID = 1L;
}