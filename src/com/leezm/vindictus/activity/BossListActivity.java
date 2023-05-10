package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.FoundBossAdapter;
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

public class BossListActivity extends Activity {
	@ViewInject(R.id.found_boss_listview)
	ListView listView;
	@ViewInject(R.id.found_boss_list_spinner)
	Spinner spinner;
	@ViewInject(R.id.found_boss_list_gosearch)
	ImageView imageView;
	@ViewInject(R.id.found_boss_list_edittext)
	EditText editText;
	private String spinnerString, edtString, advalue;
	private List<Map<String, String>> list;
	SharedPreferences preferences;
	Editor editor;
	private int s1 = 0, l1 = 0, l2 = 0;
	View view;
	FoundBossAdapter mAdapter;
	FoundInterface dao = new FoundImpl();
	// 复制
	ClipboardManager clipboardManager;
	// GDTAD
	@ViewInject(R.id.bosslist_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		// GDTAD
		// bannerView.loadAD();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
		l1 = listView.getFirstVisiblePosition();
		view = listView.getChildAt(0);
		l2 = (view == null) ? 0 : view.getTop();
		editor.putInt("index", l1);
		editor.putInt("top", l2);
		editor.commit();
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
				MobclickAgent.onEvent(BossListActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", BossListActivity.this);
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boss_list);
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
		preferences = this.getSharedPreferences("BossList",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		spinner.setSelection(preferences.getInt("area", 0));
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				spinnerString = spinner.getSelectedItem().toString();
				edtString = editText.getText().toString();
				list = dao.searchboss(
						new ChangeToDBName().changeallname(spinnerString),
						edtString);
				mAdapter = new FoundBossAdapter(BossListActivity.this, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				MobclickAgent.onEvent(BossListActivity.this, "bosssousuo");
			}
		});

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				s1 = arg2;
				editor.putInt("area", s1);
				editor.commit();
				spinnerString = spinner.getSelectedItem().toString();
				list = dao.searchboss(
						new ChangeToDBName().changeallname(spinnerString), "");
				mAdapter = new FoundBossAdapter(BossListActivity.this, list);
				listView.setAdapter(mAdapter);
				listView.setSelectionFromTop(preferences.getInt("index", 0),
						preferences.getInt("top", 0));
				mAdapter.notifyDataSetChanged();
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
				clipboardManager.setText("BOSS：" + list.get(arg2).get("title")
						+ "\n区域：" + list.get(arg2).get("area") + "\n攻击："
						+ list.get(arg2).get("att") + "\n防御："
						+ list.get(arg2).get("def") + "\n暴抗："
						+ list.get(arg2).get("resist") + "\n"
						+ list.get(arg2).get("remarks"));
				Toast.makeText(BossListActivity.this, "已复制", 0).show();
			}
		});
	}

}
