package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiInvitedRelation;

import java.util.Date;

/**
 * 邀请关系表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:52
 * @history:
 */
@ApiModel(value = "邀请关系")
public class BusiInvitedRelationVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsirnUuid;

    /**
     * 邀请人
     */
    @ApiModelProperty(value = "邀请人")
    private String bsirnInvited;

    /**
     *邀请人姓名
     */
    @ApiModelProperty(value = "邀请人姓名")
    private String bsirnInvitedName;

    /**
     *邀请人手机号
     */
    @ApiModelProperty(value = "邀请人手机号")
    private String bsirnInvitedMobile;

    /**
     *邀请人头像
     */
    @ApiModelProperty(value = "邀请人头像")
    private String bsirnInvitedHead;

    /**
     * 被邀请人
     */
    @ApiModelProperty(value = "被邀请人")
    private String bsirnBeInvited;

    /**
     *被邀请人姓名
     */
    @ApiModelProperty(value = "被邀请人姓名")
    private String bsirnBeInvitedName;

    /**
     *被邀请人手机号
     */
    @ApiModelProperty(value = "被邀请人手机号")
    private String bsirnBeInvitedMobile;

    /**
     *被邀请人头像
     */
    @ApiModelProperty(value = "被邀请人头像")
    private String bsirnBeInvitedHead;

    /**
     * 所用邀请码
     */
    @ApiModelProperty(value = "所用邀请码")
    private String bsirnCode;

    /**
     * 邀请时间
     */
    @ApiModelProperty(value = "邀请时间")
    private Date bsirnIdate;

    public String getBsirnUuid() {
        return bsirnUuid;
    }

    public void setBsirnUuid(String bsirnUuid) {
        this.bsirnUuid = bsirnUuid;
    }

    public String getBsirnInvited() {
        return bsirnInvited;
    }

    public void setBsirnInvited(String bsirnInvited) {
        this.bsirnInvited = bsirnInvited;
    }

    public String getBsirnBeInvited() {
        return bsirnBeInvited;
    }

    public void setBsirnBeInvited(String bsirnBeInvited) {
        this.bsirnBeInvited = bsirnBeInvited;
    }

    public String getBsirnCode() {
        return bsirnCode;
    }

    public void setBsirnCode(String bsirnCode) {
        this.bsirnCode = bsirnCode;
    }

    public Date getBsirnIdate() {
        return bsirnIdate;
    }

    public void setBsirnIdate(Date bsirnIdate) {
        this.bsirnIdate = bsirnIdate;
    }

    public String getBsirnInvitedName() {
        return bsirnInvitedName;
    }

    public void setBsirnInvitedName(String bsirnInvitedName) {
        this.bsirnInvitedName = bsirnInvitedName;
    }

    public String getBsirnInvitedHead() {
        return bsirnInvitedHead;
    }

    public void setBsirnInvitedHead(String bsirnInvitedHead) {
        this.bsirnInvitedHead = bsirnInvitedHead;
    }

    public String getBsirnBeInvitedName() {
        return bsirnBeInvitedName;
    }

    public void setBsirnBeInvitedName(String bsirnBeInvitedName) {
        this.bsirnBeInvitedName = bsirnBeInvitedName;
    }

    public String getBsirnBeInvitedHead() {
        return bsirnBeInvitedHead;
    }

    public void setBsirnBeInvitedHead(String bsirnBeInvitedHead) {
        this.bsirnBeInvitedHead = bsirnBeInvitedHead;
    }

    public String getBsirnInvitedMobile() {
        return bsirnInvitedMobile;
    }

    public void setBsirnInvitedMobile(String bsirnInvitedMobile) {
        this.bsirnInvitedMobile = bsirnInvitedMobile;
    }

    public String getBsirnBeInvitedMobile() {
        return bsirnBeInvitedMobile;
    }

    public void setBsirnBeInvitedMobile(String bsirnBeInvitedMobile) {
        this.bsirnBeInvitedMobile = bsirnBeInvitedMobile;
    }

    public BusiInvitedRelationVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiInvitedRelation po = (BusiInvitedRelation) poObj;
        this.bsirnUuid = po.getBsirnUuid();
        this.bsirnInvited = po.getBsirnInvited();
        this.bsirnBeInvited = po.getBsirnBeInvited();
        this.bsirnCode = po.getBsirnCode();
        this.bsirnIdate = po.getBsirnIdate();
    }
}