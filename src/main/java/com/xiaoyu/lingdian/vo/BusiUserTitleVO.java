package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiUserTitle;

import java.util.Date;

/**
 * 个人称号记录表
 *
 * @author: zhangy
 * @since: 2018年04月09日 16:57:33
 * @history:
 */
@ApiModel(value = "个人称号记录")
public class BusiUserTitleVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsuteUuid;

    /**
     * 所属用户
     */
    @ApiModelProperty(value = "所属用户")
    private String bsuteUser;

    /**
     * 所属用户名称
     */
    @ApiModelProperty(value = "所属用户名称")
    private String bsuteUserName;

    /**
     * 所属月
     */
    @ApiModelProperty(value = "所属月")
    private String bsuteMonth;

    /**
     * 获得称号
     */
    @ApiModelProperty(value = "获得称号")
    private String bsuteTitle;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsuteCdate;

    public String getBsuteUuid() {
        return bsuteUuid;
    }

    public void setBsuteUuid(String bsuteUuid) {
        this.bsuteUuid = bsuteUuid;
    }

    public String getBsuteUser() {
        return bsuteUser;
    }

    public void setBsuteUser(String bsuteUser) {
        this.bsuteUser = bsuteUser;
    }

    public String getBsuteMonth() {
        return bsuteMonth;
    }

    public void setBsuteMonth(String bsuteMonth) {
        this.bsuteMonth = bsuteMonth;
    }

    public String getBsuteTitle() {
        return bsuteTitle;
    }

    public void setBsuteTitle(String bsuteTitle) {
        this.bsuteTitle = bsuteTitle;
    }

    public Date getBsuteCdate() {
        return bsuteCdate;
    }

    public void setBsuteCdate(Date bsuteCdate) {
        this.bsuteCdate = bsuteCdate;
    }

    public String getBsuteUserName() {
        return bsuteUserName;
    }

    public void setBsuteUserName(String bsuteUserName) {
        this.bsuteUserName = bsuteUserName;
    }

    public BusiUserTitleVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiUserTitle po = (BusiUserTitle) poObj;
        this.bsuteUuid = po.getBsuteUuid();
        this.bsuteUser = po.getBsuteUser();
        this.bsuteMonth = po.getBsuteMonth();
        this.bsuteTitle = po.getBsuteTitle();
        this.bsuteCdate = po.getBsuteCdate();
        this.bsuteUserName = po.getBsuteUserName();
    }
}