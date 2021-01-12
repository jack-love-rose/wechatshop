package com.imooc.wechatshop.lock;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ansongsong
 * @Description: 分布式锁
 * @date 2020/11/6 16:37
 */
public interface DistributedLocker {
    /**
     * 加锁
     * @param lockKey 锁名称
     * @return
     */
    <T> Optional<T> lock(String lockKey);

    /**
     * 加锁，以秒为单位
     * @param lockKey 锁名称
     * @param timeout 超时时间
     * @param <T>
     * @return
     */
    <T> Optional<T> lock(String lockKey, int timeout);

    /**
     * 加锁
     * @param lockKey 锁名称
     * @param unit 时间单位
     * @param timeout 超时时间
     * @param <T>
     * @return
     */
    <T> Optional<T> lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 加锁
     * @param lockKey
     * @param unit  eg: TimeUnit.SECONDS
     * @param waitTime eg: 100 : 最多等待100秒   TimeUnit.SECONDS
     * @param leaseTime eg: 10 : 10秒自动解锁    TimeUnit.SECONDS
     * @return
     */
    Optional<Boolean> tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     *  释放锁
     * @param lockKey 锁名称
     */
    void unlock(String lockKey);

}
