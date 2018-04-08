package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.vo.BaseVO;
import com.xiaoyu.lingdian.tool.DateUtil;

/**
* 功能项表
*/
@ApiModel(value = "功能项")
public class CoreFunctionVO implements BaseVO {

	/**
	*标识UUID
	*/
	@ApiModelProperty(value = "功能项标识")
	private String crfntUuid;

	/**
	*功能项名称
	*/
	@ApiModelProperty(value = "功能项名称")
	private String crfntFunName;

	/**
	*资源请求地址
	*/
	@ApiModelProperty(value = "资源请求地址")
	private String crfntResource;
	
	/**
	*状态
	*/
	@ApiModelProperty(value = "状态")
	private Integer crfntStatus;

	/**
	*创建日期
	*/
	@ApiModelProperty(value = "创建日期")
	private String crfntCdate;

	/**
	*修改日期
	*/
	@ApiModelProperty(value = "修改日期")
	private String crfntUdate;

	/**
	*排序号
	*/
	@ApiModelProperty(value = "排序号")
	private Integer crfntOrd;

	/**
	*上级菜单
	*/
	@ApiModelProperty(value = "上级菜单")
	private String crfntFather;

	/**
	*上级菜单名称
	*/
	@ApiModelProperty(value = "上级菜单名称")
	private String crfntFatherName;
	
	public void setCrfntUuid(String crfntUuid) {
		this.crfntUuid = crfntUuid;
	}

	public String getCrfntUuid( ) {
		return crfntUuid;
	}

	public void setCrfntFunName(String crfntFunName) {
		this.crfntFunName = crfntFunName;
	}

	public String getCrfntFunName( ) {
		return crfntFunName;
	}

	public void setCrfntResource(String crfntResource) {
		this.crfntResource = crfntResource;
	}

	public String getCrfntResource( ) {
		return crfntResource;
	}

	public void setCrfntStatus(Integer crfntStatus) {
		this.crfntStatus = crfntStatus;
	}

	public Integer getCrfntStatus( ) {
		return crfntStatus;
	}

	public void setCrfntCdate(String crfntCdate) {
		this.crfntCdate = crfntCdate;
	}

	public String getCrfntCdate( ) {
		return crfntCdate;
	}

	public void setCrfntUdate(String crfntUdate) {
		this.crfntUdate = crfntUdate;
	}

	public String getCrfntUdate( ) {
		return crfntUdate;
	}

	public void setCrfntOrd(Integer crfntOrd) {
		this.crfntOrd = crfntOrd;
	}

	public Integer getCrfntOrd( ) {
		return crfntOrd;
	}

	public void setCrfntFather(String crfntFather) {
		this.crfntFather = crfntFather;
	}

	public String getCrfntFather( ) {
		return crfntFather;
	}

	public String getCrfntFatherName() {
		return crfntFatherName;
	}

	public void setCrfntFatherName(String crfntFatherName) {
		this.crfntFatherName = crfntFatherName;
	}

	public CoreFunctionVO( ) { 
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		CoreFunction po = (CoreFunction) poObj;
		this.crfntUuid = po.getCrfntUuid();
		this.crfntFunName = po.getCrfntFunName();
		this.crfntResource = po.getCrfntResource();
		this.crfntStatus = po.getCrfntStatus();
		this.crfntCdate = po.getCrfntCdate()!=null?DateUtil.formatDefaultDate(po.getCrfntCdate()):"";
		this.crfntUdate = po.getCrfntUdate()!=null?DateUtil.formatDefaultDate(po.getCrfntUdate()):"";
		this.crfntOrd = po.getCrfntOrd();
		this.crfntFather = po.getCrfntFather();
	}

}