package com.imooc.wechatshop.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpConfigStorage wxMpConfigStorage = new WxMpDefaultConfigImpl();
        ((WxMpDefaultConfigImpl) wxMpConfigStorage).setAppId(wechatAccountConfig.getOpenAppId());
        ((WxMpDefaultConfigImpl) wxMpConfigStorage).setSecret(wechatAccountConfig.getOpenAppSecret());
        return wxMpConfigStorage;
    }
}
