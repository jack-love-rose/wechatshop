package com.imooc.wechatshop.service.impl;

import com.imooc.wechatshop.repository.SellerInfoRepository;
import com.imooc.wechatshop.entity.SellerInfo;
import com.imooc.wechatshop.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
