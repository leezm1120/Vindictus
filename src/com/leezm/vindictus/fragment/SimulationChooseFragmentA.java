package com.leezm.vindictus.fragment;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.SimulationChooseActivity;
import com.leezm.vindictus.adapter.ManualEquipmentAdapter;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.ChangeToDBName;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SimulationChooseFragmentA extends BaseFragment implements
		OnClickListener {

	private static String TAG = "SimulationChooseFragmentA";
	private Activity mActivity;
	@ViewInject(R.id.simulation_equipment_listview)
	ListView listView;
	@ViewInject(R.id.simulation_equipment_spinner_level)
	Spinner spinner1;
	@ViewInject(R.id.simulation_equipment_spinner_role)
	Spinner spinner2;
	@ViewInject(R.id.simulation_equipment_spinner_type)
	Spinner spinner3;
	@ViewInject(R.id.simulation_equipment_nine_wuqi)
	LinearLayout wuqiview;
	@ViewInject(R.id.simulation_equipment_nine_shangxia)
	LinearLayout shangxiaview;
	@ViewInject(R.id.simulation_equipment_nine_tousoujiaodun)
	LinearLayout tousoujiaodunvView;

	// 武器组件
	@ViewInject(R.id.simulation_choose_weapons_main_spinner)
	Spinner wuqimainspinner;
	@ViewInject(R.id.simulation_edittext_weapons_att)
	EditText wuqimainatt;
	@ViewInject(R.id.simulation_edittext_weapons_spd)
	EditText wuqimainspd;
	@ViewInject(R.id.simulation_choose_weapons_ruili_spinner)
	Spinner ruilisSpinner;
	@ViewInject(R.id.simulation_edittext_weapons_ruili_bal)
	EditText ruilibal;
	@ViewInject(R.id.simulation_edittext_weapons_ruili_baoji)
	EditText ruilibaoji;
	@ViewInject(R.id.simulation_choose_weapons_wending_spinner)
	Spinner wendingSpinner;
	@ViewInject(R.id.simulation_edittext_weapons_wending_bal)
	EditText wendingbal;
	@ViewInject(R.id.simulation_edittext_weapons_wending_minjie)
	EditText wendingminjie;
	@ViewInject(R.id.simulation_edittext_weapons_wending_str)
	EditText wendingstr;
	@ViewInject(R.id.simulation_edittext_weapons_wending_yizhi)
	EditText wendingyizhi;
	@ViewInject(R.id.simulation_edittext_weapons_wending_zhili)
	EditText wendingzhili;
	@ViewInject(R.id.simulation_choose_weapons_qingying_spinner)
	Spinner qingyingsSpinner;
	@ViewInject(R.id.simulation_edittext_weapons_qingying_baoji)
	EditText qingyingbaoji;
	@ViewInject(R.id.simulation_edittext_weapons_qingying_spd)
	EditText qingyingspd;
	@ViewInject(R.id.simulation_choose_weapons_wanmei_spinner)
	Spinner wanmeisSpinner;
	@ViewInject(R.id.simulation_edittext_weapons_wanmei_att)
	EditText wanmeiatt;
	@ViewInject(R.id.simulation_edittext_weapons_wanmei_minjie)
	EditText wanmeiminjie;
	@ViewInject(R.id.simulation_edittext_weapons_wanmei_str)
	EditText wanmeistr;
	@ViewInject(R.id.simulation_edittext_weapons_wanmei_yizhi)
	EditText wanmeiyizhi;
	@ViewInject(R.id.simulation_edittext_weapons_wanmei_zhili)
	EditText wanmeizhili;
	@ViewInject(R.id.bt_simulation_choose_wuqi_cleanplan)
	RelativeLayout btwuqicleanplan;
	@ViewInject(R.id.bt_simulation_choose_wuqi_random)
	RelativeLayout btwuqirandom;
	@ViewInject(R.id.bt_simulation_choose_wuqi_save)
	RelativeLayout btwuqisave;

	// 衣服裤子组件
	@ViewInject(R.id.simulation_choose_shangxia_text)
	TextView shangxiatxt;
	@ViewInject(R.id.simulation_choose_sangxia_main_spinner)
	Spinner shangxiamainsSpinner;
	@ViewInject(R.id.simulation_edittext_sangxia_main_def)
	EditText sangxiamaindef;
	@ViewInject(R.id.simulation_edittext_sangxia_main_baokang)
	EditText sangxiamainbaokang;
	@ViewInject(R.id.simulation_choose_sangxia_jieshi_spinner)
	Spinner shangxiajieshiSpinner;
	@ViewInject(R.id.simulation_edittext_sangxia_jieshi_baokang)
	EditText sangxiajieshibaokang;
	@ViewInject(R.id.simulation_edittext_sangxia_jieshi_minjie)
	EditText sangxiajieshiminjie;
	@ViewInject(R.id.simulation_edittext_sangxia_jieshi_str)
	EditText sangxiajieshistr;
	@ViewInject(R.id.simulation_edittext_sangxia_jieshi_yizhi)
	EditText sangxiajieshiyizhi;
	@ViewInject(R.id.simulation_edittext_sangxia_jieshi_zhili)
	EditText sangxiajieshizhili;
	@ViewInject(R.id.simulation_choose_sangxia_guanghua_spinner)
	Spinner shangxiaguanghuaSpinner;
	@ViewInject(R.id.simulation_edittext_sangxia_guanghua_baokang)
	EditText sangxiaguanghuabaokang;
	@ViewInject(R.id.simulation_edittext_sangxia_guanghua_def)
	EditText sangxiaguanghuadef;
	@ViewInject(R.id.bt_simulation_choose_sangxia_cleanplan)
	RelativeLayout btsangxiaclean;
	@ViewInject(R.id.bt_simulation_choose_sangxia_random)
	RelativeLayout btsangxiarandom;
	@ViewInject(R.id.bt_simulation_choose_sangxia_save)
	RelativeLayout btsangxiasave;

	// 头手脚盾
	@ViewInject(R.id.simulation_choose_tousoujiaodun_text)
	TextView tousoujiaoduntxt;
	@ViewInject(R.id.simulation_choose_tousoujiaodun_main_spinner)
	Spinner tsjdSpinner;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_main_baokang)
	EditText tsjdmainbaokang;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_main_def)
	EditText tsjdmaindef;
	@ViewInject(R.id.simulation_choose_tousoujiaodun_jieshi_spinner)
	Spinner tsjdjieshiSpinner;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_jieshi_baokang)
	EditText tsjdjieshibaokang;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_jieshi_minjie)
	EditText tsjdjieshiminjie;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_jieshi_str)
	EditText tsjdjieshistr;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_jieshi_yizhi)
	EditText tsjdjieshiyizhi;
	@ViewInject(R.id.simulation_edittext_tousoujiaodun_jieshi_zhili)
	EditText tsjdjieshizhili;
	@ViewInject(R.id.bt_simulation_choose_tsjd_cleanplan)
	RelativeLayout bttsjdclean;
	@ViewInject(R.id.bt_simulation_choose_tsjd_random)
	RelativeLayout bttsjdrandom;
	@ViewInject(R.id.bt_simulation_choose_tsjd_save)
	RelativeLayout bttsjdsave;

	private String value, slevel, srole, stype, rString;
	private List<Map<String, String>> list;
	private int g = 0, iatt = 0, ispd = 0, ibal = 0, ibao = 0, istr = 0,
			iminjie = 0, iyizhi = 0, izhili = 0, idef = 0, ikang = 0, aa = 0;
	ManualEquipmentAdapter mAdapter;
	SimulationInterface dao = new SimulationImpl();
	SharedPreferences preferences;
	Editor editor;

	public static SimulationChooseFragmentA newInstance() {
		SimulationChooseFragmentA sA = new SimulationChooseFragmentA();
		return sA;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private void inittousoujiaodun() {
		bttsjdclean.setOnClickListener(this);
		bttsjdrandom.setOnClickListener(this);
		bttsjdsave.setOnClickListener(this);

		tsjdjieshiSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						tsjdjieshibaokang.setText("");
						tsjdjieshiminjie.setText("");
						tsjdjieshistr.setText("");
						tsjdjieshiyizhi.setText("");
						tsjdjieshizhili.setText("");
						if (arg2 == 0) {
							tsjdjieshibaokang.setHint("10-13");
							tsjdjieshiminjie.setHint("72-87");
							tsjdjieshistr.setHint("144-180");
							tsjdjieshiyizhi.setHint("64-80");
							tsjdjieshizhili.setHint("194-243");
						} else {
							tsjdjieshibaokang.setHint("9-11");
							tsjdjieshiminjie.setHint("63-71");
							tsjdjieshistr.setHint("126-142");
							tsjdjieshiyizhi.setHint("56-63");
							tsjdjieshizhili.setHint("170-192");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		tsjdSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				tsjdmainbaokang.setText("");
				tsjdmaindef.setText("");
				// TODO Auto-generated method stub
				if (arg2 == 0) {// 橙色
					if (value.equals("副")) {// 盾牌
						tsjdmainbaokang.setHint("1");
						tsjdmaindef.setHint("1635-1922/2013-2368");
					} else {// 头手脚
						tsjdmainbaokang.setHint("3");
						tsjdmaindef.setHint("1255-1476");
					}
				} else {// 紫色
					if (value.equals("副")) { // 盾牌
						tsjdmainbaokang.setHint("1");
						tsjdmaindef.setHint("1443-1616/1776-1989");
					} else {// 头手脚
						tsjdmainbaokang.setHint("2-3");
						tsjdmaindef.setHint("1107-1240");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initwuqi() {

		btwuqicleanplan.setOnClickListener(this);
		btwuqirandom.setOnClickListener(this);
		btwuqisave.setOnClickListener(this);

		wanmeisSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				wanmeiatt.setText("");
				wanmeiminjie.setText("");
				wanmeistr.setText("");
				wanmeiyizhi.setText("");
				wanmeizhili.setText("");
				if (arg2 == 0) {
					wanmeiatt.setHint("4390-4720");
					wanmeiminjie.setHint("29-35");
					wanmeistr.setHint("53-66");
					wanmeiyizhi.setHint("37-48");
					wanmeizhili.setHint("71-89");
				} else {
					wanmeiatt.setHint("4059-4342");
					wanmeiminjie.setHint("25-28");
					wanmeistr.setHint("46-52");
					wanmeiyizhi.setHint("34-37");
					wanmeizhili.setHint("62-70");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		qingyingsSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						qingyingbaoji.setText("");
						qingyingspd.setText("");
						if (arg2 == 0) {
							qingyingbaoji.setHint("19-21");
							qingyingspd.setHint("4-5");
						} else {
							qingyingbaoji.setHint("17-19");
							qingyingspd.setHint("2-4");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		wendingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				wendingbal.setText("");
				wendingminjie.setText("");
				wendingstr.setText("");
				wendingyizhi.setText("");
				wendingzhili.setText("");
				if (arg2 == 0) {
					wendingbal.setHint("41-45");
					wendingminjie.setHint("19-24");
					wendingstr.setHint("35-43");
					wendingyizhi.setHint("26-32");
					wendingzhili.setHint("47-59");
				} else {
					wendingbal.setHint("36-40");
					wendingminjie.setHint("17-18");
					wendingstr.setHint("31-34");
					wendingyizhi.setHint("22-24");
					wendingzhili.setHint("41-47");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ruilisSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ruilibal.setText("");
				ruilibaoji.setText("");
				if (arg2 == 0) {
					ruilibal.setHint("27-30");
					ruilibaoji.setHint("28-31");
				} else {
					ruilibal.setHint("24-27");
					ruilibaoji.setHint("25-28");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		wuqimainspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				wuqimainatt.setText("");
				wuqimainspd.setText("");
				if (arg2 == 0) {
					wuqimainatt.setHint("6584-7080");
					wuqimainspd.setHint("3-4");
				} else {
					wuqimainatt.setHint("6089-6514");
					wuqimainspd.setHint("2");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initshangxia() {
		btsangxiaclean.setOnClickListener(this);
		btsangxiarandom.setOnClickListener(this);
		btsangxiasave.setOnClickListener(this);
		shangxiaguanghuaSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						sangxiaguanghuabaokang.setText("");
						sangxiaguanghuadef.setText("");
						if (arg2 == 0) {
							sangxiaguanghuabaokang.setHint("3-4");
							sangxiaguanghuadef.setHint("629-740");
						} else {
							sangxiaguanghuabaokang.setHint("3");
							sangxiaguanghuadef.setHint("555-622");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		shangxiajieshiSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						sangxiajieshibaokang.setText("");
						sangxiajieshiminjie.setText("");
						sangxiajieshistr.setText("");
						sangxiajieshiyizhi.setText("");
						sangxiajieshizhili.setText("");
						if (arg2 == 0) {
							sangxiajieshibaokang.setHint("10-13");
							sangxiajieshiminjie.setHint("72-87");
							sangxiajieshistr.setHint("144-180");
							sangxiajieshiyizhi.setHint("64-80");
							sangxiajieshizhili.setHint("194-243");
						} else {
							sangxiajieshibaokang.setHint("9-11");
							sangxiajieshiminjie.setHint("63-71");
							sangxiajieshistr.setHint("126-142");
							sangxiajieshiyizhi.setHint("56-63");
							sangxiajieshizhili.setHint("170-192");
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		shangxiamainsSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						sangxiamainbaokang.setText("");
						sangxiamaindef.setText("");
						if (arg2 == 0) {
							if (value.equals("胸部")) {
								sangxiamainbaokang.setHint("3-4");
								sangxiamaindef.setHint("793-932");
							} else {// 腿
								sangxiamainbaokang.setHint("5-7");
								sangxiamaindef.setHint("726-855");
							}
						} else {
							if (value.equals("胸部")) {
								sangxiamainbaokang.setHint("3");
								sangxiamaindef.setHint("699-783");
							} else {// 腿
								sangxiamainbaokang.setHint("5-6");
								sangxiamaindef.setHint("641-717");
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void initmain() {
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
				editor.putString("spinner3", spinner3.getSelectedItem()
						.toString());
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// LogUtils.e("点击了", String.valueOf(position));
				dao.addequipment(list.get(position).get("title"),
						list.get(position).get("level"), list.get(position)
								.get("att"), list.get(position).get("bal"),
						list.get(position).get("critical"), list.get(position)
								.get("attspd"), list.get(position).get("str"),
						list.get(position).get("mint"),
						list.get(position).get("agi"),
						list.get(position).get("wil"),
						list.get(position).get("part"),
						list.get(position).get("type"),
						list.get(position).get("role"),
						list.get(position).get("critresist"), list
								.get(position).get("def"), list.get(position)
								.get("shengmingzhi"),
						list.get(position).get("sta"),
						list.get(position).get("remarks"));
				Toast.makeText(mActivity,
						"已成功添加:" + list.get(position).get("title"), 1).show();
			}
		});
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getArguments();
		value = bundle.getString("key");
		preferences = mActivity.getSharedPreferences(
				"SimulationChooseFragmentA", Context.MODE_PRIVATE);
		editor = preferences.edit();
		spinner1.setSelection(preferences.getInt("spinner1", 0));
		spinner2.setSelection(preferences.getInt("spinner2", 0));
		// LogUtils.e("SimulationChooseFragmentA", value);
		initmain();
		initwuqi();
		initshangxia();
		inittousoujiaodun();
	}

	private void getitem() {
		if (g == 0) {
			g = 1;
		} else {
			g = 1;

			slevel = spinner1.getSelectedItem().toString();
			srole = spinner2.getSelectedItem().toString();
			stype = spinner3.getSelectedItem().toString();
			if (slevel.equals("Lv90")) {
				yincangall();
				if (value.equals("武器")) {

					wuqiview.setVisibility(View.VISIBLE);
				} else if (value.equals("胸部") || value.equals("腿")) {
					if (value.equals("胸部")) {
						shangxiatxt.setText("封印的力量：胸部");
					} else if (value.equals("腿")) {
						shangxiatxt.setText("封印的力量：腿部");
					}
					shangxiaview.setVisibility(View.VISIBLE);
				} else if (value.equals("头") || value.equals("手")
						|| value.equals("脚") || value.equals("副")) {
					if (value.equals("头")) {
						tousoujiaoduntxt.setText("封印的力量：头部");
						tousoujiaodunvView.setVisibility(View.VISIBLE);
					} else if (value.equals("手")) {
						tousoujiaoduntxt.setText("封印的力量：手部");
						tousoujiaodunvView.setVisibility(View.VISIBLE);
					} else if (value.equals("脚")) {
						tousoujiaoduntxt.setText("封印的力量：脚部");
						tousoujiaodunvView.setVisibility(View.VISIBLE);
					} else if (value.equals("副")) {
						tousoujiaoduntxt.setText("封印的力量：小盾/巨盾");
						if (spinner2.getSelectedItem().toString().equals("菲欧娜")) {
							tousoujiaodunvView.setVisibility(View.VISIBLE);
						}
					}

				}
			} else {
				yincangall();
				gochoose(value, slevel, srole, stype);
			}
		}
	}

	private void yincangall() {
		wuqiview.setVisibility(View.GONE);
		shangxiaview.setVisibility(View.GONE);
		tousoujiaodunvView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
	}

	private void gochoose(String simulation, String level, String role,
			String remarks) {
		list = dao.selectequipment(
				new ChangeToDBName().changeallname(simulation),
				new ChangeToDBName().changelevelname(level), role,
				new ChangeToDBName().changeallname(remarks));

		mAdapter = new ManualEquipmentAdapter(mActivity, list);
		listView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		listView.setVisibility(View.VISIBLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.simulation_choose_a, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SimulationChooseFragmentA");
		editor.putInt("spinner1", spinner1.getSelectedItemPosition());
		editor.putInt("spinner2", spinner2.getSelectedItemPosition());
		editor.commit();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SimulationChooseFragmentA");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	private int changetoint(EditText editText) {
		aa = Integer.valueOf(editText.getText().toString());
		return aa;
	}

	private void cleanintall() {
		iatt = ispd = ibal = ibao = istr = iminjie = iyizhi = izhili = idef = ikang = 0;
	}

	private void tsjdgetall() {
		cleanintall();
		idef = changetoint(tsjdmaindef);
		ikang = changetoint(tsjdmainbaokang) + changetoint(tsjdjieshibaokang);
		iminjie = changetoint(tsjdjieshiminjie);
		iyizhi = changetoint(tsjdjieshiyizhi);
		istr = changetoint(tsjdjieshistr);
		izhili = changetoint(tsjdjieshizhili);
		// System.err.println(istr);
		// System.err.println(iminjie);
		// System.err.println(iyizhi);
		// System.err.println(izhili);
		// System.err.println(idef);
		// System.err.println(ikang);
	}

	private void shangxiagetall() {
		cleanintall();
		idef = changetoint(sangxiamaindef) + changetoint(sangxiaguanghuadef);
		ikang = changetoint(sangxiamainbaokang)
				+ changetoint(sangxiajieshibaokang)
				+ changetoint(sangxiaguanghuabaokang);
		iminjie = changetoint(sangxiajieshiminjie);
		iyizhi = changetoint(sangxiajieshiyizhi);
		istr = changetoint(sangxiajieshistr);
		izhili = changetoint(sangxiajieshizhili);

		// System.err.println(istr);
		// System.err.println(iminjie);
		// System.err.println(iyizhi);
		// System.err.println(izhili);
		// System.err.println(idef);
		// System.err.println(ikang);
	}

	private void wuqigetall() {
		cleanintall();
		iatt = changetoint(wuqimainatt) + changetoint(wanmeiatt);
		ispd = changetoint(wuqimainspd) + changetoint(qingyingspd);
		ibal = changetoint(ruilibal) + changetoint(wendingbal);
		ibao = changetoint(ruilibaoji) + changetoint(qingyingbaoji);
		istr = changetoint(wendingstr) + changetoint(wanmeistr);
		iminjie = changetoint(wendingminjie) + changetoint(wanmeiminjie);
		iyizhi = changetoint(wendingyizhi) + changetoint(wanmeiyizhi);
		izhili = changetoint(wendingzhili) + changetoint(wanmeizhili);
		// System.err.println(iatt);
		// System.err.println(ispd);
		// System.err.println(ibal);
		// System.err.println(ibao);
		// System.err.println(istr);
		// System.err.println(iminjie);
		// System.err.println(iyizhi);
		// System.err.println(izhili);
	}

	private void tsjdrandom() {
		if (tsjdSpinner.getSelectedItem().toString().equals("橙色")) {// 橙色
			if (value.equals("副")) {// 橙色盾牌
				tsjdmainbaokang.setText(new ChangeToDBName().getrandom(1, 1));
				tsjdmaindef.setText(new ChangeToDBName().getrandom(1922, 1635));
			} else {// 橙色头手脚
				tsjdmainbaokang.setText(new ChangeToDBName().getrandom(3, 3));
				tsjdmaindef.setText(new ChangeToDBName().getrandom(1476, 1255));
			}
		} else {// 紫色
			if (value.equals("副")) {// 紫色盾牌
				tsjdmainbaokang.setText(new ChangeToDBName().getrandom(1, 1));
				tsjdmaindef.setText(new ChangeToDBName().getrandom(1616, 1443));
			} else {// 紫色头手脚
				tsjdmainbaokang.setText(new ChangeToDBName().getrandom(3, 2));
				tsjdmaindef.setText(new ChangeToDBName().getrandom(1240, 1107));
			}
		}

		if (tsjdjieshiSpinner.getSelectedItem().toString().equals("橙色")) {
			tsjdjieshibaokang.setText(new ChangeToDBName().getrandom(13, 10));
			tsjdjieshiminjie.setText(new ChangeToDBName().getrandom(87, 72));
			tsjdjieshistr.setText(new ChangeToDBName().getrandom(180, 144));
			tsjdjieshiyizhi.setText(new ChangeToDBName().getrandom(80, 64));
			tsjdjieshizhili.setText(new ChangeToDBName().getrandom(243, 194));
		} else {
			tsjdjieshibaokang.setText(new ChangeToDBName().getrandom(11, 9));
			tsjdjieshiminjie.setText(new ChangeToDBName().getrandom(71, 63));
			tsjdjieshistr.setText(new ChangeToDBName().getrandom(142, 126));
			tsjdjieshiyizhi.setText(new ChangeToDBName().getrandom(63, 56));
			tsjdjieshizhili.setText(new ChangeToDBName().getrandom(192, 170));
		}
	}

	private void tsjdcleanall() {
		tsjdmainbaokang.setText("");
		tsjdmaindef.setText("");
		tsjdjieshibaokang.setText("");
		tsjdjieshiminjie.setText("");
		tsjdjieshistr.setText("");
		tsjdjieshiyizhi.setText("");
		tsjdjieshizhili.setText("");
	}

	private void wuqicleanall() {
		wuqimainatt.setText("");
		wuqimainspd.setText("");
		ruilibal.setText("");
		ruilibaoji.setText("");
		wendingbal.setText("");
		wendingminjie.setText("");
		wendingstr.setText("");
		wendingyizhi.setText("");
		wendingzhili.setText("");
		qingyingbaoji.setText("");
		qingyingspd.setText("");
		wanmeiatt.setText("");
		wanmeiminjie.setText("");
		wanmeistr.setText("");
		wanmeiyizhi.setText("");
		wanmeizhili.setText("");
	}

	private void wuqirandom() {
		if (ruilisSpinner.getSelectedItem().toString().equals("橙色")) {
			ruilibal.setText(new ChangeToDBName().getrandom(30, 27));
			ruilibaoji.setText(new ChangeToDBName().getrandom(31, 28));
		} else {
			ruilibal.setText(new ChangeToDBName().getrandom(27, 24));
			ruilibaoji.setText(new ChangeToDBName().getrandom(28, 25));
		}

		if (wendingSpinner.getSelectedItem().toString().equals("橙色")) {
			wendingbal.setText(new ChangeToDBName().getrandom(45, 41));
			wendingminjie.setText(new ChangeToDBName().getrandom(24, 19));
			wendingstr.setText(new ChangeToDBName().getrandom(43, 35));
			wendingyizhi.setText(new ChangeToDBName().getrandom(32, 26));
			wendingzhili.setText(new ChangeToDBName().getrandom(59, 47));
		} else {
			wendingbal.setText(new ChangeToDBName().getrandom(40, 36));
			wendingminjie.setText(new ChangeToDBName().getrandom(18, 17));
			wendingstr.setText(new ChangeToDBName().getrandom(34, 31));
			wendingyizhi.setText(new ChangeToDBName().getrandom(24, 22));
			wendingzhili.setText(new ChangeToDBName().getrandom(47, 41));
		}
		if (qingyingsSpinner.getSelectedItem().toString().equals("橙色")) {
			qingyingbaoji.setText(new ChangeToDBName().getrandom(21, 19));
			qingyingspd.setText(new ChangeToDBName().getrandom(5, 4));
		} else {
			qingyingbaoji.setText(new ChangeToDBName().getrandom(19, 17));
			qingyingspd.setText(new ChangeToDBName().getrandom(4, 2));
		}

		if (wanmeisSpinner.getSelectedItem().toString().equals("橙色")) {
			wanmeiatt.setText(new ChangeToDBName().getrandom(4720, 4390));
			wanmeiminjie.setText(new ChangeToDBName().getrandom(34, 29));
			wanmeistr.setText(new ChangeToDBName().getrandom(66, 53));
			wanmeiyizhi.setText(new ChangeToDBName().getrandom(48, 37));
			wanmeizhili.setText(new ChangeToDBName().getrandom(89, 71));
		} else {
			wanmeiatt.setText(new ChangeToDBName().getrandom(4342, 4059));
			wanmeiminjie.setText(new ChangeToDBName().getrandom(28, 25));
			wanmeistr.setText(new ChangeToDBName().getrandom(52, 46));
			wanmeiyizhi.setText(new ChangeToDBName().getrandom(37, 34));
			wanmeizhili.setText(new ChangeToDBName().getrandom(70, 62));
		}

		if (wuqimainspinner.getSelectedItem().toString().equals("橙色")) {
			wuqimainatt.setText(new ChangeToDBName().getrandom(7080, 6584));
			wuqimainspd.setText(new ChangeToDBName().getrandom(4, 3));

		} else {
			wuqimainatt.setText(new ChangeToDBName().getrandom(6514, 6089));
			wuqimainspd.setText(new ChangeToDBName().getrandom(2, 2));

		}
	}

	private void sangxiacleanall() {
		sangxiamainbaokang.setText("");
		sangxiamaindef.setText("");
		sangxiajieshibaokang.setText("");
		sangxiajieshiminjie.setText("");
		sangxiajieshistr.setText("");
		sangxiajieshiyizhi.setText("");
		sangxiajieshizhili.setText("");
		sangxiaguanghuabaokang.setText("");
		sangxiaguanghuadef.setText("");
	}

	private void sangxiarandom() {
		if (shangxiamainsSpinner.getSelectedItem().toString().equals("橙色")) {
			if (value.equals("胸部")) {
				sangxiamainbaokang
						.setText(new ChangeToDBName().getrandom(4, 3));
				sangxiamaindef
						.setText(new ChangeToDBName().getrandom(932, 793));
			} else {// 腿部
				sangxiamainbaokang
						.setText(new ChangeToDBName().getrandom(7, 5));
				sangxiamaindef
						.setText(new ChangeToDBName().getrandom(855, 726));
			}

		} else {// 紫色
			if (value.equals("胸部")) {
				sangxiamainbaokang
						.setText(new ChangeToDBName().getrandom(3, 3));
				sangxiamaindef
						.setText(new ChangeToDBName().getrandom(783, 699));
			} else {// 腿部
				sangxiamainbaokang
						.setText(new ChangeToDBName().getrandom(6, 5));
				sangxiamaindef
						.setText(new ChangeToDBName().getrandom(717, 641));
			}
		}

		if (shangxiajieshiSpinner.getSelectedItem().toString().equals("橙色")) {
			sangxiajieshibaokang
					.setText(new ChangeToDBName().getrandom(13, 10));
			sangxiajieshiminjie.setText(new ChangeToDBName().getrandom(87, 72));
			sangxiajieshistr.setText(new ChangeToDBName().getrandom(180, 144));
			sangxiajieshiyizhi.setText(new ChangeToDBName().getrandom(80, 64));
			sangxiajieshizhili
					.setText(new ChangeToDBName().getrandom(243, 194));
		} else {
			sangxiajieshibaokang.setText(new ChangeToDBName().getrandom(11, 9));
			sangxiajieshiminjie.setText(new ChangeToDBName().getrandom(71, 63));
			sangxiajieshistr.setText(new ChangeToDBName().getrandom(142, 126));
			sangxiajieshiyizhi.setText(new ChangeToDBName().getrandom(63, 56));
			sangxiajieshizhili
					.setText(new ChangeToDBName().getrandom(192, 170));
		}
		if (shangxiaguanghuaSpinner.getSelectedItem().toString().equals("橙色")) {
			sangxiaguanghuabaokang
					.setText(new ChangeToDBName().getrandom(4, 3));
			sangxiaguanghuadef
					.setText(new ChangeToDBName().getrandom(740, 629));
		} else {
			sangxiaguanghuabaokang
					.setText(new ChangeToDBName().getrandom(3, 3));
			sangxiaguanghuadef
					.setText(new ChangeToDBName().getrandom(622, 555));
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.bt_simulation_choose_wuqi_cleanplan: // 武器清空
			AlertDialogUtils.showDialog(mActivity, "确定要清空全部数值吗？",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							wuqicleanall();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});
			break;
		case R.id.bt_simulation_choose_wuqi_random: // 武器随机
			AlertDialogUtils.showDialog(mActivity, "随机生成会覆盖已输入的参数",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							wuqirandom();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});

			break;
		case R.id.bt_simulation_choose_wuqi_save: // 武器保存
			checkwuqieditextnull();
			wuqigetall();
			AlertDialogUtils.showDialog(mActivity, "是否保存当前武器：\n攻击：" + iatt
					+ "\n攻速：" + ispd + "\n平衡：" + ibal + "\n暴击：" + ibao
					+ "\n力量：" + istr + "\n智力：" + izhili + "\n意志：" + iyizhi
					+ "\n敏捷：" + iminjie + "", new OnClickYesListener() {

				@Override
				public void onClickYes() {
					// TODO Auto-generated method stub
					// 这里写保存武器数据到数据库
					dao.addequipment("Lv90武器", "Lv90", String.valueOf(iatt),
							String.valueOf(ibal), String.valueOf(ibao), String
									.valueOf(ispd), String.valueOf(istr),
							String.valueOf(izhili), String.valueOf(iminjie),
							String.valueOf(iyizhi), "武器", spinner3
									.getSelectedItem().toString(), spinner2
									.getSelectedItem().toString(), String
									.valueOf(0), String.valueOf(0), String
									.valueOf(0), String.valueOf(0), "");
					Toast.makeText(mActivity, "已保存", 0).show();
				}
			}, new OnClickNoListener() {

				@Override
				public void onClickNo() {
					// TODO Auto-generated method stub

				}
			});
			break;

		case R.id.bt_simulation_choose_sangxia_cleanplan: // 上下清空
			AlertDialogUtils.showDialog(mActivity, "确定要清空全部数值吗？",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							sangxiacleanall();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});
			break;
		case R.id.bt_simulation_choose_sangxia_random: // 上下随机
			AlertDialogUtils.showDialog(mActivity, "随机生成会覆盖已输入的参数",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							sangxiarandom();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});
			break;
		case R.id.bt_simulation_choose_sangxia_save: // 上下保存
			checkshangxiaeditextnull();
			shangxiagetall();
			if (value.equals("胸部")) {
				rString = "胸部";
			} else {
				rString = "腿部";
			}
			AlertDialogUtils.showDialog(mActivity, "是否保存当前" + rString
					+ ":\n防御：" + idef + "\n暴击抵抗：" + ikang + "\n力量：" + istr
					+ "\n智力：" + izhili + "\n意志：" + iyizhi + "\n敏捷：" + iminjie
					+ "", new OnClickYesListener() {

				@Override
				public void onClickYes() {
					// TODO Auto-generated method stub
					// 这里写保存上下数据到数据库
					dao.addequipment("Lv90" + rString + "", "Lv90", "0", "0",
							"0", "0", String.valueOf(istr), String
									.valueOf(izhili), String.valueOf(iminjie),
							String.valueOf(iyizhi), rString, spinner3
									.getSelectedItem().toString(), spinner2
									.getSelectedItem().toString(), String
									.valueOf(ikang), String.valueOf(idef), "0",
							"0", "");
					Toast.makeText(mActivity, "已保存", 0).show();
				}
			}, new OnClickNoListener() {

				@Override
				public void onClickNo() {
					// TODO Auto-generated method stub

				}
			});
			break;

		case R.id.bt_simulation_choose_tsjd_cleanplan:// 头手脚盾清空
			AlertDialogUtils.showDialog(mActivity, "确定要清空全部数值吗？",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							tsjdcleanall();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});

			break;
		case R.id.bt_simulation_choose_tsjd_random:// 头手脚盾随机
			AlertDialogUtils.showDialog(mActivity, "随机生成会覆盖已输入的参数",
					new OnClickYesListener() {

						@Override
						public void onClickYes() {
							// TODO Auto-generated method stub
							tsjdrandom();
						}
					}, new OnClickNoListener() {

						@Override
						public void onClickNo() {
							// TODO Auto-generated method stub

						}
					});
			break;
		case R.id.bt_simulation_choose_tsjd_save:// 头手脚盾保存
			checktsjdedittextnull();
			tsjdgetall();
			if (value.equals("头")) {
				rString = "头部";
			} else if (value.equals("手")) {
				rString = "手部";
			} else if (value.equals("脚")) {
				rString = "脚部";
			} else if (value.equals("副")) {
				rString = "盾牌";
			}
			AlertDialogUtils.showDialog(mActivity, "是否保存当前" + rString
					+ ":\n防御：" + idef + "\n暴击抵抗：" + ikang + "\n力量：" + istr
					+ "\n智力：" + izhili + "\n意志：" + iyizhi + "\n敏捷：" + iminjie
					+ "", new OnClickYesListener() {

				@Override
				public void onClickYes() {
					// TODO Auto-generated method stub
					// 这里写保存头手脚盾数据到数据库
					dao.addequipment("Lv90" + rString + "", "Lv90", "0", "0",
							"0", "0", String.valueOf(istr), String
									.valueOf(izhili), String.valueOf(iminjie),
							String.valueOf(iyizhi), rString, spinner3
									.getSelectedItem().toString(), spinner2
									.getSelectedItem().toString(), String
									.valueOf(ikang), String.valueOf(idef), "0",
							"0", "");
					Toast.makeText(mActivity, "已保存", 0).show();
				}
			}, new OnClickNoListener() {

				@Override
				public void onClickNo() {
					// TODO Auto-generated method stub

				}
			});
			break;

		default:
			break;
		}
	}

	private void changeednulltozero(EditText editText) {
		if (editText.getText().length() == 0) {
			editText.setText("0");
		}
	}

	private void checkshangxiaeditextnull() {
		changeednulltozero(sangxiamainbaokang);
		changeednulltozero(sangxiamaindef);
		changeednulltozero(sangxiajieshibaokang);
		changeednulltozero(sangxiajieshiminjie);
		changeednulltozero(sangxiajieshistr);
		changeednulltozero(sangxiajieshiyizhi);
		changeednulltozero(sangxiajieshizhili);
		changeednulltozero(sangxiaguanghuabaokang);
		changeednulltozero(sangxiaguanghuadef);
	}

	private void checktsjdedittextnull() {
		changeednulltozero(tsjdmainbaokang);
		changeednulltozero(tsjdmaindef);
		changeednulltozero(tsjdjieshibaokang);
		changeednulltozero(tsjdjieshiminjie);
		changeednulltozero(tsjdjieshistr);
		changeednulltozero(tsjdjieshiyizhi);
		changeednulltozero(tsjdjieshizhili);
	}

	private void checkwuqieditextnull() {

		if (wuqimainatt.getText().length() == 0) {
			wuqimainatt.setText("0");
		}
		if (wuqimainspd.getText().length() == 0) {
			wuqimainspd.setText("0");
		}
		if (ruilibal.getText().length() == 0) {
			ruilibal.setText("0");
		}
		if (ruilibaoji.getText().length() == 0) {
			ruilibaoji.setText("0");
		}
		if (wendingbal.getText().length() == 0) {
			wendingbal.setText("0");
		}
		if (wendingminjie.getText().length() == 0) {
			wendingminjie.setText("0");
		}
		if (wendingstr.getText().length() == 0) {
			wendingstr.setText("0");
		}
		if (wendingyizhi.getText().length() == 0) {
			wendingyizhi.setText("0");
		}
		if (wendingzhili.getText().length() == 0) {
			wendingzhili.setText("0");
		}
		if (qingyingbaoji.getText().length() == 0) {
			qingyingbaoji.setText("0");
		}
		if (qingyingspd.getText().length() == 0) {
			qingyingspd.setText("0");
		}
		if (wanmeiatt.getText().length() == 0) {
			wanmeiatt.setText("0");
		}
		if (wanmeiminjie.getText().length() == 0) {
			wanmeiminjie.setText("0");
		}
		if (wanmeistr.getText().length() == 0) {
			wanmeistr.setText("0");
		}
		if (wanmeiyizhi.getText().length() == 0) {
			wanmeiyizhi.setText("0");
		}
		if (wanmeizhili.getText().length() == 0) {
			wanmeizhili.setText("0");
		}
	}
}
