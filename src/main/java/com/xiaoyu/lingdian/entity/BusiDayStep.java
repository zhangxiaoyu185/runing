package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 日步数表
 *
 * @author: zhangy
 * @since: 2018年04月09日 16:57:32
 * @history:
 */
public class BusiDayStep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsdspUnid;

    /**
     * 标识UUID
     */
    private String bsdspUuid;

    /**
     * 所属用户
     */
    private String bsdspUser;

    /**
     * 所属用户名称
     */
    private String bsdspUserName;

    /**
     * 创建时间
     */
    private Date bsdspCdate;

    /**
     * 所属日期
     */
    private String bsdspDay;

    /**
     * 步数
     */
    private Integer bsdspStep;

    /**
     * 排序号
     */
    private Integer rank;

    public Integer getBsdspUnid() {
        return bsdspUnid;
    }

    public void setBsdspUnid(Integer bsdspUnid) {
        this.bsdspUnid = bsdspUnid;
    }

    public String getBsdspUuid() {
        return bsdspUuid;
    }

    public void setBsdspUuid(String bsdspUuid) {
        this.bsdspUuid = bsdspUuid;
    }

    public String getBsdspUser() {
        return bsdspUser;
    }

    public void setBsdspUser(String bsdspUser) {
        this.bsdspUser = bsdspUser;
    }

    public Date getBsdspCdate() {
        return bsdspCdate;
    }

    public void setBsdspCdate(Date bsdspCdate) {
        this.bsdspCdate = bsdspCdate;
    }

    public String getBsdspDay() {
        return bsdspDay;
    }

    public void setBsdspDay(String bsdspDay) {
        this.bsdspDay = bsdspDay;
    }

    public Integer getBsdspStep() {
        return bsdspStep;
    }

    public void setBsdspStep(Integer bsdspStep) {
        this.bsdspStep = bsdspStep;
    }

    public String getBsdspUserName() {
        return bsdspUserName;
    }

    public void setBsdspUserName(String bsdspUserName) {
        this.bsdspUserName = bsdspUserName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public BusiDayStep() {
    }

}