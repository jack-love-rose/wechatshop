package com.imooc.wechatshop.service.impl;

import com.imooc.wechatshop.repository.ProductInfoDao;
import com.imooc.wechatshop.dto.CartDTO;
import com.imooc.wechatshop.entity.ProductInfo;
import com.imooc.wechatshop.enums.ProductStatusEnum;
import com.imooc.wechatshop.enums.ResultExceptionEnum;
import com.imooc.wechatshop.exception.WechatShopException;
import com.imooc.wechatshop.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDao.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> orderDetails) {
        for (CartDTO cartDTO:orderDetails) {
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).orElse(null);
            if(null == productInfo){
                throw new WechatShopException(ResultExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> orderDetails) {
        for (CartDTO cartDTO:orderDetails) {
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).orElse(null);
            if(null == productInfo){
                throw new WechatShopException(ResultExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(stock < 0){
                throw new WechatShopException(ResultExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).orElse(null);
        if(null == productInfo){
            throw new WechatShopException(ResultExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum().equals(ProductStatusEnum.UP)){
            throw new WechatShopException(ResultExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoDao.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).orElse(null);
        if(null == productInfo){
            throw new WechatShopException(ResultExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum().equals(ProductStatusEnum.DOWN)){
            throw new WechatShopException(ResultExceptionEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoDao.save(productInfo);
    }
}
