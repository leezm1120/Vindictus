package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.AreaTipsActivity;
import com.leezm.vindictus.activity.R;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RolesSelectAdapter extends BaseAdapter {

	private Context context;
	private String[] strings;
	BitmapUtils bUtils;

	public RolesSelectAdapter(Context context, String[] strings) {
		this.context = context;
		this.strings = strings;
		bUtils = new BitmapUtils(context);
		bUtils.configDefaultShowOriginal(true);
	}

	@Override
	public int getCount() {
		return strings.length;
	}

	@Override
	public Object getItem(int position) {
		return strings[position];
	}

	@Override
	public long getItemId(int itemId) {
		return itemId;
	}

	class RolesSelectAdapterHolder {
		public ImageView iView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RolesSelectAdapterHolder holder;
		if (convertView == null) {
			holder = new RolesSelectAdapterHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_roles_select, null);
			holder.iView = (ImageView) convertView.findViewById(R.id.roles_src);
			convertView.setTag(holder);
		} else {
			holder = (RolesSelectAdapterHolder) convertView.getTag();
		}
		bUtils.display(holder.iView, "assets/roles/"+strings[position]+".png");
		return convertView;
	}

}
