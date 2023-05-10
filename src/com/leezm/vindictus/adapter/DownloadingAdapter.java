package com.leezm.vindictus.adapter;

import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.VideoCommentsBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.DateUtils;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DownloadingAdapter extends BaseAdapter {

	private Context context;
	private List<DownloadInfo> list;
	private Handler handler;
	private DownloadManager downloadManager;

	public DownloadingAdapter(Context context, List<DownloadInfo> list,
			Handler handler, DownloadManager downloadManager) {
		this.context = context;
		this.list = list;
		this.handler = handler;
		this.downloadManager = downloadManager;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int item) {
		// TODO Auto-generated method stub
		return list.get(item);
	}

	@Override
	public long getItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemId;
	}

	class VideoCommentsHolder {
		public TextView mTitle, mBtCancle, mBtPush;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		VideoCommentsHolder holder;
		final DownloadInfo info = list.get(position);
		if (convertView == null) {
			holder = new VideoCommentsHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_video_downloading, null);
			holder.mTitle = (TextView) convertView
					.findViewById(R.id.video_downloading_info);
			holder.mBtCancle = (TextView) convertView
					.findViewById(R.id.video_downloading_cancle);
			holder.mBtPush = (TextView) convertView
					.findViewById(R.id.video_downloading_push);
			convertView.setTag(holder);
		} else {
			holder = (VideoCommentsHolder) convertView.getTag();
		}

		if (info.state == DownloadInfo.STATE_DOWNLOADING) { // 当前视频的下载状态：正在下载
			holder.mBtPush.setText("正在下载");
		} else if (info.state == DownloadInfo.STATE_PAUSE) { // 当前视频的下载状态：暂停中
			holder.mBtPush.setText("暂停中");
		} else if (info.state == DownloadInfo.STATE_INIT // 当前视频的下载状态：等待中
				|| info.state == DownloadInfo.STATE_EXCEPTION
				|| info.state == DownloadInfo.STATE_WAITING) {
			holder.mBtPush.setText("等待中");
		}

		holder.mTitle.setText(info.title);
		// 单击暂停/继续按钮改变当前下载任务的状态
		holder.mBtPush.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (info.state == DownloadInfo.STATE_DOWNLOADING
						|| info.state == DownloadInfo.STATE_WAITING
						|| info.state == DownloadInfo.STATE_INIT
						|| info.state == DownloadInfo.STATE_EXCEPTION) {

					// 处于以上几种状态时单击进行暂停任务
					downloadManager.pauseDownload(info.taskId);

				} else if (info.state == DownloadInfo.STATE_PAUSE) {
					// 处于暂停状态时单击进行继续任务
					downloadManager.startDownload(info.taskId);

				}

				handler.sendEmptyMessageDelayed(0, 200);
			}
		});

		// 取消当前下载任务
		holder.mBtCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {
						// TODO Auto-generated method stub
						// 通过DonwloadManager类的deleteDownloading()接口函数删除所限泽的正在下载的任务
						return downloadManager.deleteDownloading(info.taskId);
					}

				}.execute();
				// 通知更新
				handler.sendEmptyMessageDelayed(0, 100);
			}

		});
		return convertView;
	}

}
