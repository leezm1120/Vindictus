package com.leezm.vindictus.activity;

import com.leezm.vindictus.adapter.RolesSelectAdapter;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ShowRolesDialogActivity extends Activity {

	@ViewInject(R.id.roles_show_listview)
	ListView listView;
	RolesSelectAdapter mAdapter;
	private String[] lStrings = { "img1", "img2", "img3", "img4", "img5",
			"img6", "img7", "img8", "img9", "img10", "img11" };
	SharedPreferences preferences;
	Editor editor;
	View view;
	private int l1 = 0, l2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role);
		ViewUtils.inject(this);
		// 设置窗口大小
		Window window = getWindow();
		WindowManager.LayoutParams lParams = window.getAttributes();
		Display display = getWindowManager().getDefaultDisplay();
		lParams.width = (int) (display.getWidth() * 0.9);
		lParams.height = (int) (display.getHeight() * 0.8);
		init();
	}

	private void init() {
		preferences = getSharedPreferences("ShowRolesDialogActivity",MODE_PRIVATE);
		editor = preferences.edit();
		l1 = preferences.getInt("index", 0);
		l2 = preferences.getInt("top", 0);
		mAdapter = new RolesSelectAdapter(this, lStrings);
		listView.setAdapter(mAdapter);
		listView.setSelectionFromTop(l1, l2);
		mAdapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ShowRolesDialogActivity.this, RolesSkillsDetailActivity.class);
				intent.putExtra("roles", lStrings[arg2]);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				//ShowRolesDialogActivity.this.finish();
			}
		});
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
		MobclickAgent.onPause(this);
		l1 = listView.getFirstVisiblePosition();
		view = listView.getChildAt(0);
		l2 = (view == null) ? 0 : view.getTop();
		editor.putInt("index", l1);
		editor.putInt("top", l2);
		editor.commit();

	}
}
