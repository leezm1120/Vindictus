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

public class ManualEquipmentAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public ManualEquipmentAdapter(Context context, List list) {
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

	class ManualEquipmentViewHolder {
		public TextView metitle, melevel, meremarks, meatt, mestr, memint,
				meagi, mewil, mebal, meattspd, mecritical, medef, mecritresist;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEquipmentViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEquipmentViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_equipment, null);

			holder.metitle = (TextView) convertView
					.findViewById(R.id.manual_equipment_title);// 名称
			holder.melevel = (TextView) convertView
					.findViewById(R.id.manual_equipment_level);// 等级
			holder.meremarks = (TextView) convertView
					.findViewById(R.id.manual_equipment_remarks);// 备注
			holder.meatt = (TextView) convertView
					.findViewById(R.id.manual_equipment_att);// 攻击
			holder.mestr = (TextView) convertView
					.findViewById(R.id.manual_equipment_str);// 力量
			holder.memint = (TextView) convertView
					.findViewById(R.id.manual_equipment_mint);// 智力
			holder.meagi = (TextView) convertView
					.findViewById(R.id.manual_equipment_agi);// 敏捷
			holder.mewil = (TextView) convertView
					.findViewById(R.id.manual_equipment_wil);// 意志
			holder.mebal = (TextView) convertView
					.findViewById(R.id.manual_equipment_bal);// 平衡
			holder.meattspd = (TextView) convertView
					.findViewById(R.id.manual_equipment_att_spd);// 攻速
			holder.mecritical = (TextView) convertView
					.findViewById(R.id.manual_equipment_critical);// 暴击
			holder.medef = (TextView) convertView
					.findViewById(R.id.manual_equipment_def);// 防御
			holder.mecritresist = (TextView) convertView
					.findViewById(R.id.manual_equipment_critresist);// 暴击抵抗
			convertView.setTag(holder);

		} else {

			holder = (ManualEquipmentViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String title = list.get(position).get("title");
		String level = list.get(position).get("level");
		String remarks = list.get(position).get("remarks");
		String att = list.get(position).get("att");
		String str = list.get(position).get("str");
		String mint = list.get(position).get("mint");
		String agi = list.get(position).get("agi");
		String wil = list.get(position).get("wil");
		String bal = list.get(position).get("bal");
		String attspd = list.get(position).get("attspd");
		String critical = list.get(position).get("critical");
		String def = list.get(position).get("def");
		String critresist = list.get(position).get("critresist");

		holder.metitle.setText(title);
		holder.melevel.setText(level);
		holder.meremarks.setText(remarks);
		holder.meatt.setText(att);
		holder.mestr.setText(str);
		holder.memint.setText(mint);
		holder.meagi.setText(agi);
		holder.mewil.setText(wil);
		holder.mebal.setText(bal);
		holder.meattspd.setText(attspd);
		holder.mecritical.setText(critical);
		holder.medef.setText(def);
		holder.mecritresist.setText(critresist);
		return convertView;
	}

}
