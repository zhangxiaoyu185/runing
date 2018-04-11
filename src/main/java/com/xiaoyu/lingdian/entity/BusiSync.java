package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 同步表
 *
 * @author: zhangy
 * @since: 2018年04月09日 16:57:33
 * @history:
 */
public class BusiSync extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bssycUnid;

    /**
     * 标识UUID
     */
    private String bssycUuid;

    /**
     * 所属用户
     */
    private String bssycUser;

    /**
     * 上次同步时间
     */
    private Date bssycSdate;

    public Integer getBssycUnid() {
        return bssycUnid;
    }

    public void setBssycUnid(Integer bssycUnid) {
        this.bssycUnid = bssycUnid;
    }

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

    public BusiSync() {
    }

}