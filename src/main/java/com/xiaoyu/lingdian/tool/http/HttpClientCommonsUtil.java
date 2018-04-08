package com.xiaoyu.lingdian.tool.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public final class HttpClientCommonsUtil {
	
	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

	private static HttpClientCommonsUtil httpConnect = new HttpClientCommonsUtil();
	
	MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	
	/**
	 * 工厂方法
	 * 
	 * @return
	 */
	public static HttpClientCommonsUtil getInstance() {
		return httpConnect;
	}
	
	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url 请求的URL地址
	 * @param queryString 请求的查询参数,可以为null
	 * @param charset 字符集
	 * @return 返回请求响应的HTML
	 */
	public String doGet(String url, String queryString, String charset) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (StringUtils.isNotBlank(queryString)) {
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (URIException e) {
			logger.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e) {
			logger.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 * 
	 * @param url 请求的URL地址
	 * @param params 请求的查询参数,可以为null
	 * @param charset 字符集
	 * @return 返回请求响应的HTML
	 */
	public String doPost(String url, Map<String, String> params, String charset) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new PostMethod(url);
		if (params != null) {
			HttpMethodParams p = new HttpMethodParams();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				p.setParameter(entry.getKey(), entry.getValue());
			}
			method.setParams(p);
		}
		try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			logger.error("执行HTTP Post请求" + url + "时，发生异常！", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}

	public static String getMethod(String url, String param) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(url);
		HttpMethod method = null;
		if (("").equals(param)) {
			method = new GetMethod(url);
		} else {
			try {
				method = new GetMethod(url + "?" + URLEncoder.encode(param, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		method.releaseConnection();
		String response = "";
		try {
			httpClient.executeMethod(method);
			response = method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static String postMethod(String url, Map<String, String> map) throws IOException {
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(url);
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		Iterator<String> keyIterator = map.keySet().iterator();
		NameValuePair[] params = new NameValuePair[map.keySet().size()];
		int i = 0;
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			params[i] = new NameValuePair(key, map.get(key));
			i++;
		}
		post.setRequestBody(params);
		post.releaseConnection();
		HttpMethod method = post;
		String response = method.getResponseBodyAsString();
		return response;
	}

	/**
	 * 线程池发送GET请求
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public String doGet(String url, String charset) {
		HttpClient client = new HttpClient(connectionManager);
		client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		client.getHttpConnectionManager().getParams().setSoTimeout(55000);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		HttpMethod method = new GetMethod(url);
		String response;
		try {
			client.executeMethod(method);
			response = method.getResponseBodyAsString();
		} catch (HttpException e) {
			method.releaseConnection();
			return null;
		} catch (IOException e) {
			method.releaseConnection();
			return null;
		} finally {
			method.releaseConnection();
		}
		return response;
	}

}