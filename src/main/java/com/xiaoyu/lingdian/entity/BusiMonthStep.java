package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
* 月步数表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
public class BusiMonthStep extends BaseEntity {

	private static final long serialVersionUID = 1L;

		/**标识UNID*/
	private Integer bsmspUnid;

		/**标识UUID*/
	private String bsmspUuid;

		/**所属用户*/
	private String bsmspUser;

		/**创建时间*/
	private Date bsmspCdate;

		/**所属月*/
	private String bsmspMonth;

		/**步数*/
	private Integer bsmspStep;

			public Integer getBsmspUnid() {
		return bsmspUnid;
	}

	public void setBsmspUnid(Integer bsmspUnid) {
		this.bsmspUnid = bsmspUnid;
	}

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

		public BusiMonthStep() {
	}

}