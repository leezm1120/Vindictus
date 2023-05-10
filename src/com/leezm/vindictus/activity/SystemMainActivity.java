package com.leezm.vindictus.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.fragment.CommunityFragment;
import com.leezm.vindictus.fragment.FoundFragment;
import com.leezm.vindictus.fragment.HomeFragment;
import com.leezm.vindictus.fragment.ManualFragment;
import com.leezm.vindictus.fragment.MeFragment;
import com.leezm.vindictus.fragment.NoticeFragment;
import com.leezm.vindictus.fragment.SimulationFragment;
import com.leezm.vindictus.fragment.VideoFragment;
import com.leezm.vindictus.fragment.YouKuVideoFragment;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.ConstantValues;
import com.leezm.vindictus.utils.LogUtils;
import com.leezm.vindictus.view.MyTabWidget;
import com.leezm.vindictus.view.MyTabWidget.OnTabSelectedListener;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners.FetchListener;
import com.umeng.comm.core.nets.responses.MsgCountResponse;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.community.share.UMShareServiceFactory;
import com.umeng.message.PushAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.update.UmengUpdateAgent;
import com.youku.player.YoukuPlayerBaseConfiguration;

public class SystemMainActivity extends FragmentActivity implements
		OnTabSelectedListener {

	private FragmentManager mFragmentManager;
	private MyTabWidget mTabWidget;
	private HomeFragment mHomeFragment;
//	private NoticeFragment mNoticeFragment;
	private YouKuVideoFragment mKuVideoFragment;
//	private ManualFragment mManualFragment;
	private CommunityFragment mCommunityFragment;
//	private SimulationFragment mSimulationFragment;
	private FoundFragment mFoundFragment;
	private MeFragment mMeFragment;
	private int mIndex = ConstantValues.HOME_FRAGMENT_INDEX;

	private long exitTime = 0, refrshTime = 0;// 记录标记时间

	private String unread = "0";

	CommunitySDK mCommSDK = null;

	CommunityMainFragment mCommunityMainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_main);
		init();
	}

	private void init() {
		//判断网络是否可用
		if (!CommonUtils.isNetworkAvailable(SystemMainActivity.this)) {
			CommonUtils.showShortToast("你的网络有问题哦！", SystemMainActivity.this);
		}
		mFragmentManager = getSupportFragmentManager();
		mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
		mTabWidget.setOnTabSelectedListener(this);
		Bmob.initialize(this, "491ef144a008b257143233cfc2e48bf0");// Bmob初始化
		AnalyticsConfig.enableEncrypt(true);// 友盟统计日志加密
		PushAgent mPushAgent = PushAgent.getInstance(SystemMainActivity.this);// 友盟推送
		mPushAgent.enable();// 打开推送
		PushAgent.getInstance(SystemMainActivity.this).onAppStart();// 统计应用启动数据
		MobclickAgent.openActivityDurationTrack(false);// 禁止默认的页面统计方式，这样将不会再自动统计Activity
		UmengUpdateAgent.update(SystemMainActivity.this);// 友盟自动更新
		// feedbackAgent = new FeedbackAgent(SystemMainActivity.this);// 友盟用户反馈
		// feedbackAgent.openFeedbackPush();// 友盟用户反馈
		// feedbackAgent.sync();// 友盟用户反馈
		// feedbackAgent.closeAudioFeedback();// 关闭语音反馈

		umSheQu();
//		getUnread();
		
		//BMOB 自动更新
//		BmobUpdateAgent.update(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}

	private void umSheQu() {
		Constants.UMENG_APPKEY = "56a6ee3367e58e77200000ee";
		mCommSDK = CommunityFactory.getCommSDK(this);
		mCommSDK.initSDK(this);
		Constants.TOPIC_GAT = "";
		// 注入高德定位
		LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
		initPlatforms(this);
	}

	private void getUnread() {
		refrshTime = System.currentTimeMillis();
		mCommSDK.fetchUnReadMessageCount(new FetchListener<MsgCountResponse>() {

			@Override
			public void onComplete(MsgCountResponse arg0) {
				try {
					unread = arg0.mJsonObject.getString("total");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (!unread.equals("0")) {
					mTabWidget.setIndicateDisplay(SystemMainActivity.this, 3,
							true);
				}
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 初始化分享相关的平台
	 * 
	 * @param activity
	 */
	private void initPlatforms(Activity activity) {
		// 添加QQ
		UMQQSsoHandler qqHandler = new UMQQSsoHandler(activity, "1105191892",
				"aLdYjs5qeXyF4UUE");
		qqHandler.addToSocialSDK();
		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,
				"1105191892", "aLdYjs5qeXyF4UUE");
		qZoneSsoHandler.addToSocialSDK();
		// 添加微信平台
		UMWXHandler wechatHandler = new UMWXHandler(activity,
				"wx34833c877666f1a4", "e5c364bd0c8a43b0285e424ca89182ce");
		wechatHandler.addToSocialSDK();
		// 添加微信朋友圈平台
		UMWXHandler circleHandler = new UMWXHandler(activity,
				"wx34833c877666f1a4", "e5c364bd0c8a43b0285e424ca89182ce");
		circleHandler.setToCircle(true);
		circleHandler.addToSocialSDK();
		// UMSocialService socialSDK = UMShareServiceFactory.getSocialService();

		UMShareServiceFactory
				.getSocialService()
				.getConfig()
				.setPlatforms(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ);
		UMShareServiceFactory
				.getSocialService()
				.getConfig()
				.setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE,
						SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			// 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", 1).show();
				// 将系统当前的时间赋值给exitTime
				exitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
				YoukuPlayerBaseConfiguration.exit();		//退出应用时请调用此方法
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onTabSelected(mIndex);
		mTabWidget.setTabsDisplay(this, mIndex);
		// mTabWidget.setIndicateDisplay(this, 3, true);
		MobclickAgent.onResume(this);
//		if (System.currentTimeMillis() - refrshTime > (300 * 1000)) {
//			getUnread();
//		}
	}

	@Override
	public void onTabSelected(int index) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case ConstantValues.HOME_FRAGMENT_INDEX:
			if (null == mHomeFragment) {
				mHomeFragment = new HomeFragment();
				transaction.add(R.id.center_layout, mHomeFragment);
			} else {
				transaction.show(mHomeFragment);
			}
			break;
//		case ConstantValues.NOTICE_FRAGMENT_INDEX:
//			if (null == mNoticeFragment) {
//				mNoticeFragment = new NoticeFragment();
//				transaction.add(R.id.center_layout, mNoticeFragment);
//			} else {
//				transaction.show(mNoticeFragment);
//			}
//			break;
		case ConstantValues.VIDEO_FRAGMENT_INDEX:
			if (null == mKuVideoFragment) {
				mKuVideoFragment = new YouKuVideoFragment();
				transaction.add(R.id.center_layout, mKuVideoFragment);
			} else {
				transaction.show(mKuVideoFragment);
			}
			break;

//		case ConstantValues.MANUAL_FRAGMENT_INDEX:
//			if (null == mManualFragment) {
//				mManualFragment = new ManualFragment();
//				transaction.add(R.id.center_layout, mManualFragment);
//			} else {
//				transaction.show(mManualFragment);
//			}
//			break;
		// case ConstantValues.COMMUNITY_FRAGMENT_INDEX:
		// if (null == mCommunityFragment) {
		// mCommunityFragment = new CommunityFragment();
		// transaction.add(R.id.center_layout, mCommunityFragment);
		// } else {
		// transaction.show(mCommunityFragment);
		// }
		// break;
		case ConstantValues.COMMUNITY_FRAGMENT_INDEX:
			if (null == mCommunityMainFragment) {
				mCommunityMainFragment = new CommunityMainFragment();
				mCommunityMainFragment.setBackButtonVisibility(View.INVISIBLE);
				transaction.add(R.id.center_layout, mCommunityMainFragment);
			} else {
				transaction.show(mCommunityMainFragment);
			}
			mTabWidget.setIndicateDisplay(SystemMainActivity.this, 3, false);
			break;

//		case ConstantValues.SIMULATION_FRAGMENT_INDEX:
//			if (null == mSimulationFragment) {
//				mSimulationFragment = new SimulationFragment();
//				transaction.add(R.id.center_layout, mSimulationFragment);
//			} else {
//				transaction.show(mSimulationFragment);
//			}
//			break;
		case ConstantValues.FOUND_FRAGMENT_INDEX:
			if (null == mFoundFragment) {
				mFoundFragment = new FoundFragment();
				transaction.add(R.id.center_layout, mFoundFragment);
			} else {
				transaction.show(mFoundFragment);
			}
			break;
		case ConstantValues.ME_FRAGMENT_INDEX:
			if (null == mMeFragment) {
				mMeFragment = new MeFragment();
				transaction.add(R.id.center_layout, mMeFragment);
			} else {
				transaction.show(mMeFragment);
			}
			break;
		default:
			break;
		}
		mIndex = index;
		transaction.commitAllowingStateLoss();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (null != mHomeFragment) {
			transaction.hide(mHomeFragment);
		}
//		if (null != mNoticeFragment) {
//			transaction.hide(mNoticeFragment);
//		}
		if (null != mKuVideoFragment) {
			transaction.hide(mKuVideoFragment);
		}
//		if (null != mManualFragment) {
//			transaction.hide(mManualFragment);
//		}
		// if (null != mCommunityFragment) {
		// transaction.hide(mCommunityFragment);
		// }
		if (null != mCommunityMainFragment) {
			transaction.hide(mCommunityMainFragment);
		}
//		if (null != mSimulationFragment) {
//			transaction.hide(mSimulationFragment);
//		}
		if (null != mFoundFragment) {
			transaction.hide(mFoundFragment);
		}
		if (null != mMeFragment) {
			transaction.hide(mMeFragment);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题
		// super.onSaveInstanceState(outState);
		outState.putInt("index", mIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// super.onRestoreInstanceState(savedInstanceState);
		mIndex = savedInstanceState.getInt("index");
	}
}
