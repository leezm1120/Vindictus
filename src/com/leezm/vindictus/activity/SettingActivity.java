package com.leezm.vindictus.activity;

import com.lidroid.xutils.ViewUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ViewUtils.inject(this);
	}

}
