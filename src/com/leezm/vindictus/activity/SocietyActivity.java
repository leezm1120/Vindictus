package com.leezm.vindictus.activity;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SocietyActivity extends Activity {

	String aString,bString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society);
		
		RequestParams params = new RequestParams();
		params.addBodyParameter("channel","200");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "http://1.luoyingbaike.applinzi.com/interface/society.php", params,new RequestCallBack<String>() {

	        @Override
	        public void onStart() {
	        }

	        @Override
	        public void onLoading(long total, long current, boolean isUploading) {
	            if (isUploading) {
	            	
	            } else {
	            	
	            }
	        }

	        @Override
	        public void onSuccess(ResponseInfo<String> responseInfo) {
	        	System.err.println(responseInfo.result);
	        	try {
					JSONArray jsonArray = new JSONArray(new String(responseInfo.result));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject item = jsonArray.getJSONObject(i);
						int id = item.getInt("id");
						String server = item.getString("server");
						int channel = item.getInt("channel");
						String name = item.getString("name");
						System.err.println(id+server+channel+name);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }

	        @Override
	        public void onFailure(HttpException error, String msg) {
	        	
	        }
	});
		
		/**
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET,
			    "http://1.luoyingbaike.applinzi.com/interface/society.php?channel=200",
			    new RequestCallBack<String>(){
			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
			        }

			        @Override
			        public void onSuccess(ResponseInfo<String> responseInfo) {
			        	System.err.println(responseInfo.result);
			        	try {
							JSONArray jsonArray = new JSONArray(new String(responseInfo.result));
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject item = jsonArray.getJSONObject(i);
								int id = item.getInt("id");
								String server = item.getString("server");
								int channel = item.getInt("channel");
								String name = item.getString("name");
								System.err.println(id+server+channel+name);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }

			        @Override
			        public void onStart() {
			        }

			        @Override
			        public void onFailure(HttpException error, String msg) {
			        }
			});
		*/
	}


}
