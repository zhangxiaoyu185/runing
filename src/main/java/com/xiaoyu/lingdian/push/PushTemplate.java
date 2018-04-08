package com.xiaoyu.lingdian.push;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushTemplate {

    /**
     * IOS透传方式
     * 
     * @author zhangy
     * @date 2017年6月1日
     * @param messageAppPushParam
     * @return
     */
    public static TransmissionTemplate getTransmissionTemplate(MessageAppPushParam messageAppPushParam) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(messageAppPushParam.getAppId());
        template.setAppkey(messageAppPushParam.getAppKey());
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);        
        //系统都需要发送标准消息模板内容
        template.setTransmissionContent(messageAppPushParam.getTransmissionContent());
        APNPayload payload = new APNPayload();
        //if (0 == messageAppPushParam.getPushType()) { //群推
            // 在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，如10，效果和setBadge一样，
            // 应用icon上显示指定数字。不能和setBadge同时使用
        payload.setAutoBadge("+1");
        //} else { //单推
        //    payload.setBadge(messageAppPushParam.getBadge() == 0 ? 1 : messageAppPushParam.getBadge()); // icon上角标 群推显示为1,单推显示具体的未读消息数
        //}
        payload.setContentAvailable(0);
        //通知铃声文件名 
        payload.setSound("default");
        // payload.setCategory("$由客户端定义");
        payload.addCustomMsg("payload", messageAppPushParam.getTransmissionContent());
        // 简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(messageAppPushParam.getContent()));
        // 字典模式使用下者
        // payload.setAlertMsg(getDictionaryAlertMsg());
        template.setAPNInfo(payload);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
    
    /**
     * URL推送方式,不使用
     * 
     * @author zhangy
     * @date 2017年6月1日
     * @param messageAppPushParam
     * @return    
    public static LinkTemplate getLinkTemplate(MessageAppPushParam messageAppPushParam) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(messageAppPushParam.getAppId());
        template.setAppkey(messageAppPushParam.getAppKey());

        // 设置通知栏标题与内容
        template.setTitle(messageAppPushParam.getTitle());
        template.setText(messageAppPushParam.getContent());
        // 配置通知栏图标
        template.setLogo(messageAppPushParam.getLogoIcon());
        // 配置通知栏网络图标
        template.setLogoUrl(messageAppPushParam.getLogoUrl());
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        
        // 设置打开的网址地址
        template.setUrl(messageAppPushParam.getUrl());
        return template;
    }   
     */
    public static NotificationTemplate getNotificationTemplate(MessageAppPushParam messageAppPushParam) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(messageAppPushParam.getAppId());
        template.setAppkey(messageAppPushParam.getAppKey());

        // 设置通知栏标题与内容
        template.setTitle(messageAppPushParam.getTitle());
        template.setText(messageAppPushParam.getContent());                                                                                                                                                                                                                                                                           
        // 配置通知栏图标
        template.setLogo(messageAppPushParam.getLogoIcon());
        // 配置通知栏网络图标
        template.setLogoUrl(messageAppPushParam.getLogoUrl());
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        // 透传内容
        template.setTransmissionContent(messageAppPushParam.getTransmissionContent());
        return template;
    }
   
}
