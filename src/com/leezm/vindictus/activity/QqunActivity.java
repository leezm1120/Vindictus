package com.leezm.vindictus.activity;

import com.leezm.vindictus.adapter.QqunAdapter;
import com.leezm.vindictus.utils.AlertDialogUtils;
import com.leezm.vindictus.utils.CommonUtils;
import com.leezm.vindictus.utils.GdtAdConstants;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickNoListener;
import com.leezm.vindictus.utils.AlertDialogUtils.OnClickYesListener;
import com.leezm.vindictus.utils.LogUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import android.os.Bundle;
import android.app.Activity;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class QqunActivity extends Activity {

	@ViewInject(R.id.qqun_list_view)
	ListView listView;
	String[] strArrary = null;
	QqunAdapter mAdapter;
	//复制
	ClipboardManager clipboardManager;
	// GDTAD
	@ViewInject(R.id.qqun_activity_adview)
	ViewGroup adGroup;
	BannerView bannerView;
	private String advalue;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qqun);
		ViewUtils.inject(this);
		// GDTAD
		advalue = OnlineConfigAgent.getInstance().getConfigParams(this,
				"adturn");
		initgdtad();
		if (advalue.equals("0")) {
			adGroup.removeAllViews();
			bannerView.destroy();
			bannerView = null;
		} else {
			if (bannerView == null) {
				initgdtad();
			}
			bannerView.loadAD();
		}
		//复制
		clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		strArrary = OnlineConfigAgent.getInstance()
				.getConfigParams(this, "qqundetail").split("#");
		mAdapter = new QqunAdapter(this, strArrary);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//复制
				clipboardManager.setText(strArrary[arg2]);
				Toast.makeText(QqunActivity.this, "已复制", 0).show();
			}
		});
		AlertDialogUtils
				.showDialog(
						this,
						"提供QQ群是为了更好的结交朋友\n互帮互助，分享经验\n切勿泄露游戏账号密码\n切勿进行各种线下交易\n如果发生被骗被盗\n本APP概不负责\n点击确定代表你同意以上内容",
						new OnClickYesListener() {

							@Override
							public void onClickYes() {
								// TODO Auto-generated method stub

							}
						}, new OnClickNoListener() {

							@Override
							public void onClickNo() {
								// TODO Auto-generated method stub
								QqunActivity.this.finish();
							}
						});
		// System.err.println(strArrary);
		// System.err.println(strArrary.length);
	}

	// GDTAD
	private void initgdtad() {
		bannerView = new BannerView(this, ADSize.BANNER,
				GdtAdConstants.GDTAPPID, GdtAdConstants.GDTBANNERID);
		bannerView.setRefresh(30);
		bannerView.setShowClose(true);
		bannerView.setADListener(new AbstractBannerADListener() {

			@Override
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
			}
			
			@Override
			public void onADClicked() {
				super.onADClicked();
				MobclickAgent.onEvent(QqunActivity.this, "adonclick");
				CommonUtils.showShortToast("广告点击是你对作者的最大支持，O(∩_∩)O谢谢", QqunActivity.this);
			}

			@Override
			public void onADReceiv() {
				// TODO Auto-generated method stub
				LogUtils.i("AD_DEMO", "ONBannerReceive");
			}
		});
		adGroup.addView(bannerView);
		bannerView.loadAD();
	}
}
