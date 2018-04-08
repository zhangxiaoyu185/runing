package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 充值记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
public class CoreRechargeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crrerUnid;

    /**
     * 标识UUID
     */
    private String crrerUuid;

    /**
     * 充值用户
     */
    private String crrerUser;

    /**
     * 充值状态:1成功,0失败,2申请充值
     */
    private Integer crrerStatus;

    /**
     * 充值原金额
     */
    private Double crrerFee;

    /**
     * 实际支付金额
     */
    private Double crrerPayFee;

    /**
     * 充值时间
     */
    private Date crrerDate;

    /**
     * 油卡卡号
     */
    private String crrerOilCode;

    /**
     * 是否带发票（1是，2否）
     */
    private Integer crrerInvoice;

    /**
     * 使用的优惠券合集
     */
    private String crrerUseCoupon;

    /**
     * 可获得的优惠券
     */
    private String crrerGetCoupon;

    /**
     * 支付方式（1微信支付，2银联支付）
     */
    private Integer crrerPayType;

    /**
     * 交易单号
     */
    private String crrerOrder;

    /**
     * 代理商平台(1本平台)
     */
    private String crrerAgent;

    /**
     * 代理商回调url
     */
    private String crrerCallbackUrl;

    public Integer getCrrerUnid() {
        return crrerUnid;
    }

    public void setCrrerUnid(Integer crrerUnid) {
        this.crrerUnid = crrerUnid;
    }

    public String getCrrerUuid() {
        return crrerUuid;
    }

    public void setCrrerUuid(String crrerUuid) {
        this.crrerUuid = crrerUuid;
    }

    public String getCrrerUser() {
        return crrerUser;
    }

    public void setCrrerUser(String crrerUser) {
        this.crrerUser = crrerUser;
    }

    public Integer getCrrerStatus() {
        return crrerStatus;
    }

    public void setCrrerStatus(Integer crrerStatus) {
        this.crrerStatus = crrerStatus;
    }

    public Double getCrrerFee() {
        return crrerFee;
    }

    public void setCrrerFee(Double crrerFee) {
        this.crrerFee = crrerFee;
    }

    public Date getCrrerDate() {
        return crrerDate;
    }

    public void setCrrerDate(Date crrerDate) {
        this.crrerDate = crrerDate;
    }

    public String getCrrerOilCode() {
        return crrerOilCode;
    }

    public void setCrrerOilCode(String crrerOilCode) {
        this.crrerOilCode = crrerOilCode;
    }

    public Integer getCrrerInvoice() {
        return crrerInvoice;
    }

    public void setCrrerInvoice(Integer crrerInvoice) {
        this.crrerInvoice = crrerInvoice;
    }

    public String getCrrerUseCoupon() {
        return crrerUseCoupon;
    }

    public void setCrrerUseCoupon(String crrerUseCoupon) {
        this.crrerUseCoupon = crrerUseCoupon;
    }

    public String getCrrerGetCoupon() {
        return crrerGetCoupon;
    }

    public void setCrrerGetCoupon(String crrerGetCoupon) {
        this.crrerGetCoupon = crrerGetCoupon;
    }

    public Integer getCrrerPayType() {
        return crrerPayType;
    }

    public void setCrrerPayType(Integer crrerPayType) {
        this.crrerPayType = crrerPayType;
    }

    public String getCrrerOrder() {
        return crrerOrder;
    }

    public void setCrrerOrder(String crrerOrder) {
        this.crrerOrder = crrerOrder;
    }

    public String getCrrerAgent() {
        return crrerAgent;
    }

    public void setCrrerAgent(String crrerAgent) {
        this.crrerAgent = crrerAgent;
    }

    public String getCrrerCallbackUrl() {
        return crrerCallbackUrl;
    }

    public void setCrrerCallbackUrl(String crrerCallbackUrl) {
        this.crrerCallbackUrl = crrerCallbackUrl;
    }

    public Double getCrrerPayFee() {
        return crrerPayFee;
    }

    public void setCrrerPayFee(Double crrerPayFee) {
        this.crrerPayFee = crrerPayFee;
    }

    public CoreRechargeRecord() {
    }

}