package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreCommissionRecord;

import java.util.Date;

/**
 * 佣金记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
@ApiModel(value = "佣金记录")
public class CoreCommissionRecordVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crcrdUuid;

    /**
     * 充值用户
     */
    @ApiModelProperty(value = "充值用户")
    private String crcrdUser;

    /**
     * 充值用户持卡人姓名/公司名
     */
    @ApiModelProperty(value = "充值用户持卡人姓名/公司名")
    private String crcrdUserName;

    /**
     * 充值用户头像
     */
    @ApiModelProperty(value = "充值用户头像")
    private String crcrdUserHead;

    /**
     * 充值用户手机号
     */
    @ApiModelProperty(value = "充值用户手机号")
    private String crcrdUserMobile;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private Double crcrdFee;

    /**
     * 充值时间
     */
    @ApiModelProperty(value = "充值时间")
    private Date crcrdDate;

    /**
     * 佣金用户
     */
    @ApiModelProperty(value = "佣金用户")
    private String crcrdBeUser;

    /**
     * 佣金用户持卡人姓名/公司名
     */
    @ApiModelProperty(value = "佣金用户持卡人姓名/公司名")
    private String crcrdBeUserName;

    /**
     * 佣金用户手机号
     */
    @ApiModelProperty(value = "佣金用户手机号")
    private String crcrdBeUserMobile;

    /**
     * 佣金用户头像
     */
    @ApiModelProperty(value = "佣金用户头像")
    private String crcrdBeUserHead;

    /**
     * 产生佣金金额
     */
    @ApiModelProperty(value = "产生佣金金额")
    private Double crcrdBeFee;

    /**
     * 佣金比
     */
    @ApiModelProperty(value = "佣金比")
    private Double crcrdRatio;

    /**
     * 分销等级（1一级，2二级）
     */
    @ApiModelProperty(value = "分销等级（1一级，2二级）")
    private Integer crcrdLevel;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期[成为下线时间]")
    private Date crusrCdate;

    public String getCrcrdUuid() {
        return crcrdUuid;
    }

    public void setCrcrdUuid(String crcrdUuid) {
        this.crcrdUuid = crcrdUuid;
    }

    public String getCrcrdUser() {
        return crcrdUser;
    }

    public void setCrcrdUser(String crcrdUser) {
        this.crcrdUser = crcrdUser;
    }

    public Double getCrcrdFee() {
        return crcrdFee;
    }

    public void setCrcrdFee(Double crcrdFee) {
        this.crcrdFee = crcrdFee;
    }

    public Date getCrcrdDate() {
        return crcrdDate;
    }

    public void setCrcrdDate(Date crcrdDate) {
        this.crcrdDate = crcrdDate;
    }

    public String getCrcrdBeUser() {
        return crcrdBeUser;
    }

    public void setCrcrdBeUser(String crcrdBeUser) {
        this.crcrdBeUser = crcrdBeUser;
    }

    public Double getCrcrdBeFee() {
        return crcrdBeFee;
    }

    public void setCrcrdBeFee(Double crcrdBeFee) {
        this.crcrdBeFee = crcrdBeFee;
    }

    public Double getCrcrdRatio() {
        return crcrdRatio;
    }

    public void setCrcrdRatio(Double crcrdRatio) {
        this.crcrdRatio = crcrdRatio;
    }

    public Integer getCrcrdLevel() {
        return crcrdLevel;
    }

    public void setCrcrdLevel(Integer crcrdLevel) {
        this.crcrdLevel = crcrdLevel;
    }

    public String getCrcrdUserName() {
        return crcrdUserName;
    }

    public void setCrcrdUserName(String crcrdUserName) {
        this.crcrdUserName = crcrdUserName;
    }

    public String getCrcrdUserHead() {
        return crcrdUserHead;
    }

    public void setCrcrdUserHead(String crcrdUserHead) {
        this.crcrdUserHead = crcrdUserHead;
    }

    public String getCrcrdUserMobile() {
        return crcrdUserMobile;
    }

    public void setCrcrdUserMobile(String crcrdUserMobile) {
        this.crcrdUserMobile = crcrdUserMobile;
    }

    public String getCrcrdBeUserName() {
        return crcrdBeUserName;
    }

    public void setCrcrdBeUserName(String crcrdBeUserName) {
        this.crcrdBeUserName = crcrdBeUserName;
    }

    public String getCrcrdBeUserMobile() {
        return crcrdBeUserMobile;
    }

    public void setCrcrdBeUserMobile(String crcrdBeUserMobile) {
        this.crcrdBeUserMobile = crcrdBeUserMobile;
    }

    public String getCrcrdBeUserHead() {
        return crcrdBeUserHead;
    }

    public void setCrcrdBeUserHead(String crcrdBeUserHead) {
        this.crcrdBeUserHead = crcrdBeUserHead;
    }

    public Date getCrusrCdate() {
        return crusrCdate;
    }

    public void setCrusrCdate(Date crusrCdate) {
        this.crusrCdate = crusrCdate;
    }

    public CoreCommissionRecordVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreCommissionRecord po = (CoreCommissionRecord) poObj;
        this.crcrdUuid = po.getCrcrdUuid();
        this.crcrdUser = po.getCrcrdUser();
        this.crcrdFee = po.getCrcrdFee();
        this.crcrdDate = po.getCrcrdDate();
        this.crcrdBeUser = po.getCrcrdBeUser();
        this.crcrdBeFee = po.getCrcrdBeFee();
        this.crcrdRatio = po.getCrcrdRatio();
        this.crcrdLevel = po.getCrcrdLevel();
    }
}