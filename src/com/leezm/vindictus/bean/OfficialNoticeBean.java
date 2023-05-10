package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class OfficialNoticeBean extends BmobObject {

	private String title;
	private String url;
	private String date;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "OfficialNoticeBean [title=" + title + ", url=" + url
				+ ", date=" + date + ", type=" + type + "]";
	}

}
