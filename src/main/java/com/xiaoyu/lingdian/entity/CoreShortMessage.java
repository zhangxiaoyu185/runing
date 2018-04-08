package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 短信日志记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
public class CoreShortMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crmceUnid;

    /**
     * 标识UUID
     */
    private String crmceUuid;

    /**
     * 发送手机号
     */
    private String crmceMobile;

    /**
     * 短信内容(包括验证码)
     */
    private String crmceContent;

    /**
     * 发送时间
     */
    private Date crmceTime;

    /**
     * 过期时间
     */
    private Date crmceTimeout;

    /**
     * 发送状态:1成功,0失败
     */
    private Integer crmceStatus;

    /**
     * 短信类型:1验证码2其它
     */
    private Integer crmceType;

    public Integer getCrmceUnid() {
        return crmceUnid;
    }

    public void setCrmceUnid(Integer crmceUnid) {
        this.crmceUnid = crmceUnid;
    }

    public String getCrmceUuid() {
        return crmceUuid;
    }

    public void setCrmceUuid(String crmceUuid) {
        this.crmceUuid = crmceUuid;
    }

    public String getCrmceMobile() {
        return crmceMobile;
    }

    public void setCrmceMobile(String crmceMobile) {
        this.crmceMobile = crmceMobile;
    }

    public String getCrmceContent() {
        return crmceContent;
    }

    public void setCrmceContent(String crmceContent) {
        this.crmceContent = crmceContent;
    }

    public Date getCrmceTime() {
        return crmceTime;
    }

    public void setCrmceTime(Date crmceTime) {
        this.crmceTime = crmceTime;
    }

    public Date getCrmceTimeout() {
        return crmceTimeout;
    }

    public void setCrmceTimeout(Date crmceTimeout) {
        this.crmceTimeout = crmceTimeout;
    }

    public Integer getCrmceStatus() {
        return crmceStatus;
    }

    public void setCrmceStatus(Integer crmceStatus) {
        this.crmceStatus = crmceStatus;
    }

    public Integer getCrmceType() {
        return crmceType;
    }

    public void setCrmceType(Integer crmceType) {
        this.crmceType = crmceType;
    }

    public CoreShortMessage() {
    }

}