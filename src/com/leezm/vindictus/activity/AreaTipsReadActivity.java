package com.leezm.vindictus.activity;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;


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

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class AreaTipsReadActivity extends Activity {
	public static final String ENCODING = "UTF-8";
	@ViewInject(R.id.area_tips_read_text)
	TextView textView;
	@ViewInject(R.id.area_tips_read_map_text)
	TextView mapTextView;
	@ViewInject(R.id.area_tips_read_area_text)
	TextView areaTextView;
	@ViewInject(R.id.area_tips_read_battle_text)
	TextView battleTextView;
	// GDTAD
	@ViewInject(R.id.area_tips_read_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_tips_read);
		ViewUtils.inject(this);
		// textView = (TextView) findViewById(R.id.area_tips_read_text);

		String path = this.getIntent().getStringExtra("path");
		String map = this.getIntent().getStringExtra("map");
		String area = this.getIntent().getStringExtra("area");
		String battle = this.getIntent().getStringExtra("battle");
		mapTextView.setText(map);
		areaTextView.setText(area);
		battleTextView.setText(battle);
		if (path.equals("gdpz")) {
			textView.setText(OnlineConfigAgent.getInstance().getConfigParams(
					this, "gdpzmd"));
		} else if (path.equals("shpz")) {
			textView.setText(OnlineConfigAgent.getInstance().getConfigParams(
					this, "shpzmd"));
		} else if (path.equals("tjpz")) {
			textView.setText(OnlineConfigAgent.getInstance().getConfigParams(
					this, "tjpzmd"));
		} else if (path.equals("lfpz")) {
			textView.setText(OnlineConfigAgent.getInstance().getConfigParams(
					this, "lfpzmd"));
		} else {
			textView.setText(getFromAssets(path));
		}

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
	}

	// GDTAD
	private void initgdtad() {
		bannerView = new BannerView(this, ADSize.BANNER,
				GdtAdConstants.GDTAPPID, GdtAdConstants.GDTBANNERID);
		bannerView.setRefresh(30);
		bannerView.setShowClose(true);
		bannerView.setADListener(new AbstractBannerADListener() {

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(AreaTipsReadActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", AreaTipsReadActivity.this);
			}
			
			@Override
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
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

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(
					"areatips/" + fileName + ".txt");
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
