package com.jszx.test;

import java.math.BigDecimal;

/**
 * @author 刘林
 * @version 1.0
 */
public class test {
    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(Double.toString( 10.09));
        BigDecimal b2 = new BigDecimal(Double.toString(10.1));
        System.out.println(Math.abs(b1.subtract(b2).doubleValue()));
    }
}
