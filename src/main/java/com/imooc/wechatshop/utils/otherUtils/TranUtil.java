package com.imooc.wechatshop.utils.otherUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TranUtil {

    // 参考地址： http://blog.csdn.net/a258831020/article/details/48395401

    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 转换为驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String camelCaseName(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * 分转元,保留 { @decimalNum } 位小数
     *
     * @param fenMoney
     * @return
     */
    public static Double changeMoney2Yan(long fenMoney, int decimalNum) {
        Double yuan = fenMoney / 100.0;
        BigDecimal formatYuan = new BigDecimal(yuan);
        return formatYuan.setScale(decimalNum, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
     */
    public static Long changeY2F(String amount){
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0L;
        if(index == -1){
            amLong = Long.valueOf(currency+"00");
        }else if(length - index >= 3){
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
        }else if(length - index == 2){
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
        }else{
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
        }
        return amLong;
    }

    public static Map<String, String> tranMapStringList(Map<String, String[]> httpMaps) {
        Map<String, String> map = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String[]>> httpMapsItem = httpMaps.entrySet().iterator();
        while (httpMapsItem.hasNext()) {
            Map.Entry<String, String[]> item = httpMapsItem.next();
            if (item.getValue().length > 0) {
                map.put(item.getKey(), item.getValue()[0]);
            }
        }
        return map;
    }

    /**
     *     object to map
      */
    public static Map<String, Object> obj2Map(Object object) {
        ObjectMapper oMapper = new ObjectMapper();
        return oMapper.convertValue(object, Map.class);
    }

}
