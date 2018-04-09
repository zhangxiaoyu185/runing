package com.xiaoyu.lingdian.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.xiaoyu.lingdian.entity.BusiDept;
import java.util.Date;

/**
* 部门表
* @author: zhangy
* @since: 2018年04月09日 16:57:32
* @history:
*/
@ApiModel(value = "部门")
public class BusiDeptVO implements BaseVO {

	/**标识UUID*/
	@ApiModelProperty(value = "标识UUID")
	private String bsdetUuid;

		/**部门名称*/
    @ApiModelProperty(value = "部门名称")
	private String bsdetName;

		/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private Date bsdetCdate;

		/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private Date bsdetUdate;

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

		public BusiDeptVO() {
	}

	@Override
	public void convertPOToVO(Object poObj) {
		if (null == poObj) {
			return;
		}

		BusiDept po = (BusiDept) poObj;
		this.bsdetUuid = po.getBsdetUuid();
				this.bsdetName = po.getBsdetName();
				this.bsdetCdate = po.getBsdetCdate();
				this.bsdetUdate = po.getBsdetUdate();
			}
}