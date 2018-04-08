package com.xiaoyu.lingdian.entity;


/**
 * 系统设置表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
public class CoreSystemSet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crsstUnid;

    /**
     * 标识UUID
     */
    private String crsstUuid;

    /**
     * 邀请链接前缀
     */
    private String crsstInviteLink;

    /**
     * 一级佣金比
     */
    private Double crsstFirstIncome;

    /**
     * 二级佣金比
     */
    private Double crsstSecondIncome;

    /**
     * 返还优惠券比例
     */
    private Integer crsstCoupon;

    /**
     * 短信接口路径
     */
    private String crsstMessagePath;

    /**
     * 短信账户名
     */
    private String crsstMessageLoginname;

    /**
     * 短信密码
     */
    private String crsstMessagePwd;

    /**
     * 短信KEY
     */
    private String crsstMessageKey;

    /**
     * 项目域名
     */
    private String crsstMessageDomain;

    /**
     * 附件存放目录
     */
    private String crsstAttachmentDir;

    public Integer getCrsstUnid() {
        return crsstUnid;
    }

    public void setCrsstUnid(Integer crsstUnid) {
        this.crsstUnid = crsstUnid;
    }

    public String getCrsstUuid() {
        return crsstUuid;
    }

    public void setCrsstUuid(String crsstUuid) {
        this.crsstUuid = crsstUuid;
    }

    public String getCrsstInviteLink() {
        return crsstInviteLink;
    }

    public void setCrsstInviteLink(String crsstInviteLink) {
        this.crsstInviteLink = crsstInviteLink;
    }

    public Double getCrsstFirstIncome() {
        return crsstFirstIncome;
    }

    public void setCrsstFirstIncome(Double crsstFirstIncome) {
        this.crsstFirstIncome = crsstFirstIncome;
    }

    public Double getCrsstSecondIncome() {
        return crsstSecondIncome;
    }

    public void setCrsstSecondIncome(Double crsstSecondIncome) {
        this.crsstSecondIncome = crsstSecondIncome;
    }

    public Integer getCrsstCoupon() {
        return crsstCoupon;
    }

    public void setCrsstCoupon(Integer crsstCoupon) {
        this.crsstCoupon = crsstCoupon;
    }

    public String getCrsstMessagePath() {
        return crsstMessagePath;
    }

    public void setCrsstMessagePath(String crsstMessagePath) {
        this.crsstMessagePath = crsstMessagePath;
    }

    public String getCrsstMessageLoginname() {
        return crsstMessageLoginname;
    }

    public void setCrsstMessageLoginname(String crsstMessageLoginname) {
        this.crsstMessageLoginname = crsstMessageLoginname;
    }

    public String getCrsstMessagePwd() {
        return crsstMessagePwd;
    }

    public void setCrsstMessagePwd(String crsstMessagePwd) {
        this.crsstMessagePwd = crsstMessagePwd;
    }

    public String getCrsstMessageKey() {
        return crsstMessageKey;
    }

    public void setCrsstMessageKey(String crsstMessageKey) {
        this.crsstMessageKey = crsstMessageKey;
    }

    public String getCrsstMessageDomain() {
        return crsstMessageDomain;
    }

    public void setCrsstMessageDomain(String crsstMessageDomain) {
        this.crsstMessageDomain = crsstMessageDomain;
    }

    public String getCrsstAttachmentDir() {
        return crsstAttachmentDir;
    }

    public void setCrsstAttachmentDir(String crsstAttachmentDir) {
        this.crsstAttachmentDir = crsstAttachmentDir;
    }

    public CoreSystemSet() {
    }

}