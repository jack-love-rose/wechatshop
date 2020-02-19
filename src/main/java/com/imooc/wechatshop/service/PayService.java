package com.imooc.wechatshop.service;

import com.imooc.wechatshop.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {

    PayResponse create(OrderDTO orderDto);

    PayResponse notify(String notifyBody);

    RefundResponse refund(OrderDTO orderDto);
}
