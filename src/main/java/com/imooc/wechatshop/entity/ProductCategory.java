package com.imooc.wechatshop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 商品类目表映射实体类
 */

@Entity
@Data
public class ProductCategory {

    //类目id
    @GeneratedValue
    @Id
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //类目类型
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}
