package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 优惠券表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:53
 * @history:
 */
public class CoreCoupon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crcpnUnid;

    /**
     * 标识UUID
     */
    private String crcpnUuid;

    /**
     * 所属用户
     */
    private String crcpnUser;

    /**
     * 使用状态（1已使用，2可使用，3已过期，4未分配）
     */
    private Integer crcpnUseStatus;

    /**
     * 减
     */
    private Integer crcpnReduce;

    /**
     * 添加时间
     */
    private Date crcpnCdate;

    /**
     * 发送状态（1系统发送、2手动发送）
     */
    private Integer crcpnSendStatus;

    /**
     * 生效时间
     */
    private Date crcpnStart;

    /**
     * 截止时间
     */
    private Date crcpnEnd;

    public Integer getCrcpnUnid() {
        return crcpnUnid;
    }

    public void setCrcpnUnid(Integer crcpnUnid) {
        this.crcpnUnid = crcpnUnid;
    }

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

    public CoreCoupon() {
    }

}