package com.xiaoyu.lingdian.entity.weixin;

import java.util.HashMap;
import java.util.Map;

public class Constant {

	/**
	 * 默认编码
	 */
	public static String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	 */
	public static String NOTIFY_URL = "";

	/**
	 * 授权回调地址 必须要求80端口，可带参数
	 */
	public static String REDIRECT_URI = "";
	
	/**
	 * 商家appid
	 */
	public static final String APP_ID = "wxd728dc50b5aa8ee9";
	
	/**
	 * 商家appsecret
	 */
	public static final String APP_SECRET = "5dd5487549ca976997c0503a6b1bbee4";

	/**
	 * 商家partner
	 */
	public static String PARTNER = "";

	/**
	 * 商家后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	 */
	public static String PARTNERKEY = "";

	public static String TRADE_TYPE = "JSAPI";
	
	/**
	 * 授权作用域:弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
	 */
	public static final String SCOPE_USERINFO = "snsapi_userinfo";

	/**
	 * 授权作用域:不弹出授权页面，直接跳转，只能获取用户openid
	 */
	public static final String SCOPE_BASE = "snsapi_base";

	/**
	 * 初始化配置
	 */
	public static Map<String, String> hashMap = new HashMap<String, String>();

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	public final static String EVENT_LOCATION = "LOCATION";

	/**
	 * 事件类型：scan(用户已关注时的事件推送)
	 */
	public final static String EVENT_QRCODE_SCAN = "scan";
	
}