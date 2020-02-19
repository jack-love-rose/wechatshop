package com.imooc.wechatshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/manual")
public class ManualGetWechatOpenidController {


    /**
     * 通过code
     * 手动获取access-token和openid
     * @param code
     */
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){

        log.info("进入auth方法,code={}",code);
        //通过拿到的code 获取access-token和openid
    }
}
