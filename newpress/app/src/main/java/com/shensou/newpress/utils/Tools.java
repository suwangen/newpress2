package com.shensou.newpress.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.shensou.newpress.gobal.UserInfoXML;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




public class Tools {

	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 参数加密
	 *
	 * param param
	 * @return
	 */
	public static String paramsEncryption(Map<String, String> param) {
		Gson gson = new Gson();
		String result = gson.toJson(param);
//		try {
//			result = URLEncoder.encode(result, "utf-8");
//
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		result = Base64.encode(result);


		return result;

	}

	public static String buildParams(Map<String, String> params){
		Iterator i=params.entrySet().iterator();
		StringBuffer param=new StringBuffer();
		while (i.hasNext()){
			Map.Entry e= (Map.Entry) i.next();
			param.append("&").append(e.getKey()+"=").append(e.getValue());
		}
		return param.toString();
	}

	/**
	 * @Description: TODO(检查用户是否是登陆状态)
	 * @param @param cxt
	 * @return boolean true 登陆状态 false未登录状态
	 */
	public static boolean checkUserLogin(Context cxt) {
		UserInfoXML mPreferenceUtils = UserInfoXML.getInstance(cxt);
		if (!TextUtils.isEmpty(mPreferenceUtils.getuserid()))
			return true;
		else
			return false;
	}



	/**
	 * 加密手机号
	 *
	 */
	public static String encryptPhoneNum(String phonenum) {
		StringBuilder sb = new StringBuilder();
		sb.append(phonenum.substring(0, 4));
		sb.append("****");
		sb.append(phonenum.substring(8, 11));
		return sb.toString();
	}

	/**
	 * 加密手机号
	 *
	 */
	public static String encryptIdCard(String idCard) {
		try{
			StringBuilder sb = new StringBuilder();
			sb.append(idCard.substring(0, 4));
			sb.append("***********");
			sb.append(idCard.substring(15, 18));
			return sb.toString();
		}catch (Exception e){

		}

		return idCard;

	}

	/*
	 * 拨打客服电话
	 */
	public static void callTell(Context mCotext, String phoneNum) {
		// 客服号码
		// 进行拨号，android中提供了拨号软件，通过Activity实现了拨号功能
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ phoneNum));
		// 激活Intent
		mCotext.startActivity(intent);
	}

	/**
	 * @param raidus
	 *            单位米 return minLat,minLng,maxLat,maxLng
	 */
	public static double[] getAround(double lat, double lon, int raidus) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = doubleDecimal(latitude - radiusLat);
		Double maxLat = doubleDecimal(latitude + radiusLat);

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = doubleDecimal(longitude - radiusLng);
		Double maxLng = doubleDecimal(longitude + radiusLng);
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	public static double doubleDecimal(Double f) {
		BigDecimal b = new BigDecimal(f);// BigDecimal 类使用户能完全控制舍入行为

		double f1 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

		return f1;

	}

	/**
	 * 计算时间差
	 * 如果大于60分钟 显示x小时前
	 * 如果小于1分钟  显示1分钟前
	 * 如果大于等于1分钟，显示x分钟前
	 * 如果如期与当前日期不同 直接显示年月日
	 * @param commentTime 传进来的时间
	 */
	public static String timeDifference(String commentTime) {
		long commentTimeMills= Long.parseLong(commentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		Date commentDate=new Date(commentTimeMills);
		String str = df.format(curDate);

		String time=df.format(commentDate);
		String str1 = "";
		try {
			if (time.substring(0,10).equals(str.substring(0,10))) {//如果发表时间和当前时间的年月日相同 计算时间差值
				int hours = Integer.valueOf(str.substring(11, 13)) - Integer.valueOf(time.substring(11, 13));
				int minute = Integer.valueOf(str.substring(14, 16)) - Integer.valueOf(time.substring(14, 16));
				int minuteTotal = hours*60 + minute;
				if (minuteTotal > 60) { //如果差值大于60分钟 显示x小时前
					int hourTotal = minuteTotal/60;
					str1 = hourTotal+"小时前";
				} else if(minuteTotal < 1){ //如果差值小于1 显示1分钟前
					str1 = 1 +"分钟前";
				} else {
					str1 = Math.abs(minuteTotal)+"分钟前";
				}
			} else if (!time.substring(0,10).equals(str.substring(0,10))) {
				str1 = time.substring(0,10);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str1;
	}
/**
	 * 计算时间差
	 * 如果大于60分钟 显示x小时前
	 * 如果小于1分钟  显示1分钟前
	 * 如果大于等于1分钟，显示x分钟前
	 * 如果如期与当前日期不同 直接显示年月日
	 * @param commentTime 传进来的时间
	 */
	public static String timeDifference1(String commentTime) {
//		long commentTimeMills=Long.parseLong(commentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		Date commentDate=new Date(commentTimeMills);
		String str = df.format(curDate);

//		String time=df.format(commentDate);
		String time=commentTime;
		String str1 = "";
		try {
			if (time.substring(0,10).equals(str.substring(0,10))) {//如果发表时间和当前时间的年月日相同 计算时间差值
				int hours = Integer.valueOf(str.substring(11, 13)) - Integer.valueOf(time.substring(11, 13));
				int minute = Integer.valueOf(str.substring(14, 16)) - Integer.valueOf(time.substring(14, 16));
				int minuteTotal = hours*60 + minute;
				if (minuteTotal > 60) { //如果差值大于60分钟 显示x小时前
					int hourTotal = minuteTotal/60;
					str1 = hourTotal+"小时前";
				} else if(minuteTotal < 1){ //如果差值小于1 显示1分钟前
					str1 = 1 +"分钟前";
				} else {
					str1 = Math.abs(minuteTotal)+"分钟前";
				}
			} else if (!time.substring(0,10).equals(str.substring(0,10))) {
				str1 = time.substring(5,time.length()-3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str1;
	}


	public static String parseTime(String commentTime){
		long commentTimeMills= Long.parseLong(commentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date commentDate=new Date(commentTimeMills);
		String time=df.format(commentDate);
		return time;
	}
	public static String parseTime1(String commentTime){
		long commentTimeMills= Long.parseLong(commentTime);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date commentDate=new Date(commentTimeMills);
		String time=df.format(commentDate);
		return time;
	}

	/**
	 * @author jerry.chen
	 * @param brithday
	 * @return
	 * @throws ParseException
	 *             根据生日获取年龄;
	 */
	public static int getCurrentAgeByBirthdate(String brithday){
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			String currentTime = formatDate.format(cal.getTime());
			Date today = formatDate.parse(currentTime);
			long commentTimeMills= Long.parseLong(brithday);
			Date brithDay = new Date(commentTimeMills);

			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH);
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
			cal.setTime(brithDay);

			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			int age = yearNow - yearBirth+1;

//			if (monthNow <= monthBirth) {
//				if (monthNow == monthBirth) {
//					if (dayOfMonthNow < dayOfMonthBirth) age--;
//				}else{
//					age--;
//				}
//			}

			return age;
		} catch (Exception e) {
			return 0;
		}
	}


	public static List<String> toHashMap(Object object) {
		String result=JsonUtils.serialize(object);
		List<String> data = new ArrayList<>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);

		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext())
		{
			String key = String.valueOf(it.next());
			String value = (String) jsonObject.get(key);
			data.add(value);
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.e("标签", JsonUtils.serialize(data) + "");
		return data;
	}
	/**
	 * 顶部没标题栏的时候用这个，manifest仿照
	 * @param activity
	 */
	public static void setStatusBarTranslucent(Activity activity) {
//		// 如果版本在4.4以上
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			// 状态栏透明
//			activity.getWindow().addFlags(
//					View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//			);
//			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//		}
	}

	/**
	 * 判断是否底部有虚拟按键
	 * @param context
	 * @return
	 */
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;

	}

	/**
	 * 获取虚拟底部按键高度
	 * @param context
	 * @return
	 */

	public static int getNavigationBarHeight(Context context) {
		int result = 0;
		if (checkDeviceHasNavigationBar(context)) {
			Resources res = context.getResources();
			int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
			if (resourceId > 0) {
				result = res.getDimensionPixelSize(resourceId);
			}
		}
		return result;
	}

	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	private int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
