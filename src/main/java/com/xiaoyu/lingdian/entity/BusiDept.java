package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
* 部门表
* @author: zhangy
* @since: 2018年04月09日 16:57:32
* @history:
*/
public class BusiDept extends BaseEntity {

	private static final long serialVersionUID = 1L;

		/**标识UNID*/
	private Integer bsdetUnid;

		/**标识UUID*/
	private String bsdetUuid;

		/**部门名称*/
	private String bsdetName;

		/**创建时间*/
	private Date bsdetCdate;

		/**更新时间*/
	private Date bsdetUdate;

			public Integer getBsdetUnid() {
		return bsdetUnid;
	}

	public void setBsdetUnid(Integer bsdetUnid) {
		this.bsdetUnid = bsdetUnid;
	}

		public String getBsdetUuid() {
		return bsdetUuid;
	}

	public void setBsdetUuid(String bsdetUuid) {
		this.bsdetUuid = bsdetUuid;
	}

		public String getBsdetName() {
		return bsdetName;
	}

	public void setBsdetName(String bsdetName) {
		this.bsdetName = bsdetName;
	}

		public Date getBsdetCdate() {
		return bsdetCdate;
	}

	public void setBsdetCdate(Date bsdetCdate) {
		this.bsdetCdate = bsdetCdate;
	}

		public Date getBsdetUdate() {
		return bsdetUdate;
	}

	public void setBsdetUdate(Date bsdetUdate) {
		this.bsdetUdate = bsdetUdate;
	}

		public BusiDept() {
	}

}