package com.leezm.vindictus.activity;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.adapter.ManualEnchantAdapter;
import com.leezm.vindictus.adapter.ManualEquipmentAdapter;
import com.leezm.vindictus.dbutils.dao.ManualInterface;
import com.leezm.vindictus.dbutils.impl.ManualImpl;
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ManualSearchActivity extends Activity implements OnClickListener {

	private Spinner spinner;
	private EditText editText,editText2;
	private ImageView imageView;
	private ListView listViewenchant, listViewequipment;
	private String string, typeNum,priceString,enchantname;;
	ManualEnchantAdapter mAdapter;
	ManualEquipmentAdapter eAdapter;
	private List<Map<String, String>> searchlist;
	ManualInterface dao = new ManualImpl();
	SharedPreferences preferences,preferences2;
	Editor editor,editor2;
	private PopupWindow popupWindow;
	private TextView tv1,tv2;
	
	private int s1 = 0;
	// 复制
	ClipboardManager clipboardManager;
	// GDTAD
	@ViewInject(R.id.manula_search_activity_adview)
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_search);
		ViewUtils.inject(this);
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
		init();
		// 复制
		clipboardManager = (ClipboardManager) this
				.getSystemService(Context.CLIPBOARD_SERVICE);
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
				MobclickAgent.onEvent(ManualSearchActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", ManualSearchActivity.this);
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

	private void init() {
		spinner = (Spinner) findViewById(R.id.manual_search_spinner);
		editText = (EditText) findViewById(R.id.manual_search_edittext);
		imageView = (ImageView) findViewById(R.id.manual_search_imageview);
		listViewenchant = (ListView) findViewById(R.id.manual_search_enchant_listview);
		listViewequipment = (ListView) findViewById(R.id.manual_search_equipment_listview);

		imageView.setOnClickListener(this);
		preferences = this.getSharedPreferences("ManualSearch",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		preferences2 = this.getSharedPreferences("enchantprice",Context.MODE_PRIVATE);
		editor2 = preferences2.edit();
		spinner.setSelection(preferences.getInt("type", 0));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				s1 = arg2;
				editor.putInt("type", s1);
				editor.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		listViewequipment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				clipboardManager.setText(searchlist.get(arg2).get("title")
						+ "\n" + searchlist.get(arg2).get("level") + "\n"
						+ searchlist.get(arg2).get("remarks") + "\n攻击："
						+ searchlist.get(arg2).get("att") + "\n力量："
						+ searchlist.get(arg2).get("str") + "\n智力："
						+ searchlist.get(arg2).get("mint") + "\n敏捷："
						+ searchlist.get(arg2).get("agi") + "\n意志："
						+ searchlist.get(arg2).get("wil") + "\n平衡："
						+ searchlist.get(arg2).get("bal") + "\n攻速："
						+ searchlist.get(arg2).get("attspd") + "\n暴击："
						+ searchlist.get(arg2).get("critical") + "\n防御："
						+ searchlist.get(arg2).get("def") + "\n暴抗："
						+ searchlist.get(arg2).get("critresist"));
				Toast.makeText(ManualSearchActivity.this, "已复制", 0).show();
			}
		});

		listViewenchant.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				clipboardManager.setText("附魔：" + searchlist.get(arg2).get("title")
						+ "\n等级：" + searchlist.get(arg2).get("level") + "\n类型："
						+ searchlist.get(arg2).get("style") + "\n部位："
						+ searchlist.get(arg2).get("custompart") + "\n属性："
						+ searchlist.get(arg2).get("customattribute") + "\n出处："
						+ searchlist.get(arg2).get("customprovenance"));
				Toast.makeText(ManualSearchActivity.this, "已复制", 0).show();
			}
		});
		listViewenchant.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View mView,
					int arg2, long arg3) {
				priceString = preferences2.getString(searchlist.get(arg2).get("title"), "");
				enchantname = searchlist.get(arg2).get("title");
				showPopWindow(mView);
				return true;
			}
		});
	}

	private void showPopWindow(View view){
		View pView = LayoutInflater.from(this).inflate(R.layout.pop_enchant, null);
		editText2 = (EditText) pView.findViewById(R.id.pop_enchant_ed);
		tv1 = (TextView) pView.findViewById(R.id.pop_enchant_cancle);
		tv2 = (TextView) pView.findViewById(R.id.pop_enchant_save);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		editText2.setText(priceString);
		popupWindow = new PopupWindow(pView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop));
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}
	
	private void search() {
		listViewenchant.setVisibility(View.GONE);
		listViewequipment.setVisibility(View.GONE);
		typeNum = spinner.getSelectedItem().toString();
		if (typeNum.equals("附魔")) {
			string = editText.getText().toString();
			searchlist = dao.searchEnchant(string);
			mAdapter = new ManualEnchantAdapter(this, searchlist);
			listViewenchant.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			listViewenchant.setVisibility(View.VISIBLE);
			if (searchlist.isEmpty()) {
				Toast.makeText(this, "无搜索结果，请重新输入", 1).show();
			}
		} else {
			// 这里写装备的搜索
			string = editText.getText().toString();
			searchlist = dao.searchEquipment(string);
			eAdapter = new ManualEquipmentAdapter(this, searchlist);
			listViewequipment.setAdapter(eAdapter);
			eAdapter.notifyDataSetChanged();
			listViewequipment.setVisibility(View.VISIBLE);
			if (searchlist.isEmpty()) {
				Toast.makeText(this, "无搜索结果，请重新输入", 1).show();
			}
		}
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {
		case R.id.manual_search_imageview:
			search();
			MobclickAgent.onEvent(ManualSearchActivity.this, "shujusousuo");
			break;
		case R.id.pop_enchant_cancle:
			popupWindow.dismiss();
			break;
		case R.id.pop_enchant_save:
			popupWindow.dismiss();
			editor2.putString(enchantname, editText2.getText().toString());
			editor2.commit();
			mAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}

}
