package com.torch.util.payUtil;

import com.alibaba.fastjson.JSONObject;
import com.torch.util.payUtil.aliPay.AlipaySubmit;
import com.torch.util.payUtil.config.AliPayConfig;
import com.torch.util.payUtil.weChat.HttpUtil;
import com.torch.util.payUtil.weChat.WeChatAppPayConfig;
import com.torch.util.payUtil.weChat.WeChatAppPayCore;
import com.torch.util.payUtil.weChat.WeChatAppSignUtil;
import com.torch.util.payUtil.weChat.XmlUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by yuanj on 2017/6/29.
 */
@Slf4j
public class PayUtil {

  /**
   *
   * @param order_sn 订单编号
   * @param goods_name  商品名称
   * @param totalPrice 订单总价
   * @param times
   * @return
   */
  private JSONObject aliPay(String order_sn, String goods_name, String totalPrice, String times) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", "1");
    String result = "";

    result = "partner=\"" + AliPayConfig.partner + "\"&seller_id=\"" + AliPayConfig.seller_id + "\"&out_trade_no=\"" +
        order_sn + "\"&subject=\"" + goods_name + "\"&body=\"" + goods_name + "\"&total_fee=\"" + totalPrice
        + "\"&notify_url=\"" +
        AliPayConfig.notify_url
        + "\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"" + times + "\""
        +
        "&return_url=\"m.alipay.com\"";
    String sign = AlipaySubmit.buildRequestMysignForApp(result);
    log.debug(result);
    result += "&sign=\"" + sign + "\"&sign_type=\"RSA\"";

    if (!StringUtils.isBlank(result)) {
      jsonObject.remove("code");
      jsonObject.put("code", "0");
      jsonObject.put("result", result);
    }

    return jsonObject;
  }

  private JSONObject weChatPay(String ip, String order_sn, String totalPrice) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", "1");

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("HHmmss");
    DateTime dt = new DateTime();
    String date = dt.toString(dateTimeFormatter);
    order_sn = date + order_sn;

    String totalFeeString = WeChatAppPayCore.doubleString2IntStringFee(totalPrice);
    Map<String, String> urlParamMap = new HashMap<String, String>();
    urlParamMap.put("body", "订单号:" + order_sn);
    urlParamMap.put("out_trade_no", order_sn);
    urlParamMap.put("total_fee", totalFeeString);
    urlParamMap.put("spbill_create_ip", ip);
    urlParamMap.put("notify_url", WeChatAppPayConfig.APP_NOTIFY_URL);

    WeChatAppSignUtil weChatAppSignUtil = new WeChatAppSignUtil();
    urlParamMap = weChatAppSignUtil.generateSignParam(urlParamMap);

    if (null == urlParamMap) {
      jsonObject.put("msg", "生成签名失败！");
      return jsonObject;
    }

    String appKey = urlParamMap.get("appKey");
    urlParamMap.remove("appKey");
    JSONObject jObject = JSONObject.parseObject(JSONObject.toJSONString(urlParamMap));
    //json转换xml
    String xmlObject = XmlUtil.jsonToXml(jObject.toJSONString(), "xml", false);

    log.debug("requset unifedorder xml= {}", xmlObject);
    //调用微信统一下单
    String resultXml = HttpUtil.httpPostData("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlObject, 3000);
    JSONObject resultJson = null;
    try {
      resultXml = new String(resultXml.getBytes("ISO8859_1"), "utf-8").trim();
      log.debug("requset unifedorder resultXml=" + resultXml);
      String tempJson = XmlUtil.xml2Json(resultXml);
      resultJson = JSONObject.parseObject(tempJson);
    } catch (Exception e) {
      jsonObject.put("msg", "统一订单生成发生异常！");
      return jsonObject;
    }
    if (null == resultJson) {
      jsonObject.put("msg", "统一订单生成发生异常！");
      return jsonObject;
    }
    if (!"SUCCESS".equals(resultJson.get("return_code"))) {
      jsonObject.put("msg", "统一订单生成失败，错误信息：" + resultJson.get("return_msg"));
      return jsonObject;
    }
    jsonObject.remove("code");
    JSONObject resJonsn = new JSONObject();
    resJonsn.put("appid", urlParamMap.get("appid"));
    resJonsn.put("partnerid", urlParamMap.get("mch_id"));
    resJonsn.put("prepayid", resultJson.get("prepay_id"));
    resJonsn.put("noncestr", urlParamMap.get("nonce_str"));
    resJonsn.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    if ("APP".equals(resultJson.get("trade_type"))) {
      resJonsn.put("package", "Sign=WXPay");
    }
    Map<String, String> map = JSONObject.toJavaObject(resJonsn, Map.class);
    String sign = weChatAppSignUtil.sign(map, appKey);
    resJonsn.put("sign", sign);
    resJonsn.put("out_trade_no", order_sn);
    jsonObject.put("code", "0");
    jsonObject.put("result", resJonsn);

    return jsonObject;
  }
}
