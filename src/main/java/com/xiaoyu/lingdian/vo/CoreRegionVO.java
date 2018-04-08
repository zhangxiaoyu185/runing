package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.CoreRegion;
import com.xiaoyu.lingdian.vo.BaseVO;

/**
* 区域表
*/
@ApiModel(value = "区域")
public class CoreRegionVO implements BaseVO {

	/**
	*标识UUID
	*/
	@ApiModelProperty(value = "区域标识")
	private String crrgnUuid;

	/**
	*地域名称
	*/
	@ApiModelProperty(value = "地域名称")
	private String crrgnName;

	/**
	*区域类型(1:country/国家;2:province/省/自治区/直辖市;3:city/地区/省下面的地级市;4:district/县/县级市/区)
	*/
	@ApiModelProperty(value = "区域类型(1:country/国家;2:province/省/自治区/直辖市;3:city/地区/省下面的地级市;4:district/县/县级市/区)")
	private Integer crrgnType;

	/**
	*父节点
	*/
	@ApiModelProperty(value = "父节点")
	private String crrgnParent;

	/**
	*地区邮编
	*/
	@ApiModelProperty(value = "地区邮编")
	private String crrgnZipCode;

	public void setCrrgnUuid(String crrgnUuid) {
		this.crrgnUuid = crrgnUuid;
	}

	public String getCrrgnUuid( ) {
		return crrgnUuid;
	}

	public void setCrrgnName(String crrgnName) {
		this.crrgnName = crrgnName;
	}

	public String getCrrgnName( ) {
		return crrgnName;
	}

	public void setCrrgnType(Integer crrgnType) {
		this.crrgnType = crrgnType;
	}

	public Integer getCrrgnType( ) {
		return crrgnType;
	}

	public void setCrrgnParent(String crrgnParent) {
		this.crrgnParent = crrgnParent;
	}

	public String getCrrgnParent( ) {
		return crrgnParent;
	}

	public void setCrrgnZipCode(String crrgnZipCode) {
		this.crrgnZipCode = crrgnZipCode;
	}

	public String getCrrgnZipCode( ) {
		return crrgnZipCode;
	}

	public CoreRegionVO( ) { 
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		CoreRegion po = (CoreRegion) poObj;
		this.crrgnUuid = po.getCrrgnUuid();
		this.crrgnName = po.getCrrgnName();
		this.crrgnType = po.getCrrgnType();
		this.crrgnParent = po.getCrrgnParent();
		this.crrgnZipCode = po.getCrrgnZipCode();
	}

}