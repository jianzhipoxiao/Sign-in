package com.jszx.utils;

import lombok.Data;

/**
 * @author 刘林
 * @version 1.0
 */

public class SignTime {
    private static Long SignTimeWatch = 80*60*1000L;
    public static Long getSignTimeWatch(){
        return SignTimeWatch;
    }
}
