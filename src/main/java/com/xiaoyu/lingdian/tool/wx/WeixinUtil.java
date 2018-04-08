package com.xiaoyu.lingdian.tool.wx;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyu.lingdian.entity.weixin.AccessTokenModel;
import com.xiaoyu.lingdian.entity.weixin.CheckModel;
import com.xiaoyu.lingdian.entity.weixin.OpenidAndAccessToken;
import com.xiaoyu.lingdian.entity.weixin.ShareConfig;
import com.xiaoyu.lingdian.entity.weixin.WeixinUserInfo;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.encrypt.Sha1Util;
import com.xiaoyu.lingdian.tool.http.HttpUrlConnectionUtil;

/**
 * 微信api工具类
 */
public class WeixinUtil {
	
	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

	// 主动发送客服消息url
	public final static String SEND_CUSTOM_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 微信模板消息调用接口URL
	public final static String TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	// 通过OpenID获取查询用户所在分组url
	public final static String GET_PERSONGROUPID_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	// 生成临时二维码url
	public final static String TEMPORARY_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	// 生成永久二维码url
	public final static String PERMANENT_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	// 换取二维码url
	protected final static String GET_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/shoMyna Wangrcode?ticket=TICKET";
	// 获取关注者列表url
	public final static String GET_USERLIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	// 获取所有分组信息url
	public final static String GET_GROUPS_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	// 创建分组url
	public final static String CREATE_GROUPS_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	// 修改分组url
	public final static String UPDATE_GROUPS_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	// 移动用户分组url
	public final static String REMOVE_MEMBER_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
	// 上传多媒体文件url
	public final static String UPLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// 下载多媒体文件url
	public final static String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	// 菜单查询（GET）
	public final static String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	// OAuth2.0通过code换取网页授权access_token和openid(grant_type为authorization_code或refresh_token)
	public final static String OAUTH2_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// OAuth2.0拉取用户信息(需scope为 snsapi_userinfo)
	public final static String OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 根据OpenID机制获得用户详细信息
	public final static String OPENID_INFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 获取jsapi_ticket的接口地址（GET） 缓存7200秒  
	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 获取access_token的接口地址（GET） 限200（次/天）  
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
	
	/**
	 * 获取微信JS-SDK接口的临时票据jsapi_ticket,次数有限，需要保存
	 * 
	 * @param access_token 全局access_token
	 * @return
	 */
	public static String getJsApiTicket(String access_token) {
		String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		// 发起GET请求获取凭证
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		String ticket = null;
		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
			} catch (JSONException e) {	
				logger.error(e.getMessage());
				return "";
			}
		}
		return ticket;
	}
	
	/**
	 * 获取全局access_token,次数有限，需要保存
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static AccessTokenModel getAccessToken(String appid, String secret) {
		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("SECRET", secret);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			return JSON.parseObject(jsonObject.toString(), AccessTokenModel.class);
		}
		
		return null;
	}
	
	/**
	 * 通过code获取网页授权access_token,次数不限,不需要保存
	 * 
	 * @param appid
	 * @param appsecret
	 * @param grantType,可不传，默认authorization_code
	 * @param code
	 * @return
	 */
	public static OpenidAndAccessToken getOpenIdAndToken(String appid,String appsecret, String grantType, String code){
		if(StringUtil.isEmpty(grantType)) {
			grantType = "authorization_code";
		}
		String requestUrl = OAUTH2_ACCESSTOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code).replace("authorization_code", grantType);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			return JSON.parseObject(jsonObject.toString(), OpenidAndAccessToken.class);
		}
		
		return null;
	}
	
	/**
	 * 发送客服消息方法
	 * 
	 * @param accessToken 全局access_token
	 * @param jsonMsg json格式客服消息
	 * @return true|false
	 */
	public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
		boolean result = false;
		String requestUrl = SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
		// 发送客服消息
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				logger.info(errorMsg);
			} else {
				logger.error(errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 下载多媒体文件
	 * 
	 * @param accessToken 全局access_token
	 * @param mediaId 媒体文件ID
	 * @param savePath 保存路径目录
	 * @return String 保存文件名
	 */
	public static String getMedia(String accessToken, String mediaId, String savePath) {
		String filePath = null;
		String requestUrl = DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try{
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			if (!savePath.endsWith("\\")) {
				savePath += "\\";
			}
			// 根据内容类型获取扩展名
			String fileExt = StringUtil.getFileExt(conn.getHeaderField("Content-Type"));
			// 将mediaId作为文件名
			filePath = savePath + mediaId + fileExt;
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			fos.close();
			bis.close();
			conn.disconnect();
			logger.info("下载媒体文件成功,filePath=" + filePath);
			return mediaId + fileExt;
		}
		catch (Exception e) {
			logger.error("下载媒体文件失败:{}", e);
		}
		return "";
	}
	
	/**
	 * 查询用户所在分组
	 * 
	 * @param accessToken 全局access_token
	 * @param openId  普通用户的标识，对当前公众号唯一
	 * @return groupid
	 */
	public static int getPersonGroupId(String accessToken, String openId) {
		int groupId = 0;
		String requestUrl = GET_PERSONGROUPID_URL.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\"}";
		// 创建分组
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "POST",
				String.format(jsonData, openId));
		if (null != jsonObject) {
			try {
				groupId = jsonObject.getIntValue("groupid");
			}
			catch (JSONException e) {
				groupId = -1;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error(errorCode + ":" +errorMsg);
			}
		}
		return groupId;
	}
	
	/**
	 * 发送模版消息
	 * 
	 * @param jsonStr
	 * @param accessToken 全局access_token
	 * @return
	 */
	public static String sendTemplateMsg(String jsonStr, String accessToken) {
		String requestUrl = WeixinUtil.TEMPLATE_MSG_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "POST", jsonStr);
		return jsonObject.toString();
	}
	
	/**
	 * OAuth2.0拉取用户信息(需scope为 snsapi_userinfo)
	 * 
	 * @param access_token 网页授权access_token
	 * @param openid
	 * @return
	 */
	public static WeixinUserInfo getUserDetail(String access_token, String openid){
		String requestUrl = OAUTH2_USERINFO_URL.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			return JSON.parseObject(jsonObject.toString(), WeixinUserInfo.class);
		}
		
		return null;
	}

	/**
	 * 根据OpenID机制获得用户详细信息
	 * 
	 * @param access_token 网页授权access_token
	 * @param openid
	 * @return
	 */
	public static WeixinUserInfo getUserDetailByOpenID(String access_token, String openid){
		String requestUrl = OPENID_INFO.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			return JSON.parseObject(jsonObject.toString(), WeixinUserInfo.class);
		}
		
		return null;
	}

	/**
	 * 微信开发者验证
	 * 
	 * @param wxToken  
	 * @param tokenModel
	 * @return
	 */
	public static String validate(String wxToken, CheckModel tokenModel){
		String signature = tokenModel.getSignature();
		Long timestamp = tokenModel.getTimestamp();
		Long nonce = tokenModel.getNonce();
		String echostr = tokenModel.getEchostr();
		if(signature != null && timestamp != null && nonce != null) {
			String[] str = {wxToken, timestamp+"", nonce+""};
			Arrays.sort(str); // 字典序排序
			String bigStr = str[0] + str[1] + str[2];
	        // SHA1加密	
	        String digest = Sha1Util.getSha1(bigStr).toLowerCase();
	        // 确认请求来至微信
	        if (digest.equals(signature)) {
	        	return echostr;
	        }
		}
		return "error";
	}

	public static ShareConfig makeWXTicket(String jsapi_ticket, String appid, String url) {
	    String nonce_str = StringUtil.create_nonce_str();
	    String timestamp = StringUtil.create_timestamp();
	    String string1;
	    String signature = "";

	    //注意这里参数名必须全部小写，且必须有序
	    string1 = "jsapi_ticket=" + jsapi_ticket +
	              "&noncestr=" + nonce_str +
	              "&timestamp=" + timestamp +
	              "&url=" + url;
	    
	    signature = Sha1Util.getSha1(string1);
	    /**
	    try {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        signature = StringUtil.byteToHex(crypt.digest());  	        
	    }
	    catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    **/
	    ShareConfig shareConfig = new ShareConfig();
	    shareConfig.setUrl(url);
	    shareConfig.setAppid(appid);
	    shareConfig.setJsapi_ticket(jsapi_ticket);
	    shareConfig.setNonceStr(nonce_str);
	    shareConfig.setSignature(signature);
	    shareConfig.setTimestamp(timestamp);

	    return shareConfig;
	}

}