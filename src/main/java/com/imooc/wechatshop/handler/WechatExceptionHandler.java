package com.imooc.wechatshop.handler;


import com.imooc.wechatshop.vo.ResultVO;
import com.imooc.wechatshop.config.ProjectUrlConfig;
import com.imooc.wechatshop.exception.ResponseBankException;
import com.imooc.wechatshop.exception.SellerAuthorizeException;
import com.imooc.wechatshop.exception.WechatShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常全局统一处理，捕获异常然后做点什么
 */
@ControllerAdvice
public class WechatExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
        .concat(projectUrlConfig.getWechatOpenAuth())
        .concat("/sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlConfig.getSell())
        .concat("/sell/seller/login"));
    }

    @ExceptionHandler(value = WechatShopException.class)
    @ResponseBody
    public ResultVO handlerSellerException(WechatShopException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 比如这里要求抛异常了之后返回的状态码不能是200 那么可以用这个注解或者引用httpresponse
     * */
    //@ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {

    }
}
