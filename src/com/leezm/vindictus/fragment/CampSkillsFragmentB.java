package com.leezm.vindictus.fragment;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CampSkillsFragmentB extends BaseFragment implements
		OnClickListener {

	private static String TAG = "CampSkillsFragmentB";
	private Activity mActivity;
	public static final String ENCODING = "UTF-8";

	public static CampSkillsFragmentB newInstance() {
		CampSkillsFragmentB cB = new CampSkillsFragmentB();
		return cB;
	}

	private TextView tViewSkillTitle;
	private ImageView iViewAdd, iViewMinus;

	// 一变技能图标按钮↓
	@ViewInject(R.id.camp_ha1_view)
	RelativeLayout bthaa1;
	@ViewInject(R.id.camp_ha2_view)
	RelativeLayout bthaa2;
	@ViewInject(R.id.camp_ha3_view)
	RelativeLayout bthaa3;
	@ViewInject(R.id.camp_ha4_view)
	RelativeLayout bthaa4;
	@ViewInject(R.id.camp_ha5_view)
	RelativeLayout bthaa5;
	@ViewInject(R.id.camp_ha6_view)
	RelativeLayout bthaa6;
	@ViewInject(R.id.camp_ha7_view)
	RelativeLayout bthaa7;
	@ViewInject(R.id.camp_ha8_view)
	RelativeLayout bthaa8;
	@ViewInject(R.id.camp_ha9_view)
	RelativeLayout bthaa9;
	@ViewInject(R.id.camp_ha10_view)
	RelativeLayout bthaa10;
	// 一变技能文字↓
	@ViewInject(R.id.camp_ha1_txt)
	TextView tvhaa1;
	@ViewInject(R.id.camp_ha2_txt)
	TextView tvhaa2;
	@ViewInject(R.id.camp_ha3_txt)
	TextView tvhaa3;
	@ViewInject(R.id.camp_ha4_txt)
	TextView tvhaa4;
	@ViewInject(R.id.camp_ha5_txt)
	TextView tvhaa5;
	@ViewInject(R.id.camp_ha6_txt)
	TextView tvhaa6;
	@ViewInject(R.id.camp_ha7_txt)
	TextView tvhaa7;
	@ViewInject(R.id.camp_ha8_txt)
	TextView tvhaa8;
	@ViewInject(R.id.camp_ha9_txt)
	TextView tvhaa9;
	@ViewInject(R.id.camp_ha10_txt)
	TextView tvhaa10;
	// 二变技能图标按钮↓
	@ViewInject(R.id.camp_ha_1_view)
	RelativeLayout bthab1;
	@ViewInject(R.id.camp_ha_2_view)
	RelativeLayout bthab2;
	@ViewInject(R.id.camp_ha_3_view)
	RelativeLayout bthab3;
	@ViewInject(R.id.camp_ha_4_view)
	RelativeLayout bthab4;
	@ViewInject(R.id.camp_ha_5_view)
	RelativeLayout bthab5;
	@ViewInject(R.id.camp_ha_6_view)
	RelativeLayout bthab6;
	@ViewInject(R.id.camp_ha_7_view)
	RelativeLayout bthab7;
	@ViewInject(R.id.camp_ha_8_view)
	RelativeLayout bthab8;
	@ViewInject(R.id.camp_ha_9_view)
	RelativeLayout bthab9;
	// 二变技能文字↓
	@ViewInject(R.id.camp_ha_1_txt)
	TextView tvhab1;
	@ViewInject(R.id.camp_ha_2_txt)
	TextView tvhab2;
	@ViewInject(R.id.camp_ha_3_txt)
	TextView tvhab3;
	@ViewInject(R.id.camp_ha_4_txt)
	TextView tvhab4;
	@ViewInject(R.id.camp_ha_5_txt)
	TextView tvhab5;
	@ViewInject(R.id.camp_ha_6_txt)
	TextView tvhab6;
	@ViewInject(R.id.camp_ha_7_txt)
	TextView tvhab7;
	@ViewInject(R.id.camp_ha_8_txt)
	TextView tvhab8;
	@ViewInject(R.id.camp_ha_9_txt)
	TextView tvhab9;
	// 剩余技能点&清空按钮
	@ViewInject(R.id.camp_ha_syjnd_txt)
	TextView tvsyjnd;
	@ViewInject(R.id.bt_camp_ha_clean)
	RelativeLayout btclean;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("CampSkillsFragmentB");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("CampSkillsFragmentB");
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.camp_skill_fragment_b, container,
				false);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {
		bthaa1.setOnClickListener(this);
		bthaa2.setOnClickListener(this);
		bthaa3.setOnClickListener(this);
		bthaa4.setOnClickListener(this);
		bthaa5.setOnClickListener(this);
		bthaa6.setOnClickListener(this);
		bthaa7.setOnClickListener(this);
		bthaa8.setOnClickListener(this);
		bthaa9.setOnClickListener(this);
		bthaa10.setOnClickListener(this);
		bthab1.setOnClickListener(this);
		bthab2.setOnClickListener(this);
		bthab3.setOnClickListener(this);
		bthab4.setOnClickListener(this);
		bthab5.setOnClickListener(this);
		bthab6.setOnClickListener(this);
		bthab7.setOnClickListener(this);
		bthab8.setOnClickListener(this);
		bthab9.setOnClickListener(this);
		btclean.setOnClickListener(this);
	}

	private int dq, zg, total = 40, ia = 0;

	private void getjnsj(TextView tView) {
		dq = Integer.valueOf((String.valueOf(tView.getText().toString()
				.charAt(0))));
		zg = Integer.valueOf((String.valueOf(tView.getText().toString()
				.charAt(2))));
	}

	private boolean addhs(String lvsString) {
		if (lvsString.equals("l1")) {
			return true;
		} else if (lvsString.equals("l2")) {
			if (ia >= 5) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l3")) {
			if (ia >= 10) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l4")) {
			if (ia >= 15) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l5")) {
			if (ia >= 10) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l6")) {
			if (ia >= 20) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l7")) {
			if (ia >= 25) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l8")) {
			if (ia >= 30) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l9")) {
			if (ia >= 35) {
				return true;
			} else {
				return false;
			}
		} else if (lvsString.equals("l10")) {
			if (ia >= 40) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void showPopUpWindow(View view, final TextView textView,
			String skillurl, final String lString) {
		// PopUpWindow布局
		View pView = LayoutInflater.from(mActivity).inflate(
				R.layout.camp_skill_show_simulation, null);
		// 显示设置技能名字
		tViewSkillTitle = (TextView) pView
				.findViewById(R.id.camp_skill_show_name);
		tViewSkillTitle.setText(getFromAssets(skillurl));
		iViewAdd = (ImageView) pView.findViewById(R.id.camp_skill_show_add);
		iViewMinus = (ImageView) pView.findViewById(R.id.camp_skill_show_minus);
		iViewAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (addhs(lString)) {
					getjnsj(textView);
					if (dq < zg) {
						ia = ia + 1;
						textView.setText(dq + 1 + "/" + zg);
						total = total - 1;
						tvsyjnd.setText("剩余技能点：" + total);
					}
				}else {
					CommonUtils.showShortToast("上面技能点数不足", mActivity);
				}
			}
		});
		iViewMinus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getjnsj(textView);
				if (dq > 0) {
					ia = ia -1;
					textView.setText(dq - 1 + "/" + zg);
					total = total + 1;
					tvsyjnd.setText("剩余技能点：" + total);
				}
			}
		});

		PopupWindow popupWindow = new PopupWindow(pView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_pop));
		popupWindow.showAsDropDown(view);
	}

	// 从assets 文件夹中获取文件并读取数据
	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(
					"campskills/" + fileName + ".txt");
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		switch (mView.getId()) {
		case R.id.camp_ha1_view:
			showPopUpWindow(bthaa1, tvhaa1, "haa1", "l1");
			break;
		case R.id.camp_ha2_view:
			showPopUpWindow(bthaa2, tvhaa2, "haa2", "l1");
			break;
		case R.id.camp_ha3_view:
			showPopUpWindow(bthaa3, tvhaa3, "haa3", "l1");
			break;
		case R.id.camp_ha4_view:
			showPopUpWindow(bthaa4, tvhaa4, "haa4", "l2");
			break;
		case R.id.camp_ha5_view:
			showPopUpWindow(bthaa5, tvhaa5, "haa5", "l2");
			break;
		case R.id.camp_ha6_view:
			showPopUpWindow(bthaa6, tvhaa6, "haa6", "l2");
			break;
		case R.id.camp_ha7_view:
			showPopUpWindow(bthaa7, tvhaa7, "haa7", "l3");
			break;
		case R.id.camp_ha8_view:
			showPopUpWindow(bthaa8, tvhaa8, "haa8", "l4");
			break;
		case R.id.camp_ha9_view:
			showPopUpWindow(bthaa9, tvhaa9, "haa9", "l4");
			break;
		case R.id.camp_ha10_view:
			showPopUpWindow(bthaa10, tvhaa10, "haa10", "l5");
			break;
		case R.id.camp_ha_1_view:
			showPopUpWindow(bthab1, tvhab1, "hab1", "l6");
			break;
		case R.id.camp_ha_2_view:
			showPopUpWindow(bthab2, tvhab2, "hab2", "l6");
			break;
		case R.id.camp_ha_3_view:
			showPopUpWindow(bthab3, tvhab3, "hab3", "l6");
			break;
		case R.id.camp_ha_4_view:
			showPopUpWindow(bthab4, tvhab4, "hab4", "l7");
			break;
		case R.id.camp_ha_5_view:
			showPopUpWindow(bthab5, tvhab5, "hab5", "l7");
			break;
		case R.id.camp_ha_6_view:
			showPopUpWindow(bthab6, tvhab6, "hab6", "l8");
			break;
		case R.id.camp_ha_7_view:
			showPopUpWindow(bthab7, tvhab7, "hab7", "l9");
			break;
		case R.id.camp_ha_8_view:
			showPopUpWindow(bthab8, tvhab8, "hab8", "l9");
			break;
		case R.id.camp_ha_9_view:
			showPopUpWindow(bthab9, tvhab9, "hab9", "l10");
			break;
		case R.id.bt_camp_ha_clean:
			total = 40;
			ia = 0;
			tvsyjnd.setText("剩余技能点：" + total);
			tvhaa1.setText("0/3");
			tvhaa2.setText("0/3");
			tvhaa3.setText("0/5");
			tvhaa4.setText("0/5");
			tvhaa5.setText("0/5");
			tvhaa6.setText("0/5");
			tvhaa7.setText("0/5");
			tvhaa8.setText("0/3");
			tvhaa9.setText("0/1");
			tvhaa10.setText("0/1");
			tvhab1.setText("0/3");
			tvhab2.setText("0/3");
			tvhab3.setText("0/5");
			tvhab4.setText("0/3");
			tvhab5.setText("0/1");
			tvhab6.setText("0/5");
			tvhab7.setText("0/3");
			tvhab8.setText("0/5");
			tvhab9.setText("0/1");
			break;
		default:
			break;
		}

	}

}
