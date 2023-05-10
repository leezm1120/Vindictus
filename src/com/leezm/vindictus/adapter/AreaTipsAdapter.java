package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.AreaTipsActivity;
import com.leezm.vindictus.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AreaTipsAdapter extends BaseAdapter {

	private Context context;
	private String[] strings;
	//public static int mPosition=0;

	public AreaTipsAdapter(Context context, String[] strings) {
		this.context = context;
		this.strings = strings;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strings[position];
	}

	@Override
	public long getItemId(int itemId) {
		// TODO Auto-generated method stub
		return itemId;
	}

	class ManualEnchantViewHolder {
		public TextView mtitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_tips_area, null);
			holder.mtitle = (TextView) convertView
					.findViewById(R.id.area_tips_item_title);// BOSS名称
			convertView.setTag(holder);
			holder.mtitle.setText(strings[position]);
		} else {
			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		if (position == AreaTipsActivity.mPosition) {
			holder.mtitle.setTextColor(Color.rgb(247, 171, 38));
		} else {
			holder.mtitle.setTextColor(Color.rgb(255, 255, 255));
		}
		return convertView;
	}

}
