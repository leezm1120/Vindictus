package com.leezm.vindictus.adapter;

import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.VideoCommentsBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.DateUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoCommentsAdapter extends BaseAdapter{

	private Context context;
	private List<VideoCommentsBean> list;
	
	public VideoCommentsAdapter(Context context,List<VideoCommentsBean> list){
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
		public TextView mComments,mTime;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		VideoCommentsHolder holder;
		if (convertView==null) {
			holder = new VideoCommentsHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_video_comments, null);
			holder.mComments = (TextView) convertView.findViewById(R.id.video_comments_info);
			holder.mTime = (TextView) convertView.findViewById(R.id.video_comments_time);
			convertView.setTag(holder);
		} else {
			holder = (VideoCommentsHolder) convertView.getTag();
		}
		
		String comments = list.get(position).getComments();
		String time = list.get(position).getAddTime();
		
		holder.mComments.setText(comments);
		holder.mTime.setText(DateUtils.fromToday(time));
		
		return convertView;
	}

}
