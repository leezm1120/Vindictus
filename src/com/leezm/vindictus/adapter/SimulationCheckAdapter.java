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

public class SimulationCheckAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public SimulationCheckAdapter(Context context, List list) {
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
		public TextView metitle, melevel, mestyle, mecustompart,
				mecustomattribute, mecustomprovenance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_simulation_check, null);

			holder.metitle = (TextView) convertView
					.findViewById(R.id.simulation_check_title);// 名称
			holder.melevel = (TextView) convertView
					.findViewById(R.id.simulation_check_level);// 级别 7 8 9 A B C
																// D
			holder.mestyle = (TextView) convertView
					.findViewById(R.id.simulation_check_type);// 类型 首次 进阶
			holder.mecustompart = (TextView) convertView
					.findViewById(R.id.simulation_check_part);// 部位 用备注字段显示
//			holder.mecustomprovenance = (TextView) convertView
//					.findViewById(R.id.simulation_check_remarks);
			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String title = list.get(position).get("title");
		String level = list.get(position).get("level");
		String stype = list.get(position).get("type");
		String spart = list.get(position).get("part");
		String sremarks = list.get(position).get("remarks");

		holder.metitle.setText(title);
		holder.melevel.setText(level);
		//holder.mestyle.setText(stype);
		holder.mecustompart.setText(sremarks);
		//holder.mecustomprovenance.setText(sremarks);

		return convertView;
	}

}
