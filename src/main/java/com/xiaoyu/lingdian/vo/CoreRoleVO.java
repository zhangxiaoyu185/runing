package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreRole;

/**
 * 角色表
 */
@ApiModel(value = "角色")
public class CoreRoleVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crrolUuid;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String crrolName;

    /**
     * 功能菜单集合
     */
    @ApiModelProperty(value = "功能菜单集合")
    private String crrolFuns;

    /**
     * 功能菜单名称集合
     */
    @ApiModelProperty(value = "功能菜单名称集合")
    private String crrolFunsName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    private String crrolDesc;

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

    public String getCrrolFunsName() {
        return crrolFunsName;
    }

    public void setCrrolFunsName(String crrolFunsName) {
        this.crrolFunsName = crrolFunsName;
    }

    public String getCrrolUuid() {
        return crrolUuid;
    }

    public void setCrrolUuid(String crrolUuid) {
        this.crrolUuid = crrolUuid;
    }

    public CoreRoleVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreRole po = (CoreRole) poObj;
        this.crrolUuid = po.getCrrolUuid();
        this.crrolName = po.getCrrolName();
        this.crrolFuns = po.getCrrolFuns();
        this.crrolDesc = po.getCrrolDesc();
    }

}