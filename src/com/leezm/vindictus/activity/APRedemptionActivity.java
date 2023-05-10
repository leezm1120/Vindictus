package com.leezm.vindictus.activity;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class APRedemptionActivity extends Activity {
	@ViewInject(R.id.redemption_total)
	TextView textView;
	@ViewInject(R.id.redemption_level)
	Spinner spinnerlevel;
	@ViewInject(R.id.redemption_pinzhi)
	Spinner spinnerpinzhi;
	@ViewInject(R.id.redemption_qianghua)
	Spinner spinnerqianghua;
	@ViewInject(R.id.redemption_enchant_first)
	CheckBox enchantf;
	@ViewInject(R.id.redemption_enchant_second)
	CheckBox enchants;
	private int intlevel = 0, intqianghua = 0, intpinzhi = 0, intfumof = 0,
			intfumos = 0, inttotal = 0;
	private String level, qianghua, pinzhi, advalue;
	// GDTAD
	@ViewInject(R.id.apredemption_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;

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
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apredemption);
		ViewUtils.inject(this);
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
				MobclickAgent.onEvent(APRedemptionActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", APRedemptionActivity.this);
			}

			@Override
			public void onNoAD(int arg0) {
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}

			@Override
			public void onADReceiv() {
				LogUtils.i("AD_DEMO", "ONBannerReceive");
			}
		});
		adGroup.addView(bannerView);
		bannerView.loadAD();
	}

	private void init() {
		spinnerlevel.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinnerqianghua.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spinnerpinzhi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		enchantf.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					intfumof = 1000;
				} else {
					intfumof = 0;
				}
				getitem();
			}
		});
		enchants.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					intfumos = 1000;
				} else {
					intfumos = 0;
				}
				getitem();
			}

		});
	}

	private void getitem() {
		level = spinnerlevel.getSelectedItem().toString();
		qianghua = spinnerqianghua.getSelectedItem().toString();
		pinzhi = spinnerpinzhi.getSelectedItem().toString();
		if (level.equals("1-59级")) {
			intlevel = 0;
		} else if (level.equals("60-69级")) {
			intlevel = 500;
		} else if (level.equals("70-79级")) {
			intlevel = 2000;
		} else if (level.equals("80-85级")) {
			intlevel = 5000;
		} else if (level.equals("86-90级")) {
			intlevel = 16000;
		} else if (level.equals("工艺品")) {
			intlevel = 5000;
		}
		if (qianghua.equals("+10")) {
			intqianghua = 2000;
		} else if (qianghua.equals("+11")) {
			intqianghua = 5000;
		} else if (qianghua.equals("+8")) {
			intqianghua = 500;
		} else if (qianghua.equals("+9")) {
			intqianghua = 1000;
		} else if (qianghua.equals("+12")) {
			intqianghua = 8000;
		} else if (qianghua.equals("+13")) {
			intqianghua = 13000;
		} else if (qianghua.equals("+14")) {
			intqianghua = 20000;
		} else if (qianghua.equals("+0")) {
			intqianghua = 0;
		}
		if (pinzhi.equals("1星")) {
			intpinzhi = 0;
		} else if (pinzhi.equals("2星")) {
			intpinzhi = 0;
		} else if (pinzhi.equals("3星")) {
			intpinzhi = 1000;
		} else if (pinzhi.equals("4星")) {
			intpinzhi = 1000;
		} else if (pinzhi.equals("5星")) {
			intpinzhi = 2000;
		}
		inttotal = intlevel + intqianghua + intpinzhi + intfumof + intfumos;
		textView.setText("赎回当前装备所需AP：" + inttotal);
	}
}
