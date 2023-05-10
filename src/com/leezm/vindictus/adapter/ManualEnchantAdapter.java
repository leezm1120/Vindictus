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

public class ManualEnchantAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public ManualEnchantAdapter(Context context, List list) {
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
				mecustomattribute, mecustomprovenance,mprice;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		SharedPreferences preferences;
		preferences = context.getSharedPreferences("enchantprice", Context.MODE_PRIVATE);
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_enchant, null);

			holder.metitle = (TextView) convertView
					.findViewById(R.id.manual_enchant_title);// 名称
			holder.melevel = (TextView) convertView
					.findViewById(R.id.manual_enchant_level);// 级别 7 8 9 A B C D
			holder.mestyle = (TextView) convertView
					.findViewById(R.id.manual_enchant_style);// 类型 首次 进阶
			holder.mecustompart = (TextView) convertView
					.findViewById(R.id.manual_enchant_custom_part);// 部位 用备注字段显示
			holder.mecustomattribute = (TextView) convertView
					.findViewById(R.id.manual_enchant_custom_attribute);// 属性
																		// 用备注字段显示
			holder.mecustomprovenance = (TextView) convertView
					.findViewById(R.id.manual_enchant_custom_provenance);// 出处用备注字段显示
			
			holder.mprice = (TextView) convertView.findViewById(R.id.manual_enchant_price);//自定义价格备注

			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String id = list.get(position).get("_id");
		String title = list.get(position).get("title");
		String level = list.get(position).get("level");
		String style = list.get(position).get("style");
		String custompart = list.get(position).get("custompart");
		String customattribute = list.get(position).get("customattribute");
		String customprovenance = list.get(position).get("customprovenance");

		holder.metitle.setText(title);
		holder.melevel.setText(level);
		holder.mestyle.setText(style);
		holder.mecustompart.setText(custompart);
		holder.mecustomattribute.setText(customattribute);
		holder.mecustomprovenance.setText(customprovenance);

		String priceString = preferences.getString(title, "");
		holder.mprice.setText(priceString);
		
		return convertView;
	}

}
