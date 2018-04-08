package com.xiaoyu.lingdian.entity;


/**
 * 系统配置表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
public class CoreSystemConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crscgUnid;

    /**
     * 标识UUID
     */
    private String crscgUuid;

    /**
     * 配置KEY
     */
    private String crscgKey;

    /**
     * 配置值
     */
    private String crscgValue;

    /**
     * 配置说明
     */
    private String crscgDesc;

    public Integer getCrscgUnid() {
        return crscgUnid;
    }

    public void setCrscgUnid(Integer crscgUnid) {
        this.crscgUnid = crscgUnid;
    }

    public String getCrscgUuid() {
        return crscgUuid;
    }

    public void setCrscgUuid(String crscgUuid) {
        this.crscgUuid = crscgUuid;
    }

    public String getCrscgKey() {
        return crscgKey;
    }

    public void setCrscgKey(String crscgKey) {
        this.crscgKey = crscgKey;
    }

    public String getCrscgValue() {
        return crscgValue;
    }

    public void setCrscgValue(String crscgValue) {
        this.crscgValue = crscgValue;
    }

    public String getCrscgDesc() {
        return crscgDesc;
    }

    public void setCrscgDesc(String crscgDesc) {
        this.crscgDesc = crscgDesc;
    }

    public CoreSystemConfig() {
    }

}