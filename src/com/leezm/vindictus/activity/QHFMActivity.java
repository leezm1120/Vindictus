package com.leezm.vindictus.activity;

import com.leezm.vindictus.activity.R.color;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.constants.ErrorCode.InitError;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.os.Bundle;
import android.os.Handler;
import android.R.interpolator;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QHFMActivity extends Activity implements OnClickListener {

	@ViewInject(R.id.qhfm_bt_qh)
	RelativeLayout btqh;
	@ViewInject(R.id.qhfm_bt_fm)
	RelativeLayout btfm;
	@ViewInject(R.id.qhfm_img_qh)
	ImageView btimqh;
	@ViewInject(R.id.qhfm_img_fm)
	ImageView btimfm;
	@ViewInject(R.id.qhfm_view_qh)
	LinearLayout Viewqh;
	@ViewInject(R.id.qhfm_view_fm)
	LinearLayout Viewfm;
	@ViewInject(R.id.qhfm_spinner_qh)
	Spinner qhsSpinner;
	@ViewInject(R.id.qhfm_qh_cishu)
	TextView cishuTextView;
	@ViewInject(R.id.qhfm_qh_shitou)
	TextView shitouTextView;
	@ViewInject(R.id.qhfm_qh_yaoshui)
	TextView yaoshuiTextView;
	@ViewInject(R.id.qhfm_qh_baohushi)
	TextView baohushiTextView;
	@ViewInject(R.id.qhfm_qh_elevenbaohushi)
	TextView elevenbhsTextView;
	@ViewInject(R.id.qhfm_qh_suidiao)
	TextView suidiaoTextView;
	@ViewInject(R.id.qhfm_qh_checkbox_baohushi)
	CheckBox baohushiCheckBox;
	@ViewInject(R.id.qhfm_qh_checkbox_eleven_baohushi)
	CheckBox elevenbhsCheckBox;
	@ViewInject(R.id.qhfm_qh_checkbox_zhuangbeicongzhi)
	CheckBox chongzhiCheckBox;
	@ViewInject(R.id.bt_qhfm_qh_auto)
	RelativeLayout btqhauto;
	@ViewInject(R.id.bt_qhfm_qh_one)
	RelativeLayout btqhone;
	@ViewInject(R.id.bt_qhfm_qh_reset)
	RelativeLayout btreset;
	@ViewInject(R.id.qhfm_qh_one_text)
	TextView qhoneTextView;
	@ViewInject(R.id.qhfm_qh_auto_text)
	TextView autotTextView;
	@ViewInject(R.id.qhfm_qh_fail)
	TextView failcishutTextView;
	@ViewInject(R.id.qhfm_qh_jilv)
	TextView seccedtTextView;
	@ViewInject(R.id.qhfm_fm_text_result)
	TextView fmresult;
	@ViewInject(R.id.qhfm_fm_text_total_cishu)
	TextView fmlingyaoshengyu;
	@ViewInject(R.id.qhfm_fm_text_total_br)
	TextView totalbr;
	@ViewInject(R.id.qhfm_fm_text_total_jilv)
	TextView totaljilv;
	@ViewInject(R.id.qhfm_spinner_fm)
	Spinner fmSpinner;
	@ViewInject(R.id.bt_qhfm_fm_reset)
	RelativeLayout btfmreset;
	@ViewInject(R.id.bt_qhfm_fm_go)
	RelativeLayout btfmgo;
	@ViewInject(R.id.qhfm_fm_bulin_mofa)
	RelativeLayout mofa;
	@ViewInject(R.id.qhfm_fm_bulin_shengmingmofa)
	RelativeLayout shengmingmofa;
	@ViewInject(R.id.qhfm_fm_bulin_wushimofa)
	RelativeLayout wushimofa;
	@ViewInject(R.id.qhfm_fm_bulin_yibaimofa)
	RelativeLayout yibaimofa;
	@ViewInject(R.id.qhfm_fm_bulin_zufumofa)
	RelativeLayout zufumofa;
	@ViewInject(R.id.qhfm_fm_zongcishu)
	TextView fmzongcishu;
	@ViewInject(R.id.qhfm_fm_chenggongcishu)
	TextView fmchenggongcishu;
	@ViewInject(R.id.qhfm_fm_shijijilv)
	TextView fmshijijilv;

	// GDTAD
	@ViewInject(R.id.qhfm_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	Toast mToast;

	private int bt1 = 0, bt2 = 0, bt3 = 0, icishu = 0, iyaoshui = 0,
			ishitou = 0, ibaohushi = 0, ielevenbhs = 0, isuidiao = 0,
			level = 0, is1 = 0, is2 = 0, is3 = 0, if4 = 0, if5 = 0, if6 = 0,
			if7 = 0, if8 = 0, if9 = 0, if10 = 0, if11 = 0, if12 = 0, if13 = 0,
			if14 = 0, if15 = 0, fmtotal, iaa, ibb, zx1, zx2, zx3;

	private float is4 = 0, is5 = 0, is6 = 0, is7 = 0, is8 = 0, is9 = 0,
			is10 = 0, is11 = 0, is12 = 0, is13 = 0, is14 = 0, is15 = 0,
			it4 = 0, it5 = 0, it6 = 0, it7 = 0, it8 = 0, it9 = 0, it10 = 0,
			it11 = 0, it12 = 0, it13 = 0, it14 = 0, it15 = 0, ibrtotal = 0,
			ifmjichujilv = 0, ifmzongcishu = 0, ifmchenggongcishu = 0,
			ifmshijijilv = 0;
	private String qhdj;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qhfm);
		ViewUtils.inject(this);
		initqh();
		initfm();
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
				MobclickAgent.onEvent(QHFMActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", QHFMActivity.this);
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

	private void showfmtotalfm(int cishu) {
		fmlingyaoshengyu.setText("注入魔法次数：" + String.valueOf(cishu) + "");
		// System.err.println(ibrtotal);
		totalbr.setText("" + (int) ibrtotal + "br");
		if (cishu == 0) {
			mofa.setEnabled(false);
			zufumofa.setEnabled(false);
			shengmingmofa.setEnabled(false);
			wushimofa.setEnabled(false);
			yibaimofa.setEnabled(false);
		} else {
			mofa.setEnabled(true);
			zufumofa.setEnabled(true);
			shengmingmofa.setEnabled(true);
			wushimofa.setEnabled(true);
			yibaimofa.setEnabled(true);
		}
		showfmjilv(iaa);
	}

	private void initfm() {
		fmtotal = 5;
		fmresult.setText("");
		showfmtotalfm(fmtotal);
		btfm.setOnClickListener(this);
		btfmgo.setOnClickListener(this);
		btfmreset.setOnClickListener(this);
		mofa.setOnClickListener(this);
		zufumofa.setOnClickListener(this);
		shengmingmofa.setOnClickListener(this);
		wushimofa.setOnClickListener(this);
		yibaimofa.setOnClickListener(this);
		fmSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				showfmjilv(arg2);
				iaa = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showfmjilv(int fmspinnerpos) {
		if (fmspinnerpos == 0 | fmspinnerpos == 1 | fmspinnerpos == 2
				| fmspinnerpos == 3) {
			ifmjichujilv = 25;
			if (fmspinnerpos == 0) {
				ibb = (int) (ifmjichujilv + (100 - ifmjichujilv) / 100
						* ibrtotal);
				totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率"
						+ ibb + "%）");
			} else if (fmspinnerpos == 1) {
				ibb = (int) (ifmjichujilv + (40 - ifmjichujilv) / 100
						* ibrtotal);
				totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率"
						+ ibb + "%）");
			} else if (fmspinnerpos == 2) {
				ibb = (int) (ifmjichujilv + (50 - ifmjichujilv) / 100
						* ibrtotal);
				totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率"
						+ ibb + "%）");
			} else if (fmspinnerpos == 3) {
				ibb = (int) (ifmjichujilv + (60 - ifmjichujilv) / 100
						* ibrtotal);
				totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率"
						+ ibb + "%）");
			}

		} else if (fmspinnerpos == 4 | fmspinnerpos == 5) {
			ifmjichujilv = 35;
			ibb = (int) (ifmjichujilv + (74 - ifmjichujilv) / 100 * ibrtotal);
			totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率" + ibb
					+ "%）");

		} else if (fmspinnerpos == 6) {
			ifmjichujilv = 45;
			ibb = (int) (ifmjichujilv + (76 - ifmjichujilv) / 100 * ibrtotal);
			totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率" + ibb
					+ "%）");

		} else if (fmspinnerpos == 7) {
			ifmjichujilv = 45;
			ibb = (int) (ifmjichujilv + (78 - ifmjichujilv) / 100 * ibrtotal);
			totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率" + ibb
					+ "%）");

		} else if (fmspinnerpos == 8) {
			ifmjichujilv = 50;
			ibb = (int) (ifmjichujilv + (80 - ifmjichujilv) / 100 * ibrtotal);
			totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率" + ibb
					+ "%）");
		}
		if (ibrtotal > 100) {
			ibb = (int) ifmjichujilv;
			totaljilv.setText("目前魔法力：" + (int) ibrtotal + "/100（目前成功概率"
					+ (int) ifmjichujilv + "%）");
		}

	}

	private boolean fmgo(int fmjilv) {
		if (fmjilv >= CommonUtils.getRandom(0, 100)) {
			return true;
		} else {
			return false;
		}

	}

	private boolean qh(int succed) {
		if (succed >= CommonUtils.getRandom(0, 100)) {
			return true;
		} else {
			return false;
		}

	}

	private void showfailcishu() {
		failcishutTextView.setText("失败次数：0/0/0/" + if4 + "/" + if5 + "/" + if6
				+ "/" + if7 + "/" + if8 + "/" + if9 + "/" + if10 + "/" + if11
				+ "/" + if12 + "/" + if13 + "/" + if14 + "/" + if15 + "");
	}

	private void showsuccedjilv() {
		if (it4 != 0) {
			is4 = ((it4 - if4) / it4) * 100;
		}
		if (it5 != 0) {
			is5 = ((it5 - if5) / it5) * 100;
		}
		if (it6 != 0) {
			is6 = ((it6 - if6) / it6) * 100;
		}
		if (it7 != 0) {
			is7 = ((it7 - if7) / it7) * 100;
		}
		if (it8 != 0) {
			is8 = ((it8 - if8) / it8) * 100;
		}
		if (it9 != 0) {
			is9 = ((it9 - if9) / it9) * 100;
		}
		if (it10 != 0) {
			is10 = ((it10 - if10) / it10) * 100;
		}
		if (it11 != 0) {
			is11 = ((it11 - if11) / it11) * 100;
		}
		if (it12 != 0) {
			is12 = ((it12 - if12) / it12) * 100;
		}
		if (it13 != 0) {
			is13 = ((it13 - if13) / it13) * 100;
		}
		if (it14 != 0) {
			is14 = ((it14 - if14) / it14) * 100;
		}
		if (it15 != 0) {
			is15 = ((it15 - if15) / it15) * 100;
		}

		seccedtTextView.setText("实际几率：" + is1 + "/" + is2 + "/" + is3 + "/"
				+ (int) is4 + "/" + (int) is5 + "/" + (int) is6 + "/"
				+ (int) is7 + "/" + (int) is8 + "/" + (int) is9 + "/"
				+ (int) is10 + "/" + (int) is11 + "/" + (int) is12 + "/"
				+ (int) is13 + "/" + (int) is14 + "/" + (int) is15 + "");
	}

	private void successorfail(String dengji) {
		level = qhsSpinner.getSelectedItemPosition();
		if (dengji.equals("+1") | dengji.equals("+2") | dengji.equals("+0")) {
			qhsSpinner.setSelection(level + 1);
			ishitou = ishitou + 2;
			if (dengji.equals("+0")) {
				is1 = 100;
			} else if (dengji.equals("+1")) {
				is2 = 100;
			} else if (dengji.equals("+2")) {
				is3 = 100;
			}
		} else if (dengji.equals("+3")) {
			ishitou = ishitou + 3;
			it4 = it4 + 1;
			if (qh(75)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if4 = if4 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(level - 1);
					} else {

					}

				}
			}
		} else if (dengji.equals("+4")) {
			ishitou = ishitou + 3;
			it5 = it5 + 1;
			if (qh(75)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if5 = if5 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(level - 1);
					} else {

					}
				}
			}
		} else if (dengji.equals("+5")) {
			ishitou = ishitou + 4;
			iyaoshui = iyaoshui + 1;
			it6 = it6 + 1;
			if (qh(50)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if6 = if6 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
				}
			}
		} else if (dengji.equals("+6")) {
			ishitou = ishitou + 4;
			iyaoshui = iyaoshui + 1;
			it7 = it7 + 1;
			if (qh(50)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if7 = if7 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
				}
			}
		} else if (dengji.equals("+7")) {
			ishitou = ishitou + 4;
			iyaoshui = iyaoshui + 2;
			it8 = it8 + 1;
			if (qh(50)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if8 = if8 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
				}
			}
		} else if (dengji.equals("+8")) {
			ishitou = ishitou + 5;
			iyaoshui = iyaoshui + 2;
			it9 = it9 + 1;
			if (qh(40)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if9 = if9 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
					isuidiao = isuidiao + 1;
				}
			}
		} else if (dengji.equals("+9")) {
			ishitou = ishitou + 5;
			iyaoshui = iyaoshui + 2;
			it10 = it10 + 1;
			if (qh(40)) {

				qhsSpinner.setSelection(level + 1);
			} else {
				if10 = if10 + 1;
				if (baohushiCheckBox.isChecked()) {
					ibaohushi = ibaohushi + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
					isuidiao = isuidiao + 1;
				}
			}
		} else if (dengji.equals("+10")) {
			ishitou = ishitou + 6;
			iyaoshui = iyaoshui + 3;
			it11 = it11 + 1;
			if (qh(40)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if11 = if11 + 1;
				if (elevenbhsCheckBox.isChecked()) {
					ielevenbhs = ielevenbhs + 1;
				} else {
					if (chongzhiCheckBox.isChecked()) {
						qhsSpinner.setSelection(0);
					} else {

					}
					isuidiao = isuidiao + 1;
				}
			}
		} else if (dengji.equals("+11")) {
			ishitou = ishitou + 7;
			iyaoshui = iyaoshui + 3;
			it12 = it12 + 1;
			if (qh(40)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if12 = if12 + 1;
				if (chongzhiCheckBox.isChecked()) {
					qhsSpinner.setSelection(0);
				} else {

				}
				isuidiao = isuidiao + 1;
			}
		} else if (dengji.equals("+12")) {
			ishitou = ishitou + 8;
			iyaoshui = iyaoshui + 3;
			it13 = it13 + 1;
			if (qh(33)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if13 = if13 + 1;
				if (chongzhiCheckBox.isChecked()) {
					qhsSpinner.setSelection(0);
				} else {

				}
				isuidiao = isuidiao + 1;
			}
		} else if (dengji.equals("+13")) {
			ishitou = ishitou + 9;
			iyaoshui = iyaoshui + 3;
			it14 = it14 + 1;
			if (qh(33)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if14 = if14 + 1;
				if (chongzhiCheckBox.isChecked()) {
					qhsSpinner.setSelection(0);
				} else {

				}
				isuidiao = isuidiao + 1;
			}
		} else if (dengji.equals("+14")) {
			ishitou = ishitou + 10;
			iyaoshui = iyaoshui + 3;
			it15 = it15 + 1;
			if (qh(33)) {
				qhsSpinner.setSelection(level + 1);
			} else {
				if15 = if15 + 1;
				if (chongzhiCheckBox.isChecked()) {
					qhsSpinner.setSelection(0);
				} else {

				}
				isuidiao = isuidiao + 1;
			}
		}
		if (!dengji.equals("+15")) {
			icishu = icishu + 1;
		}

		showtext(icishu, ishitou, iyaoshui, ibaohushi, ielevenbhs, isuidiao);

	}

	private void qhgo() {
		qhdj = qhsSpinner.getSelectedItem().toString();
		successorfail(qhdj);
	}

	private void initqh() {
		btqh.setOnClickListener(this);
		btqhauto.setOnClickListener(this);
		btqhone.setOnClickListener(this);
		btreset.setOnClickListener(this);
		qhsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 15) {
					qhoneTextView.setTextColor(Color.rgb(43, 43, 43));
					autotTextView.setTextColor(Color.rgb(43, 43, 43));
					btqhauto.setEnabled(false);
					btqhone.setEnabled(false);
				} else {
					qhoneTextView.setTextColor(Color.rgb(255, 255, 255));
					autotTextView.setTextColor(Color.rgb(255, 255, 255));
					btqhauto.setEnabled(true);
					btqhone.setEnabled(true);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showtext(int cishu, int qianghuashi, int qianghuayaoshui,
			int baohushi, int elevenbaohushi, int suidiao) {
		cishuTextView.setText(String.valueOf(cishu));
		shitouTextView.setText(String.valueOf(qianghuashi));
		yaoshuiTextView.setText(String.valueOf(qianghuayaoshui));
		baohushiTextView.setText(String.valueOf(baohushi));
		elevenbhsTextView.setText(String.valueOf(elevenbaohushi));
		suidiaoTextView.setText(String.valueOf(suidiao));
		showfailcishu();
		showsuccedjilv();
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {
		case R.id.qhfm_bt_qh:
			if (bt1 == 0) {
				bt1 = 1;
				btimqh.setBackgroundResource(R.drawable.icon_down);
				Viewqh.setVisibility(View.VISIBLE);
				bt2 = 0;
				btimfm.setBackgroundResource(R.drawable.icon_right);
				Viewfm.setVisibility(View.GONE);
			} else {
				bt1 = 0;
				btimqh.setBackgroundResource(R.drawable.icon_right);
				Viewqh.setVisibility(View.GONE);
			}
			break;
		case R.id.qhfm_bt_fm:
			if (bt2 == 0) {
				bt2 = 1;
				btimfm.setBackgroundResource(R.drawable.icon_down);
				Viewfm.setVisibility(View.VISIBLE);
				bt1 = 0;
				btimqh.setBackgroundResource(R.drawable.icon_right);
				Viewqh.setVisibility(View.GONE);
			} else {
				bt2 = 0;
				btimfm.setBackgroundResource(R.drawable.icon_right);
				Viewfm.setVisibility(View.GONE);
			}
			break;

		case R.id.bt_qhfm_qh_one:
			qhgo();
			break;

		case R.id.bt_qhfm_qh_reset:
			icishu = iyaoshui = ishitou = ibaohushi = ielevenbhs = isuidiao = 0;
			is1 = is2 = is3 = if4 = if5 = if6 = if7 = if8 = if9 = if10 = if11 = if12 = if13 = if14 = if15 = 0;
			it4 = it5 = it6 = it7 = it8 = it9 = it10 = it11 = it12 = it13 = it14 = it15 = 0;
			is4 = is5 = is6 = is7 = is8 = is9 = is10 = is11 = is12 = is13 = is14 = is15 = 0;
			showtext(icishu, ishitou, iyaoshui, ibaohushi, ielevenbhs, isuidiao);
			qhsSpinner.setSelection(0);
			break;

		case R.id.bt_qhfm_qh_auto:
			if (chongzhiCheckBox.isChecked()) {
				// mToast.makeText(QHFMActivity.this, "正在自动强化，耗时可能较长！",
				// 0).show();
			}
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for (int i = 0; i < 10; i++) {
						qhgo();
					}
				}
			});

			break;

		case R.id.bt_qhfm_fm_go:
			if (fmgo(ibb)) {
				ifmchenggongcishu = ifmchenggongcishu + 1;
				ifmzongcishu = ifmzongcishu + 1;
				fmresult.setText("附魔成功");
			} else {
				fmresult.setText("附魔失败");
				ifmzongcishu = ifmzongcishu + 1;
			}
			zx1 = (int) ifmzongcishu;
			zx2 = (int) ifmchenggongcishu;
			zx3 = (int) ((ifmchenggongcishu / ifmzongcishu) * 100);

			fmzongcishu.setText(String.valueOf(zx1));
			fmchenggongcishu.setText(String.valueOf(zx2));
			fmshijijilv.setText(String.valueOf(zx3));
			break;
		case R.id.bt_qhfm_fm_reset:
			fmresult.setText("");
			fmtotal = 5;
			ibrtotal = 0;
			ifmchenggongcishu = 0;
			ifmzongcishu = 0;
			ifmshijijilv = 0;
			fmzongcishu.setText("0");
			fmchenggongcishu.setText("0");
			fmshijijilv.setText("0");
			showfmtotalfm(fmtotal);
			break;

		case R.id.qhfm_fm_bulin_mofa:
			ibrtotal = ibrtotal + CommonUtils.getRandom(1, 10);
			fmtotal = fmtotal - 1;
			showfmtotalfm(fmtotal);
			break;
		case R.id.qhfm_fm_bulin_shengmingmofa:
			ibrtotal = ibrtotal + CommonUtils.getRandom(3, 5);
			fmtotal = fmtotal - 1;
			showfmtotalfm(fmtotal);
			break;
		case R.id.qhfm_fm_bulin_wushimofa:
			ibrtotal = ibrtotal + 50;
			fmtotal = fmtotal - 1;
			showfmtotalfm(fmtotal);
			break;
		case R.id.qhfm_fm_bulin_yibaimofa:
			ibrtotal = ibrtotal + 100;
			fmtotal = fmtotal - 1;
			showfmtotalfm(fmtotal);
			break;
		case R.id.qhfm_fm_bulin_zufumofa:
			ibrtotal = ibrtotal + CommonUtils.getRandom(10, 50);
			fmtotal = fmtotal - 1;
			showfmtotalfm(fmtotal);
			break;
		default:
			break;
		}
	}
}
