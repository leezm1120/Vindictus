package com.leezm.vindictus.dbutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.leezm.vindictus.activity.R;
import com.leezm.vindictus.utils.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class VindictusDBManager {
	private int BUFFER_SIZE = 400000;
	public static final String DB_NAME = ".23.ijiami.so"; // 保存的数据库文件名
	public static final String PACKAGE_NAME = "com.leezm.vindictus.activity";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; //+ "files";
	private SQLiteDatabase database;
	private Context context;

	public VindictusDBManager(Context context) {
		this.context = context;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public void openDatabase() {
		LogUtils.d("openDatabase", DB_PATH + "/" + DB_NAME);
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}

	public void delDatabase() {
		LogUtils.d("delDatabase", DB_PATH + "/" + DB_NAME);
		File file = new File(DB_PATH + "/" + DB_NAME);
		file.delete();
	}

	private SQLiteDatabase openDatabase(String dbfile) {

		try {
			if (!(new File(dbfile).exists())) {
				// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is = this.context.getResources().getAssets().open("ijiami.so"); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}

			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
					null);
			// LogUtils.e("open","SQLiteDatabase");
			return db;

		} catch (FileNotFoundException e) {
			LogUtils.e("Database", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			LogUtils.e("Database", "IO exception");
			e.printStackTrace();
		}
		return null;
	}

	public void closeDatabase() {
		this.database.close();

	}
}
