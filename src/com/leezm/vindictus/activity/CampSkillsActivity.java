package com.leezm.vindictus.activity;

import java.util.ArrayList;
import java.util.List;

import com.leezm.vindictus.adapter.CampSkillsAdapter;
import com.leezm.vindictus.fragment.BaseFragment;
import com.leezm.vindictus.fragment.CampSkillsFragmentA;
import com.leezm.vindictus.fragment.CampSkillsFragmentB;
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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CampSkillsActivity extends FragmentActivity implements
		OnClickListener {

	private CampSkillsFragmentA cA = new CampSkillsFragmentA();
	private CampSkillsFragmentB cB = new CampSkillsFragmentB();
	private List<TextView> tList = new ArrayList<TextView>();
	private List<BaseFragment> fList = new ArrayList<BaseFragment>();
	@ViewInject(R.id.camp_skill_view)
	ViewPager mViewPager;
	@ViewInject(R.id.txt_camp_gmll)
	TextView tViewA;
	@ViewInject(R.id.txt_camp_hall)
	TextView tViewB;

	// GDTAD
	@ViewInject(R.id.camp_skill_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

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
		setContentView(R.layout.activity_camp_skills);
		ViewUtils.inject(this);
		init();
		changeView(0);
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
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(CampSkillsActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", CampSkillsActivity.this);
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

	private void init() {
		tList.add(tViewA);
		tList.add(tViewB);
		tViewA.setOnClickListener(this);
		tViewB.setOnClickListener(this);
		fList.add(cA);
		fList.add(cB);
		CampSkillsAdapter mAdapter = new CampSkillsAdapter(this, fList);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int code) {
				changeView(code);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	// 手动设置ViewPager要显示的视图
	private void changeView(int desTab) {
		mViewPager.setCurrentItem(desTab, true);
		for (int i = 0; i < tList.size(); i++) {
			if (i == desTab) {
				tList.get(i).setTextColor(Color.rgb(247, 171, 38));
				tList.get(i).setTextSize(17);
				TextPaint tPaint = tList.get(i).getPaint();
				tPaint.setFakeBoldText(true);
			} else {
				tList.get(i).setTextColor(Color.rgb(255, 255, 255));
				tList.get(i).setTextSize(13);
				TextPaint tPaint = tList.get(i).getPaint();
				tPaint.setFakeBoldText(false);
			}
		}
	}

	@Override
	public void onClick(View mview) {
		switch (mview.getId()) {
		case R.id.txt_camp_gmll:
			changeView(0);
			break;
		case R.id.txt_camp_hall:
			changeView(1);
			break;

		default:
			break;
		}

	}

}
