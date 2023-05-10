package com.leezm.vindictus.dbutils.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.utils.LogUtils;

public class SimulationImpl implements SimulationInterface {
	private List<Map<String, String>> list;

	@Override
	public List selectequipment(String simulation, String level, String role,
			String remarks) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery(
				"select * from equipment where simulation like '%" + simulation
						+ "%' and coalesce(mohu,'') like '%" + level
						+ "%' and role like '%" + role
						+ "%' and coalesce(remarks,'') like '%" + remarks
						+ "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String slevel = cursor.getString(cursor.getColumnIndex("level"));
			String satt = cursor.getString(cursor.getColumnIndex("att"));
			String sbal = cursor.getString(cursor.getColumnIndex("bal"));
			String sattspd = cursor.getString(cursor.getColumnIndex("attspd"));
			String scritical = cursor.getString(cursor
					.getColumnIndex("critical"));
			String sstr = cursor.getString(cursor.getColumnIndex("str"));
			String smint = cursor.getString(cursor.getColumnIndex("mint"));
			String sagi = cursor.getString(cursor.getColumnIndex("agi"));
			String swil = cursor.getString(cursor.getColumnIndex("wil"));
			String spart = cursor.getString(cursor.getColumnIndex("part"));
			String stype = cursor.getString(cursor.getColumnIndex("type"));
			String srole = cursor.getString(cursor.getColumnIndex("role"));
			String scritresist = cursor.getString(cursor
					.getColumnIndex("critresist"));
			String sdef = cursor.getString(cursor.getColumnIndex("def"));
			String shp = cursor
					.getString(cursor.getColumnIndex("shengmingzhi"));
			String ssta = cursor.getString(cursor.getColumnIndex("sta"));
			String sremarks = cursor
					.getString(cursor.getColumnIndex("remarks"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("att", satt);
			map.put("bal", sbal);
			map.put("attspd", sattspd);
			map.put("critical", scritical);
			map.put("str", sstr);
			map.put("mint", smint);
			map.put("agi", sagi);
			map.put("wil", swil);
			map.put("part", spart);
			map.put("type", stype);
			map.put("role", srole);
			map.put("critresist", scritresist);
			map.put("def", sdef);
			map.put("shengmingzhi", shp);
			map.put("sta", ssta);
			map.put("remarks", sremarks);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List selectenchant(String part, String level, String style) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from enchant where simulation like '%"
				+ part + "%' and level like '%" + level + "%' and style like'%"
				+ style + "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String slevel = cursor.getString(cursor.getColumnIndex("level"));
			String sstyle = cursor.getString(cursor.getColumnIndex("style"));
			String scustompart = cursor.getString(cursor
					.getColumnIndex("custompart"));
			String scustomattribute = cursor.getString(cursor
					.getColumnIndex("customattribute"));
			String scustomprovenance = cursor.getString(cursor
					.getColumnIndex("customprovenance"));
			
			String satt = cursor.getString(cursor.getColumnIndex("att"));
			String sbal = cursor.getString(cursor.getColumnIndex("bal"));
			String sattspd = cursor.getString(cursor.getColumnIndex("attspd"));
			String scritical = cursor.getString(cursor
					.getColumnIndex("critical"));
			String sstr = cursor.getString(cursor.getColumnIndex("str"));
			String smint = cursor.getString(cursor.getColumnIndex("mint"));
			String sagi = cursor.getString(cursor.getColumnIndex("agi"));
			String swil = cursor.getString(cursor.getColumnIndex("wil"));
			String spart = cursor.getString(cursor.getColumnIndex("part"));
			String scritresist = cursor.getString(cursor
					.getColumnIndex("critresist"));
			String sdef = cursor.getString(cursor.getColumnIndex("def"));
			String shp = cursor
					.getString(cursor.getColumnIndex("shengmingzhi"));
			String ssta = cursor.getString(cursor.getColumnIndex("sta"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("style", sstyle);
			map.put("custompart", scustompart);
			map.put("customattribute", scustomattribute);
			map.put("customprovenance", scustomprovenance);
			map.put("att", satt);
			map.put("bal", sbal);
			map.put("attspd", sattspd);
			map.put("critical", scritical);
			map.put("str", sstr);
			map.put("mint", smint);
			map.put("agi", sagi);
			map.put("wil", swil);
			map.put("part", spart);
			map.put("critresist", scritresist);
			map.put("def", sdef);
			map.put("shengmingzhi", shp);
			map.put("sta", ssta);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List addequipment(String title, String level, String att,
			String bal, String critical, String attspd, String str,
			String mint, String agi, String wil, String part, String type,
			String role, String critresist, String def, String shengmingzhi,
			String sta, String remarks) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("insert into usersimulation (title,level,att,bal,attspd,critical,str,mint,agi,wil,part,type,role,critresist,def,shengmingzhi,sta,remarks)values('"
				+ title
				+ "','"
				+ level
				+ "',"
				+ att
				+ ","
				+ bal
				+ ","
				+ attspd
				+ ","
				+ critical
				+ ","
				+ str
				+ ","
				+ mint
				+ ","
				+ agi
				+ ","
				+ wil
				+ ",'"
				+ part
				+ "','"
				+ type
				+ "','"
				+ role
				+ "',"
				+ critresist
				+ ","
				+ def
				+ ","
				+ shengmingzhi
				+ ","
				+ sta
				+ ",'" + remarks + "')");
		db.close();
		return null;
	}

	@Override
	public List addenchant(String title, String level, String att, String bal,
			String critical, String attspd, String str, String mint,
			String agi, String wil, String part, String style,
			String critresist, String def, String shengmingzhi, String sta) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("insert into usersimulation (title,level,att,bal,attspd,critical,str,mint,agi,wil,part,type,critresist,def,shengmingzhi,sta)values('"
				+ title
				+ "','"
				+ level
				+ "',"
				+ att
				+ ","
				+ bal
				+ ","
				+ attspd
				+ ","
				+ critical
				+ ","
				+ str
				+ ","
				+ mint
				+ ","
				+ agi
				+ ","
				+ wil
				+ ",'"
				+ part
				+ "','"
				+ style
				+ "',"
				+ critresist
				+ ","
				+ def
				+ ","
				+ shengmingzhi
				+ ","+ sta+ ")");
				//new Object[]{title,level,att,bal,attspd,critical,str,mint,agi,wil,part,style,critresist,def,shengmingzhi,sta});
		db.close();
		return null;
	}

	@Override
	public List checkSimulation() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from usersimulation", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String slevel = cursor.getString(cursor.getColumnIndex("level"));
			String stype = cursor.getString(cursor.getColumnIndex("type"));
			String spart = cursor.getString(cursor.getColumnIndex("part"));
			String sremarks = cursor.getString(cursor.getColumnIndex("remarks"));
			
			

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("type", stype);
			map.put("part", spart);
			map.put("remarks", sremarks);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List delectSimulation(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("delete from usersimulation where _id="+id+"");
		db.close();
		return null;
	}

	@Override
	public List checkSimulationresultad() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select group_concat(title),sum(att),sum(bal),sum(attspd),sum(critical),sum(str),sum(mint),sum(agi),sum(wil),sum(critresist),sum(def),sum(shengmingzhi),sum(sta)from usersimulation", null);
		while (cursor.moveToNext()) {
			
			String stitle = cursor.getString(cursor.getColumnIndex("group_concat(title)"));
			String satt = cursor.getString(cursor.getColumnIndex("sum(att)"));
			String sbal = cursor.getString(cursor.getColumnIndex("sum(bal)"));
			String sattspd = cursor.getString(cursor.getColumnIndex("sum(attspd)"));
			String scritical = cursor.getString(cursor.getColumnIndex("sum(critical)"));
			String sstr = cursor.getString(cursor.getColumnIndex("sum(str)"));
			String smint = cursor.getString(cursor.getColumnIndex("sum(mint)"));
			String sagi = cursor.getString(cursor.getColumnIndex("sum(agi)"));
			String swil = cursor.getString(cursor.getColumnIndex("sum(wil)"));
			String scritresist = cursor.getString(cursor.getColumnIndex("sum(critresist)"));
			String sdef = cursor.getString(cursor.getColumnIndex("sum(def)"));
			String sshengmingzhi = cursor.getString(cursor.getColumnIndex("sum(shengmingzhi)"));
			String ssta = cursor.getString(cursor.getColumnIndex("sum(sta)"));
			
			//替换标题格式
			stitle = stitle.replaceAll(",", "\n");
			
			int gongji,liliang,fangyu,minjie,smz,yizhi,baoji;
			gongji = Integer.valueOf(satt);
			liliang = Integer.valueOf(sstr);
			fangyu = Integer.valueOf(sdef);
			minjie = Integer.valueOf(sagi);
			yizhi = Integer.valueOf(swil);
			smz = Integer.valueOf(sshengmingzhi);
			baoji = Integer.valueOf(scritical);
			
			gongji = (int) (gongji + (liliang*2.66));
			fangyu = fangyu + (minjie/2);
			smz = (int) (smz + (yizhi*0.6));
			if (yizhi<2000) {
				baoji = (int) (baoji + (yizhi*0.0075));
			}else {
				baoji = baoji + 15;
			}
			
			//暴击加上技能和人物自带3
			baoji = baoji + 31;
			//攻击加上自带
			gongji = gongji + 486;
			//防御加上自带
			fangyu = fangyu + 1280;
			
			satt = String.valueOf(gongji);
			sdef = String.valueOf(fangyu);
			sshengmingzhi = String.valueOf(smz);
			scritical = String.valueOf(baoji);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("att", satt);
			map.put("title", stitle);
			map.put("bal", sbal);
			map.put("critical", scritical);
			map.put("attspd", sattspd);
			map.put("str", sstr);
			map.put("mint", smint);
			map.put("agi", sagi);
			map.put("wil", swil);
			map.put("critresist", scritresist);
			map.put("def", sdef);
			map.put("shengmingzhi", sshengmingzhi);
			map.put("sta", ssta);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List savetoplan(String title, String att, String bal,
			String critical, String attspd, String str, String mint,
			String agi, String wil, String critresist, String def,
			String shengmingzhi, String sta) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		db.execSQL("insert into userplan (title,att,bal,attspd,critical,str,mint,agi,wil,critresist,def,shengmingzhi,sta)values('"
				+ title
				+ "',"
				+ att
				+ ","
				+ bal
				+ ","
				+ attspd
				+ ","
				+ critical
				+ ","
				+ str
				+ ","
				+ mint
				+ ","
				+ agi
				+ ","
				+ wil
				+ ","
				+ critresist
				+ ","
				+ def
				+ ","
				+ shengmingzhi
				+ ","+ sta+ ")");
		db.close();
		return null;
	}

	@Override
	public List delall() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("delete from usersimulation");
		db.close();
		return null;
	}

	@Override
	public List alldifferentplan() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from userplan", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String satt = cursor.getString(cursor.getColumnIndex("att"));
			String sbal = cursor.getString(cursor.getColumnIndex("bal"));
			String sattspd = cursor.getString(cursor.getColumnIndex("attspd"));
			String scritical = cursor.getString(cursor
					.getColumnIndex("critical"));
			String sstr = cursor.getString(cursor.getColumnIndex("str"));
			String smint = cursor.getString(cursor.getColumnIndex("mint"));
			String sagi = cursor.getString(cursor.getColumnIndex("agi"));
			String swil = cursor.getString(cursor.getColumnIndex("wil"));
			String scritresist = cursor.getString(cursor
					.getColumnIndex("critresist"));
			String sdef = cursor.getString(cursor.getColumnIndex("def"));
			String shp = cursor
					.getString(cursor.getColumnIndex("shengmingzhi"));
			String ssta = cursor.getString(cursor.getColumnIndex("sta"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("att", satt);
			map.put("bal", sbal);
			map.put("attspd", sattspd);
			map.put("critical", scritical);
			map.put("str", sstr);
			map.put("mint", smint);
			map.put("agi", sagi);
			map.put("wil", swil);
			map.put("critresist", scritresist);
			map.put("def", sdef);
			map.put("shengmingzhi", shp);
			map.put("sta", ssta);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List delplan(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("delete from userplan where _id="+id+"");
		db.close();
		return null;
	}

	@Override
	public List delallplan() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("delete from userplan");
		db.close();
		return null;
	}

	@Override
	public List addenchants(String title, String level, String att, String bal,
			String critical, String attspd, String str, String mint,
			String agi, String wil, String part, String style,
			String critresist, String def, String shengmingzhi, String sta) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		db.execSQL("insert into usersimulation (title,level,att,bal,attspd,critical,str,mint,agi,wil,part,type,critresist,def,shengmingzhi,sta)values('"
				+ title
				+ "','"
				+ level
				+ "',"
				+ att
				+ ","
				+ bal
				+ ","
				+ attspd
				+ ","
				+ critical
				+ ","
				+ str
				+ ","
				+ mint
				+ ","
				+ agi
				+ ","
				+ wil
				+ ",'"
				+ part
				+ "','"
				+ style
				+ "',"
				+ critresist
				+ ","
				+ def
				+ ","
				+ shengmingzhi
				+ ","+ sta+ ")");
				//new Object[]{title,level,att,bal,attspd,critical,str,mint,agi,wil,part,style,critresist,def,shengmingzhi,sta});
		db.close();
		return null;
	}

	@Override
	public List checkSimulationresultap() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select group_concat(title),sum(att),sum(bal),sum(attspd),sum(critical),sum(str),sum(mint),sum(agi),sum(wil),sum(critresist),sum(def),sum(shengmingzhi),sum(sta)from usersimulation", null);
		while (cursor.moveToNext()) {
			
			String stitle = cursor.getString(cursor.getColumnIndex("group_concat(title)"));
			String satt = cursor.getString(cursor.getColumnIndex("sum(att)"));
			String sbal = cursor.getString(cursor.getColumnIndex("sum(bal)"));
			String sattspd = cursor.getString(cursor.getColumnIndex("sum(attspd)"));
			String scritical = cursor.getString(cursor.getColumnIndex("sum(critical)"));
			String sstr = cursor.getString(cursor.getColumnIndex("sum(str)"));
			String smint = cursor.getString(cursor.getColumnIndex("sum(mint)"));
			String sagi = cursor.getString(cursor.getColumnIndex("sum(agi)"));
			String swil = cursor.getString(cursor.getColumnIndex("sum(wil)"));
			String scritresist = cursor.getString(cursor.getColumnIndex("sum(critresist)"));
			String sdef = cursor.getString(cursor.getColumnIndex("sum(def)"));
			String sshengmingzhi = cursor.getString(cursor.getColumnIndex("sum(shengmingzhi)"));
			String ssta = cursor.getString(cursor.getColumnIndex("sum(sta)"));

			//替换标题格式
			stitle = stitle.replaceAll(",", "\n");
			
			int gongji,zhili,fangyu,minjie,smz,yizhi,baoji;
			gongji = Integer.valueOf(satt);
			zhili = Integer.valueOf(smint);
			fangyu = Integer.valueOf(sdef);
			minjie = Integer.valueOf(sagi);
			yizhi = Integer.valueOf(swil);
			smz = Integer.valueOf(sshengmingzhi);
			baoji = Integer.valueOf(scritical);
			
			gongji = (int) (gongji + (zhili*2));
			fangyu = fangyu + (minjie/2);
			smz = (int) (smz + (yizhi*0.6));
			if (yizhi<2000) {
				baoji = (int) (baoji + (yizhi*0.0075));
			}else {
				baoji = baoji + 15;
			}
			
			//暴击加上技能和人物自带3
			baoji = baoji + 31;
			//攻击加上自带
			gongji = gongji + 1100;
			//防御加上自带
			fangyu = fangyu + 1280;
			
			satt = String.valueOf(gongji);
			sdef = String.valueOf(fangyu);
			sshengmingzhi = String.valueOf(smz);
			scritical = String.valueOf(baoji);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("att", satt);
			map.put("title", stitle);
			map.put("bal", sbal);
			map.put("critical", scritical);
			map.put("attspd", sattspd);
			map.put("str", sstr);
			map.put("mint", smint);
			map.put("agi", sagi);
			map.put("wil", swil);
			map.put("critresist", scritresist);
			map.put("def", sdef);
			map.put("shengmingzhi", sshengmingzhi);
			map.put("sta", ssta);

			list.add(map);
		}
		db.close();
		return list;
	}





}
