package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
* 积分明细表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
public class BusiIntegralDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

		/**标识UNID*/
	private Integer bsidlUnid;

		/**标识UUID*/
	private String bsidlUuid;

		/**增减方向(1增2减)*/
	private Integer bsidlDire;

		/**数值*/
	private Integer bsidlNum;

		/**备注*/
	private String bsidlRemark;

		/**被修改人*/
	private String bsidlUser;

		/**创建时间*/
	private Date bsidlCdate;

		/**操作人*/
	private String bsidlOper;

			public Integer getBsidlUnid() {
		return bsidlUnid;
	}

	public void setBsidlUnid(Integer bsidlUnid) {
		this.bsidlUnid = bsidlUnid;
	}

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

		public BusiIntegralDetail() {
	}

}