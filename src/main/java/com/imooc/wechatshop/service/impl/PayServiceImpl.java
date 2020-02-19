package com.imooc.wechatshop.service.impl;

import com.imooc.wechatshop.dto.OrderDTO;
import com.imooc.wechatshop.enums.ResultExceptionEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import com.imooc.wechatshop.service.OrderService;
import com.imooc.wechatshop.service.PayService;
import com.imooc.wechatshop.utils.JsonUtil;
import com.imooc.wechatshop.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;

    /**
     * 发起支付
     * @param orderDto
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDto) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        payRequest.setOrderName(ORDER_NAME);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[返回微信预支付结果]，payResponse={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    /**
     * 微信异步通知
     * @param notifyBody
     * @return
     */
    @Override
    public PayResponse notify(String notifyBody) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyBody);
        log.info("[返回微信支付结果一步通知]，payResponse={}", JsonUtil.toJson(payResponse));

        OrderDTO orderDto = orderService.findOne(payResponse.getOrderId());

        if(null == orderDto){
            throw new WechatShopException(ResultExceptionEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if(!MathUtil.equalse(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            throw new WechatShopException(ResultExceptionEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单的支付状态
        orderService.paid(orderDto);

        return payResponse;
    }

    /**
     * 微信支付退款
     * @param orderDto
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        return refundResponse;
    }
}
