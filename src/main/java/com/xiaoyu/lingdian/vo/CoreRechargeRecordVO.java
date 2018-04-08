package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreRechargeRecord;

import java.util.Date;

/**
 * 充值记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
@ApiModel(value = "充值记录")
public class CoreRechargeRecordVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crrerUuid;

    /**
     * 充值用户
     */
    @ApiModelProperty(value = "充值用户")
    private String crrerUser;

    /**
     * 充值用户持卡人姓名/公司名
     */
    @ApiModelProperty(value = "充值用户持卡人姓名/公司名")
    private String crrerUserName;

    /**
     * 充值用户手机号
     */
    @ApiModelProperty(value = "充值用户手机号")
    private String crrerUserMobile;

    /**
     * 充值状态:1成功,0失败,2申请充值
     */
    @ApiModelProperty(value = "充值状态:1成功,0失败,2申请充值")
    private Integer crrerStatus;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private Double crrerFee;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    private Double crrerPayFee;

    /**
     * 充值时间
     */
    @ApiModelProperty(value = "充值时间")
    private Date crrerDate;

    /**
     * 油卡卡号
     */
    @ApiModelProperty(value = "油卡卡号")
    private String crrerOilCode;

    /**
     * 是否带发票（1是，2否）
     */
    @ApiModelProperty(value = "是否带发票（1是，2否）")
    private Integer crrerInvoice;

    /**
     * 使用的优惠券合集
     */
    @ApiModelProperty(value = "使用的优惠券合集")
    private String crrerUseCoupon;

    /**
     * 可获得的优惠券
     */
    @ApiModelProperty(value = "可获得的优惠券")
    private String crrerGetCoupon;

    /**
     * 支付方式（1微信支付，2银联支付）
     */
    @ApiModelProperty(value = "支付方式（1微信支付，2银联支付）")
    private Integer crrerPayType;

    /**
     * 交易单号
     */
    @ApiModelProperty(value = "交易单号")
    private String crrerOrder;

    /**
     * 代理商平台(1本平台)
     */
    @ApiModelProperty(value = "代理商平台(1本平台)")
    private String crrerAgent;

    /**
     * 代理商回调url
     */
    @ApiModelProperty(value = "代理商回调url")
    private String crrerCallbackUrl;

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

    public String getCrrerUserName() {
        return crrerUserName;
    }

    public void setCrrerUserName(String crrerUserName) {
        this.crrerUserName = crrerUserName;
    }

    public String getCrrerUserMobile() {
        return crrerUserMobile;
    }

    public void setCrrerUserMobile(String crrerUserMobile) {
        this.crrerUserMobile = crrerUserMobile;
    }

    public Double getCrrerPayFee() {
        return crrerPayFee;
    }

    public void setCrrerPayFee(Double crrerPayFee) {
        this.crrerPayFee = crrerPayFee;
    }

    public String getCrrerOilCode() {
        return crrerOilCode;
    }

    public void setCrrerOilCode(String crrerOilCode) {
        this.crrerOilCode = crrerOilCode;
    }

    public CoreRechargeRecordVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreRechargeRecord po = (CoreRechargeRecord) poObj;
        this.crrerUuid = po.getCrrerUuid();
        this.crrerUser = po.getCrrerUser();
        this.crrerStatus = po.getCrrerStatus();
        this.crrerFee = po.getCrrerFee();
        this.crrerDate = po.getCrrerDate();
        this.crrerOilCode = po.getCrrerOilCode();
        this.crrerInvoice = po.getCrrerInvoice();
        this.crrerUseCoupon = po.getCrrerUseCoupon();
        this.crrerGetCoupon = po.getCrrerGetCoupon();
        this.crrerPayType = po.getCrrerPayType();
        this.crrerOrder = po.getCrrerOrder();
        this.crrerAgent = po.getCrrerAgent();
        this.crrerCallbackUrl = po.getCrrerCallbackUrl();
    }
}