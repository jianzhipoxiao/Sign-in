package com.jszx.pojo.vo;

import com.jszx.pojo.RecodrFrom;
import com.jszx.pojo.User;
import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 * @caeateDate 2023-11-13
 * @description 用于存储签到表信息
 */
@Data
public class SignUser {
    private String name;
    private String phone;
//    private String image;
    private String type;


    public static SignUser getSignUser(User user, RecodrFrom recodrFrom){
        SignUser signUser = new SignUser();
        signUser.setName(user.getName());
        signUser.setPhone(user.getPhone());
        signUser.setType(recodrFrom.getType());
        return signUser;
    }
}
