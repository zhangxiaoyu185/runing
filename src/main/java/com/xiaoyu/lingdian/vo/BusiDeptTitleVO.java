package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiDeptTitle;
import java.util.Date;

/**
* 部门称号记录表
* @author: zhangy
* @since: 2018年04月09日 16:57:33
* @history:
*/
@ApiModel(value = "部门称号记录")
public class BusiDeptTitleVO implements BaseVO {

	/**标识UUID*/
	@ApiModelProperty(value = "标识UUID")
	private String bsdteUuid;

		/**所属部门*/
    @ApiModelProperty(value = "所属部门")
	private String bsdteDept;

		/**所属月*/
    @ApiModelProperty(value = "所属月")
	private String bsdteMonth;

		/**获得称号*/
    @ApiModelProperty(value = "获得称号")
	private String bsdteTitle;

		/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date bsdteCdate;

		public String getBsdteUuid() {
		return bsdteUuid;
	}

	public void setBsdteUuid(String bsdteUuid) {
		this.bsdteUuid = bsdteUuid;
	}

		public String getBsdteDept() {
		return bsdteDept;
	}

	public void setBsdteDept(String bsdteDept) {
		this.bsdteDept = bsdteDept;
	}

		public String getBsdteMonth() {
		return bsdteMonth;
	}

	public void setBsdteMonth(String bsdteMonth) {
		this.bsdteMonth = bsdteMonth;
	}

		public String getBsdteTitle() {
		return bsdteTitle;
	}

	public void setBsdteTitle(String bsdteTitle) {
		this.bsdteTitle = bsdteTitle;
	}

		public Date getBsdteCdate() {
		return bsdteCdate;
	}

	public void setBsdteCdate(Date bsdteCdate) {
		this.bsdteCdate = bsdteCdate;
	}

		public BusiDeptTitleVO() {
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		BusiDeptTitle po = (BusiDeptTitle) poObj;
		this.bsdteUuid = po.getBsdteUuid();
				this.bsdteDept = po.getBsdteDept();
				this.bsdteMonth = po.getBsdteMonth();
				this.bsdteTitle = po.getBsdteTitle();
				this.bsdteCdate = po.getBsdteCdate();
			}
}