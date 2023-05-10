package com.leezm.vindictus.activity;


import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.app.Activity;
import android.graphics.Bitmap;

public class ShowImgDialogActivity extends Activity {

	@ViewInject(R.id.main_show_img_view)
	ImageView view;
	@ViewInject(R.id.main_show_img_save)
	ImageView save;
	private String urlString,titleString;
	BitmapUtils bitmapUtils;
	Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_img_dialog);
		ViewUtils.inject(this);
		urlString = this.getIntent().getStringExtra("urlString");
		titleString = this.getIntent().getStringExtra("titleString");
		// 设置窗口大小
		Window window = getWindow();
		WindowManager.LayoutParams lParams = window.getAttributes();
		Display display = getWindowManager().getDefaultDisplay();
		lParams.width = (int) (display.getWidth() * 0.8);
		lParams.height = (int) (display.getHeight() * 0.8);
		init();
	}

	private void init() {
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultShowOriginal(true);
		bitmapUtils.display(view, urlString);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ShowImgDialogActivity.this.finish();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						//将ImageView中的图片转换成Bitmap
						view.buildDrawingCache();
						bitmap = view.getDrawingCache();
						MediaStore.Images.Media.insertImage(
								ShowImgDialogActivity.this.getContentResolver(),
								bitmap,titleString, "");
						CommonUtils.showShortToast("已保存到相册", ShowImgDialogActivity.this);
					}
				});
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
}
