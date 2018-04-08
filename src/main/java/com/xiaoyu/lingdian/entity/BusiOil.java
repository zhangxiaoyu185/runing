package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 油卡表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:52
 * @history:
 */
public class BusiOil extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsoilUnid;

    /**
     * 标识UUID
     */
    private String bsoilUuid;

    /**
     * 油卡卡号
     */
    private String bsoilCode;

    /**
     * 绑定姓名
     */
    private String bsoilName;

    /**
     * 快递单号
     */
    private String bsoilExpress;

    /**
     * 绑定手机号
     */
    private String bsoilMobile;

    /**
     * 车牌
     */
    private String bsoilCarNo;

    /**
     * 行驶证号
     */
    private String bsoilDrivingNo;

    /**
     * 申请人
     */
    private String bsoilUser;

    /**
     * 状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     */
    private Integer bsoilStatus;

    /**
     * 邮寄地址
     */
    private String bsoilAddress;

    /**
     * 油卡图
     */
    private String bsoilPic;

    /**
     * 创建时间
     */
    private Date bsoilCdate;

    /**
     * 更新时间
     */
    private Date bsoilUdate;

    /**
     * 删除状态1未删除0已删除
     */
    private Integer bsoilDeleteFlag;

    /**
     * 代理商平台(1本平台)
     */
    private String bsoilAgent;

    /**
     * 代理商回调url
     */
    private String bsoilCallbackUrl;

    public Integer getBsoilUnid() {
        return bsoilUnid;
    }

    public void setBsoilUnid(Integer bsoilUnid) {
        this.bsoilUnid = bsoilUnid;
    }

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

    public BusiOil() {
    }

}