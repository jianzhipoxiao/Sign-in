package com.jszx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jszx.mapper.CarryKeyMapper;
import com.jszx.mapper.UserMapper;
import com.jszx.pojo.CarryKey;
import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.User;
import com.jszx.pojo.vo.PortalVo;
import com.jszx.pojo.vo.SignUser;
import com.jszx.utils.*;
import com.jszx.pojo.vo.SignIn;
import com.jszx.pojo.vo.SignOut;
import com.jszx.service.RecodrFromService;
import com.jszx.mapper.RecodrFromMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private CarryKeyMapper carryKeyMapper;
    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 用户签到
     * 1 token-> 用户信息
     * 签到位置经度纬度来判读是否成功
     *
     * @param signIn
     * @return
     */
    public Result signIn(SignIn signIn, String token) {
        //解析token
        int userId = jwtHelper.getUserId(token).intValue();
        signIn.setUser(userId);

        //state==1为签出，签到失败
        LambdaQueryWrapper<RecodrFrom> recodrFromLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getUser, userId)
                .eq(RecodrFrom::getState, 1); //state==1 未签出
        List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(recodrFromLambdaQueryWrapper);
        if (!recodrFroms.isEmpty()) {
            return Result.build("签到失败，不能重复签到", ResultCodeEnum.SIGN_IN_ERROE);
        }
        //检查经度纬度
        if (signIn.getPlace() == null) {
            signIn.setPlace("117.5555,128.888");
        }

        //将经度维度从字符串转为BigDecimal
        String[] split = signIn.getPlace().split(",");
        BigDecimal bLongitude = new BigDecimal(split[0]);
        BigDecimal bLatitude = new BigDecimal(split[1]);
        System.out.println("bLatitude = " + bLatitude);
        System.out.println("bLongitude = " + bLongitude);

        if (SignMessage.checkPlace(bLongitude, bLatitude)) {
            RecodrFrom recodrFrom = new RecodrFrom();
            recodrFrom.setRid(UUID.randomUUID().toString());
            recodrFrom.setUser(signIn.getUser());
            recodrFrom.setInTime(new Date());
            recodrFrom.setType(signIn.getType());
            recodrFrom.setCarryKey(signIn.getKey());
            recodrFrom.setState(1);
            int rows = recodrFromMapper.insert(recodrFrom);
            if (rows < 1) {
                return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
            }
            return Result.ok("签到成功");
        }
        return Result.build("签到位置不符合", ResultCodeEnum.SIGN_IN_ERROE);
    }

    /**
     * 用户签出
     * 1 验证 token -> user
     * 2 验证签到地点
     *
     * @param signOut
     * @param token
     * @return
     */
    @Transactional
    public Result signOut(SignOut signOut, String token) {
        //解析token
        int userId = jwtHelper.getUserId(token).intValue();
        signOut.setUser(userId);
        signOut.setOutTime(new Date());

        //检查经度纬度
        if (signOut.getPlace() == null) {
            signOut.setPlace("117.5555,128.888");
        }
        //将经度维度从字符串转为BigDecimal
        String[] split = signOut.getPlace().split(",");
        BigDecimal bLongitude = new BigDecimal(split[0]);
        BigDecimal bLatitude = new BigDecimal(split[1]);
        System.out.println("bLatitude = " + bLatitude);
        System.out.println("bLongitude = " + bLongitude);

        if (SignMessage.checkPlace(bLongitude, bLatitude)) {
            signOut.setOutTime(new Date());
            LambdaQueryWrapper<RecodrFrom> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RecodrFrom::getUser, signOut.getUser())
                    .eq(RecodrFrom::getState, 1);
            List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(wrapper);
            Iterator<RecodrFrom> iterator = recodrFroms.iterator();
            while (iterator.hasNext()) {
                RecodrFrom recodrFrom = iterator.next();
                if (signOut.getOutTime().getTime() - recodrFrom.getInTime().getTime() <= (SignMessage.getSignTimeWatch())) {
                    return Result.build("签出失败，未到规定时间", ResultCodeEnum.SIGN_OUT_ERROR);
                }
                recodrFrom.setCarryKey(signOut.getKey());
                recodrFrom.setState(0); //State =0 已迁出
                int rows = recodrFromMapper.updateById(recodrFrom);
                if (rows < 1) {
                    return Result.build(null, ResultCodeEnum.SIGN_IN_ERROE);
                }
            }
            //签出携带钥匙，增加一条携带钥匙表记录
            if (signOut.getKey().equals(SignMessage.getCarryKey()[0])) { //[0] 是 [1] 否
                LambdaQueryWrapper<CarryKey> carryKeyLambdaQueryWrapper = new LambdaQueryWrapper<>();
                carryKeyLambdaQueryWrapper.eq(CarryKey::getCUser, userId);
                int count = carryKeyMapper.selectCount(carryKeyLambdaQueryWrapper).intValue();
                //判断是否有钥匙，
                if (count > 0) {
                    //有钥匙 不在新增钥匙
                    return Result.ok("签出成功");
                }
                //没钥匙添加钥匙
                CarryKey addCarryKey = new CarryKey();
                addCarryKey.setCUser(userId);
                carryKeyMapper.insert(addCarryKey);
            }
            return Result.ok("签出成功");
        }
        return Result.build("签出位置不符合", ResultCodeEnum.SIGN_OUT_ERROR);
    }

    /**
     * 1根据recoderFrom表中state为1（未签出的）获取签到记录中Userid
     * 2 state = 0 -> userId->userMessage
     *
     * @return
     */
    @Override
    public Result queryRoomOnlineUsers() {
        LambdaQueryWrapper<RecodrFrom> recodrFromLambdaQueryWrapper = new LambdaQueryWrapper<>();
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getState, 1);

        List<RecodrFrom> recodrFroms = recodrFromMapper.selectList(recodrFromLambdaQueryWrapper);
        if (recodrFroms == null) {
            return Result.ok("工作室目前没人哦~");
        }


        List<SignUser> onlineUsers = room.getOnlineUser();
        onlineUsers.clear();
        //封装网站在线人员，
        Iterator<RecodrFrom> recodrFromIterator = recodrFroms.iterator();
        while (recodrFromIterator.hasNext()) {
            RecodrFrom recodrFrom = recodrFromIterator.next();
            Integer userId = recodrFrom.getUser();
            User user = userMapper.selectById(userId);
            SignUser signUser = SignUser.getSignUser(user, recodrFrom);
            onlineUsers.add(signUser);
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
        recodrFromLambdaQueryWrapper.eq(RecodrFrom::getCarryKey, SignMessage.getCarryKey());
        List<User> getKeyUsers = room.getGetKey();
        getKeyUsers.clear();
        //封装携带key用户List
        List<CarryKey> carryKeys = carryKeyMapper.selectList(null);
        Iterator<CarryKey> iterator = carryKeys.iterator();
        while (iterator.hasNext()) {
            CarryKey carryKey = iterator.next();
            Integer userId = carryKey.getCUser();
            User user = userMapper.selectById(userId);
            User carryUser = new User();
            carryUser.setName(user.getName());
            carryUser.setImage(user.getImage());
            carryUser.setPhone(user.getPhone());
            getKeyUsers.add(carryUser);
        }


        return Result.ok(getKeyUsers);
    }

    /**
     * 根据用户姓名查询出用户id然后修改签到表中用户的携带钥匙情况
     *
     * @param userName
     * @return
     */
    @Override
    public Result updateByUserName(String token, String userName) {
        //判断token是否过期
        if (jwtHelper.isExpiration(token)) {
            //过期，需要重新登录
            return Result.build("请重新登录", ResultCodeEnum.NOTLOGIN);
        }
        //登陆用户id，
        int loginUserId = jwtHelper.getUserId(token).intValue();

        //根据userName 查询 userId
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getName, userName);
        User tmpUser = userMapper.selectOne(userLambdaQueryWrapper);
        Integer uid = tmpUser.getId(); //钥匙接收在id

        //修改钥匙携带者表 carrykey
        CarryKey carryKey = new CarryKey();
        carryKey.setCUser(uid);
        LambdaQueryWrapper<CarryKey> carryKeyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        carryKeyLambdaQueryWrapper.eq(CarryKey::getCUser, loginUserId);
        int update = carryKeyMapper.update(carryKey, carryKeyLambdaQueryWrapper);
        System.out.println("update = " + update);
        return Result.ok("转交完成");
    }

    @Override
    public Result findOnlineUserPage(PortalVo portalVo) {


        IPage<Map> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        recodrFromMapper.selectOnlineUserPageMap(page,portalVo);
        //封装网站在线人员，

        HashMap<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        HashMap<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        return Result.ok(pageInfoMap);
    }
}




