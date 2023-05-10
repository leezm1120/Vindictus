package com.leezm.vindictus.adapter;

import java.util.Date;
import java.util.List;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.VideoBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.DateUtils;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter{

	private Context context;
	private List<VideoBean> list;
	private BitmapUtils bUtils;
	private  BitmapDisplayConfig config;
	
	public VideoAdapter(Context context,List<VideoBean> list){
		this.context = context;
		this.list = list;
		bUtils = new BitmapUtils(context);
		bUtils.configDefaultLoadingImage(R.drawable.video_loading);//默认背景图片 
		bUtils.configDefaultLoadFailedImage(R.drawable.video_load_fail);//加载失败图片  
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

	class VideoAdapterHolder{
		public RelativeLayout imageView;
		public TextView mtitle,mdetails,mtime,muptime;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VideoAdapterHolder holder;
		if (convertView == null) {
			holder = new VideoAdapterHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_video, null);
			holder.imageView = (RelativeLayout) convertView.findViewById(R.id.video_layout);
			holder.mtitle = (TextView) convertView.findViewById(R.id.video_title);
			holder.mdetails = (TextView) convertView.findViewById(R.id.video_details);
			holder.mtime = (TextView) convertView.findViewById(R.id.video_time);
			holder.muptime = (TextView) convertView.findViewById(R.id.video_uptime);
			convertView.setTag(holder);
		} else {
			holder = (VideoAdapterHolder) convertView.getTag();
		}

		String iconUrl = list.get(position).getvImgUrl();
		String title = list.get(position).getvTitle();
		String details = list.get(position).getvDetails();
		String time = list.get(position).getvLength();
		String addtime = list.get(position).getAddTime();
		
		bUtils.display(holder.imageView, iconUrl);
		holder.mtitle.setText(title);
		holder.mdetails.setText(details);
		LogUtils.e("details", details.length()+"");
		holder.mtime.setText(" "+CommonUtils.second2Hours(time)+" ");
		holder.muptime.setText(DateUtils.fromToday(addtime));
		
		return convertView;
	}

	
	
}
