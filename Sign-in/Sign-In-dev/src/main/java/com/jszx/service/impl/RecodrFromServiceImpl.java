package com.jszx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.mapper.RecodrFromMapper;
import com.jszx.utils.Result;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【t_recodr_from】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class RecodrFromServiceImpl extends ServiceImpl<RecodrFromMapper, RecodrFrom>
    implements RecodrFromService{

    @Override
    public Result signIn(SignIn signIn) {
        return null;
    }

    @Override
    public Result signOut(SignOut signOut) {
        return null;
    }
}




