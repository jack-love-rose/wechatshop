package com.imooc.wechatshop.demo;

import com.imooc.wechatshop.entity.OrderDetail;
import com.imooc.wechatshop.entity.OrderMaster;
import com.imooc.wechatshop.enums.PayStatusEnum;
import com.lly835.bestpay.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * redis'常见操作
 */
@RequestMapping("/RedisOperationDemo")
@RestController
public class RedisOperationDemo {

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    public void redisListDemo(){


        List<String> aList = new ArrayList<>();
        aList.add("a1");
        aList.add("a2");
        aList.add("a3");

        List<Object> bList =  new ArrayList<>();
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12387874934839");
        orderMaster.setOrderAmount(BigDecimal.ONE);
        orderMaster.setOrderStatus(1567);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMaster.setBuyerName("褚佳鑫");

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("34529837483923874");
        orderDetail.setOrderId("12387874934839");
        orderDetail.setProductName("A2婴儿奶粉");
        bList.add(JsonUtil.toJson(orderMaster));
        bList.add(JsonUtil.toJson(orderDetail));

        Map<String,String> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");

        redisTemplate.opsForList().leftPush("aListKey", aList);
        redisTemplate.opsForList().rightPush("bListKey",bList);
        redisTemplate.opsForList().rightPush("mapKey",map);

        List<String> aListResult = (List<String>)redisTemplate.opsForList().leftPop("aListKey");
        List<String> bListResult = (List<String>)redisTemplate.opsForList().rightPop("bListKey");
        Map<String,String> mapResult = (Map<String,String>)redisTemplate.opsForList().rightPop("mapKey");

        System.out.println("aListResult:"+aListResult);
        System.out.println("mapResult:"+mapResult);
        System.out.println("bListResult:"+bListResult);
    }
}
