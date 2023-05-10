package com.leezm.vindictus.adapter;

import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.VideoCommentsBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.DateUtils;
import com.youku.service.download.DownloadInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DownLoadedAdapter extends BaseAdapter{

	private Context context;
	private List<DownloadInfo> list;
	
	public DownLoadedAdapter(Context context,List<DownloadInfo> list){
		this.context = context;
		this.list = list;
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

	class VideoCommentsHolder{
		public TextView mTitle,mTime;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		VideoCommentsHolder holder;
		if (convertView==null) {
			holder = new VideoCommentsHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_video_downloaded, null);
			holder.mTitle = (TextView) convertView.findViewById(R.id.video_downloaded_info);
			convertView.setTag(holder);
		} else {
			holder = (VideoCommentsHolder) convertView.getTag();
		}
		
		DownloadInfo info = list.get(position);
		
		holder.mTitle.setText(info.title);
		
		return convertView;
	}

}
