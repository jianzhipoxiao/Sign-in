package com.jszx.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @deprecated 在线人员页面
 */
@Data
public class PortalVo {
    //默认第一页
    @ApiModelProperty("分页数量")
    private Integer pageNum = 1;
    //一页十条数据
    @ApiModelProperty("一页有多少数据")
    private Integer pageSize =6;
}