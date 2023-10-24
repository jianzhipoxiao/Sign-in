package com.jszx.service;

import com.jszx.pojo.RecodrFrom;
import com.baomidou.mybatisplus.extension.service.IService;
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
    Result signIn(SignIn signIn);

    //签出
    Result signOut(SignOut signOut);
}
