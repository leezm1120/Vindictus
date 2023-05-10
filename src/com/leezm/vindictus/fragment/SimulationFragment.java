package com.leezm.vindictus.fragment;

import java.util.ArrayList;
import java.util.List;

import com.leezm.vindictus.activity.CheckSimulationActivity;
import com.leezm.vindictus.activity.CheckSimulationDifferentActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.activity.SimulationBraceletActivity;
import com.leezm.vindictus.activity.SimulationChooseActivity;
import com.leezm.vindictus.activity.SimulationNewChooseActivity;
import com.leezm.vindictus.activity.SimulationRolesActivity;
import com.leezm.vindictus.adapter.HomeFragmentAdapter;
import com.leezm.vindictus.adapter.SimulationFragmentAdapter;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.MarqueeTextView;
import com.leezm.vindictus.view.TopIndicator.OnTopIndicatorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SimulationFragment extends BaseFragment implements OnClickListener {

	private static String TAG = "SimulationFragment";

	private Activity mActivity;
	private SimulationFragmentA fgaA = new SimulationFragmentA();
	private SimulationFragmentB fgaB = new SimulationFragmentB();
	private SimulationFragmentC fgaC = new SimulationFragmentC();
	private ViewPager mViewPager;
	private TextView tvA, tvB, tvC;
	private List<BaseFragment> list = new ArrayList<BaseFragment>();
	private List<TextView> tvList = new ArrayList<TextView>();
	private String advalue, noticvalue;
	SimulationInterface dao = new SimulationImpl();
	public static final int SUCCESS = 1, FAIL = 0, EARRINGS = 1, HEAD = 2,
			WEAPONS = 3, CHEST = 4, DEPUTYWEAPON = 5, LEG = 6, HAND = 7,
			BELT = 8, FOOT = 9, BROOCH = 10, RINGF = 11, RINGS = 12,
			ARTWARE = 13, BRACELETF = 14, BRACELETS = 15, CLEANPLAN = 16,
			ROLES = 17;

	ViewGroup bannerContainer;
	BannerView bv;
	@ViewInject(R.id.simulation_text_notic)
	MarqueeTextView marqueeTextView;
	@ViewInject(R.id.simulation_earrings)
	ImageView iearrings;
	@ViewInject(R.id.simulation_head)
	ImageView ihead;
	@ViewInject(R.id.simulation_weapons)
	ImageView iweapons;
	@ViewInject(R.id.simulation_chest)
	ImageView ichest;
	@ViewInject(R.id.simulation_deputyweapon)
	ImageView ideputyweapon;
	@ViewInject(R.id.simulation_leg)
	ImageView ileg;
	@ViewInject(R.id.simulation_hand)
	ImageView ihand;
	@ViewInject(R.id.simulation_belt)
	ImageView ibelt;
	@ViewInject(R.id.simulation_foot)
	ImageView ifoot;
	@ViewInject(R.id.simulation_brooch)
	ImageView ibrooch;
	@ViewInject(R.id.simulation_ringf)
	ImageView iringf;
	@ViewInject(R.id.simulation_rings)
	ImageView irings;
	@ViewInject(R.id.simulation_artware)
	ImageView iartware;
	@ViewInject(R.id.simulation_braceletf)
	ImageView ibraceletf;
	@ViewInject(R.id.simulation_bracelets)
	ImageView ibracelets;
	@ViewInject(R.id.simulation_roles)
	ImageView iroles;
	@ViewInject(R.id.bt_simulation_contrastplan)
	RelativeLayout btcontrastplan;
	@ViewInject(R.id.bt_simulation_checkplan)
	RelativeLayout btcheckplan;
	@ViewInject(R.id.bt_simulation_cleanplan)
	RelativeLayout btcleanplan;
	SharedPreferences preferences;
	Editor editor;

	public static SimulationFragment newInstance() {
		SimulationFragment simulationFragment = new SimulationFragment();
		return simulationFragment;

	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SimulationFragment");
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SimulationFragment");
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_simulation, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		preferences = mActivity.getSharedPreferences("SimulationFragment",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		advalue = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"adctrl");
		noticvalue = OnlineConfigAgent.getInstance().getConfigParams(mActivity,
				"simulationtext");
		if (!noticvalue.equals(".")) {
			marqueeTextView.setText(noticvalue);
		}
		init();
		initimgoflishi();
	}

	private void init() {

		iearrings.setOnClickListener(this);
		ihead.setOnClickListener(this);
		iweapons.setOnClickListener(this);
		ichest.setOnClickListener(this);
		ideputyweapon.setOnClickListener(this);
		ileg.setOnClickListener(this);
		ihand.setOnClickListener(this);
		ibelt.setOnClickListener(this);
		ifoot.setOnClickListener(this);
		ibrooch.setOnClickListener(this);
		iringf.setOnClickListener(this);
		irings.setOnClickListener(this);
		iartware.setOnClickListener(this);
		ibraceletf.setOnClickListener(this);
		ibracelets.setOnClickListener(this);
		btcheckplan.setOnClickListener(this);
		btcontrastplan.setOnClickListener(this);
		btcleanplan.setOnClickListener(this);
		iroles.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == EARRINGS) {
			// Toast.makeText(mActivity, "成功保存了耳环", 1).show();
			iearrings.setImageResource(R.drawable.earring1);
			editor.putInt("iearrings", 1);
			editor.commit();
		} else if (resultCode == HEAD) {
			// Toast.makeText(mActivity, "成功保存了头部", 1).show();
			ihead.setImageResource(R.drawable.helm1);
			editor.putInt("ihead", 1);
			editor.commit();
		} else if (resultCode == WEAPONS) {
			// Toast.makeText(mActivity, "成功保存了武器", 1).show();
			iweapons.setImageResource(R.drawable.weapon1);
			editor.putInt("iweapons", 1);
			editor.commit();
		} else if (resultCode == CHEST) {
			// Toast.makeText(mActivity, "成功保存了胸部", 1).show();
			ichest.setImageResource(R.drawable.tunic1);
			editor.putInt("ichest", 1);
			editor.commit();
		} else if (resultCode == DEPUTYWEAPON) {
			// Toast.makeText(mActivity, "成功保存了副武器", 1).show();
			ideputyweapon.setImageResource(R.drawable.unique1);
			editor.putInt("ideputyweapon", 1);
			editor.commit();
		} else if (resultCode == LEG) {
			// Toast.makeText(mActivity, "成功保存了腿部", 1).show();
			ileg.setImageResource(R.drawable.pants1);
			editor.putInt("ileg", 1);
			editor.commit();
		} else if (resultCode == HAND) {
			// Toast.makeText(mActivity, "成功保存了手部", 1).show();
			ihand.setImageResource(R.drawable.gloves1);
			editor.putInt("ihand", 1);
			editor.commit();
		} else if (resultCode == BELT) {
			// Toast.makeText(mActivity, "成功保存了腰带", 1).show();
			ibelt.setImageResource(R.drawable.belt1);
			editor.putInt("ibelt", 1);
			editor.commit();
		} else if (resultCode == FOOT) {
			// Toast.makeText(mActivity, "成功保存了脚部", 1).show();
			ifoot.setImageResource(R.drawable.boots1);
			editor.putInt("ifoot", 1);
			editor.commit();
		} else if (resultCode == BROOCH) {
			// Toast.makeText(mActivity, "成功保存了胸针", 1).show();
			ibrooch.setImageResource(R.drawable.jewelry1);
			editor.putInt("ibrooch", 1);
			editor.commit();
		} else if (resultCode == RINGF) {
			// Toast.makeText(mActivity, "成功保存了左手戒指", 1).show();
			iringf.setImageResource(R.drawable.ring1);
			editor.putInt("iringf", 1);
			editor.commit();
		} else if (resultCode == RINGS) {
			// Toast.makeText(mActivity, "成功保存了右手戒指", 1).show();
			irings.setImageResource(R.drawable.ring1);
			editor.putInt("irings", 1);
			editor.commit();
		} else if (resultCode == ARTWARE) {
			// Toast.makeText(mActivity, "成功保存了工艺品", 1).show();
			iartware.setImageResource(R.drawable.artifact1);
			editor.putInt("iartware", 1);
			editor.commit();
		} else if (resultCode == BRACELETF) {
			// Toast.makeText(mActivity, "成功保存了左手手镯", 1).show();
			ibraceletf.setImageResource(R.drawable.bracelet1);
			editor.putInt("ibraceletf", 1);
			editor.commit();
		} else if (resultCode == BRACELETS) {
			// Toast.makeText(mActivity, "成功保存了右手手镯", 1).show();
			ibracelets.setImageResource(R.drawable.bracelet1);
			editor.putInt("ibracelets", 1);
			editor.commit();
		} else if (resultCode == ROLES) {
			// Toast.makeText(mActivity, "成功保存了右手手镯", 1).show();
			iroles.setImageResource(R.drawable.renwushux1);
			editor.putInt("iroles", 1);
			editor.commit();
		} else if (resultCode == CLEANPLAN) {
			Toast.makeText(mActivity, "已清空全部", 1).show();
			// 这里写把全部图片还原
			resetallimg();
		}
	}

	private void initimgoflishi() {
		if (preferences.getInt("iearrings", 0) == 1) {
			iearrings.setImageResource(R.drawable.earring1);
		}
		if (preferences.getInt("ihead", 0) == 1) {
			ihead.setImageResource(R.drawable.helm1);
		}
		if (preferences.getInt("iweapons", 0) == 1) {
			iweapons.setImageResource(R.drawable.weapon1);
		}
		if (preferences.getInt("ichest", 0) == 1) {
			ichest.setImageResource(R.drawable.tunic1);
		}
		if (preferences.getInt("ideputyweapon", 0) == 1) {
			ideputyweapon.setImageResource(R.drawable.unique1);
		}
		if (preferences.getInt("ileg", 0) == 1) {
			ileg.setImageResource(R.drawable.pants1);
		}
		if (preferences.getInt("ihand", 0) == 1) {
			ihand.setImageResource(R.drawable.gloves1);
		}
		if (preferences.getInt("ibelt", 0) == 1) {
			ibelt.setImageResource(R.drawable.belt1);
		}
		if (preferences.getInt("ifoot", 0) == 1) {
			ifoot.setImageResource(R.drawable.boots1);
		}
		if (preferences.getInt("ibrooch", 0) == 1) {
			ibrooch.setImageResource(R.drawable.jewelry1);
		}
		if (preferences.getInt("iringf", 0) == 1) {
			iringf.setImageResource(R.drawable.ring1);
		}
		if (preferences.getInt("irings", 0) == 1) {
			irings.setImageResource(R.drawable.ring1);
		}
		if (preferences.getInt("iartware", 0) == 1) {
			iartware.setImageResource(R.drawable.artifact1);
		}
		if (preferences.getInt("ibraceletf", 0) == 1) {
			ibraceletf.setImageResource(R.drawable.bracelet1);
		}
		if (preferences.getInt("ibracelets", 0) == 1) {
			ibracelets.setImageResource(R.drawable.bracelet1);
		}
		if (preferences.getInt("iroles", 0) == 1) {
			iroles.setImageResource(R.drawable.renwushux1);
		}
	}

	private void resetallimg() {
		iearrings.setImageResource(R.drawable.earring);
		ihead.setImageResource(R.drawable.helm);
		iweapons.setImageResource(R.drawable.weapon);
		ichest.setImageResource(R.drawable.tunic);
		ideputyweapon.setImageResource(R.drawable.unique);
		ileg.setImageResource(R.drawable.pants);
		ihand.setImageResource(R.drawable.gloves);
		ibelt.setImageResource(R.drawable.belt);
		ifoot.setImageResource(R.drawable.boots);
		ibrooch.setImageResource(R.drawable.jewelry);
		iringf.setImageResource(R.drawable.ring);
		irings.setImageResource(R.drawable.ring);
		iartware.setImageResource(R.drawable.artifact);
		ibraceletf.setImageResource(R.drawable.bracelet);
		ibracelets.setImageResource(R.drawable.bracelet);
		iroles.setImageResource(R.drawable.renwushux);

		editor.putInt("iearrings", 0);
		editor.putInt("ihead", 0);
		editor.putInt("iweapons", 0);
		editor.putInt("ichest", 0);
		editor.putInt("ideputyweapon", 0);
		editor.putInt("ileg", 0);
		editor.putInt("ihand", 0);
		editor.putInt("ibelt", 0);
		editor.putInt("ifoot", 0);
		editor.putInt("ibrooch", 0);
		editor.putInt("iringf", 0);
		editor.putInt("irings", 0);
		editor.putInt("iartware", 0);
		editor.putInt("ibraceletf", 0);
		editor.putInt("ibracelets", 0);
		editor.putInt("iroles", 0);

		editor.commit();
	}

	@Override
	public void onClick(View mView) {
		switch (mView.getId()) {
		case R.id.simulation_earrings:// 耳环
			choosego("EARRINGS");
			break;
		case R.id.simulation_head: // 头
			choosego("HEAD");
			break;
		case R.id.simulation_weapons:// 武器
			choosego("WEAPONS");
			break;
		case R.id.simulation_chest:// 胸部
			choosego("CHEST");
			break;
		case R.id.simulation_deputyweapon:// 副武器
			choosego("DEPUTYWEAPON");
			break;
		case R.id.simulation_leg:// 裤
			choosego("LEG");
			break;
		case R.id.simulation_hand:// 手部
			choosego("HAND");
			break;
		case R.id.simulation_belt:// 腰带
			choosego("BELT");
			break;
		case R.id.simulation_foot:// 脚
			choosego("FOOT");
			break;
		case R.id.simulation_brooch:// 胸针
			choosego("BROOCH");
			break;
		case R.id.simulation_ringf:// 戒指1
			choosego("RINGF");
			break;
		case R.id.simulation_rings:// 戒指2
			choosego("RINGS");
			break;
		case R.id.simulation_artware:// 工艺品
			choosego("ARTWARE");
			break;
		case R.id.simulation_braceletf:// 手镯1
			// choosego("BRACELETF");
			Intent intenta = new Intent(mActivity,
					SimulationBraceletActivity.class);
			intenta.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intenta.putExtra("key", "BRACELETF");
			startActivityForResult(intenta, mActivity.RESULT_FIRST_USER);
			break;
		case R.id.simulation_bracelets:// 手镯2
			// choosego("BRACELETS");
			Intent intentb = new Intent(mActivity,
					SimulationBraceletActivity.class);
			intentb.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intentb.putExtra("key", "BRACELETS");
			startActivityForResult(intentb, mActivity.RESULT_FIRST_USER);
			break;

		case R.id.simulation_roles:
			Intent intentc = new Intent(mActivity,
					SimulationRolesActivity.class);
			intentc.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intentc.putExtra("key", "ROLES");
			startActivityForResult(intentc, mActivity.RESULT_FIRST_USER);
			break;

		case R.id.bt_simulation_contrastplan:// 对比方案
			CommonUtils.launchActivity(mActivity,
					CheckSimulationDifferentActivity.class);
			break;
		case R.id.bt_simulation_checkplan:// 查看方案
			Intent intent1 = new Intent(mActivity,
					CheckSimulationActivity.class);
			intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent1.putExtra("key", "checkplan");
			startActivityForResult(intent1, mActivity.RESULT_FIRST_USER);
			break;
		case R.id.bt_simulation_cleanplan:// 清空当前方案
			new AlertDialog.Builder(mActivity)
					.setMessage("确定要清空当前方案吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// 这里写清空图片交互样子
									dao.delall();
									Toast.makeText(mActivity, "已清空当前所有配装", 0)
											.show();
									resetallimg();
								}
							}).setNegativeButton("取消", null).show();
			break;
		default:
			break;
		}
	}

	private void choosego(String part) {
		Intent intent = new Intent(mActivity, SimulationNewChooseActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("key", part);
		startActivityForResult(intent, mActivity.RESULT_FIRST_USER);
	}

}
