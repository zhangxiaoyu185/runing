package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiIntegralDetail;
import java.util.Date;

/**
* 积分明细表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
@ApiModel(value = "积分明细")
public class BusiIntegralDetailVO implements BaseVO {

	/**标识UUID*/
	@ApiModelProperty(value = "标识UUID")
	private String bsidlUuid;

		/**增减方向(1增2减)*/
    @ApiModelProperty(value = "增减方向(1增2减)")
	private Integer bsidlDire;

		/**数值*/
    @ApiModelProperty(value = "数值")
	private Integer bsidlNum;

		/**备注*/
    @ApiModelProperty(value = "备注")
	private String bsidlRemark;

		/**被修改人*/
    @ApiModelProperty(value = "被修改人")
	private String bsidlUser;

		/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date bsidlCdate;

		/**操作人*/
    @ApiModelProperty(value = "操作人")
	private String bsidlOper;

		public String getBsidlUuid() {
		return bsidlUuid;
	}

	public void setBsidlUuid(String bsidlUuid) {
		this.bsidlUuid = bsidlUuid;
	}

		public Integer getBsidlDire() {
		return bsidlDire;
	}

	public void setBsidlDire(Integer bsidlDire) {
		this.bsidlDire = bsidlDire;
	}

		public Integer getBsidlNum() {
		return bsidlNum;
	}

	public void setBsidlNum(Integer bsidlNum) {
		this.bsidlNum = bsidlNum;
	}

		public String getBsidlRemark() {
		return bsidlRemark;
	}

	public void setBsidlRemark(String bsidlRemark) {
		this.bsidlRemark = bsidlRemark;
	}

		public String getBsidlUser() {
		return bsidlUser;
	}

	public void setBsidlUser(String bsidlUser) {
		this.bsidlUser = bsidlUser;
	}

		public Date getBsidlCdate() {
		return bsidlCdate;
	}

	public void setBsidlCdate(Date bsidlCdate) {
		this.bsidlCdate = bsidlCdate;
	}

		public String getBsidlOper() {
		return bsidlOper;
	}

	public void setBsidlOper(String bsidlOper) {
		this.bsidlOper = bsidlOper;
	}

		public BusiIntegralDetailVO() {
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		BusiIntegralDetail po = (BusiIntegralDetail) poObj;
		this.bsidlUuid = po.getBsidlUuid();
				this.bsidlDire = po.getBsidlDire();
				this.bsidlNum = po.getBsidlNum();
				this.bsidlRemark = po.getBsidlRemark();
				this.bsidlUser = po.getBsidlUser();
				this.bsidlCdate = po.getBsidlCdate();
				this.bsidlOper = po.getBsidlOper();
			}
}