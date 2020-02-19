package com.imooc.wechatshop.exception;

import com.imooc.wechatshop.enums.ResultExceptionEnum;
import lombok.Getter;

@Getter
public class WechatShopException extends RuntimeException {

    private Integer code;

    public WechatShopException(ResultExceptionEnum resultExceptionEnum) {

        super(resultExceptionEnum.getMessage());
        this.code = resultExceptionEnum.getCode();
    }

    public WechatShopException(Integer code, String message) {

        super(message);
        this.code = code;
    }
}
