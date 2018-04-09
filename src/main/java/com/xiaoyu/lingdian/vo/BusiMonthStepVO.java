package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiMonthStep;
import java.util.Date;

/**
* 月步数表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
@ApiModel(value = "月步数")
public class BusiMonthStepVO implements BaseVO {

	/**标识UUID*/
	@ApiModelProperty(value = "标识UUID")
	private String bsmspUuid;

		/**所属用户*/
    @ApiModelProperty(value = "所属用户")
	private String bsmspUser;

		/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date bsmspCdate;

		/**所属月*/
    @ApiModelProperty(value = "所属月")
	private String bsmspMonth;

		/**步数*/
    @ApiModelProperty(value = "步数")
	private Integer bsmspStep;

		public String getBsmspUuid() {
		return bsmspUuid;
	}

	public void setBsmspUuid(String bsmspUuid) {
		this.bsmspUuid = bsmspUuid;
	}

		public String getBsmspUser() {
		return bsmspUser;
	}

	public void setBsmspUser(String bsmspUser) {
		this.bsmspUser = bsmspUser;
	}

		public Date getBsmspCdate() {
		return bsmspCdate;
	}

	public void setBsmspCdate(Date bsmspCdate) {
		this.bsmspCdate = bsmspCdate;
	}

		public String getBsmspMonth() {
		return bsmspMonth;
	}

	public void setBsmspMonth(String bsmspMonth) {
		this.bsmspMonth = bsmspMonth;
	}

		public Integer getBsmspStep() {
		return bsmspStep;
	}

	public void setBsmspStep(Integer bsmspStep) {
		this.bsmspStep = bsmspStep;
	}

		public BusiMonthStepVO() {
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		BusiMonthStep po = (BusiMonthStep) poObj;
		this.bsmspUuid = po.getBsmspUuid();
				this.bsmspUser = po.getBsmspUser();
				this.bsmspCdate = po.getBsmspCdate();
				this.bsmspMonth = po.getBsmspMonth();
				this.bsmspStep = po.getBsmspStep();
			}
}