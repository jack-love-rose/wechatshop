package com.imooc.wechatshop.utils;


import com.imooc.wechatshop.enums.CodeEnum;

public class EnumUtil{

    public static <T extends CodeEnum> T getValuesByCode(Integer code, Class<T> enumClass){

        for (T each:enumClass.getEnumConstants()) {
            if(each.getCode().equals(code)){
                return each;
            }
        }
        return null;
    }
}
