package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class BlackListBean extends BmobObject{
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BlackListBean [userId=" + userId + "]";
	}
	
}
