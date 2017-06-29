package com.torch.util.payUtil.weChat;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by xubao on 2017/1/5.
 * app调用微信支付
 */
public class WeChatPayService {

    public static String weChatPay(String out_trade_no,String totalFeeString,String ip,String attach,String notify_url){
        JSONObject resultJson = new JSONObject();
        Map<String, String> urlParamMap=new HashMap<String, String>();
        urlParamMap.put("body", "订单号:"+out_trade_no);
        urlParamMap.put("out_trade_no", out_trade_no);
        urlParamMap.put("total_fee", totalFeeString);
        urlParamMap.put("spbill_create_ip", ip);
        if(StringUtils.isBlank(attach)){
            attach = "cbGolf";
        }
        urlParamMap.put("attach", attach);
        urlParamMap.put("notify_url", notify_url);
        WeChatAppSignUtil weChatAppSignUtil = new WeChatAppSignUtil();
        urlParamMap= weChatAppSignUtil.generateSignParam(urlParamMap);

        if(null==urlParamMap){
            resultJson.put("code","1");
            resultJson.put("msg", "生成签名失败！");
            return resultJson.toJSONString();
        }

        String appKey= urlParamMap.get("appKey");
        urlParamMap.remove("appKey");
        JSONObject jObject=JSONObject.parseObject(JSONObject.toJSONString(urlParamMap));
        //json转换xml
        String xmlObject= XmlUtil.jsonToXml(jObject.toJSONString(), "xml",false);

        //调用微信统一下单
        String resultXml= HttpUtil.httpPostData("https://api.mch.weixin.qq.com/pay/unifiedorder",xmlObject , 3000);
        JSONObject jsonObject = null;
        try {
            resultXml =new String(resultXml.getBytes("ISO8859_1"),"utf-8").trim();
            String tempJson=XmlUtil.xml2Json(resultXml);
            jsonObject = JSONObject.parseObject(tempJson);
        } catch (Exception e){
            resultJson.put("code","1");
            resultJson.put("msg", "统一订单生成发生异常！");
            return resultJson.toJSONString();
        }
        if(null==jsonObject){
            resultJson.put("code","1");
            resultJson.put("msg", "统一订单生成发生异常！");
            return resultJson.toJSONString();
        }
        if(!"SUCCESS".equals(jsonObject.get("return_code"))){
            resultJson.put("code","1");
            resultJson.put("msg", "统一订单生成失败，错误信息："+resultJson.get("return_msg"));
            return resultJson.toJSONString();
        }
        JSONObject resJonsn = new JSONObject();
        resJonsn.put("appid", urlParamMap.get("appid"));
        resJonsn.put("partnerid", urlParamMap.get("mch_id"));
        resJonsn.put("prepayid", jsonObject.get("prepay_id"));
        resJonsn.put("noncestr", urlParamMap.get("nonce_str"));
        resJonsn.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        if("APP".equals(jsonObject.get("trade_type"))){
            resJonsn.put("package", "Sign=WXPay");
        }
        Map<String,String> map=JSONObject.toJavaObject(resJonsn, Map.class);
        String sign=weChatAppSignUtil.sign(map,appKey);
        resJonsn.put("sign", sign);
        resJonsn.put("out_trade_no",out_trade_no);
        resultJson.put("code","0");
        resultJson.put("result",resJonsn);

        return resultJson.toJSONString();
    }
}
