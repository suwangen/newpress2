
package com.shensou.newpress.gobal;

import android.content.Context;
import android.content.SharedPreferences;

public class LocationInfoXML {

	/**
	 * 保存Preference的name
	 */
	private String PREFERENCE_NAME = Constants.LOCATION_INFO;
	private static SharedPreferences mSharedPreferences;
	private static LocationInfoXML mPreferenceUtils;
	private static SharedPreferences.Editor editor;

	
	
	
	private String CITY_ID="cityID";//选择的城市的id
	private String CITY_NAME="cityName";//选择的城市名
	private String LATITUDE="latitude";//选择的纬度
	private String LONGTITUDE="longtitude";//选择的经度
	private String IS_FIRST="is_first";//是否第一次登陆
	private String LOCATION_CITY="locationCity";//定位到的城市
	private String CITY_IS_CHANGE="is_change";//切换城市是是否改变
	

	private LocationInfoXML(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * @param cxt
	 * @return
	 */
	public static LocationInfoXML getInstance(Context cxt) {
		if (mPreferenceUtils == null) {
			mPreferenceUtils = new LocationInfoXML(cxt);
		}
		editor = mSharedPreferences.edit();
		return mPreferenceUtils;
	}
	
	public static SharedPreferences.Editor getSharedEditor(Context cxt) {
		getInstance(cxt);
		return editor;
	}
	


	

	public String getCityID() {
		return mSharedPreferences.getString(CITY_ID, "");
	}

	public void setCityID(String cityID) {
		editor.putString(CITY_ID, cityID);
		editor.commit();
	}
	
	public String getCityName() {
		return mSharedPreferences.getString(CITY_NAME, "");
	}

	public void setCityName(String cityName) {
		editor.putString(CITY_NAME, cityName);
		editor.commit();
	}

	public String getLatitude() {
		return mSharedPreferences.getString(LATITUDE, "");
	}

	public void setLatitude(String latitude) {
		editor.putString(LATITUDE, latitude);
		editor.commit();
	}
	
	public String getLongtitude() {
		return mSharedPreferences.getString(LONGTITUDE, "");
	}

	public void setLongtitude(String longtitude) {
		editor.putString(LONGTITUDE, longtitude);
		editor.commit();
	}

	public boolean isFirst() {
		return mSharedPreferences.getBoolean(IS_FIRST, true);
	}

	public void setFirst(boolean isFirst) {
		editor.putBoolean(IS_FIRST, isFirst);
		editor.commit();
	}
	public boolean isChange() {
		return mSharedPreferences.getBoolean(CITY_IS_CHANGE, false);
	}
	
	public void setIsChange(boolean isChange) {
		editor.putBoolean(CITY_IS_CHANGE, isChange);
		editor.commit();
	}

	public String getLocationCity() {
		 return mSharedPreferences.getString(LOCATION_CITY, "");
	}

	public void setLocationCity(String locationCity) {
		editor.putString(LOCATION_CITY, locationCity);
		editor.commit();
	}

	
}
