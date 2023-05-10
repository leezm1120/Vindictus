package com.leezm.vindictus.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import com.leezm.vindictus.adapter.DownloadingAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.widget.ListView;

/**
 * 正在下载中的视频
 * 
 * @author LeeZm
 * 
 */

public class YouKuDownloadingActivity extends Activity {
	// 展示视频信息的ListView
	@ViewInject(R.id.video_downloading_list)
	ListView listView;

	// 通过DownloadManager获取下载视频列表
	private DownloadManager downloadManager;

	// 记录当前下载中的视频列表
	private ArrayList<DownloadInfo> list = new ArrayList<DownloadInfo>();

	// 数据Adapter
	private DownloadingAdapter mAdapter;

	private static final int MSG_STATE_CHANGE = 0;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == MSG_STATE_CHANGE) {
				initDate();
				// 更改界面现实状态
				mAdapter.notifyDataSetChanged();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_ku_downloading);
		ViewUtils.inject(this);
		initDate();
		init();
	}

	private void initDate() {
		list.clear();

		// 通过DownloadManager类的getDownloadingData()接口函数获取已经下载的视频信息
		downloadManager = DownloadManager.getInstance();
		Iterator iter = downloadManager.getDownloadingData().entrySet()
				.iterator();

		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			// 视频信息实体类用DownloadInfo表示
			DownloadInfo info = (DownloadInfo) entry.getValue();
			list.add(info);
		}

	}

	private void init() {
		mAdapter = new DownloadingAdapter(this, list, handler, downloadManager);
		listView.setAdapter(mAdapter);
	}
}
