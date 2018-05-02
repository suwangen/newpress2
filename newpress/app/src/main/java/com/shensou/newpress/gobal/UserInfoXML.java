
package com.shensou.newpress.gobal;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoXML {

	/**
	 * 保存Preference的name
	 */
	private String PREFERENCE_NAME = Constants.XML_USER_INFO;
	private static SharedPreferences mSharedPreferences;
	private static UserInfoXML mPreferenceUtils;
	private static SharedPreferences.Editor editor;
	private String IS_CHANGE_STATUE= "is_change_statue";//是否改变登录状态  1正常 2改变
	private String LOGIN_JSON="login_json";//
	private String USERID="userid";//token
	private String UID="uid";
	private String USERNAME="username";
	private String NICK_NAME="nickname";
	private String USER_AVATAR="user_avatar";
	private String ACCESSTOKEN="accesstoken";//第三方登录的accesstoken即uid
	private String PWD="pwd";



	private UserInfoXML(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * @param cxt
	 * @return
	 */
	public static UserInfoXML getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new UserInfoXML(cxt);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}
	
	public static SharedPreferences.Editor getSharedEditor(Context cxt) {
		getInstance(cxt);
		return editor;
	}

	public String getis_change_statue() {
		return mSharedPreferences.getString(IS_CHANGE_STATUE, "");
	}
	
	public void setis_change_statue(String is_change_statue) {
		editor.putString(IS_CHANGE_STATUE, is_change_statue);
		editor.commit();
	}
	public String getlogin_json() {
		return mSharedPreferences.getString(LOGIN_JSON, "");
	}

	public void setlogin_json(String login_json) {
		editor.putString(LOGIN_JSON, login_json);
		editor.commit();
	}
	public String getuserid() {
		return mSharedPreferences.getString(USERID, "");
	}

	public void setuserid(String userid) {
		editor.putString(USERID, userid);
		editor.commit();
	}
	public String getusername() {
		return mSharedPreferences.getString(USERNAME, "");
	}

	public void setusername(String username) {
		editor.putString(USERNAME, username);
		editor.commit();
	}




	public String getPwd() {
		return mSharedPreferences.getString(PWD, "");
	}

	public void setPwd(String pwd) {
		editor.putString(PWD, pwd);
		editor.commit();
	}

	public String getUid() {
		return mSharedPreferences.getString(UID, "");
	}

	public void setUid(String uid) {
		editor.putString(UID, uid);
		editor.commit();
	}

	public String getNickName() {
		return mSharedPreferences.getString(NICK_NAME, "");
	}

	public void setNickName(String nickName) {
		editor.putString(NICK_NAME, nickName);
		editor.commit();
	}



	public String getUser_avatar() {
		return mSharedPreferences.getString(USER_AVATAR, "");
	}

	public void setUser_avatar(String user_avatar) {
		editor.putString(USER_AVATAR, user_avatar);
		editor.commit();
	}


	public String getAccesstoken() {
		return mSharedPreferences.getString(ACCESSTOKEN, "");
	}

	public void setAccesstoken(String accesstoken) {
		editor.putString(ACCESSTOKEN, accesstoken);
		editor.commit();
	}


}
