package com.imooc.wechatshop.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 使用gson 格式化对象为json格式 GsonBuilder可以自定义json格式化的规则
 */
public class JsonUtil {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 设置序列化参数
        // 解析单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许使用非双引号属性名字
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 支持000001赋值给json某变量
        mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 反序列化时忽略未知属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置序列号日期格式
        mapper.getSerializationConfig().with(dateFormat);
        // 设置反序列号日期格式
        mapper.getDeserializationConfig().with(dateFormat);
    }

    public static <T> T json2Bean(String value, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(value) || "{}".equals(value)){
                return null;
            }
            return (T) mapper.readValue(value, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object json2Bean(String value, Class clazz1, Class... clazz2) {
        try {
            if (StringUtils.isBlank(value) || "{}".equals(value)){
                return null;
            }
            JavaType javaType = null;
            if (List.class.isAssignableFrom(clazz1)) {
                javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz2[0]);
            } else if (Map.class.isAssignableFrom(clazz1)) {
                javaType = mapper.getTypeFactory().constructMapType(clazz1, clazz2[0], clazz2[1]);
            }
            return mapper.readValue(value, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map json2Map(String value) {
        try {
            if (StringUtils.isBlank(value) || "{}".equals(value)){
                return null;
            }
            return mapper.readValue(value, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>List bean2List(Object obj, Class<T> clazz) {
        try {
            if (obj == null){
                return null;
            }
            TypeFactory tf = mapper.getTypeFactory();
            JavaType classType = tf.constructParametricType(List.class, clazz);
            if (obj instanceof String) {
                return mapper.readValue((String) obj, classType);
            } else {
                return mapper.readValue(bean2Json(obj), classType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String map2Json(Map map) {
        try {
            if (map == null || map.isEmpty()){
                return "{}";
            }
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String list2Json(List list) {
        try {
            if (list == null || list.isEmpty()){
                return "[]";
            }
            return mapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List json2List(String value) {
        try {
            if (StringUtils.isBlank(value) || "{}".equals(value) || "[]".equals(value)) {
                return null;
            }
            return mapper.readValue(value, List.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String bean2Json(Object obj) {
        try {
            if (obj == null){
                return "{}";
            }
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T>T bean2Bean(Object obj, Class<T> clazz) {
        if (obj instanceof  String) {
            return json2Bean((String)obj, clazz);
        }
        return json2Bean(bean2Json(obj), clazz);
    }

    public static  String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
