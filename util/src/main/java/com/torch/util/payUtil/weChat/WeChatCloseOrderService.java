package com.torch.util.payUtil.weChat;

import com.alibaba.fastjson.JSONObject;
import com.torch.util.TorchUtilException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by yuanj on 2017/1/11.
 * 微信关闭订单公用方法
 */
public class WeChatCloseOrderService {

    public static void closeOrder(String orderSn){
        Map<String, String> urlParamMap=new HashMap<String, String>();
        urlParamMap.put("appid", WeChatAppPayConfig.APP_APP_ID);
        urlParamMap.put("mch_id", WeChatAppPayConfig.APP_MCH_ID);
        urlParamMap.put("out_trade_no", orderSn);
        urlParamMap.put("nonce_str", RandomStringUtils.randomAlphabetic(18));
        WeChatAppSignUtil weChatAppSignUtil = new WeChatAppSignUtil();
        String appKey = WeChatAppPayConfig.APP_APP_KEY;
        String sign = weChatAppSignUtil.sign(urlParamMap,appKey);
        if(null==sign){
            throw new TorchUtilException("关闭订单签名失败");
        }else{
            urlParamMap.put("sign", sign);
            urlParamMap.put("appKey", appKey);

        }
        urlParamMap.remove("appKey");
        JSONObject jObject=JSONObject.parseObject(JSONObject.toJSONString(urlParamMap));
        //json转换xml
        String xmlObject= XmlUtil.jsonToXml(jObject.toJSONString(), "xml",false);

        //调用微信关闭订单
        String resultXml= HttpUtil.httpPostData("https://api.mch.weixin.qq.com/pay/closeorder",xmlObject , 3000);
        JSONObject resultJson=null;
        try {
            resultXml =new String(resultXml.getBytes("ISO8859_1"),"utf-8").trim();
            String tempJson=XmlUtil.xml2Json(resultXml);
            resultJson=JSONObject.parseObject(tempJson);
        } catch (Exception e){
            throw new TorchUtilException("关闭订单发生异常");
        }
        if(null==resultJson){
            throw new TorchUtilException("关闭订单发生异常");
        }
        if(!"SUCCESS".equals(resultJson.get("return_code"))){
            throw new TorchUtilException("关闭订单发生异常，错误信息："+resultJson.get("return_msg"));
        }
    }
}
