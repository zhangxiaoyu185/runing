package com.xiaoyu.lingdian.push;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//import org.springframework.stereotype.Component;

/**
 * 个推配置信息
 * 
 * @author zhangyu
 * @version 1.0.0
 * @date 2017/7/10 14:56
 * @see
 */
// @Component("pushConfigBean")
@Configuration
public class PushConfigBean {

	/** 个推主机 */
	@Value("${gexin.host}")
	private String host;

	/** 个推appId */
	@Value("${gexin.appId}")
	private String appId;

	/** 个推appKey */
	@Value("${gexin.appKey}")
	private String appKey;

	/** 个推密钥 */
	@Value("${gexin.masterSecret:http://sdk.open.api.igexin.com/apiex.htm}")
	private String masterSecret;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

}