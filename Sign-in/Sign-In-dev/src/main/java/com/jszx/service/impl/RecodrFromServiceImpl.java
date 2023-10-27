package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.mapper.RecodrFromMapper;
import com.jszx.utils.Result;
import com.jszx.utils.ResultCodeEnum;
import com.jszx.utils.Sign;
import com.jszx.utils.SignTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
* @author lenovo
* @description 针对表【t_recodr_from】的数据库操作Service实现
* @createDate 2023-10-22 23:23:55
*/
@Service
public class RecodrFromServiceImpl extends ServiceImpl<RecodrFromMapper, RecodrFrom>
    implements RecodrFromService{

    @Autowired
    private RecodrFromMapper recodrFromMapper;
    public Result signIn(SignIn signIn) {
        if (signIn.getPlace().equals(Sign.SIGIN_PLACE.getPlace())){
            RecodrFrom recodrFrom = new RecodrFrom();
            recodrFrom.setRid(UUID.randomUUID().toString());
            recodrFrom.setUser(signIn.getUser());
            recodrFrom.setInTime(new Date());
            recodrFrom.setType(signIn.getType());
            recodrFrom.setCarryKey(signIn.getKey());
            int rows = recodrFromMapper.insert(recodrFrom);
            if (rows<1){
                return Result.build(null,ResultCodeEnum.SIGN_IN_ERROE);
            }
            return Result.ok("签到成功");
        }
        return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
    }

    @Transactional
    public Result signOut(SignOut signOut) {
        if (signOut.getPlace().equals(Sign.SIGIN_PLACE.getPlace())){
            LambdaQueryWrapper<RecodrFrom> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RecodrFrom::getUser,signOut.getUser())
                    .eq(RecodrFrom::getState,0);
            RecodrFrom recodrFrom = recodrFromMapper.selectOne(wrapper);
            if (signOut.getOutTime().getTime()-recodrFrom.getInTime().getTime()<=(SignTime.getSignTimeWatch())){
                    return Result.build("签出失败，未到规定时间",ResultCodeEnum.SIGN_OUT_ERROR);
                }
                recodrFrom.setOutTime(signOut.getOutTime());
                recodrFrom.setType(signOut.getType());
                recodrFrom.setCarryKey(signOut.getKey());
                recodrFrom.setState(1);
                int rows = recodrFromMapper.updateById(recodrFrom);
                if (rows<1){
                    return Result.build(null,ResultCodeEnum.SIGN_IN_ERROE);
                }


            return Result.ok("签出成功");
        }
        return Result.build("签出位置不符合",ResultCodeEnum.SIGN_OUT_ERROR);
    }

    @Override
    public Result queryRoomOnlieUsers() {
        LambdaQueryWrapper<RecodrFrom> recodrFromLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getState,0);

        List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(recodrFromLambdaQueryWrapper);
        if (recodrFroms==null){
            return Result.ok("工作室目前没人哦~")
        }

        return null;
    }
}




