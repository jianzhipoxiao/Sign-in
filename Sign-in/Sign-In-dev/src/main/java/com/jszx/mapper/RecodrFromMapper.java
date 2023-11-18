package com.jszx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jszx.pojo.RecodrFrom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.RecoderVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author lenovo
* @description 针对表【t_recodr_from】的数据库操作Mapper
* @createDate 2023-10-22 23:23:55
* @Entity com.jszx.pojo.RecodrFrom
*/
public interface RecodrFromMapper extends BaseMapper<RecodrFrom> {

    IPage<Map> selectOnlineUserPageMap(IPage page, @Param("portalvo") PortalVo portalVo);
    IPage<Map> selectAllUserPageMap(IPage page, @Param("recoderVo") RecoderVo recoderVo);
}




