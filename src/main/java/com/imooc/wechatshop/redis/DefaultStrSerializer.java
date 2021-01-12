package com.imooc.wechatshop.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 重写Redis默认序列化
 *
 * @author huyunxiu@fenxiangbuy.com
 * @date 2020/06/06
 */
public class DefaultStrSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public DefaultStrSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public DefaultStrSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return o == null ? null : String.valueOf(o).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, charset);

    }
}
