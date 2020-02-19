package com.imooc.wechatshop.service;


import com.imooc.wechatshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);

    ProductCategory save(ProductCategory productCategory);
}
