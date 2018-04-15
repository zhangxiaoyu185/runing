package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiWeekStep;

import java.util.Date;

/**
 * 周步数表
 *
 * @author: zhangy
 * @since: 2018年04月11日 14:02:56
 * @history:
 */
@ApiModel(value = "周步数")
public class BusiWeekStepVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bswspUuid;

    /**
     * 所属用户
     */
    @ApiModelProperty(value = "所属用户")
    private String bswspUser;

    /**
     * 所属用户名称
     */
    @ApiModelProperty(value = "所属用户名称")
    private String bswspUserName;

    /**
     * 所属用户头像
     */
    @ApiModelProperty(value = "所属用户头像")
    private String bswspUserHead;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bswspCdate;

    /**
     * 所属周
     */
    @ApiModelProperty(value = "所属周")
    private String bswspWeek;

    /**
     * 所属月
     */
    @ApiModelProperty(value = "所属月")
    private String bswspMonth;

    /**
     * 步数
     */
    @ApiModelProperty(value = "步数")
    private Integer bswspStep;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值")
    private Integer bswspScore;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer bswspOrd;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer rank;

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

    public String getBswspUserHead() {
        return bswspUserHead;
    }

    public void setBswspUserHead(String bswspUserHead) {
        this.bswspUserHead = bswspUserHead;
    }

    public BusiWeekStepVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiWeekStep po = (BusiWeekStep) poObj;
        this.bswspUuid = po.getBswspUuid();
        this.bswspUser = po.getBswspUser();
        this.bswspCdate = po.getBswspCdate();
        this.bswspWeek = po.getBswspWeek();
        this.bswspMonth = po.getBswspMonth();
        this.bswspStep = po.getBswspStep();
        this.bswspScore = po.getBswspScore();
        this.bswspOrd = po.getBswspOrd();
        this.bswspUserName = po.getBswspUserName();
        this.rank = po.getRank();
    }
}