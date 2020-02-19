package com.imooc.wechatshop.service.impl;

import com.imooc.wechatshop.converter.OrderMaster2OrderDtoConverter;
import com.imooc.wechatshop.repository.OrderDetailDao;
import com.imooc.wechatshop.repository.OrderMasterDao;
import com.imooc.wechatshop.dto.CartDTO;
import com.imooc.wechatshop.dto.OrderDTO;
import com.imooc.wechatshop.entity.OrderDetail;
import com.imooc.wechatshop.entity.OrderMaster;
import com.imooc.wechatshop.entity.ProductInfo;
import com.imooc.wechatshop.enums.OrderStatusEnum;
import com.imooc.wechatshop.enums.PayStatusEnum;
import com.imooc.wechatshop.enums.ResultExceptionEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import com.imooc.wechatshop.service.*;
import com.imooc.wechatshop.utils.otherUtils.RandomUtil;
import com.imooc.wechatshop.websocket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private PayService payService;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private PushMessageService pushMessageService;

    /**
     * 商品订单中的每件商品的价格一定不能使用前端传过来的，不安全（前端只传过来商品的ID和购买商品的数量）
     * 订单总价的计算也在后台计算
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDto) {
        /**
         * 随机生成的订单id和初始订单金额
         */
        String orderId = RandomUtil.genUniqueKey();
        BigDecimal orderAmount  = new BigDecimal(BigInteger.ZERO);
        /**
         * 分别将订单信息存在订单详情表和订单主表
         */
        //查询商品的价格和数量等
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new WechatShopException(ResultExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //生成订单入库
            orderDetail.setDetailId(RandomUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);
        }

        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //扣库存
        List<CartDTO> cartDTOList = orderDto.getOrderDetailList().stream().map(e ->new CartDTO(
                e.getProductId(),e.getProductQuantity()
            )).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        // 发送websocket消息
        webSocket.sendMessage("您有新的订单!");
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDto) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new WechatShopException(ResultExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(null == result){
            log.error("[取消订单] 更新失败，orderMaster={}",orderMaster);
            throw new WechatShopException(ResultExceptionEnum.ORDER_UPDATE_FAIL);
        }

        //加库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("[取消订单] 订单中无订单详情，orderMaster={}",orderMaster);
            throw new WechatShopException(ResultExceptionEnum.ORDERDETAIL_IS_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productInfoService.increaseStock(cartDTOList);

        //如果已经支付，需要退款
        if(orderDto.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDto);
        }

        return orderDto;
    }

    @Override
    public OrderDTO findOne(String id) {
        OrderMaster orderMaster = orderMasterDao.findById(id).orElse(null);
        if(null == orderMaster){
            throw new WechatShopException(ResultExceptionEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(id);
        if(null == orderDetails){
            throw new WechatShopException(ResultExceptionEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }

    @Override
    public Page<OrderDTO> findList(String buyerId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerId,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDtoConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public OrderDTO finish(OrderDTO orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new WechatShopException(ResultExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if(null == result){
            log.error("[完结订单] 更新失败，orderMaster{}",orderMaster);
            throw new WechatShopException(ResultExceptionEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模板消息
        pushMessageService.orderStatus(orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付] 订单状态不正确 orderDto={}",orderDto);
            throw new WechatShopException(ResultExceptionEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付] 订单支付状态不正确 orderDto={}",orderDto);
            throw new WechatShopException(ResultExceptionEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(null == updateResult){
            log.error("[订单支付] 更新失败，orderMaster{}",orderMaster);
            throw new WechatShopException(ResultExceptionEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPageable = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDtoConverter.converter(orderMasterPageable.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPageable.getTotalElements());
    }
}
