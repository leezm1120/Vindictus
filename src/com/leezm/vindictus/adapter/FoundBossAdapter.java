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

public class FoundBossAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public FoundBossAdapter(Context context, List list) {
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
		public TextView mftitle, mfatt, mfdef, mfresist, marea, maremarks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_boss_list, null);

			holder.mftitle = (TextView) convertView
					.findViewById(R.id.found_boss_title);// BOSS名称
			holder.mfatt = (TextView) convertView
					.findViewById(R.id.found_boss_att);// 攻击
			holder.mfdef = (TextView) convertView
					.findViewById(R.id.found_boss_def);// 防御
			holder.mfresist = (TextView) convertView
					.findViewById(R.id.found_boss_resist);// 爆抗
			holder.marea = (TextView) convertView
					.findViewById(R.id.found_boss_area);// 区域
			holder.maremarks = (TextView) convertView
					.findViewById(R.id.found_boss_remarksa);// 血量

			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String title = list.get(position).get("title");
		String att = list.get(position).get("att");
		String def = list.get(position).get("def");
		String resist = list.get(position).get("resist");
		String area = list.get(position).get("area");
		String remarkString = list.get(position).get("remarks");

		holder.mftitle.setText(title);
		holder.marea.setText(area);
		holder.mfatt.setText("攻击：" + att);
		holder.mfdef.setText("防御：" + def);
		holder.mfresist.setText("暴抗：" + resist);
		holder.maremarks.setText(remarkString);

		return convertView;
	}

}
