package com.leezm.vindictus.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Random;
import java.util.UUID;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.leezm.vindictus.activity.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 通用工具类
 * 
 * @author dewyze
 * 
 */
public class CommonUtils {

	private static final String TAG = "CommonUtils";

	/**
	 * 刷新相册
	 */
	public static void reFreshPhoto(Activity context) {
		int version = android.os.Build.VERSION.SDK_INT;
		if (version >= 19) {
			MediaScannerConnection.scanFile(
					context,
					new String[] { Environment
							.getExternalStoragePublicDirectory(
									Environment.DIRECTORY_DCIM).getPath()
							+ "/" }, null, null);
		} else {
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
					.parse("file://"
							+ Environment.getExternalStorageDirectory())));
		}
	}

	/**
	 * 生成随机数
	 * 
	 * @return
	 * 
	 * */
	public static int getRandom(int min, int max) {
		Random random = new Random();
		int a = random.nextInt(max - min + 1) + min;
		return a;
	}

	/**
	 * 显示提示
	 * 
	 * */
	public static void showShortToast(String string, Activity context) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(String string, Activity context) {
		Toast.makeText(context, string, Toast.LENGTH_LONG).show();
	}

	/**
	 * 开启activity
	 */
	public static void launchActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	}

	public static void launchActivityForResult(Activity context,
			Class<?> activity, int requestCode) {
		Intent intent = new Intent(context, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, requestCode);
	}

	/**
	 * 带传值和返回值的开启activity
	 * */
	public static void launchActivityForResultPut(Activity context,
			Class<?> activity, int requestCode, String key, String value) {
		Intent intent = new Intent(context, activity);
		intent.putExtra(key, value);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, requestCode);
	}

	/**
	 * 从fragment带传值和返回值的开启activity
	 * */
	public static void launchActivityForResultPutfromfragment(Activity context,
			Class<?> activity, int requestCode, String key, String value) {
		Intent intent = new Intent(context, activity);
		intent.putExtra(key, value);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivityForResult(intent, context.RESULT_FIRST_USER);
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftKeybord(Activity activity) {

		if (null == activity) {
			return;
		}
		try {
			final View v = activity.getWindow().peekDecorView();
			if (v != null && v.getWindowToken() != null) {
				InputMethodManager imm = (InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 判断是否为合法的json
	 * 
	 * @param jsonContent
	 *            需判断的字串
	 */
	public static boolean isJsonFormat(String jsonContent) {
		try {
			new JsonParser().parse(jsonContent);
			return true;
		} catch (JsonParseException e) {
			LogUtils.i(TAG, "bad json: " + jsonContent);
			return false;
		}
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param text
	 * @return true null false !null
	 */
	public static boolean isNull(String text) {
		if (text == null || "".equals(text.trim()) || "null".equals(text))
			return true;
		return false;
	}

	/**
	 * 抖动动画
	 */
	public static void startShakeAnim(Context context, View view) {
		Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
		view.startAnimation(shake);
	}

	/**
	 * 判断网络是否可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 显示进度
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param indeterminate
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(cancelable);
		dialog.setCanceledOnTouchOutside(false);
		// dialog.setDefaultButton(false);
		dialog.show();
		return dialog;
	}

	public static String softVersion(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info.versionName;
	}

	// 获取版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	// 获取版本号
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	public static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;
		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * 判断是否是 wifi 网络
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 时间秒长度转换成xx:xx:xx
	 */
	public static String second2Hours(String time) {
		long a = Long.valueOf(time);
		long hour = a / 3600;
		long minute = a % 3600 / 60;
		long second = a % 60;
		String b = "";
		String c = "";
		String d = "";
		if (second < 10) {
			b = "0";
		}

		if (minute < 10) {
			c = "0";
		}
		if (hour < 10) {
			d = "0";
		}
		LogUtils.e("时间转换前：" + time + "秒", "时间转换后：" + d + hour + ":" + c
				+ minute + ":" + b + second);
		// System.out.println(d+hour + ":" +c+minute + ":" + b+second);
		return d + hour + ":" + c + minute + ":" + b + second;
	}

	/**
	 * 获取自身签名MD5值
	 */
	public static void getSingInfo(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo("com.leezm.vindictus.activity",
							PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			parseSignature(sign.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseSignature(byte[] signature) {
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature));
			String pubKey = cert.getPublicKey().toString();
			String signNumber = cert.getSerialNumber().toString();
			LogUtils.e("signName:", "signName:" + cert.getSigAlgName());
			LogUtils.e("pubKey:", pubKey);
			LogUtils.e("signNumber:", signNumber);
			LogUtils.e("subjectDN:", cert.getSubjectDN().toString());
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取设备ID
	 */

	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	public synchronized static String getDriverId(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists())
					writeInstallationFile(installation);
				sID = readInstallationFile(installation);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		if (sID==null) {
			return "";
		} else {
			return sID;
		}
		
	}

	private static String readInstallationFile(File installation)
			throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationFile(File installation)
			throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}

}
