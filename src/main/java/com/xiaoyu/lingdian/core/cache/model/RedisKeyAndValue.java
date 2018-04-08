package com.xiaoyu.lingdian.core.cache.model;

import com.xiaoyu.lingdian.entity.BaseEntity;

/**
 * redis存储类，必须序列化
 *
 */
public class RedisKeyAndValue extends BaseEntity {

	/**
     * 
     */
	private static final long serialVersionUID = -1959528436584592183L;

	private String key;
	private String value;
	private Long expire;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public RedisKeyAndValue(String key, String value, Long expire) {
		this.key = key;
		this.value = value;
		this.expire = expire;
	}

	public RedisKeyAndValue(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public RedisKeyAndValue() {
		super();
	}

}
