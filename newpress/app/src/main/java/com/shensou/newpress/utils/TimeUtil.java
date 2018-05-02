package com.shensou.newpress.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class TimeUtil

{
	private static int days;
	private static int hours;
	private static int minutes;
	private static int seconds;
	private static int hours2;
	private static int minutes2;
	private static int seconds2;
	private static int last_hour;
	private static int last_minutes;
	private static int last_seconds;
	private static int last_time;
	private static Calendar calendar = Calendar.getInstance();
	/**
	 * 倒计时
	 * @param seconds_  总秒数
	 * @param callbackTime
	 */
	public static void deadTime(int seconds_,final CallbackTime callbackTime){
		last_time =seconds_;
		TimerTaskUtils.getInstance().startTimer(new TimerTask() {

			@Override
			public void run() {
				last_time--;
				days = last_time / (  60 * 60 * 24);
				hours = (last_time % ( 60 * 60 * 24)) / ( 60 * 60);
				minutes = (last_time % ( 60 * 60)) / 60;
				seconds = last_time % 60;
				callbackTime.settext(""+days,""+hours, ""+minutes, ""+seconds);

			}
		}, 0, 1000);
	}
	/**
	 * 倒计时,不包括年月日
	 */
	 public static void deadTime(String deadtime,final Context context,final CallbackTime callbackTime){
		 if(!TextUtils.isEmpty(deadtime)){
				hours = Integer.parseInt(deadtime.trim().substring(0,2));
				minutes = Integer.parseInt(deadtime.trim().substring(3,5));
				seconds = 0;
				hours2 = calendar.get(Calendar.HOUR_OF_DAY);
				minutes2 = calendar.get(Calendar.MINUTE);
				seconds2 = calendar.get(Calendar.SECOND);
				
				if(hours<hours2){
					hours += 24;
				}
				last_seconds = 60-seconds2;
				if( minutes-1-minutes2>=0){
					last_minutes = minutes-1-minutes2;
					last_hour = hours-hours2;
				}else{
					last_minutes = 60+minutes-1-minutes2;
					last_hour = hours-1-hours2;
				}
				TimerTaskUtils.getInstance().startTimer(new TimerTask() {
					
					@Override
					public void run() {
						((Activity)context).runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								callbackTime.settext(""+(last_hour>=10?last_hour:("0"+last_hour)),
										""+(last_minutes>=10?last_minutes:("0"+last_minutes))
										,""+(last_seconds>=10?last_seconds:("0"+last_seconds)));
							}
						});
						
						last_seconds--;
						if(last_seconds==-1){
							last_minutes--;
							if(last_minutes==-1){
								last_hour--;
								if(last_hour==-1){
									TimerTaskUtils.getInstance().cancelTimer();
								}
								last_minutes = 59;
							}
							last_seconds = 59;
						}
					}
				}, 0, 1000);
			}else{
//				tv_fragment_shop_city_hour.setText("00");
				callbackTime.nullSettext();
			}
	 }
	 public interface CallbackTime{
		 void settext(String hour, String minute, String seconds);
		 void settext(String days, String hour, String minute, String seconds);
		 void nullSettext();
	 }
	/**
     * 指定日期加上num天数后的日期
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
	 public static String plusDay(int num,String newDate) throws ParseException{
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date  currdate = format.parse(newDate);
	        System.out.println("现在的日期是：" + currdate);
	        Calendar ca = Calendar.getInstance();
	        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
	        currdate = ca.getTime();
	        String enddate = format.format(currdate);
	        System.out.println("增加天数以后的日期：" + enddate);
	        return enddate;
	    }
	 /**
	  * 默认当前日期加上num天数后的日期
	  * @param num
	  * @return
	  * @throws ParseException
	  */
	 public static String plusDay(int num) throws ParseException{
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date  currdate ;
//	        System.out.println("现在的日期是：" + currdate);
	        Calendar ca = Calendar.getInstance();
	        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
	        currdate = ca.getTime();
	        String enddate = format.format(currdate);
	        System.out.println("增加天数以后的日期：" + enddate);
	        return enddate;
	    }
	/**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
	public static String timeStamp2Date(String seconds,String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
	/**
	 * 日期格式字符串转换成时间戳
	 * @param format 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 取得当前时间戳（精确到秒）
	 * @return
	 */
	public static String timeStamp(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000);
		return t;
	}
	/**
	 * 获取当前时间并转化时间格式
	 * @return
	 */
	public static String timeDate(){
		String time = timeStamp();
		String t = timeStamp2Date(time,"yyyy-MM-dd HH:mm:ss");
		return t;
	}
	/**
	 * 获取当前时间并转化时间格式
	 * @return
	 */
	public static String timeDate(String format){
		String time = timeStamp();
		String t = timeStamp2Date(time,format);
		return t;
	}
	/**
	 * 获取当前时间并转化时间格式
	 * @return
	 */
	public static String timeDate1(){
		String time = timeStamp();
		String t = timeStamp2Date(time,"yyyy-MM-dd");
		return t;
	}
	
	/**
	 * 取得当前天时间戳（精确到秒）
	 * @return
	 */
	public static String timeStamp2(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000*3600*24);
		return t;
	}
	/**
	 * 取得当前天时间戳（精确到秒）
	 * @return  返回秒数
	 */
	public static long timeStamp3(){
		long time = System.currentTimeMillis();
		return time/1000*3600*24;
	}
	/**
	 * 秒数格式化(不会超过1小时的情况下)
	 * @return  返回格式化字符串
	 */
	public static String secondsToFormat(int second_){
//		days = seconds / (  60 * 60 * 24);
//		hours = (second_ % ( 60 * 60 * 24)) / ( 60 * 60);
		minutes = second_ / 60;
		seconds = second_ % 60;
		return (minutes>9?""+minutes:"0"+minutes)+":"+(seconds>9?""+seconds:"0"+seconds);
	}
	/*//  输出结果：
	//	timeStamp=1417792627
	//	date=2014-12-05 23:17:07
	//	1417792627
	public static void main(String[] args) {
		String timeStamp = timeStamp();
		System.out.println("timeStamp="+timeStamp);
		
		String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
		System.out.println("date="+date);
		
		String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
		System.out.println(timeStamp2);
	}*/
}
