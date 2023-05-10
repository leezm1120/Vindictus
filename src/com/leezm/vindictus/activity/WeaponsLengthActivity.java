package com.leezm.vindictus.activity;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class WeaponsLengthActivity extends Activity {

	@ViewInject(R.id.weapons_view)
	ImageView imageView;
	BitmapUtils bitmapUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weapons_length);
		ViewUtils.inject(this);
		init();
	}

	private void init(){
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultShowOriginal(true);
		bitmapUtils.display(imageView, "assets/wuqijuli.jpg");
	}
	
}
