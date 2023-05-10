package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.EquipmentSkillAdapter;
import com.leezm.vindictus.dbutils.dao.FoundInterface;
import com.leezm.vindictus.dbutils.impl.FoundImpl;
import com.leezm.vindictus.utils.ChangeToDBName;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class EquipmentSkillActivity extends Activity {
	@ViewInject(R.id.found_skill_part_spinner)
	Spinner partsSpinner;
	@ViewInject(R.id.found_skill_level_spinner)
	Spinner levelSpinner;
	@ViewInject(R.id.found_skill_list_edittext)
	EditText editText;
	@ViewInject(R.id.found_skill_list_gosearch)
	ImageView btgosearch;
	@ViewInject(R.id.found_skill_listview)
	ListView listView;
	private List<Map<String, String>> list;
	FoundInterface dao = new FoundImpl();
	EquipmentSkillAdapter mAdapter;
	private String s1, s2, ed, advalue;
	private int set1, set2;
	SharedPreferences preferences;
	Editor editor;
	// GDTAD
	@ViewInject(R.id.skill_list_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	// 复制
	ClipboardManager clipboardManager;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	// GDTAD
	private void initgdtad() {
		bannerView = new BannerView(this, ADSize.BANNER,
				GdtAdConstants.GDTAPPID, GdtAdConstants.GDTBANNERID);
		bannerView.setRefresh(30);
		bannerView.setShowClose(true);
		bannerView.setADListener(new AbstractBannerADListener() {

			@Override
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(EquipmentSkillActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", EquipmentSkillActivity.this);
			}
			
			@Override
			public void onADReceiv() {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "ONBannerReceive");
			}
		});
		adGroup.addView(bannerView);
		bannerView.loadAD();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		editor.putInt("part", set1);
		editor.putInt("level", set2);
		editor.commit();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipment_skill);
		ViewUtils.inject(this);
		// 复制
		clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		// GDTAD
		advalue = OnlineConfigAgent.getInstance().getConfigParams(this,
				"adturn");
		initgdtad();
		if (advalue.equals("0")) {
			adGroup.removeAllViews();
			bannerView.destroy();
			bannerView = null;
		} else {
			if (bannerView == null) {
				initgdtad();
			}
			bannerView.loadAD();
		}
		preferences = this.getSharedPreferences("EquipmentSkillActivity",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		partsSpinner.setSelection(preferences.getInt("part", 0));
		levelSpinner.setSelection(preferences.getInt("level", 0));

		btgosearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				s1 = partsSpinner.getSelectedItem().toString();
				s2 = levelSpinner.getSelectedItem().toString();
				ed = editText.getText().toString();
				// list = dao.searchskillmohu(new
				// ChangeToDBName().changeallname(s1),
				// new ChangeToDBName().changeallname(s2), ed);
				list = dao.searchskill(new ChangeToDBName().changeallname(s1),
						new ChangeToDBName().changeallname(s2), ed);
				mAdapter = new EquipmentSkillAdapter(
						EquipmentSkillActivity.this, list);
				listView.setAdapter(mAdapter);
			}
		});

		partsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				gosearch();
				set1 = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		levelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				gosearch();
				set2 = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				clipboardManager.setText("装备技能：" + list.get(arg2).get("name")
						+ "\n类型：" + list.get(arg2).get("part") + "\n等级："
						+ list.get(arg2).get("level") + "\n重置时间："
						+ list.get(arg2).get("retime") + "\n技能效果："
						+ list.get(arg2).get("detail"));
				Toast.makeText(EquipmentSkillActivity.this, "已复制", 0).show();
			}
		});
	}

	private void gosearch() {
		s1 = partsSpinner.getSelectedItem().toString();
		s2 = levelSpinner.getSelectedItem().toString();
		list = dao.searchskill(new ChangeToDBName().changeallname(s1),
				new ChangeToDBName().changeallname(s2), "");
		mAdapter = new EquipmentSkillAdapter(this, list);
		listView.setAdapter(mAdapter);
	}

}
