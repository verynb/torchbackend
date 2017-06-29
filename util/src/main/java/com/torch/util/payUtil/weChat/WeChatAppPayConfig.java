package com.torch.util.payUtil.weChat;

/**
 * Created by xubao on 2016/12/16.
 */
public class WeChatAppPayConfig {

    //APP
    public static String APP_APP_ID="wx35b61811b1f79ce6"; //应用appid
    public static String APP_APP_KEY="ChuangBoGOLF13022740011356674102";  //应用密钥
    public static String APP_MCH_ID="1356674102"; //商户号
    //微信支付异步通知
    public static String APP_NOTIFY_URL="http://api.cbgolf.cn:8080/backend/api/app/payback/wechat";
    //微信退订异步通知
    public static String APP_TD_NOTIFY_URL="http://api.cbgolf.cn:8080/backend/api/app/refund/wechat";
    //微信改签异步通知
    public static String APP_GQ_NOTIFY_URL="http://api.cbgolf.cn:8080/backend/api/app/endorse/wechat";

}
