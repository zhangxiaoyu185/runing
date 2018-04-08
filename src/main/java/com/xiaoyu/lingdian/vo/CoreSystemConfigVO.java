package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreSystemConfig;

/**
 * 系统配置表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
@ApiModel(value = "系统配置")
public class CoreSystemConfigVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crscgUuid;

    /**
     * 配置KEY
     */
    @ApiModelProperty(value = "配置KEY")
    private String crscgKey;

    /**
     * 配置值
     */
    @ApiModelProperty(value = "配置值")
    private String crscgValue;

    /**
     * 配置说明
     */
    @ApiModelProperty(value = "配置说明")
    private String crscgDesc;

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

    public CoreSystemConfigVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreSystemConfig po = (CoreSystemConfig) poObj;
        this.crscgUuid = po.getCrscgUuid();
        this.crscgKey = po.getCrscgKey();
        this.crscgValue = po.getCrscgValue();
        this.crscgDesc = po.getCrscgDesc();
    }
}