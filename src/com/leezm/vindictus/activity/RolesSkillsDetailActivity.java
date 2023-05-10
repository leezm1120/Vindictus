package com.leezm.vindictus.activity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.adapter.RolesSkillsAdapter;
import com.leezm.vindictus.bean.MapImgBean;
import com.leezm.vindictus.bean.RolesSkillsBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class RolesSkillsDetailActivity extends Activity{

	@ViewInject(R.id.roles_skills_listview)
	ListView listView;
	@ViewInject(R.id.roles_skills_biaoti)
	TextView mTextView;
	
	//popwindows
	private ImageView pImageView;
	private TextView pTextView1,pTextView2,pTextView3;
	BitmapUtils bUtils;
	private PopupWindow popupWindow;
	Display display;
	
	private List<RolesSkillsBean> list;
	private String roleString, dbString = "";
	private RolesSkillsAdapter mAdapter;
	BmobQuery<RolesSkillsBean> query = new BmobQuery<RolesSkillsBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roles_skills_detail);
		ViewUtils.inject(this);
		display = getWindowManager().getDefaultDisplay();
		init();
	}

	/*
	 * 查询优化
	 */
	long lastquerytime;
	Long timeNow;
	SharedPreferences preferences;
	Editor editor;
	
	private void init() {
		bUtils = new BitmapUtils(this);
		//bUtils.configDefaultShowOriginal(true);
		roleString = getIntent().getStringExtra("roles");
		change2DBName(roleString);
		mTextView.setText(dbString);
		//查询优化
		preferences = getSharedPreferences("RolesSkillsDetailActivity", MODE_PRIVATE);
		editor = preferences.edit();
		lastquerytime = preferences.getLong("querytime", 0);
		timeNow = new Date().getTime();
		query.addWhereEqualTo("roleName", dbString);
		query.setMaxCacheAge(TimeUnit.DAYS.toMillis(9999));
		if ((timeNow - lastquerytime) > 180 * 60 * 1000) {
			// 如果查询间隔大于3个小时，就联网查询
			editor.putLong("querytime", timeNow);
			editor.commit();
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);// 先从缓存取数据，无论结果如何都会再次从网络获取数据。也就是说会产生2次调用。
		} else {
			// 如果查询间隔小于3小时，就优先缓存
			boolean isInCache = query.hasCachedResult(this, RolesSkillsBean.class);
			if (isInCache) {
				query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);// 如果有缓存，先从缓存读取数据，如果没有，再从网络获取。
			} else {
				query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);// 如果没缓存，先从网络读取数据，如果没有，再从缓存中获取。
			}
		}
		query.findObjects(this, new FindListener<RolesSkillsBean>() {
			
			@Override
			public void onSuccess(List<RolesSkillsBean> mlist) {
				list = mlist;
				mAdapter = new RolesSkillsAdapter(RolesSkillsDetailActivity.this, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				if (arg0 != 9009) {
				CommonUtils.showShortToast("加载数据失败：" + arg1 + "错误码：" + arg0, RolesSkillsDetailActivity.this);
			}}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//这里写弹窗技能详细页
				showPopWindow(arg1, arg2);
			}
		});
	}

	private void showPopWindow(View mView,int postiton){
		View pView = LayoutInflater.from(this).inflate(R.layout.pop_roles_skills_details, null);
		pImageView = (ImageView) pView.findViewById(R.id.pop_roles_skills_icon);
		pTextView1 = (TextView) pView.findViewById(R.id.pop_roles_skills_title);
		pTextView2 = (TextView) pView.findViewById(R.id.pop_roles_skills_info);
		pTextView3 = (TextView) pView.findViewById(R.id.pop_roles_skills_awaken);
		
		bUtils.display(pImageView, list.get(postiton).getIconUrl());
		
		String aString = "",bString = "",cString = "";
		String skillName = list.get(postiton).getSkillName();
		String inFo = list.get(postiton).getInfo();
		String awaken = list.get(postiton).getAwaken();
		
		String[] astr = skillName.split("#");
		for (int i = 0; i < astr.length; i++) {
			aString = aString + astr[i] + "\n";
		}
		
		String[] bstr = inFo.split("#");
		for (int i = 0; i < bstr.length; i++) {
			bString = bString + bstr[i] + "\n";
		}
		
		String[] cstr = awaken.split("#");
		for (int i = 0; i < cstr.length; i++) {
			cString = cString + cstr[i] + "\n";
		}
		
		pTextView1.setText(aString);
		pTextView2.setText(bString);
		pTextView3.setText(cString);
		
		popupWindow = new PopupWindow(pView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth((int) (display.getWidth()*0.8));
		popupWindow.setHeight((int) (display.getHeight()*0.8));
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop));
		popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}
	
	
	private void change2DBName(String string) {
		if (string.equals("img1")) {
			dbString = "利斯塔";
		} else if (string.equals("img2")) {
			dbString = "菲欧娜";
		} else if (string.equals("img3")) {
			dbString = "伊菲";
		} else if (string.equals("img4")) {
			dbString = "卡鲁";
		} else if (string.equals("img5")) {
			dbString = "凯伊";
		} else if (string.equals("img6")) {
			dbString = "维拉";
		} else if (string.equals("img7")) {
			dbString = "霍克";
		} else if (string.equals("img8")) {
			dbString = "琳";
		} else if (string.equals("img9")) {
			dbString = "艾莉莎";
		} else if (string.equals("img10")) {
			dbString = "海基";
		} else if (string.equals("img11")) {
			dbString = "黛莉娅";
		}
	}

	

}
