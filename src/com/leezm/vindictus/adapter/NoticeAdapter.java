package com.leezm.vindictus.adapter;

import java.util.List;
import java.util.Map;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.bean.OfficialNoticeBean;
import com.leezm.vindictus.fragment.ManualFragmentA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeAdapter extends BaseAdapter {

	private Context context;
	private List<OfficialNoticeBean> list;

	public NoticeAdapter(Context context, List list) {
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
		public TextView mtype,mtitle,mdate,murl;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ManualEnchantViewHolder holder;
		// ManualFragmentA mFragmentA = context;

		if (convertView == null) {
			holder = new ManualEnchantViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_notice, null);

			holder.mtype = (TextView) convertView
					.findViewById(R.id.item_notice_type);// 类型
			holder.mtitle = (TextView) convertView
					.findViewById(R.id.item_notice_title);// 标题
			holder.mdate = (TextView) convertView
					.findViewById(R.id.item_notice_date);// 日期
			
			convertView.setTag(holder);

		} else {

			holder = (ManualEnchantViewHolder) convertView.getTag();
		}
		String title = list.get(position).getTitle();
		String type = list.get(position).getType();
		String date = list.get(position).getDate();

		holder.mtype.setText(" "+type+" ");
		holder.mtitle.setText(title);
		holder.mdate.setText(date);

		return convertView;
	}

}
