package com.imooc.wechatshop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "project-url-config")
public class ProjectUrlConfig {

    private String wechatMpAuth;
    private String wechatOpenAuth;
    private String sell;

}
