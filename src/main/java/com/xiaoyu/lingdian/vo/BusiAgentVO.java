package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiAgent;

/**
 * 代理商表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:50
 * @history:
 */
@ApiModel(value = "代理商")
public class BusiAgentVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsaetUuid;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private Double bsaetFee;

    /**
     * 代理商名称
     */
    @ApiModelProperty(value = "代理商名称")
    private String bsaetCode;

    /**
     * 代理商密钥
     */
    @ApiModelProperty(value = "代理商密钥")
    private String bsaetPwd;

    /**
     * 状态:1启用,0禁用
     */
    @ApiModelProperty(value = "状态:1启用,0禁用")
    private Integer bsaetStatus;

    public String getBsaetUuid() {
        return bsaetUuid;
    }

    public void setBsaetUuid(String bsaetUuid) {
        this.bsaetUuid = bsaetUuid;
    }

    public Double getBsaetFee() {
        return bsaetFee;
    }

    public void setBsaetFee(Double bsaetFee) {
        this.bsaetFee = bsaetFee;
    }

    public String getBsaetCode() {
        return bsaetCode;
    }

    public void setBsaetCode(String bsaetCode) {
        this.bsaetCode = bsaetCode;
    }

    public String getBsaetPwd() {
        return bsaetPwd;
    }

    public void setBsaetPwd(String bsaetPwd) {
        this.bsaetPwd = bsaetPwd;
    }

    public Integer getBsaetStatus() {
        return bsaetStatus;
    }

    public void setBsaetStatus(Integer bsaetStatus) {
        this.bsaetStatus = bsaetStatus;
    }

    public BusiAgentVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiAgent po = (BusiAgent) poObj;
        this.bsaetUuid = po.getBsaetUuid();
        this.bsaetFee = po.getBsaetFee();
        this.bsaetCode = po.getBsaetCode();
        this.bsaetPwd = po.getBsaetPwd();
        this.bsaetStatus = po.getBsaetStatus();
    }
}