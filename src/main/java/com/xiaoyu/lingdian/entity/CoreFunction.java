package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

import java.util.Date;

/**
 * 功能项表
 */
public class CoreFunction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crfntUnid;

    /**
     * 标识UUID
     */
    private String crfntUuid;

    /**
     * 功能项名称
     */
    private String crfntFunName;

    /**
     * 资源请求地址
     */
    private String crfntResource;

    /**
     * 状态
     */
    private Integer crfntStatus;

    /**
     * 创建日期
     */
    private Date crfntCdate;

    /**
     * 修改日期
     */
    private Date crfntUdate;

    /**
     * 排序号
     */
    private Integer crfntOrd;

    /**
     * 上级菜单
     */
    private String crfntFather;

    public void setCrfntUnid(Integer crfntUnid) {
        this.crfntUnid = crfntUnid;
    }

    public Integer getCrfntUnid() {
        return crfntUnid;
    }

    public void setCrfntUuid(String crfntUuid) {
        this.crfntUuid = crfntUuid;
    }

    public String getCrfntUuid() {
        return crfntUuid;
    }

    public void setCrfntFunName(String crfntFunName) {
        this.crfntFunName = crfntFunName;
    }

    public String getCrfntFunName() {
        return crfntFunName;
    }

    public void setCrfntResource(String crfntResource) {
        this.crfntResource = crfntResource;
    }

    public String getCrfntResource() {
        return crfntResource;
    }

    public void setCrfntStatus(Integer crfntStatus) {
        this.crfntStatus = crfntStatus;
    }

    public Integer getCrfntStatus() {
        return crfntStatus;
    }

    public void setCrfntCdate(Date crfntCdate) {
        this.crfntCdate = crfntCdate;
    }

    public Date getCrfntCdate() {
        return crfntCdate;
    }

    public void setCrfntUdate(Date crfntUdate) {
        this.crfntUdate = crfntUdate;
    }

    public Date getCrfntUdate() {
        return crfntUdate;
    }

    public void setCrfntOrd(Integer crfntOrd) {
        this.crfntOrd = crfntOrd;
    }

    public Integer getCrfntOrd() {
        return crfntOrd;
    }

    public void setCrfntFather(String crfntFather) {
        this.crfntFather = crfntFather;
    }

    public String getCrfntFather() {
        return crfntFather;
    }

    public CoreFunction() {
    }

//<=================定制内容开始==============
//==================定制内容结束==============>

}