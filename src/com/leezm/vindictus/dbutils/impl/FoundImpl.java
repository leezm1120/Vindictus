package com.leezm.vindictus.dbutils.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leezm.vindictus.bean.RolesSkillsBean;
import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.dbutils.dao.FoundInterface;

public class FoundImpl implements FoundInterface {
	private List<Map<String, String>> list;
	private List<RolesSkillsBean> mList;

	@Override
	public List showallboss() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from battleslist", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String satt = cursor.getString(cursor.getColumnIndex("att"));
			String sdef = cursor.getString(cursor.getColumnIndex("def"));
			String sresist = cursor.getString(cursor.getColumnIndex("resist"));
			String sarea = cursor.getString(cursor.getColumnIndex("area"));
			String remarks = cursor.getString(cursor.getColumnIndex("remarks"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("att", satt);
			map.put("def", sdef);
			map.put("resist", sresist);
			map.put("area", sarea);
			map.put("remarks", remarks);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchboss(String area, String txt) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery(
				"select * from battleslist where area like '%" + area
						+ "%' and title like '%" + txt + "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String satt = cursor.getString(cursor.getColumnIndex("att"));
			String sdef = cursor.getString(cursor.getColumnIndex("def"));
			String sresist = cursor.getString(cursor.getColumnIndex("resist"));
			String sarea = cursor.getString(cursor.getColumnIndex("area"));
			String remarks = cursor.getString(cursor.getColumnIndex("remarks"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("att", satt);
			map.put("def", sdef);
			map.put("resist", sresist);
			map.put("area", sarea);
			map.put("remarks", remarks);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List showtitle(String role, String type) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from " + role
				+ " where type like '%" + type + "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String stype = cursor.getString(cursor.getColumnIndex("type"));
			String sarea = cursor.getString(cursor.getColumnIndex("area"));
			String scarbonf = cursor
					.getString(cursor.getColumnIndex("carbonf"));
			String scarbons = cursor
					.getString(cursor.getColumnIndex("carbons"));
			String schannel = cursor
					.getString(cursor.getColumnIndex("channel"));
			String sremarks = cursor
					.getString(cursor.getColumnIndex("remarks"));
			String sliliang = cursor
					.getString(cursor.getColumnIndex("liliang"));
			String szhili = cursor.getString(cursor.getColumnIndex("zhili"));
			String sminjie = cursor.getString(cursor.getColumnIndex("minjie"));
			String syizhi = cursor.getString(cursor.getColumnIndex("yizhi"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("type", stype);
			map.put("area", sarea);
			map.put("carbonf", scarbonf);
			map.put("carbons", scarbons);
			map.put("channel", schannel);
			map.put("remarks", sremarks);
			map.put("liliang", sliliang);
			map.put("zhili", szhili);
			map.put("minjie", sminjie);
			map.put("yizhi", syizhi);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchtitle(String role, String txt) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from " + role
				+ " where title like '%" + txt + "%' or type like '%" + txt
				+ "%' or area like '%" + txt + "%' or carbonf like '%" + txt
				+ "%' or carbons like '%" + txt + "%' or channel like '%" + txt
				+ "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String stitle = cursor.getString(cursor.getColumnIndex("title"));
			String stype = cursor.getString(cursor.getColumnIndex("type"));
			String sarea = cursor.getString(cursor.getColumnIndex("area"));
			String scarbonf = cursor
					.getString(cursor.getColumnIndex("carbonf"));
			String scarbons = cursor
					.getString(cursor.getColumnIndex("carbons"));
			String schannel = cursor
					.getString(cursor.getColumnIndex("channel"));
			String sremarks = cursor
					.getString(cursor.getColumnIndex("remarks"));
			String sliliang = cursor
					.getString(cursor.getColumnIndex("liliang"));
			String szhili = cursor.getString(cursor.getColumnIndex("zhili"));
			String sminjie = cursor.getString(cursor.getColumnIndex("minjie"));
			String syizhi = cursor.getString(cursor.getColumnIndex("yizhi"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("title", stitle);
			map.put("type", stype);
			map.put("area", sarea);
			map.put("carbonf", scarbonf);
			map.put("carbons", scarbons);
			map.put("channel", schannel);
			map.put("remarks", sremarks);
			map.put("liliang", sliliang);
			map.put("zhili", szhili);
			map.put("minjie", sminjie);
			map.put("yizhi", syizhi);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchskill(String part, String level, String text) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery(
				"select * from equipmentskill where part like '%" + part
						+ "%' and level like '%" + level
						+ "%' and remarks like '%" + text + "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String sname = cursor.getString(cursor.getColumnIndex("name"));
			String spart = cursor.getString(cursor.getColumnIndex("part"));
			String slevel = cursor.getString(cursor.getColumnIndex("level"));
			String sdetail = cursor.getString(cursor.getColumnIndex("detail"));
			String sretime = cursor.getString(cursor.getColumnIndex("retime"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("name", sname);
			map.put("part", spart);
			map.put("level", slevel);
			map.put("detail", sdetail);
			map.put("retime", sretime);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List searchareatips(String area) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		list = new ArrayList<Map<String, String>>();
		Cursor cursor = db.rawQuery("select * from areatips where area like '%"
				+ area + "%'", null);
		while (cursor.moveToNext()) {
			String sid = cursor.getString(cursor.getColumnIndex("_id"));
			String smap = cursor.getString(cursor.getColumnIndex("map"));
			String sarea = cursor.getString(cursor.getColumnIndex("area"));
			String sbattle = cursor.getString(cursor.getColumnIndex("battle"));
			String spathid = cursor.getString(cursor.getColumnIndex("pathid"));

			Map<String, String> map = new HashMap<String, String>();
			map.put("_id", sid);
			map.put("map", smap);
			map.put("area", sarea);
			map.put("battle", sbattle);
			map.put("pathid", spathid);

			list.add(map);
		}
		db.close();
		return list;
	}

	@Override
	public List rolesskills(String dbString) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
				VindictusDBManager.DB_PATH + "/" + VindictusDBManager.DB_NAME,
				null);
		mList = new ArrayList<RolesSkillsBean>();
		Cursor cursor = db.rawQuery("select * from "+dbString, null);
		
		while (cursor.moveToNext()) {
			String skill = cursor.getString(cursor.getColumnIndex("title"));
			
			RolesSkillsBean mBean = new RolesSkillsBean();
			mBean.setSkillName(skill);
			
			mList.add(mBean);
		}
		return mList;
	}

}
