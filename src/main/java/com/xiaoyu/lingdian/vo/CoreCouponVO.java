package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreCoupon;

import java.util.Date;

/**
 * 优惠券表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
@ApiModel(value = "优惠券")
public class CoreCouponVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crcpnUuid;

    /**
     * 所属用户
     */
    @ApiModelProperty(value = "所属用户")
    private String crcpnUser;

    /**
     * 所属用户手机号
     */
    @ApiModelProperty(value = "所属用户手机号")
    private String crcpnUserMobile;

    /**
     * 所属用户姓名
     */
    @ApiModelProperty(value = "所属用户姓名")
    private String crcpnUserName;

    /**
     * 使用状态（1已使用，2可使用，3冻结中）
     */
    @ApiModelProperty(value = "使用状态（1已使用，2可使用，3已过期，4未分配）")
    private Integer crcpnUseStatus;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private Integer crcpnReduce;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date crcpnCdate;

    /**
     * 发送状态（1系统发送、2手动发送）
     */
    @ApiModelProperty(value = "发送状态（1系统发送、2手动发送）")
    private Integer crcpnSendStatus;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private Date crcpnStart;

    /**
     * 截止时间
     */
    @ApiModelProperty(value = "截止时间")
    private Date crcpnEnd;

    public String getCrcpnUuid() {
        return crcpnUuid;
    }

    public void setCrcpnUuid(String crcpnUuid) {
        this.crcpnUuid = crcpnUuid;
    }

    public String getCrcpnUser() {
        return crcpnUser;
    }

    public void setCrcpnUser(String crcpnUser) {
        this.crcpnUser = crcpnUser;
    }

    public Integer getCrcpnUseStatus() {
        return crcpnUseStatus;
    }

    public void setCrcpnUseStatus(Integer crcpnUseStatus) {
        this.crcpnUseStatus = crcpnUseStatus;
    }

    public Integer getCrcpnReduce() {
        return crcpnReduce;
    }

    public void setCrcpnReduce(Integer crcpnReduce) {
        this.crcpnReduce = crcpnReduce;
    }

    public Date getCrcpnCdate() {
        return crcpnCdate;
    }

    public void setCrcpnCdate(Date crcpnCdate) {
        this.crcpnCdate = crcpnCdate;
    }

    public Integer getCrcpnSendStatus() {
        return crcpnSendStatus;
    }

    public void setCrcpnSendStatus(Integer crcpnSendStatus) {
        this.crcpnSendStatus = crcpnSendStatus;
    }

    public Date getCrcpnStart() {
        return crcpnStart;
    }

    public void setCrcpnStart(Date crcpnStart) {
        this.crcpnStart = crcpnStart;
    }

    public Date getCrcpnEnd() {
        return crcpnEnd;
    }

    public void setCrcpnEnd(Date crcpnEnd) {
        this.crcpnEnd = crcpnEnd;
    }

    public String getCrcpnUserMobile() {
        return crcpnUserMobile;
    }

    public void setCrcpnUserMobile(String crcpnUserMobile) {
        this.crcpnUserMobile = crcpnUserMobile;
    }

    public String getCrcpnUserName() {
        return crcpnUserName;
    }

    public void setCrcpnUserName(String crcpnUserName) {
        this.crcpnUserName = crcpnUserName;
    }

    public CoreCouponVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreCoupon po = (CoreCoupon) poObj;
        this.crcpnUuid = po.getCrcpnUuid();
        this.crcpnUser = po.getCrcpnUser();
        this.crcpnUseStatus = po.getCrcpnUseStatus();
        this.crcpnReduce = po.getCrcpnReduce();
        this.crcpnCdate = po.getCrcpnCdate();
        this.crcpnSendStatus = po.getCrcpnSendStatus();
        this.crcpnStart = po.getCrcpnStart();
        this.crcpnEnd = po.getCrcpnEnd();
    }
}