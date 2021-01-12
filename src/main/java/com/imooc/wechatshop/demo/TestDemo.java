package com.imooc.wechatshop.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 褚佳鑫
 * @description 测试一些有的没的
 * @date 2020/10/22 11:11
 **/
public class TestDemo {

    public static void main(String[] args) {

        String beginDate = "2020-20";
        String endDate = "2020-55";
        int beginDateInt = Integer.parseInt(beginDate.replace("-",""));
        int endDateInt = Integer.parseInt(endDate.replace("-",""));

        for (int i = beginDateInt; i <= endDateInt; i++) {
            StringBuffer str = new StringBuffer(String.valueOf(i)).insert(4,'-');
        }
        getDaysPer(beginDate,endDate);
    }

    public static List<String> getDaysPer(String startDay, String endDay) {

        int startTime = Integer.parseInt(startDay.replace("-", ""));
        int endTIme = Integer.parseInt(endDay.replace("-", ""));
        List<String> rs = new ArrayList<>();
        String startStr = String.valueOf(startTime);
        StringBuffer sb = new StringBuffer(startStr);
        sb.insert(4, "-");
        rs.add(sb.toString());

        while (startTime < endTIme) {

            // 判断是否大于52周
            if(startTime%100 >= 52){
                startTime = startTime + 49;
            }else{
                startTime = startTime + 1;
            }

            String tmpStr = String.valueOf(startTime);

            StringBuffer stringBuffer = new StringBuffer(tmpStr);
            stringBuffer.insert(4, "-");
            rs.add(stringBuffer.toString());
        }
        return rs;
    }
}
