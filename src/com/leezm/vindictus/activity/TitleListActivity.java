package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.TitleAdapter;
import com.leezm.vindictus.adapter.TitleNatureAdapter;
import com.leezm.vindictus.dbutils.dao.FoundInterface;
import com.leezm.vindictus.dbutils.impl.FoundImpl;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.LogUtils;
import com.leezm.vindictus.utils.ChangeToDBName;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TitleListActivity extends Activity implements OnClickListener {
	private ListView listView;
	private List<Map<String, String>> list;
	private Spinner spinnerf, spinners;
	//TitleAdapter mAdapter;
	TitleNatureAdapter mAdapter2;
	FoundInterface dao = new FoundImpl();
	private String role, type = "";
	private ImageView imageView;
	private int s1 = 0, s2 = 0, l1 = 0, l2 = 0, g = 0;
	SharedPreferences preferences,preferences2;
	Editor editor,editor2;
	View view;
	// 复制
	ClipboardManager clipboardManager;
	// GDTAD
	@ViewInject(R.id.title_list_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		// GDTAD
		// bannerView.loadAD();
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
				MobclickAgent.onEvent(TitleListActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", TitleListActivity.this);
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
		setContentView(R.layout.activity_title_list);
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
		init();

	}

	private void init() {
		listView = (ListView) findViewById(R.id.found_title_listview);
		spinnerf = (Spinner) findViewById(R.id.found_title_spinnerf);
		spinners = (Spinner) findViewById(R.id.found_title_spinners);
		imageView = (ImageView) findViewById(R.id.found_title_search_goto);
		preferences = this.getSharedPreferences("TitleList",Context.MODE_PRIVATE);
		editor = preferences.edit();
		preferences2 = this.getSharedPreferences("titlehasdone", Context.MODE_PRIVATE);
		editor2 = preferences2.edit();
		spinnerf.setSelection(preferences.getInt("role", 0));
		spinners.setSelection(preferences.getInt("type", 0));
		l1 = preferences.getInt("index", 0);
		l2 = preferences.getInt("top", 0);
		imageView.setOnClickListener(this);
		spinnerf.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				getitem();
				// com.lidroid.xutils.util.LogUtils.e(spinnerStringf);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spinners.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				clipboardManager.setText("头衔：" + list.get(arg2).get("title")
						+ "\n类型：" + list.get(arg2).get("type") + "\n区域地图："
						+ list.get(arg2).get("area") + "---"
						+ list.get(arg2).get("carbonf") + "\n获得方法："
						+ list.get(arg2).get("channel") + "\n力量："
						+ list.get(arg2).get("liliang") + "\n智力："
						+ list.get(arg2).get("zhili") + "\n敏捷："
						+ list.get(arg2).get("minjie") + "\n意志："
						+ list.get(arg2).get("yizhi"));
				Toast.makeText(TitleListActivity.this, "已复制", 0).show();
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String title = list.get(arg2).get("title");
				LogUtils.e("", role+title);
				if (preferences2.getString(role+title, "").equals("")) {
					editor2.putString(role+title, "done");
					editor2.commit();
				} else {
					editor2.putString(role+title, "");
					editor2.commit();
				}
				mAdapter2.notifyDataSetChanged();
				return true;
			}
		});
	}

	private void getitem() {
		if (g == 0) {
			g = 1;
		} else {
			g = 1;
			role = (String) spinnerf.getSelectedItem();
			type = (String) spinners.getSelectedItem();
			s1 = spinnerf.getSelectedItemPosition();
			s2 = spinners.getSelectedItemPosition();
			editor.putInt("role", s1);
			editor.putInt("type", s2);
			editor.commit();
			spinnergo(role, type);
		}
	}

	private void spinnergo(String srole, String stype) {

		if (stype.equals("全部")) {
			type = "";
		}
		list = dao.showtitle(new ChangeToDBName().changerolename(srole), type);
		mAdapter2 = new TitleNatureAdapter(this, list,role);
		// mAdapter = new TitleAdapter(this, list);
		listView.setAdapter(mAdapter2);
		// listView.setAdapter(mAdapter);
		listView.setSelectionFromTop(l1, l2);
		// mAdapter.notifyDataSetChanged();
		l1 = l2 = 0;
		// LogUtils.e(String.valueOf(l1), String.valueOf(l2));
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.found_title_search_goto:
			CommonUtils.launchActivity(this, TitleListSearchActivity.class);
			break;

		default:
			break;
		}
	}

}
