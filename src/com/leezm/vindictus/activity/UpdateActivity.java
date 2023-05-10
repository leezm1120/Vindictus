package com.leezm.vindictus.activity;

import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class UpdateActivity extends Activity {
	@ViewInject(R.id.text_update_rizhi)
	TextView textView;
	@ViewInject(R.id.update_go)
	RelativeLayout rLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		ViewUtils.inject(this);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case UpdateStatus.Yes: // has update
					UmengUpdateAgent.showUpdateDialog(UpdateActivity.this,
							updateInfo);
					break;
				case UpdateStatus.No: // has no update
					Toast.makeText(UpdateActivity.this, "当前已经是最新版本",
							Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.NoneWifi: // none wifi
					UmengUpdateAgent.showUpdateDialog(UpdateActivity.this,
							updateInfo);
					Toast.makeText(UpdateActivity.this, "你没有连接上WIFI网络，确定要更新吗？",
							Toast.LENGTH_SHORT).show();
					break;
				case UpdateStatus.Timeout: // time out
					Toast.makeText(UpdateActivity.this, "连接超时",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}

		});
		String txt = OnlineConfigAgent.getInstance().getConfigParams(this,
				"updatelog");
		textView.setText(txt);
		rLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				UmengUpdateAgent.forceUpdate(UpdateActivity.this);
				Toast.makeText(
						UpdateActivity.this,
						"当前版本号："
								+ CommonUtils
										.getVersionName(UpdateActivity.this)
								+ "  正在检查更新...", 1).show();

			}
		});
	}

}
