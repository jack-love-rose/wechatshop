package com.imooc.wechatshop.demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 褚佳鑫
 * @description 随机数生成
 * @date 2020/12/22 11:04
 **/

@RestController
public class ThreadLocalRandomDemo {

    public static void demo(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(50,100));
        }
    }

    public static void main(String[] args) {
        demo();
    }
}
