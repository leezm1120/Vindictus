package com.leezm.vindictus.activity;

import java.util.ArrayList;
import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.adapter.SimulationChooseAdapter;
import com.leezm.vindictus.fragment.BaseFragment;
import com.leezm.vindictus.fragment.SimulationChooseFragmentA;
import com.leezm.vindictus.fragment.SimulationChooseFragmentB;
import com.leezm.vindictus.fragment.SimulationChooseFragmentC;
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
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SimulationChooseActivity extends FragmentActivity implements
		OnClickListener {
	private SimulationChooseFragmentA mFragment1 = new SimulationChooseFragmentA();
	private SimulationChooseFragmentB mFragment2 = new SimulationChooseFragmentB();
	private SimulationChooseFragmentC mFragment3 = new SimulationChooseFragmentC();
	private FragmentTransaction transaction;
	private ViewPager mViewPager;
	private TextView tvA, tvB, tvC;
	private List<BaseFragment> list = new ArrayList<BaseFragment>();
	private List<TextView> tvList = new ArrayList<TextView>();
	private String changevalue, value;
	public static final int SUCCESS = 1, FAIL = 0, EARRINGS = 1, HEAD = 2,
			WEAPONS = 3, CHEST = 4, DEPUTYWEAPON = 5, LEG = 6, HAND = 7,
			BELT = 8, FOOT = 9, BROOCH = 10, RINGF = 11, RINGS = 12,
			ARTWARE = 13, BRACELETF = 14, BRACELETS = 15;
	@ViewInject(R.id.simulation_sure)
	RelativeLayout rLayoutsure;
	@ViewInject(R.id.simulation_back)
	RelativeLayout rLayoutback;

	// GDTAD
	@ViewInject(R.id.simulation_choose_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
				MobclickAgent.onEvent(SimulationChooseActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", SimulationChooseActivity.this);
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
		setContentView(R.layout.activity_simulation_choose);
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
		value = this.getIntent().getStringExtra("key");
		changevalue = new ChangeToDBName().changevaluename(this.getIntent()
				.getStringExtra("key"));

		// Toast.makeText(this, value, 1).show();

		Bundle bundle = new Bundle();
		bundle.putString("key", changevalue);
		mFragment1.setArguments(bundle);
		mFragment2.setArguments(bundle);
		mFragment3.setArguments(bundle);

		loadview();
		initFragment();
		changeView(0);
	}

	private void loadview() {

		mViewPager = (ViewPager) findViewById(R.id.simulation_view);
		tvA = (TextView) findViewById(R.id.hf_bt_one);
		tvB = (TextView) findViewById(R.id.hf_bt_two);
		tvC = (TextView) findViewById(R.id.hf_bt_three);
		tvList.add(tvA);
		tvList.add(tvB);
		tvList.add(tvC);

		tvA.setOnClickListener(this);
		tvB.setOnClickListener(this);
		tvC.setOnClickListener(this);
		rLayoutback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rLayoutsure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				result();
				finish();
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int code) {
				// TODO Auto-generated method stub
				for (int i = 0; i < tvList.size(); i++) {
					if (i == code) {

						tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
						tvList.get(i)
								.setBackgroundColor(Color.rgb(0, 157, 220));
					} else {
						tvList.get(i).setBackgroundColor(Color.rgb(0, 73, 144));
						tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void result() {
		Intent intent = new Intent();
		if (value.equals("EARRINGS")) {
			setResult(EARRINGS, intent);
		} else if (value.equals("HEAD")) {
			setResult(HEAD, intent);
		} else if (value.equals("WEAPONS")) {
			setResult(WEAPONS, intent);
		} else if (value.equals("CHEST")) {
			setResult(CHEST, intent);
		} else if (value.equals("DEPUTYWEAPON")) {
			setResult(DEPUTYWEAPON, intent);
		} else if (value.equals("LEG")) {
			setResult(LEG, intent);
		} else if (value.equals("HAND")) {
			setResult(HAND, intent);
		} else if (value.equals("BELT")) {
			setResult(BELT, intent);
		} else if (value.equals("FOOT")) {
			setResult(FOOT, intent);
		} else if (value.equals("BROOCH")) {
			setResult(BROOCH, intent);
		} else if (value.equals("RINGF")) {
			setResult(RINGF, intent);
		} else if (value.equals("RINGS")) {
			setResult(RINGS, intent);
		} else if (value.equals("ARTWARE")) {
			setResult(ARTWARE, intent);
		} else if (value.equals("BRACELETF")) {
			setResult(BRACELETF, intent);
		} else if (value.equals("BRACELETS")) {
			setResult(BRACELETS, intent);
		}
	}

	private void initFragment() {
		list.add(mFragment1);
		list.add(mFragment2);
		list.add(mFragment3);
		SimulationChooseAdapter sAdapter = new SimulationChooseAdapter(this,
				list);
		mViewPager.setAdapter(sAdapter);
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {
		case R.id.hf_bt_one:
			changeView(0);
			break;
		case R.id.hf_bt_two:
			changeView(1);
			break;
		case R.id.hf_bt_three:
			changeView(2);
			break;
		default:
			break;
		}
	}

	// 手动设置ViewPager要显示的视图
	private void changeView(int desTab) {
		mViewPager.setCurrentItem(desTab, true);
		for (int i = 0; i < tvList.size(); i++) {
			if (i == desTab) {
				tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
				tvList.get(i).setBackgroundColor(Color.rgb(0, 157, 220));
			} else {
				tvList.get(i).setBackgroundColor(Color.rgb(0, 73, 144));
				tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
			}
		}
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
	}

	public String returnvalue() {
		return value;

	}
}
