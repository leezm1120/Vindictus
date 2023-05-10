package com.leezm.vindictus.fragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.YouKuPlayerActivity;
import com.leezm.vindictus.adapter.OtherVideoAdapter;
import com.leezm.vindictus.bean.OtherVideoBean;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class YouKuVideoFragmentB extends BaseFragment{

	private static String TAG = "YouKuVideoFragmentB";
	private Activity mActivity;
	private OtherVideoAdapter mAdapter;
	private List<OtherVideoBean> mList;
	
	@ViewInject(R.id.video_fragment_listview_b)
	ListView listView;
	@ViewInject(R.id.youku_viewo_other_search_ed)
	EditText editText;
	@OnClick(R.id.youku_viewo_other_search)
	public void goSearch(View view){
		String info = editText.getText().toString();
		Gquery.addWhereContains("vTitle", info);
		Gquery.setLimit(1000);
		Gquery.order("-createdAt");
		Gquery.findObjects(mActivity, new FindListener<OtherVideoBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				if (arg0 != 9009) {
					if (arg0 == 9015) {
						CommonUtils.showShortToast("没有该条数据，请重新输入", mActivity);
					} else {
						CommonUtils.showShortToast("加载数据失败：" + arg1 + "错误码："
								+ arg0, mActivity);
					}
				}
			}

			@Override
			public void onSuccess(List<OtherVideoBean> list) {
				mAdapter = new OtherVideoAdapter(mActivity, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
		});
	}
	
	//查询全部
	BmobQuery<OtherVideoBean> query = new BmobQuery<OtherVideoBean>();
	BmobQuery<OtherVideoBean> Gquery = new BmobQuery<OtherVideoBean>();
	
	public static YouKuVideoFragmentB newInstance(){
		YouKuVideoFragmentB yFragmentB = new YouKuVideoFragmentB();
		return yFragmentB;
		
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
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
		View view = inflater.inflate(R.layout.you_ku_video_fragment_b, container,
				false);
		ViewUtils.inject(this,view);
		return view;
		}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		init();
		queryAll();
	}
	
	private void init(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// 判断是否WIFI网络继续看视频
				if (CommonUtils.isWifi(mActivity)) {
					Intent intent = new Intent(mActivity,
							YouKuPlayerActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("vid", mList.get(arg2).getvId());
					startActivity(intent);
				} else {
					AlertDialogUtils.showDialog(mActivity,
							"当前为非WIFI网络环境，是否继续观看视频？", new OnClickYesListener() {
								@Override
								public void onClickYes() {
									Intent intent = new Intent(mActivity,
											YouKuPlayerActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									intent.putExtra("vid", mList.get(arg2)
											.getvId());
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
	
	//查询全部非洛英视频
	private void queryAll(){
		query.order("-createdAt");
		query.setLimit(1000);
		query.findObjects(mActivity, new FindListener<OtherVideoBean>() {

			@Override
			public void onSuccess(List<OtherVideoBean> list) {
				mList = null;
				mList = list;
				mAdapter = new OtherVideoAdapter(mActivity, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
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
