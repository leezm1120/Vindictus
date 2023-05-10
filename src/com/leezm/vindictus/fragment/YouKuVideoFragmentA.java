package com.leezm.vindictus.fragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.activity.YouKuPlayerActivity;
import com.leezm.vindictus.adapter.VideoAdapter;
import com.leezm.vindictus.bean.VideoBean;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.LogUtils;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class YouKuVideoFragmentA extends BaseFragment implements
		OnClickListener {

	private static String TAG = "YouKuVideoFragmentA";
	private Activity mActivity;
	VideoAdapter mAdapter;
	BmobQuery<VideoBean> query = new BmobQuery<VideoBean>();
	BmobQuery<VideoBean> Gquery = new BmobQuery<VideoBean>();
	BmobQuery<VideoBean> Squery1 = new BmobQuery<VideoBean>();
	BmobQuery<VideoBean> Squery2 = new BmobQuery<VideoBean>();
	List<VideoBean> mList;
	VideoBean mBean;

	@ViewInject(R.id.video_fragment_listview)
	ListView listView;

	// 筛选相关
	private PopupWindow popupWindow;
	private TextView tViewQB,tViewS1, tViewS2, tViewS3, tViewLST, tViewFON, tViewYF,
			tViewKL, tViewKY, tViewWL, tViewHK, tViewL, tViewALS, tViewHJ,
			tViewDLY;
@ViewInject(R.id.youku_viewo_ly_select_text)
TextView textView;
	
	@OnClick(R.id.youku_viewo_ly_select)
	public void goSelect(View view) {
		showPopWindow(view);
	}

	//点击POP之后的处理
	private void goSelectSearch(String string){
		textView.setText(string);
		popupWindow.dismiss();
		if (string.equals("S1")|string.equals("S2")|string.equals("S3")) {
			Squery1.addWhereContains("gSeason", string);
			LogUtils.e("gSeason", string);
			Squery1.order("-createdAt");
			Squery1.setLimit(1000);
			Squery1.findObjects(mActivity, new FindListener<VideoBean>() {
				
				@Override
				public void onSuccess(List<VideoBean> list) {
					mList = list;
					mAdapter = new VideoAdapter(mActivity, mList);
					listView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
				
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
			});
		} else {
			Squery2.addWhereContains("gRole", string);
			LogUtils.e("gRole", string);
			Squery2.order("-createdAt");
			Squery2.setLimit(1000);
			Squery2.findObjects(mActivity, new FindListener<VideoBean>() {
				
				@Override
				public void onSuccess(List<VideoBean> list) {
					mList = list;
					mAdapter = new VideoAdapter(mActivity, mList);
					listView.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				}
				
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
			});
		}
		
	}
	
	private void showPopWindow(View view) {
		// PopUpWindow布局
		View pView = LayoutInflater.from(mActivity).inflate(
				R.layout.pop_video_select, null);
		// 相关组件
		tViewQB = (TextView) pView.findViewById(R.id.yk_pop_ly_qb);
		tViewS1 = (TextView) pView.findViewById(R.id.yk_pop_ly_s1);
		tViewS2 = (TextView) pView.findViewById(R.id.yk_pop_ly_s2);
		tViewS3 = (TextView) pView.findViewById(R.id.yk_pop_ly_s3);
		tViewLST = (TextView) pView.findViewById(R.id.yk_pop_ly_lisita);
		tViewFON = (TextView) pView.findViewById(R.id.yk_pop_ly_feiouna);
		tViewYF = (TextView) pView.findViewById(R.id.yk_pop_ly_yifei);
		tViewKL = (TextView) pView.findViewById(R.id.yk_pop_ly_kalu);
		tViewKY = (TextView) pView.findViewById(R.id.yk_pop_ly_kaiyi);
		tViewWL = (TextView) pView.findViewById(R.id.yk_pop_ly_weila);
		tViewHK = (TextView) pView.findViewById(R.id.yk_pop_ly_huoke);
		tViewL = (TextView) pView.findViewById(R.id.yk_pop_ly_lin);
		tViewALS = (TextView) pView.findViewById(R.id.yk_pop_ly_ailisha);
		tViewHJ = (TextView) pView.findViewById(R.id.yk_pop_ly_haiji);
		tViewDLY = (TextView) pView.findViewById(R.id.yk_pop_ly_dailiya);

		tViewQB.setOnClickListener(this);
		tViewS1.setOnClickListener(this);
		tViewS2.setOnClickListener(this);
		tViewS3.setOnClickListener(this);
		tViewLST.setOnClickListener(this);
		tViewFON.setOnClickListener(this);
		tViewYF.setOnClickListener(this);
		tViewKL.setOnClickListener(this);
		tViewKY.setOnClickListener(this);
		tViewWL.setOnClickListener(this);
		tViewHK.setOnClickListener(this);
		tViewL.setOnClickListener(this);
		tViewALS.setOnClickListener(this);
		tViewHJ.setOnClickListener(this);
		tViewDLY.setOnClickListener(this);

		popupWindow = new PopupWindow(pView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_pop));
		popupWindow.showAsDropDown(view);
	}

	// 搜索相关
	@ViewInject(R.id.youku_viewo_ly_search_ed)
	EditText editText;

	@OnClick(R.id.youku_viewo_ly_search)
	public void goSearch(View view) {
		String info = editText.getText().toString();
		Gquery.addWhereContains("vTitle", info);
		Gquery.setLimit(1000);
		Gquery.order("-createdAt");
		Gquery.findObjects(mActivity, new FindListener<VideoBean>() {

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
			public void onSuccess(List<VideoBean> list) {
				mList = list;
				mAdapter = new VideoAdapter(mActivity, mList);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	public static YouKuVideoFragmentA newInstance() {
		YouKuVideoFragmentA simulationFragment = new YouKuVideoFragmentA();
		return simulationFragment;

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
		View view = inflater.inflate(R.layout.you_ku_video_fragment_a,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		init();
		queryAll();
	}

	private void queryAll() {
		textView.setText("全部");
		query.order("-createdAt");
		query.setLimit(1000);
		query.findObjects(mActivity, new FindListener<VideoBean>() {

			@Override
			public void onSuccess(List<VideoBean> list) {
				mList = null;
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

	private void init() {
		// 默认显示全部
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
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

	@Override
	public void onClick(View mView) {
		switch (mView.getId()) {
		case R.id.yk_pop_ly_s1:
			goSelectSearch("S1");
			break;
		case R.id.yk_pop_ly_s2:
			goSelectSearch("S2");
			break;
		case R.id.yk_pop_ly_s3:
			goSelectSearch("S3");
			break;
		case R.id.yk_pop_ly_qb:
			popupWindow.dismiss();
			queryAll();
			break;
		case R.id.yk_pop_ly_lisita:
			goSelectSearch("利斯塔");
			break;
		case R.id.yk_pop_ly_feiouna:
			goSelectSearch("菲欧娜");
			break;
		case R.id.yk_pop_ly_yifei:
			goSelectSearch("伊菲");
			break;
		case R.id.yk_pop_ly_kalu:
			goSelectSearch("卡鲁");
			break;
		case R.id.yk_pop_ly_kaiyi:
			goSelectSearch("凯伊");
			break;
		case R.id.yk_pop_ly_weila:
			goSelectSearch("维拉");
			break;
		case R.id.yk_pop_ly_huoke:
			goSelectSearch("霍克");
			break;
		case R.id.yk_pop_ly_lin:
			goSelectSearch("琳");
			break;
		case R.id.yk_pop_ly_ailisha:
			goSelectSearch("艾莉莎");
			break;
		case R.id.yk_pop_ly_haiji:
			goSelectSearch("海基");
			break;
		case R.id.yk_pop_ly_dailiya:
			goSelectSearch("黛莉娅");
			break;
		default:
			break;
		}
	}

}
