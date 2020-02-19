package com.imooc.wechatshop.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品类目和商品信息实体类最外层的VO，这就是给用户看的商品信息 包括商品的一些详情和商品的类目
 */
@Data
public class ProductVO implements Serializable {


    private static final long serialVersionUID = -63L;
    @JsonProperty("name") //此注解是返回给前段时的字段名称
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
