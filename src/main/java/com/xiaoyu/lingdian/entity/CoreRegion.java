package com.xiaoyu.lingdian.entity;

import com.xiaoyu.lingdian.entity.BaseEntity;

/**
 * 区域表
 */
public class CoreRegion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crrgnUnid;

    /**
     * 标识UUID
     */
    private String crrgnUuid;

    /**
     * 地域名称
     */
    private String crrgnName;

    /**
     * 区域类型(1:country/国家;2:province/省/自治区/直辖市;3:city/地区/省下面的地级市;4:district/县/县级市/区)
     */
    private Integer crrgnType;

    /**
     * 父节点
     */
    private String crrgnParent;

    /**
     * 地区邮编
     */
    private String crrgnZipCode;

    public void setCrrgnUnid(Integer crrgnUnid) {
        this.crrgnUnid = crrgnUnid;
    }

    public Integer getCrrgnUnid() {
        return crrgnUnid;
    }

    public void setCrrgnUuid(String crrgnUuid) {
        this.crrgnUuid = crrgnUuid;
    }

    public String getCrrgnUuid() {
        return crrgnUuid;
    }

    public void setCrrgnName(String crrgnName) {
        this.crrgnName = crrgnName;
    }

    public String getCrrgnName() {
        return crrgnName;
    }

    public void setCrrgnType(Integer crrgnType) {
        this.crrgnType = crrgnType;
    }

    public Integer getCrrgnType() {
        return crrgnType;
    }

    public void setCrrgnParent(String crrgnParent) {
        this.crrgnParent = crrgnParent;
    }

    public String getCrrgnParent() {
        return crrgnParent;
    }

    public void setCrrgnZipCode(String crrgnZipCode) {
        this.crrgnZipCode = crrgnZipCode;
    }

    public String getCrrgnZipCode() {
        return crrgnZipCode;
    }

    public CoreRegion() {
    }

//<=================定制内容开始==============
//==================定制内容结束==============>

}