package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 邀请关系表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:52
 * @history:
 */
public class BusiInvitedRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsirnUnid;

    /**
     * 标识UUID
     */
    private String bsirnUuid;

    /**
     * 邀请人
     */
    private String bsirnInvited;

    /**
     * 被邀请人
     */
    private String bsirnBeInvited;

    /**
     * 所用邀请码
     */
    private String bsirnCode;

    /**
     * 邀请时间
     */
    private Date bsirnIdate;

    public Integer getBsirnUnid() {
        return bsirnUnid;
    }

    public void setBsirnUnid(Integer bsirnUnid) {
        this.bsirnUnid = bsirnUnid;
    }

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

    public BusiInvitedRelation() {
    }

}