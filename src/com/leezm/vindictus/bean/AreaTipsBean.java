package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class AreaTipsBean extends BmobObject {
	private String areaName;
	private String mapName;
	private String url;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "AreaTipsBean [areaName=" + areaName + ", mapName=" + mapName
				+ ", url=" + url + "]";
	}

	

}
