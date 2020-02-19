package com.imooc.wechatshop.service;


import com.imooc.wechatshop.dto.OrderDTO;

/**
 * 微信模板推送消息
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
