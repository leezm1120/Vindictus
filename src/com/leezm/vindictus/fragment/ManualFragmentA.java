package com.leezm.vindictus.fragment;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.adapter.ManualEquipmentAdapter;
import com.leezm.vindictus.adapter.ManualEquipmentNineAdapter;
import com.leezm.vindictus.dbutils.dao.ManualInterface;
import com.leezm.vindictus.dbutils.impl.ManualImpl;
import com.leezm.vindictus.utils.ChangeToDBName;
import com.leezm.vindictus.utils.LogUtils;
import com.leezm.vindictus.view.TopIndicator.OnTopIndicatorListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class ManualFragmentA extends BaseFragment {
	@ViewInject(R.id.manual_equipment_listview)
	ListView listView;
	@ViewInject(R.id.manual_equipment_spinner_level)
	Spinner levelsSpinner;
	@ViewInject(R.id.manual_equipment_spinner_role)
	Spinner roleSpinner;
	@ViewInject(R.id.manual_equipment_spinner_type)
	Spinner typesSpinner;
	private static String TAG = "ManualFragmentA";
	private Activity mActivity;
	private String slevel, srole, stype;
	private int s1 = 0, s2 = 0, s3 = 0, g = 0;
	private List<Map<String, String>> list;
	ManualEquipmentAdapter mAdapter;
	ManualEquipmentNineAdapter mAdapter2;
	ManualInterface dao = new ManualImpl();
	SharedPreferences preferences;
	Editor editor;
	// 复制
	ClipboardManager clipboardManager;

	public static ManualFragmentA newInstance() {
		ManualFragmentA simulationFragment = new ManualFragmentA();
		return simulationFragment;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("ManualFragmentA");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("ManualFragmentA");
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.manual_fragment_zhuangbei,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		preferences = mActivity.getSharedPreferences("ManualFragmentA",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		// 复制
		clipboardManager = (ClipboardManager) mActivity
				.getSystemService(Context.CLIPBOARD_SERVICE);
		levelsSpinner.setSelection(preferences.getInt("level", 0));
		roleSpinner.setSelection(preferences.getInt("role", 0));
		typesSpinner.setSelection(preferences.getInt("type", 0));

		levelsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int select, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		roleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int select, long arg3) {
				// TODO Auto-generated method stub
				getitem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		typesSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int select, long arg3) {
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (slevel.equals("Lv90") | slevel.equals("Lv0")) {
					clipboardManager.setText(list.get(arg2).get("title") + "\n"
							+ list.get(arg2).get("level") + "\n"
							+ list.get(arg2).get("remarks"));
				} else {
					clipboardManager.setText(list.get(arg2).get("title") + "\n"
							+ list.get(arg2).get("level") + "\n攻击："
							+ list.get(arg2).get("att") + "\n力量："
							+ list.get(arg2).get("str") + "\n智力："
							+ list.get(arg2).get("mint") + "\n敏捷："
							+ list.get(arg2).get("agi") + "\n意志："
							+ list.get(arg2).get("wil") + "\n平衡："
							+ list.get(arg2).get("bal") + "\n攻速："
							+ list.get(arg2).get("attspd") + "\n暴击："
							+ list.get(arg2).get("critical") + "\n防御："
							+ list.get(arg2).get("def") + "\n暴抗："
							+ list.get(arg2).get("critresist"));
				}
				Toast.makeText(mActivity, "已复制", 0).show();
			}
		});
	}

	private void getitem() {
		if (g == 0) {
			g = 1;
		} else {
			g = 1;
			slevel = levelsSpinner.getSelectedItem().toString();
			srole = roleSpinner.getSelectedItem().toString();
			stype = typesSpinner.getSelectedItem().toString();
			s1 = levelsSpinner.getSelectedItemPosition();
			s2 = roleSpinner.getSelectedItemPosition();
			s3 = typesSpinner.getSelectedItemPosition();
			editor.putInt("level", s1);
			editor.putInt("role", s2);
			editor.putInt("type", s3);
			editor.commit();
			equipmentspinner(slevel, srole, stype);
		}
	}

	private void equipmentspinner(String level, String role, String type) {

		if (level.equals("Lv90")) {
			list = dao.selectequipmentspinner(
					new ChangeToDBName().changeallname(level), role,
					new ChangeToDBName().changeallname(type));
			mAdapter2 = new ManualEquipmentNineAdapter(mActivity, list);
			listView.setAdapter(mAdapter2);
			mAdapter2.notifyDataSetChanged();
		} else if (level.equals("Lv0")) {
			list = dao.selectequipmentspinner(
					new ChangeToDBName().changeallname(level), role,
					new ChangeToDBName().changeallname(type));
			mAdapter2 = new ManualEquipmentNineAdapter(mActivity, list);
			listView.setAdapter(mAdapter2);
			mAdapter2.notifyDataSetChanged();
		} else {
			list = dao.selectequipmentspinner(
					new ChangeToDBName().changelevelname(level), role,
					new ChangeToDBName().changeallname(type));
			mAdapter = new ManualEquipmentAdapter(mActivity, list);
			listView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			// LogUtils.e(list.toString(), "");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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

}
