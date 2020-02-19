package com.imooc.wechatshop.repository;

import com.imooc.wechatshop.entity.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
