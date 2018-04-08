package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiAgentPay;

import java.util.Date;

/**
 * 代理商打款表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:51
 * @history:
 */
@ApiModel(value = "代理商打款")
public class BusiAgentPayVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsapyUuid;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private Double bsapyFee;

    /**
     * 代理商标识
     */
    @ApiModelProperty(value = "代理商标识")
    private String bsapyAgent;

    /**
     * 代理商名称
     */
    @ApiModelProperty(value = "代理商名称")
    private String bsapyAgentCode;

    /**
     * 线下汇款单号
     */
    @ApiModelProperty(value = "线下汇款单号")
    private String bsapyOrder;

    /**
     * 线下汇款单照片base64
     */
    @ApiModelProperty(value = "线下汇款单照片base64")
    private String bsapyPic;

    /**
     * 操作财务姓名
     */
    @ApiModelProperty(value = "操作财务姓名")
    private String bsapyFinance;

    /**
     * 状态:1申请中2已驳回3成功
     */
    @ApiModelProperty(value = "状态:1申请中2已驳回3成功")
    private Integer bsapyStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsapyCdate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date  bsapyUdate;

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

    public String getBsapyAgentCode() {
        return bsapyAgentCode;
    }

    public void setBsapyAgentCode(String bsapyAgentCode) {
        this.bsapyAgentCode = bsapyAgentCode;
    }

    public BusiAgentPayVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiAgentPay po = (BusiAgentPay) poObj;
        this.bsapyUuid = po.getBsapyUuid();
        this.bsapyFee = po.getBsapyFee();
        this.bsapyAgent = po.getBsapyAgent();
        this.bsapyOrder = po.getBsapyOrder();
        this.bsapyPic = po.getBsapyPic();
        this.bsapyFinance = po.getBsapyFinance();
        this.bsapyStatus = po.getBsapyStatus();
        this.bsapyCdate = po.getBsapyCdate();
        this.bsapyUdate = po.getBsapyUdate();
    }
}