package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreSystemSet;

/**
 * 系统设置表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
@ApiModel(value = "系统设置")
public class CoreSystemSetVO implements BaseVO {

    /**
     * 标识UUID
     */
    @ApiModelProperty(value = "标识UUID")
    private String crsstUuid;

    /**
     * 项目域名
     */
    @ApiModelProperty(value = "项目域名")
    private String crsstMessageDomain;

    /**
     * 附件存放目录
     */
    @ApiModelProperty(value = "附件存放目录")
    private String crsstAttachmentDir;

    /**
     * 每日一言内容
     */
    @ApiModelProperty(value = "每日一言内容")
    private String crsstDayContent;

    public String getCrsstUuid() {
        return crsstUuid;
    }

    public void setCrsstUuid(String crsstUuid) {
        this.crsstUuid = crsstUuid;
    }

    public String getCrsstDayContent() {
        return crsstDayContent;
    }

    public void setCrsstDayContent(String crsstDayContent) {
        this.crsstDayContent = crsstDayContent;
    }

    public String getCrsstMessageDomain() {
        return crsstMessageDomain;
    }

    public void setCrsstMessageDomain(String crsstMessageDomain) {
        this.crsstMessageDomain = crsstMessageDomain;
    }

    public String getCrsstAttachmentDir() {
        return crsstAttachmentDir;
    }

    public void setCrsstAttachmentDir(String crsstAttachmentDir) {
        this.crsstAttachmentDir = crsstAttachmentDir;
    }

    public CoreSystemSetVO() {
    }

    @Override
    public void convertPOToVO(Object poObj) {
        if (null == poObj) {
            return;
        }

        CoreSystemSet po = (CoreSystemSet) poObj;
        this.crsstUuid = po.getCrsstUuid();
        this.crsstMessageDomain = po.getCrsstMessageDomain();
        this.crsstAttachmentDir = po.getCrsstAttachmentDir();
        this.crsstDayContent = po.getCrsstDayContent();
    }
}