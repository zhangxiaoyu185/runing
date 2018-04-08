package com.xiaoyu.lingdian.tool.wx;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.xiaoyu.lingdian.entity.weixin.Constant;
import com.xiaoyu.lingdian.entity.weixin.PayOrder;
import com.xiaoyu.lingdian.entity.weixin.PayPackage;
import com.xiaoyu.lingdian.entity.weixin.PayResult;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.SystemUtil;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;

/**
 * 微信支付API
 */
public class WxPayUtil {

	private static Logger logger = LoggerFactory.getLogger("BASE_LOGGER");

	private static String CREATE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名
	 * 
	 * @param packageParams
	 * @param partner_key
	 * @param charset
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String createSign(SortedMap<String, String> packageParams, String partner_key, String charset) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + partner_key);
		String sign = MD5Util.encode(sb.toString(), charset).toUpperCase();
		return sign;
	}
	
	/**
	 * 获取请求预支付id报文
	 * 
	 * @param payOrder
	 * return PayPackage
	 */
	public PayPackage getPackage(PayOrder payOrder) {
		String spbill_create_ip = SystemUtil.getIPInfo(); //订单生成的机器 IP
		// 总金额以分为单位，不带小数点
		String totalFee = StringUtil.getMoney(payOrder.getFee().toString());
		// 随机字符串
		String nonce_str = StringUtil.getNonceStr();
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", payOrder.getAppid());
		packageParams.put("mch_id", payOrder.getMch_id());
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", payOrder.getBody());
		packageParams.put("attach", payOrder.getAttach());
		packageParams.put("out_trade_no", payOrder.getOut_trade_no());
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", payOrder.getNotify_url());
		packageParams.put("trade_type", Constant.TRADE_TYPE);
		packageParams.put("openid", payOrder.getOpenId());
		
		if (StringUtil.isEmpty(payOrder.getCharset())) {
			payOrder.setCharset(Constant.DEFAULT_CHARSET);
		}
		
		String sign = createSign(packageParams, payOrder.getPartnerkey(), payOrder.getCharset());
		String xml = "<xml>" 
				+ "<appid>" + payOrder.getAppid() + "</appid>" 
				+ "<mch_id>" + payOrder.getMch_id() + "</mch_id>"
				+ "<nonce_str>" + nonce_str + "</nonce_str>" 
				+ "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + payOrder.getBody() + "]]></body>" 
				+ "<out_trade_no>" + payOrder.getOut_trade_no() + "</out_trade_no>" 
				+ "<attach>" + payOrder.getAttach() + "</attach>" 
				+ "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" 
				+ "<notify_url>" + payOrder.getNotify_url() + "</notify_url>" 
				+ "<trade_type>" + Constant.TRADE_TYPE + "</trade_type>" 
				+ "<openid>" + payOrder.getOpenId() + "</openid>" 
				+ "</xml>";
		
		String prepay_id = getPayNo(CREATE_ORDER_URL, xml);
		logger.info("获取到的预支付ID：" + prepay_id);
		
		// 获取prepay_id后，拼接最后请求支付所需要的package
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = StringUtil.create_timestamp();
		String packages = "prepay_id=" + prepay_id;
		finalpackage.put("appId", payOrder.getAppid());
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		// 要签名
		String finalsign = createSign(finalpackage, payOrder.getPartnerkey(), payOrder.getCharset());
		PayPackage payPackage = new PayPackage();
		payPackage.setAppId(payOrder.getAppid());
		payPackage.setNonceStr(nonce_str);
		payPackage.setPackages(packages);
		payPackage.setPaySign(finalsign);
		payPackage.setSignType("MD5");
		payPackage.setTimeStamp(timestamp);
		logger.info("V3 jsApi package:" + "\"appId\":\"" + payOrder.getAppid() + "\",\"timeStamp\":\""
				+ timestamp + "\",\"nonceStr\":\"" + nonce_str
				+ "\",\"package\":\"" + packages + "\",\"signType\" : \"MD5"
				+ "\",\"paySign\":\"" + finalsign + "\"");
		return payPackage;
	}
	
	/**
	 * description:获取预支付id
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getPayNo(String url, String xmlParam) {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = getPostMethod(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = getSSLInstance().execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			if (jsonStr.indexOf("FAIL") != -1) {
				logger.info("FAIL:" + jsonStr);
				return prepay_id;
			}
			Map map = doXMLParse(jsonStr);
			prepay_id = (String) map.get("prepay_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}

	/**
	 * description:获取扫码支付连接
	 * 
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getCodeUrl(String url, String xmlParam) {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost = getPostMethod(url);
		String code_url = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = getSSLInstance().execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			if (jsonStr.indexOf("FAIL") != -1) {
				logger.info(jsonStr);
				return code_url;
			}
			Map map = doXMLParse(jsonStr);
			code_url = (String) map.get("code_url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_url;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map m = new HashMap();
		InputStream in = new ByteArrayInputStream(strxml.getBytes());
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}
		// 关闭流
		in.close();
		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	/**
	 * 解析微信回调通知xml
	 * 
	 * @param xml
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PayResult parseXmlToList(String xml) {
		PayResult wpr = new PayResult();
		try {
			Map m = new HashMap();
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					m.put(element.getName(), element.getValue());
				}
			}
			
			wpr.setAppid(m.get("appid").toString());
			String attach = m.get("attach")==null?"":m.get("attach").toString();
			wpr.setAttach(attach);
			wpr.setBankType(m.get("bank_type").toString());
			wpr.setCashFee(m.get("cash_fee").toString());
			wpr.setFeeType(m.get("fee_type").toString());
			wpr.setIsSubscribe(m.get("is_subscribe").toString());
			wpr.setMchId(m.get("mch_id").toString());
			wpr.setNonceStr(m.get("nonce_str").toString());
			wpr.setOpenid(m.get("openid").toString());
			wpr.setOutTradeNo(m.get("out_trade_no").toString());
			wpr.setResultCode(m.get("result_code").toString());
			wpr.setSign(m.get("sign").toString());
			wpr.setTimeEnd(m.get("time_end").toString());
			wpr.setTotalFee(m.get("total_fee").toString());
			wpr.setTradeType(m.get("trade_type").toString());
			wpr.setTransactionId(m.get("transaction_id").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wpr;
	}
	
	/**
	 * 获取SSL验证的HttpClient
	 * 
	 * @param httpClient
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static HttpClient getSSLInstance(){
		HttpClient httpClient = new DefaultHttpClient();
		ClientConnectionManager ccm = httpClient.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", MySSLSocketFactory.getInstance(), 443));
		httpClient =  new DefaultHttpClient(ccm, httpClient.getParams());
		return httpClient;
	}

	/**
	 * 模拟浏览器post提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpPost getPostMethod(String url) {
		HttpPost pmethod = new HttpPost(url); // 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Accept", "*/*");
		pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		pmethod.addHeader("Host", "api.mch.weixin.qq.com");
		pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		return pmethod;
	}

	/**
	 * 模拟浏览器GET提交
	 * 
	 * @param url
	 * @return
	 */
	public static HttpGet getGetMethod(String url) {
		HttpGet pmethod = new HttpGet(url);
		// 设置响应头信息
		pmethod.addHeader("Connection", "keep-alive");
		pmethod.addHeader("Cache-Control", "max-age=0");
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
		pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
		return pmethod;
	}

}