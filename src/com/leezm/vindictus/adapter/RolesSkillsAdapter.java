package com.leezm.vindictus.adapter;

import java.util.List;

import com.leezm.vindictus.activity.MapImgActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.RolesSkillsBean;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RolesSkillsAdapter extends BaseAdapter {

	private Context context;
	private List<RolesSkillsBean> list;
	private BitmapUtils bUtils;

	public RolesSkillsAdapter(Context context, List list) {
		this.context = context;
		this.list = list;
		bUtils = new BitmapUtils(context);
		bUtils.configDefaultShowOriginal(true);
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

	class RolesSkillsAdapterHolder {
		public ImageView mIcon;
		public TextView mTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		RolesSkillsAdapterHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new RolesSkillsAdapterHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_roles_skill, null);
			holder.mTitle = (TextView) convertView
					.findViewById(R.id.roles_skills_title);
			holder.mIcon = (ImageView) convertView
					.findViewById(R.id.roles_skills_icon);
			convertView.setTag(holder);

		} else {
			holder = (RolesSkillsAdapterHolder) convertView.getTag();
		}

		String title = list.get(position).getSkillName();
		String iconurl = list.get(position).getIconUrl();
		String mtitle = "";

		String[] str = title.split("#");
		for (int i = 0; i < str.length; i++) {
			mtitle = mtitle + str[i] + "\n";
		}
		
		holder.mTitle.setText(mtitle);
		bUtils.display(holder.mIcon, list.get(position).getIconUrl());

		return convertView;
	}

}
