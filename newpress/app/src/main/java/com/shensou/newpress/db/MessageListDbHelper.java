package com.shensou.newpress.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MessageListDbHelper extends SQLiteOpenHelper {

	private static Context context;
	private static final int DATABASE_VERSION = 1;
	private static MessageListDbHelper instance;

	public MessageListDbHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	private static String getUserDatabaseName() {
		return "message.db";
	}

	public static MessageListDbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new MessageListDbHelper(context.getApplicationContext());
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	public void closeDB() {
		if (instance != null) {
			try {
				SQLiteDatabase db = instance.getWritableDatabase();
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
