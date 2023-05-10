package com.leezm.vindictus.dbutils.dao;

import java.util.List;

import android.R.string;

public interface SimulationInterface {

	List selectequipment(String simulation,String level,String role,String remarks);//装备选择查询
	List selectenchant(String part,String level,String style);//附魔选择查询
	List addequipment(String title,String level,String att,String bal,String critical,String attspd,String str,String mint,String agi,String wil,String part,String type,String role,String critresist,String def,String shengmingzhi,String sta,String remarks);//添加装备
	List addenchant(String title, String level, String att,
			String bal, String critical, String attspd, String str,
			String mint, String agi, String wil, String part, String style, String critresist, String def, String shengmingzhi,
			String sta);//添加附魔
	List addenchants(String title, String level, String att,
			String bal, String critical, String attspd, String str,
			String mint, String agi, String wil, String part, String style, String critresist, String def, String shengmingzhi,
			String sta);//添加附魔进阶
	List checkSimulation();//查看配装
	List delectSimulation(String id);//删除配装
	List checkSimulationresultad();//查看配装结果
	List checkSimulationresultap();//查看配装结果
	List savetoplan(String title, String att,
			String bal, String critical, String attspd, String str,
			String mint, String agi, String wil, String critresist, String def, String shengmingzhi,
			String sta);//保存求和到另一个表
	List delall();//删除全部配装
	List alldifferentplan();//显示所有方案
	List delplan(String id);//删除方案
	List delallplan();//删除所有方案
	
//	List showallenchant();//显示全部附魔
//	List showallequipment();//显示全部装备
//	
//	List selectLevelAndPartAndStyle(String Level,String Part,String Style);//根据等级 部位
//	List selectequipmentspinner(String level,String role,String type);
//	List searchEnchant(String txt);
//	List searchEquipment(String txt);
}
