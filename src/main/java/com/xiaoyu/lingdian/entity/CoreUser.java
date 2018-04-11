package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 用户表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
public class CoreUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crusrUnid;

    /**
     * 标识UUID
     */
    private String crusrUuid;

    /**
     * 姓名
     */
    private String crusrName;

    /**
     * 手机号码
     */
    private String crusrMobile;

    /**
     * 状态:1启用,0禁用
     */
    private Integer crusrStatus;

    /**
     * 创建日期
     */
    private Date crusrCdate;

    /**
     * 修改日期
     */
    private Date crusrUdate;

    /**
     * 性别:1男,2女,0其它
     */
    private Integer crusrGender;

    /**
     * 详细地址
     */
    private String crusrAddress;

    /**
     * 备注
     */
    private String crusrRemarks;

    /**
     * 授权OPENID
     */
    private String crusrOpenid;

    /**
     * 微信用户的昵称
     */
    private String crusrWxNickname;

    /**
     * 微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String crusrWxSex;

    /**
     * 微信用户所在城市
     */
    private String crusrWxCity;

    /**
     * 微信用户所在国家
     */
    private String crusrWxCountry;

    /**
     * 微信用户所在省份
     */
    private String crusrWxProvince;

    /**
     * 微信用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     */
    private String crusrWxHeadimgurl;

    /**
     * 用户UnionID
     */
    private String crusrUnionid;

    /**
     * 会话秘钥
     */
    private String crusrSessionKey;

    /**
     * 最后登录时间
     */
    private Date crusrLastTime;

    /**
     * 所属部门
     */
    private String crusrDept;

    /**
     * 所属部门名称
     */
    private String crusrDeptName;

    /**
     * 积分
     */
    private Integer crusrIntegral;

    /**
     * 称号
     */
    private String crusrTitle;

    public Integer getCrusrUnid() {
        return crusrUnid;
    }

    public void setCrusrUnid(Integer crusrUnid) {
        this.crusrUnid = crusrUnid;
    }

    public String getCrusrUuid() {
        return crusrUuid;
    }

    public void setCrusrUuid(String crusrUuid) {
        this.crusrUuid = crusrUuid;
    }

    public String getCrusrName() {
        return crusrName;
    }

    public void setCrusrName(String crusrName) {
        this.crusrName = crusrName;
    }

    public String getCrusrMobile() {
        return crusrMobile;
    }

    public void setCrusrMobile(String crusrMobile) {
        this.crusrMobile = crusrMobile;
    }

    public Integer getCrusrStatus() {
        return crusrStatus;
    }

    public void setCrusrStatus(Integer crusrStatus) {
        this.crusrStatus = crusrStatus;
    }

    public Date getCrusrCdate() {
        return crusrCdate;
    }

    public void setCrusrCdate(Date crusrCdate) {
        this.crusrCdate = crusrCdate;
    }

    public Date getCrusrUdate() {
        return crusrUdate;
    }

    public void setCrusrUdate(Date crusrUdate) {
        this.crusrUdate = crusrUdate;
    }

    public Integer getCrusrGender() {
        return crusrGender;
    }

    public void setCrusrGender(Integer crusrGender) {
        this.crusrGender = crusrGender;
    }

    public String getCrusrAddress() {
        return crusrAddress;
    }

    public void setCrusrAddress(String crusrAddress) {
        this.crusrAddress = crusrAddress;
    }

    public String getCrusrRemarks() {
        return crusrRemarks;
    }

    public void setCrusrRemarks(String crusrRemarks) {
        this.crusrRemarks = crusrRemarks;
    }

    public String getCrusrOpenid() {
        return crusrOpenid;
    }

    public void setCrusrOpenid(String crusrOpenid) {
        this.crusrOpenid = crusrOpenid;
    }

    public String getCrusrWxNickname() {
        return crusrWxNickname;
    }

    public void setCrusrWxNickname(String crusrWxNickname) {
        this.crusrWxNickname = crusrWxNickname;
    }

    public String getCrusrWxSex() {
        return crusrWxSex;
    }

    public void setCrusrWxSex(String crusrWxSex) {
        this.crusrWxSex = crusrWxSex;
    }

    public String getCrusrWxCity() {
        return crusrWxCity;
    }

    public void setCrusrWxCity(String crusrWxCity) {
        this.crusrWxCity = crusrWxCity;
    }

    public String getCrusrWxCountry() {
        return crusrWxCountry;
    }

    public void setCrusrWxCountry(String crusrWxCountry) {
        this.crusrWxCountry = crusrWxCountry;
    }

    public String getCrusrWxProvince() {
        return crusrWxProvince;
    }

    public void setCrusrWxProvince(String crusrWxProvince) {
        this.crusrWxProvince = crusrWxProvince;
    }

    public String getCrusrWxHeadimgurl() {
        return crusrWxHeadimgurl;
    }

    public void setCrusrWxHeadimgurl(String crusrWxHeadimgurl) {
        this.crusrWxHeadimgurl = crusrWxHeadimgurl;
    }

    public Date getCrusrLastTime() {
        return crusrLastTime;
    }

    public void setCrusrLastTime(Date crusrLastTime) {
        this.crusrLastTime = crusrLastTime;
    }

    public String getCrusrUnionid() {
        return crusrUnionid;
    }

    public void setCrusrUnionid(String crusrUnionid) {
        this.crusrUnionid = crusrUnionid;
    }

    public String getCrusrSessionKey() {
        return crusrSessionKey;
    }

    public void setCrusrSessionKey(String crusrSessionKey) {
        this.crusrSessionKey = crusrSessionKey;
    }

    public String getCrusrDept() {
        return crusrDept;
    }

    public void setCrusrDept(String crusrDept) {
        this.crusrDept = crusrDept;
    }

    public Integer getCrusrIntegral() {
        return crusrIntegral;
    }

    public void setCrusrIntegral(Integer crusrIntegral) {
        this.crusrIntegral = crusrIntegral;
    }

    public String getCrusrTitle() {
        return crusrTitle;
    }

    public void setCrusrTitle(String crusrTitle) {
        this.crusrTitle = crusrTitle;
    }

    public String getCrusrDeptName() {
        return crusrDeptName;
    }

    public void setCrusrDeptName(String crusrDeptName) {
        this.crusrDeptName = crusrDeptName;
    }

    public CoreUser() {
    }

}