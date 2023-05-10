package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.SimulationCheckDifferentAdapter;
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
import android.content.Context;
import android.content.DialogInterface;
import android.text.ClipboardManager;
import android.util.Log;
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

public class CheckSimulationDifferentActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {

	@ViewInject(R.id.simulation_check_different_listview)
	ListView listView;
	@ViewInject(R.id.simulation_checkdifferent_del)
	ImageView delView;
	// @ViewInject(R.id.simulation_checkdifferent_save)
	// ImageView savevView;
	@ViewInject(R.id.simulation_checkdifferent_adview)
	RelativeLayout adview;
	private List<Map<String, String>> list;
	SimulationInterface dao = new SimulationImpl();
	SimulationCheckDifferentAdapter mAdapter;

	// 复制
	ClipboardManager clipboardManager;

	// GDTAD
	@ViewInject(R.id.check_simulation_different_activity_adview)
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
				MobclickAgent.onEvent(CheckSimulationDifferentActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", CheckSimulationDifferentActivity.this);
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
		setContentView(R.layout.activity_check_simulation_different);
		ViewUtils.inject(this);
		init();// 显示方案列表
		// 复制
		clipboardManager = (ClipboardManager) this
				.getSystemService(Context.CLIPBOARD_SERVICE);
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
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		delView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(CheckSimulationDifferentActivity.this)
						.setMessage("确定要清空本次全部配装吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										dao.delallplan();
										init();
									}
								}).setNegativeButton("取消", null).show();
			}

		});
	}

	private void init() {
		list = dao.alldifferentplan();
		mAdapter = new SimulationCheckDifferentAdapter(this, list);
		listView.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		clipboardManager.setText(list.get(arg2).get("title") + "\n攻击："
				+ list.get(arg2).get("att") + "\n力量："
				+ list.get(arg2).get("str") + "\n智力："
				+ list.get(arg2).get("mint") + "\n敏捷："
				+ list.get(arg2).get("agi") + "\n意志："
				+ list.get(arg2).get("wil") + "\n平衡："
				+ list.get(arg2).get("bal") + "\n攻速："
				+ list.get(arg2).get("attspd") + "\n暴击："
				+ list.get(arg2).get("critical") + "\n防御："
				+ list.get(arg2).get("def") + "\n暴抗："
				+ list.get(arg2).get("critresist") + "\n生命值："
				+ list.get(arg2).get("shengmingzhi") + "\n体力:"
				+ list.get(arg2).get("sta"));
		Toast.makeText(CheckSimulationDifferentActivity.this, "已复制", 0).show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			final int arg2, long arg3) {
		new AlertDialog.Builder(CheckSimulationDifferentActivity.this)
				.setItems(new String[] { "删除" },
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								// LogUtils.e("点击", "点击");
								dao.delplan(list.get(arg2).get("_id"));
								init();
							}
						}).show();
		return true;
	}

}
