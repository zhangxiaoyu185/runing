package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiSync;

import java.util.Date;

/**
 * 同步表
 *
 * @author: zhangy
 * @since: 2018年04月09日 16:57:33
 * @history:
 */
@ApiModel(value = "同步")
public class BusiSyncVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bssycUuid;

    /**
     * 所属用户
     */
    @ApiModelProperty(value = "所属用户")
    private String bssycUser;

    /**
     * 上次同步时间
     */
    @ApiModelProperty(value = "上次同步时间")
    private Date bssycSdate;

    public String getBssycUuid() {
        return bssycUuid;
    }

    public void setBssycUuid(String bssycUuid) {
        this.bssycUuid = bssycUuid;
    }

    public String getBssycUser() {
        return bssycUser;
    }

    public void setBssycUser(String bssycUser) {
        this.bssycUser = bssycUser;
    }

    public Date getBssycSdate() {
        return bssycSdate;
    }

    public void setBssycSdate(Date bssycSdate) {
        this.bssycSdate = bssycSdate;
    }

    public BusiSyncVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiSync po = (BusiSync) poObj;
        this.bssycUuid = po.getBssycUuid();
        this.bssycUser = po.getBssycUser();
        this.bssycSdate = po.getBssycSdate();
    }
}