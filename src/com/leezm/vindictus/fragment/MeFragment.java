package com.leezm.vindictus.fragment;

import cn.bmob.v3.update.BmobUpdateAgent;

import com.leezm.vindictus.activity.AboutUsActivity;
import com.leezm.vindictus.activity.AddVideoActivity;
import com.leezm.vindictus.activity.BossListActivity;
import com.leezm.vindictus.activity.DonationActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.SettingActivity;
import com.leezm.vindictus.activity.SimulationNewChooseActivity;
import com.leezm.vindictus.activity.SocietyActivity;
import com.leezm.vindictus.activity.TitleListActivity;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.activity.YouKuDownloadActivity;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.MarqueeTextView;
import com.leezm.vindictus.view.TopIndicator.OnTopIndicatorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.qq.e.ads.appwall.APPWall;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MeFragment extends BaseFragment implements OnClickListener {
	private Activity mActivity;
	private static String TAG = "MeFragment";
	private TextView mTitleTv;
	@ViewInject(R.id.me_fb)
	RelativeLayout rLayoutfb;
	@ViewInject(R.id.me_update)
	RelativeLayout rLayoutupdate;
	@ViewInject(R.id.me_aboutus)
	RelativeLayout rLayoutaboutus;
	@ViewInject(R.id.me_share)
	RelativeLayout rLayoutshare;
	@ViewInject(R.id.me_juanzeng)
	RelativeLayout rLayoutjuanzeng;
	@ViewInject(R.id.me_text_notice)
	MarqueeTextView marqueeTextView;
	@ViewInject(R.id.me_pingfen)
	RelativeLayout rLayoutpingfen;
	@ViewInject(R.id.me_juanzeng_view)
	View juanzView;
	@ViewInject(R.id.me_tuijian)
	RelativeLayout rLayouttuijian;
	@ViewInject(R.id.me_tuijian_view)
	View tuijianView;
	@ViewInject(R.id.me_yixiazai)
	RelativeLayout rLayoutYixiazai;
	@ViewInject(R.id.me_add_video)
	RelativeLayout rLayouttianjiaship;
//	@ViewInject(R.id.me_setting)
//	RelativeLayout btsetting;

	// UMImage umImage = new UMImage(mActivity,
	// "http://imgsrc.baidu.com/forum/pic/item/7f5a892f07082838c0bd2c4ebf99a9014d08f1da.jpg");

	private String value, fbwelcominfo, shareinfo, juanzeng, menotic,
			xinjuanzeng;

	public static MeFragment newInstance() {
		MeFragment meFragment = new MeFragment();
		return meFragment;
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
		MobclickAgent.onPageStart("MeFragment");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MeFragment");
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
		View view = inflater.inflate(R.layout.fragment_setting, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		rLayoutfb.setOnClickListener(this);
		rLayoutupdate.setOnClickListener(this);
		rLayoutshare.setOnClickListener(this);
		rLayoutaboutus.setOnClickListener(this);
		rLayoutjuanzeng.setOnClickListener(this);
		rLayoutpingfen.setOnClickListener(this);
		rLayouttuijian.setOnClickListener(this);
		rLayoutYixiazai.setOnClickListener(this);
		rLayouttianjiaship.setOnClickListener(this);
//		btsetting.setOnClickListener(this);
		value = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"aboutus");
		fbwelcominfo = OnlineConfigAgent.getInstance().getConfigParams(
				mActivity, "fbwelcominfo");
		shareinfo = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"shareinfo");
		juanzeng = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"juanzeng");
		xinjuanzeng = OnlineConfigAgent.getInstance().getConfigParams(
				mActivity, "xinjuanzeng");
		menotic = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"metext");
		if (!menotic.equals(".")) {
			marqueeTextView.setText(menotic);
		}
		if (juanzeng.equals("0")) {
			rLayoutjuanzeng.setVisibility(View.GONE);
			juanzView.setVisibility(View.GONE);
		}
		if (xinjuanzeng.equals("1")) {
			rLayoutjuanzeng.setVisibility(View.VISIBLE);
			juanzView.setVisibility(View.VISIBLE);
		}
		if (OnlineConfigAgent.getInstance()
				.getConfigParams(mActivity, "adyyqturn").equals("0")) {
			rLayouttuijian.setVisibility(View.GONE);
			tuijianView.setVisibility(View.GONE);
		}
	}

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
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
//		case R.id.me_setting:
//			CommonUtils.launchActivity(mActivity, SettingActivity.class);
//			break;
		case R.id.me_fb:
//			FeedbackAgent agent = new FeedbackAgent(mActivity);
//			agent.startFeedbackActivity();
//			agent.setWelcomeInfo(fbwelcominfo);
			// CommonUtils.launchActivity(mActivity, SocietyActivity.class);
			break;
		case R.id.me_update:
//			 BmobUpdateAgent.forceUpdate(mActivity);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				@Override
				public void onUpdateReturned(int updateStatus,
						UpdateResponse updateInfo) {
					switch (updateStatus) {
					case UpdateStatus.Yes: // has update
						UmengUpdateAgent
								.showUpdateDialog(mActivity, updateInfo);
						break;
					case UpdateStatus.No: // has no update
						Toast.makeText(mActivity, "当前已是最新版本",
								Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.NoneWifi: // none wifi
						Toast.makeText(mActivity, "正在使用非WIFI网络",
								Toast.LENGTH_SHORT).show();
						UmengUpdateAgent
								.showUpdateDialog(mActivity, updateInfo);
						break;
					case UpdateStatus.Timeout: // time out
						Toast.makeText(mActivity, "连接超时，请重试...",
								Toast.LENGTH_SHORT).show();
						break;
					}
				}

			});
			UmengUpdateAgent.forceUpdate(getActivity());
			Toast.makeText(
					mActivity,
					"当前版本：" + CommonUtils.getVersionName(mActivity) + "   "
							+ "正在检查更新...", 1).show();
			// CommonUtils.launchActivity(mActivity, UpdateActivity.class);
			break;
		case R.id.me_aboutus:
			// CommonUtils.launchActivity(mActivity, AboutUsActivity.class);
			Intent intent = new Intent(mActivity, AboutUsActivity.class);
			intent.putExtra("msg", value);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			break;
		case R.id.me_share:
			// SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
			// {
			// SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
			// SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
			// };
			// new ShareAction(mActivity).setDisplayList( displaylist )
			// .withText( "呵呵" )
			// .withTitle("title")
			// .withTargetUrl("http://www.baidu.com")
			// .withMedia( umImage )
			// .open();
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, shareinfo);
			shareIntent.setType("text/plain");
			// 设置分享列表的标题，并且每次都显示分享列表
			startActivity(Intent.createChooser(shareIntent, getResources()
					.getText(R.string.app_name)));
			break;
		case R.id.me_juanzeng:
			CommonUtils.launchActivity(mActivity, DonationActivity.class);
			//CommonUtils.launchActivity(mActivity, SimulationNewChooseActivity.class);
			break;
		case R.id.me_pingfen:
			try {
				Uri uri = Uri.parse("market://details?id="
						+ "com.leezm.vindictus.activity");
				Intent intent3 = new Intent(Intent.ACTION_VIEW, uri);
				intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent3);
				Toast.makeText(mActivity, "谢谢支持！记得给五星好评哦！", 0).show();
			} catch (ActivityNotFoundException e) {
				Toast.makeText(mActivity, "Couldn't launch the market!",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.me_tuijian:
			APPWall appWall = new APPWall(mActivity, GdtAdConstants.GDTAPPID,
					GdtAdConstants.GDTYYQID);
			appWall.setScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			appWall.doShowAppWall();

//			CommonUtils.launchActivity(mActivity, YouKuDownloadActivity.class);
			
			break;

			case R.id.me_yixiazai:
				CommonUtils.launchActivity(mActivity, YouKuDownloadActivity.class);
				break;
			
				case R.id.me_add_video:
					new AlertDialog.Builder(mActivity).setMessage("你要上传的是否为洛英视频？")
					.setPositiveButton("是", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							Intent intent = new Intent(mActivity, AddVideoActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							intent.putExtra("isLYVideo", true);
							mActivity.startActivity(intent);
						}
					})
					.setNegativeButton("否", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							Intent intent = new Intent(mActivity, AddVideoActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							intent.putExtra("isLYVideo", false);
							mActivity.startActivity(intent);
							
						}
					}).show();
					break;
				
		default:
			break;
		}
	}

}
