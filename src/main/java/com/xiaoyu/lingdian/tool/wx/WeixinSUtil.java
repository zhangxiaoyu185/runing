package com.xiaoyu.lingdian.tool.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyu.lingdian.entity.weixin.*;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.http.HttpUrlConnectionUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 小程序api工具类
 */
public class WeixinSUtil {
	
	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

	//登录凭证（code）换取用户登录态信息
	public final static String S_OAUTH2_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	//获取小程序二维码
	public final static String S_GET_WXA_CODE_UNLIMIT_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";

	/**
	 * 登录凭证（code）换取用户登录态信息
	 * 
	 * @param appid
	 * @param appsecret
	 * @param grantType,可不传，默认authorization_code
	 * @param code
	 * @return
	 */
	public static SopenidAndUnionid getSopenidAndUnionid(String appid,String appsecret, String grantType, String code){
		if(StringUtil.isEmpty(grantType)) {
			grantType = "authorization_code";
		}
		String requestUrl = S_OAUTH2_ACCESSTOKEN_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("JSCODE", code).replace("authorization_code", grantType);
		JSONObject jsonObject = HttpUrlConnectionUtil.sendWxHttpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			return JSON.parseObject(jsonObject.toString(), SopenidAndUnionid.class);
		}
		
		return null;
	}

	/**
	 * 获取小程序二维码
	 *
	 * @param path
	 * @param sceneStr
	 * @param page
	 * @param accessToken
	 * @return
	 */
	public static void getSWxaCodeUnlimit(String path, String sceneStr, String page, String accessToken) {
		RestTemplate rest = new RestTemplate();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			String requestUrl = S_GET_WXA_CODE_UNLIMIT_URL.replace("ACCESS_TOKEN", accessToken);
			Map<String,Object> param = new HashMap<>();
			param.put("scene", sceneStr);
			param.put("page", page);
			param.put("width", 430);
			param.put("auto_color", true);
//			Map<String,Object> line_color = new HashMap<>();
//			line_color.put("r", 0);
//			line_color.put("g", 0);
//			line_color.put("b", 0);
//			param.put("line_color", line_color);
			logger.info("调用生成微信URL接口传参:" + param);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			HttpEntity requestEntity = new HttpEntity(param, headers);
			ResponseEntity<byte[]> entity = rest.exchange(requestUrl, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
			logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
			byte[] result = entity.getBody();
			logger.info(Base64.encodeBase64String(result));
			inputStream = new ByteArrayInputStream(result);
			File file = new File(path);
			if (!file.exists()){
				file.createNewFile();
			}
			outputStream = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
		} catch (Exception e) {
			logger.error("调用小程序生成微信永久小程序码URL接口异常",e);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}