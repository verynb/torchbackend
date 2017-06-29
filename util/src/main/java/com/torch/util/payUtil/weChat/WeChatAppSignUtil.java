package com.torch.util.payUtil.weChat;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by xubao on 2016/12/16.
 */
public class WeChatAppSignUtil {


    /**
     * 封装加签参数，业务参数由外部传入，此处只封装微信相关配置参数
     * @param urlParamMap
     * @return //返回参数包含签名的map集合
     * @throws UnsupportedEncodingException
     */
    public Map<String, String> generateSignParam(Map<String, String> urlParamMap){


        String appKey=WeChatAppPayConfig.APP_APP_KEY;
        urlParamMap.put("appid", WeChatAppPayConfig.APP_APP_ID);
        urlParamMap.put("mch_id", WeChatAppPayConfig.APP_MCH_ID);
        urlParamMap.put("trade_type", "APP");
        urlParamMap.put("nonce_str", nonceString());

        String sign=sign(urlParamMap,appKey);
        if(null==sign){
            return null;
        }else{
            urlParamMap.put("sign", sign);
            urlParamMap.put("appKey", appKey);
            return urlParamMap;
        }
    }


    /**
     * 支付成功后的签名验证
     * @param json
     * @return
     */
    public String generateSignCheck(JSONObject json){
        Map<String, String> map=JSONObject.toJavaObject(json, Map.class);
        String appKey=map.get("appKey");
        map.remove("appKey");
        String sign=sign(map,appKey);
        return sign;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private  Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value.trim());
        }

        return result;
    }

    /**
     * 微信支付签名生成
     * @param urlParamMap
     * @return
     */
    public String sign(Map<String, String> urlParamMap,String appKey) {
        List<String> keys = new ArrayList<String>(paraFilter(urlParamMap).keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (String key : keys) {
            if ("" != urlParamMap.get(key)) {
                if (isFirst) {
                    isFirst = false;
                    stringBuilder.append(key).append("=").append(urlParamMap.get(key));
                } else {
                    stringBuilder.append("&").append(key).append("=").append(urlParamMap.get(key));
                }
            }
        }
        stringBuilder.append("&key=").append(appKey);
        String stringSignTemp = stringBuilder.toString();
        return MD5Util.MD5Encode(stringSignTemp,"utf-8").toUpperCase();
    }

    /**
     * 随机字符串生成
     * @return
     */
    private String nonceString(){
        //使用使用Apache的commons-lang.jar 生成18位随机字符串
        return RandomStringUtils.randomAlphabetic(18);
    }
}
