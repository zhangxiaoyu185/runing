package com.xiaoyu.lingdian.enums;

public enum StatusEnum {

	ENABLE(1, "启动"),
	DISABLE(0, "禁用");

	private int code;
	private String description;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	StatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
