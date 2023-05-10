package com.leezm.vindictus.dbutils.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.dbutils.dao.ManualInterface;

public class ManualImpl implements ManualInterface {

	private List<Map<String, String>> list;

	@Override
	public List showallenchant() { // 显示全部附魔
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from enchant", null);
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

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("style", sstyle);
			map.put("custompart", scustompart);
			map.put("customattribute", scustomattribute);
			map.put("customprovenance", scustomprovenance);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchEnchant(String txt) { // 附魔搜索
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from enchant where title like '%"
				+ txt + "%' or custompart like '%" + txt
				+ "%' or customattribute like '%" + txt
				+ "%' or customprovenance like '%" + txt + "%'", null);
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

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("style", sstyle);
			map.put("custompart", scustompart);
			map.put("customattribute", scustomattribute);
			map.put("customprovenance", scustomprovenance);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List selectLevelAndPartAndStyle(String Level, String Part,
			String Style) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from enchant where level like '%"
				+ Level + "%' and part like '%" + Part + "%' and style like '%"
				+ Style + "%'", null);
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

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("level", slevel);
			map.put("style", sstyle);
			map.put("custompart", scustompart);
			map.put("customattribute", scustomattribute);
			map.put("customprovenance", scustomprovenance);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchEquipment(String txt) { // 装备搜索
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from equipment where title like '%"+txt+"%' or role like '%"+txt+"%' or part like '%"+txt+"%' or type like '%"+txt+"%' or remarks like '%"+txt+"%'", null);
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
	public List showallequipment() { // 显示全部装备
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from equipment", null);
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
	public List selectequipmentspinner(String level, String role, String type) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from equipment where mohu like '%"+level+"%' and role like '%"+role+"%' and type like '%"+type+"%'", null);
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

}
