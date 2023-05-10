package com.leezm.vindictus.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.leezm.vindictus.bean.BlackListBean;
import com.leezm.vindictus.bean.OtherVideoBean;
import com.leezm.vindictus.bean.VideoBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddVideoActivity extends Activity implements OnClickListener {

	@ViewInject(R.id.video_add_edText)
	EditText editText;
	@ViewInject(R.id.video_add_video_search)
	TextView btSearch;
	@ViewInject(R.id.video_add_video_info)
	TextView vInfo;
	@ViewInject(R.id.video_add_video_goadd)
	TextView btGoAdd;
	@ViewInject(R.id.video_add_video_gameinfo)
	LinearLayout layout;
	@ViewInject(R.id.video_add_video_gameinfo_season)
	Spinner spinnerSeason;
	@ViewInject(R.id.video_add_video_gameinfo_role)
	Spinner spinnerRole;
	
	private HttpUtils httpUtils;
	private String vid="",jianjie="",imgUrl="",vLink="",vName="",vTitle="",vTime="";
	private VideoBean mBean;
	private OtherVideoBean oBean;
	
	private boolean isLYVideo;
	
	//黑名单相关
		private BmobQuery<BlackListBean> queryBlack = new BmobQuery<BlackListBean>();
		private BmobQuery<VideoBean> queryVideo = new BmobQuery<VideoBean>();
		private BmobQuery<OtherVideoBean> queryOther = new BmobQuery<OtherVideoBean>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_video);
		ViewUtils.inject(this);
		init();
	}

	
	private void init(){
		isLYVideo = getIntent().getBooleanExtra("isLYVideo", true);
		LogUtils.e("isLYVideo", isLYVideo+"");
		btSearch.setOnClickListener(this);
		btGoAdd.setOnClickListener(this);
		btGoAdd.setVisibility(View.GONE);
		layout.setVisibility(View.GONE);
	}

	private void getYouKuVideoInfo(){
		httpUtils = new HttpUtils();
		LogUtils.e("getYouKuUrl", "https://openapi.youku.com/v2/videos/show_basic.json?client_id=be6d8ffde043b496&video_url="+editText.getText().toString());
		httpUtils.send(HttpMethod.GET, "https://openapi.youku.com/v2/videos/show_basic.json?client_id=be6d8ffde043b496&video_url="+editText.getText().toString(), new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String result) {
				LogUtils.e("获取优酷视频信息失败：", result);
				vInfo.setText("获取优酷视频信息失败！错误信息："+result);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				LogUtils.e("获取优酷视频信息成功：", responseInfo.result);
				String user = "";
				try {
					JSONObject jsonObject = new JSONObject(responseInfo.result);
					user = jsonObject.getString("user");
					JSONObject jsonObject2 = new JSONObject(user);
					jianjie = jsonObject.getString("description");
					vid = jsonObject.getString("id");
					imgUrl = jsonObject.getString("thumbnail");
					vLink = jsonObject.getString("link");
					vName =jsonObject2.getString("name");
					vTitle = jsonObject.getString("title");
					vTime = String.valueOf(jsonObject.getInt("duration"));
					if (jianjie.length()>60) {
						jianjie = jianjie.substring(0,59)+"...";
					}
					if (vTitle.length()>35) {
						vTitle = vTitle.substring(0,34)+"...";
					}
					vInfo.setText("视频标题："+jsonObject.getString("title")+"\n视频简介："+jianjie+"\n视频作者:"+jsonObject2.getString("name")+"\n播放次数："+jsonObject.getString("view_count"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (user.equals("")) {
					return;
				}
				btGoAdd.setVisibility(View.VISIBLE);
				if (isLYVideo) {
					layout.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	private void goAddVideo(){
		//先查找是否已有！
		if (isLYVideo) {
			queryVideo.addWhereEqualTo("vId", vid);
			queryVideo.findObjects(this, new FindListener<VideoBean>() {
				@Override
				public void onError(int arg0, String arg1) {
					CommonUtils.showShortToast("数据库连接失败！code:"+arg0+",msg："+arg1,
							AddVideoActivity.this);
				}

				@Override
				public void onSuccess(List<VideoBean> list) {
					if (list.size()!=0) {
						vInfo.setText("该视频已添加过啦！");
						editText.setText("");
						btGoAdd.setVisibility(View.GONE);
					}else {
							mBean = new VideoBean();
							mBean.setAddTime(String.valueOf(System.currentTimeMillis()));
							mBean.setAddUserId(CommonUtils.getDriverId(AddVideoActivity.this));
							String role = spinnerRole.getSelectedItem().toString();
							if (role.equals("全部")) {
								role = "黛莉娅，海基，艾莉莎，琳，霍克，维拉，凯伊，卡鲁，伊菲，菲欧娜，利斯塔";
							}
							mBean.setgRole(role);
							mBean.setgSeason(spinnerSeason.getSelectedItem().toString());
							mBean.setvDetails(jianjie);
							mBean.setvId(vid);
							mBean.setvImgUrl(imgUrl);
							mBean.setvLink(vLink);
							mBean.setvPublishedUser(vName);
							mBean.setvTitle(vTitle);
							mBean.setvLength(vTime);
							mBean.save(AddVideoActivity.this, new SaveListener() {
								
								@Override
								public void onSuccess() {
									// TODO Auto-generated method stub
									vInfo.setText("添加成功！");
									btGoAdd.setVisibility(View.GONE);
									editText.setText("");
									btGoAdd.setVisibility(View.GONE);
									if (isLYVideo) {
										layout.setVisibility(View.GONE);
									}
								}
								
								@Override
								public void onFailure(int arg0, String arg1) {
									// TODO Auto-generated method stub
									vInfo.setText("添加失败！code："+arg0+",msg:"+arg1);
								}
							});
						
						
					}
				}
			});
		}else {
			queryOther.addWhereEqualTo("vId", vid);
			queryOther.findObjects(AddVideoActivity.this, new FindListener<OtherVideoBean>() {

				@Override
				public void onError(int arg0, String arg1) {
					CommonUtils.showShortToast("数据库连接失败！code:"+arg0+",msg："+arg1,
							AddVideoActivity.this);					
				}

				@Override
				public void onSuccess(List<OtherVideoBean> list) {
					if (list.size()!=0) {
						vInfo.setText("该视频已添加过啦！");
						editText.setText("");
						btGoAdd.setVisibility(View.GONE);
					}else {
					//查找其他视频
					oBean = new OtherVideoBean();
					oBean.setAddTime(String.valueOf(System.currentTimeMillis()));
					oBean.setAddUserId(CommonUtils.getDriverId(AddVideoActivity.this));
					oBean.setvDetails(jianjie);
					oBean.setvId(vid);
					oBean.setvImgUrl(imgUrl);
					oBean.setvLink(vLink);
					oBean.setvPublishedUser(vName);
					oBean.setvTitle(vTitle);
					oBean.setvLength(vTime);
					oBean.save(AddVideoActivity.this, new SaveListener() {
						
						@Override
						public void onSuccess() {
							vInfo.setText("添加成功！");
							btGoAdd.setVisibility(View.GONE);
							editText.setText("");
							btGoAdd.setVisibility(View.GONE);
							if (isLYVideo) {
								layout.setVisibility(View.GONE);
							}
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							vInfo.setText("添加失败！code："+arg0+",msg:"+arg1);
						}
					});
				}}
			});
			
			
			
		}
		

	}
	

	
	@Override
	public void onClick(View mView) {
		switch (mView.getId()) {
		case R.id.video_add_video_search:
			if (editText.getText().length()==0) {
				CommonUtils.showShortToast("链接地址不能为空哦！", this);
				return;
			}
			getYouKuVideoInfo();
			btGoAdd.setVisibility(View.GONE);
			if (isLYVideo) {
				layout.setVisibility(View.GONE);
			}
			
			break;
case R.id.video_add_video_goadd:
	queryBlack.addWhereEqualTo("userId",
			CommonUtils.getDriverId(this));
	queryBlack.findObjects(this, new FindListener<BlackListBean>() {

		@Override
		public void onError(int arg0, String arg1) {
			CommonUtils.showShortToast("数据库连接失败！code:"+arg0+",msg："+arg1,
					AddVideoActivity.this);
		}

		@Override
		public void onSuccess(List<BlackListBean> list) {
			if (list.size() == 1) {
				CommonUtils.showShortToast("你由于发布违规信息已被加入黑名单！",
						AddVideoActivity.this);
			}else {
				goAddVideo();
			}
		}
	});
			break;
			
		default:
			break;
		}
	}
	
}
