package com.leezm.vindictus.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

public class AlertDialogUtils {

	public interface OnClickYesListener {
		abstract void onClickYes();
	}

	public interface OnClickNoListener {
		abstract void onClickNo(); 
	}

	public static void showDialog(Context context, String Message,
			final OnClickYesListener listenerYes,
			final OnClickNoListener listenerNo) {
		new AlertDialog.Builder(context).setMessage(Message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (listenerYes != null) {
							listenerYes.onClickYes();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						if (listenerNo != null) {
							listenerNo.onClickNo();
						}
					}
				}).show();
	}
}
