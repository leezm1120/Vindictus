package com.leezm.vindictus.fragment;

import com.leezm.vindictus.activity.R;
import com.lidroid.xutils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityFragment extends BaseFragment {

	private static String TAG = "CommunityFragment";
	private Activity mActivity;


	public static CommunityFragment newInstance() {
		CommunityFragment communityFragment = new CommunityFragment();
		return communityFragment;

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("CommunityFragment");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("CommunityFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		CommunityMainFragment mFragment = new CommunityMainFragment();
//		mFragment.setBackButtonVisibility(View.INVISIBLE);
//		getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, mFragment).commit();
		View view = inflater.inflate(R.layout.fragment_community, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return TAG;
	}

}
