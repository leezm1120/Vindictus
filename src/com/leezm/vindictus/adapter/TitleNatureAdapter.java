package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.fragment.ManualFragmentA;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitleNatureAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;
	private String role;

	public TitleNatureAdapter(Context context, List list,String role) {
		this.context = context;
		this.list = list;
		this.role = role;
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
		carbonf, carbons,channel,liliang,minjie,zhili,yizhi,hasdone;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TitleViewHolder holder;
		SharedPreferences preferences;
		preferences = context.getSharedPreferences("titlehasdone", Context.MODE_PRIVATE);
		// ManualFragmentA mFragmentA = context;
		if (convertView == null) {
			holder = new TitleViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_title_nature_list, null);

			holder.title = (TextView) convertView
					.findViewById(R.id.found_title_nature_title);// 名称
			holder.remarks = (TextView) convertView
					.findViewById(R.id.found_title_nature_remarks);//备注
			holder.type = (TextView) convertView
					.findViewById(R.id.found_title_nature_type);// 类型
			holder.area = (TextView) convertView
					.findViewById(R.id.found_title_nature_area);// 区域
			holder.carbonf = (TextView) convertView
					.findViewById(R.id.found_title_nature_carbonf);// 地图1
			holder.channel = (TextView) convertView
					.findViewById(R.id.found_title_nature_channel);// 渠道
			holder.liliang = (TextView) convertView
					.findViewById(R.id.found_title_nature_liliang);// 力量
			holder.zhili = (TextView) convertView
					.findViewById(R.id.found_title_nature_zhili);// 智力
			holder.minjie = (TextView) convertView
					.findViewById(R.id.found_title_nature_minjie);// 敏捷
			holder.yizhi = (TextView) convertView
					.findViewById(R.id.found_title_nature_yizhi);// 意志
			holder.hasdone = (TextView) convertView.findViewById(R.id.found_title_nature_done);//已完成标记
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
		String liliang = list.get(position).get("liliang");
		String zhili = list.get(position).get("zhili");
		String minjie = list.get(position).get("minjie");
		String yizhi = list.get(position).get("yizhi");
		
		holder.title.setText(title);
		holder.remarks.setText(remarks);
		holder.type.setText(type);
		holder.area.setText(area);
		holder.carbonf.setText(carbonf);
	//	holder.carbons.setText(carbons);
		holder.channel.setText(channel);
		holder.liliang.setText(liliang);
		holder.zhili.setText(zhili);
		holder.minjie.setText(minjie);
		holder.yizhi.setText(yizhi);

		//标记已完成
		String hasdoneString = preferences.getString(role+title, "");
		if (hasdoneString.equals("")) {
			holder.hasdone.setText("");
		} else {
			holder.hasdone.setText("√");
		}
		
		return convertView;
	}

}
