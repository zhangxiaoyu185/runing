package com.xiaoyu.lingdian.tool.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");	
	
	/**
	 * 发送HTTP_GET请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL    请求地址(含参数)
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset){
		long responseLength = 0;       //响应长度
		String responseContent = null; //响应内容
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
		try{
			HttpResponse response = httpClient.execute(httpGet); //执行GET请求
			HttpEntity entity = response.getEntity();            //获取响应实体
			if(null != entity){
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); //Consume response content
			}
			logger.info("请求地址: " + httpGet.getURI());
			logger.info("响应状态: " + response.getStatusLine());
			logger.info("响应长度: " + responseLength);
			logger.info("响应内容: " + responseContent);
		}catch(ClientProtocolException e){
			logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
		}catch(ParseException e){
			logger.error(e.getMessage(), e);
		}catch(IOException e){
			logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return responseContent;
	}	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder){
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param sendData      请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,传入该参数中
	 * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		//httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		try{
			if(isEncoder){
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for(String str : sendData.split("&")){
					formParams.add(new BasicNameValuePair(str.substring(0,str.indexOf("=")), str.substring(str.indexOf("=")+1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset==null ? "UTF-8" : encodeCharset)));
			}else{
				httpPost.setEntity(new StringEntity(sendData));
			}
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}	
	
	/**
	 * 发送HTTP_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
		for(Map.Entry<String,String> entry : params.entrySet()){
			formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try{
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		}catch(Exception e){
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}	
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params){
		return sendPostSSLRequest(reqURL, params, null, null);
	}	
	
	/**
	 * 发送HTTPS_POST请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset){
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {return null;}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{xtm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for(Map.Entry<String,String> entry : params.entrySet()){
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	/**
	 * 发送HTTPS_POST,类型为JSON的请求
	 * @see 该方法为<code>sendPostSSLRequest(String,String,String,String)</code>方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL, String params){
		return sendPostSSLRequest(reqURL, params, null, null);
	}
	
	/**
	 * 发送HTTPS_POST，类型为JSON的请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL        请求地址
	 * @param params        请求参数
	 * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL, String params, String encodeCharset, String decodeCharset){
		String responseContent = "";
		HttpClient httpClient = new DefaultHttpClient();
		X509TrustManager xtm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {return null;}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{xtm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpPost httpPost = new HttpPost(reqURL);
			httpPost.setEntity(new StringEntity(params, encodeCharset == null ? "UTF-8" : encodeCharset));
			httpPost.addHeader("content-type", "application/json"); 
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}	
	
	/**
	 * 发送HTTPS_GET请求
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL    请求地址(含参数)
	 * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetSSLRequest(String reqURL, String decodeCharset){
		long responseLength = 0;       //响应长度
		String responseContent = null; //响应内容
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager(){
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {return null;}
		};
		
		try{
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[]{xtm}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
			
			HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
			HttpResponse response = httpClient.execute(httpGet); //执行GET请求
			HttpEntity entity = response.getEntity();            //获取响应实体
			if(null != entity){
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); //Consume response content
			}
			logger.info("请求地址: " + httpGet.getURI());
			logger.info("响应状态: " + response.getStatusLine());
			logger.info("响应长度: " + responseLength);
			logger.info("响应内容: " + responseContent);
		} catch (Exception e) {
			logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
		}finally{
			httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源
		}
		return responseContent;
	}
	
	/**
	 * soap方式发送webService
	 * 
	 * @param url
	 * @param paramStr
	 * @return
	 */
	public static String soapWebService(String url,String paramStr) {
		String responseString="";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost;
		try {
			//发送post请求 传输无参数据 方法
			httppost = new HttpPost(url); 
			StringEntity myEntity = new StringEntity(paramStr, "utf-8");
			//httppost.addHeader("AllowAutoRedirect", "False");
			httppost.addHeader("Content-Type", "text/xml; charset=utf-8");
			//Content-Type:text/html(html),image/jpeg(jpg),text/plain(txt),text/javascript(javascript),text/json(json),application/x-json(json)
			httppost.addHeader("Method","POST");
			httppost.addHeader("SOAPAction","");
			httppost.addHeader("Timeout","100000");
			//发送实体
			httppost.setEntity(myEntity); 
			//执行请求
			HttpResponse response = httpclient.execute(httppost);
			
			HttpEntity resEntity = response.getEntity();
			InputStreamReader reader = new InputStreamReader(resEntity.getContent(), "UTF-8");
			StringBuffer sb=new StringBuffer();
			char[] buff = new char[1024]; 
			int length = 0;
			while ((length = reader.read(buff)) != -1) {
			  sb.append(new String(buff, 0, length));
			} 
			responseString= sb.toString();
		}  catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭连接，释放资源
			httpclient.getConnectionManager().shutdown();
		}
		return responseString;
	}

}