package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class MapImgBean extends BmobObject {
	private String name;
	private String imgurl;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "MapImgBean [name=" + name + ", imgurl=" + imgurl + ", type="
				+ type + "]";
	}
}
