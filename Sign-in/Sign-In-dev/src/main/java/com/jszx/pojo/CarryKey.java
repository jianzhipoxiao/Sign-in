package com.jszx.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 */
@Data
public class CarryKey {
    @TableId
    private Integer cId;

    private Integer cUser;
}
