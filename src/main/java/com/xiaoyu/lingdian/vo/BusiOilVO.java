package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiOil;

import java.util.Date;

/**
 * 油卡表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:52
 * @history:
 */
@ApiModel(value = "油卡")
public class BusiOilVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String bsoilUuid;

    /**
     * 油卡卡号
     */
    @ApiModelProperty(value = "油卡卡号")
    private String bsoilCode;

    /**
     * 绑定姓名
     */
    @ApiModelProperty(value = "绑定姓名")
    private String bsoilName;

    /**
     * 快递单号
     */
    @ApiModelProperty(value = "快递单号")
    private String bsoilExpress;

    /**
     * 绑定手机号
     */
    @ApiModelProperty(value = "绑定手机号")
    private String bsoilMobile;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private String bsoilUser;

    /**
     * 申请人头像
     */
    @ApiModelProperty(value = "申请人头像")
    private String bsoilUserHead;

    /**
     * 车牌
     */
    @ApiModelProperty(value = "车牌")
    private String bsoilCarNo;

    /**
     * 行驶证号
     */
    @ApiModelProperty(value = "行驶证号")
    private String bsoilDrivingNo;

    /**
     * 状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     */
    @ApiModelProperty(value = "状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）")
    private Integer bsoilStatus;

    /**
     * 邮寄地址
     */
    @ApiModelProperty(value = "邮寄地址")
    private String bsoilAddress;

    /**
     * 油卡图
     */
    @ApiModelProperty(value = "油卡图")
    private String bsoilPic;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date bsoilCdate;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date bsoilUdate;

    /**
     * 删除状态1未删除0已删除
     */
    @ApiModelProperty(value = "删除状态1未删除0已删除")
    private Integer bsoilDeleteFlag;

    /**
     * 代理商平台(1本平台)
     */
    @ApiModelProperty(value = "代理商平台(1本平台)")
    private String bsoilAgent;

    /**
     * 代理商回调url
     */
    @ApiModelProperty(value = "代理商回调url")
    private String bsoilCallbackUrl;

    public String getBsoilUuid() {
        return bsoilUuid;
    }

    public void setBsoilUuid(String bsoilUuid) {
        this.bsoilUuid = bsoilUuid;
    }

    public String getBsoilCode() {
        return bsoilCode;
    }

    public void setBsoilCode(String bsoilCode) {
        this.bsoilCode = bsoilCode;
    }

    public String getBsoilName() {
        return bsoilName;
    }

    public void setBsoilName(String bsoilName) {
        this.bsoilName = bsoilName;
    }

    public String getBsoilExpress() {
        return bsoilExpress;
    }

    public void setBsoilExpress(String bsoilExpress) {
        this.bsoilExpress = bsoilExpress;
    }

    public String getBsoilMobile() {
        return bsoilMobile;
    }

    public void setBsoilMobile(String bsoilMobile) {
        this.bsoilMobile = bsoilMobile;
    }

    public String getBsoilUser() {
        return bsoilUser;
    }

    public void setBsoilUser(String bsoilUser) {
        this.bsoilUser = bsoilUser;
    }

    public Integer getBsoilStatus() {
        return bsoilStatus;
    }

    public void setBsoilStatus(Integer bsoilStatus) {
        this.bsoilStatus = bsoilStatus;
    }

    public String getBsoilPic() {
        return bsoilPic;
    }

    public void setBsoilPic(String bsoilPic) {
        this.bsoilPic = bsoilPic;
    }

    public Date getBsoilCdate() {
        return bsoilCdate;
    }

    public void setBsoilCdate(Date bsoilCdate) {
        this.bsoilCdate = bsoilCdate;
    }

    public Date getBsoilUdate() {
        return bsoilUdate;
    }

    public void setBsoilUdate(Date bsoilUdate) {
        this.bsoilUdate = bsoilUdate;
    }

    public String getBsoilAgent() {
        return bsoilAgent;
    }

    public void setBsoilAgent(String bsoilAgent) {
        this.bsoilAgent = bsoilAgent;
    }

    public String getBsoilCallbackUrl() {
        return bsoilCallbackUrl;
    }

    public void setBsoilCallbackUrl(String bsoilCallbackUrl) {
        this.bsoilCallbackUrl = bsoilCallbackUrl;
    }

    public String getBsoilAddress() {
        return bsoilAddress;
    }

    public void setBsoilAddress(String bsoilAddress) {
        this.bsoilAddress = bsoilAddress;
    }

    public Integer getBsoilDeleteFlag() {
        return bsoilDeleteFlag;
    }

    public void setBsoilDeleteFlag(Integer bsoilDeleteFlag) {
        this.bsoilDeleteFlag = bsoilDeleteFlag;
    }

    public String getBsoilUserHead() {
        return bsoilUserHead;
    }

    public void setBsoilUserHead(String bsoilUserHead) {
        this.bsoilUserHead = bsoilUserHead;
    }

    public String getBsoilCarNo() {
        return bsoilCarNo;
    }

    public void setBsoilCarNo(String bsoilCarNo) {
        this.bsoilCarNo = bsoilCarNo;
    }

    public String getBsoilDrivingNo() {
        return bsoilDrivingNo;
    }

    public void setBsoilDrivingNo(String bsoilDrivingNo) {
        this.bsoilDrivingNo = bsoilDrivingNo;
    }

    public BusiOilVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        BusiOil po = (BusiOil) poObj;
        this.bsoilUuid = po.getBsoilUuid();
        this.bsoilCode = po.getBsoilCode();
        this.bsoilName = po.getBsoilName();
        this.bsoilExpress = po.getBsoilExpress();
        this.bsoilMobile = po.getBsoilMobile();
        this.bsoilDrivingNo = po.getBsoilDrivingNo();
        this.bsoilCarNo = po.getBsoilCarNo();
        this.bsoilUser = po.getBsoilUser();
        this.bsoilStatus = po.getBsoilStatus();
        this.bsoilAddress = po.getBsoilAddress();
        this.bsoilPic = po.getBsoilPic();
        this.bsoilCdate = po.getBsoilCdate();
        this.bsoilUdate = po.getBsoilUdate();
        this.bsoilDeleteFlag = po.getBsoilDeleteFlag();
        this.bsoilAgent = po.getBsoilAgent();
        this.bsoilCallbackUrl = po.getBsoilCallbackUrl();
    }
}