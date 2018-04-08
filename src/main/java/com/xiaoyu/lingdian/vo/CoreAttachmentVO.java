package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreAttachment;
import com.xiaoyu.lingdian.tool.DateUtil;

/**
* 业务附件表
*/
@ApiModel(value = "业务附件")
public class CoreAttachmentVO implements BaseVO {

	/**
	*标识UUID
	*/
	@ApiModelProperty(value = "业务附件标识")
	private String cratmUuid;

	/**
	*业务实体UUID
	*/
	@ApiModelProperty(value = "业务实体UUID")
	private String cratmBusUuid;

	/**
	*名称
	*/
	@ApiModelProperty(value = "名称")
	private String cratmFileName;

	/**
	*目录,例a/b
	*/
	@ApiModelProperty(value = "业务附件标识")
	private String cratmDir;

	/**
	*文件扩展名
	*/
	@ApiModelProperty(value = "文件扩展名")
	private String cratmExtension;

	/**
	*创建日期
	*/
	@ApiModelProperty(value = "创建日期")
	private String cratmCdate;

	/**
	*宽度
	*/
	@ApiModelProperty(value = "宽度")
	private Integer cratmWidth;

	/**
	*高度
	*/
	@ApiModelProperty(value = "高度")
	private Integer cratmHeight;

	/**
	*类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片
	*/
	@ApiModelProperty(value = "类型:1用户头像2代理商打款凭证8驾驶证照片9用户邀请码11首页banner图(750X350)12充值banner图（750X350）13邀请banner图（750X350）14logo15其他999非图片")
	private Integer cratmType;

	public void setCratmUuid(String cratmUuid) {
		this.cratmUuid = cratmUuid;
	}

	public String getCratmUuid( ) {
		return cratmUuid;
	}

	public void setCratmBusUuid(String cratmBusUuid) {
		this.cratmBusUuid = cratmBusUuid;
	}

	public String getCratmBusUuid( ) {
		return cratmBusUuid;
	}

	public void setCratmFileName(String cratmFileName) {
		this.cratmFileName = cratmFileName;
	}

	public String getCratmFileName( ) {
		return cratmFileName;
	}

	public void setCratmDir(String cratmDir) {
		this.cratmDir = cratmDir;
	}

	public String getCratmDir( ) {
		return cratmDir;
	}

	public void setCratmExtension(String cratmExtension) {
		this.cratmExtension = cratmExtension;
	}

	public String getCratmExtension( ) {
		return cratmExtension;
	}

	public void setCratmCdate(String cratmCdate) {
		this.cratmCdate = cratmCdate;
	}

	public String getCratmCdate( ) {
		return cratmCdate;
	}

	public void setCratmWidth(Integer cratmWidth) {
		this.cratmWidth = cratmWidth;
	}

	public Integer getCratmWidth( ) {
		return cratmWidth;
	}

	public void setCratmHeight(Integer cratmHeight) {
		this.cratmHeight = cratmHeight;
	}

	public Integer getCratmHeight( ) {
		return cratmHeight;
	}

	public void setCratmType(Integer cratmType) {
		this.cratmType = cratmType;
	}

	public Integer getCratmType( ) {
		return cratmType;
	}

	public CoreAttachmentVO( ) { 
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		CoreAttachment po = (CoreAttachment) poObj;
		this.cratmUuid = po.getCratmUuid();
		this.cratmBusUuid = po.getCratmBusUuid();
		this.cratmFileName = po.getCratmFileName();
		this.cratmDir = po.getCratmDir();
		this.cratmExtension = po.getCratmExtension();
		this.cratmCdate = po.getCratmCdate()!=null?DateUtil.formatDefaultDate(po.getCratmCdate()):"";
		this.cratmWidth = po.getCratmWidth();
		this.cratmHeight = po.getCratmHeight();
		this.cratmType = po.getCratmType();
	}

}