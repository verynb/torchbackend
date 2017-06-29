package com.torch.util.payUtil.weChat;

import java.math.BigDecimal;

/**
 * Created by xubao on 2016/12/16.
 */
public class WeChatAppPayCore {

    /**
     * double型金额 10.11 元 转 int型金额 1011分
     * @return
     */
    public static String doubleString2IntStringFee(String doubleStringFee){
        int index = doubleStringFee.indexOf(".");
        int length = doubleStringFee.length();
        Long amLong = 0l;
        if(index == -1){
            amLong = Long.parseLong(doubleStringFee+"00");
        }else if(length - index >= 3){
            amLong = Long.parseLong((doubleStringFee.substring(0,index+3)).replace(".",""));
        }else if(length - index == 2){
            amLong = Long.parseLong((doubleStringFee.substring(0,index+2)).replace(".","")+0);
        }else{
            amLong = Long.parseLong((doubleStringFee.substring(0,index+1)).replace(".","")+"00");
        }
        return amLong.toString();
    }

    /**
     * int型金额 1011分 转 DOUBLE型金额 10.11元
     * @return
     */
    public static String intString2DoubleStringFee(String intStringFee){
        return BigDecimal.valueOf(Long.parseLong(intStringFee)).divide(new BigDecimal(100)).toString();
    }
}
