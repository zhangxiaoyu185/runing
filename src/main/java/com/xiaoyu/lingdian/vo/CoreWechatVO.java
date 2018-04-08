package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreWechat;

import java.util.Date;

/**
 * 小程序表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
@ApiModel(value = "小程序")
public class CoreWechatVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crwctUuid;

    /**
     * 小程序名称
     */
    @ApiModelProperty(value = "小程序名称")
    private String crwctName;

    /**
     * appid
     */
    @ApiModelProperty(value = "appid")
    private String crwctAppid;

    /**
     * appsecret
     */
    @ApiModelProperty(value = "appsecret")
    private String crwctAppsecret;

    /**
     * accessToken(全局)
     */
    @ApiModelProperty(value = "accessToken(全局)")
    private String crwctAccessToken;

    /**
     * accessTokenLastTime
     */
    @ApiModelProperty(value = "accessTokenLastTime")
    private Date crwctAccessTime;

    /**
     * 商户号partner/mch_id
     */
    @ApiModelProperty(value = "商户号partner/mch_id")
    private String crwctPartner;

    /**
     * 商户密钥partnerkey
     */
    @ApiModelProperty(value = "商户密钥partnerkey")
    private String crwctPartnerkey;

    /**
     * 回调URLnotifyurl
     */
    @ApiModelProperty(value = "回调URLnotifyurl")
    private String crwctNotifyurl;

    /**
     * 状态:1启用,0禁用
     */
    @ApiModelProperty(value = "状态:1启用,0禁用")
    private Integer crwctStatus;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private Date crwctCdate;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改日期")
    private Date crwctUdate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String crwctRemarks;

    public String getCrwctUuid() {
        return crwctUuid;
    }

    public void setCrwctUuid(String crwctUuid) {
        this.crwctUuid = crwctUuid;
    }

    public String getCrwctName() {
        return crwctName;
    }

    public void setCrwctName(String crwctName) {
        this.crwctName = crwctName;
    }

    public String getCrwctAppid() {
        return crwctAppid;
    }

    public void setCrwctAppid(String crwctAppid) {
        this.crwctAppid = crwctAppid;
    }

    public String getCrwctAppsecret() {
        return crwctAppsecret;
    }

    public void setCrwctAppsecret(String crwctAppsecret) {
        this.crwctAppsecret = crwctAppsecret;
    }

    public String getCrwctAccessToken() {
        return crwctAccessToken;
    }

    public void setCrwctAccessToken(String crwctAccessToken) {
        this.crwctAccessToken = crwctAccessToken;
    }

    public Date getCrwctAccessTime() {
        return crwctAccessTime;
    }

    public void setCrwctAccessTime(Date crwctAccessTime) {
        this.crwctAccessTime = crwctAccessTime;
    }

    public String getCrwctPartner() {
        return crwctPartner;
    }

    public void setCrwctPartner(String crwctPartner) {
        this.crwctPartner = crwctPartner;
    }

    public String getCrwctPartnerkey() {
        return crwctPartnerkey;
    }

    public void setCrwctPartnerkey(String crwctPartnerkey) {
        this.crwctPartnerkey = crwctPartnerkey;
    }

    public String getCrwctNotifyurl() {
        return crwctNotifyurl;
    }

    public void setCrwctNotifyurl(String crwctNotifyurl) {
        this.crwctNotifyurl = crwctNotifyurl;
    }

    public Integer getCrwctStatus() {
        return crwctStatus;
    }

    public void setCrwctStatus(Integer crwctStatus) {
        this.crwctStatus = crwctStatus;
    }

    public Date getCrwctCdate() {
        return crwctCdate;
    }

    public void setCrwctCdate(Date crwctCdate) {
        this.crwctCdate = crwctCdate;
    }

    public Date getCrwctUdate() {
        return crwctUdate;
    }

    public void setCrwctUdate(Date crwctUdate) {
        this.crwctUdate = crwctUdate;
    }

    public String getCrwctRemarks() {
        return crwctRemarks;
    }

    public void setCrwctRemarks(String crwctRemarks) {
        this.crwctRemarks = crwctRemarks;
    }

    public CoreWechatVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreWechat po = (CoreWechat) poObj;
        this.crwctUuid = po.getCrwctUuid();
        this.crwctName = po.getCrwctName();
        this.crwctAppid = po.getCrwctAppid();
        this.crwctAppsecret = po.getCrwctAppsecret();
        this.crwctAccessToken = po.getCrwctAccessToken();
        this.crwctAccessTime = po.getCrwctAccessTime();
        this.crwctPartner = po.getCrwctPartner();
        this.crwctPartnerkey = po.getCrwctPartnerkey();
        this.crwctNotifyurl = po.getCrwctNotifyurl();
        this.crwctStatus = po.getCrwctStatus();
        this.crwctCdate = po.getCrwctCdate();
        this.crwctUdate = po.getCrwctUdate();
        this.crwctRemarks = po.getCrwctRemarks();
    }
}