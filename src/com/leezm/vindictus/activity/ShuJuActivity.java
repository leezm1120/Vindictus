package com.leezm.vindictus.activity;

import java.util.ArrayList;
import java.util.List;

import com.leezm.vindictus.adapter.ManualFragmentAdapter;
import com.leezm.vindictus.fragment.BaseFragment;
import com.leezm.vindictus.fragment.ManualFragmentA;
import com.leezm.vindictus.fragment.ManualFragmentB;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShuJuActivity extends FragmentActivity implements OnClickListener {

	private Activity mActivity;
	private ManualFragmentA fgaA = new ManualFragmentA();
	private ManualFragmentB fgaB = new ManualFragmentB();
	private ViewPager mViewPager;
	private TextView tvA, tvB;
	private ImageView imageView;
	private List<BaseFragment> list = new ArrayList<BaseFragment>();
	private List<TextView> tvList = new ArrayList<TextView>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shu_ju);
		ViewUtils.inject(this);
		mActivity = this;
		
		loadview();
		initFragment();
		changeView(0);
	}
	
	
	private void loadview() {

		mViewPager = (ViewPager) mActivity.findViewById(R.id.manualview);
		tvA = (TextView) mActivity.findViewById(R.id.mf_bt_one);
		tvB = (TextView) mActivity.findViewById(R.id.mf_bt_two);

		imageView = (ImageView) mActivity
				.findViewById(R.id.manual_fragment_search);

		tvList.add(tvA);
		tvList.add(tvB);

		tvA.setOnClickListener(this);
		tvB.setOnClickListener(this);

		imageView.setOnClickListener(this);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int code) {
				// TODO Auto-generated method stub
				for (int i = 0; i < tvList.size(); i++) {
					if (i == code) {	
						//选中
						tvList.get(i).setTextColor(Color.rgb(247, 171, 38));
						tvList.get(i).setTextSize(17);
						TextPaint tPaint = tvList.get(i).getPaint();
						tPaint.setFakeBoldText(true);
					} else {
						tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
						tvList.get(i).setTextSize(13);
						TextPaint tPaint = tvList.get(i).getPaint();
						tPaint.setFakeBoldText(false);
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

	private void initFragment() {
		list.add(fgaA);
		list.add(fgaB);
		ManualFragmentAdapter manualFragmentAdapter = new ManualFragmentAdapter(
				mActivity, list);
		mViewPager.setAdapter(manualFragmentAdapter);
	}
	
	// 手动设置ViewPager要显示的视图
		private void changeView(int desTab) {
			mViewPager.setCurrentItem(desTab, true);
			for (int i = 0; i < tvList.size(); i++) {
				if (i == desTab) {
					tvList.get(i).setTextColor(Color.rgb(247, 171, 38));
					tvList.get(i).setTextSize(17);
					TextPaint tPaint = tvList.get(i).getPaint();
					tPaint.setFakeBoldText(true);
				} else {
					tvList.get(i).setTextColor(Color.rgb(255, 255, 255));
					tvList.get(i).setTextSize(13);
					TextPaint tPaint = tvList.get(i).getPaint();
					tPaint.setFakeBoldText(false);
				}
			}
		}
		@Override
		public void onClick(View mView) {
			// TODO Auto-generated method stub
			switch (mView.getId()) {
			case R.id.mf_bt_one:
				changeView(0);
				break;
			case R.id.mf_bt_two:
				changeView(1);
				break;
			case R.id.manual_fragment_search:
				CommonUtils.launchActivity(mActivity, ManualSearchActivity.class);
				break;
			default:
				break;
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
