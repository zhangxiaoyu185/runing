package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

/**
 * 角色表
 */
public class CoreRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crrolUnid;

    /**
     * 标识UUID
     */
    private String crrolUuid;

    /**
     * 角色名称
     */
    private String crrolName;

    /**
     * 功能菜单集合
     */
    private String crrolFuns;

    /**
     * 角色描述
     */
    private String crrolDesc;

    public void setCrrolUnid(Integer crrolUnid) {
        this.crrolUnid = crrolUnid;
    }

    public Integer getCrrolUnid() {
        return crrolUnid;
    }

    public void setCrrolUuid(String crrolUuid) {
        this.crrolUuid = crrolUuid;
    }

    public String getCrrolUuid() {
        return crrolUuid;
    }

    public void setCrrolName(String crrolName) {
        this.crrolName = crrolName;
    }

    public String getCrrolName() {
        return crrolName;
    }

    public void setCrrolFuns(String crrolFuns) {
        this.crrolFuns = crrolFuns;
    }

    public String getCrrolFuns() {
        return crrolFuns;
    }

    public void setCrrolDesc(String crrolDesc) {
        this.crrolDesc = crrolDesc;
    }

    public String getCrrolDesc() {
        return crrolDesc;
    }

    public CoreRole() {
    }

//<=================定制内容开始==============
//==================定制内容结束==============>

}