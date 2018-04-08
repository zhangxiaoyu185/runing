package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiBanner;

import java.util.Date;

/**
 * 首页Banner表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:51
 * @history:
 */
@ApiModel(value = "首页Banner")
public class BusiBannerVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsbarUuid;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private String bsbarLink;

    /**
     * BANNER图
     */
    @ApiModelProperty(value = "BANNER图")
    private String bsbarPic;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer bsbarOrd;

    /**
     * 状态:1启动0禁用
     */
    @ApiModelProperty(value = "状态:1启动0禁用")
    private Integer bsbarStatus;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String bsbarDesc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsbarCdate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date bsbarUdate;

    public String getBsbarUuid() {
        return bsbarUuid;
    }

    public void setBsbarUuid(String bsbarUuid) {
        this.bsbarUuid = bsbarUuid;
    }

    public String getBsbarLink() {
        return bsbarLink;
    }

    public void setBsbarLink(String bsbarLink) {
        this.bsbarLink = bsbarLink;
    }

    public String getBsbarPic() {
        return bsbarPic;
    }

    public void setBsbarPic(String bsbarPic) {
        this.bsbarPic = bsbarPic;
    }

    public Integer getBsbarOrd() {
        return bsbarOrd;
    }

    public void setBsbarOrd(Integer bsbarOrd) {
        this.bsbarOrd = bsbarOrd;
    }

    public Integer getBsbarStatus() {
        return bsbarStatus;
    }

    public void setBsbarStatus(Integer bsbarStatus) {
        this.bsbarStatus = bsbarStatus;
    }

    public String getBsbarDesc() {
        return bsbarDesc;
    }

    public void setBsbarDesc(String bsbarDesc) {
        this.bsbarDesc = bsbarDesc;
    }

    public Date getBsbarCdate() {
        return bsbarCdate;
    }

    public void setBsbarCdate(Date bsbarCdate) {
        this.bsbarCdate = bsbarCdate;
    }

    public Date getBsbarUdate() {
        return bsbarUdate;
    }

    public void setBsbarUdate(Date bsbarUdate) {
        this.bsbarUdate = bsbarUdate;
    }

    public BusiBannerVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiBanner po = (BusiBanner) poObj;
        this.bsbarUuid = po.getBsbarUuid();
        this.bsbarLink = po.getBsbarLink();
        this.bsbarPic = po.getBsbarPic();
        this.bsbarOrd = po.getBsbarOrd();
        this.bsbarStatus = po.getBsbarStatus();
        this.bsbarDesc = po.getBsbarDesc();
        this.bsbarCdate = po.getBsbarCdate();
        this.bsbarUdate = po.getBsbarUdate();
    }
}