package com.imooc.wechatshop.service;

import com.imooc.wechatshop.dto.CartDTO;
import com.imooc.wechatshop.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**]
 * 商品信息
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> orderDetails);

    //减库存
    void decreaseStock(List<CartDTO> orderDetails);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
