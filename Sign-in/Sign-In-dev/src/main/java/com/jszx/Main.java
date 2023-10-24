package com.jszx;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 刘林
 * @version 1.0
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.jszx.mapper")
public class Main {

        public static void main(String[] args) {
            SpringApplication.run(Main.class,args);
            log.info("server started");
        }

        //配置mybatis-plus插件
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); //分页
            interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());  //乐观锁
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());  //防全局修改和删除
            return interceptor;
        }
}