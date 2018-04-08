package com.xiaoyu.lingdian.entity.weixin;

public class Group {

	private Long count;
	private String id;
	private String name;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group(Long count, String id, String name) {
		this.count = count;
		this.id = id;
		this.name = name;
	}

	public Group() {
		super();
	}

}