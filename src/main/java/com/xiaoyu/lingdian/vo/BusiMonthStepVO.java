package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiMonthStep;

import java.util.Date;

/**
 * 月步数表
 *
 * @author: zhangy
 * @since: 2018年04月11日 14:02:56
 * @history:
 */
@ApiModel(value = "月步数")
public class BusiMonthStepVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsmspUuid;

    /**
     * 所属用户
     */
    @ApiModelProperty(value = "所属用户")
    private String bsmspUser;

    /**
     * 所属用户名称
     */
    @ApiModelProperty(value = "所属用户名称")
    private String bsmspUserName;

    /**
     * 所属用户头像
     */
    @ApiModelProperty(value = "所属用户头像")
    private String bsmspUserHead;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsmspCdate;

    /**
     * 所属月
     */
    @ApiModelProperty(value = "所属月")
    private String bsmspMonth;

    /**
     * 步数
     */
    @ApiModelProperty(value = "步数")
    private Integer bsmspStep;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值")
    private Integer bsmspScore;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer bsmspOrd;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer rank;

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

    public String getBsmspUserHead() {
        return bsmspUserHead;
    }

    public void setBsmspUserHead(String bsmspUserHead) {
        this.bsmspUserHead = bsmspUserHead;
    }

    public BusiMonthStepVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiMonthStep po = (BusiMonthStep) poObj;
        this.bsmspUuid = po.getBsmspUuid();
        this.bsmspUser = po.getBsmspUser();
        this.bsmspCdate = po.getBsmspCdate();
        this.bsmspMonth = po.getBsmspMonth();
        this.bsmspStep = po.getBsmspStep();
        this.bsmspScore = po.getBsmspScore();
        this.bsmspOrd = po.getBsmspOrd();
        this.bsmspUserName = po.getBsmspUserName();
        this.rank = po.getRank();
    }
}