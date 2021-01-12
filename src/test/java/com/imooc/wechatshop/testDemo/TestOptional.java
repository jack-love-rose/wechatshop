package com.imooc.wechatshop.testDemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 褚佳鑫
 * @description 测试Optional
 * @date 2020/11/19 11:51
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestOptional {

    @Test
    public void test(){
        Map<String,String> map = new HashMap<>();
        map.put("name","chujiaxin");
        map.put("age","25");
        map.put("height","180");
        String name = Optional.ofNullable(map).orElse(null).get("name");
        log.info("-------------------name------------------{}",name);
    }
}
