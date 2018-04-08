package com.xiaoyu.lingdian.enums;

public enum SexEnum {

	MALE(1, "男"),
	FEMALE(2, "女"),
	UNKNOWN(0, "未知");

    private Integer code;
	private String description;

	SexEnum(Integer code, String description) {
	    this.code = code;
		this.description = description;
	}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
