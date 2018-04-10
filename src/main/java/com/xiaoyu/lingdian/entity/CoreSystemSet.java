package com.xiaoyu.lingdian.entity;


/**
 * 系统设置表
 *
 * @author: zhangy
 * @since: 2018年01月09日 23:24:54
 * @history:
 */
public class CoreSystemSet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识UNID
     */
    private Integer crsstUnid;

    /**
     * 标识UUID
     */
    private String crsstUuid;

    /**
     * 项目域名
     */
    private String crsstMessageDomain;

    /**
     * 附件存放目录
     */
    private String crsstAttachmentDir;

    /**
     * 每日一言内容
     */
    private String crsstDayContent;

    public Integer getCrsstUnid() {
        return crsstUnid;
    }

    public void setCrsstUnid(Integer crsstUnid) {
        this.crsstUnid = crsstUnid;
    }

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

    public CoreSystemSet() {
    }

}