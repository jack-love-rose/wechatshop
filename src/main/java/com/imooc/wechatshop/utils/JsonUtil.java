package com.imooc.wechatshop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 使用gson 格式化对象为json格式 GsonBuilder可以自定义json格式化的规则
 */
public class JsonUtil {

    public static  String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
