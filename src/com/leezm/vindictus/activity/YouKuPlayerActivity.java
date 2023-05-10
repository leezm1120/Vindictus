package com.leezm.vindictus.activity;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.leezm.vindictus.adapter.VideoCommentsAdapter;
import com.leezm.vindictus.bean.BlackListBean;
import com.leezm.vindictus.bean.VideoCommentsBean;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;
import com.youku.player.plugin.YoukuPlayerListener;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.OnCreateDownloadListener;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class YouKuPlayerActivity extends Activity implements OnClickListener {

	// 播放器Manager
	private YoukuBasePlayerManager PlayerManager;

	// 播放器控件
	YoukuPlayerView mPlayerView;

	// 需要播放的视频id
	private String vid;

	// 标示是否播放的本地视频
	private boolean isFromLocal = false;

	// 需要播放的本地视频的id
		private String local_vid;
	
	// YoukuPlayer实例，进行视频播放控制
	private YoukuPlayer youkuPlayer;

	// 自定义控件
	@ViewInject(R.id.video_player_view_write)
	private ImageView btComments;
	@ViewInject(R.id.video_player_view_down)
	private ImageView btDownd;
	@ViewInject(R.id.video_player_view_share)
	private ImageView btShare;
	@ViewInject(R.id.video_player_view_open)
	private ImageView btOpenUrl;
	@ViewInject(R.id.video_info)
	private TextView vInfo;
	@ViewInject(R.id.video_comments)
	private ListView listView;

	private HttpUtils httpUtils;

	// 视频标题
	private String vTitle = "";

	// PopWindow 相关组件
	private EditText editText;
	private TextView tv1, tv2;
	private PopupWindow popupWindow;
	private Display display;

	// 评论相关
	private VideoCommentsBean mBean;
	private BmobQuery<BlackListBean> queryBlack = new BmobQuery<BlackListBean>();
	private BmobQuery<VideoCommentsBean> queryComments = new BmobQuery<VideoCommentsBean>();
	private SharedPreferences preferences;
	private Editor editor;
	private long lastAddTime;
	Long timeNow;
	private VideoCommentsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_ku_player);
		ViewUtils.inject(this);
		display = getWindowManager().getDefaultDisplay();
		initYouKu();
		initView();
		initCommentsTime();
		initCommentsList();
	}

	private void initCommentsList() {
		queryComments.order("-createdAt");
		queryComments.addWhereEqualTo("vId", vid);
		queryComments.findObjects(this, new FindListener<VideoCommentsBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<VideoCommentsBean> list) {
				// TODO Auto-generated method stub
				LogUtils.e("VideoCommentsBeanList", list.toString());
				mAdapter = new VideoCommentsAdapter(YouKuPlayerActivity.this, list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	private void getVideoInfo(){
		httpUtils = new HttpUtils();
		httpUtils
				.send(HttpMethod.GET,
						"https://openapi.youku.com/v2/videos/show_basic.json?client_id=be6d8ffde043b496&video_id="
								+ vid, new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException codException,
									String result) {
								// TODO Auto-generated method stub
								LogUtils.e("获取优酷视频信息失败：", result);
								vInfo.setText("***\n获取优酷视频信息失败！点击重试！\n***");
							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								// TODO Auto-generated method stub
								LogUtils.e("获取优酷视频信息成功：", responseInfo.result);
								String user = "";
								try {
									JSONObject jsonObject = new JSONObject(
											responseInfo.result);
									vTitle = jsonObject.getString("title");
									user = jsonObject.getString("user");
									JSONObject jsonObject2 = new JSONObject(
											user);
									LogUtils.d("vTitle",
											jsonObject.getString("title"));
									LogUtils.d("user",
											jsonObject.getString("user"));
									LogUtils.d("name",
											jsonObject2.getString("name"));
									LogUtils.d("view_count",
											jsonObject.getString("view_count"));
									LogUtils.d("published",
											jsonObject.getString("published"));
									LogUtils.d("title", jsonObject.getString("title"));
									String jianjie =  jsonObject.getString("description");
									if (jianjie.length()>55) {
										jianjie = jianjie.substring(0,54)+"...";
									}
									if (vTitle.length()>31) {
										vTitle = vTitle.substring(0,29)+"...";
									}
									vInfo.setText("视频标题："+jsonObject.getString("title")+"\n视频简介："+jianjie+"\n视频作者："+ jsonObject2.getString("name")+ "\n播放次数："+ jsonObject.getString("view_count")+ "\n上传日期："+ jsonObject.getString("published"));
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
	}
	
	private void initView() {
		vInfo.setOnClickListener(this);
		btComments.setOnClickListener(this);
		btDownd.setOnClickListener(this);
		btOpenUrl.setOnClickListener(this);
		btShare.setOnClickListener(this);
		getVideoInfo();
	}

	private void initYouKu() {
		PlayerManager = new YoukuBasePlayerManager(this) {

			@Override
			public void setPadHorizontalLayout() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSmallscreenListener() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onInitializationSuccess(YoukuPlayer player) {
				// TODO Auto-generated method stub
				// 初始化成功后需要添加该行代码
				addPlugins();

				// 实例化YoukuPlayer实例
				youkuPlayer = player;

				// 进行播放
				goPlay();
			}

			@Override
			public void onFullscreenListener() {
				// TODO Auto-generated method stub

			}
		};
		PlayerManager.onCreate();

		// 通过上个页面传递过来的Intent获取播放参数
		getIntentData(getIntent());

		// 播放器控件
		mPlayerView = (YoukuPlayerView) this.findViewById(R.id.ykplayer);
		// 控制竖屏和全屏时候的布局参数。这两句必填。
		mPlayerView.setSmallScreenLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		mPlayerView.setFullScreenLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		// 初始化播放器相关数据
		mPlayerView.initialize(PlayerManager);

		// 添加播放器的回调
		PlayerManager.setPlayerListener(new YoukuPlayerListener() {
			@Override
			public void onCompletion() {
				// TODO Auto-generated method stub
				super.onCompletion();
			}

			@Override
			public void onStartBuffering() {
				// TODO Auto-generated method stub
				super.onStartBuffering();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

		// 通过Intent获取播放需要的相关参数
		getIntentData(intent);

		// 进行播放
		goPlay();
	}

	/**
	 * 获取上个页面传递过来的数据
	 */
	private void getIntentData(Intent intent) {
		if (intent != null) {
			// 判断是不是本地视频
			isFromLocal = intent.getBooleanExtra("isFromLocal", false);
			if (isFromLocal) { // 播放本地视频
				local_vid = intent.getStringExtra("local_vid");
			} else { // 在线播放
				vid = intent.getStringExtra("vid");
			}
		}

	}

	private void goPlay() {
		if (isFromLocal) { // 播放本地视频
			youkuPlayer.playLocalVideo(local_vid);
			LogUtils.e("local_vid", local_vid);
		} else { // 播放在线视频
			youkuPlayer.playVideo(vid);
			LogUtils.e("vid", vid);
		}
	}

	@Override
	public void onBackPressed() { // android系统调用
		LogUtils.e("sgh", "onBackPressed before super");
		super.onBackPressed();
		LogUtils.e("sgh", "onBackPressed");
		PlayerManager.onBackPressed();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		PlayerManager.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PlayerManager.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean managerKeyDown = PlayerManager.onKeyDown(keyCode, event);
		if (PlayerManager.shouldCallSuperKeyDown()) {
			return super.onKeyDown(keyCode, event);
		} else {
			return managerKeyDown;
		}

	}

	@Override
	public void onLowMemory() { // android系统调用
		super.onLowMemory();
		PlayerManager.onLowMemory();
	}

	@Override
	protected void onPause() {
		super.onPause();
		PlayerManager.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		PlayerManager.onResume();
	}

	@Override
	public boolean onSearchRequested() { // android系统调用
		return PlayerManager.onSearchRequested();
	}

	@Override
	protected void onStart() {
		super.onStart();
		PlayerManager.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		PlayerManager.onStop();
	}

	@Override
	public void onClick(View mView) {
		// TODO Auto-generated method stub
		switch (mView.getId()) {
		case R.id.video_player_view_down:
			if (CommonUtils.isWifi(this)) {
				downLoadVideo();
			} else {
				AlertDialogUtils.showDialog(this, "当前为非WIFI网络环境，是否继续下载视频？",
						new OnClickYesListener() {

							@Override
							public void onClickYes() {
								// TODO Auto-generated method stub
								downLoadVideo();
							}
						}, new OnClickNoListener() {

							@Override
							public void onClickNo() {
								// TODO Auto-generated method stub

							}
						});
			}

			break;
		case R.id.video_player_view_open:
			Uri uri = Uri
					.parse("http://v.youku.com/v_show/id_" + vid + ".html");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			break;
		case R.id.video_info:
			getVideoInfo();
			break;
		case R.id.video_player_view_share:
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, vTitle
					+ "：http://v.youku.com/v_show/id_" + vid + ".html");
			shareIntent.setType("text/plain");
			// 设置分享列表的标题，并且每次都显示分享列表
			startActivity(Intent.createChooser(shareIntent, vTitle));
			break;
		case R.id.video_player_view_write:
			showPopWindow(btComments);
			break;
		case R.id.pop_enchant_cancle:
			popupWindow.dismiss();
			break;
		case R.id.pop_enchant_save:
			if (editText.getText().length() == 0) {
				CommonUtils.showShortToast("不能为空", YouKuPlayerActivity.this);
				return;
			}
			popupWindow.dismiss();
			LogUtils.e("在这请求数据库评论添加", "先判断间隔，再判断是否黑名单");
			// BlackListBean bean = new BlackListBean();
			// bean.setUserId("a1be781c-df56-403a-b5e5-b07a96651ef6");
			// bean.save(this);
			timeNow = new Date().getTime();
			if ((timeNow - lastAddTime) > 60) {
				// 如果提交间隔超过60秒就可再次提交
				// 判断是否为黑名单
				final String aString = CommonUtils.getDriverId(this);
				LogUtils.e("设备的ID是：", aString);
				queryBlack.addWhereEqualTo("userId",
						CommonUtils.getDriverId(this));
				queryBlack.findObjects(this, new FindListener<BlackListBean>() {

					@Override
					public void onSuccess(List<BlackListBean> list) {
						editor.putLong("lastAddTime", timeNow);
						editor.commit();
						lastAddTime = timeNow;

						LogUtils.e("list", list.size() + "");
						if (list.size() == 1) {
							CommonUtils.showShortToast("你被加入黑名单！",
									YouKuPlayerActivity.this);
						} else {
							// 这里正式添加评论到数据库
							mBean = new VideoCommentsBean();
							mBean.setAddTime(String.valueOf(System
									.currentTimeMillis()));
							mBean.setAddUserId(aString);
							mBean.setComments(editText.getText().toString());
							mBean.setvId(vid);
							mBean.save(YouKuPlayerActivity.this,new SaveListener() {
								
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									CommonUtils.showShortToast("评论已发布！",
											YouKuPlayerActivity.this);
								}
								
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									CommonUtils.showShortToast("评论发布失败：code:"+arg0+"msg："+arg1,
											YouKuPlayerActivity.this);
								}
							});
							
						}
					}

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						CommonUtils.showShortToast("数据库连接失败！code:"+arg0+",msg："+arg1,
								YouKuPlayerActivity.this);
					}
				});
			} else {
				CommonUtils.showShortToast("1分钟内只能发表一次评论哦！", this);
			}
			break;
		default:
			break;
		}
	}

	private void initCommentsTime() {
		preferences = getSharedPreferences("videocomments", MODE_PRIVATE);
		editor = preferences.edit();
		lastAddTime = preferences.getLong("lastAddTime", 0);
	}

	private void downLoadVideo() {
		// 通过DownloadManager类实现视频下载
		DownloadManager d = DownloadManager.getInstance();
		/**
		 * 第一个参数为需要下载的视频id 第二个参数为该视频的标题title 第三个对下载视频结束的监听，可以为空null
		 */
		d.createDownload(vid, vTitle, new OnCreateDownloadListener() {
			@Override
			public void onfinish(boolean isNeedRefresh) {

			}
		});
	}

	private void showPopWindow(View view) {
		View pView = LayoutInflater.from(this).inflate(R.layout.pop_enchant,
				null);
		editText = (EditText) pView.findViewById(R.id.pop_enchant_ed);
		editText.setHint("请在此输入你的评论！");
		tv1 = (TextView) pView.findViewById(R.id.pop_enchant_cancle);
		tv2 = (TextView) pView.findViewById(R.id.pop_enchant_save);
		tv2.setText("提交");
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		popupWindow = new PopupWindow(pView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth((int) (display.getWidth() * 0.75));
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_pop));
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}

}
