package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

import java.util.Date;

/**
 * 账户表
 */
public class CoreAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer cractUnid;

    /**
     * 标识UUID
     */
    private String cractUuid;

    /**
     * 帐户名称
     */
    private String cractName;

    /**
     * 登录密码
     */
    private String cractPassword;

    /**
     * 状态:1启用,0禁用
     */
    private Integer cractStatus;

    /**
     * 角色集合
     */
    private String cractRoles;

    /**
     * 创建日期
     */
    private Date cractCdate;

    /**
     * 修改日期
     */
    private Date cractUdate;

    /**
     * 联系方式
     */
    private String cractTel;

    /**
     * 邮箱
     */
    private String cractEmail;

    /**
     * 备注
     */
    private String cractRemarks;

    public void setCractUnid(Integer cractUnid) {
        this.cractUnid = cractUnid;
    }

    public Integer getCractUnid() {
        return cractUnid;
    }

    public void setCractUuid(String cractUuid) {
        this.cractUuid = cractUuid;
    }

    public String getCractUuid() {
        return cractUuid;
    }

    public void setCractName(String cractName) {
        this.cractName = cractName;
    }

    public String getCractName() {
        return cractName;
    }

    public void setCractPassword(String cractPassword) {
        this.cractPassword = cractPassword;
    }

    public String getCractPassword() {
        return cractPassword;
    }

    public void setCractStatus(Integer cractStatus) {
        this.cractStatus = cractStatus;
    }

    public Integer getCractStatus() {
        return cractStatus;
    }

    public void setCractRoles(String cractRoles) {
        this.cractRoles = cractRoles;
    }

    public String getCractRoles() {
        return cractRoles;
    }

    public void setCractCdate(Date cractCdate) {
        this.cractCdate = cractCdate;
    }

    public Date getCractCdate() {
        return cractCdate;
    }

    public void setCractUdate(Date cractUdate) {
        this.cractUdate = cractUdate;
    }

    public Date getCractUdate() {
        return cractUdate;
    }

    public void setCractTel(String cractTel) {
        this.cractTel = cractTel;
    }

    public String getCractTel() {
        return cractTel;
    }

    public void setCractEmail(String cractEmail) {
        this.cractEmail = cractEmail;
    }

    public String getCractEmail() {
        return cractEmail;
    }

    public void setCractRemarks(String cractRemarks) {
        this.cractRemarks = cractRemarks;
    }

    public String getCractRemarks() {
        return cractRemarks;
    }

    public CoreAccount() {
    }

//<=================定制内容开始==============
//==================定制内容结束==============>

}