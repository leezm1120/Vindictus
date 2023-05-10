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

public class EquipmentSkillAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public EquipmentSkillAdapter(Context context, List list) {
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
		public TextView mname, mpart, mlevel, mdetail,mretime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_skill_list, null);

			holder.mname = (TextView) convertView
					.findViewById(R.id.skill_item_title);// BOSS名称
			holder.mpart = (TextView) convertView
					.findViewById(R.id.skill_item_title_part);// 攻击
			holder.mlevel = (TextView) convertView
					.findViewById(R.id.skill_item_title_level);// 防御
			holder.mdetail = (TextView) convertView
					.findViewById(R.id.skill_item_remarks);// 爆抗
			holder.mretime = (TextView) convertView.findViewById(R.id.skill_item_lengque);//区域

			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String name = list.get(position).get("name");
		String part = list.get(position).get("part");
		String level = list.get(position).get("level");
		String detail = list.get(position).get("detail");
		String retime = list.get(position).get("retime");

		holder.mname.setText(name);
		holder.mpart.setText(part);
		holder.mlevel.setText(level);
		holder.mdetail.setText(detail);
		holder.mretime.setText("重置时间："+retime);

		return convertView;
	}

}
