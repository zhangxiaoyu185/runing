package com.xiaoyu.lingdian.tool.xdata;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 通过注解将xml绑定到javabean的成员变量上
 */
public class CDataAdapter extends XmlAdapter<String, String> {

	// 从javabean到xml的适配方法
	@Override
	public String marshal(String str) throws Exception {
		return "<![CDATA[" + str + "]]>";
	}

	// 从xml到javabean的适配方法
	@Override
	public String unmarshal(String str) throws Exception {
		return str;
	}
	
}