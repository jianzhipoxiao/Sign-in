package com.jszx.test;

import com.jszx.utils.IpMacUtil;

/**
 * @author 刘林
 * @version 1.0
 */
public class test {
    public static void main(String[] args) {
        String mac = IpMacUtil.getMacByIP("127.0.0.1");
        System.out.println("mac = " + mac);
    }
}
