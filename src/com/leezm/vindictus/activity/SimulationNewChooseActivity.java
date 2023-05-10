package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.ManualEnchantAdapter;
import com.leezm.vindictus.adapter.ManualEquipmentAdapter;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.ChangeToDBName;
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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class SimulationNewChooseActivity extends Activity implements
		OnClickListener {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	// GDTAD
	@ViewInject(R.id.new_simulation_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String changevalue = "", advalue;
	// init
	public static final int SUCCESS = 1, FAIL = 0, EARRINGS = 1, HEAD = 2,
			WEAPONS = 3, CHEST = 4, DEPUTYWEAPON = 5, LEG = 6, HAND = 7,
			BELT = 8, FOOT = 9, BROOCH = 10, RINGF = 11, RINGS = 12,
			ARTWARE = 13, BRACELETF = 14, BRACELETS = 15;
	private String value;
	@ViewInject(R.id.bt_new_simulation_equipment_choose)
	RelativeLayout btequipment;
	@ViewInject(R.id.bt_new_simulation_enchant_choose)
	RelativeLayout btenchant;
	@ViewInject(R.id.bt_new_simulation_other_choose)
	RelativeLayout btother;
	@ViewInject(R.id.view_new_simulation_equipment_choose)
	LinearLayout viewequipment;
	@ViewInject(R.id.view_new_simulation_enchant_choose)
	LinearLayout viewenchant;
	@ViewInject(R.id.view_new_simulation_other_choose)
	LinearLayout viewother;
	@ViewInject(R.id.img_new_simulation_equipment_choose)
	ImageView imgequipment;
	@ViewInject(R.id.img_new_simulation_enchant_choose)
	ImageView imgenchant;
	@ViewInject(R.id.img_new_simulation_other_choose)
	ImageView imgother;
	@ViewInject(R.id.bt_new_simulation_cancel)
	RelativeLayout btcancel;
	@ViewInject(R.id.bt_new_simulation_save)
	RelativeLayout btsave;

	// 90装备view组件
	@ViewInject(R.id.new_simulation_equipment_nine)
	ScrollView zbnineview;

	// 记录上次选择
	SharedPreferences preferences;
	Editor editor;

	// 装备选择组件
	@ViewInject(R.id.new_simulation_equipment_spinner_level)
	Spinner spinnerzblevel;
	@ViewInject(R.id.new_simulation_equipment_spinner_role)
	Spinner spinnerzbrole;
	@ViewInject(R.id.new_simulation_equipment_spinner_type)
	Spinner spinnerzbtype;
	@ViewInject(R.id.new_simulation_equipment_listview)
	ListView listViewzb;
	@ViewInject(R.id.txt_new_simulation_equipment_choose_title)
	TextView textViewzbtitle;
	private String slevel = "", srole, stype = "", zbtitle = "", zblevel = "",
			zbatt = "0", zbbal = "0", zbcritical = "0", zbattspd = "0",
			zbmint = "0", zbagi = "0", zbwil = "0", zbpart = "", zbtype = "",
			zbrole = "0", zbcritresist = "0", zbdef = "0",
			zbshengmingzhi = "0", zbsta = "0", zbremarks = "", zbstr = "0";
	ManualEquipmentAdapter mAdapterzb;
	SimulationInterface dao = new SimulationImpl();
	private List<Map<String, String>> listzb;

	// 附魔选择组件
	@ViewInject(R.id.new_simulation_enchant_spinner_level_first)
	Spinner spinnerfmfirst;
	@ViewInject(R.id.new_simulation_enchant_spinner_level_second)
	Spinner spinnerfmsecond;
	@ViewInject(R.id.new_simulation_enchant_listview_first)
	ListView listViewfmf;
	@ViewInject(R.id.new_simulation_enchant_listview_second)
	ListView listViewfms;
	@ViewInject(R.id.txt_new_simulation_enchant_choose_title)
	TextView textViewfmtitle;
	private String fmvaluename = "", fmlevelf = "", fmlevels = "",
			fmnamef = "", fmnames = "", fmftitle = "无", fmflevel = "",
			fmfatt = "0", fmfbal = "0", fmfcritical = "0", fmfattspd = "0",
			fmfmint = "0", fmfagi = "0", fmfwil = "0", fmfpart = "0",
			fmftype = "0", fmfrole = "0", fmfcritresist = "0", fmfdef = "0",
			fmfshengmingzhi = "0", fmfsta = "0", fmfremarks = "0",
			fmfstr = "0", fmstitle = "无", fmslevel = "0", fmsatt = "0",
			fmsbal = "0", fmscritical = "0", fmsattspd = "0", fmsmint = "0",
			fmsagi = "0", fmswil = "0", fmspart = "0", fmstype = "0",
			fmsrole = "0", fmscritresist = "0", fmsdef = "0",
			fmsshengmingzhi = "0", fmssta = "0", fmsremarks = "0",
			fmsstr = "0";
	private int fmsetf = 0, fmsets = 0;
	private List<Map<String, String>> listfmf, listfms;
	ManualEnchantAdapter mAdapterfmf, mAdapterfms;

	// 其他选择组件
	@ViewInject(R.id.new_simulation_other_spinner_qianghua)
	Spinner spinnerqtqh;
	@ViewInject(R.id.new_simulation_other_spinner_jinglingshi)
	Spinner spinnerqtjls;
	@ViewInject(R.id.new_simulation_other_spinner_pinzhi)
	Spinner spinnerqtpz;
	@ViewInject(R.id.new_simulation_txt_qianghua)
	TextView textViewqtqh;
	@ViewInject(R.id.new_simulation_txt_jinglingshi)
	TextView textViewqtjls;
	@ViewInject(R.id.new_simulation_txt_pinzhi)
	TextView textViewqtpz;
	private String s1 = "无", s2 = "无", s3 = "2星", rString = "饰品", iatt = "0",
			ispd = "0", idef = "0", ibal = "0", ibao = "0";
	private int tspd = 0, tdef = 0, tbal = 0, tbao = 0, t1 = 0, t2 = 0, t3 = 0,
			j1 = 0, j2 = 0, j3 = 0, j4 = 0, j5 = 0;

	// 90装备组件
	@ViewInject(R.id.new_simulation_equipment_nine_bt)
	LinearLayout viewninebt;
	@ViewInject(R.id.new_simulation_equipment_nine_wq_main_view)
	RelativeLayout viewninemain;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_ruili_view)
	RelativeLayout viewwqruili;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_qingying_view)
	RelativeLayout viewwqqingying;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_wending_view)
	RelativeLayout viewwqwending;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_wanmei_view)
	RelativeLayout viewwqwanmei;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_jieshi_view)
	RelativeLayout viewfjjieshi;
	@ViewInject(R.id.new_simulation_equipment_nine_fj_guanghua_view)
	RelativeLayout viewfjguanghua;
	@ViewInject(R.id.new_simulation_equipment_nine_main_title)
	TextView txtmaintitle;
	@ViewInject(R.id.new_simulation_equipment_nine_main_pinzhi)
	TextView txtmainpinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_main_title_f)
	TextView txtmainf;
	@ViewInject(R.id.new_simulation_equipment_nine_main_title_s)
	TextView txtmains;
	private boolean blninemain = true, blruili = true, blqingying = true,
			blwending = true, blwanmei = true, bljieshi = true,
			blguanghua = true;
	@ViewInject(R.id.new_simulation_equipment_nine_main_title_f_ed)
	EditText edmainf;
	@ViewInject(R.id.new_simulation_equipment_nine_main_title_s_ed)
	EditText edmains;
	@ViewInject(R.id.new_simulation_equipment_nine_ruili_title)
	TextView txtwqruilititle;
	@ViewInject(R.id.new_simulation_equipment_nine_ruili_pinzhi)
	TextView txtwqruilipinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_qingying_title)
	TextView txtwqqingyingtitle;
	@ViewInject(R.id.new_simulation_equipment_nine_qingying_pinzhi)
	TextView txtwqqingyingpinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_title)
	TextView txtwqwendingtitle;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_pinzhi)
	TextView txtwqwendingpinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title)
	TextView txtwqwanmeititle;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_pinzhi)
	TextView txtwqwanmeipinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title)
	TextView txtjieshititle;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_pinzhi)
	TextView txtjieshipinzhi;
	@ViewInject(R.id.new_simulation_equipment_nine_guanghua_title)
	TextView txtguanghuatitle;
	@ViewInject(R.id.new_simulation_equipment_nine_guanghua_pinzhi)
	TextView txtguanghuapinzhi;

	@ViewInject(R.id.new_simulation_equipment_nine_ruili_title_f_ed)
	EditText edruilif;
	@ViewInject(R.id.new_simulation_equipment_nine_ruili_title_s_ed)
	EditText edruilis;
	@ViewInject(R.id.new_simulation_equipment_nine_qingying_title_f_ed)
	EditText edqingyingf;
	@ViewInject(R.id.new_simulation_equipment_nine_qingying_title_s_ed)
	EditText edqingyings;

	@ViewInject(R.id.new_simulation_equipment_nine_wending_title_f_ed)
	EditText edwendinga;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_title_s_ed)
	EditText edwendingb;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_title_t_ed)
	EditText edwendingc;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_title_fo_ed)
	EditText edwendingd;
	@ViewInject(R.id.new_simulation_equipment_nine_wending_title_fi_ed)
	EditText edwendinge;

	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title_f_ed)
	EditText edwanmeia;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title_s_ed)
	EditText edwanmeib;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title_t_ed)
	EditText edwanmeic;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title_fo_ed)
	EditText edwanmeid;
	@ViewInject(R.id.new_simulation_equipment_nine_wanmei_title_fi_ed)
	EditText edwanmeie;

	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title_f_ed)
	EditText edjieshia;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title_s_ed)
	EditText edjieshib;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title_t_ed)
	EditText edjieshic;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title_fo_ed)
	EditText edjieshid;
	@ViewInject(R.id.new_simulation_equipment_nine_jieshi_title_fi_ed)
	EditText edjieshie;

	@ViewInject(R.id.new_simulation_equipment_nine_guanghua_title_f_ed)
	EditText edguanghuaa;
	@ViewInject(R.id.new_simulation_equipment_nine_guanghua_title_s_ed)
	EditText edguanghuab;

	// TODO
	@ViewInject(R.id.bt_new_simulation_choose_nine_clean)
	RelativeLayout btnineclean;
	@ViewInject(R.id.bt_new_simulation_choose_nine_random)
	RelativeLayout btninerandom;
	@ViewInject(R.id.bt_new_simulation_choose_nine_save)
	RelativeLayout btninesave;
	private int iiatt, iispd, iibal, iibao, iistr, iiminjie, iiyizhi, iizhili,
			iidef, iikang;
	private float pzs = 1, pzg = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulation_new_choose);
		ViewUtils.inject(this);
		value = this.getIntent().getStringExtra("key");
		changevalue = new ChangeToDBName().changevaluename(value);
		// System.err.println(changevalue);
		init();
		initequipment();
		initenchant();
		initother();
		initnineequipment();
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
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(SimulationNewChooseActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", SimulationNewChooseActivity.this);
			}
			
			@Override
			public void onADReceiv() {
				LogUtils.i("AD_DEMO", "ONBannerReceive");
			}
		});
		adGroup.addView(bannerView);
		bannerView.loadAD();
	}

	private void init() {
		preferences = this.getSharedPreferences("SimulationNewChooseActivity",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		spinnerzblevel.setSelection(preferences.getInt("spinnerzblevel", 0));
		spinnerzbrole.setSelection(preferences.getInt("spinnerzbrole", 0));
		spinnerfmfirst.setSelection(preferences.getInt("spinnerfmfirst", 0));
		spinnerfmsecond.setSelection(preferences.getInt("spinnerfmsecond", 0));

		btequipment.setOnClickListener(this);
		btenchant.setOnClickListener(this);
		btother.setOnClickListener(this);
		btcancel.setOnClickListener(this);
		btsave.setOnClickListener(this);

		if (changevalue.equals("头")) {
			rString = "头部";
		} else if (changevalue.equals("手")) {
			rString = "手部";
		} else if (changevalue.equals("脚")) {
			rString = "脚部";
		} else if (changevalue.equals("副")) {
			rString = "盾牌";
		} else if (changevalue.equals("胸部")) {
			rString = "胸部";
		} else if (changevalue.equals("腿")) {
			rString = "腿部";
		} else if (changevalue.equals("武器")) {
			rString = "武器";
		}

	}

	private void getotheritem() {
		s1 = spinnerqtqh.getSelectedItem().toString();
		s2 = spinnerqtjls.getSelectedItem().toString();
		s3 = spinnerqtpz.getSelectedItem().toString();
		// TODO
		if (s3.equals("1星")) {
			pzg = (float) 0.96;
			pzs = (float) 0.8;
		} else if (s3.equals("2星")) {
			pzg = (float) 1;
			pzs = (float) 1;
		} else if (s3.equals("3星")) {
			pzg = (float) 1.02;
			pzs = (float) 1.15;
		} else if (s3.equals("4星")) {
			pzg = (float) 1.04;
			pzs = (float) 1.2;
		} else if (s3.equals("5星")) {
			pzg = (float) 1.06;
			pzs = (float) 1.25;
		}

		// System.err.println("pzg"+pzg);
		// System.err.println("pzs"+pzs);

		if (s2.equals("+134物攻")) {
			j1 = 134;
		} else if (s2.equals("+189魔攻")) {
			j1 = 189;
		} else if (s2.equals("+2平衡")) {
			j2 = 2;
		} else if (s2.equals("+2暴击")) {
			j3 = 2;
		} else if (s2.equals("+1攻速")) {
			j4 = 1;
		} else if (s2.equals("+105防御")) {
			j5 = 105;
		}

		if (changevalue.equals("武器")) {
			if (s1.equals("+10")) {
				s1 = s1 + "   " + "攻击+1000，攻速+15，追加伤害+120";
				t1 = 1000;
				t2 = 15;
			} else if (s1.equals("+11")) {
				s1 = s1 + "   " + "攻击+1500，攻速+19，追加伤害+150";
				t1 = 1500;
				t2 = 19;
			} else if (s1.equals("+12")) {
				s1 = s1 + "   " + "攻击+2000，攻速+23，追加伤害+180";
				t1 = 2000;
				t2 = 23;
			} else if (s1.equals("+13")) {
				s1 = s1 + "   " + "攻击+2600，攻速+28，追加伤害+220";
				t1 = 2600;
				t2 = 28;
			} else if (s1.equals("+14")) {
				s1 = s1 + "   " + "攻击+3300，攻速+33，追加伤害+260";
				t1 = 3300;
				t2 = 33;
			} else if (s1.equals("+15")) {
				s1 = s1 + "   " + "攻击+4100，攻速+38，追加伤害+300";
				t1 = 4100;
				t2 = 38;
			} else if (s1.equals("+8")) {
				s1 = s1 + "   " + "攻击+700，攻速+9，追加伤害+60";
				t1 = 700;
				t2 = 9;
			} else if (s1.equals("+9")) {
				s1 = s1 + "   " + "攻击+850，攻速+12，追加伤害+90";
				t1 = 850;
				t2 = 12;
			}
		} else if (changevalue.equals("副")) {
			if (s1.equals("+10")) {
				s1 = s1 + "   " + "防御+305";
				t3 = 305;
			} else if (s1.equals("+11")) {
				s1 = s1 + "   " + "防御+355";
				t3 = 355;
			} else if (s1.equals("+12")) {
				s1 = s1 + "   " + "防御+405";
				t3 = 405;
			} else if (s1.equals("+13")) {
				s1 = s1 + "   " + "防御+455";
				t3 = 455;
			} else if (s1.equals("+14")) {
				s1 = s1 + "   " + "防御+520";
				t3 = 520;
			} else if (s1.equals("+15")) {
				s1 = s1 + "   " + "防御+585";
				t3 = 585;
			} else if (s1.equals("+8")) {
				s1 = s1 + "   " + "防御+229";
				t3 = 229;
			} else if (s1.equals("+9")) {
				s1 = s1 + "   " + "防御+267";
				t3 = 267;
			}
		} else if (changevalue.equals("头") || changevalue.equals("手")
				|| changevalue.equals("脚") || changevalue.equals("胸部")
				|| changevalue.equals("腿")) {
			if (stype.equals("布甲")) {
				if (s1.equals("+10")) {
					s1 = s1 + "   " + "防御+76" + "   " + "碎后防御减为防具+0时的25%";
					t3 = 76;
				} else if (s1.equals("+11")) {
					s1 = s1 + "   " + "防御+91" + "   " + "碎后防御减为防具+0时的30%";
					t3 = 91;
				} else if (s1.equals("+12")) {
					s1 = s1 + "   " + "防御+106" + "   " + "碎后防御减为防具+0时的35%";
					t3 = 106;
				} else if (s1.equals("+13")) {
					s1 = s1 + "   " + "防御+121" + "   " + "碎后防御减为防具+0时的40%";
					t3 = 121;
				} else if (s1.equals("+14")) {
					s1 = s1 + "   " + "防御+141" + "   " + "碎后防御减为防具+0时的45%";
					t3 = 141;
				} else if (s1.equals("+15")) {
					s1 = s1 + "   " + "防御+161" + "   " + "碎后防御减为防具+0时的50%";
					t3 = 161;
				} else if (s1.equals("+8")) {
					s1 = s1 + "   " + "防御+56" + "   " + "碎后防御减为防具+0时的15%";
					t3 = 56;
				} else if (s1.equals("+9")) {
					s1 = s1 + "   " + "防御+66" + "   " + "碎后防御减为防具+0时的20%";
					t3 = 66;
				}
			} else if (stype.equals("皮甲")) {
				if (s1.equals("+10")) {
					s1 = s1 + "   " + "防御+107" + "   " + "碎后防御减为防具+0时的25%";
					t3 = 107;
				} else if (s1.equals("+11")) {
					s1 = s1 + "   " + "防御+131" + "   " + "碎后防御减为防具+0时的30%";
					t3 = 131;
				} else if (s1.equals("+12")) {
					s1 = s1 + "   " + "防御+155" + "   " + "碎后防御减为防具+0时的35%";
					t3 = 155;
				} else if (s1.equals("+13")) {
					s1 = s1 + "   " + "防御+179" + "   " + "碎后防御减为防具+0时的40%";
					t3 = 179;
				} else if (s1.equals("+14")) {
					s1 = s1 + "   " + "防御+214" + "   " + "碎后防御减为防具+0时的45%";
					t3 = 214;
				} else if (s1.equals("+15")) {
					s1 = s1 + "   " + "防御+249" + "   " + "碎后防御减为防具+0时的50%";
					t3 = 249;
				} else if (s1.equals("+8")) {
					s1 = s1 + "   " + "防御+79" + "   " + "碎后防御减为防具+0时的15%";
					t3 = 79;
				} else if (s1.equals("+9")) {
					s1 = s1 + "   " + "防御+93" + "   " + "碎后防御减为防具+0时的20%";
					t3 = 93;
				}
			} else if (stype.equals("重甲")) {
				if (s1.equals("+10")) {
					s1 = s1 + "   " + "防御+148" + "   " + "碎后防御减为防具+0时的25%";
					t3 = 148;
				} else if (s1.equals("+11")) {
					s1 = s1 + "   " + "防御+172" + "   " + "碎后防御减为防具+0时的30%";
					t3 = 172;
				} else if (s1.equals("+12")) {
					s1 = s1 + "   " + "防御+196" + "   " + "碎后防御减为防具+0时的35%";
					t3 = 196;
				} else if (s1.equals("+13")) {
					s1 = s1 + "   " + "防御+220" + "   " + "碎后防御减为防具+0时的40%";
					t3 = 220;
				} else if (s1.equals("+14")) {
					s1 = s1 + "   " + "防御+255" + "   " + "碎后防御减为防具+0时的45%";
					t3 = 255;
				} else if (s1.equals("+15")) {
					s1 = s1 + "   " + "防御+290" + "   " + "碎后防御减为防具+0时的50%";
					t3 = 290;
				} else if (s1.equals("+8")) {
					s1 = s1 + "   " + "防御+110" + "   " + "碎后防御减为防具+0时的15%";
					t3 = 110;
				} else if (s1.equals("+9")) {
					s1 = s1 + "   " + "防御+129" + "   " + "碎后防御减为防具+0时的20%";
					t3 = 129;
				}
			} else if (stype.equals("板甲")) {
				if (s1.equals("+10")) {
					s1 = s1 + "   " + "防御+189" + "   " + "碎后防御减为防具+0时的25%";
					t3 = 189;
				} else if (s1.equals("+11")) {
					s1 = s1 + "   " + "防御+224" + "   " + "碎后防御减为防具+0时的30%";
					t3 = 224;
				} else if (s1.equals("+12")) {
					s1 = s1 + "   " + "防御+259" + "   " + "碎后防御减为防具+0时的35%";
					t3 = 259;
				} else if (s1.equals("+13")) {
					s1 = s1 + "   " + "防御+294" + "   " + "碎后防御减为防具+0时的40%";
					t3 = 294;
				} else if (s1.equals("+14")) {
					s1 = s1 + "   " + "防御+339" + "   " + "碎后防御减为防具+0时的45%";
					t3 = 339;
				} else if (s1.equals("+15")) {
					s1 = s1 + "   " + "防御+384" + "   " + "碎后防御减为防具+0时的50%";
					t3 = 384;
				} else if (s1.equals("+8")) {
					s1 = s1 + "   " + "防御+141" + "   " + "碎后防御减为防具+0时的15%";
					t3 = 141;
				} else if (s1.equals("+9")) {
					s1 = s1 + "   " + "防御+165" + "   " + "碎后防御减为防具+0时的20%";
					t3 = 165;
				}
			}
		}

		t1 = t1 + j1;// 攻击
		t2 = t2 + j4;// 攻速
		t3 = t3 + j5;// 防御
		// j2平衡 j3暴击
		iatt = String.valueOf(t1);
		ispd = String.valueOf(t2);
		idef = String.valueOf(t3);
		ibal = String.valueOf(j2);
		ibao = String.valueOf(j3);

		textViewqtqh.setText(s1);
		textViewqtjls.setText(s2);
		textViewqtpz.setText(s3);
	}

	private void initother() {
		spinnerqtqh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getotheritem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerqtjls.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getotheritem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		spinnerqtpz.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getotheritem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void initenchant() {
		fmvaluename = changevalue;
		if (changevalue.equals("耳环")) {
			fmvaluename = "首饰";
		} else if (changevalue.equals("腰带")) {
			fmvaluename = "首饰";
		} else if (changevalue.equals("胸针")) {
			fmvaluename = "首饰";
		} else if (changevalue.equals("戒指")) {
			fmvaluename = "首饰";
		} else if (changevalue.equals("工艺品")) {
			fmvaluename = "首饰";
		}

		spinnerfmfirst.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getenchantitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerfmsecond.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getenchantitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}

	private void getenchantitem() {
		fmlevelf = spinnerfmfirst.getSelectedItem().toString();
		fmlevels = spinnerfmsecond.getSelectedItem().toString();
		listfmf = dao.selectenchant(
				new ChangeToDBName().changeallname(fmvaluename),
				new ChangeToDBName().changeallname(fmlevelf), "首次");
		listfms = dao.selectenchant(
				new ChangeToDBName().changeallname(fmvaluename),
				new ChangeToDBName().changeallname(fmlevels), "进阶");
		mAdapterfmf = new ManualEnchantAdapter(this, listfmf);
		mAdapterfms = new ManualEnchantAdapter(this, listfms);
		listViewfmf.setAdapter(mAdapterfmf);
		listViewfms.setAdapter(mAdapterfms);
		listViewfmf.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				fmftitle = listfmf.get(position).get("title");
				fmflevel = listfmf.get(position).get("level");
				fmfatt = listfmf.get(position).get("att");
				fmfbal = listfmf.get(position).get("bal");
				fmfcritical = listfmf.get(position).get("critical");
				fmfattspd = listfmf.get(position).get("attspd");
				fmfstr = listfmf.get(position).get("str");
				fmfmint = listfmf.get(position).get("mint");
				fmfagi = listfmf.get(position).get("agi");
				fmfwil = listfmf.get(position).get("wil");
				fmfpart = listfmf.get(position).get("part");
				fmftype = listfmf.get(position).get("style");
				fmfcritresist = listfmf.get(position).get("critresist");
				fmfdef = listfmf.get(position).get("def");
				fmfshengmingzhi = listfmf.get(position).get("shengmingzhi");
				fmfsta = listfmf.get(position).get("sta");

				fmnamef = listfmf.get(position).get("title");
				textViewfmtitle.setText("附魔选择：首次：" + fmnamef + "   进阶："
						+ fmnames);
				fmsetf = 1;
				if (fmsetf == 1 & fmsets == 1) {
					viewgone();
					imgreset();
				}
			}
		});
		listViewfms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				fmstitle = listfms.get(position).get("title");
				fmslevel = listfms.get(position).get("level");
				fmsatt = listfms.get(position).get("att");
				fmsbal = listfms.get(position).get("bal");
				fmscritical = listfms.get(position).get("critical");
				fmsattspd = listfms.get(position).get("attspd");
				fmsstr = listfms.get(position).get("str");
				fmsmint = listfms.get(position).get("mint");
				fmsagi = listfms.get(position).get("agi");
				fmswil = listfms.get(position).get("wil");
				fmspart = listfms.get(position).get("part");
				fmstype = listfms.get(position).get("style");
				fmscritresist = listfms.get(position).get("critresist");
				fmsdef = listfms.get(position).get("def");
				fmsshengmingzhi = listfms.get(position).get("shengmingzhi");
				fmssta = listfms.get(position).get("sta");

				fmnames = listfms.get(position).get("title");
				textViewfmtitle.setText("附魔选择：首次：" + fmnamef + "   进阶："
						+ fmnames);
				fmsets = 1;
				if (fmsetf == 1 & fmsets == 1) {
					viewgone();
					imgreset();
				}
			}
		});
	}

	private void initequipment() {
		chushitxtcolor();
		sethit();
		spinnerzblevel.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getequipmentitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerzbrole.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getequipmentitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinnerzbtype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				getequipmentitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void cleanallequipmentitemview() {
		listViewzb.setVisibility(View.GONE);
		zbnineview.setVisibility(View.GONE);
		viewninebt.setVisibility(View.GONE);
		viewninemain.setVisibility(View.GONE);
		viewwqqingying.setVisibility(View.GONE);
		viewwqruili.setVisibility(View.GONE);
		viewwqwanmei.setVisibility(View.GONE);
		viewwqwending.setVisibility(View.GONE);
		viewfjjieshi.setVisibility(View.GONE);
		viewfjguanghua.setVisibility(View.GONE);
	}

	private void initnineequipment() {
		btnineclean.setOnClickListener(this);
		btninerandom.setOnClickListener(this);
		btninesave.setOnClickListener(this);

		txtmainpinzhi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (blninemain) {
					txtmaintitle.setTextColor(Color.rgb(255, 0, 255));
					txtmainpinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtmainpinzhi.setText("紫色");
					sethit();
					blninemain = false;
				} else {
					txtmaintitle.setTextColor(Color.rgb(255, 125, 0));
					txtmainpinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtmainpinzhi.setText("橙色");
					sethit();
					blninemain = true;
				}
			}
		});

		txtwqruilipinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (blruili) {
					txtwqruilipinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtwqruilititle.setTextColor(Color.rgb(255, 0, 255));
					txtwqruilipinzhi.setText("紫色");
					sethit();
					blruili = false;
				} else {
					txtwqruilipinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtwqruilititle.setTextColor(Color.rgb(255, 125, 0));
					txtwqruilipinzhi.setText("橙色");
					sethit();
					blruili = true;
				}
			}
		});

		txtwqqingyingpinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (blqingying) {
					txtwqqingyingpinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtwqqingyingtitle.setTextColor(Color.rgb(255, 0, 255));
					txtwqqingyingpinzhi.setText("紫色");
					sethit();
					blqingying = false;
				} else {
					txtwqqingyingpinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtwqqingyingtitle.setTextColor(Color.rgb(255, 125, 0));
					txtwqqingyingpinzhi.setText("橙色");
					sethit();
					blqingying = true;
				}
			}
		});

		txtwqwendingpinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (blwending) {
					txtwqwendingpinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtwqwendingtitle.setTextColor(Color.rgb(255, 0, 255));
					txtwqwendingpinzhi.setText("紫色");
					sethit();
					blwending = false;
				} else {
					txtwqwendingpinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtwqwendingtitle.setTextColor(Color.rgb(255, 125, 0));
					txtwqwendingpinzhi.setText("橙色");
					sethit();
					blwending = true;
				}
			}
		});

		txtwqwanmeipinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (blwanmei) {
					txtwqwanmeipinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtwqwanmeititle.setTextColor(Color.rgb(255, 0, 255));
					txtwqwanmeipinzhi.setText("紫色");
					sethit();
					blwanmei = false;
				} else {
					txtwqwanmeipinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtwqwanmeititle.setTextColor(Color.rgb(255, 125, 0));
					txtwqwanmeipinzhi.setText("橙色");
					sethit();
					blwanmei = true;
				}
			}
		});

		txtguanghuapinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (blguanghua) {
					txtguanghuapinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtguanghuatitle.setTextColor(Color.rgb(255, 0, 255));
					txtguanghuapinzhi.setText("紫色");
					sethit();
					blguanghua = false;
				} else {
					txtguanghuapinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtguanghuatitle.setTextColor(Color.rgb(255, 125, 0));
					txtguanghuapinzhi.setText("橙色");
					sethit();
					blguanghua = true;
				}
			}
		});

		txtjieshipinzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (bljieshi) {
					txtjieshipinzhi.setTextColor(Color.rgb(255, 0, 255));
					txtjieshititle.setTextColor(Color.rgb(255, 0, 255));
					txtjieshipinzhi.setText("紫色");
					sethit();
					bljieshi = false;
				} else {
					txtjieshipinzhi.setTextColor(Color.rgb(255, 125, 0));
					txtjieshititle.setTextColor(Color.rgb(255, 125, 0));
					txtjieshipinzhi.setText("橙色");
					sethit();
					bljieshi = true;
				}
			}
		});
	}

	// 材料名字颜色和品质颜色设置
	private void chushitxtcolor() {
		txtmaintitle.setTextColor(Color.rgb(255, 125, 0));
		txtmainpinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtwqruilititle.setTextColor(Color.rgb(255, 125, 0));
		txtwqruilipinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtwqqingyingpinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtwqqingyingtitle.setTextColor(Color.rgb(255, 125, 0));
		txtwqwendingpinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtwqwendingtitle.setTextColor(Color.rgb(255, 125, 0));
		txtwqwanmeipinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtwqwanmeititle.setTextColor(Color.rgb(255, 125, 0));
		txtguanghuapinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtguanghuatitle.setTextColor(Color.rgb(255, 125, 0));
		txtjieshipinzhi.setTextColor(Color.rgb(255, 125, 0));
		txtjieshititle.setTextColor(Color.rgb(255, 125, 0));
	}

	// 设置edittxt提示
	private void sethit() {
		if (changevalue.equals("武器")) {
			if (txtwqruilipinzhi.getText().toString().equals("紫色")) {
				edruilif.setHint("24-27");
				edruilis.setHint("25-28");
			} else {
				edruilif.setHint("27-30");
				edruilis.setHint("28-31");
			}
			if (txtwqqingyingpinzhi.getText().toString().equals("紫色")) {
				edqingyingf.setHint("17-19");
				edqingyings.setHint("2-4");
			} else {
				edqingyingf.setHint("19-21");
				edqingyings.setHint("4-5");
			}

			if (txtwqwendingpinzhi.getText().toString().equals("紫色")) {
				edwendinga.setHint("36-40");
				edwendingb.setHint("31-35");
				edwendingc.setHint("41-47");
				edwendingd.setHint("17-18");
				edwendinge.setHint("22-24");
			} else {
				edwendinga.setHint("41-45");
				edwendingb.setHint("35-44");
				edwendingc.setHint("47-59");
				edwendingd.setHint("19-24");
				edwendinge.setHint("26-32");
			}

			if (txtwqwanmeipinzhi.getText().toString().equals("紫色")) {
				edwanmeia.setHint("4059-4342");
				edwanmeib.setHint("46-52");
				edwanmeic.setHint("62-70");
				edwanmeid.setHint("25-28");
				edwanmeie.setHint("34-37");
			} else {
				edwanmeia.setHint("4390-4720");
				edwanmeib.setHint("53-66");
				edwanmeic.setHint("71-89");
				edwanmeid.setHint("29-35");
				edwanmeie.setHint("38-48");
			}

			if (txtmainpinzhi.getText().toString().equals("紫色")) {
				edmainf.setHint("6089-6514");
				edmains.setHint("2");
			} else {
				edmainf.setHint("6584-7080");
				edmains.setHint("3-4");
			}
		} else {
			if (txtguanghuapinzhi.getText().toString().equals("紫色")) {
				edguanghuaa.setHint("555-622");
				edguanghuab.setHint("3");
			} else {
				edguanghuaa.setHint("629-740");
				edguanghuab.setHint("3-4");
			}

			if (txtjieshipinzhi.getText().toString().equals("紫色")) {
				edjieshia.setHint("126-142");
				edjieshib.setHint("170-192");
				edjieshic.setHint("9-11");
				edjieshid.setHint("63-71");
				edjieshie.setHint("56-63");
			} else {
				edjieshia.setHint("144-180");
				edjieshib.setHint("194-243");
				edjieshic.setHint("10-13");
				edjieshid.setHint("72-90");
				edjieshie.setHint("64-80");
			}

			if (changevalue.equals("头") || changevalue.equals("手")
					|| changevalue.equals("脚")) {
				if (txtmainpinzhi.getText().toString().equals("紫色")) {
					edmainf.setHint("1107-1240");
					edmains.setHint("2-3");
				} else {
					edmainf.setHint("1255-1476");
					edmains.setHint("3");
				}
			} else if (changevalue.equals("胸部")) {
				if (txtmainpinzhi.getText().toString().equals("紫色")) {
					edmainf.setHint("699-783");
					edmains.setHint("3");
				} else {
					edmainf.setHint("793-932");
					edmains.setHint("3-4");
				}
			} else if (changevalue.equals("腿")) {
				if (txtmainpinzhi.getText().toString().equals("紫色")) {
					edmainf.setHint("641-717");
					edmains.setHint("5-6");
				} else {
					edmainf.setHint("726-855");
					edmains.setHint("5-7");
				}
			} else if (changevalue.equals("副")) {
				if (txtmainpinzhi.getText().toString().equals("紫色")) {
					edmainf.setHint("1443-1616/1776-1989");
					edmains.setHint("1");
				} else {
					edmainf.setHint("1635-1922/2013-2368");
					edmains.setHint("1");
				}
			}

		}

	}

	private void cleanninetozero() {
		edmainf.setText("");
		edmains.setText("");
		edruilif.setText("");
		edruilis.setText("");
		edqingyingf.setText("");
		edqingyings.setText("");
		edwendinga.setText("");
		edwendingb.setText("");
		edwendingc.setText("");
		edwendingd.setText("");
		edwendinge.setText("");
		edwanmeia.setText("");
		edwanmeib.setText("");
		edwanmeic.setText("");
		edwanmeid.setText("");
		edwanmeie.setText("");
		edjieshia.setText("");
		edjieshib.setText("");
		edjieshic.setText("");
		edjieshid.setText("");
		edjieshie.setText("");
		edguanghuaa.setText("");
		edguanghuab.setText("");
	}

	private void getrandom() {
		if (txtwqruilipinzhi.getText().toString().equals("橙色")) {
			edruilif.setText(String.valueOf(CommonUtils.getRandom(27, 30)));
			edruilis.setText(String.valueOf(CommonUtils.getRandom(28, 31)));
		} else {
			edruilif.setText(String.valueOf(CommonUtils.getRandom(24, 27)));
			edruilis.setText(String.valueOf(CommonUtils.getRandom(25, 28)));
		}
		if (txtwqqingyingpinzhi.getText().toString().equals("橙色")) {
			edqingyingf.setText(String.valueOf(CommonUtils.getRandom(19, 21)));
			edqingyings.setText(String.valueOf(CommonUtils.getRandom(4, 5)));
		} else {
			edqingyingf.setText(String.valueOf(CommonUtils.getRandom(17, 19)));
			edqingyings.setText(String.valueOf(CommonUtils.getRandom(2, 4)));
		}
		if (txtwqwendingpinzhi.getText().toString().equals("橙色")) {
			edwendinga.setText(String.valueOf(CommonUtils.getRandom(41, 45)));
			edwendingb.setText(String.valueOf(CommonUtils.getRandom(35, 44)));
			edwendingc.setText(String.valueOf(CommonUtils.getRandom(47, 59)));
			edwendingd.setText(String.valueOf(CommonUtils.getRandom(19, 24)));
			edwendinge.setText(String.valueOf(CommonUtils.getRandom(26, 32)));
		} else {
			edwendinga.setText(String.valueOf(CommonUtils.getRandom(36, 40)));
			edwendingb.setText(String.valueOf(CommonUtils.getRandom(31, 35)));
			edwendingc.setText(String.valueOf(CommonUtils.getRandom(41, 47)));
			edwendingd.setText(String.valueOf(CommonUtils.getRandom(17, 18)));
			edwendinge.setText(String.valueOf(CommonUtils.getRandom(22, 24)));
		}
		if (txtwqwanmeipinzhi.getText().toString().equals("橙色")) {
			edwanmeia
					.setText(String.valueOf(CommonUtils.getRandom(4390, 4720)));
			edwanmeib.setText(String.valueOf(CommonUtils.getRandom(53, 66)));
			edwanmeic.setText(String.valueOf(CommonUtils.getRandom(71, 89)));
			edwanmeid.setText(String.valueOf(CommonUtils.getRandom(29, 35)));
			edwanmeie.setText(String.valueOf(CommonUtils.getRandom(38, 48)));
		} else {
			edwanmeia
					.setText(String.valueOf(CommonUtils.getRandom(4059, 4342)));
			edwanmeib.setText(String.valueOf(CommonUtils.getRandom(46, 52)));
			edwanmeic.setText(String.valueOf(CommonUtils.getRandom(62, 70)));
			edwanmeid.setText(String.valueOf(CommonUtils.getRandom(25, 28)));
			edwanmeie.setText(String.valueOf(CommonUtils.getRandom(34, 37)));
		}
		if (txtjieshipinzhi.getText().toString().equals("橙色")) {
			edjieshia.setText(String.valueOf(CommonUtils.getRandom(144, 180)));
			edjieshib.setText(String.valueOf(CommonUtils.getRandom(194, 243)));
			edjieshic.setText(String.valueOf(CommonUtils.getRandom(10, 13)));
			edjieshid.setText(String.valueOf(CommonUtils.getRandom(72, 90)));
			edjieshie.setText(String.valueOf(CommonUtils.getRandom(64, 80)));
		} else {
			edjieshia.setText(String.valueOf(CommonUtils.getRandom(126, 142)));
			edjieshib.setText(String.valueOf(CommonUtils.getRandom(170, 192)));
			edjieshic.setText(String.valueOf(CommonUtils.getRandom(9, 11)));
			edjieshid.setText(String.valueOf(CommonUtils.getRandom(63, 71)));
			edjieshie.setText(String.valueOf(CommonUtils.getRandom(56, 63)));
		}
		if (txtguanghuapinzhi.getText().toString().equals("橙色")) {
			edguanghuaa
					.setText(String.valueOf(CommonUtils.getRandom(629, 740)));
			edguanghuab.setText(String.valueOf(CommonUtils.getRandom(3, 4)));
		} else {
			edguanghuaa
					.setText(String.valueOf(CommonUtils.getRandom(555, 622)));
			edguanghuab.setText(String.valueOf(CommonUtils.getRandom(3, 3)));
		}
		if (txtmainpinzhi.getText().toString().equals("橙色")) {
			if (changevalue.equals("武器")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(6584, 7080)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(3, 4)));
			} else if (changevalue.equals("头") || changevalue.equals("手")
					|| changevalue.equals("脚")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(1255, 1476)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(3, 3)));
			} else if (changevalue.equals("副")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(1635, 1922)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(1, 1)));
			} else if (changevalue.equals("胸部")) {
				edmainf.setText(String.valueOf(CommonUtils.getRandom(793, 932)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(3, 4)));
			} else if (changevalue.equals("腿")) {
				edmainf.setText(String.valueOf(CommonUtils.getRandom(726, 855)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(5, 7)));
			}
		} else {
			if (changevalue.equals("武器")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(6089, 6514)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(2, 2)));
			} else if (changevalue.equals("头") || changevalue.equals("手")
					|| changevalue.equals("脚")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(1107, 1240)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(2, 3)));
			} else if (changevalue.equals("副")) {
				edmainf.setText(String.valueOf(CommonUtils
						.getRandom(1443, 1616)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(1, 1)));
			} else if (changevalue.equals("胸部")) {
				edmainf.setText(String.valueOf(CommonUtils.getRandom(699, 783)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(3, 3)));
			} else if (changevalue.equals("腿")) {
				edmainf.setText(String.valueOf(CommonUtils.getRandom(641, 717)));
				edmains.setText(String.valueOf(CommonUtils.getRandom(5, 6)));
			}
		}
	}

	private void getequipmentitem() {
		cleanallequipmentitemview();
		slevel = spinnerzblevel.getSelectedItem().toString();
		srole = spinnerzbrole.getSelectedItem().toString();
		stype = spinnerzbtype.getSelectedItem().toString();
		if (slevel.equals("Lv90")) {
			// 这里写90装备需要显示的view
			zbnineview.setVisibility(View.VISIBLE);// 显示90装备主view
			viewninebt.setVisibility(View.VISIBLE);// 显示90装备按钮
			// LogUtils.e("", changevalue);
			if (changevalue.equals("武器")) {
				viewninemain.setVisibility(View.VISIBLE);
				viewwqqingying.setVisibility(View.VISIBLE);
				viewwqruili.setVisibility(View.VISIBLE);
				viewwqwanmei.setVisibility(View.VISIBLE);
				viewwqwending.setVisibility(View.VISIBLE);
				txtmaintitle.setText("封印的力量：武器");
				txtmainf.setText("攻");
				txtmains.setText("速");
			} else if (changevalue.equals("头") || changevalue.equals("手")
					|| changevalue.equals("脚") || changevalue.equals("副")) {
				viewninemain.setVisibility(View.VISIBLE);
				viewfjjieshi.setVisibility(View.VISIBLE);
				txtmainf.setText("防");
				txtmains.setText("抗");
				if (changevalue.equals("头")) {
					txtmaintitle.setText("封印的力量：头部");
				} else if (changevalue.equals("手")) {
					txtmaintitle.setText("封印的力量：手部");
				} else if (changevalue.equals("脚")) {
					txtmaintitle.setText("封印的力量：脚部");
				} else if (changevalue.equals("副")) {
					txtmaintitle.setText("封印的力量：小盾/巨盾");
				}
			} else if (changevalue.equals("胸部") || changevalue.equals("腿")) {
				viewninemain.setVisibility(View.VISIBLE);
				viewfjjieshi.setVisibility(View.VISIBLE);
				viewfjguanghua.setVisibility(View.VISIBLE);
				txtmainf.setText("防");
				txtmains.setText("抗");
				if (changevalue.equals("胸部")) {
					txtmaintitle.setText("封印的力量：胸部");
				} else if (changevalue.equals("腿")) {
					txtmaintitle.setText("封印的力量：腿部");
				}
			}
		} else {
			selectequipment(value, slevel, srole, stype);
			// LogUtils.e("selectequipment",value+slevel+srole+stype);
		}
	}

	private void selectequipment(String simulation, String level, String role,
			String remarks) {
		listzb = dao.selectequipment(changevalue,
				new ChangeToDBName().changelevelname(level), role,
				new ChangeToDBName().changeallname(remarks));
		mAdapterzb = new ManualEquipmentAdapter(this, listzb);
		listViewzb.setAdapter(mAdapterzb);
		listViewzb.setVisibility(View.VISIBLE);
		// LogUtils.e("", listzb.toString());
		listViewzb.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				viewgone();
				imgreset();
				textViewzbtitle.setText("装备选择："
						+ listzb.get(position).get("title"));
				zbtitle = listzb.get(position).get("title");
				zblevel = listzb.get(position).get("level");
				zbatt = listzb.get(position).get("att");
				zbbal = listzb.get(position).get("bal");
				zbcritical = listzb.get(position).get("critical");
				zbattspd = listzb.get(position).get("attspd");
				zbstr = listzb.get(position).get("str");
				zbmint = listzb.get(position).get("mint");
				zbagi = listzb.get(position).get("agi");
				zbwil = listzb.get(position).get("wil");
				zbpart = listzb.get(position).get("part");
				zbtype = listzb.get(position).get("type");
				zbrole = listzb.get(position).get("role");
				zbcritresist = listzb.get(position).get("critresist");
				zbdef = listzb.get(position).get("def");
				zbshengmingzhi = listzb.get(position).get("shengmingzhi");
				zbsta = listzb.get(position).get("sta");
				zbremarks = listzb.get(position).get("remarks");
			}
		});
	}

	private void viewgone() {
		viewequipment.setVisibility(View.GONE);
		viewenchant.setVisibility(View.GONE);
		viewother.setVisibility(View.GONE);
		viewninebt.setVisibility(View.GONE);
	}

	private void imgreset() {
		imgequipment.setBackgroundResource(R.drawable.icon_right);
		imgenchant.setBackgroundResource(R.drawable.icon_right);
		imgother.setBackgroundResource(R.drawable.icon_right);
	}

	private void saveintent() {
		Intent intent = new Intent();
		if (value.equals("EARRINGS")) {
			setResult(EARRINGS, intent);
		} else if (value.equals("HEAD")) {
			setResult(HEAD, intent);
		} else if (value.equals("WEAPONS")) {
			setResult(WEAPONS, intent);
		} else if (value.equals("CHEST")) {
			setResult(CHEST, intent);
		} else if (value.equals("DEPUTYWEAPON")) {
			setResult(DEPUTYWEAPON, intent);
		} else if (value.equals("LEG")) {
			setResult(LEG, intent);
		} else if (value.equals("HAND")) {
			setResult(HAND, intent);
		} else if (value.equals("BELT")) {
			setResult(BELT, intent);
		} else if (value.equals("FOOT")) {
			setResult(FOOT, intent);
		} else if (value.equals("BROOCH")) {
			setResult(BROOCH, intent);
		} else if (value.equals("RINGF")) {
			setResult(RINGF, intent);
		} else if (value.equals("RINGS")) {
			setResult(RINGS, intent);
		} else if (value.equals("ARTWARE")) {
			setResult(ARTWARE, intent);
		} else if (value.equals("BRACELETF")) {
			setResult(BRACELETF, intent);
		} else if (value.equals("BRACELETS")) {
			setResult(BRACELETS, intent);
		}
	}

	private String addtodb(String a, String b, String c, String d) {
		String adddbString;
		adddbString = String.valueOf((Integer.valueOf(a) + Integer.valueOf(b)
				+ Integer.valueOf(c) + Integer.valueOf(d)));
		return adddbString;
	}

	private String addpzatt(String a, boolean atty) {
		String addpzString;
		if (atty) {
			addpzString = String.valueOf((int) (Float.valueOf(a) * pzg));
		} else {
			addpzString = String.valueOf((int) (Float.valueOf(a) * pzs));
		}
		return addpzString;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_new_simulation_equipment_choose:
			viewgone();
			imgreset();
			imgequipment.setBackgroundResource(R.drawable.icon_down);
			viewequipment.setVisibility(View.VISIBLE);
			if (slevel.equals("Lv90")) {
				viewninebt.setVisibility(View.VISIBLE);// 显示90装备按钮
			}
			break;
		case R.id.bt_new_simulation_enchant_choose:
			viewgone();
			imgreset();
			imgenchant.setBackgroundResource(R.drawable.icon_down);
			viewenchant.setVisibility(View.VISIBLE);
			break;
		case R.id.bt_new_simulation_other_choose:
			viewgone();
			imgreset();
			imgother.setBackgroundResource(R.drawable.icon_down);
			viewother.setVisibility(View.VISIBLE);
			break;

		case R.id.bt_new_simulation_cancel:
			// LogUtils.e("", addtodb("0", "1", "2", "3"));
			// LogUtils.e("", addtodb("1", "2", "3", "4"));
			finish();
			break;
		case R.id.bt_new_simulation_save:
			// 品质算法
			zbatt = addpzatt(zbatt, true);
			zbstr = addpzatt(zbstr, false);
			zbmint = addpzatt(zbmint, false);
			zbagi = addpzatt(zbagi, false);
			zbwil = addpzatt(zbwil, false);
			zbdef = addpzatt(zbdef, false);
			// 装备添加到数据库
			dao.addequipment(
					rString + "：" + zbtitle,
					zblevel,
					addtodb(zbatt, fmfatt, fmsatt, iatt),
					addtodb(zbbal, fmfbal, fmsbal, ibal),
					addtodb(zbcritical, fmfcritical, fmscritical, ibao),
					addtodb(zbattspd, fmfattspd, fmsattspd, ispd),
					addtodb(zbstr, fmfstr, fmsstr, "0"),
					addtodb(zbmint, fmfmint, fmsmint, "0"),
					addtodb(zbagi, fmfagi, fmsagi, "0"),
					addtodb(zbwil, fmfwil, fmswil, "0"),
					zbpart,
					zbtype,
					zbrole,
					addtodb(zbcritresist, fmfcritresist, fmscritresist, "0"),
					addtodb(zbdef, fmfdef, fmsdef, idef),
					addtodb(zbshengmingzhi, fmfshengmingzhi, fmsshengmingzhi,
							"0"), addtodb(zbsta, fmfsta, fmssta, "0"),
							"附魔首次：" + fmftitle + "\n附魔进阶："
									+ fmstitle + "\n强化：" + s1 + "\n精灵石：" + s2 + "\n品质："
									+ s3);

			/**
			 * // 附魔首次添加到数据库 dao.addenchant(rString + "附魔首次:" + fmftitle,
			 * fmflevel, fmfatt, fmfbal, fmfcritical, fmfattspd, fmfstr,
			 * fmfmint, fmfagi, fmfwil, fmfpart, fmftype, fmfcritresist, fmfdef,
			 * fmfshengmingzhi, fmfsta); // 附魔进阶添加到数据库 dao.addenchants(rString +
			 * "附魔进阶:" + fmstitle, fmslevel, fmsatt, fmsbal, fmscritical,
			 * fmsattspd, fmsstr, fmsmint, fmsagi, fmswil, fmspart, fmstype,
			 * fmscritresist, fmsdef, fmsshengmingzhi, fmssta); // 其他强化精灵石添加到数据库
			 * dao.addenchant(rString + "强化:" + s1 + "精灵石：" + s2, "", iatt,
			 * ibal, ibao, ispd, "0", "0", "0", "0", rString, stype, "0", idef,
			 * "0", "0");
			 */
			saveintent();
			finish();
			break;
		case R.id.bt_new_simulation_choose_nine_clean:
			AlertDialogUtils.showDialog(this, "确定要清空全部数值吗？",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							cleanninetozero();
						}
					}, new OnClickNoListener() {
						@Override
						public void onClickNo() {

						}
					});
			break;
		case R.id.bt_new_simulation_choose_nine_random:
			AlertDialogUtils.showDialog(this, "随机生成数值将会覆盖已输入数据！",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							getrandom();
						}
					}, new OnClickNoListener() {
						@Override
						public void onClickNo() {
						}
					});
			break;
		case R.id.bt_new_simulation_choose_nine_save:
			// TODO
			gocheckninenotnull();
			getninesum();
			AlertDialogUtils.showDialog(this, "是否保存当前" + rString + "？\n攻击："
					+ iiatt + "\n攻速：" + iispd + "\n平衡：" + iibal + "\n暴击："
					+ iibao + "\n力量：" + iistr + "\n智力：" + iizhili + "\n意志："
					+ iiyizhi + "\n敏捷：" + iiminjie + "\n防御：" + iidef
					+ "\n暴击抵抗：" + iikang, new OnClickYesListener() {
				@Override
				public void onClickYes() {
					zbtitle = "Lv90" + rString;
					zblevel = "Lv90";
					zbatt = String.valueOf(iiatt);
					zbattspd = String.valueOf(iispd);
					zbbal = String.valueOf(iibal);
					zbcritical = String.valueOf(iibao);
					zbstr = String.valueOf(iistr);
					zbmint = String.valueOf(iizhili);
					zbwil = String.valueOf(iiyizhi);
					zbagi = String.valueOf(iiminjie);
					zbdef = String.valueOf(iidef);
					zbcritresist = String.valueOf(iikang);
					viewgone();
					imgreset();
					textViewzbtitle.setText("装备选择：Lv90：" + rString);
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

	private void checkninenulltozero(EditText editText) {
		if (editText.getText().length() == 0) {
			editText.setText("0");
		}
	}

	private int changenineint(EditText editText) {
		int abc = Integer.valueOf(editText.getText().toString());
		return abc;
	}

	private void getninesum() {
		iiatt = iispd = iibal = iibao = iistr = iiminjie = iiyizhi = iizhili = iidef = iikang = 0;
		if (changevalue.equals("武器")) {
			iiatt = changenineint(edmainf) + changenineint(edwanmeia);
			iispd = changenineint(edmains) + changenineint(edqingyings);
			iibal = changenineint(edruilif) + changenineint(edwendinga);
			iibao = changenineint(edruilis) + changenineint(edqingyingf);
			iistr = changenineint(edwendingb) + changenineint(edwanmeib);
			iiminjie = changenineint(edwendingd) + changenineint(edwanmeid);
			iiyizhi = changenineint(edwendinge) + changenineint(edwanmeie);
			iizhili = changenineint(edwendingc) + changenineint(edwanmeic);
		} else if (changevalue.equals("胸部") | changevalue.equals("腿")) {
			iidef = changenineint(edmainf) + changenineint(edguanghuaa);
			iikang = changenineint(edmains) + changenineint(edjieshic)
					+ changenineint(edguanghuab);
			iistr = changenineint(edjieshia);
			iizhili = changenineint(edjieshib);
			iiminjie = changenineint(edjieshid);
			iiyizhi = changenineint(edjieshie);
		} else if (changevalue.equals("头") | changevalue.equals("手")
				| changevalue.equals("脚") | changevalue.equals("副")) {
			iidef = changenineint(edmainf);
			iikang = changenineint(edmains) + changenineint(edjieshic);
			iistr = changenineint(edjieshia);
			iizhili = changenineint(edjieshib);
			iiminjie = changenineint(edjieshid);
			iiyizhi = changenineint(edjieshie);
		}
	}

	private void gocheckninenotnull() {
		checkninenulltozero(edmainf);
		checkninenulltozero(edmains);
		checkninenulltozero(edruilif);
		checkninenulltozero(edruilis);
		checkninenulltozero(edqingyingf);
		checkninenulltozero(edqingyings);
		checkninenulltozero(edwendinga);
		checkninenulltozero(edwendingb);
		checkninenulltozero(edwendingc);
		checkninenulltozero(edwendingd);
		checkninenulltozero(edwendinge);
		checkninenulltozero(edwanmeia);
		checkninenulltozero(edwanmeib);
		checkninenulltozero(edwanmeic);
		checkninenulltozero(edwanmeid);
		checkninenulltozero(edwanmeie);
		checkninenulltozero(edjieshia);
		checkninenulltozero(edjieshib);
		checkninenulltozero(edjieshic);
		checkninenulltozero(edjieshid);
		checkninenulltozero(edjieshie);
		checkninenulltozero(edguanghuaa);
		checkninenulltozero(edguanghuab);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		editor.putInt("spinnerzblevel",
				spinnerzblevel.getSelectedItemPosition());
		editor.putInt("spinnerzbrole", spinnerzbrole.getSelectedItemPosition());
		editor.putInt("spinnerfmfirst",
				spinnerfmfirst.getSelectedItemPosition());
		editor.putInt("spinnerfmsecond",
				spinnerfmsecond.getSelectedItemPosition());
		editor.commit();
	}

}
