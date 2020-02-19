package com.imooc.wechatshop.controller;

import com.imooc.wechatshop.config.ProjectUrlConfig;
import com.imooc.wechatshop.enums.ResultEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 用@Restcontroller 重定向不成功 应为被转为json了
 */
@Controller
@Slf4j
@RequestMapping("/sdk")
public class SDKGetWechatOpenidController {


    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 通过code
     * 使用sdk获取access-token和openid
     * @param returnUrl
     */
    @GetMapping("/auth")
    public String auth(@RequestParam("returnUrl") String returnUrl){

        String url = projectUrlConfig.getWechatMpAuth()+"/sell/sdk/userInfo";
        String rediectUrl = null;
        try {
            /**
             * oauth2buildAuthorizationUrl->String redirectURI, String scope, String state
             * 这里暂时把returnUrl 当做state传进去了 他还会给带回来 因为微信那边state会原封不动回传
             */
            rediectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "redirect:"+rediectUrl;
    }

    /**
     * 上面的方法跳转过来的跳转
     * 使用sdk通过code获取access-token和openid等信息
     * @param code
     * @param  returnUrl
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("state") String returnUrl,
                         @RequestParam("code") String code){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("[微信网页授权],e={}",e);
            throw  new WechatShopException(ResultEnum.WECHAT_MP_ERROR.getCode(), ResultEnum.WECHAT_MP_ERROR.getMessage());
        }
        //如果上面都没问题，那么这里已经拿到openid了
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:"+returnUrl+"?openid="+openId;
    }

    @GetMapping("/qrAuth")
    public String qrAuth(@RequestParam("returnUrl") String returnUrl){

        String url = projectUrlConfig.getWechatOpenAuth()+"/sell/sdk/qrUserInfo";
        String redirectUrl = null;
        try {
            redirectUrl = wxMpService.buildQrConnectUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect"+redirectUrl;
    }

    /**
     * 上面的方法跳转过来的跳转
     * 使用sdk通过code获取access-token和openid等信息
     * @param code
     * @param  returnUrl
     */
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("state") String returnUrl,
                           @RequestParam("code") String code){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("[微信网页授权],e={}",e);
            throw  new WechatShopException(ResultEnum.WECHAT_MP_ERROR.getCode(), ResultEnum.WECHAT_MP_ERROR.getMessage());
        }
        //如果上面都没问题，那么这里已经拿到openid了
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:"+returnUrl+"?openid="+openId;
    }
}
