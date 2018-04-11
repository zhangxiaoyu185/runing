package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
 * 部门称号记录表
 *
 * @author: zhangy
 * @since: 2018年04月09日 16:57:33
 * @history:
 */
public class BusiDeptTitle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer bsdteUnid;

    /**
     * 标识UUID
     */
    private String bsdteUuid;

    /**
     * 所属部门
     */
    private String bsdteDept;

    /**
     * 所属部门名称
     */
    private String bsdteDeptName;

    /**
     * 所属月
     */
    private String bsdteMonth;

    /**
     * 获得称号
     */
    private String bsdteTitle;

    /**
     * 创建时间
     */
    private Date bsdteCdate;

    public Integer getBsdteUnid() {
        return bsdteUnid;
    }

    public void setBsdteUnid(Integer bsdteUnid) {
        this.bsdteUnid = bsdteUnid;
    }

    public String getBsdteUuid() {
        return bsdteUuid;
    }

    public void setBsdteUuid(String bsdteUuid) {
        this.bsdteUuid = bsdteUuid;
    }

    public String getBsdteDept() {
        return bsdteDept;
    }

    public void setBsdteDept(String bsdteDept) {
        this.bsdteDept = bsdteDept;
    }

    public String getBsdteMonth() {
        return bsdteMonth;
    }

    public void setBsdteMonth(String bsdteMonth) {
        this.bsdteMonth = bsdteMonth;
    }

    public String getBsdteTitle() {
        return bsdteTitle;
    }

    public void setBsdteTitle(String bsdteTitle) {
        this.bsdteTitle = bsdteTitle;
    }

    public Date getBsdteCdate() {
        return bsdteCdate;
    }

    public void setBsdteCdate(Date bsdteCdate) {
        this.bsdteCdate = bsdteCdate;
    }

    public String getBsdteDeptName() {
        return bsdteDeptName;
    }

    public void setBsdteDeptName(String bsdteDeptName) {
        this.bsdteDeptName = bsdteDeptName;
    }

    public BusiDeptTitle() {
    }

}