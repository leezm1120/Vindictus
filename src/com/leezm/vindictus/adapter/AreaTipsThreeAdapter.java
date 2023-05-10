package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AreaTipsThreeAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public AreaTipsThreeAdapter(Context context, List list) {
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

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String battle = list.get(position).get("battle");

		holder.mtitle.setText(battle);

		return convertView;
	}

}
