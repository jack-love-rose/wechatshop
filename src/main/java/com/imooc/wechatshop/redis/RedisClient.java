package com.imooc.wechatshop.redis;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis客户端
 * @author huyunxiu@fenxiangbuy.com
 * @date 2020/06/06
 */
public class RedisClient {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisClient(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * Delete given {@code keys}.
     *
     * @param key must not be {@literal null}.
     * @return The key that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * Delete given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * Count how many of the given {@code keys} exist. Providing the very same {@code key} more than once also counts
     * multiple times.
     *
     * @param keys must not be {@literal null}.
     * @return the number of keys existing among the ones specified as arguments. {@literal null} when used in pipeline /
     *         transaction.
     * @since 2.1
     */
    public Long countExistingKeys(Collection<String> keys) {
        return redisTemplate.countExistingKeys(keys);
    }

    /**
     * Get the time to live for {@code key} in seconds.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/ttl">Redis Documentation: TTL</a>
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * Get the precise time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key must not be {@literal null}.
     * @param timeUnit must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.8
     * @see <a href="https://redis.io/commands/pttl">Redis Documentation: PTTL</a>
     */
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * Set time to live for given {@code key} in seconds.
     *
     * @param key must not be {@literal null}.
     * @param timeout
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     */
    public Boolean expire(String key, final long timeout, final TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal UNIX} timestamp in milliseconds.
     *
     * @param key must not be {@literal null}.
     * @param date
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/pexpireat">Redis Documentation: PEXPIREAT</a>
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * Determine if given {@code key} exists.
     *
     * @param key must not be {@literal null}.
     * @return {@literal true} if key exists. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * Remove the expiration from given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/persist">Redis Documentation: PERSIST</a>
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * Overwrite parts of {@code key} starting at the specified {@code offset} with given {@code value}.
     *
     * @param key must not be {@literal null}.
     * @param object
     * @param offset
     * @see <a href="https://redis.io/commands/setrange">Redis Documentation: SETRANGE</a>
     */
    public void set(String key, Object object, long offset) {
        String value = JsonUtil.toJsonStr(object);
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     * @since 2.1
     */
    public void set(String key, Object object, Duration timeout) {
        String value = JsonUtil.toJsonStr(object);
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * Set the {@code value} and expiration {@code timeout} for {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param timeUnit must not be {@literal null}.
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     */
    public void set(String key, Object object, long timeout, TimeUnit timeUnit) {
        String value = JsonUtil.toJsonStr(object);
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param timeUnit must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     */
    public Boolean setIfAbsent(String key, Object object, long timeout, TimeUnit timeUnit) {
        String value = JsonUtil.toJsonStr(object);
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is absent.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    public Boolean setIfAbsent(String key, Object object, Duration timeout) {
        String value = JsonUtil.toJsonStr(object);
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout the key expiration timeout.
     * @param timeUnit must not be {@literal null}.
     * @return command result indicating if the key has been set.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    public Boolean setIfPresent(String key, Object object, long timeout, TimeUnit timeUnit) {
        String value = JsonUtil.toJsonStr(object);
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout, timeUnit);
    }

    /**
     * Set {@code key} to hold the string {@code value} and expiration {@code timeout} if {@code key} is present.
     *
     * @param key must not be {@literal null}.
     * @param object must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if either {@code key}, {@code value} or {@code timeout} is not present.
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @since 2.1
     */
    public Boolean setIfPresent(String key, Object object, Duration timeout) {
        String value = JsonUtil.toJsonStr(object);
        return redisTemplate.opsForValue().setIfPresent(key, value, timeout);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple}.
     *
     * @param map must not be {@literal null}.
     * @see <a href="https://redis.io/commands/mset">Redis Documentation: MSET</a>
     */
    public void multiSet(Map<String, ?> map) {
        Map<String, String> resultMap = MapUtil.newHashMap(map.size());
        for (Map.Entry<String, ?> entry: map.entrySet()) {
            resultMap.put(entry.getKey(), JsonUtil.toJsonStr(entry.getValue()));
        }
        redisTemplate.opsForValue().multiSet(resultMap);
    }

    /**
     * Set multiple keys to multiple values using key-value pairs provided in {@code tuple} only if the provided key does
     * not exist.
     *
     * @param map must not be {@literal null}.
     * @param {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/msetnx">Redis Documentation: MSETNX</a>
     */
    public void multiSetIfAbsent(Map<String, ?> map) {
        Map<String, String> resultMap = MapUtil.newHashMap(map.size());
        for (Map.Entry<String, ?> entry: map.entrySet()) {
            resultMap.put(entry.getKey(), JsonUtil.toJsonStr(entry.getKey()));
        }
        redisTemplate.opsForValue().multiSetIfAbsent(resultMap);
    }

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    public <T> Optional<T> getAndSet(String key, Object value, Class<T> beanClass) {
        return JsonUtil.toBean(redisTemplate.opsForValue().getAndSet(key, JsonUtil.toJsonStr(value)), beanClass);
    }

    /**
     * Set {@code value} of {@code key} and return its old value.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
     */
    public <T> Optional<T> getAndSet(String key, Object value, TypeReference<T> typeReference) {
        return JsonUtil.toBean(redisTemplate.opsForValue().getAndSet(key, JsonUtil.toJsonStr(value)), typeReference);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
     */
    public <T> Optional<T> get(Object key, Class<T> beanClass) {
        return JsonUtil.toBean(redisTemplate.opsForValue().get(key), beanClass);
    }

    /**
     * Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
     */
    public <T> Optional<T> get(Object key, TypeReference<T> typeReference) {
        return JsonUtil.toBean(redisTemplate.opsForValue().get(key), typeReference);
    }

    /**
     * Get multiple {@code keys}. Values are returned in the order of the requested keys.
     *
     * @param keys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     */
    public <T> List<T> multiGet(Collection<String> keys, Class<T> beanClass) {
        List<T> result = new ArrayList<>();

        List<String> jsonStrings = redisTemplate.opsForValue().multiGet(keys);
        Objects.requireNonNull(jsonStrings);
        for (String json: jsonStrings) {
            Optional<T> bean = JsonUtil.toBean(json, beanClass);
            bean.ifPresent(result::add);
        }

        return result;
    }

    /**
     * Increment an integer value stored as string value under {@code key} by one.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     * @see <a href="https://redis.io/commands/incr">Redis Documentation: INCR</a>
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Increment an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrby">Redis Documentation: INCRBY</a>
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Increment a floating point number value stored as string value under {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/incrbyfloat">Redis Documentation: INCRBYFLOAT</a>
     */
    public Double increment(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Decrement an integer value stored as string value under {@code key} by one.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     * @see <a href="https://redis.io/commands/decr">Redis Documentation: DECR</a>
     */
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * Decrement an integer value stored as string value under {@code key} by {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     * @see <a href="https://redis.io/commands/decrby">Redis Documentation: DECRBY</a>
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * Get the length of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/strlen">Redis Documentation: STRLEN</a>
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * Sets the bit at {@code offset} in value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param offset
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="https://redis.io/commands/setbit">Redis Documentation: SETBIT</a>
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * Get the bit value at {@code offset} of value at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param offset
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.5
     * @see <a href="https://redis.io/commands/setbit">Redis Documentation: GETBIT</a>
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * Get / Manipulate specific integer fields of varying bit widths and arbitrary non (necessary) aligned offset stored
     * at a given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param subCommands must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     * @see <a href="https://redis.io/commands/bitfield">Redis Documentation: BITFIELD</a>
     */
    public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
        return redisTemplate.opsForValue().bitField(key, subCommands);
    }

    /**
     * Delete given hash {@code hashKeys}.
     *
     * @param key must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * Determine if given hash {@code hashKey} exists.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Boolean hHasKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Get value for given {@code hashKey} from hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when key or hashKey does not exist or used in pipeline / transaction.
     */
    public <T> Optional<T> hGet(String key, Object hashKey, Class<T> beanClass) {
        Object o = redisTemplate.opsForHash().get(key, hashKey);
        if (o == null) {
            return Optional.empty();
        }
        return JsonUtil.toBean(o.toString(), beanClass);
    }

    /**
     * Get values for given {@code hashKeys} from hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param hashKeys must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public List<Object> hMultiGet(String key, Collection<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Long hIncrement(String key, Object hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Increment {@code value} of a hash {@code hashKey} by the given {@code delta}.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param delta
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Double hIncrement(String key, Object hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Get key set (fields) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * Returns the length of the value associated with {@code hashKey}. If either the {@code key} or the {@code hashKey}
     * do not exist, {@code 0} is returned.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 2.1
     */
    public Long hLengthOfValue(String key, Object hashKey) {
        return redisTemplate.opsForHash().lengthOfValue(key, hashKey);
    }

    /**
     * Get size of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * Set multiple hash fields to multiple values using data provided in {@code m}.
     *
     * @param key must not be {@literal null}.
     * @param m must not be {@literal null}.
     */
    public void hPutAll(String key, Map<?, ?> m) {
        redisTemplate.opsForHash().putAll(key, m);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey}.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value
     */
    public void hPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * Set the {@code value} of a hash {@code hashKey} only if {@code hashKey} does not exist.
     *
     * @param key must not be {@literal null}.
     * @param hashKey must not be {@literal null}.
     * @param value
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Boolean hPutIfAbsent(String key, Object hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * Get entry set (values) of hash at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * Get entire hash stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Use a {@link Cursor} to iterate over entries in hash at {@code key}. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leak.
     *
     * @param key must not be {@literal null}.
     * @param options
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.4
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /**
     * Get all elements of set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/smembers">Redis Documentation: SMEMBERS</a>
     */
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Add given {@code values} to set at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param object
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sadd">Redis Documentation: SADD</a>
     */
    public Long sAdd(String key, Object object) {
        String value = JsonUtil.toJsonStr(object);
        return redisTemplate.opsForSet().add(key,value);
    }

    /**
     * Get elements in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * Get set of {@link RedisZSetCommands.Tuple}s in range from {@code start} to {@code end} from sorted set ordered from high to low.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * Get elements where score is between {@code min} and {@code max} from sorted set ordered from high to low.
     *
     * @param key must not be {@literal null}.
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation: ZREVRANGE</a>
     */
    public Set<String> zReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * Get set of {@link RedisZSetCommands.Tuple} where score is between {@code min} and {@code max} from sorted set ordered from high to
     * low.
     *
     * @param key must not be {@literal null}.
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * Get set of {@link RedisZSetCommands.Tuple} in range from {@code start} to {@code end} where score is between {@code min} and
     * {@code max} from sorted set ordered high -> low.
     *
     * @param key must not be {@literal null}.
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrangebyscore">Redis Documentation: ZREVRANGEBYSCORE</a>
     */
    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Count number of elements within sorted set with scores between {@code min} and {@code max}.
     *
     * @param key must not be {@literal null}.
     * @param min
     * @param max
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     */
    @Nullable
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * Returns the number of elements of the sorted set stored with given {@code key}.
     *
     * @see #zCard(String)
     * @param key
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    @Nullable
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * Get the size of sorted set with {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.3
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     */
    @Nullable
    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * Get the score of element with {@code value} from sorted set with key {@code key}.
     *
     * @param key must not be {@literal null}.
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zscore">Redis Documentation: ZSCORE</a>
     */
    @Nullable
    public Double zScore(String key, Object o) {
        return redisTemplate.opsForZSet().score(key, o);
    }

    /**
     * Add {@code value} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param key must not be {@literal null}.
     * @param score the score.
     * @param value the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    @Nullable
    public Boolean add(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Add {@code tuples} to a sorted set at {@code key}, or update its {@code score} if it already exists.
     *
     * @param key must not be {@literal null}.
     * @param tuples must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     */
    @Nullable
    public Long add(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * Remove {@code values} from sorted set. Return number of removed elements.
     *
     * @param key must not be {@literal null}.
     * @param values must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrem">Redis Documentation: ZREM</a>
     */
    @Nullable
    public Long remove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * Increment the score of element with {@code value} in sorted set by {@code increment}.
     *
     * @param key must not be {@literal null}.
     * @param delta
     * @param value the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zincrby">Redis Documentation: ZINCRBY</a>
     */
    @Nullable
    public Double incrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * Determine the index of element with {@code value} in a sorted set.
     *
     * @param key must not be {@literal null}.
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrank">Redis Documentation: ZRANK</a>
     */
    @Nullable
    public Long rank(String key, Object o) {
        return redisTemplate.opsForZSet().rank(key, o);
    }

    /**
     * Determine the index of element with {@code value} in a sorted set when scored high to low.
     *
     * @param key must not be {@literal null}.
     * @param o the value.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrevrank">Redis Documentation: ZREVRANK</a>
     */
    @Nullable
    public Long reverseRank(String key, Object o) {
        return redisTemplate.opsForZSet().reverseRank(key, o);
    }

    /**
     * Get elements between {@code start} and {@code end} from sorted set.
     *
     * @param key must not be {@literal null}.
     * @param start
     * @param end
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     */
    @Nullable
    public Set<String> range(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }
}
