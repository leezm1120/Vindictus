package com.leezm.vindictus.fragment;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.R.id;
import com.leezm.vindictus.activity.R.layout;
import com.leezm.vindictus.adapter.ManualEnchantAdapter;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ManualFragmentB extends BaseFragment implements OnClickListener {

	private static String TAG = "ManualFragmentB";
	ManualEnchantAdapter mAdapter;
	private List<Map<String, String>> list;
	private ListView listView;
	private Activity mActivity;
	ManualInterface dao = new ManualImpl();
	private String slevel, spart, stype,priceString,enchantname;
	private int s1 = 0, s2 = 0, s3 = 0;
	SharedPreferences preferences,preferences2;
	Editor editor,editor2;
	private EditText editText;
	private TextView tv1,tv2;
	private PopupWindow popupWindow;
	
	
	// 复制
	ClipboardManager clipboardManager;

	public static ManualFragmentB newInstance() {
		ManualFragmentB simulationFragment = new ManualFragmentB();
		return simulationFragment;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("ManualFragmentB");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("ManualFragmentB");
		//mAdapter.notifyDataSetChanged();
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
		View view = inflater.inflate(R.layout.manual_fragment_fumo, container,
				false);
		ViewUtils.inject(this, view);
		return view;

	}

	@ViewInject(R.id.manual_enchant_spinner_level)
	Spinner levelSpinner;
	@ViewInject(R.id.manual_enchant_spinner_part)
	Spinner partSpinner;
	@ViewInject(R.id.manual_enchant_spinner_type)
	Spinner typeSpinner;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView) getActivity().findViewById(
				R.id.manual_enchant_listview);
		preferences = mActivity.getSharedPreferences("ManualFragmentB",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		preferences2 = mActivity.getSharedPreferences("enchantprice",Context.MODE_PRIVATE);
		editor2 = preferences2.edit();
		levelSpinner.setSelection(preferences.getInt("level", 0));
		partSpinner.setSelection(preferences.getInt("part", 0));
		typeSpinner.setSelection(preferences.getInt("type", 0));
		// 复制
		clipboardManager = (ClipboardManager) mActivity
				.getSystemService(Context.CLIPBOARD_SERVICE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				clipboardManager.setText("附魔：" + list.get(arg2).get("title")
						+ "\n等级：" + list.get(arg2).get("level") + "\n类型："
						+ list.get(arg2).get("style") + "\n部位："
						+ list.get(arg2).get("custompart") + "\n属性："
						+ list.get(arg2).get("customattribute") + "\n出处："
						+ list.get(arg2).get("customprovenance"));
				Toast.makeText(mActivity, "已复制", 0).show();
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View mView,
					int arg2, long arg3) {
				priceString = preferences2.getString(list.get(arg2).get("title"), "");
				enchantname = list.get(arg2).get("title");
				//System.err.println(priceString+"---"+enchantname);
				showPopWindow(mView);
				return true;
			}
		});
		levelSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
				enchantspinner(slevel, spart, stype);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		partSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
				enchantspinner(slevel, spart, stype);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				getitem();
				enchantspinner(slevel, spart, stype);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void showPopWindow(View view){
		View pView = LayoutInflater.from(mActivity).inflate(R.layout.pop_enchant, null);
		editText = (EditText) pView.findViewById(R.id.pop_enchant_ed);
		tv1 = (TextView) pView.findViewById(R.id.pop_enchant_cancle);
		tv2 = (TextView) pView.findViewById(R.id.pop_enchant_save);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		editText.setText(priceString);
		popupWindow = new PopupWindow(pView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop));
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}
	
	private void getitem() {
		slevel = levelSpinner.getSelectedItem().toString();
		spart = partSpinner.getSelectedItem().toString();
		stype = typeSpinner.getSelectedItem().toString();
		s1 = levelSpinner.getSelectedItemPosition();
		s2 = partSpinner.getSelectedItemPosition();
		s3 = typeSpinner.getSelectedItemPosition();
		editor.putInt("level", s1);
		editor.putInt("part", s2);
		editor.putInt("type", s3);
		editor.commit();
	}

	private void enchantspinner(String level, String part, String type) {
		list = dao.selectLevelAndPartAndStyle(
				new ChangeToDBName().changeallname(level),
				new ChangeToDBName().changeallname(part),
				new ChangeToDBName().changeallname(type));
		mAdapter = new ManualEnchantAdapter(getActivity(), list);
		listView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
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

	@Override
	public void onClick(View mView) {
		switch (mView.getId()) {
		case R.id.pop_enchant_cancle:
			popupWindow.dismiss();
			break;
		case R.id.pop_enchant_save:
			popupWindow.dismiss();
			editor2.putString(enchantname, editText.getText().toString());
			editor2.commit();
			mAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		
	}

}
