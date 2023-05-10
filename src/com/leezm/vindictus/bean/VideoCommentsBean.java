package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class VideoCommentsBean extends BmobObject {
	private String addUserId;
	private String comments;
	private String addTime;
	private String vId;

	public String getAddUserId() {
		return addUserId;
	}

	@Override
	public String toString() {
		return "VideoCommentsBean [addUserId=" + addUserId + ", comments="
				+ comments + ", addTime=" + addTime + ", vId=" + vId + "]";
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}
}
