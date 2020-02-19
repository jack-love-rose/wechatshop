package com.imooc.wechatshop.controller;

import com.imooc.wechatshop.dto.OrderDTO;
import com.imooc.wechatshop.enums.ResultExceptionEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import com.imooc.wechatshop.service.OrderService;
import com.imooc.wechatshop.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


/**
 * 支付
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;



    /**
     * 发起支付
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        ModelAndView modelAndView = new ModelAndView();

        //支付前先查询是否存在这个订单
        OrderDTO orderDto = orderService.findOne(orderId);
        if(orderDto == null){
            throw new WechatShopException(ResultExceptionEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderDto);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        modelAndView.addObject(map);
        modelAndView.setViewName("pay/create");
        return modelAndView;
    }

    /**
     * 微信支付结果异步通知
     */

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyBody){
        //TODO 校验验证签名 SDK已经做了
        //TODO 校验支付的状态 SDK已经做了
        //TODO 校验支付的金额
        //TODO 校验支付人（如果不允许代付的话 应该下单人和支付人是同一人，这个不强制校验）

        payService.notify(notifyBody);

        //返回给微信处理结果，不让他再继续异步通知咱们已经支付的消息
        return new ModelAndView("pay/success");
    }
}
