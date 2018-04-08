package com.xiaoyu.lingdian.entity.weixin;

public class PayOrder {

	private String appid;
	
	private String appsecret;
	
	private String attach;
	
	private String openId;
	
	private String body;
	
	private Double fee;
	
	/**
	 * 商户订单号
	 */
	private String out_trade_no;
	
	private String notify_url;
	
	private String mch_id;
	
	private String partnerkey;
	
	private String charset;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getPartnerkey() {
		return partnerkey;
	}

	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public PayOrder(String appid, String appsecret, String attach,
			String openId, String body, Double fee, String out_trade_no,
			String notify_url, String mch_id, String partnerkey, String charset) {
		super();
		this.appid = appid;
		this.appsecret = appsecret;
		this.attach = attach;
		this.openId = openId;
		this.body = body;
		this.fee = fee;
		this.out_trade_no = out_trade_no;
		this.notify_url = notify_url;
		this.mch_id = mch_id;
		this.partnerkey = partnerkey;
		this.charset = charset;
	}
	
}
