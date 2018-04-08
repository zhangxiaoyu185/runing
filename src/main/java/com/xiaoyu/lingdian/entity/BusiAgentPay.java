package com.xiaoyu.lingdian.entity;


import java.util.Date;

/**
 * 代理商打款表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:51
 * @history:
 */
public class BusiAgentPay extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsapyUnid;

    /**
     * 标识UUID
     */
    private String bsapyUuid;

    /**
     * 充值金额
     */
    private Double bsapyFee;

    /**
     * 代理商标识
     */
    private String bsapyAgent;

    /**
     * 线下汇款单号
     */
    private String bsapyOrder;

    /**
     * 线下汇款单照片base64
     */
    private String bsapyPic;

    /**
     * 操作财务姓名
     */
    private String bsapyFinance;

    /**
     * 状态:1申请中2已驳回3成功
     */
    private Integer bsapyStatus;

    /**
     * 创建时间
     */
    private Date bsapyCdate;

    /**
     * 更新时间
     */
    private Date  bsapyUdate;

    public Integer getBsapyUnid() {
        return bsapyUnid;
    }

    public void setBsapyUnid(Integer bsapyUnid) {
        this.bsapyUnid = bsapyUnid;
    }

    public String getBsapyUuid() {
        return bsapyUuid;
    }

    public void setBsapyUuid(String bsapyUuid) {
        this.bsapyUuid = bsapyUuid;
    }

    public Double getBsapyFee() {
        return bsapyFee;
    }

    public void setBsapyFee(Double bsapyFee) {
        this.bsapyFee = bsapyFee;
    }

    public String getBsapyAgent() {
        return bsapyAgent;
    }

    public void setBsapyAgent(String bsapyAgent) {
        this.bsapyAgent = bsapyAgent;
    }

    public String getBsapyOrder() {
        return bsapyOrder;
    }

    public void setBsapyOrder(String bsapyOrder) {
        this.bsapyOrder = bsapyOrder;
    }

    public String getBsapyPic() {
        return bsapyPic;
    }

    public void setBsapyPic(String bsapyPic) {
        this.bsapyPic = bsapyPic;
    }

    public String getBsapyFinance() {
        return bsapyFinance;
    }

    public void setBsapyFinance(String bsapyFinance) {
        this.bsapyFinance = bsapyFinance;
    }

    public Integer getBsapyStatus() {
        return bsapyStatus;
    }

    public void setBsapyStatus(Integer bsapyStatus) {
        this.bsapyStatus = bsapyStatus;
    }

    public Date getBsapyCdate() {
        return bsapyCdate;
    }

    public void setBsapyCdate(Date bsapyCdate) {
        this.bsapyCdate = bsapyCdate;
    }

    public Date getBsapyUdate() {
        return bsapyUdate;
    }

    public void setBsapyUdate(Date bsapyUdate) {
        this.bsapyUdate = bsapyUdate;
    }

    public BusiAgentPay() {
    }

}