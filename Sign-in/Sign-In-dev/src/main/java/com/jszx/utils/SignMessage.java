package com.jszx.utils;

/**
 * @author 刘林
 * @version 1.0
 */

public final class SignMessage {
    //签出时常设置
    private static Long SignTimeWatch = 80*60*1000L;
    public static Long getSignTimeWatch(){
        return SignTimeWatch;
    }

    //是否携带钥匙
    private static String carryKey = "是";
    public static String getCarryKey(){
        return carryKey;
    }

    //钥匙是否已转交
    private static String noTransmitKey = "未转交";
    public static String getNoTransmitKey() {
        return noTransmitKey;
    }
}
