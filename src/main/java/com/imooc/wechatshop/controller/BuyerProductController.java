package com.imooc.wechatshop.controller;

import com.imooc.wechatshop.vo.ProductInfoVO;
import com.imooc.wechatshop.vo.ProductVO;
import com.imooc.wechatshop.vo.ResultVO;
import com.imooc.wechatshop.entity.ProductCategory;
import com.imooc.wechatshop.entity.ProductInfo;
import com.imooc.wechatshop.service.ProductCategoryService;
import com.imooc.wechatshop.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * redis注解：
     *  还可以在类上加 @CacheConfig(cacheNames = "product") 这样下面的注解就都不用写cacheNames了
     ** key如果不填写的话：无参方法默认为空，有参方法默认为第一个参数得值作为key
     *  key还可以用方法中的参数来注入称为spel表达式 用#参数名称
     *  condition参数可以加一些条件 比如参数为productId方法 condition = "#productId.length()>3" 表示这个参数得长度大于三的情况下才缓存
     *  unless参数对方法整体的返回值做一些约束 用#result代表方法返回值 比如 unless = "#result.getCode()!=0" unless是如果不的意思 双重否定=肯定所以用 !=0
     *
     *  @Cacheable注解不支持配置过期时间，所有需要通过配置CacheManneg来配置默认的过期时间和针对每个类或者是方法进行缓存失效时间配置。
     * @return
     */
    @Cacheable(cacheNames = "product",key = "123") //这个注解放上了之后只访问一次这个方法 以后就访问redis了，如果没有对应的key就会访问这个方法
    @GetMapping("/list")
    public ResultVO list(){

        //查询所有上架商品
        List<ProductInfo> productInfoList =  productInfoService.findUpAll();

        //查询商品类目
        List<Integer> list = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(list);

        //数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory p:productCategoryList) {
            //设置商品信息外层返回VO中的类别名和商品类别类型
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(p.getCategoryName());
            productVO.setCategoryType(p.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList) {
                if(p.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVO.success(productVOList);
    }
}
