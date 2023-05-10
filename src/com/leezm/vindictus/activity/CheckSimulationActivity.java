package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.SimulationCheckAdapter;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CheckSimulationActivity extends Activity implements
		OnItemClickListener {
	@ViewInject(R.id.simulation_check_adview)
	RelativeLayout madview;
	@ViewInject(R.id.simulation_check_listview)
	ListView listView;
	@ViewInject(R.id.simulation_check_save)
	ImageView saveiView;
	@ViewInject(R.id.simulation_check_del)
	ImageView delView;
	private List<Map<String, String>> list, list2, list3;
	SimulationCheckAdapter mAdapter;
	SimulationInterface dao = new SimulationImpl();
	public static final int CLEANPLAN = 16;
	private String advalue;

	// GDTAD
	@ViewInject(R.id.check_simulation_activity_adview)
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
		// GDTAD
		// bannerView.loadAD();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
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
				MobclickAgent.onEvent(CheckSimulationActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", CheckSimulationActivity.this);
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
		setContentView(R.layout.activity_check_simulation);
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
		init();// 显示配装列表
		listView.setOnItemClickListener(this);
		delView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(CheckSimulationActivity.this)
						.setMessage("确定要清空本次全部配装吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										dao.delall();
										init();
										Intent intent = new Intent();
										setResult(CLEANPLAN, intent);
										finish();
									}
								}).setNegativeButton("取消", null).show();
			}

		});
		saveiView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 保存到方案表
				if (list.isEmpty()) {
					Toast.makeText(CheckSimulationActivity.this, "你还没有添加任何装备！",
							0).show();
				} else {
					list2 = dao.checkSimulationresultad();
					list3 = dao.checkSimulationresultap();

					new AlertDialog.Builder(CheckSimulationActivity.this)
							.setMessage("确定要保存该方案吗？\n\n请选择你的角色类型！")
							.setPositiveButton("物理",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated method stub
											dao.savetoplan(
													"物理配装结果---[角色与技能均满级]\n"
															+ list2.get(0).get(
																	"title"),
													list2.get(0).get("att"),
													list2.get(0).get("bal"),
													list2.get(0)
															.get("critical"),
													list2.get(0).get("attspd"),
													list2.get(0).get("str"),
													list2.get(0).get("mint"),
													list2.get(0).get("agi"),
													list2.get(0).get("wil"),
													list2.get(0).get(
															"critresist"),
													list2.get(0).get("def"),
													list2.get(0).get(
															"shengmingzhi"),
													list2.get(0).get("sta"));
											Toast.makeText(
													CheckSimulationActivity.this,
													"已成功保存该方案", 1).show();
											CheckSimulationActivity.this
													.finish();
										}
									})
							.setNegativeButton("魔法",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated method stub
											dao.savetoplan(
													"魔法配装结果---[角色与技能均满级]\n"
															+ list3.get(0).get(
																	"title"),
													list3.get(0).get("att"),
													list3.get(0).get("bal"),
													list3.get(0)
															.get("critical"),
													list3.get(0).get("attspd"),
													list3.get(0).get("str"),
													list3.get(0).get("mint"),
													list3.get(0).get("agi"),
													list3.get(0).get("wil"),
													list3.get(0).get(
															"critresist"),
													list3.get(0).get("def"),
													list3.get(0).get(
															"shengmingzhi"),
													list3.get(0).get("sta"));
											Toast.makeText(
													CheckSimulationActivity.this,
													"已成功保存该方案", 1).show();
											CheckSimulationActivity.this
													.finish();
										}
									}).show();
				}
			}
		});
	}

	private void init() {
		list = dao.checkSimulation();
		mAdapter = new SimulationCheckAdapter(this, list);
		listView.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(CheckSimulationActivity.this).setItems(
				new String[] { "删除" }, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						// LogUtils.e("点击", "点击");
						dao.delectSimulation(list.get(arg2).get("_id"));
						init();
					}
				}).show();
	}

}
