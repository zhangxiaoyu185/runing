package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiOrderPay;

import java.util.Date;

/**
 * 支付记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:52
 * @history:
 */
@ApiModel(value = "支付记录")
public class BusiOrderPayVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsopyUuid;

    /**
     * 交易单号
     */
    @ApiModelProperty(value = "交易单号")
    private String bsopyOrder;

    /**
     * 客户端地址
     */
    @ApiModelProperty(value = "客户端地址")
    private String bsopyClientIp;

    /**
     * 支付渠道
     */
    @ApiModelProperty(value = "支付渠道")
    private String bsopyPayChannel;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private String bsopyTradeType;

    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    private String bsopyUser;

    /**
     * 同步通知地址
     */
    @ApiModelProperty(value = "同步通知地址")
    private String bsopyReturnUrl;

    /**
     * 支付返回参数（返回用于前端页面支付参数）
     */
    @ApiModelProperty(value = "支付返回参数（返回用于前端页面支付参数）")
    private String bsopyPayParams;

    /**
     * 业务结果
     */
    @ApiModelProperty(value = "业务结果")
    private String bsopyResultCode;

    /**
     * 错误代码
     */
    @ApiModelProperty(value = "错误代码")
    private String bsopyErrorCode;

    /**
     * 错误描述
     */
    @ApiModelProperty(value = "错误描述")
    private String bsopyErrorMsg;

    /**
     * 第三方单号
     */
    @ApiModelProperty(value = "第三方单号")
    private String bsopyOutTradeNo;

    /**
     * 支付结果
     */
    @ApiModelProperty(value = "支付结果")
    private String bsopyPayResult;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date bsopyPayDate;

    /**
     * 通知时间
     */
    @ApiModelProperty(value = "通知时间")
    private Date bsopyNotifyDate;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsopyCdate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date bsopyUdate;

    public String getBsopyUuid() {
        return bsopyUuid;
    }

    public void setBsopyUuid(String bsopyUuid) {
        this.bsopyUuid = bsopyUuid;
    }

    public String getBsopyOrder() {
        return bsopyOrder;
    }

    public void setBsopyOrder(String bsopyOrder) {
        this.bsopyOrder = bsopyOrder;
    }

    public String getBsopyClientIp() {
        return bsopyClientIp;
    }

    public void setBsopyClientIp(String bsopyClientIp) {
        this.bsopyClientIp = bsopyClientIp;
    }

    public String getBsopyPayChannel() {
        return bsopyPayChannel;
    }

    public void setBsopyPayChannel(String bsopyPayChannel) {
        this.bsopyPayChannel = bsopyPayChannel;
    }

    public String getBsopyTradeType() {
        return bsopyTradeType;
    }

    public void setBsopyTradeType(String bsopyTradeType) {
        this.bsopyTradeType = bsopyTradeType;
    }

    public String getBsopyUser() {
        return bsopyUser;
    }

    public void setBsopyUser(String bsopyUser) {
        this.bsopyUser = bsopyUser;
    }

    public String getBsopyReturnUrl() {
        return bsopyReturnUrl;
    }

    public void setBsopyReturnUrl(String bsopyReturnUrl) {
        this.bsopyReturnUrl = bsopyReturnUrl;
    }

    public String getBsopyPayParams() {
        return bsopyPayParams;
    }

    public void setBsopyPayParams(String bsopyPayParams) {
        this.bsopyPayParams = bsopyPayParams;
    }

    public String getBsopyResultCode() {
        return bsopyResultCode;
    }

    public void setBsopyResultCode(String bsopyResultCode) {
        this.bsopyResultCode = bsopyResultCode;
    }

    public String getBsopyErrorCode() {
        return bsopyErrorCode;
    }

    public void setBsopyErrorCode(String bsopyErrorCode) {
        this.bsopyErrorCode = bsopyErrorCode;
    }

    public String getBsopyErrorMsg() {
        return bsopyErrorMsg;
    }

    public void setBsopyErrorMsg(String bsopyErrorMsg) {
        this.bsopyErrorMsg = bsopyErrorMsg;
    }

    public String getBsopyOutTradeNo() {
        return bsopyOutTradeNo;
    }

    public void setBsopyOutTradeNo(String bsopyOutTradeNo) {
        this.bsopyOutTradeNo = bsopyOutTradeNo;
    }

    public String getBsopyPayResult() {
        return bsopyPayResult;
    }

    public void setBsopyPayResult(String bsopyPayResult) {
        this.bsopyPayResult = bsopyPayResult;
    }

    public Date getBsopyPayDate() {
        return bsopyPayDate;
    }

    public void setBsopyPayDate(Date bsopyPayDate) {
        this.bsopyPayDate = bsopyPayDate;
    }

    public Date getBsopyNotifyDate() {
        return bsopyNotifyDate;
    }

    public void setBsopyNotifyDate(Date bsopyNotifyDate) {
        this.bsopyNotifyDate = bsopyNotifyDate;
    }

    public Date getBsopyCdate() {
        return bsopyCdate;
    }

    public void setBsopyCdate(Date bsopyCdate) {
        this.bsopyCdate = bsopyCdate;
    }

    public Date getBsopyUdate() {
        return bsopyUdate;
    }

    public void setBsopyUdate(Date bsopyUdate) {
        this.bsopyUdate = bsopyUdate;
    }

    public BusiOrderPayVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiOrderPay po = (BusiOrderPay) poObj;
        this.bsopyUuid = po.getBsopyUuid();
        this.bsopyOrder = po.getBsopyOrder();
        this.bsopyClientIp = po.getBsopyClientIp();
        this.bsopyPayChannel = po.getBsopyPayChannel();
        this.bsopyTradeType = po.getBsopyTradeType();
        this.bsopyUser = po.getBsopyUser();
        this.bsopyReturnUrl = po.getBsopyReturnUrl();
        this.bsopyPayParams = po.getBsopyPayParams();
        this.bsopyResultCode = po.getBsopyResultCode();
        this.bsopyErrorCode = po.getBsopyErrorCode();
        this.bsopyErrorMsg = po.getBsopyErrorMsg();
        this.bsopyOutTradeNo = po.getBsopyOutTradeNo();
        this.bsopyPayResult = po.getBsopyPayResult();
        this.bsopyPayDate = po.getBsopyPayDate();
        this.bsopyNotifyDate = po.getBsopyNotifyDate();
        this.bsopyCdate = po.getBsopyCdate();
        this.bsopyUdate = po.getBsopyUdate();
    }
}