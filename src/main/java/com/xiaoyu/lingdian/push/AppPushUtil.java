package com.xiaoyu.lingdian.push;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;


/**
 * App消息推送工具
 * 
 * @author: zhangyu
 * @since: 2017年6月1日
 */
public class AppPushUtil {
    
    /** 日志 */
	private final static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

    /**
     * 离线消息有效时间（ms）
     */
    private static long OFFLINE_EXPLIRE_TIME = 24 * 3600 * 1000;
    
    /**
     * 安卓
     */
    private static String ANDROID = "ANDROID";
    
    /**
     * IOS
     */
    private static String IOS = "IOS";

    /**
     * 单推
     * 
     * @param messageAppPushParam
     * @return
     */
    public static String singlePushMessage(MessageAppPushParam messageAppPushParam) {
        if (messageAppPushParam.getClientIdList().size() <= 0 || messageAppPushParam.getClientIdList().get(0) == null) {
            return "单推失败,clientId必传";
        }
        String[] cliendAndOsType = messageAppPushParam.getClientIdList().get(0).split("\\|");
        if (cliendAndOsType.length < 2) {
            return "单推失败,clientId错误";
        }
        String osType = cliendAndOsType[1];
        String cliendId = cliendAndOsType[0]; 
        messageAppPushParam.setPushType(1);
        IGtPush push = new IGtPush(messageAppPushParam.getHost(), messageAppPushParam.getAppKey(), messageAppPushParam.getMasterSecret());        
        AbstractTemplate template = null;
        if (ANDROID.equalsIgnoreCase(osType)) {
            messageAppPushParam.setTransmissionContent("{\"title\":\"" + messageAppPushParam.getTitle() + "\",\"content\":\""
                    + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");
            // 通知消息
            template = PushTemplate.getTransmissionTemplate(messageAppPushParam);
        } else {
            messageAppPushParam.setTransmissionContent("{\"content\":\"" + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");
            // 透传消息
            template = PushTemplate.getTransmissionTemplate(messageAppPushParam);
        }
        // 定义"SingleMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPLIRE_TIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(messageAppPushParam.getAppId());

        target.setClientId(cliendId);
        //target.setAlias(messageAppPushParam.getClientIdList().get(0));
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
        	logger.error(e.getMessage(), e);
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null && ret.getResponse() != null) {
        	logger.info(ret.getResponse().toString());
            return ret.getResponse().toString();
        } else {
        	logger.error("个推服务器响应异常");
            return "";
        }
    }
    
    public static String listPushMessage(MessageAppPushParam messageAppPushParam) {
        if (messageAppPushParam.getClientIdList().size() <= 0) {
            return "用户列表推送失败,clientId集合为空";
        }
        Set<String> androidClientIdSet = new HashSet<String>();
        Set<String> iosClientIdSet = new HashSet<String>();
        for (String clientStr : messageAppPushParam.getClientIdList()) {
            String[] cliendAndOsType = clientStr.split("\\|");
            if (cliendAndOsType.length >= 2) {
                if (ANDROID.equals(cliendAndOsType[1])) {
                    androidClientIdSet.add(cliendAndOsType[0]);
                } else {
                    iosClientIdSet.add(cliendAndOsType[0]);
                }
            }         
        }
        List<String> androidClientIdList = new ArrayList<>(androidClientIdSet);
        List<String> iosClientIdList = new ArrayList<>(iosClientIdSet);
        if (androidClientIdList.size() > 0) {
            listPushMessage(messageAppPushParam, androidClientIdList, ANDROID);
        }
        if (iosClientIdList.size() > 0) {
            listPushMessage(messageAppPushParam, iosClientIdList, IOS);
        }
        return "推送成功";
    }
    
    /**
     * 用户列表推送
     * @param messageAppPushParam
     * @param clientIdList
     * @param osType
     * @return
     */
    private static String listPushMessage(MessageAppPushParam messageAppPushParam, List<String> clientIdList, String osType) {
        // 配置返回每个用户返回用户状态，可选
        //System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的用户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        messageAppPushParam.setPushType(0);
        IGtPush push = new IGtPush(messageAppPushParam.getHost(), messageAppPushParam.getAppKey(), messageAppPushParam.getMasterSecret());
        AbstractTemplate template = null;
        if (ANDROID.equalsIgnoreCase(osType)) {
            messageAppPushParam.setTransmissionContent("{\"title\":\"" + messageAppPushParam.getTitle() + "\",\"content\":\""
                    + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");
            //通知消息
            template = PushTemplate.getTransmissionTemplate(messageAppPushParam);
        } else {
            messageAppPushParam.setTransmissionContent("{\"content\":\"" + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");
            // 通知透传模板
            template = PushTemplate.getTransmissionTemplate(messageAppPushParam);
        }
        
        ListMessage message = new ListMessage();
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPLIRE_TIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        // 配置推送目标
        List<Target> targets = new ArrayList<Target>();
        for (String clientId : clientIdList) {
            Target target = new Target();
            target.setAppId(messageAppPushParam.getAppId());
            target.setClientId(clientId);
            //target.setAlias(clientId);
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        
        IPushResult ret = null;
        try {
            ret = push.pushMessageToList(taskId, targets);
        } catch (RequestException e) {
        	logger.error(e.getMessage(), e);
        }
        if (ret != null && ret.getResponse() != null) {
        	logger.info(ret.getResponse().toString());
            return ret.getResponse().toString();
        } else {
        	logger.info("个推服务器响应异常");
            return "";
        }
    }
    
    /**
     * 群推
     * 
     * @author zhangy
     * @date 2017年6月6日
     * @param messageAppPushParam
     * @return
     */
    public static void appPushMessage(MessageAppPushParam messageAppPushParam) {
        appPushMessage(messageAppPushParam, ANDROID);
        appPushMessage(messageAppPushParam, IOS);
    }
    
    /**
     * 群推
     * 
     * @param messageAppPushParam
     * @param osType
     * @return
     */
    private static String appPushMessage(MessageAppPushParam messageAppPushParam, String osType) {
        messageAppPushParam.setPushType(0);
        //手机类型
        List<String> phoneTypeList = new ArrayList<String>();
        IGtPush push = new IGtPush(messageAppPushParam.getHost(), messageAppPushParam.getAppKey(), messageAppPushParam.getMasterSecret());
        if (ANDROID.equalsIgnoreCase(osType)) {
            messageAppPushParam.setTransmissionContent("{\"title\":\"" + messageAppPushParam.getTitle() + "\",\"content\":\""
                    + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");         
            phoneTypeList.add(ANDROID);
        } else {
            messageAppPushParam.setTransmissionContent("{\"content\":\"" + messageAppPushParam.getContent() + "\",\"pushId\":\""
                    + messageAppPushParam.getPushId() + "\",\"payload\":" + "{\"msgId\":\""
                    + messageAppPushParam.getMsgId() + "\",\"msgType\":\""
                    + messageAppPushParam.getSource() + "\"}}");
            phoneTypeList.add(IOS);
        }
        AbstractTemplate template = PushTemplate.getTransmissionTemplate(messageAppPushParam);
        // 定义"SingleMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINE_EXPLIRE_TIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions(); 
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(messageAppPushParam.getAppId());
        message.setAppIdList(appIdList);
        //省份
        //List<String> provinceList = new ArrayList<String>();
        //自定义tag
        List<String> tagList = new ArrayList<String>();
        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        //cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG, tagList);
        message.setConditions(cdt);     

        IPushResult ret = null;
        try {
            ret = push.pushMessageToApp(message);
        } catch (RequestException e) {
        	logger.error(e.getMessage(), e);
        }
        if (ret != null && ret.getResponse() != null) {
        	logger.info(ret.getResponse().toString());
            return ret.getResponse().toString();
        } else {
        	logger.info("个推服务器响应异常");
            return "";
        }
    }
    
}
