package com.leezm.vindictus.adapter;

import java.util.List;

import com.leezm.vindictus.fragment.BaseFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

public class CampSkillsAdapter extends FragmentPagerAdapter {

	private Context context;
	private List<BaseFragment> list;

	public CampSkillsAdapter(Context context, List<BaseFragment> list) {
		super(((FragmentActivity) context).getSupportFragmentManager());
		this.context = context;
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
