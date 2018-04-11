package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 周步数表
 *
 * @author: zhangy
 * @since: 2018年04月11日 13:52:43
 * @history:
 */
public class BusiWeekStep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bswspUnid;

    /**
     * 标识UUID
     */
    private String bswspUuid;

    /**
     * 所属用户
     */
    private String bswspUser;

    /**
     * 所属用户昵称
     */
    private String bswspUserName;

    /**
     * 创建时间
     */
    private Date bswspCdate;

    /**
     * 所属周
     */
    private String bswspWeek;

    /**
     * 所属月
     */
    private String bswspMonth;

    /**
     * 步数
     */
    private Integer bswspStep;

    /**
     * 分值
     */
    private Integer bswspScore;

    /**
     * 序号
     */
    private Integer bswspOrd;

    /**
     * 排序号
     */
    private Integer rank;

    public Integer getBswspUnid() {
        return bswspUnid;
    }

    public void setBswspUnid(Integer bswspUnid) {
        this.bswspUnid = bswspUnid;
    }

    public String getBswspUuid() {
        return bswspUuid;
    }

    public void setBswspUuid(String bswspUuid) {
        this.bswspUuid = bswspUuid;
    }

    public String getBswspUser() {
        return bswspUser;
    }

    public void setBswspUser(String bswspUser) {
        this.bswspUser = bswspUser;
    }

    public Date getBswspCdate() {
        return bswspCdate;
    }

    public void setBswspCdate(Date bswspCdate) {
        this.bswspCdate = bswspCdate;
    }

    public String getBswspWeek() {
        return bswspWeek;
    }

    public void setBswspWeek(String bswspWeek) {
        this.bswspWeek = bswspWeek;
    }

    public String getBswspMonth() {
        return bswspMonth;
    }

    public void setBswspMonth(String bswspMonth) {
        this.bswspMonth = bswspMonth;
    }

    public Integer getBswspStep() {
        return bswspStep;
    }

    public void setBswspStep(Integer bswspStep) {
        this.bswspStep = bswspStep;
    }

    public Integer getBswspScore() {
        return bswspScore;
    }

    public void setBswspScore(Integer bswspScore) {
        this.bswspScore = bswspScore;
    }

    public Integer getBswspOrd() {
        return bswspOrd;
    }

    public void setBswspOrd(Integer bswspOrd) {
        this.bswspOrd = bswspOrd;
    }

    public String getBswspUserName() {
        return bswspUserName;
    }

    public void setBswspUserName(String bswspUserName) {
        this.bswspUserName = bswspUserName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public BusiWeekStep() {
    }

}