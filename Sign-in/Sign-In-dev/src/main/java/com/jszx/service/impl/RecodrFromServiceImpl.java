package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.mapper.UserMapper;
import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.User;
import com.jszx.utils.Room;
import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.mapper.RecodrFromMapper;
import com.jszx.utils.Result;
import com.jszx.utils.ResultCodeEnum;
import com.jszx.utils.Sign;
import com.jszx.utils.SignMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lenovo
 * @description 针对表【t_recodr_from】的数据库操作Service实现
 * @createDate 2023-10-22 23:23:55
 */
@Service
public class RecodrFromServiceImpl extends ServiceImpl<RecodrFromMapper, RecodrFrom>
        implements RecodrFromService {
    @Autowired
    private Room room;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RecodrFromMapper recodrFromMapper;

    public Result signIn(SignIn signIn) {
        if (signIn.getPlace().equals(Sign.SIGIN_PLACE.getPlace())) {
            RecodrFrom recodrFrom = new RecodrFrom();
            recodrFrom.setRid(UUID.randomUUID().toString());
            recodrFrom.setUser(signIn.getUser());
            recodrFrom.setInTime(new Date());
            recodrFrom.setType(signIn.getType());
            recodrFrom.setCarryKey(signIn.getKey());
            int rows = recodrFromMapper.insert(recodrFrom);
            if (rows < 1) {
                return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
            }
            return Result.ok("签到成功");
        }
        return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
    }

    @Transactional
    public Result signOut(SignOut signOut) {
        if (signOut.getPlace().equals(Sign.SIGIN_PLACE.getPlace())) {
            LambdaQueryWrapper<RecodrFrom> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RecodrFrom::getUser, signOut.getUser())
                    .eq(RecodrFrom::getState, 0);
            RecodrFrom recodrFrom = recodrFromMapper.selectOne(wrapper);
            if (signOut.getOutTime().getTime() - recodrFrom.getInTime().getTime() <= (SignMessage.getSignTimeWatch())) {
                return Result.build("签出失败，未到规定时间", ResultCodeEnum.SIGN_OUT_ERROR);
            }
            recodrFrom.setOutTime(signOut.getOutTime());
            recodrFrom.setType(signOut.getType());
            recodrFrom.setCarryKey(signOut.getKey());
            recodrFrom.setState(1);
            int rows = recodrFromMapper.updateById(recodrFrom);
            if (rows < 1) {
                return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
            }


            return Result.ok("签出成功");
        }
        return Result.build("签出位置不符合", ResultCodeEnum.SIGN_OUT_ERROR);
    }

    /**
     * 1根据recoderFrom表中state为0（未签出的）获取签到记录中Userid
     * 2 state = 0 -> userId->userMessage
     *
     * @return
     */
    @Override
    public Result queryRoomOnlieUsers() {
        LambdaQueryWrapper<RecodrFrom> recodrFromLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getState, 0);

        List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(recodrFromLambdaQueryWrapper);
        if (recodrFroms == null) {
            return Result.ok("工作室目前没人哦~");
        }


        List<User> onlineUsers = room.getOnlineUser();
        //封装网站在线人员，
        Iterator<RecodrFrom> recodrFromIterator = recodrFroms.iterator();
        while (recodrFromIterator.hasNext()) {
            RecodrFrom recodrFrom = recodrFromIterator.next();
            Integer userId = recodrFrom.getUser();
            User user = userMapper.selectById(userId);
            User roomOnlineUser = new User();
            roomOnlineUser.setName(user.getName());
            roomOnlineUser.setImage(user.getImage());
            roomOnlineUser.setPhone(user.getPhone());
            if (onlineUsers.contains(roomOnlineUser)) {
                continue;
            }
            onlineUsers.add(roomOnlineUser);
        }
        return Result.ok(onlineUsers);
    }

    /**
     * carrykey == 是 && TransmitKey == 未转交 -> recoder
     * ->userId->user
     *
     * @return
     */
    @Override
    public Result queryCarryKeyUser() {
        LambdaQueryWrapper<RecodrFrom> recodrFromLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getCarryKey, SignMessage.getCarryKey())
                .eq(RecodrFrom::getTransmitKey, SignMessage.getNoTransmitKey());
        List<User> getKeyUsers = room.getGetKey();
        List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(recodrFromLambdaQueryWrapper);
        Iterator<RecodrFrom> iterator = recodrFroms.iterator();
        while (iterator.hasNext()) {
            RecodrFrom recodrFrom = iterator.next();
            Integer userId = recodrFrom.getUser();
            User user = userMapper.selectById(userId);
            User carryUser = new User();
            carryUser.setName(user.getName());
            carryUser.setImage(user.getImage());
            carryUser.setPhone(user.getPhone());
            getKeyUsers.add(carryUser);
        }

        return Result.ok(getKeyUsers);
    }
}




