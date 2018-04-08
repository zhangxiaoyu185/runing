package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiCashRecord;

import java.util.Date;

/**
 * 用户提现记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:51
 * @history:
 */
@ApiModel(value = "用户提现记录")
public class BusiCashRecordVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bscrdUuid;

    /**
     * 提取金额
     */
    @ApiModelProperty(value = "提取金额")
    private String bscrdAmount;

    /**
     * 提取人
     */
    @ApiModelProperty(value = "提取人")
    private String bscrdExtracted;

    /**
     *提取人姓名
     */
    @ApiModelProperty(value = "提取人姓名")
    private String bscrdExtractedName;

    /**
     * 状态:1已申请2待打款3已打款4已驳回
     */
    @ApiModelProperty(value = "状态:1已申请2待打款3已打款4已驳回")
    private Integer bscrdStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bscrdCdate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date bscrdUdate;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String bscrdMobile;

    /**
     * 银行账户名称
     */
    @ApiModelProperty(value = "银行账户名称")
    private String bscrdAccountName;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    private String bscrdAccountNo;

    /**
     * 银行开户行
     */
    @ApiModelProperty(value = "银行开户行")
    private String bscrdBankName;

    /**
     * 银行开户网点
     */
    @ApiModelProperty(value = "银行开户网点")
    private String bscrdBankSite;

    public String getBscrdUuid() {
        return bscrdUuid;
    }

    public void setBscrdUuid(String bscrdUuid) {
        this.bscrdUuid = bscrdUuid;
    }

    public String getBscrdAmount() {
        return bscrdAmount;
    }

    public void setBscrdAmount(String bscrdAmount) {
        this.bscrdAmount = bscrdAmount;
    }

    public String getBscrdExtracted() {
        return bscrdExtracted;
    }

    public void setBscrdExtracted(String bscrdExtracted) {
        this.bscrdExtracted = bscrdExtracted;
    }

    public Integer getBscrdStatus() {
        return bscrdStatus;
    }

    public void setBscrdStatus(Integer bscrdStatus) {
        this.bscrdStatus = bscrdStatus;
    }

    public Date getBscrdCdate() {
        return bscrdCdate;
    }

    public void setBscrdCdate(Date bscrdCdate) {
        this.bscrdCdate = bscrdCdate;
    }

    public Date getBscrdUdate() {
        return bscrdUdate;
    }

    public void setBscrdUdate(Date bscrdUdate) {
        this.bscrdUdate = bscrdUdate;
    }

    public String getBscrdMobile() {
        return bscrdMobile;
    }

    public void setBscrdMobile(String bscrdMobile) {
        this.bscrdMobile = bscrdMobile;
    }

    public String getBscrdAccountName() {
        return bscrdAccountName;
    }

    public void setBscrdAccountName(String bscrdAccountName) {
        this.bscrdAccountName = bscrdAccountName;
    }

    public String getBscrdAccountNo() {
        return bscrdAccountNo;
    }

    public void setBscrdAccountNo(String bscrdAccountNo) {
        this.bscrdAccountNo = bscrdAccountNo;
    }

    public String getBscrdBankName() {
        return bscrdBankName;
    }

    public void setBscrdBankName(String bscrdBankName) {
        this.bscrdBankName = bscrdBankName;
    }

    public String getBscrdBankSite() {
        return bscrdBankSite;
    }

    public void setBscrdBankSite(String bscrdBankSite) {
        this.bscrdBankSite = bscrdBankSite;
    }

    public String getBscrdExtractedName() {
        return bscrdExtractedName;
    }

    public void setBscrdExtractedName(String bscrdExtractedName) {
        this.bscrdExtractedName = bscrdExtractedName;
    }

    public BusiCashRecordVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiCashRecord po = (BusiCashRecord) poObj;
        this.bscrdUuid = po.getBscrdUuid();
        this.bscrdAmount = po.getBscrdAmount();
        this.bscrdExtracted = po.getBscrdExtracted();
        this.bscrdStatus = po.getBscrdStatus();
        this.bscrdCdate = po.getBscrdCdate();
        this.bscrdUdate = po.getBscrdUdate();
        this.bscrdMobile = po.getBscrdMobile();
        this.bscrdAccountName = po.getBscrdAccountName();
        this.bscrdAccountNo = po.getBscrdAccountNo();
        this.bscrdBankName = po.getBscrdBankName();
        this.bscrdBankSite = po.getBscrdBankSite();
    }
}