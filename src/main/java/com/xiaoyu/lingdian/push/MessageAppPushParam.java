package com.xiaoyu.lingdian.push;

import java.util.ArrayList;
import java.util.List;

/** 
* app消息推送内容
* 
* @author: zhangyu
* @since: 2017年6月1日
* @history:
*/
public class MessageAppPushParam {

    /**标识用户的独立clientId集合,用来在推送app消息时使用*/
    private List<String> clientIdList;

    /**消息发送来源 1推送商讯 2互动消息 3系统消息*/
    private String source;

    /**通知栏标题*/
    private String title;

    /**通知栏内容*/
    private String content;
    
    /**通知栏图标*/
    private String logoIcon;
    
    /**通知栏图标地址*/
    private String logoUrl;

    /**个推主机*/
    private String host;
    
    /**个推appId*/
    private String appId;

    /**个推appKey*/
    private String appKey;

    /**个推密钥*/
    private String masterSecret;

    private String url;

    private String transmissionContent;
    
    /**
     * 未读消息树
     */
    private int badge;
    
    /**
     * 标识ID
     */
    private long pushId;
    
    /**
     * 消息ID
     */
    private long msgId;
    
    /**
     * 群推单推标识 0群推 1单推
     */
    private int pushType;
    
    public MessageAppPushParam(PushConfigBean configBean) {
        super();
        this.host = configBean.getHost();
        this.appId = configBean.getAppId();
        this.appKey = configBean.getAppKey();
        this.masterSecret = configBean.getMasterSecret();
    }

    public MessageAppPushParam(String host, String appId, String appKey, String masterSecret) {
        super();
        this.host = host;
        this.appId = appId;
        this.appKey = appKey;
        this.masterSecret = masterSecret;
    }

    /**
     * 功能说明：设值替换内容
     * 
     * @param key
     * @param value 
     * void
     */
    public void put(String key, String value) {
        if (content != null) {
            content = content.replace("{" + key + "}", value);
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getClientIdList() {
        return clientIdList == null ? new ArrayList<String>() : clientIdList;
    }

    public void setClientIdList(List<String> clientIdList) {
        this.clientIdList = clientIdList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoIcon() {
        return logoIcon;
    }

    public void setLogoIcon(String logoIcon) {
        this.logoIcon = logoIcon;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTransmissionContent() {
        return transmissionContent;
    }

    public void setTransmissionContent(String transmissionContent) {
        this.transmissionContent = transmissionContent;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public long getPushId() {
        return pushId;
    }

    public void setPushId(long pushId) {
        this.pushId = pushId;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

}
