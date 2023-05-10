package com.leezm.vindictus.fragment;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.adapter.ManualEnchantAdapter;
import com.leezm.vindictus.adapter.ManualEquipmentAdapter;
import com.leezm.vindictus.dbutils.dao.SimulationInterface;
import com.leezm.vindictus.dbutils.impl.SimulationImpl;
import com.leezm.vindictus.utils.ChangeToDBName;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SimulationChooseFragmentB extends BaseFragment {

	private static String TAG = "SimulationChooseFragmentB";
	private Activity mActivity;
	@ViewInject(R.id.simulation_enchant_spinner_level)
	Spinner spinner1;
	@ViewInject(R.id.simulation_enchant_spinner_type)
	Spinner spinner2;
	@ViewInject(R.id.simulation_enchant_listview)
	ListView listView;
	private String value, slevel, stype;
	SimulationInterface dao = new SimulationImpl();
	ManualEnchantAdapter mAdapter;
	SharedPreferences preferences;
	Editor editor;
	private List<Map<String, String>> list;
	private int aa = 0;

	public static SimulationChooseFragmentB newInstance() {
		SimulationChooseFragmentB sB = new SimulationChooseFragmentB();
		return sB;
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
		preferences = mActivity.getSharedPreferences("SimulationChooseFragmentB", Context.MODE_PRIVATE);
		editor = preferences.edit();
		spinner1.setSelection(preferences.getInt("spinner1", 0));
		spinner2.setSelection(preferences.getInt("spinner2", 0));
		Bundle bundle = getArguments();
		value = bundle.getString("key");
		if (value.equals("耳环")) {
			value = "首饰";
		} else if (value.equals("腰带")) {
			value = "首饰";
		} else if (value.equals("胸针")) {
			value = "首饰";
		} else if (value.equals("戒指")) {
			value = "首饰";
		} else if (value.equals("工艺品")) {
			value = "首饰";
		}
		// LogUtils.e("SimulationChooseFragmentB", value);
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
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				dao.addenchant(list.get(position).get("title"),
						list.get(position).get("level"), list.get(position)
								.get("att"), list.get(position).get("bal"),
						list.get(position).get("critical"), list.get(position)
								.get("attspd"), list.get(position).get("str"),
						list.get(position).get("mint"),
						list.get(position).get("agi"),
						list.get(position).get("wil"),
						list.get(position).get("part"),
						list.get(position).get("style"), list.get(position)
								.get("critresist"),
						list.get(position).get("def"),
						list.get(position).get("shengmingzhi"),
						list.get(position).get("sta"));
				Toast.makeText(mActivity,
						"已成功添加:" + list.get(position).get("title"), 1).show();
				// LogUtils.e(list.toString(), "");
			}
		});
	}

	private void getitem() {
		if (aa==0) {
			aa=1;
		}else{
			
		
		slevel = spinner1.getSelectedItem().toString();
		stype = spinner2.getSelectedItem().toString();
		gochoose(value, slevel, stype);
		//LogUtils.e(value, stype+slevel);
		}
	}

	private void gochoose(String custompart, String level, String remarks) {
		list = dao.selectenchant(
				new ChangeToDBName().changeallname(custompart),
				new ChangeToDBName().changeallname(level),
				new ChangeToDBName().changeallname(remarks));
		mAdapter = new ManualEnchantAdapter(mActivity, list);
		listView.setAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.simulation_choose_b, container,
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
		MobclickAgent.onPageEnd("SimulationChooseFragmentB");
		editor.putInt("spinner1", spinner1.getSelectedItemPosition());
		editor.putInt("spinner2", spinner2.getSelectedItemPosition());
		editor.commit();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SimulationChooseFragmentB");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

}
