package com.xiaoyu.lingdian.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 月步数表
 *
 * @author: zhangy
 * @since: 2018年04月11日 13:52:43
 * @history:
 */
public class BusiMonthStep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsmspUnid;

    /**
     * 标识UUID
     */
    private String bsmspUuid;

    /**
     * 所属用户
     */
    private String bsmspUser;

    /**
     * 所属用户名称
     */
    private String bsmspUserName;

    /**
     * 创建时间
     */
    private Date bsmspCdate;

    /**
     * 所属月
     */
    private String bsmspMonth;

    /**
     * 步数
     */
    private Integer bsmspStep;

    /**
     * 分值
     */
    private Integer bsmspScore;

    /**
     * 序号
     */
    private Integer bsmspOrd;

    /**
     * 排序号
     */
    private Integer rank;

    public Integer getBsmspUnid() {
        return bsmspUnid;
    }

    public void setBsmspUnid(Integer bsmspUnid) {
        this.bsmspUnid = bsmspUnid;
    }

    public String getBsmspUuid() {
        return bsmspUuid;
    }

    public void setBsmspUuid(String bsmspUuid) {
        this.bsmspUuid = bsmspUuid;
    }

    public String getBsmspUser() {
        return bsmspUser;
    }

    public void setBsmspUser(String bsmspUser) {
        this.bsmspUser = bsmspUser;
    }

    public Date getBsmspCdate() {
        return bsmspCdate;
    }

    public void setBsmspCdate(Date bsmspCdate) {
        this.bsmspCdate = bsmspCdate;
    }

    public String getBsmspMonth() {
        return bsmspMonth;
    }

    public void setBsmspMonth(String bsmspMonth) {
        this.bsmspMonth = bsmspMonth;
    }

    public Integer getBsmspStep() {
        return bsmspStep;
    }

    public void setBsmspStep(Integer bsmspStep) {
        this.bsmspStep = bsmspStep;
    }

    public Integer getBsmspScore() {
        return bsmspScore;
    }

    public void setBsmspScore(Integer bsmspScore) {
        this.bsmspScore = bsmspScore;
    }

    public Integer getBsmspOrd() {
        return bsmspOrd;
    }

    public void setBsmspOrd(Integer bsmspOrd) {
        this.bsmspOrd = bsmspOrd;
    }

    public String getBsmspUserName() {
        return bsmspUserName;
    }

    public void setBsmspUserName(String bsmspUserName) {
        this.bsmspUserName = bsmspUserName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public BusiMonthStep() {
    }

}