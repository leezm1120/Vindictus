package com.leezm.vindictus.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import com.leezm.vindictus.adapter.DownLoadedAdapter;
import com.leezm.vindictus.adapter.VideoCommentsAdapter;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 展示已经缓存的视频
 * 
 * @author LeeZm
 * 
 */

public class YouKuDownloadActivity extends Activity {

	// 展示视频信息的ListView
	@ViewInject(R.id.video_downloaded_list)
	ListView listView;
	// 数据Adapter
	private DownLoadedAdapter mAdapter;
	// 通过DownloadManager获取下载视频列表
	private DownloadManager downloadManager;

	// 记录当前已经下载的视频列表
	private ArrayList<DownloadInfo> list = new ArrayList<DownloadInfo>();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 获取已经下载的视频数据
		initData();
		// 同志数据更新
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_ku_download);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		mAdapter = new DownLoadedAdapter(this, list);
		listView.setAdapter(mAdapter);
		/**
		 * 通过单击已经下载过的视频进行播放
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 获取点击item表示视频信息
				DownloadInfo info = list.get(position);

				// 跳转到播放界面进行播放，用户可以修改为自己的播放界面
				Intent intent = new Intent(YouKuDownloadActivity.this,
						YouKuPlayerActivity.class);

				// 点击缓存视频播放时需要传递给播放界面的两个参数
				intent.putExtra("isFromLocal", true);
				intent.putExtra("local_vid", info.videoid);
				LogUtils.e("local_vid", info.videoid);

				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				AlertDialogUtils.showDialog(YouKuDownloadActivity.this, "是否要删除该视频？", new OnClickYesListener() {
					
					@Override
					public void onClickYes() {
						DownloadInfo info = list.get(arg2);
						downloadManager.deleteDownloaded(info);
						// 获取已经下载的视频数据
						initData();
						// 同志数据更新
						mAdapter.notifyDataSetChanged();
					}
				}, new OnClickNoListener() {
					@Override
					public void onClickNo() {
					}
				});
				return true;
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		list.clear();
		super.onDestroy();
	}

	/**
	 * 获取已下载视频信息
	 */
	private void initData() {
		list.clear();

		// 通过DownloadManager类的getDownloadedData()接口函数获取已经下载的视频信息
		downloadManager = DownloadManager.getInstance();
		Iterator iter = downloadManager.getDownloadedData().entrySet()
				.iterator();

		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			// 视频信息实体类用DownloadInfo表示
			DownloadInfo info = (DownloadInfo) entry.getValue();
			list.add(info);
		}

	}

}
