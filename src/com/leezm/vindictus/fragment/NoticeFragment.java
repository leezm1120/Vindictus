package com.leezm.vindictus.fragment;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.WebViewActivity;
import com.leezm.vindictus.adapter.NoticeAdapter;
import com.leezm.vindictus.bean.OfficialNoticeBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NoticeFragment extends BaseFragment {

	private static String TAG = "NoticeFragment";
	private Activity mActivity;

	@ViewInject(R.id.notice_fragment_listview)
	ListView listView;
	NoticeAdapter mAdapter;
	BmobQuery<OfficialNoticeBean> query = new BmobQuery<OfficialNoticeBean>();
	List<OfficialNoticeBean> listNotices;

	public static NoticeFragment newInstance() {
		NoticeFragment noticeFragment = new NoticeFragment();
		return noticeFragment;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("NoticeFragment");
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("NoticeFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_notice, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	/*
	 * 查询优化
	 */
	SharedPreferences preferences;
	Editor editor;
	long lastquerytime;
	Long timeNow;

	private void initQueryTime() {
		preferences = mActivity.getSharedPreferences("NoticeFragment",
				Context.MODE_PRIVATE);
		editor = preferences.edit();
		lastquerytime = preferences.getLong("querytime", 0);
		timeNow = new Date().getTime();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initQueryTime();
		query.order("-createdAt");
		query.setLimit(1000);
		query.setMaxCacheAge(TimeUnit.DAYS.toMillis(9999));
		if ((timeNow - lastquerytime) > 60 * 60 * 1000) {
			// 如果查询间隔大于1个小时，就联网查询
			editor.putLong("querytime", timeNow);
			editor.commit();
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);// 先从缓存取数据，无论结果如何都会再次从网络获取数据。也就是说会产生2次调用。
		} else {
			// 如果查询间隔小于1小时，就优先缓存
			boolean isInCache = query.hasCachedResult(mActivity,
					OfficialNoticeBean.class);
			if (isInCache) {
				query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);// 如果有缓存，先从缓存读取数据，如果没有，再从网络获取。
			} else {
				query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);// 如果没缓存，先从网络读取数据，如果没有，再从缓存中获取。
			}
		}
		query.findObjects(mActivity, new FindListener<OfficialNoticeBean>() {
			@Override
			public void onSuccess(List<OfficialNoticeBean> list) {
				listNotices = list;
				mAdapter = new NoticeAdapter(mActivity, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				if (arg0 != 9009) {
					CommonUtils.showShortToast(
							"加载数据失败：" + arg1 + "错误码：" + arg0, mActivity);
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String urlString = listNotices.get(arg2).getUrl();
				Intent intent = new Intent(mActivity, WebViewActivity.class);
				intent.putExtra("url", urlString);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

}
