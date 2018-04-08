package com.xiaoyu.lingdian.entity;


/**
 * 代理商表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:50
 * @history:
 */
public class BusiAgent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsaetUnid;

    /**
     * 标识UUID
     */
    private String bsaetUuid;

    /**
     * 余额
     */
    private Double bsaetFee;

    /**
     * 代理商名称
     */
    private String bsaetCode;

    /**
     * 代理商密钥
     */
    private String bsaetPwd;

    /**
     * 状态:1启用,0禁用
     */
    private Integer bsaetStatus;

    public Integer getBsaetUnid() {
        return bsaetUnid;
    }

    public void setBsaetUnid(Integer bsaetUnid) {
        this.bsaetUnid = bsaetUnid;
    }

    public String getBsaetUuid() {
        return bsaetUuid;
    }

    public void setBsaetUuid(String bsaetUuid) {
        this.bsaetUuid = bsaetUuid;
    }

    public Double getBsaetFee() {
        return bsaetFee;
    }

    public void setBsaetFee(Double bsaetFee) {
        this.bsaetFee = bsaetFee;
    }

    public String getBsaetCode() {
        return bsaetCode;
    }

    public void setBsaetCode(String bsaetCode) {
        this.bsaetCode = bsaetCode;
    }

    public String getBsaetPwd() {
        return bsaetPwd;
    }

    public void setBsaetPwd(String bsaetPwd) {
        this.bsaetPwd = bsaetPwd;
    }

    public Integer getBsaetStatus() {
        return bsaetStatus;
    }

    public void setBsaetStatus(Integer bsaetStatus) {
        this.bsaetStatus = bsaetStatus;
    }

    public BusiAgent() {
    }

}