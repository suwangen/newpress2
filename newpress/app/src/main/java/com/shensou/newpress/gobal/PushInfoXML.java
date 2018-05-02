
package com.shensou.newpress.gobal;

import android.content.Context;
import android.content.SharedPreferences;

public class PushInfoXML {

	/**
	 * 保存Preference的name
	 */
	private String PREFERENCE_NAME = Constants.PUSH_INFO;
	private static SharedPreferences mSharedPreferences;
	private static PushInfoXML mPreferenceUtils;
	private static SharedPreferences.Editor editor;

	
	private static final String BIND__FLAG = "bind_flag";
	private static final String IS_RECEIVE="is_receive";
	
	private String CHANNEL_ID="channelId";//百度推送设备ID
	

	

	private PushInfoXML(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * @param cxt
	 * @return
	 */
	public static PushInfoXML getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new PushInfoXML(cxt);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}
	
	public static SharedPreferences.Editor getSharedEditor(Context cxt) {
		getInstance(cxt);
		return editor;
	}
	


	

	

	public boolean isBind() {
		return mSharedPreferences.getBoolean(BIND__FLAG, false);
	}

	public void setBind(boolean isBind) {
		editor.putBoolean(BIND__FLAG, isBind);
		editor.commit();
	}

	public boolean isReceive() {
		return mSharedPreferences.getBoolean(IS_RECEIVE, true);
	}

	public void setReceive(boolean isReceive) {
		editor.putBoolean(IS_RECEIVE, isReceive);
		editor.commit();
	}
	
	
	public String getChannelId() {
		return mSharedPreferences.getString(CHANNEL_ID, "");
	}

	public void setChannelId(String channelId) {
		editor.putString(CHANNEL_ID, channelId);
		editor.commit();
	}
	
}
