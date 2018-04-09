package com.xiaoyu.lingdian.entity;

import java.util.Date;

/**
* 个人称号记录表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
public class BusiUserTitle extends BaseEntity {

	private static final long serialVersionUID = 1L;

		/**标识UNID*/
	private Integer bsuteUnid;

		/**标识UUID*/
	private String bsuteUuid;

		/**所属用户*/
	private String bsuteUser;

		/**所属月*/
	private String bsuteMonth;

		/**获得称号*/
	private String bsuteTitle;

		/**创建时间*/
	private Date bsuteCdate;

			public Integer getBsuteUnid() {
		return bsuteUnid;
	}

	public void setBsuteUnid(Integer bsuteUnid) {
		this.bsuteUnid = bsuteUnid;
	}

		public String getBsuteUuid() {
		return bsuteUuid;
	}

	public void setBsuteUuid(String bsuteUuid) {
		this.bsuteUuid = bsuteUuid;
	}

		public String getBsuteUser() {
		return bsuteUser;
	}

	public void setBsuteUser(String bsuteUser) {
		this.bsuteUser = bsuteUser;
	}

		public String getBsuteMonth() {
		return bsuteMonth;
	}

	public void setBsuteMonth(String bsuteMonth) {
		this.bsuteMonth = bsuteMonth;
	}

		public String getBsuteTitle() {
		return bsuteTitle;
	}

	public void setBsuteTitle(String bsuteTitle) {
		this.bsuteTitle = bsuteTitle;
	}

		public Date getBsuteCdate() {
		return bsuteCdate;
	}

	public void setBsuteCdate(Date bsuteCdate) {
		this.bsuteCdate = bsuteCdate;
	}

		public BusiUserTitle() {
	}

}