package com.imooc.wechatshop.controller;

import com.imooc.wechatshop.vo.ResultVO;
import com.imooc.wechatshop.converter.OrderForm2OrderDTOConverter;
import com.imooc.wechatshop.dto.OrderDTO;
import com.imooc.wechatshop.enums.ResultExceptionEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import com.imooc.wechatshop.form.OrderForm;
import com.imooc.wechatshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("[创建订单] 表单验证 orderForm={}",orderForm);
            throw new WechatShopException(ResultExceptionEnum.PARAMS_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDto = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空");
            throw new WechatShopException(ResultExceptionEnum.CART_EMPTY);
        }
        OrderDTO result = orderService.create(orderDto);
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderId",result.getOrderId());
        return ResultVO.success(map);
    }
    //查询订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(value = "openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "1") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("[查询订单列表openid为空]");
            throw new WechatShopException(ResultExceptionEnum.CART_EMPTY);
        }

        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> orderDtoPage = orderService.findList(openid,pageRequest);

        return ResultVO.success(orderDtoPage.getContent());
    }

    //查询某个订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam(value = "openid") String openid,
                                     @RequestParam(value = "orderId") String orderId){
        OrderDTO orderDto = orderService.findOne(orderId);
        if(null == orderDto){
            return null;
        }
        if(!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单] 订单的openId不一致 openid={},orderDto={}",orderId,orderDto);
            throw new WechatShopException(ResultExceptionEnum.ORDER_OWNER_ERROR);
        }
        return ResultVO.success(orderDto);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVO cancel(@RequestParam(value = "openid") String openid,
                                           @RequestParam(value = "orderId") String orderId){

        OrderDTO orderDto = orderService.findOne(orderId);
        if(null == orderDto){
            log.error("[取消订单] 查不到该订单 orderId={}",orderId);
            throw new WechatShopException(ResultExceptionEnum.ORDER_NOT_EXIST);
        }
        if(!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[取消订单] 订单的openId不一致 openid={},orderDto={}",orderId,orderDto);
            throw new WechatShopException(ResultExceptionEnum.ORDER_OWNER_ERROR);
        }
        orderService.cancel(orderDto);
        return ResultVO.success();
    }
}