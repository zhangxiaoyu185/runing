package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
* 周步数表
* @author: zhangy
* @since: 2018年04月09日 16:57:34
* @history:
*/
public class BusiWeekStep extends BaseEntity {

	private static final long serialVersionUID = 1L;

		/**标识UNID*/
	private Integer bswspUnid;

		/**标识UUID*/
	private String bswspUuid;

		/**所属用户*/
	private String bswspUser;

		/**创建时间*/
	private Date bswspCdate;

		/**所属周*/
	private String bswspWeek;

		/**步数*/
	private Integer bswspStep;

			public Integer getBswspUnid() {
		return bswspUnid;
	}

	public void setBswspUnid(Integer bswspUnid) {
		this.bswspUnid = bswspUnid;
	}

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

		public BusiWeekStep() {
	}

}