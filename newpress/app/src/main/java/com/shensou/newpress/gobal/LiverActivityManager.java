package com.shensou.newpress.gobal;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class LiverActivityManager {
	private static final String TAG = "ActivityManager";

	// 保存创建的所有activity
	private static List<Activity> activityStack ;
	
	private static LiverActivityManager instance;


	public static LiverActivityManager getAppManager() {
		if (instance == null) {
			instance = new LiverActivityManager();
		}
		if (activityStack == null) {
			activityStack = new ArrayList<Activity>();
		}
		return instance;
	}

	/**
	 * 压栈
	 */
	public void addActivity(Activity activity) {
		activityStack.add(activity);
	}

	/**
	 * 获取当前页面上一级的Activity
	 *
	 * @return 上一级的activity
	 */
	public static Activity getSuperiorActivity() {
		if (activityStack.size() > 1) {
			return activityStack.get(1);
		}
		return null;
	}
	/**
	 * 结束当前Activity
	 */
	public void finishActivity(Activity activity) {
		if (null != activity){
			activityStack.remove(activity);
			activity = null;
		}
	}
	
	/**
	 * 关闭倒数第二个
	 */
	public void finishLastTwoActivity(){
		if(activityStack.size()>2){
			 activityStack.get(activityStack.size()-2).finish();
		}
	}
	/**
	 * @Description: TODO(保留首页的Activity) 
	 * @ param     设定文件
	 * @return void    返回类型
	 */
	public void keepFirstActivity(){
		for (int i = 1; i < activityStack.size(); i++) {
			activityStack.get(i).finish();
		}
	}
	/**
	 * 结束所有Activity，但保留最后一个
	 */
	public void finishActivitiesAndKeepLastOne() {
		int size = activityStack.size() - 1;
		for (int i = 0; i < size; i++) {
			activityStack.get(0).finish();
			activityStack.remove(0);
		}
	}
	
	//關閉倒数三个activity
	public void finishLastThree() {
		if(activityStack.size()>3){
			for (int i = 0; i < 3; i++) {
				activityStack.get(activityStack.size()-1).finish();
				activityStack.remove(activityStack.size()-1);
			}
		}
		
	}
	
	//關閉倒数三个activity
		public void finishLastTwo() {
			if(activityStack.size()>2){
				for (int i = 0; i < 2; i++) {
					activityStack.get(activityStack.size()-1).finish();
					activityStack.remove(activityStack.size()-1);
				}
			}
			
		}
	

	/**
	 * 退出应用程序
	 */
	public void AppExit() {
		finishAllActivity();
		// 退出程序
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(0);
	}
	
	/**
	 * 关闭所有Activity
	 */
	public void finishAllActivity(){
		if (activityStack.size() > 0) {
			for (Activity activity : activityStack) {
				activity.finish();// finish自动调用onDestroy方法
			}
			activityStack.clear();
			activityStack=null;
		}
	}
}
