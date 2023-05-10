package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.TitleNatureAdapter;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TitleListSearchActivity extends Activity {
	@ViewInject(R.id.found_title_search_spinner)
	Spinner spinner;
	@ViewInject(R.id.found_title_search_edittext)
	EditText editText;
	@ViewInject(R.id.found_title_search_gosearch)
	ImageView imageView;
	@ViewInject(R.id.found_title_search_listview)
	ListView listView;
	private String spinnerString, eString, role,rolesName;
	FoundInterface dao = new FoundImpl();
	private TitleNatureAdapter mNatureAdapter;
	private List<Map<String, String>> list;
	private int s1 = 0, l1 = 0, l2 = 0;
	View view;
	SharedPreferences preferences,preferences2;
	Editor editor,editor2;

	// 复制
	ClipboardManager clipboardManager;
	// GDTAD
	@ViewInject(R.id.title_list_search_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

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
		eString = editText.getText().toString();
		l1 = listView.getFirstVisiblePosition();
		view = listView.getChildAt(0);
		l2 = (view == null) ? 0 : view.getTop();
		editor.putInt("index", l1);
		editor.putInt("top", l2);
		editor.putString("searchlishi", eString);
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
				MobclickAgent.onEvent(TitleListSearchActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", TitleListSearchActivity.this);
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
		setContentView(R.layout.activity_title_list_search);
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
		preferences = this.getSharedPreferences("TitleListSearch",Context.MODE_PRIVATE);
		editor = preferences.edit();
		preferences2 = this.getSharedPreferences("titlehasdone", Context.MODE_PRIVATE);
		editor2 = preferences2.edit();
		
		spinner.setSelection(preferences.getInt("position", 0));
		l1 = preferences.getInt("index", 0);
		l2 = preferences.getInt("top", 0);
		editText.setText(preferences.getString("searchlishi", null));
		gosearch(spinnerString = spinner.getSelectedItem().toString(),
				preferences.getString("searchlishi", null));
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				spinnerString = spinner.getSelectedItem().toString();
				eString = editText.getText().toString();
				gosearch(spinnerString, eString);
			}
		});

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				s1 = arg2;
				editor.putInt("position", s1);
				editor.commit();
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
				clipboardManager.setText("头衔：" + list.get(arg2).get("title")
						+ "\n类型：" + list.get(arg2).get("type") + "\n区域地图："
						+ list.get(arg2).get("area") + "---"
						+ list.get(arg2).get("carbonf") + "\n获得方法："
						+ list.get(arg2).get("channel") + "\n力量："
						+ list.get(arg2).get("liliang") + "\n智力："
						+ list.get(arg2).get("zhili") + "\n敏捷："
						+ list.get(arg2).get("minjie") + "\n意志："
						+ list.get(arg2).get("yizhi"));
				Toast.makeText(TitleListSearchActivity.this, "已复制", 0).show();
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String title = list.get(arg2).get("title");
				LogUtils.e("", rolesName+title);
				if (preferences2.getString(rolesName+title, "").equals("")) {
					editor2.putString(rolesName+title, "done");
					editor2.commit();
				} else {
					editor2.putString(rolesName+title, "");
					editor2.commit();
				}
				mNatureAdapter.notifyDataSetChanged();
				return true;
			}
		});
	}

	public void gosearch(String srole, String txt) {
		MobclickAgent.onEvent(TitleListSearchActivity.this, "touxiansousuo");
		role = new ChangeToDBName().changerolename(srole);
		list = dao.searchtitle(role, txt);
		rolesName = spinner.getSelectedItem().toString();
		mNatureAdapter = new TitleNatureAdapter(this, list,rolesName);
		// mAdapter = new TitleAdapter(this, list);
		// listView.setAdapter(mAdapter);
		listView.setAdapter(mNatureAdapter);
		listView.setSelectionFromTop(l1, l2);
		l1 = l2 = 0;
		if (list.isEmpty()) {
			if (!editText.getText().toString().equals("")) {
				Toast.makeText(this, "无搜索结果，请重新输入", 1).show();
			}
		} 
	}
}
