package com.leezm.vindictus.fragment;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SimulationChooseFragmentC extends BaseFragment {

	private static String TAG = "SimulationChooseFragmentC";
	private Activity mActivity;

	@ViewInject(R.id.simulation_other_spinner_qianghua)
	Spinner qianghua;
	@ViewInject(R.id.simulation_other_spinner_jinglingshi)
	Spinner jinglingshi;
	@ViewInject(R.id.simulation_other_spinner_pinzhi)
	Spinner pinzhi;
	@ViewInject(R.id.simulation_txt_qianghua)
	TextView qianghuatTextView;
	@ViewInject(R.id.simulation_txt_jinglingshi)
	TextView jinglingshiTextView;
	@ViewInject(R.id.simulation_txt_pinzhi)
	TextView pinzhitTextView;
	private String s1, s2, s3, value, fangjutype = "", rString, iatt, ispd,
			idef, ibal, ibao;
	private int tspd, tdef, tbal, tbao, t1 = 0, t2 = 0, t3 = 0, j1 = 0, j2 = 0,
			j3 = 0, j4 = 0, j5 = 0;
	SharedPreferences preferences;
	Editor editor;
	@ViewInject(R.id.bt_simulation_choose_qita_clean)
	RelativeLayout btqitaclean;
	@ViewInject(R.id.bt_simulation_choose_qita_save)
	RelativeLayout btqitasave;
	SimulationInterface dao = new SimulationImpl();

	public static SimulationChooseFragmentC newInstance() {
		SimulationChooseFragmentC sC = new SimulationChooseFragmentC();
		return sC;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		preferences = mActivity.getSharedPreferences(
				"SimulationChooseFragmentA", Context.MODE_PRIVATE);
		fangjutype = preferences.getString("spinner3", null);
		Bundle bundle = getArguments();
		value = bundle.getString("key");

		btqitaclean.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				qianghua.setSelection(0);
				jinglingshi.setSelection(0);
				pinzhi.setSelection(0);
			}
		});
		btqitasave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeseename(value);
				t1 = t1 + j1;// 攻击
				t2 = t2 + j4;// 攻速
				t3 = t3 + j5;// 防御
				// j2平衡 j3暴击
				iatt = String.valueOf(t1);
				ispd = String.valueOf(t2);
				idef = String.valueOf(t3);
				ibal = String.valueOf(j2);
				ibao = String.valueOf(j3);
				AlertDialogUtils.showDialog(mActivity, "确定保存当前选择吗？" + rString
						+ "\n强化：" + s1 + "\n精灵石：" + s2 + "\n品质：" + s3 + "",
						new OnClickYesListener() {

							@Override
							public void onClickYes() {
								// TODO Auto-generated method stub
								// 这里写保存其他到数据库
								dao.addenchant(rString + "强化附魔品质", "", iatt,
										ibal, ibao, ispd, "0", "0", "0", "0",
										rString, fangjutype, "0", idef, "0",
										"0");
								Toast.makeText(mActivity, "已保存", 0).show();
							}
						}, new OnClickNoListener() {

							@Override
							public void onClickNo() {
								// TODO Auto-generated method stub

							}
						});
			}
		});

		qianghua.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		jinglingshi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		pinzhi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private String changeseename(String string) {
		if (value.equals("头")) {
			rString = "头部";
		} else if (value.equals("手")) {
			rString = "手部";
		} else if (value.equals("脚")) {
			rString = "脚部";
		} else if (value.equals("副")) {
			rString = "盾牌";
		} else if (value.equals("胸部")) {
			rString = "胸部";
		} else if (value.equals("腿")) {
			rString = "腿部";
		} else if (value.equals("武器")) {
			rString = "武器";
		}
		return rString;

	}

	private void getitem() {
		s1 = qianghua.getSelectedItem().toString();
		s2 = jinglingshi.getSelectedItem().toString();
		s3 = pinzhi.getSelectedItem().toString();

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

		if (value.equals("武器")) {
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
			}
		} else if (value.equals("副")) {
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
			}
		} else if (value.equals("头") || value.equals("手") || value.equals("脚")
				|| value.equals("胸部") || value.equals("腿")) {
			if (fangjutype.equals("布甲")) {
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
				}
			} else if (fangjutype.equals("皮甲")) {
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
				}
			} else if (fangjutype.equals("重甲")) {
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
				}
			} else if (fangjutype.equals("板甲")) {
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
				}
			}
		}

		qianghuatTextView.setText(s1);
		jinglingshiTextView.setText(s2);
		pinzhitTextView.setText(s3);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.simulation_choose_c, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SimulationChooseFragmentC");
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SimulationChooseFragmentC");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

}
