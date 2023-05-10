package com.leezm.vindictus.activity;

import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SimulationRolesActivity extends Activity implements
		OnClickListener {

	private String value, rString = "人物属性";
	public static final int ROLES = 17;
	SimulationInterface dao = new SimulationImpl();

	@ViewInject(R.id.new_simulation_roles_minjie)
	EditText edminjie;
	@ViewInject(R.id.new_simulation_roles_smz)
	EditText edsmz;
	@ViewInject(R.id.new_simulation_roles_str)
	EditText edliliang;
	@ViewInject(R.id.new_simulation_roles_tili)
	EditText edtili;
	@ViewInject(R.id.new_simulation_roles_yizhi)
	EditText edyizhi;
	@ViewInject(R.id.new_simulation_roles_zhili)
	EditText edzhili;
	@ViewInject(R.id.bt_new_simulation_roles_cancel)
	RelativeLayout btcancel;
	@ViewInject(R.id.bt_new_simulation_roles_clean)
	RelativeLayout btclean;
	@ViewInject(R.id.bt_new_simulation_roles_save)
	RelativeLayout btsave;
	SharedPreferences preferences;
	Editor editor;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	// GDTAD
	@ViewInject(R.id.roles_simulation_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String changevalue = "", advalue;

	// GDTAD
	private void initgdtad() {
		bannerView = new BannerView(this, ADSize.BANNER,
				GdtAdConstants.GDTAPPID, GdtAdConstants.GDTBANNERID);
		bannerView.setRefresh(30);
		bannerView.setShowClose(true);
		bannerView.setADListener(new AbstractBannerADListener() {

			@Override
			public void onNoAD(int arg0) {
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(SimulationRolesActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", SimulationRolesActivity.this);
			}
			
			@Override
			public void onADReceiv() {
				LogUtils.i("AD_DEMO", "ONBannerReceive");
			}
		});
		adGroup.addView(bannerView);
		bannerView.loadAD();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_roles);
		ViewUtils.inject(this);
		value = this.getIntent().getStringExtra("key");
		initmain();
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

	private void initmain() {
		preferences = getSharedPreferences("SimulationRolesActivity",
				MODE_PRIVATE);
		editor = preferences.edit();
		edliliang.setText(preferences.getString("edliliang", null));
		edminjie.setText(preferences.getString("edminjie", null));
		edsmz.setText(preferences.getString("edsmz", null));
		edtili.setText(preferences.getString("edtili", null));
		edyizhi.setText(preferences.getString("edyizhi", null));
		edzhili.setText(preferences.getString("edzhili", null));
		btcancel.setOnClickListener(this);
		btclean.setOnClickListener(this);
		btsave.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		editor.putString("edliliang", edliliang.getText().toString());
		editor.putString("edminjie", edminjie.getText().toString());
		editor.putString("edsmz", edsmz.getText().toString());
		editor.putString("edtili", edtili.getText().toString());
		editor.putString("edyizhi", edyizhi.getText().toString());
		editor.putString("edzhili", edzhili.getText().toString());
		editor.commit();
	}

	private void saveintent() {
		Intent intent = new Intent();
		setResult(ROLES, intent);
	}

	private void setzero() {
		edliliang.setText("");
		edminjie.setText("");
		edsmz.setText("");
		edtili.setText("");
		edyizhi.setText("");
		edzhili.setText("");
	}

	private String checknotnull(EditText editText) {
		if (editText.getText().length() == 0) {
			editText.setText("0");
		}
		String aa = editText.getText().toString();
		return aa;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_new_simulation_roles_cancel:
			SimulationRolesActivity.this.finish();
			break;
		case R.id.bt_new_simulation_roles_clean:
			AlertDialogUtils.showDialog(this, "确定要清空已输入数据吗？",
					new OnClickYesListener() {
						@Override
						public void onClickYes() {
							setzero();
						}
					}, new OnClickNoListener() {
						@Override
						public void onClickNo() {
						}
					});
			break;
		case R.id.bt_new_simulation_roles_save:
			AlertDialogUtils.showDialog(this, "是否保存当前人物属性？\n力量："
					+ checknotnull(edliliang) + "\n智力：" + checknotnull(edzhili)
					+ "\n敏捷：" + checknotnull(edminjie) + "\n意志："
					+ checknotnull(edyizhi) + "\nHP：" + checknotnull(edsmz)
					+ "\n体力：" + checknotnull(edtili), new OnClickYesListener() {
				@Override
				public void onClickYes() {
					dao.addequipment("属性：人物无装备属性", "", "0", "0", "0", "0",
							checknotnull(edliliang), checknotnull(edzhili),
							checknotnull(edminjie), checknotnull(edyizhi),
							"力量：" + checknotnull(edliliang) + " 智力："
									+ checknotnull(edzhili) + " 敏捷："
									+ checknotnull(edminjie) + " 意志："
									+ checknotnull(edyizhi) + " HP："
									+ checknotnull(edsmz) + " 体力："
									+ checknotnull(edtili), "", "", "0", "0",
							checknotnull(edsmz), checknotnull(edtili), "力量："
									+ checknotnull(edliliang) + "\n智力：" + checknotnull(edzhili)
									+ "\n敏捷：" + checknotnull(edminjie) + "\n意志："
									+ checknotnull(edyizhi) + "\nHP：" + checknotnull(edsmz)
									+ "\n体力：" + checknotnull(edtili));
					saveintent();
					SimulationRolesActivity.this.finish();
				}
			}, new OnClickNoListener() {
				@Override
				public void onClickNo() {
				}
			});
			break;

		default:
			break;
		}
	}

}
