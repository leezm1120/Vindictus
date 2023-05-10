package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.fragment.ManualFragmentA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitleAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public TitleAdapter(Context context, List list) {
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

	class TitleViewHolder {
		public TextView title, remarks, type, area,
		carbonf, carbons,channel;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TitleViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new TitleViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_title_list, null);

			holder.title = (TextView) convertView
					.findViewById(R.id.found_title_title);// 名称
			holder.remarks = (TextView) convertView
					.findViewById(R.id.found_title_remarks);//备注
			holder.type = (TextView) convertView
					.findViewById(R.id.found_title_type);// 类型
			holder.area = (TextView) convertView
					.findViewById(R.id.found_title_area);// 区域
			holder.carbonf = (TextView) convertView
					.findViewById(R.id.found_title_carbonf);// 地图1
																		
//			holder.carbons = (TextView) convertView
//					.findViewById(R.id.found_title_carbons);// 地图2

			holder.channel = (TextView) convertView
					.findViewById(R.id.found_title_channel);// 渠道

			convertView.setTag(holder);

		} else {

			holder = (TitleViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String title = list.get(position).get("title");
		String remarks = list.get(position).get("remarks");
		String type = list.get(position).get("type");
		String area = list.get(position).get("area");
		String carbonf = list.get(position).get("carbonf");
	//	String carbons = list.get(position).get("carbons");
		String channel = list.get(position).get("channel");
		
		holder.title.setText(title);
		holder.remarks.setText(remarks);
		holder.type.setText(type);
		holder.area.setText(area);
		holder.carbonf.setText(carbonf);
	//	holder.carbons.setText(carbons);
		holder.channel.setText(channel);

		return convertView;
	}

}
