package com.leezm.vindictus.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.adapter.AreaTipsAdapter;
import com.leezm.vindictus.adapter.AreaTipsThreeAdapter;
import com.leezm.vindictus.adapter.AreaTipsThreeNewAdapter;
import com.leezm.vindictus.adapter.AreaTipsTwoAdapter;
import com.leezm.vindictus.bean.AreaTipsBean;
import com.leezm.vindictus.bean.OfficialNoticeBean;
import com.leezm.vindictus.dbutils.dao.FoundInterface;
import com.leezm.vindictus.dbutils.impl.FoundImpl;
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
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AreaTipsActivity extends Activity {

	@ViewInject(R.id.area_tips_listview1)
	ListView lView1;
	@ViewInject(R.id.area_tips_listview2)
	ListView lView2;
	@ViewInject(R.id.area_tips_listview3)
	ListView lView3;
	public static int mPosition = 0, mPosition2 = 0;
	AreaTipsAdapter mAdapter;
	AreaTipsTwoAdapter mAdapter2;
	AreaTipsThreeAdapter mAdapter3;
	private List<Map<String, String>> list;
	FoundInterface dao = new FoundImpl();
	private String[] twoStrings;
	private String path;
	// GDTAD
	@ViewInject(R.id.area_tips_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	// 网络查询攻略
	AreaTipsBean areaTipsBean;
	BmobQuery<AreaTipsBean> query = new BmobQuery<AreaTipsBean>();
	private List<AreaTipsBean> list2;
	AreaTipsThreeNewAdapter mAdapter4;

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
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	// 1级分类
	private String[] map1 = { "库汗", "罗切斯特", "莫勒班", "梵赛诺" };
	// 库汗分类
	private String[] area1 = { "北方废墟", "冰山谷", "阿尤伦", "平原入口", "废墟圣域", "冰山谷深处",
			"希尔达森林遗址", "艾贝尔", "哈伊德", "尼福尔海姆", "未知的区域", "钓鱼船", "运动会" };
	// 罗切斯特分类
	private String[] area2 = { "魔族根据地", "奥鲁特城", "地下水路", "卡塔克", "罗切斯特城主塔",
			"尼福尔海姆", "未知的区域", "钓鱼船", "运动会" };
	// 莫勒班分类
	private String[] area3 = { "新月岛", "船之坟墓", "时光的沙漠", "迷雾峰", "安雯", "钓鱼船",
			"运动会" };
	// 梵赛诺分类
	private String[] area4 = { "梵赛诺入口", "梵赛诺山脚", "梵赛诺山腰", "梵赛诺顶峰", "罗霍兰平原" };

	// 防盗防骗分类
	private String[] fdfp = { "骗子名单", "防盗知识", "减少损失" };

	// GDTAD
	private void initgdtad() {
		bannerView = new BannerView(this, ADSize.BANNER,
				GdtAdConstants.GDTAPPID, GdtAdConstants.GDTBANNERID);
		bannerView.setRefresh(30);
		bannerView.setShowClose(true);
		bannerView.setADListener(new AbstractBannerADListener() {

			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(AreaTipsActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", AreaTipsActivity.this);
			}
			
			@Override
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
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

	/*
	 * 查询优化
	 */
	SharedPreferences preferences;
	Editor editor;
	long lastquerytime;
	Long timeNow;

	private void initQueryTime() {
		preferences = getSharedPreferences("AreaTipsActivity",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		lastquerytime = preferences.getLong("querytime", 0);
		timeNow = new Date().getTime();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_tips);
		ViewUtils.inject(this);
		initQueryTime();
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
		mAdapter = new AreaTipsAdapter(this, map1);
		mAdapter2 = new AreaTipsTwoAdapter(this, area1);
		lView1.setAdapter(mAdapter);
		lView2.setAdapter(mAdapter2);
		mAdapter2.notifyDataSetChanged();
		mAdapter.notifyDataSetChanged();
		mPosition = 0;
		mPosition2 = 0;
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				selectonetotwo(0);
				select2to3web(0);
			}
		});
		lView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mPosition = arg2;
				mPosition2 = 0;
				mAdapter.notifyDataSetChanged();
				selectonetotwo(arg2);
				// selectwotothree(0);
				select2to3web(0);
			}
		});
		lView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mPosition2 = arg2;
				mAdapter2.notifyDataSetChanged();
				// selectwotothree(arg2);
				select2to3web(arg2);
			}
		});
		/**
		 * lView3.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 *           arg2, long arg3) { // TODO Auto-generated method stub //
		 *           LogUtilsUtils.e("",
		 *           String.valueOf(list.get(arg2).get("pathid"))); path =
		 *           list.get(arg2).get("pathid"); Intent intent = new
		 *           Intent(AreaTipsActivity.this, AreaTipsReadActivity.class);
		 *           intent.putExtra("path", path); intent.putExtra("area",
		 *           list.get(arg2).get("area")); intent.putExtra("map",
		 *           list.get(arg2).get("map")); intent.putExtra("battle",
		 *           list.get(arg2).get("battle"));
		 *           intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		 *           startActivity(intent); } });
		 */

		lView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String urlString = list2.get(arg2).getUrl();
				Intent intent = new Intent(AreaTipsActivity.this,
						WebViewActivity.class);
				intent.putExtra("url", urlString);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
		});

	}

	private void select2to3web(int category2) {
		query.order("mapName");
		query.addWhereEqualTo("areaName", twoStrings[category2]);
		query.setLimit(1000);
		query.setMaxCacheAge(TimeUnit.DAYS.toMillis(9999));
		if ((timeNow - lastquerytime) > 180 * 60 * 1000) {
			// 如果查询间隔大于3个小时，就联网查询
			editor.putLong("querytime", timeNow);
			editor.commit();
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);// 先从缓存取数据，无论结果如何都会再次从网络获取数据。也就是说会产生2次调用。
		} else {
			// 如果查询间隔小于3小时，就优先缓存
			boolean isInCache = query.hasCachedResult(this, AreaTipsBean.class);
			if (isInCache) {
				query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);// 如果有缓存，先从缓存读取数据，如果没有，再从网络获取。
			} else {
				query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);// 如果没缓存，先从网络读取数据，如果没有，再从缓存中获取。
			}
		}
		query.findObjects(AreaTipsActivity.this,
				new FindListener<AreaTipsBean>() {

					@Override
					public void onSuccess(List<AreaTipsBean> listt) {
						list2 = listt;
						mAdapter4 = new AreaTipsThreeNewAdapter(
								AreaTipsActivity.this, listt);
						lView3.setAdapter(mAdapter4);
						mAdapter4.notifyDataSetChanged();
					}

					@Override
					public void onError(int arg0, String arg1) {
						if (arg0 != 9009) {
							CommonUtils.showShortToast("加载数据失败：" + arg1
									+ "错误码：" + arg0, AreaTipsActivity.this);
							System.err.println(arg0);
						}
					}
				});
	}

	private void selectonetotwo(int category) {
		if (category == 0) {
			mAdapter2 = new AreaTipsTwoAdapter(this, area1);
			twoStrings = area1;
		} else if (category == 1) {
			mAdapter2 = new AreaTipsTwoAdapter(this, area2);
			twoStrings = area2;
		} else if (category == 2) {
			mAdapter2 = new AreaTipsTwoAdapter(this, area3);
			twoStrings = area3;
		} else if (category == 3) {
			mAdapter2 = new AreaTipsTwoAdapter(this, area4);
			twoStrings = area4;
		} else if (category == 4) {
			mAdapter2 = new AreaTipsTwoAdapter(this, fdfp);
			twoStrings = fdfp;
		}
		lView2.setAdapter(mAdapter2);
		mAdapter2.notifyDataSetChanged();
	}

}
