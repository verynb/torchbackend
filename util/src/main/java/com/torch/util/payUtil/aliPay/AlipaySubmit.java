package com.torch.util.payUtil.aliPay;

import com.torch.util.payUtil.config.AliPayConfig;
import com.torch.util.payUtil.sign.RSA;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by xubao on 2016/12/7.
 * 支付宝各接口请求提交类
 */
public class AlipaySubmit {

    /**
     * 生成签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysignForApp(String sPara) {
        String mysign = "";
        if(AliPayConfig.sign_type.equals("RSA")){
            mysign = RSA.sign(sPara, AliPayConfig.private_key,
                    AliPayConfig.input_charset);
            try {
                mysign = URLEncoder.encode(mysign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.getMessage();
            }
        }
        return mysign;
    }

    /**
     * 生成签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara) {
        String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if (AliPayConfig.sign_type.equals("RSA")) {
            mysign = RSA.sign(prestr, AliPayConfig.private_key,
                    AliPayConfig.input_charset);
        }
        return mysign;
    }
}
