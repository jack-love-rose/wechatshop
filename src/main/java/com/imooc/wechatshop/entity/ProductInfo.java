package com.imooc.wechatshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.wechatshop.enums.OrderStatusEnum;
import com.imooc.wechatshop.enums.ProductStatusEnum;
import com.imooc.wechatshop.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;
    //商品名字
    private String productName;
    //商品价格
    private BigDecimal productPrice;
    //商品库存
    private Integer productStock;
    //商品描述
    private String productDescription;
    //商品图片
    private String productIcon;
    //商品状态
    private Integer productStatus = OrderStatusEnum.NEW.getCode();
    //类目编号
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getValuesByCode(productStatus,ProductStatusEnum.class);
    }
}
