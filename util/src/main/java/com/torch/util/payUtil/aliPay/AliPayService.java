package com.torch.util.payUtil.aliPay;

import com.alibaba.fastjson.JSONObject;
import com.torch.util.payUtil.config.AliPayConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by xubao on 2017/1/5.
 * app调用支付宝支付
 */
public class AliPayService {

    /**
     * app调用支付宝支付
     * @param money 支付的金额
     * @param subject 商品名
     * @param body 商品描述
     * @param out_trade_no 订单编号
     * @param it_b_pay 支付超时时间
     * @return
     */
    public static String aliPay(String money,String subject,String body,String out_trade_no,String it_b_pay,String notify_url){
        JSONObject resultJson = new JSONObject();
        String result = "";
        result = "partner=\""+ AliPayConfig.partner+"\"&seller_id=\""+ AliPayConfig.seller_id+"\"&out_trade_no=\""+
                out_trade_no+"\"&subject=\""+subject+"\"&body=\""+body+"\"&total_fee=\""+money+"\"&notify_url=\""+
                notify_url+"\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\""+it_b_pay+"\""+
                "&return_url=\"m.alipay.com\"";
        String sign = AlipaySubmit.buildRequestMysignForApp(result);
        result += "&sign=\""+sign+"\"&sign_type=\"RSA\"";
        if(StringUtils.isBlank(result)){
            resultJson.put("code","1");
            resultJson.put("msg","FAIL");
        }
        resultJson.put("code","0");
        resultJson.put("msg","SUCCESS");
        resultJson.put("result",result);
        return resultJson.toJSONString();
    }
}
