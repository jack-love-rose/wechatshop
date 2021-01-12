package com.imooc.wechatshop.redislock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 首先方法上加 synchronized关键字是能解决秒杀活动时超卖现象的，但是并发情况下性能堪忧
 * redis实现的分布式锁
 * 这里其实是使用了redis的两个命令:
 * 1.setnx 当key存在时什么也不做，当key不存在时设置值为你设置的value  ----> 在java中是setIfAbsent()方法，如果可以设置的话返回true否则返回false
 * 2.getset 先get 对应key的value 后set 新的value到这个key上       ----> 在java中是getAndSet()方法
 */
@Slf4j
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁 true就是拿到锁了 false就是没拿到锁
     * @param key
     * @param value 当前时间+超时时间(时间戳格式的)
     * @return
     */
    public boolean lock(String key, String value) {
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (StringUtils.isNotBlank(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (StringUtils.isNotBlank(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}
