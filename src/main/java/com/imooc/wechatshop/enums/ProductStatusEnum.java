package com.imooc.wechatshop.enums;

import lombok.Getter;

/**
 * 商品状态枚举类
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{

    UP(0,"商品在架"),
    DOWN(1,"商品下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
