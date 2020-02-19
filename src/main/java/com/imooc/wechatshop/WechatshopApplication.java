package com.imooc.wechatshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.imooc.wechatshop.mapper")
@EnableCaching //开启redis注解缓存
public class WechatshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatshopApplication.class, args);
    }

}
