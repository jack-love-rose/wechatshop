package com.imooc.wechatshop.converter;


import com.imooc.wechatshop.dto.OrderDTO;
import com.imooc.wechatshop.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDtoConverter {

    public static OrderDTO converter(OrderMaster orderMaster){

        OrderDTO orderDto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasters){

        return orderMasters.stream().map(e->converter(e)).collect(Collectors.toList());
    }
}
