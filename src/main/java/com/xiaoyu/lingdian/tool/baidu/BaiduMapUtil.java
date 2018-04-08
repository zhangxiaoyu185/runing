package com.xiaoyu.lingdian.tool.baidu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyu.lingdian.tool.http.HttpUrlConnectionUtil;

public class BaiduMapUtil {

	/** 
	 * 查询地址的经纬度 
	 * 
	 * @param address
	 * @return map
	 */ 
	public Map<String, Object> getCoordinate(String address) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		try { 
			address = java.net.URLEncoder.encode(address, "UTF-8"); 
		} catch (UnsupportedEncodingException e1) { 
			return hashMap; 
		} 
		String key = "f247cdb592eb43ebac6ccd27f796e2d2"; 
		String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
		String data = HttpUrlConnectionUtil.sendGetHttp(url);
		JSONObject jsonObject = JSON.parseObject(data);
		if ("OK".equals(jsonObject.getString("status"))) {
			System.out.println(data);
			try {
				JSONObject localObject = jsonObject.getJSONObject("result").getJSONObject("location");
				hashMap.put("lng", localObject.getDoubleValue("lng")); //经度
				hashMap.put("lat", localObject.getDoubleValue("lat")); //纬度
			} catch (Exception e) {
			}			
		}
		return hashMap;
	} 

	/** 
	 * 根据经纬度查询地址
	 *  
	 * @param lng //经度
	 * @param lat //纬度
	 * @return
	 */
	public Map<String, Object> getAddress(double lng, double lat) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		String ak = "KT7OH721BvLLcg3vAUMk2t95ok2Fx1Gk"; 
		String url = String .format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json&pois=1", ak, lat, lng); 
		String data = HttpUrlConnectionUtil.sendGetHttp(url);
		System.out.println(data);	
		JSONObject jsonObject = JSON.parseObject(data);
		if ("OK".equals(jsonObject.getString("status"))) {
			try {
				JSONObject resultObject = jsonObject.getJSONObject("result");
				hashMap.put("address", resultObject.getString("formatted_address")); //详细地址
				JSONObject addressObject = resultObject.getJSONObject("addressComponent");
				hashMap.put("country", addressObject.getString("country"));
				hashMap.put("province", addressObject.getString("province"));
				hashMap.put("city", addressObject.getString("city"));
				hashMap.put("district", addressObject.getString("district"));
				hashMap.put("adcode", addressObject.getString("adcode"));
			} catch (Exception e) {
			}
		}
		return hashMap;
	} 

	public static void main(String[] args) throws IOException {
		BaiduMapUtil getLatAndLngByBaidu = new BaiduMapUtil();
		getLatAndLngByBaidu.getCoordinate("四川省成都市武侯区益州大道中段");
		getLatAndLngByBaidu.getAddress(104.063832, 30.54855);		
	}

}
