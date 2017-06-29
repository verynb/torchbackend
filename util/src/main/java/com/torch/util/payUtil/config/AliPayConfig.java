package com.torch.util.payUtil.config;

/* *
 *Created by xubao on 2016/12/7.
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
public class AliPayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088221610454625";
    // 商户的私钥
      public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANdARTLfdjthWVlNkts9Y58fOLJZtE1MOgS8vHu7m71SsShXh9pycHado2rHAbtLBaJYqW+AaXzzEj7LiGFirausT2ppp6GrW//uaFw42FX1vjiSdCqeZWuygQ4IbkrIxuRJEcj6AbZaY+qS6DjC972fCsR235BcmbuxqPRrmRfjAgMBAAECgYEAyfZYtyHqOk7d2aeqeiDAf0V5GgaG4Nv7uAQqSWI+/0U+Sd7O7gzgLrs74Gz21/fNQoUz/RWcZItg/CBWRyGiVi/KxVhaN7K/YXzQOww6tTchuBTmbM7MU8d3tFAWs06nrr+Dix35Xxwhr3hARXqmsJrvG5zF0+yzfOYzdGn0FWECQQDv5yQJWYIavP5BFOwdi5iaX1tFwLqJKKv9/GxOI8Hsti+yR83Ph42H8YLvP2P6Ev3KrzqMx8L4dAsui/g8rLURAkEA5bGuGRDtlOE3n4Wa7XFpW0IogWSAGiDmEvvGgrIRXq1+kx/IQpv6FGBYs7OQqIUMbQPwNp14xqFjc9H9mNqtswJAN75BhVqi/IuFmKG/622QW+5kSuTpcw1cSXvpvo5YdSQwGQDONbaNkFTx052rIWRgTERDGosgJpLwNjSXzaFjwQJAXrI5L5ZeDLIpZ3gCnn/SStBkuKvBcIQ9tnfcqIdXR+xJMci5nFSH7pymFTgjnIZ1z//+qdWUNHfW86oQXlNRpwJBAIj4QOHjIhxWs+1DgD5SAv8XPqWz2bvRODWKr026G/Gk6+4GnBsPu+A6/VQHK0leo+k+3D/kLHopvCCOPspFzOo=";
    // 支付宝的公钥，无需修改该值
    public static String public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    public static String appid = "2016061601522808";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";
    // 支付类型 ，无需修改
    public static String payment_type = "1";

    //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
    public static String product_code = "QUICK_MSECURITY_PAY";
    // 签名方式 不需修改
    public static String sign_type = "RSA";
    public static String seller_id="cbdsv@foxmail.com";

    //支付宝支付异步通知
    public static String notify_url = "http://api.cbgolf.cn:8080/backend/api/app/payback/ali";

    //支付宝退订异步通知
    public static String td_notify_url = "http://api.cbgolf.cn:8080/backend/api/app/refund/ali";

    //支付宝改签异步通知
    public static String gq_notify_url = "http://api.cbgolf.cn:8080/backend/api/app/endorse/ali";

}
