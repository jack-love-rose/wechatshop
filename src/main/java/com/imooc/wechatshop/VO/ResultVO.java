package com.imooc.wechatshop.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) //这个注解的作用是，如果下面的字段有null值则不返回这个字段给前端
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -9858L;

    /*返回错误码*/
    private Integer code;
    /* 提示信息*/
    private String msg;
    /*返回的具体内容*/
    private T data;

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO error(Integer code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }

    public static ResultVO success(){
        return null;
    }
}
