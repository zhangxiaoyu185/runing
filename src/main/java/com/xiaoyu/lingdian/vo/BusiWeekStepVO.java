package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiWeekStep;
import java.util.Date;

/**
* 周步数表
* @author: zhangy
* @since: 2018年04月09日 16:57:34
* @history:
*/
@ApiModel(value = "周步数")
public class BusiWeekStepVO implements BaseVO {

	/**标识UUID*/
	@ApiModelProperty(value = "标识UUID")
	private String bswspUuid;

		/**所属用户*/
    @ApiModelProperty(value = "所属用户")
	private String bswspUser;

		/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date bswspCdate;

		/**所属周*/
    @ApiModelProperty(value = "所属周")
	private String bswspWeek;

		/**步数*/
    @ApiModelProperty(value = "步数")
	private Integer bswspStep;

		public String getBswspUuid() {
		return bswspUuid;
	}

	public void setBswspUuid(String bswspUuid) {
		this.bswspUuid = bswspUuid;
	}

		public String getBswspUser() {
		return bswspUser;
	}

	public void setBswspUser(String bswspUser) {
		this.bswspUser = bswspUser;
	}

		public Date getBswspCdate() {
		return bswspCdate;
	}

	public void setBswspCdate(Date bswspCdate) {
		this.bswspCdate = bswspCdate;
	}

		public String getBswspWeek() {
		return bswspWeek;
	}

	public void setBswspWeek(String bswspWeek) {
		this.bswspWeek = bswspWeek;
	}

		public Integer getBswspStep() {
		return bswspStep;
	}

	public void setBswspStep(Integer bswspStep) {
		this.bswspStep = bswspStep;
	}

		public BusiWeekStepVO() {
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		BusiWeekStep po = (BusiWeekStep) poObj;
		this.bswspUuid = po.getBswspUuid();
				this.bswspUser = po.getBswspUser();
				this.bswspCdate = po.getBswspCdate();
				this.bswspWeek = po.getBswspWeek();
				this.bswspStep = po.getBswspStep();
			}
}