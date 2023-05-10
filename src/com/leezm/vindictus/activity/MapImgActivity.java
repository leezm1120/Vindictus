package com.leezm.vindictus.activity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.adapter.MapImgAdapter;
import com.leezm.vindictus.bean.MapImgBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MapImgActivity extends Activity implements OnClickListener {

	@ViewInject(R.id.map_img_view)
	ImageView imageView;
	@ViewInject(R.id.map_img_search)
	ImageView btSearch;
	@ViewInject(R.id.map_img_listview)
	ListView listView;
	@ViewInject(R.id.txt_map_img_zhuangbei)
	TextView tViewzb;
	@ViewInject(R.id.txt_map_img_shizhuang)
	TextView tViewsz;
	@ViewInject(R.id.txt_map_img_neiyi)
	TextView tViewny;
	SharedPreferences preferences;
	Editor editor;
	private int is;
	private String keyString = "", urlString = "", titleString = "";
	public static int mPosition = 0;
	BmobQuery<MapImgBean> query = new BmobQuery<MapImgBean>();
	List<MapImgBean> mlist;
	MapImgAdapter mAdapter;
	BitmapUtils bitmapUtils;
	
	//PopWindow
	private ImageView pSave,pImg;
	private PopupWindow popupWindow;
	private Display display;
	private Bitmap bitmap;
	
	// GDTAD
	@ViewInject(R.id.mapimg_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/*
	 * 查询优化
	 */
	long lastquerytime;
	Long timeNow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_img);
		ViewUtils.inject(this);
		display = getWindowManager().getDefaultDisplay();
		init();
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
				MobclickAgent.onEvent(MapImgActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", MapImgActivity.this);
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
		preferences = getSharedPreferences("MapImgActivity", MODE_PRIVATE);
		editor = preferences.edit();
		lastquerytime = preferences.getLong("querytime", 0);
		timeNow = new Date().getTime();
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultShowOriginal(true);
		tViewzb.setOnClickListener(this);
		tViewsz.setOnClickListener(this);
		tViewny.setOnClickListener(this);
		btSearch.setOnClickListener(this);
		if (preferences.getInt("select", 0) == 1) {
			tViewzb.performClick();
		} else if (preferences.getInt("select", 0) == 2) {
			tViewsz.performClick();
		} else if (preferences.getInt("select", 0) == 3) {
			tViewny.performClick();
		} else {
			tViewzb.performClick();
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				bitmapUtils.display(imageView, mlist.get(arg2).getImgurl());
				mPosition = arg2;
				mAdapter.notifyDataSetChanged();
				urlString = mlist.get(arg2).getImgurl();
				titleString = keyString + "：" + mlist.get(arg2).getName();
			}
		});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(MapImgActivity.this,
//						ShowImgDialogActivity.class);
//				intent.putExtra("urlString", urlString);
//				intent.putExtra("titleString", titleString);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//				startActivity(intent);
				
				//把activity改成popwindow
				showPopWindow(arg0);
			}
		});
	}

	private void showPopWindow(View mView){
		View pView = LayoutInflater.from(this).inflate(R.layout.pop_show_map_img, null);
		pSave = (ImageView) pView.findViewById(R.id.pop_show_img_save);
		pImg = (ImageView) pView.findViewById(R.id.pop_show_img_view);
		
		bitmapUtils.display(pImg, urlString);
		
		pSave.setOnClickListener(this);
		pImg.setOnClickListener(this);
		
		popupWindow = new PopupWindow(pView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth((int) (display.getWidth()*0.85));
		popupWindow.setHeight((int) (display.getHeight()*0.75));
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop));
		popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}
	
	private void gosearch() {
		query.order("name");
		query.setMaxCacheAge(TimeUnit.DAYS.toMillis(9999));
		query.addWhereEqualTo("type", keyString);
		query.setLimit(1000);
		if ((timeNow - lastquerytime) > 180 * 60 * 1000) {
			// 如果查询间隔大于3个小时，就联网查询
			editor.putLong("querytime", timeNow);
			editor.commit();
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);// 先从缓存取数据，无论结果如何都会再次从网络获取数据。也就是说会产生2次调用。
		} else {
			// 如果查询间隔小于3小时，就优先缓存
			boolean isInCache = query.hasCachedResult(this, MapImgBean.class);
			if (isInCache) {
				query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);// 如果有缓存，先从缓存读取数据，如果没有，再从网络获取。
			} else {
				query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);// 如果没缓存，先从网络读取数据，如果没有，再从缓存中获取。
			}
		}
		query.findObjects(this, new FindListener<MapImgBean>() {

			@Override
			public void onSuccess(List<MapImgBean> list) {
				mlist = list;
				mAdapter = new MapImgAdapter(MapImgActivity.this, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				bitmapUtils.display(imageView, mlist.get(0).getImgurl());
				mPosition = 0;
				urlString = mlist.get(0).getImgurl();
				titleString = keyString + "：" + mlist.get(0).getName();
			}

			@Override
			public void onError(int arg0, String arg1) {
				if (arg0 != 9009) {
					CommonUtils.showShortToast(
							"加载数据失败：" + arg1 + "错误码：" + arg0,
							MapImgActivity.this);
				}
			}
		});
	}

	private void setSelectStyles(TextView textView) {
		textView.setTextColor(Color.rgb(247, 171, 38));
		textView.setTextSize(17);
		TextPaint tPaint = textView.getPaint();
		tPaint.setFakeBoldText(true);
		gosearch();
	}

	private void setUnSelectStyles(TextView textView) {
		textView.setTextColor(Color.rgb(255, 255, 255));
		textView.setTextSize(13);
		TextPaint tPaint = textView.getPaint();
		tPaint.setFakeBoldText(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		editor.putInt("select", is);
		editor.commit();
		bitmapUtils.pause();
		try {
			CommonUtils.reFreshPhoto(this);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		bitmapUtils.resume();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.txt_map_img_zhuangbei:
			is = 1;
			keyString = "装备";
			setSelectStyles(tViewzb);
			setUnSelectStyles(tViewsz);
			setUnSelectStyles(tViewny);
			break;
		case R.id.txt_map_img_shizhuang:
			is = 2;
			keyString = "时装";
			setSelectStyles(tViewsz);
			setUnSelectStyles(tViewzb);
			setUnSelectStyles(tViewny);
			break;
		case R.id.txt_map_img_neiyi:
			is = 3;
			keyString = "内衣";
			setSelectStyles(tViewny);
			setUnSelectStyles(tViewsz);
			setUnSelectStyles(tViewzb);
			break;
		case R.id.map_img_search:
			CommonUtils.launchActivity(MapImgActivity.this, MapImgSearchActivity.class);
			break;
				
			case R.id.pop_show_img_save:
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						//将ImageView中的图片转换成Bitmap
						pImg.buildDrawingCache();
						bitmap = pImg.getDrawingCache();
						MediaStore.Images.Media.insertImage(
								MapImgActivity.this.getContentResolver(),
								bitmap,titleString, "");
						CommonUtils.showShortToast("已保存到相册", MapImgActivity.this);
					}
				});
				break;
				
	case R.id.pop_show_img_view:
				popupWindow.dismiss();
				break;
				
		default:
			break;
		}
	}
}
