package com.leezm.vindictus.dbutils.dao;

import java.util.List;

public interface FoundInterface {

	List showallboss();//显示BOSS属性
	List searchboss(String area,String txt);//搜索BOSS属性
	
	List showtitle(String role,String type);//显示头衔列表
	List searchtitle(String role,String txt);//搜索头衔列表
	
	List searchskill(String part,String level,String text);//搜索装备技能列表
	
	List searchareatips(String area);//根据area区域搜索攻略
	
	List rolesskills(String dbString);//根据角色名显示技能
}
