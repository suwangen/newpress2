package com.shensou.newpress.db;

import android.content.Context;



public class MessageListDao {

	private Context mContext;

	private MessageListDbHelper dbHelper;

	public MessageListDao(Context context) {
		dbHelper = MessageListDbHelper.getInstance(context);
		mContext=context;
	}
	


}
