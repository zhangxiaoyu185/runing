package com.xiaoyu.lingdian.entity.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.xiaoyu.lingdian.tool.xdata.CDataAdapter;

@XmlRootElement(name="item")
public class NewsModel {
	
	private String title;	
	private String description;	
	private String picUrl;	
	private String url;

	@XmlJavaTypeAdapter(CDataAdapter.class)
	@XmlElement(name="Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlJavaTypeAdapter(CDataAdapter.class)
	@XmlElement(name="Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlJavaTypeAdapter(CDataAdapter.class)
	@XmlElement(name="PicUrl")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@XmlJavaTypeAdapter(CDataAdapter.class)
	@XmlElement(name="Url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public NewsModel(String title, String description, String picUrl,
			String url) {
		super();
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}

	public NewsModel() {
		super();
	}
	
}