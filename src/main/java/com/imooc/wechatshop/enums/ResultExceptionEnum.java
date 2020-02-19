package com.imooc.wechatshop.enums;

import lombok.Getter;

@Getter
public enum ResultExceptionEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"商品明细不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDERDETAIL_IS_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付错误"),
    PARAMS_ERROR(18,"参数不正确"),
    CART_EMPTY(19,"购物车为空"),
    ORDER_OWNER_ERROR(20,"订单所有者不一致"),
    PRODUCT_STATUS_ERROR(21,"商品状态不正确")
    ;



    private Integer code;

    private String message;

    ResultExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
