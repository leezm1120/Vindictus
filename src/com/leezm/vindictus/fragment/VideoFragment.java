package com.leezm.vindictus.fragment;



import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.activity.AddVideoActivity;
import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.YouKuPlayerActivity;
import com.leezm.vindictus.adapter.VideoAdapter;
import com.leezm.vindictus.bean.VideoBean;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.LogUtils;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoFragment extends BaseFragment implements OnClickListener {

	private static String TAG = "VideoFragment";
	private Activity mActivity;
	VideoAdapter mAdapter;
	BmobQuery<VideoBean> query = new BmobQuery<VideoBean>();
	List<VideoBean> mList;
	VideoBean mBean;
	
	@ViewInject(R.id.video_fragment_listview)
	ListView listView;


	public static VideoFragment newInstance() {
		VideoFragment videoFragment = new VideoFragment();
		return videoFragment;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("VideoFragment");
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("VideoFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.you_ku_video_fragment_a, container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init();
		query();
//		add();
	}

	private void add(){
		mBean = new VideoBean();
		mBean.setAddTime("1466736095");
		mBean.setAddUserId("leezm");
		mBean.setgRole("艾莉莎");
		mBean.setgSeason("S3");
		mBean.setvDetails("完整高清视频，请到微信公众号“老斯基”观看");
		mBean.setvId("XMTYxNjc4MzU3Mg==");
		mBean.setvImgUrl("http://r2.ykimg.com/05420101576A5C396A0A4704E576DE33");
		mBean.setvLength("2132");
		mBean.setvLink("http://v.youku.com/v_show/id_XMTYxNjc4MzU3Mg==.html");
		mBean.setvPublishedUser("幻想曲资讯");
		mBean.setvTitle("你的飞机杯很值钱 做好保养省大钱"+CommonUtils.getRandom(0, 100));
		mBean.save(mActivity);
	}
	
	private void query(){
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.order("-createdAt");
		query.setLimit(1000);
		query.findObjects(mActivity, new FindListener<VideoBean>() {
			
			@Override
			public void onSuccess(List<VideoBean> list) {
				// TODO Auto-generated method stub
				mList = list;
				mAdapter = new VideoAdapter(mActivity, mList);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void init(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				//判断是否WIFI网络继续看视频
				if (CommonUtils.isWifi(mActivity)) {
					Intent intent = new Intent(mActivity,YouKuPlayerActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("vid", mList.get(arg2).getvId());
					startActivity(intent);
				} else {
					AlertDialogUtils.showDialog(mActivity, "当前为非WIFI网络环境，是否继续观看视频？", new OnClickYesListener() {
						@Override
						public void onClickYes() {
							Intent intent = new Intent(mActivity,YouKuPlayerActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							intent.putExtra("vid", mList.get(arg2).getvId());
							startActivity(intent);
						}
					}, new OnClickNoListener() {
						@Override
						public void onClickNo() {
						}
					});
				}
				
			}
		});
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public String getFragmentName() {
		return TAG;
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {

		default:
			break;
		}
	}

}
