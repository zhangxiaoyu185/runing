package com.xiaoyu.lingdian.tool;

import java.io.IOException;
import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoyu.lingdian.tool.encrypt.AESUtil;

public class SendSMSUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		sendCash("http://apis.hzfacaiyu.com/sms/openCard", "3n1A4nMdVsTsRhjL", "15157135185", "5678", "chengkedz", "D2JwIPUS");
	}

	private static WebClient createClient(String uri) {
		WebClient client = WebClient.create(uri);
		// 非常重要
		client.type("application/json;charset=UTF-8");
		return client;
	}

	/**
	 * 注册发送短信
	 * 
	 * @param smsUrl
	 * @param smsKey
	 * @param mobileTel
	 * @param code
	 * @param loginname
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public static boolean sendRegister(String smsUrl, String smsKey, String mobileTel, String code, String loginname, String password) throws Exception {
		WebClient client = createClient(smsUrl);
		client.type("application/json;charset=UTF-8");
		Map<String, Object> params = new HashMap<String, Object>();	
		String tradeNo = AESUtil.getTradeNo();
		
		String content = "您在油卡服务平台的注册验证码："+code+",十分钟内有效!";
		/**
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		 **/
		params.put("tradeNo", tradeNo);
		params.put("userName", loginname);
		params.put("userPassword", password);
		params.put("phones", mobileTel);
		params.put("content", content);
		params.put("etnumber", "");
		String sign = AESUtil.encrypt(objectMapper.writeValueAsString(params), smsKey);
		params.put("sign", sign);
		params.put("userPassword", AESUtil.MD5(password));
		String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);

		Response response = client.post(body);
		int status = printResult(response);
		if (status <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 忘记密码发送短信
	 * 
	 * @param smsUrl
	 * @param smsKey
	 * @param mobileTel
	 * @param code
	 * @param loginname
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public static boolean sendForgetPwd(String smsUrl, String smsKey, String mobileTel, String code, String loginname, String password) throws Exception {
		WebClient client = createClient(smsUrl);
		client.type("application/json;charset=UTF-8");
		Map<String, Object> params = new HashMap<String, Object>();	
		String tradeNo = AESUtil.getTradeNo();
		
		String content = "您在油卡服务平台申请忘记密码的验证码是："+code+",十分钟内有效!";
		/**
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		**/
		params.put("tradeNo", tradeNo);
		params.put("userName", loginname);
		params.put("userPassword", password);
		params.put("phones", mobileTel);
		params.put("content", content);
		params.put("etnumber", "");
		String sign = AESUtil.encrypt(objectMapper.writeValueAsString(params), smsKey);
		params.put("sign", sign);
		params.put("userPassword", AESUtil.MD5(password));
		String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);

		Response response = client.post(body);
		int status = printResult(response);
		if (status <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 申请提现发送短信
	 * 
	 * @param smsUrl
	 * @param smsKey
	 * @param mobileTel
	 * @param code
	 * @param loginname
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public static boolean sendCash(String smsUrl, String smsKey, String mobileTel, String code, String loginname, String password) throws Exception {
		WebClient client = createClient(smsUrl);
		client.type("application/json;charset=UTF-8");
		Map<String, Object> params = new HashMap<String, Object>();	
		String tradeNo = AESUtil.getTradeNo();
		
		String content = "您在油卡服务平台申请提现,申请验证码："+code+",十分钟内有效!";
		/**
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		**/
		params.put("tradeNo", tradeNo);
		params.put("userName", loginname);
		params.put("userPassword", password);
		params.put("phones", mobileTel);
		params.put("content", content);
		params.put("etnumber", "");
		String sign = AESUtil.encrypt(objectMapper.writeValueAsString(params), smsKey);
		params.put("sign", sign);
		params.put("userPassword", AESUtil.MD5(password));
		String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);

		Response response = client.post(body);
		int status = printResult(response);
		if (status <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 完善信息发送短信
	 *
	 * @param smsUrl
	 * @param smsKey
	 * @param mobileTel
	 * @param code
	 * @param loginname
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static boolean completeInfo(String smsUrl, String smsKey, String mobileTel, String code, String loginname, String password) throws Exception {
		WebClient client = createClient(smsUrl);
		client.type("application/json;charset=UTF-8");
		Map<String, Object> params = new HashMap<String, Object>();
		String tradeNo = AESUtil.getTradeNo();

		String content = "您在油卡服务平台完善信息,验证码："+code+",十分钟内有效!";
		/**
		 try {
		 content = URLEncoder.encode(content, "UTF-8");
		 } catch (UnsupportedEncodingException e) {
		 return false;
		 }
		 **/
		params.put("tradeNo", tradeNo);
		params.put("userName", loginname);
		params.put("userPassword", password);
		params.put("phones", mobileTel);
		params.put("content", content);
		params.put("etnumber", "");
		String sign = AESUtil.encrypt(objectMapper.writeValueAsString(params), smsKey);
		params.put("sign", sign);
		params.put("userPassword", AESUtil.MD5(password));
		String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);

		Response response = client.post(body);
		int status = printResult(response);
		if (status <= 0) {
			return false;
		}
		return true;
	}

	private static int printResult(Response response) {
		try {
			InputStream stream = (InputStream) response.getEntity();
			int available = stream.available();
			if (available == 0) {
				return 0;
			}
			JsonNode responseNode = objectMapper.readTree(stream);
			String strReturn = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseNode);
			if (StringUtil.isEmpty(strReturn)) {
				return 0;
			}
			JSONObject jsonObject = (JSONObject) JSONObject.parse(strReturn);
			return jsonObject.getInteger("status");
		} catch (IOException e) {
			return 0;
		}
	}

}