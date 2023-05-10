package com.leezm.vindictus.bean;

import cn.bmob.v3.BmobObject;

public class RolesSkillsBean extends BmobObject{
	private String roleName;
	private String skillName;
	private String iconUrl;
	private String info;
	private String awaken;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAwaken() {
		return awaken;
	}
	public void setAwaken(String awaken) {
		this.awaken = awaken;
	}
	@Override
	public String toString() {
		return "RolesSkillsBean [roleName=" + roleName + ", skillName="
				+ skillName + ", iconUrl=" + iconUrl + ", info=" + info
				+ ", awaken=" + awaken + "]";
	}
	
}
