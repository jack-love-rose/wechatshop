package com.imooc.wechatshop.utils.otherUtils;

import java.util.Random;

/**
 * 用于生成
 * 订单id
 * 一些不是自增的主键id
 */
public class RandomUtil {

    /**
     * 生成唯一主键
     * 格式：时间+随机数
     * synchronized关键字给方法上锁，虽然时间精确到了毫秒但在多线程情况下会有重复
     * 上了锁同一时间只有一个对象拿到这个方法
     *
     */
    public static synchronized String genUniqueKey(){
       long currentTime =  System.currentTimeMillis();
       Random random = new Random();
       Integer number = random.nextInt(900000)+100000;

       return currentTime + String.valueOf(number);
    }
}
