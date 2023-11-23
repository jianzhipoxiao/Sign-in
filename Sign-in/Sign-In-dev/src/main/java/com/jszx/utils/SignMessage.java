package com.jszx.utils;

import java.math.BigDecimal;

/**
 * @author 刘林
 * @version 1.0
 */

public final class SignMessage {
    //签出时常设置
//    private static Long SignTimeWatch = 80*60*1000L;
    private final static Long SignTimeWatch = 80*60L;
    public final static Long getSignTimeWatch(){
        return SignTimeWatch;
    }

    public final static String[] carryKey =new String[]{
      "是","否"
    };
    //工作室经度
    public static BigDecimal placeLongitude = new BigDecimal(39.188915);
    //工作室维度
    public static BigDecimal placeLatitude  = new BigDecimal(117.134325);

    /**
     * 判断签到位置是否是合适的
     * 判断依据经度纬度的差值
     * @param
     * @return
     */

    public static boolean checkPlace(BigDecimal Longitude ,BigDecimal bLatitude ){


        double absLongitude = Math.abs(Longitude.subtract(placeLongitude).doubleValue());
        double absLatitude = Math.abs(bLatitude.subtract(placeLatitude).doubleValue());
        System.out.println("维度差 absLatitude = " + absLatitude);
        System.out.println("经度差 absLongitude = " + absLongitude);
        if (absLatitude<0.0003 && absLongitude<0.0004){
            return true;
        }
        return false;
    }

    public static String[] getCarryKey(){
        return carryKey;
    }

    //钥匙是否已转交
    private static String noTransmitKey = "未转交";
    public static String getNoTransmitKey() {
        return noTransmitKey;
    }
}
