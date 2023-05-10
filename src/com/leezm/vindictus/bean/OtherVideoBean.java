package com.leezm.vindictus.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class OtherVideoBean extends BmobObject {

	private String vId;
	private String vTitle;
	private String vImgUrl;
	private String vLength;
	private String addTime;
	private String vDetails;
	private String addUserId;
	private String vPublishedUser;
	private String vLink;


	public String getvTitle() {
		return vTitle;
	}

	public void setvTitle(String vTitle) {
		this.vTitle = vTitle;
	}

	public String getvImgUrl() {
		return vImgUrl;
	}

	public void setvImgUrl(String vImgUrl) {
		this.vImgUrl = vImgUrl;
	}

	public String getvLength() {
		return vLength;
	}

	public void setvLength(String vLength) {
		this.vLength = vLength;
	}



	public String getvDetails() {
		return vDetails;
	}

	public void setvDetails(String vDetails) {
		this.vDetails = vDetails;
	}

	public String getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(String addUserId) {
		this.addUserId = addUserId;
	}

	public String getvPublishedUser() {
		return vPublishedUser;
	}

	public void setvPublishedUser(String vPublishedUser) {
		this.vPublishedUser = vPublishedUser;
	}

	public String getvLink() {
		return vLink;
	}

	public void setvLink(String vLink) {
		this.vLink = vLink;
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

	@Override
	public String toString() {
		return "OtherVideoBean [vId=" + vId + ", vTitle=" + vTitle
				+ ", vImgUrl=" + vImgUrl + ", vLength=" + vLength
				+ ", addTime=" + addTime + ", vDetails=" + vDetails
				+ ", addUserId=" + addUserId + ", vPublishedUser="
				+ vPublishedUser + ", vLink=" + vLink + "]";
	}

	


}
