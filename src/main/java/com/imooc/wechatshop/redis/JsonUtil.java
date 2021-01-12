package com.imooc.wechatshop.redis;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;

import java.util.Optional;

/**
 * Redis客户端
 * @author huyunxiu@fenxiangbuy.com
 * @date 2020/06/06
 */
public class JsonUtil {

    public static String toJsonStr(Object obj) {
        if (obj == null) {
            return null;
        }

        switch (obj.getClass().getName()) {
            case "java.lang.Integer":
            case "java.lang.String":
            case "java.lang.Boolean":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.lang.Long":
                return String.valueOf(obj);
            default:
                return JSONUtil.toJsonStr(obj);
        }
    }

    public static <T> Optional<T> toBean(String jsonString, Class<T> beanClass) {
        if (jsonString == null) {
            return Optional.empty();
        }

        switch (beanClass.getName()) {
            case "java.lang.Integer":
                return Optional.of((T) Integer.valueOf(jsonString));
            case "java.lang.String":
                return Optional.of((T) jsonString);
            case "java.lang.Boolean":
                return Optional.of((T) Boolean.valueOf(jsonString));
            case "java.lang.Float":
                return Optional.of((T) Float.valueOf(jsonString));
            case "java.lang.Double":
                return Optional.of((T) Double.valueOf(jsonString));
            case "java.lang.Long":
                return Optional.of((T) Long.valueOf(jsonString));
            default:
                return Optional.ofNullable(JSONUtil.toBean(jsonString, beanClass));
        }
    }

    public static <T> Optional<T> toBean(String jsonString, TypeReference<T> typeReference) {
        if (jsonString == null) {
            return Optional.empty();
        }
        return Optional.of(JSONUtil.toBean(jsonString, typeReference.getType(), false));
    }
}
