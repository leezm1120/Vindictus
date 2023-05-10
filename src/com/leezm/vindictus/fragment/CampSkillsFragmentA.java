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

public class CampSkillsFragmentA extends BaseFragment implements
		OnClickListener {

	private static String TAG = "CampSkillsFragmentA";
	private Activity mActivity;
	public static final String ENCODING = "UTF-8";

	public static CampSkillsFragmentA newInstance() {
		CampSkillsFragmentA cA = new CampSkillsFragmentA();
		return cA;
	}

	private TextView tViewSkillTitle;
	private ImageView iViewAdd, iViewMinus;

	// 一变技能图标按钮↓
	@ViewInject(R.id.camp_gm1_view)
	RelativeLayout btgma1;
	@ViewInject(R.id.camp_gm2_view)
	RelativeLayout btgma2;
	@ViewInject(R.id.camp_gm3_view)
	RelativeLayout btgma3;
	@ViewInject(R.id.camp_gm4_view)
	RelativeLayout btgma4;
	@ViewInject(R.id.camp_gm5_view)
	RelativeLayout btgma5;
	@ViewInject(R.id.camp_gm6_view)
	RelativeLayout btgma6;
	@ViewInject(R.id.camp_gm7_view)
	RelativeLayout btgma7;
	@ViewInject(R.id.camp_gm8_view)
	RelativeLayout btgma8;
	@ViewInject(R.id.camp_gm9_view)
	RelativeLayout btgma9;
	@ViewInject(R.id.camp_gm10_view)
	RelativeLayout btgma10;
	// 一变技能文字↓
	@ViewInject(R.id.camp_gm1_txt)
	TextView tvgma1;
	@ViewInject(R.id.camp_gm2_txt)
	TextView tvgma2;
	@ViewInject(R.id.camp_gm3_txt)
	TextView tvgma3;
	@ViewInject(R.id.camp_gm4_txt)
	TextView tvgma4;
	@ViewInject(R.id.camp_gm5_txt)
	TextView tvgma5;
	@ViewInject(R.id.camp_gm6_txt)
	TextView tvgma6;
	@ViewInject(R.id.camp_gm7_txt)
	TextView tvgma7;
	@ViewInject(R.id.camp_gm8_txt)
	TextView tvgma8;
	@ViewInject(R.id.camp_gm9_txt)
	TextView tvgma9;
	@ViewInject(R.id.camp_gm10_txt)
	TextView tvgma10;
	// 二变技能图标按钮↓
	@ViewInject(R.id.camp_gm_1_view)
	RelativeLayout btgmb1;
	@ViewInject(R.id.camp_gm_2_view)
	RelativeLayout btgmb2;
	@ViewInject(R.id.camp_gm_3_view)
	RelativeLayout btgmb3;
	@ViewInject(R.id.camp_gm_4_view)
	RelativeLayout btgmb4;
	@ViewInject(R.id.camp_gm_5_view)
	RelativeLayout btgmb5;
	@ViewInject(R.id.camp_gm_6_view)
	RelativeLayout btgmb6;
	@ViewInject(R.id.camp_gm_7_view)
	RelativeLayout btgmb7;
	@ViewInject(R.id.camp_gm_8_view)
	RelativeLayout btgmb8;
	@ViewInject(R.id.camp_gm_9_view)
	RelativeLayout btgmb9;
	// 二变技能文字↓
	@ViewInject(R.id.camp_gm_1_txt)
	TextView tvgmb1;
	@ViewInject(R.id.camp_gm_2_txt)
	TextView tvgmb2;
	@ViewInject(R.id.camp_gm_3_txt)
	TextView tvgmb3;
	@ViewInject(R.id.camp_gm_4_txt)
	TextView tvgmb4;
	@ViewInject(R.id.camp_gm_5_txt)
	TextView tvgmb5;
	@ViewInject(R.id.camp_gm_6_txt)
	TextView tvgmb6;
	@ViewInject(R.id.camp_gm_7_txt)
	TextView tvgmb7;
	@ViewInject(R.id.camp_gm_8_txt)
	TextView tvgmb8;
	@ViewInject(R.id.camp_gm_9_txt)
	TextView tvgmb9;
	// 剩余技能点&清空按钮
	@ViewInject(R.id.camp_gm_syjnd_txt)
	TextView tvsyjnd;
	@ViewInject(R.id.bt_camp_gm_clean)
	RelativeLayout btclean;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("CampSkillsFragmentA");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("CampSkillsFragmentA");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.camp_skill_fragment_a, container,
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
		btgma1.setOnClickListener(this);
		btgma2.setOnClickListener(this);
		btgma3.setOnClickListener(this);
		btgma4.setOnClickListener(this);
		btgma5.setOnClickListener(this);
		btgma6.setOnClickListener(this);
		btgma7.setOnClickListener(this);
		btgma8.setOnClickListener(this);
		btgma9.setOnClickListener(this);
		btgma10.setOnClickListener(this);
		btgmb1.setOnClickListener(this);
		btgmb2.setOnClickListener(this);
		btgmb3.setOnClickListener(this);
		btgmb4.setOnClickListener(this);
		btgmb5.setOnClickListener(this);
		btgmb6.setOnClickListener(this);
		btgmb7.setOnClickListener(this);
		btgmb8.setOnClickListener(this);
		btgmb9.setOnClickListener(this);
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
			if (ia >=5) {
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
					ia = ia - 1;
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
		case R.id.camp_gm1_view:
			showPopUpWindow(btgma1, tvgma1, "gma1", "l1");
			break;
		case R.id.camp_gm2_view:
			showPopUpWindow(btgma2, tvgma2, "gma2", "l1");
			break;
		case R.id.camp_gm3_view:
			showPopUpWindow(btgma3, tvgma3, "gma3", "l1");
			break;
		case R.id.camp_gm4_view:
			showPopUpWindow(btgma4, tvgma4, "gma4", "l2");
			break;
		case R.id.camp_gm5_view:
			showPopUpWindow(btgma5, tvgma5, "gma5", "l2");
			break;
		case R.id.camp_gm6_view:
			showPopUpWindow(btgma6, tvgma6, "gma6", "l2");
			break;
		case R.id.camp_gm7_view:
			showPopUpWindow(btgma7, tvgma7, "gma7", "l3");
			break;
		case R.id.camp_gm8_view:
			showPopUpWindow(btgma8, tvgma8, "gma8", "l4");
			break;
		case R.id.camp_gm9_view:
			showPopUpWindow(btgma9, tvgma9, "gma9", "l4");
			break;
		case R.id.camp_gm10_view:
			showPopUpWindow(btgma10, tvgma10, "gma10", "l5");
			break;
		case R.id.camp_gm_1_view:
			showPopUpWindow(btgmb1, tvgmb1, "gmb1", "l6");
			break;
		case R.id.camp_gm_2_view:
			showPopUpWindow(btgmb2, tvgmb2, "gmb2", "l6");
			break;
		case R.id.camp_gm_3_view:
			showPopUpWindow(btgmb3, tvgmb3, "gmb3", "l6");
			break;
		case R.id.camp_gm_4_view:
			showPopUpWindow(btgmb4, tvgmb4, "gmb4", "l7");
			break;
		case R.id.camp_gm_5_view:
			showPopUpWindow(btgmb5, tvgmb5, "gmb5", "l7");
			break;
		case R.id.camp_gm_6_view:
			showPopUpWindow(btgmb6, tvgmb6, "gmb6", "l8");
			break;
		case R.id.camp_gm_7_view:
			showPopUpWindow(btgmb7, tvgmb7, "gmb7", "l9");
			break;
		case R.id.camp_gm_8_view:
			showPopUpWindow(btgmb8, tvgmb8, "gmb8", "l9");
			break;
		case R.id.camp_gm_9_view:
			showPopUpWindow(btgmb9, tvgmb9, "gmb9", "l10");
			break;
		case R.id.bt_camp_gm_clean:
			total = 40;
			ia = 0;
			tvsyjnd.setText("剩余技能点：" + total);
			tvgma1.setText("0/3");
			tvgma2.setText("0/3");
			tvgma3.setText("0/5");
			tvgma4.setText("0/5");
			tvgma5.setText("0/5");
			tvgma6.setText("0/5");
			tvgma7.setText("0/5");
			tvgma8.setText("0/3");
			tvgma9.setText("0/1");
			tvgma10.setText("0/1");
			tvgmb1.setText("0/3");
			tvgmb2.setText("0/3");
			tvgmb3.setText("0/5");
			tvgmb4.setText("0/3");
			tvgmb5.setText("0/1");
			tvgmb6.setText("0/5");
			tvgmb7.setText("0/3");
			tvgmb8.setText("0/5");
			tvgmb9.setText("0/1");
			break;
		default:
			break;
		}

	}

}
