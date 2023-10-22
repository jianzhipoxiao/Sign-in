package com.jszx;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 刘林
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.jszx.mapper")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}