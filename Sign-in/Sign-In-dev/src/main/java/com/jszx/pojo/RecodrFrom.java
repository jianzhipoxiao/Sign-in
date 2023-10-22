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
public class RecodrFrom implements Serializable {
    @TableId
    private Long rid;

    private Integer user;

    private Date inTime;

    private String type;

    private Date outTime;

    private String carryKey;
    @Version
    private Integer version;

    private static final long serialVersionUID = 1L;
}