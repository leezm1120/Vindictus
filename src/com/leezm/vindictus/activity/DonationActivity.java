package com.leezm.vindictus.activity;

import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DonationActivity extends Activity {

	@ViewInject(R.id.donation_info)
	TextView infotTextView;
	@ViewInject(R.id.donation_zfb)
	TextView zfbTextView;
	// @ViewInject(R.id.donation_wx)
	// TextView wxtTextView;
	@ViewInject(R.id.donation_patron)
	TextView zanzuTextView;
	@ViewInject(R.id.img_zfb)
	ImageView zfbView;
	@ViewInject(R.id.img_wx)
	ImageView wxView;
	@ViewInject(R.id.img_qq)
	ImageView qqView;
	
	private String info, zanzuString;
	private int a = 0, version = 0;
	ClipboardManager clipboardManager;
	Bitmap bitmap, bitmap2,bitmap3;
	BitmapUtils bUtils, bUtils2,bUtils3;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donation);
		ViewUtils.inject(this);
		version = android.os.Build.VERSION.SDK_INT;
		info = OnlineConfigAgent.getInstance().getConfigParams(this,
				"zanzuinfo");
		zanzuString = OnlineConfigAgent.getInstance().getConfigParams(this,
				"zanzuren");

		infotTextView.setText(info);
		if (!zanzuString.equals(".")) {
			zanzuTextView.setText(zanzuString);
		}
		zfbTextView.setText("支付宝账号：lizhihui_332968466@qq.com（点击复制）");
		clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

		zfbView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						bitmap = BitmapFactory.decodeResource(getResources(),
								R.raw.zfb);
						MediaStore.Images.Media.insertImage(
								DonationActivity.this.getContentResolver(),
								bitmap, "leezmvindictuszfb", "");
						Toast.makeText(DonationActivity.this, "支付宝二维码已保存到相册", 0)
								.show();
						a = 1;
					}
				});
			}
		});
		wxView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						bitmap2 = BitmapFactory.decodeResource(getResources(),
								R.raw.wx);
						MediaStore.Images.Media.insertImage(
								DonationActivity.this.getContentResolver(),
								bitmap2, "leezmvindictuswx", "");
						Toast.makeText(DonationActivity.this, "微信二维码已保存到相册", 0)
								.show();
						a = 1;
					}
				});
			}
		});
		qqView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new Handler().post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						bitmap3 = BitmapFactory.decodeResource(getResources(), R.raw.qq);
						MediaStore.Images.Media.insertImage(
								DonationActivity.this.getContentResolver(),
								bitmap3, "leezmvindictusqq", "");
						Toast.makeText(DonationActivity.this, "QQ二维码已保存到相册", 0)
								.show();
						a = 1;
					}
				});
			}
		});
		
		zfbTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clipboardManager.setText("lizhihui_332968466@qq.com");
				Toast.makeText(DonationActivity.this, "已复制到粘贴板", 1).show();
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

		if (a == 1) {
			if (version >= 19) {
				// 发送系统通知
				MediaScannerConnection.scanFile(
						this,
						new String[] { Environment
								.getExternalStoragePublicDirectory(
										Environment.DIRECTORY_DCIM).getPath()
								+ "/" }, null, null);
			} else {

				this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
						.parse("file://"
								+ Environment.getExternalStorageDirectory())));
			}
		}

	}

}
