package com.imooc.wechatshop.service;

import com.imooc.wechatshop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单
 */
public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderDto);
    //取消订单
    OrderDTO cancel(OrderDTO orderDto);
    //查询单个订单
    OrderDTO findOne(String id);
    //查询多个订单
    Page<OrderDTO> findList(String buyerId, Pageable pageable);
    //完结订单
    OrderDTO finish(OrderDTO orderDto);
    //修改支付订单
    OrderDTO paid(OrderDTO orderDto);

    Page<OrderDTO> findAll(Pageable pageable);
}
