package com.leezm.vindictus.fragment;

import com.leezm.vindictus.activity.APRedemptionActivity;
import com.leezm.vindictus.activity.AboutUsActivity;
import com.leezm.vindictus.activity.AreaTipsActivity;
import com.leezm.vindictus.activity.BossListActivity;
import com.leezm.vindictus.activity.CampSkillsActivity;
import com.leezm.vindictus.activity.EquipmentSkillActivity;
import com.leezm.vindictus.activity.MapImgActivity;
import com.leezm.vindictus.activity.MoNiActivity;
import com.leezm.vindictus.activity.QHFMActivity;
import com.leezm.vindictus.activity.QqunActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.ShowRolesDialogActivity;
import com.leezm.vindictus.activity.ShuJuActivity;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.activity.TitleListActivity;
import com.leezm.vindictus.activity.WebViewActivity;
import com.leezm.vindictus.dbutils.VindictusDBManager;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.MarqueeTextView;
import com.leezm.vindictus.view.TopIndicator.OnTopIndicatorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.proguard.r;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class FoundFragment extends BaseFragment implements OnClickListener {

	private static String TAG = "FoundFragment";
	private RelativeLayout rLayoutBoss, rLayoutTitle;
	private Activity mActivity;
	private String urlString, roleurlString, communityurlString;
	// @ViewInject(R.id.found_role_goto)
	// RelativeLayout rLayout;
	@ViewInject(R.id.found_text_notice)
	MarqueeTextView mTextView;
	// @ViewInject(R.id.found_mahejijie)
	// RelativeLayout rLayoutmahe;
	// @ViewInject(R.id.found_text_mahebiaoti)
	// TextView textView;
	// @ViewInject(R.id.found_community_goto)
	// RelativeLayout rLayoutcommunity;
	// @ViewInject(R.id.found_community_goto_view)
	// View communityView;
	@ViewInject(R.id.found_apshuhui)
	RelativeLayout rLayoutShuhui;
	@ViewInject(R.id.found_bt_equipmentskill)
	RelativeLayout btskill;
	@ViewInject(R.id.found_bt_area_tips)
	RelativeLayout bttips;
	@ViewInject(R.id.found_bt_qhfm)
	RelativeLayout btqhfm;
	// @ViewInject(R.id.found_qquntuijian)
	// RelativeLayout btqqun;
	// @ViewInject(R.id.found_qquntuijian_view)
	// View qqunview;
	@ViewInject(R.id.found_tujian)
	RelativeLayout bttujian;
	@ViewInject(R.id.found_zhenying)
	RelativeLayout btzhenying;
	@ViewInject(R.id.found_jsjd)
	RelativeLayout btjsjd;
	@ViewInject(R.id.found_shuju11)
	RelativeLayout btshuju;
	@ViewInject(R.id.found_moni11)
	RelativeLayout btmoni;

	public static FoundFragment newInstance() {
		FoundFragment foundFragment = new FoundFragment();
		return foundFragment;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("FoundFragment");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("FoundFragment");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
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
		View view = inflater.inflate(R.layout.fragment_found, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {
		rLayoutBoss = (RelativeLayout) mActivity
				.findViewById(R.id.found_boss_goto);
		rLayoutTitle = (RelativeLayout) mActivity
				.findViewById(R.id.found_title_goto);
		rLayoutBoss.setOnClickListener(this);
		rLayoutTitle.setOnClickListener(this);
		// rLayout.setOnClickListener(this);
		// rLayoutmahe.setOnClickListener(this);
		// rLayoutcommunity.setOnClickListener(this);
		rLayoutShuhui.setOnClickListener(this);
		btskill.setOnClickListener(this);
		bttips.setOnClickListener(this);
		btqhfm.setOnClickListener(this);
		// btqqun.setOnClickListener(this);
		bttujian.setOnClickListener(this);
		btzhenying.setOnClickListener(this);
		btjsjd.setOnClickListener(this);
		btshuju.setOnClickListener(this);
		btmoni.setOnClickListener(this);
		String value = OnlineConfigAgent.getInstance().getConfigParams(
				mActivity, "foundtxtnotice");
		String txt = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"mahebiaoti");
		urlString = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"maheurl");
		roleurlString = OnlineConfigAgent.getInstance().getConfigParams(
				mActivity, "roleurl");
		communityurlString = OnlineConfigAgent.getInstance().getConfigParams(
				mActivity, "communityurl");
		// if (txt.equals(".")) {
		// textView.setText("");
		// } else {
		// textView.setText(txt);
		// }
		//
		// if (OnlineConfigAgent.getInstance()
		// .getConfigParams(mActivity, "qqunturn").equals("1")) {
		// btqqun.setVisibility(View.VISIBLE);
		// qqunview.setVisibility(View.VISIBLE);
		// } else {
		// btqqun.setVisibility(View.GONE);
		// qqunview.setVisibility(View.GONE);
		// }
		//
		// if (OnlineConfigAgent.getInstance()
		// .getConfigParams(mActivity, "communityturn").equals("0")) {
		// rLayoutcommunity.setVisibility(View.GONE);
		// communityView.setVisibility(View.GONE);
		// } else {
		// rLayoutcommunity.setVisibility(View.VISIBLE);
		// communityView.setVisibility(View.VISIBLE);
		// }

		if (value.equals(".")) {
			mTextView.setText("");
		} else {
			mTextView.setText(value);
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
		case R.id.found_boss_goto:
			CommonUtils.launchActivity(mActivity, BossListActivity.class);
			break;
		case R.id.found_title_goto:
			CommonUtils.launchActivity(mActivity, TitleListActivity.class);
			break;
		// case R.id.found_role_goto:
		// // FeedbackAgent agent = new FeedbackAgent(mActivity);
		// // agent.startFeedbackActivity();
		// Intent intent2 = new Intent(mActivity, WebViewActivity.class);
		// intent2.putExtra("url", roleurlString);
		// intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		// startActivity(intent2);
		// break;
		// case R.id.found_mahejijie:
		// Intent intent = new Intent(mActivity, WebViewActivity.class);
		// intent.putExtra("url", urlString);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		// startActivity(intent);
		// break;
		// case R.id.found_community_goto:
		// Intent intent3 = new Intent(mActivity, WebViewActivity.class);
		// intent3.putExtra("url", communityurlString);
		// intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		// startActivity(intent3);
		// break;
		case R.id.found_apshuhui:
			CommonUtils.launchActivity(mActivity, APRedemptionActivity.class);
			// CommonUtils.launchActivity(mActivity,
			// XFiveWebViewActivity.class);
			break;

		case R.id.found_bt_equipmentskill:
			CommonUtils.launchActivity(mActivity, EquipmentSkillActivity.class);
			break;
		case R.id.found_bt_area_tips:
			CommonUtils.launchActivity(mActivity, AreaTipsActivity.class);
			break;
		case R.id.found_bt_qhfm:
			CommonUtils.launchActivity(mActivity, QHFMActivity.class);
			break;

		// case R.id.found_qquntuijian:
		// CommonUtils.launchActivity(mActivity, QqunActivity.class);
		// break;

		case R.id.found_tujian:
			CommonUtils.launchActivity(mActivity, MapImgActivity.class);
			break;

		case R.id.found_zhenying:
			CommonUtils.launchActivity(mActivity, CampSkillsActivity.class);
			break;
		case R.id.found_jsjd:
			CommonUtils
					.launchActivity(mActivity, ShowRolesDialogActivity.class);
			break;

		// 2016-9-5 10:11:20 数据按钮，由fragment转出activity
		case R.id.found_shuju11:
			CommonUtils
					.launchActivity(mActivity, ShuJuActivity.class);
			break;
		// 2016-9-5 10:11:30 模拟按钮，由fragment转出activity
		case R.id.found_moni11:
			CommonUtils
					.launchActivity(mActivity, MoNiActivity.class);
			break;

		default:
			break;
		}
	}

}
