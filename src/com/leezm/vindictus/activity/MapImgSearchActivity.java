package com.leezm.vindictus.activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.listener.FindListener;

import com.leezm.vindictus.adapter.MapImgSearchAdapter;
import com.leezm.vindictus.bean.MapImgBean;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MapImgSearchActivity extends Activity implements OnClickListener {

	private String edString;
	private BmobQuery<MapImgBean> query = new BmobQuery<MapImgBean>();
	private List<MapImgBean> list;
	private MapImgSearchAdapter mAdapter;
	private BitmapUtils bitmapUtils;
	private String keyString = "", urlString = "", titleString = "";
	public static int mPosition = 0;
	@ViewInject(R.id.map_img_ed)
	EditText editText;
	@ViewInject(R.id.map_img_search_listview)
	ListView listView;
	@ViewInject(R.id.map_img_search_view)
	ImageView imageView;

	// PopWindow
	private ImageView pSave, pImg;
	private PopupWindow popupWindow;
	private Display display;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_img_search);
		ViewUtils.inject(this);
		display = getWindowManager().getDefaultDisplay();
		init();
	}

	private void init() {
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultShowOriginal(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				bitmapUtils.display(imageView, list.get(arg2).getImgurl());
				mPosition = arg2;
				mAdapter.notifyDataSetChanged();
				urlString = list.get(arg2).getImgurl();
				keyString = list.get(arg2).getType();
				titleString = keyString + "：" + list.get(arg2).getName();
			}
		});

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showPopWindow(arg0);
			}
		});
	}

	private void showPopWindow(View mView) {
		View pView = LayoutInflater.from(this).inflate(
				R.layout.pop_show_map_img, null);
		pSave = (ImageView) pView.findViewById(R.id.pop_show_img_save);
		pImg = (ImageView) pView.findViewById(R.id.pop_show_img_view);

		bitmapUtils.display(pImg, urlString);

		pSave.setOnClickListener(this);
		pImg.setOnClickListener(this);

		popupWindow = new PopupWindow(pView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setWidth((int) (display.getWidth() * 0.85));
		popupWindow.setHeight((int) (display.getHeight() * 0.75));
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_pop));
		popupWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
	}

	@OnClick(R.id.map_img_gosearch)
	public void gosearch(View view) {
		edString = editText.getText().toString();
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.addWhereContains("name", edString);
		query.setLimit(1000);
		query.findObjects(this, new FindListener<MapImgBean>() {
			@Override
			public void onSuccess(List<MapImgBean> mList) {
				list = mList;
				mAdapter = new MapImgSearchAdapter(MapImgSearchActivity.this,
						list);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
				bitmapUtils.display(imageView, list.get(0).getImgurl());
				mPosition = 0;
				urlString = list.get(0).getImgurl();
				keyString = list.get(0).getType();
				titleString = keyString + "：" + list.get(0).getName();
			}

			@Override
			public void onError(int arg0, String arg1) {
				if (arg0 != 9009) {
					if (arg0==9015) {
						CommonUtils.showShortToast("没有该条数据，请重新输入", MapImgSearchActivity.this);
					}else {
					CommonUtils.showShortToast("加载数据失败：" + arg1 + "错误码：" + arg0,MapImgSearchActivity.this);
					}
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.pop_show_img_save:
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					// 将ImageView中的图片转换成Bitmap
					pImg.buildDrawingCache();
					bitmap = pImg.getDrawingCache();
					MediaStore.Images.Media.insertImage(
							MapImgSearchActivity.this.getContentResolver(),
							bitmap, titleString, "");
					CommonUtils.showShortToast("已保存到相册",
							MapImgSearchActivity.this);
				}
			});
			break;

		case R.id.pop_show_img_view:
			popupWindow.dismiss();
			break;

		default:
			break;
		}
	}

}
