package com.leezm.vindictus.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
@ViewInject(R.id.about_us_text)
TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ViewUtils.inject(this);
		String msg = this.getIntent().getStringExtra("msg");
		if (msg.equals(".")) {
			textView.setText("");
		}else {
			textView.setText(msg);
		}
		
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
	}

}
