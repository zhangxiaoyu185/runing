package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 佣金记录表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
public class CoreCommissionRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crcrdUnid;

    /**
     * 标识UUID
     */
    private String crcrdUuid;

    /**
     * 充值用户
     */
    private String crcrdUser;

    /**
     * 充值金额
     */
    private Double crcrdFee;

    /**
     * 充值时间
     */
    private Date crcrdDate;

    /**
     * 佣金用户
     */
    private String crcrdBeUser;

    /**
     * 产生佣金金额
     */
    private Double crcrdBeFee;

    /**
     * 佣金比
     */
    private Double crcrdRatio;

    /**
     * 分销等级（1一级，2二级）
     */
    private Integer crcrdLevel;

    public Integer getCrcrdUnid() {
        return crcrdUnid;
    }

    public void setCrcrdUnid(Integer crcrdUnid) {
        this.crcrdUnid = crcrdUnid;
    }

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

    public CoreCommissionRecord() {
    }

}