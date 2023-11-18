package com.jszx.service;

import com.jszx.pojo.RecodrFrom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.utils.Result;

/**
* @author lenovo
* @description 针对表【t_recodr_from】的数据库操作Service
* @createDate 2023-10-22 23:23:55
*/
public interface RecodrFromService extends IService<RecodrFrom> {

    //签入
    Result signIn(SignIn signIn,String tokne);

    //签出
    Result signOut(SignOut signOut,String token);

    //查询工作室在线人数
    Result queryRoomOnlineUsers();

    //查询携带工作室钥匙成员
    Result queryCarryKeyUser();

    //更新签到表中用户携带钥匙
    Result updateByUserName(String token, String userName);

    //首页用户分页
    Result findOnlineUserPage(PortalVo portalVo);
}
