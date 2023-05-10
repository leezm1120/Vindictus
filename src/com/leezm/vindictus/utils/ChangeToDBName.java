package com.leezm.vindictus.utils;

import java.util.Random;

public class ChangeToDBName {
	private String role, all, level, value,irandom;

	public String changeallname(String allname) {
		all = new String();
		if (allname.equals("全部")) {
			all = "";
		} else if (allname.equals("副武器")) {
			all = "副";
		} else {
			all = allname;
		}
		return all;
	}
	
	public String changelevelname(String levelname) {
		level = new String();
		if (levelname.equals("60以下")) {
			level = "Lv40";
		} 
		 else {
			level = levelname;
		}
		return level;
	}
	
	
	public String getrandom(int max,int min){
		Random random = new Random();
		irandom = String.valueOf((random.nextInt(max-min+1)+min));
		return irandom;
		
	}

	public String changevaluename(String valuename) {
		value = new String();
		if (valuename.equals("EARRINGS")) {
			value = "耳环";
		} else if (valuename.equals("HEAD")) {
			value = "头";
		} else if (valuename.equals("WEAPONS")) {
			value = "武器";
		} else if (valuename.equals("CHEST")) {
			value = "胸部";
		} else if (valuename.equals("DEPUTYWEAPON")) {
			value = "副";
		} else if (valuename.equals("LEG")) {
			value = "腿";
		} else if (valuename.equals("HAND")) {
			value = "手";
		} else if (valuename.equals("BELT")) {
			value = "腰带";
		} else if (valuename.equals("FOOT")) {
			value = "脚";
		} else if (valuename.equals("BROOCH")) {
			value = "胸针";
		} else if (valuename.equals("RINGF")) {
			value = "戒指";
		} else if (valuename.equals("RINGS")) {
			value = "戒指";
		} else if (valuename.equals("ARTWARE")) {
			value = "工艺品";
		} else if (valuename.equals("BRACELETF")) {
			value = "手镯";
		} else if (valuename.equals("BRACELETS")) {
			value = "手镯";
		}
		return value;

	}

	public String changerolename(String rolename) {
		role = new String();
		if (rolename.equals("黛莉娅")) {
			role = "titledelia";
		} else if (rolename.equals("艾莉莎")) {
			role = "titlearisha";
		} else if (rolename.equals("海基")) {
			role = "titlehagie";
		} else if (rolename.equals("琳")) {
			role = "titlelynn";
		} else if (rolename.equals("霍克")) {
			role = "titlehurk";
		} else if (rolename.equals("维拉")) {
			role = "titlevella";
		} else if (rolename.equals("凯伊")) {
			role = "titlekay";
		} else if (rolename.equals("卡鲁")) {
			role = "titlekarok";
		} else if (rolename.equals("伊菲")) {
			role = "titleevy";
		} else if (rolename.equals("菲欧娜")) {
			role = "titlefiona";
		} else if (rolename.equals("利斯塔")) {
			role = "titlelethita";
		}
		return role;
	}
}
