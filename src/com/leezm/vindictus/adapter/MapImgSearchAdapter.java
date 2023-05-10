package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.MapImgActivity;
import com.leezm.vindictus.activity.MapImgSearchActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.AreaTipsBean;
import com.leezm.vindictus.bean.MapImgBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapImgSearchAdapter extends BaseAdapter {

	private Context context;
	private List<MapImgBean> list;

	public MapImgSearchAdapter(Context context, List list) {
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
					R.layout.item_mapimg, null);

			holder.mtitle = (TextView) convertView
					.findViewById(R.id.mapimg_item_titleaa);// BOSS名称

			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		if (position == MapImgSearchActivity.mPosition) {
			holder.mtitle.setTextColor(Color.rgb(247, 171, 38));
		} else {
			holder.mtitle.setTextColor(Color.rgb(255, 255, 255));
		}
		String id = list.get(position).getObjectId();
		String battle = list.get(position).getName();

		holder.mtitle.setText(battle);

		return convertView;
	}

}
