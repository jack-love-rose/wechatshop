package com.imooc.wechatshop.service;

import com.imooc.wechatshop.entity.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
