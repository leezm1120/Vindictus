package com.leezm.vindictus.dbutils.dao;

import java.util.List;

public interface ManualInterface {

	List showallenchant();//显示全部附魔
	List showallequipment();//显示全部装备
	
	List selectLevelAndPartAndStyle(String Level,String Part,String Style);//根据等级 部位
	List selectequipmentspinner(String level,String role,String type);
	List searchEnchant(String txt);
	List searchEquipment(String txt);
}
