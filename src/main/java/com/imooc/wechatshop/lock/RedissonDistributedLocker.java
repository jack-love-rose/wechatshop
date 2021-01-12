package com.imooc.wechatshop.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ansongsong
 * @Description: Redisson redis 分布式锁
 * @date 2020/11/6 16:52
 */
@Slf4j
public class RedissonDistributedLocker implements DistributedLocker {

    private RedissonClient redissonClient;

    /**
     * 加锁
     * @param lockKey 锁名称
     * @return
     */
    @Override
    public Optional<RLock> lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return Optional.ofNullable(lock);
    }
    /**
     * 加锁，以秒为单位
     * @param lockKey 锁名称
     * @param timeout 超时时间
     * @return
     */
    @Override
    public Optional<RLock> lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return Optional.ofNullable(lock);
    }

    /**
     * 加锁
     * @param lockKey 锁名称
     * @param unit 时间单位
     * @param timeout 超时时间
     * @return
     */
    @Override
    public Optional<RLock> lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return Optional.ofNullable(lock);
    }

    /**
     * 加锁
     * @param lockKey
     * @param unit  eg: TimeUnit.SECONDS
     * @param waitTime eg: 100 : 最多等待100秒   TimeUnit.SECONDS
     * @param leaseTime eg: 10 : 10秒自动解锁    TimeUnit.SECONDS
     * @return
     */
    @Override
    public Optional<Boolean> tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isSuccess = lock.tryLock(waitTime, leaseTime, unit);
            return Optional.ofNullable(isSuccess);
        } catch (InterruptedException e) {
            log.error("RedissonDistributedLocker.tryLock lockKey:{},unit:{},waitTime:{},leaseTime:{}",lockKey,unit,waitTime,leaseTime);
            return Optional.ofNullable(false);
        }
    }

    /**
     *  释放锁
     * @param lockKey 锁名称
     */
    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }


    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
