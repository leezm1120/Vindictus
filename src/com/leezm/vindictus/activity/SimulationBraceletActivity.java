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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SimulationBraceletActivity extends Activity implements
		OnClickListener {

	@ViewInject(R.id.new_simulation_bracelet_gj_att)
	EditText edgja;
	@ViewInject(R.id.new_simulation_bracelet_gj_def)
	EditText edgjb;
	@ViewInject(R.id.new_simulation_bracelet_gj_smz)
	EditText edgjc;
	@ViewInject(R.id.new_simulation_bracelet_zj_att)
	EditText edzja;
	@ViewInject(R.id.new_simulation_bracelet_zj_def)
	EditText edzjb;
	@ViewInject(R.id.new_simulation_bracelet_zj_smz)
	EditText edzjc;
	@ViewInject(R.id.new_simulation_bracelet_dj_att)
	EditText eddja;
	@ViewInject(R.id.new_simulation_bracelet_dj_def)
	EditText eddjb;
	@ViewInject(R.id.new_simulation_bracelet_dj_smz)
	EditText eddjc;
	@ViewInject(R.id.bt_new_simulation_bracelet_cancel)
	RelativeLayout btcancel;
	@ViewInject(R.id.bt_new_simulation_bracelet_clean)
	RelativeLayout btclean;
	@ViewInject(R.id.bt_new_simulation_bracelet_save)
	RelativeLayout btsave;
	// GDTAD
	@ViewInject(R.id.simulation_bracelet_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	private String value, rString;
	public static final int BRACELETF = 14, BRACELETS = 15;
	SimulationInterface dao = new SimulationImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_bracelet);
		ViewUtils.inject(this);
		value = this.getIntent().getStringExtra("key");
		if (value.equals("BRACELETF")) {
			rString = "左手镯";
		} else {
			rString = "右手镯";
		}
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
				MobclickAgent.onEvent(SimulationBraceletActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", SimulationBraceletActivity.this);
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

	private void initmain() {
		btcancel.setOnClickListener(this);
		btclean.setOnClickListener(this);
		btsave.setOnClickListener(this);
	}

	private void saveintent() {
		Intent intent = new Intent();
		if (value.equals("BRACELETF")) {
			setResult(BRACELETF, intent);
		} else if (value.equals("BRACELETS")) {
			setResult(BRACELETS, intent);
		}
	}

	private void cleanalled() {
		edgja.setText("");
		edgjb.setText("");
		edgjc.setText("");
		edzja.setText("");
		edzjb.setText("");
		edzjc.setText("");
		eddja.setText("");
		eddjb.setText("");
		eddjc.setText("");
	}

	private int changetoint(EditText editText) {
		if (editText.getText().length() == 0) {
			editText.setText("0");
		}
		int a = Integer.valueOf(editText.getText().toString());
		return a;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_new_simulation_bracelet_cancel:
			SimulationBraceletActivity.this.finish();
			break;
		case R.id.bt_new_simulation_bracelet_clean:
			AlertDialogUtils.showDialog(this, "确定要清空所有数据吗？",
					new OnClickYesListener() {
						@Override
						public void onClickYes() {
							cleanalled();
						}
					}, new OnClickNoListener() {
						@Override
						public void onClickNo() {

						}
					});
			break;
		case R.id.bt_new_simulation_bracelet_save:
			AlertDialogUtils
					.showDialog(
							this,
							"确定保存"
									+ rString
									+ "吗？\n攻击："
									+ (changetoint(edgja) + changetoint(edzja) + changetoint(eddja))
									+ "\n防御："
									+ (changetoint(edgjb) + changetoint(edzjb) + changetoint(eddjb))
									+ "\n生命值："
									+ (changetoint(edgjc) + changetoint(edzjc) + changetoint(eddjc)),
							new OnClickYesListener() {
								@Override
								public void onClickYes() {
									dao.addequipment("手镯："+rString, "Lv80", String.valueOf((changetoint(edgja)
											+ changetoint(edzja) + changetoint(eddja))), "0", "0", "0", "0", "0", "0", "0", "", "", "", "0", String.valueOf((changetoint(edgjb)
													+ changetoint(edzjb) + changetoint(eddjb))), String.valueOf((changetoint(edgjc)
															+ changetoint(edzjc) + changetoint(eddjc))), "0", "攻击："
																	+ String.valueOf((changetoint(edgja)
																			+ changetoint(edzja) + changetoint(eddja)))
																	+ "\n防御："
																	+ String.valueOf((changetoint(edgjb)
																			+ changetoint(edzjb) + changetoint(eddjb)))
																	+ "\n生命值："
																	+ String.valueOf((changetoint(edgjc)
																			+ changetoint(edzjc) + changetoint(eddjc))));
//									dao.addenchant(
//											rString,
//											"Lv80",
//											String.valueOf((changetoint(edgja)
//													+ changetoint(edzja) + changetoint(eddja))),
//											"0",
//											"0",
//											"0",
//											"0",
//											"0",
//											"0",
//											"0",
//											"攻击："
//													+ String.valueOf((changetoint(edgja)
//															+ changetoint(edzja) + changetoint(eddja)))
//													+ " 防御："
//													+ String.valueOf((changetoint(edgjb)
//															+ changetoint(edzjb) + changetoint(eddjb)))
//													+ " 生命值："
//													+ String.valueOf((changetoint(edgjc)
//															+ changetoint(edzjc) + changetoint(eddjc))),
//											"首饰",
//											"0",
//											String.valueOf((changetoint(edgjb)
//													+ changetoint(edzjb) + changetoint(eddjb))),
//											String.valueOf((changetoint(edgjc)
//													+ changetoint(edzjc) + changetoint(eddjc))),
//											"0");
									saveintent();
									SimulationBraceletActivity.this.finish();
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
