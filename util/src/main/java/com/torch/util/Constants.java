package com.torch.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Constants {
    public final String ENV="-test";
    public final int PAGESIZE = 20;
    public final Double DENSITY = 7.85;
    public final SimpleDateFormat DF_yyyy_MM_ddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final SimpleDateFormat DF_DATE_SEQUENCE = new SimpleDateFormat("yyyyMMddHHmmss");
    public final SimpleDateFormat DF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public final SimpleDateFormat DF_YYYY_MM = new SimpleDateFormat("yyyy-MM");
    public final SimpleDateFormat DF_YY = new SimpleDateFormat("yy");
    public final SimpleDateFormat DF_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    public final SimpleDateFormat DF_HHmmss = new SimpleDateFormat("HH:mm:ss");
    public final double HottempPosi = 6000;
    public final double AvetempPosi = 600;
    public final double FullLength = 30090+4000;
    public final double Guidao = 4000;
    public final String PRODUCEPLAN_PREFIX = "P";
    public final String LINGLIAO_PREFIX = "L";
    public final String TICKET_PREFIX = "T";
    public final boolean INCLUDE_AGENTER = true;
    public final boolean SINGLETON_TASK = true;
    public final String FURNDIRECTION = "rtl";
    public final String LOGINUSER = "loginuser";
    public final String ROLE_VIEWTASK="renwuchakan";
    /******** sms短信{ *******/
    public final String SMS_APIKEY = "275355bdd84de9d34179654754a65f5d";
    public final String SMS_URI_SEND = "http://yunpian.com/v1/sms/send.json";
    public final String SMS_URI_TPL_SEND = "http://yunpian.com/v1/sms/tpl_send.json";
    public final String SMS_ENCODING = "UTF-8";
    public final String SMS_APP_SIGN = "【数字钢厂】";
    public final long SLEEP_TIME_MS = 20 * 1000;
    public final int SMS_MAX_TRYTIMES = 3;
    /******** sms短信} *******/

    /***********传票扩展信息字段{**********/
    public final String TK_EX_COLDOUT = "coldout";
    /***********传票扩展信息字段}**********/

    /*********系统设置中显示的开关控制项{************/
    public final String SYS_SWITCH_OPTIMIZEDATA = "DataOptimization";
    public final String SYS_SWITCH_TICKETFLOW = "produceTaskSwitch";
    public final String SYS_SWITCH_MATERIALFLOW = "materialTaskSwitch";
    /*********传票结束的方式**********************/
    public final String TICKET_TASK_END_AUTO="auto";//自动
    public final String TICKET_TASK_END_MANUAL="manual";//手动
    public final String TICKET_TASK_END_IMMDE="immed";//即时
    @SuppressWarnings("serial")
    public final List<String> ALLSWITCH = new ArrayList<String>(){{
        add(SYS_SWITCH_OPTIMIZEDATA);
        add(SYS_SWITCH_TICKETFLOW);
        add(SYS_SWITCH_MATERIALFLOW);
    }};
    /*********系统设置中显示的开关控制项}************/

    /*******数据优化********/
    public final String DATA_OPTIMIZE_WAIGUAN="waiguan";
    public final String DATA_OPTIMIZE_CHENGFEN="chengfen";
    public final String DATA_OPTIMIZE_CHENGPIN="chengpin";
    public final String DATA_OPTIMIZE_CHANPIN="chanpin";
    public final String DATA_OPTIMIZE_LUWEN="luwen";

    @SuppressWarnings("serial")
    public final Map<String,String> DATA_OPTIMIZE = new HashMap<String,String>(){{
        put(DATA_OPTIMIZE_WAIGUAN,DATA_OPTIMIZE_WAIGUAN);
        put(DATA_OPTIMIZE_CHENGFEN,DATA_OPTIMIZE_CHENGFEN);
        put(DATA_OPTIMIZE_CHENGPIN,DATA_OPTIMIZE_CHENGPIN);
        put(DATA_OPTIMIZE_CHANPIN,DATA_OPTIMIZE_CHANPIN);
        put(DATA_OPTIMIZE_LUWEN,DATA_OPTIMIZE_LUWEN);
    }};
    /*******数据优化********/

    /******切头损耗参数{*****/
    public final String LOSS_BIANGANG="扁钢";
    public final String LOSS_DAIGANG="带钢";
    public final String LOSS_XINGGANG="型钢";//扁钢之外的任何
    @SuppressWarnings("serial")
    public final Map<String,Double> LOSS_BY_CUT = new HashMap<String,Double>(){{
        put(LOSS_BIANGANG,0.035);
        put(LOSS_DAIGANG,0.035);
        put(LOSS_XINGGANG,0.05);
    }};
    /******切头损耗参数}*****/

    /******火耗（氧化）损耗参数{*****/
    public final double LOSS_BY_FIRE=0.02;
    /******火耗（氧化）损耗参数}*****/

    public final String CALENDAR_KEY_SWITCHTIME = "switchtime";

    /****sysconfig{*****/
    public final String SYS_CONFIG_WORKTIME = "worktime";
    /****sysconfig}*****/

    /****ticket extended fileds{*****/
    public final String TK_EXT_COLDOUT = "coldout";
    public final String TK_EXT_ISSUSPECTED = "issuspected";

    @SuppressWarnings("serial")
    public final Map<String,String> TK_EXTEND_FIELDS = new HashMap<String,String>(){{
        put(TK_EX_COLDOUT,TK_EX_COLDOUT);
        put(TK_EXT_ISSUSPECTED,TK_EXT_ISSUSPECTED);
    }};
    /****ticket extended fileds}*****/
    public final String MATERIAL_RECEIVE_CLASS_PREFIXED="MaterialReceiveImpl";//原料入库类全限定名前缀

    @SuppressWarnings("serial")
    public final List<String> ALLFLOWS = new ArrayList<String>(){{
        add("material");
        add("preproduce");
        add("produce");
        add("accessproduce");
        add("ticketmonitor");
    }};
    //配置页面搜索表对应搜索字段
    @SuppressWarnings("serial")
    public final Map<String,String> TABLE_COLUMN = new HashMap<String,String>(){{
        put("Produceplan","planno,productname,productguige,ganghao");
        put("Contract","contractno,company");
        put("Ticket","ticketno,furnaceno,prdname,prdguige");
        put("PowerConsumptionRecorder","numberon,numberoff,createtime");
        put("CoalRecorder","firaddqty,secaddqty,createtime");
        put("Materialout","contractno,company");
        put("Productout","company,modifytime,contractno");
        put("ForgeContract","contractno,pickPlace,transporterPayer,meansPayment,remark,signedPlace,disputeResolution,companyName,companyNameB");
        put("ForgeContractLine","ganghao,productname,guige,productdrawno,type,gongyi,productCategory,standard,standardWay");
        put("ForgeProducePlan","planno");
        put("ForgeTicket","forgeticketno,forgeplanno,chejian");
        put("DataDic","name,value");
    }};
    public final String WEIXIN_TOKEN="bici";

    public static final int PAGE_SIZE_MAX_LIMIT = 100;

}

