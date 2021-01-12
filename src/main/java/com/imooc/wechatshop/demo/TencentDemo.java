package com.imooc.wechatshop.demo;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author 褚佳鑫
 * @description
 * @date 2020/10/14 17:27
 **/

@Slf4j
public class TencentDemo {

    private final static String CHARSET = "UTF-8";

    public static String sign(String s, String key, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(CHARSET), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(s.getBytes(CHARSET));
        return DatatypeConverter.printBase64Binary(hash);
    }

    public static String getStringToSign(TreeMap<String, Object> params) {
        StringBuilder s2s = new StringBuilder("GETcvm.tencentcloudapi.com/?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    public static String getUrl(TreeMap<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("https://cvm.tencentcloudapi.com/?");
        // 实际请求的url中对参数顺序没有要求
        for (String k : params.keySet()) {
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CHARSET)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }

    public static void main(String[] args) throws Exception {
        /**TreeMap可以自动排序，用于存放公共参数，因为公共参数需要按字典序排序**/
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
        params.put("Timestamp", System.currentTimeMillis() / 1000);
        params.put("SecretId", "AKIDz8krbsJ5yKBZQpn74WFkmLPx3*******");
        params.put("Action", "DescribeInstances");
        params.put("Version", "2017-03-12");
        params.put("Region", "ap-guangzhou");
        params.put("Limit", 20);
        params.put("Offset", 0);
        params.put("InstanceIds.0", "ins-09dx96dg");
        params.put("Signature", sign(getStringToSign(params), "Gu5t9xGARNpq86cd98joQYCN3*******", "HmacSHA1"));
        log.error("请求链接-------------------------->",getUrl(params));
    }
}
