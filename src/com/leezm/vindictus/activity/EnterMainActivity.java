package com.leezm.vindictus.activity;

import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class EnterMainActivity extends Activity {
	private Animation animation;
	private static int TIME = 1000;
	private View view;
	private boolean canJump = false;
	VindictusDBManager vindictusDBManager;
	BitmapUtils bitmapUtils;
	String urlString;
	
	@SuppressWarnings("unused")
	private SplashAD splashAD;
	@ViewInject(R.id.enter_main_activity)
	ViewGroup container;
	private String kpadString = "off";

	// 定义数组存放随机图片
	// private int[] bgsplash = new int[] {};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		if (canJump) {
			canNext();
		}
		canJump = true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
		canJump = false;
	}

	/**
	 * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。
	 * 当从广告落地页返回以后， 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
	 */
	private void canNext() {
		if (canJump) {
			next();
		} else {
			canJump = true;
		}
	}

	private void next() {
		CommonUtils.launchActivity(EnterMainActivity.this,
				SystemMainActivity.class);
		EnterMainActivity.this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initgdtkp() {
		kpadString = "on";
		splashAD = new SplashAD(this, container, GdtAdConstants.GDTAPPID,
				GdtAdConstants.GDTKPID, new SplashADListener() {

					@Override
					public void onNoAD(int arg0) {
						LogUtils.i("AD_DEMO", "LoadSplashADFail,ecode=" + arg0);
						MobclickAgent.onEvent(EnterMainActivity.this, "SplashADonNoAD");
						next();
					}

					@Override
					public void onADPresent() {
						LogUtils.i("AD_DEMO", "SplashADPresent");
					}

					@Override
					public void onADDismissed() {
						LogUtils.i("AD_DEMO", "SplashADDismissed");
						MobclickAgent.onEvent(EnterMainActivity.this, "SplashADonADDismissed");
						canNext();
					}

					@Override
					public void onADClicked() {
						LogUtils.i("AD_DEMO", "SplashADClicked");
							MobclickAgent.onEvent(EnterMainActivity.this, "kpadonclick");
							CommonUtils.showShortToast("你给开发者投了一枚滑稽币！", EnterMainActivity.this);
					}
				});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		view = View.inflate(this, R.layout.activity_enter_main, null);
		setContentView(view);
		ViewUtils.inject(this);
		
		bitmapUtils = new BitmapUtils(this);
		urlString = OnlineConfigAgent.getInstance().getConfigParams(this, "mainkpurl");
		bitmapUtils.configDefaultShowOriginal(true);
		bitmapUtils.display(view, "assets/splash24.png");
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 在线参数的测试模式
				//OnlineConfigAgent.getInstance().setDebugMode(true);
				OnlineConfigAgent.getInstance().updateOnlineConfig(
						EnterMainActivity.this);// 请求友盟在线参数
				vindictusDBManager = new VindictusDBManager(
						EnterMainActivity.this);
				vindictusDBManager.openDatabase();
				vindictusDBManager.closeDatabase();
			}
		});
		if (OnlineConfigAgent.getInstance()
				.getConfigParams(EnterMainActivity.this, "newkpad")
				.equals("on")) {
			initgdtkp();
		}
		// 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		// 给view设置动画效果
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (kpadString.equals("on")) {
						} else {
							next();
						}
					}
				}, TIME);
			}
		});
	}

}
