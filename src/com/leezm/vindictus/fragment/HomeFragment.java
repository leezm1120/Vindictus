package com.leezm.vindictus.fragment;

import java.util.ArrayList;
import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.activity.SystemMainActivity;
import com.leezm.vindictus.adapter.HomeFragmentAdapter;
import com.leezm.vindictus.utils.LogUtils;
import com.leezm.vindictus.view.TopIndicator.OnTopIndicatorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class HomeFragment extends BaseFragment implements OnClickListener {

	private static String TAG = "HomeFragment";
	private Activity mActivity;

	// private HomeFragmentA fgaA = new HomeFragmentA();
	// private HomeFragmentB fgaB = new HomeFragmentB();
	// private HomeFragmentC fgaC = new HomeFragmentC();
	// private ViewPager mViewPager;
	// private TextView tvA,tvB,tvC;
	// private List<BaseFragment> list = new ArrayList<BaseFragment>();
	// private List<TextView> tvList = new ArrayList<TextView>();

	@ViewInject(R.id.home_progresswebview)
	WebView mwebView;
	@ViewInject(R.id.home_web_text)
	TextView textView;
	@ViewInject(R.id.home_web_share)
	ImageView shareView;

	private String urlvalue, hometitle, shareimg;
	private long exitTime = 0;// 记录标记时间

	public static HomeFragment newInstance() {
		HomeFragment homeFragment = new HomeFragment();
		return homeFragment;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("HomeFragment");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("HomeFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_webview, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		urlvalue = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"homeurl");
		hometitle = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"hometitle");
		shareimg = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"homeshare");
		if (shareimg.equals("1")) {
			shareView.setVisibility(View.VISIBLE);
		}
		if (urlvalue.equals("")) {
			urlvalue = "http://mp.weixin.qq.com/mp/homepage?__biz=MzA5ODI2Mjg0MA==&hid=1&sn=a4e81e969787d2d1cfff601c23658594#wechat_redirect";
		}
		if (!hometitle.equals("0")) {
			textView.setText(hometitle);
		}
		// loadview();
		// initFragment();
		// changeView(0);
		homewebviewinit();
	}

	private void homewebviewinit() {
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mwebView.loadUrl(urlvalue);
		mwebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});

		mwebView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getRepeatCount() == 0) {
					if (mwebView.canGoBack()) {
						mwebView.goBack();// 返回上一页面
						return true;
					} else {
						//LogUtils.e("back", "back");

					}
				}
				return false;

			}
		});

		shareView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(mwebView.getUrl());
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
	}

	/**
	 * private void loadview(){
	 * 
	 * mViewPager = (ViewPager) mActivity.findViewById(R.id.homeview); tvA =
	 * (TextView) mActivity.findViewById(R.id.hf_bt_one); tvB = (TextView)
	 * mActivity.findViewById(R.id.hf_bt_two); tvC = (TextView)
	 * mActivity.findViewById(R.id.hf_bt_three); tvList.add(tvA);
	 * tvList.add(tvB); tvList.add(tvC);
	 * 
	 * tvA.setOnClickListener(this); tvB.setOnClickListener(this);
	 * tvC.setOnClickListener(this);
	 * 
	 * mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
	 * 
	 * @Override public void onPageSelected(int code) { // TODO Auto-generated
	 *           method stub for(int i=0;i<tvList.size();i++){ if(i == code){
	 * 
	 *           tvList.get(i).setTextColor(Color.rgb(254, 77, 61)); //
	 *           tvList.get(i).setBackgroundResource(R.drawable.topselect);
	 *           }else{ tvList.get(i).setBackgroundColor(Color.WHITE);
	 *           tvList.get(i).setTextColor(Color.rgb(0,0, 0)); } } }
	 * @Override public void onPageScrolled(int arg0, float arg1, int arg2) { //
	 *           TODO Auto-generated method stub
	 * 
	 *           }
	 * @Override public void onPageScrollStateChanged(int arg0) { // TODO
	 *           Auto-generated method stub
	 * 
	 *           } }); }
	 */
	/**
	 * private void initFragment(){ list.add(fgaA); list.add(fgaB);
	 * list.add(fgaC); HomeFragmentAdapter mHomeFragmentAdapter = new
	 * HomeFragmentAdapter(mActivity, list);
	 * mViewPager.setAdapter(mHomeFragmentAdapter); }
	 */

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return TAG;
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {
		// case R.id.hf_bt_one:
		// changeView(0);
		// break;
		// case R.id.hf_bt_two:
		// changeView(1);
		// break;
		// case R.id.hf_bt_three:
		// changeView(2);
		// break;
		default:
			break;
		}
	}
	/**
	 * // 手动设置ViewPager要显示的视图 private void changeView(int desTab) {
	 * mViewPager.setCurrentItem(desTab, true); for(int
	 * i=0;i<tvList.size();i++){ if(i == desTab){
	 * tvList.get(i).setTextColor(Color.rgb(254, 77, 61)); //
	 * tvList.get(i).setBackgroundResource(R.drawable.topselect); }else{
	 * tvList.get(i).setBackgroundColor(Color.WHITE);
	 * tvList.get(i).setTextColor(Color.rgb(0,0, 0)); } } }
	 */
}
