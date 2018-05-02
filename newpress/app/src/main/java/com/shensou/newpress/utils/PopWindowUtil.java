package com.shensou.newpress.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.adapter.BaseLoadingAdapter;
import com.shensou.newpress.adapter.PopJobsClassifyAdapter;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.DateArrayAdapter;


/**
 * popwindow 工具
 * 
 * @author Administrator
 * 
 */
public class PopWindowUtil {
	private PopupWindow popupWindow;

	public interface HandlerItemCallBack {
		public void handle(View view, int position);
	}

	public interface HandlerItemCallBack1 {
		public void handle(AdapterView<?> parent, View view, int position,
                           long id);
	}
	public interface HandlerItemCallBack2 {
		public void handle(String s, int position);
	}

	public interface HandlerItemCallBackPlace {
		public void handle(String s1, String s2, String s3, String s1_code, String s2_code, String s3_code, int position);
	}
	public interface CallBackHandler {
		void reset();

		void ensure(PopupWindow pWindow);

	}

	public interface CallBackEditor {
		public void handle(String str);

	}



	private DateArrayAdapter yearAdapter;
	private DateArrayAdapter monthAdapter;
	private DateArrayAdapter dayAdapter;

	private String[] yearArray = new String[12];
	private String[] monthArray = new String[12];
	private String[] dayArray = new String[12];
	private Calendar calendar;
	@SuppressWarnings("null")
	public PopupWindow initPopuptWindowBirthDate(final Context context,
			final View view,
			final HandlerItemCallBack2 hanlder) {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return null;
		}
		// 获取自定义布局文件pop.xml的视图
		final View popupWindow_view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_numberpicker_birthday, null, false);
		// 创建PopupWindow实例,200,150分别是宽度和高度
		popupWindow = new PopupWindow(popupWindow_view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);
		// 这里是位置显示方式
		//		popupWindow.showAsDropDown(view);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
		//		List<String> stringListYear = new ArrayList<String>();
		//		for(int i=1920;i<1920+100;i++){
		//			stringListYear.add(i+"年");
		//		}
		//		String[] stringArrayYear = new String[stringListYear.size()];
		//		for(int i=0;i<stringListYear.size();i++){
		//			stringArrayYear[i] = stringListYear.get(i);
		//		}
		//
		//		List<String> stringListMonth = new ArrayList<String>();
		//		for(int i=0;i<12;i++){
		//			stringListMonth.add(i+"月");
		//		}
		//		String[] stringArrayMonth = new String[stringListMonth.size()];
		//		for(int i=0;i<stringListMonth.size();i++){
		//			stringArrayMonth[i] = stringListMonth.get(i);
		//		}
		//
		//		List<String> stringListDay = new ArrayList<String>();
		//		for(int i=0;i<30;i++){
		//			stringListDay.add(i+"日");
		//		}
		//		String[] stringArrayDay = new String[stringListDay.size()];
		//		for(int i=0;i<30;i++){
		//			stringArrayDay[i] = stringListDay.get(i);
		//		}
		final WheelView pop_numberpicker_birthdate_year = (WheelView)popupWindow_view.findViewById(R.id.pop_numberpicker_birthdate_year);
		final WheelView pop_numberpicker_birthdate_month = (WheelView)popupWindow_view.findViewById(R.id.pop_numberpicker_birthdate_month);
		final WheelView pop_numberpicker_birthdate_day = (WheelView)popupWindow_view.findViewById(R.id.pop_numberpicker_birthdate_day);
		final WheelView wheelview1 = (WheelView)popupWindow_view.findViewById(R.id.wheelview1);
		final WheelView wheelview2 = (WheelView)popupWindow_view.findViewById(R.id.wheelview2);
		final WheelView wheelview3 = (WheelView)popupWindow_view.findViewById(R.id.wheelview3);
		final View view_birth = (View)popupWindow_view.findViewById(R.id.view_birth);
		TextView iv_pop_birthday_confirm = (TextView)popupWindow_view.findViewById(R.id.iv_pop_birthday_confirm);
		TextView iv_pop_birthday_cancel = (TextView)popupWindow_view.findViewById(R.id.iv_pop_birthday_cancel);


		calendar = Calendar.getInstance();
		Date time = new Date();
		calendar.setTime(time);
		final int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		//		Log.e("swg","year"+year+"--"+"month"+month+"days--"+days+"day--"+day);
		iv_pop_birthday_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				hanlder.handle(pop_numberpicker_birthdate_year.getCurrentItem()+(year-25)
						+"."+
						((pop_numberpicker_birthdate_month.getCurrentItem()+1>=10)?pop_numberpicker_birthdate_month.getCurrentItem()+1:("0"+(pop_numberpicker_birthdate_month.getCurrentItem()+1)))
						+"."+
						((pop_numberpicker_birthdate_day.getCurrentItem()+1>=10)?pop_numberpicker_birthdate_day.getCurrentItem()+1:("0"+(pop_numberpicker_birthdate_day.getCurrentItem()+1)))
						,0);
			}
		});
		List<String> stringListYear = new ArrayList<String>();
		for(int i=year-25;i<year+25;i++){
			stringListYear.add(i+"年");
		}
		yearArray= new String[stringListYear.size()];
		for(int i=0;i<stringListYear.size();i++){
			yearArray[i] = stringListYear.get(i);
		}
		for(int i = 0;i<12;i++){
			monthArray[i] = (i+1)+"月";
		}
		final List<String> stringListDay = new ArrayList<String>();
		for(int i=0;i<days;i++){
			stringListDay.add((i+1)+"日");
		}
		dayArray= new String[stringListDay.size()];
		for(int i=0;i<stringListDay.size();i++){
			dayArray[i] = stringListDay.get(i);
		}
		wheelview1.setViewAdapter(new DateArrayAdapter(context, new String[]{""}, wheelview1.getCurrentItem()));
		wheelview1.setVisibleItems(5);
		wheelview2.setViewAdapter(new DateArrayAdapter(context, new String[]{""}, wheelview2.getCurrentItem()));
		wheelview2.setVisibleItems(5);
		wheelview3.setViewAdapter(new DateArrayAdapter(context, new String[]{""}, wheelview3.getCurrentItem()));
		wheelview3.setVisibleItems(5);
		yearAdapter = new DateArrayAdapter(context, yearArray,pop_numberpicker_birthdate_year.getCurrentItem());
		pop_numberpicker_birthdate_year.setViewAdapter(yearAdapter);
		pop_numberpicker_birthdate_year.setVisibleItems(5);
		pop_numberpicker_birthdate_year.setCyclic(false);
		pop_numberpicker_birthdate_year.setCurrentItem(25);
		yearAdapter = new DateArrayAdapter(context, yearArray,pop_numberpicker_birthdate_year.getCurrentItem());
		pop_numberpicker_birthdate_year.setViewAdapter(yearAdapter);;
		pop_numberpicker_birthdate_year.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearAdapter = new DateArrayAdapter(context, yearArray,pop_numberpicker_birthdate_year.getCurrentItem());
				pop_numberpicker_birthdate_year.setViewAdapter(yearAdapter);
				calendar.set(Calendar.YEAR,pop_numberpicker_birthdate_year.getCurrentItem()+(year-25));
				calendar.set(Calendar.MONTH,pop_numberpicker_birthdate_month.getCurrentItem()); 
				int days1 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				//				Log.e("swg","year:"+calendar.get(Calendar.YEAR)+"month:"+calendar.get(Calendar.DAY_OF_MONTH)+"days1"+days1);
				if(stringListDay!=null)
					stringListDay.clear();
				for(int i=0;i<days1;i++){
					stringListDay.add((i+1)+"日");
				}
				dayArray= new String[stringListDay.size()];
				for(int i=0;i<stringListDay.size();i++){
					dayArray[i] = stringListDay.get(i);
				}
				//				Log.e("swg",pop_numberpicker_birthdate_year.getCurrentItem()+
				//						":"+pop_numberpicker_birthdate_month.getCurrentItem()+
				//						":"+pop_numberpicker_birthdate_day.getCurrentItem());
				if(pop_numberpicker_birthdate_day.getCurrentItem()>days1){
					pop_numberpicker_birthdate_day.setCurrentItem(days1-1);
				}
				dayAdapter = new DateArrayAdapter(context, dayArray,pop_numberpicker_birthdate_day.getCurrentItem());
				pop_numberpicker_birthdate_day.setViewAdapter(dayAdapter);
			}
		});


		monthAdapter = new DateArrayAdapter(context, monthArray,pop_numberpicker_birthdate_month.getCurrentItem());
		pop_numberpicker_birthdate_month.setViewAdapter(monthAdapter);
		pop_numberpicker_birthdate_month.setVisibleItems(5);
		pop_numberpicker_birthdate_month.setCyclic(true);
		pop_numberpicker_birthdate_month.setCurrentItem(month);
		monthAdapter = new DateArrayAdapter(context, monthArray,pop_numberpicker_birthdate_month.getCurrentItem());
		pop_numberpicker_birthdate_month.setViewAdapter(monthAdapter);
		pop_numberpicker_birthdate_month.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				monthAdapter = new DateArrayAdapter(context, monthArray,pop_numberpicker_birthdate_month.getCurrentItem());
				pop_numberpicker_birthdate_month.setViewAdapter(monthAdapter);

				calendar.set(Calendar.YEAR,pop_numberpicker_birthdate_year.getCurrentItem()+(year-25));
				calendar.set(Calendar.MONTH,pop_numberpicker_birthdate_month.getCurrentItem()); 
				int days1 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				Log.e("swg","year:"+calendar.get(Calendar.YEAR)+"month:"+calendar.get(Calendar.DAY_OF_MONTH)+"days1"+days1);
				if(stringListDay!=null)
					stringListDay.clear();
				for(int i=0;i<days1;i++){
					stringListDay.add((i+1)+"日");
				}
				dayArray= new String[stringListDay.size()];
				for(int i=0;i<stringListDay.size();i++){
					dayArray[i] = stringListDay.get(i);
				}
				//				Log.e("swg",pop_numberpicker_birthdate_year.getCurrentItem()+
				//						":"+pop_numberpicker_birthdate_month.getCurrentItem()+
				//						":"+pop_numberpicker_birthdate_day.getCurrentItem()+"daysiez:"+dayArray.length);
				if(pop_numberpicker_birthdate_day.getCurrentItem()>days1){
					pop_numberpicker_birthdate_day.setCurrentItem(days1-1);
				}
				dayAdapter = new DateArrayAdapter(context, dayArray,pop_numberpicker_birthdate_day.getCurrentItem());
				pop_numberpicker_birthdate_day.setViewAdapter(dayAdapter);
			}
		});

		dayAdapter = new DateArrayAdapter(context, dayArray,pop_numberpicker_birthdate_day.getCurrentItem());
		pop_numberpicker_birthdate_day.setViewAdapter(dayAdapter);
		pop_numberpicker_birthdate_day.setCurrentItem(day-1);
		pop_numberpicker_birthdate_day.setVisibleItems(5);
		pop_numberpicker_birthdate_day.setCyclic(true);
		dayAdapter = new DateArrayAdapter(context, dayArray,pop_numberpicker_birthdate_day.getCurrentItem());
		pop_numberpicker_birthdate_day.setViewAdapter(dayAdapter);
		pop_numberpicker_birthdate_day.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				dayAdapter = new DateArrayAdapter(context, dayArray,pop_numberpicker_birthdate_day.getCurrentItem());
				pop_numberpicker_birthdate_day.setViewAdapter(dayAdapter);

			}
		});

		//		pop_numberpicker_birthdate_year.setMaxValue(stringListYear.size()-1);
		//		pop_numberpicker_birthdate_year.setMinValue(0);
		//		pop_numberpicker_birthdate_year.setDisplayedValues(stringArrayYear);
		//		pop_numberpicker_birthdate_year.setValue(5);
		//		pop_numberpicker_birthdate_year.setDescendantFocusability(MyNumberPicker.FOCUS_BLOCK_DESCENDANTS);
		//		pop_numberpicker_birthdate_year.setWrapSelectorWheel(true);
		//		pop_numberpicker_birthdate_year.setNumberPickerDividerColor(context.getResources().getColor(R.color.white));
		//		pop_numberpicker_birthdate_year.setOnValueChangedListener(new OnValueChangeListener() {
		//
		//			@Override
		//			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});
		//		pop_numberpicker_birthdate_month.setMaxValue(stringListMonth.size()-1);
		//		pop_numberpicker_birthdate_month.setMinValue(0);
		//		pop_numberpicker_birthdate_month.setDisplayedValues(stringArrayMonth);
		//		pop_numberpicker_birthdate_month.setValue(5);
		//		pop_numberpicker_birthdate_month.setWrapSelectorWheel(true);
		//		pop_numberpicker_birthdate_month.setNumberPickerDividerColor(context.getResources().getColor(R.color.white));
		//		pop_numberpicker_birthdate_month.setOnValueChangedListener(new OnValueChangeListener() {
		//
		//			@Override
		//			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		//				// TODO Auto-generated method stub
		//
		//			}
		//		});
		//
		//		pop_numberpicker_birthdate_day.setMaxValue(stringListDay.size()-1);
		//		pop_numberpicker_birthdate_day.setMinValue(0);
		//		pop_numberpicker_birthdate_day.setDisplayedValues(stringArrayDay);
		//		pop_numberpicker_birthdate_day.setValue(5);
		//		pop_numberpicker_birthdate_day.setWrapSelectorWheel(true);
		//		pop_numberpicker_birthdate_day.setNumberPickerDividerColor(context.getResources().getColor(R.color.white));

		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AnimationFade);

		// 点击其他地方消失
		view_birth.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		iv_pop_birthday_cancel.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});



		return popupWindow;
	}

	private String[] sexArray = new String[2];
	private DateArrayAdapter sexAdapter;
	/**
	 *
	 * @param context
	 * @param view
	 * @param hanlder
	 * @return
	 */
	public PopupWindow initPopuptWindowSex(final Context context, final View view,
										   final HandlerItemCallBack2 hanlder) {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return null;
		}
		// 获取自定义布局文件pop.xml的视图
		final View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.popwindow_sex, null, false);
		// 创建PopupWindow实例,200,150分别是宽度和高度
		popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// 这里是位置显示方式
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AnimationFade);

		final WheelView lv_pop_sex = (WheelView) popupWindow_view.findViewById(R.id.lv_pop_sex);
		TextView iv_pop_birthday_confirm = (TextView)popupWindow_view.findViewById(R.id.iv_pop_birthday_confirm);
		sexArray[0] = "男";
		sexArray[1] = "女";
		sexAdapter = new DateArrayAdapter(context, sexArray,lv_pop_sex.getCurrentItem());
		lv_pop_sex.setViewAdapter(yearAdapter);
		lv_pop_sex.setVisibleItems(3);
		lv_pop_sex.setCyclic(false);
		lv_pop_sex.setCurrentItem(0);
		lv_pop_sex.setViewAdapter(sexAdapter);
		iv_pop_birthday_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hanlder.handle(sexArray[lv_pop_sex.getCurrentItem()],lv_pop_sex.getCurrentItem());
				popupWindow.dismiss();

			}
		});
		lv_pop_sex.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				sexAdapter = new DateArrayAdapter(context, sexArray,lv_pop_sex.getCurrentItem());
				lv_pop_sex.setViewAdapter(sexAdapter);
			}
		});
//		PopwindowTextAdapter popwindowTextAdapter = new PopwindowTextAdapter(context, list);
//		lv_pop_sex.setAdapter(popwindowTextAdapter);
//		lv_pop_sex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				hanlder.handle(parent, view, position, id);
//				popupWindow.dismiss();
//				popupWindow = null;
//			}
//		});
		// 点击其他地方消失
		popupWindow_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});

		return popupWindow;
	}

private String[] listArray ;
	/**
	 *
	 * @param context
	 * @param view
	 * @param hanlder
	 * @return
	 */
	public PopupWindow initPopuptWindowList(final Context context, final View view,
										   final HandlerItemCallBack2 hanlder,List<String> list) {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return null;
		}
		// 获取自定义布局文件pop.xml的视图
		final View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.popwindow_sex, null, false);
		// 创建PopupWindow实例,200,150分别是宽度和高度
		popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// 这里是位置显示方式
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AnimationFade);

		final WheelView lv_pop_sex = (WheelView) popupWindow_view.findViewById(R.id.lv_pop_sex);
		TextView iv_pop_birthday_confirm = (TextView)popupWindow_view.findViewById(R.id.iv_pop_birthday_confirm);
		TextView tv_list_cancel = (TextView)popupWindow_view.findViewById(R.id.tv_list_cancel);
		View view_list = (View)popupWindow_view.findViewById(R.id.view_list);
		if(list!=null&&list.size()>0){
			listArray = new String[list.size()];
			for(int i=0;i<list.size();i++){
                listArray[i] = list.get(i);
			}
		}else{
			listArray = new String[2];
			listArray[0] = "男";
			listArray[1] = "女";
		}


		sexAdapter = new DateArrayAdapter(context, listArray,lv_pop_sex.getCurrentItem());
		lv_pop_sex.setViewAdapter(yearAdapter);
		lv_pop_sex.setVisibleItems(5);
		lv_pop_sex.setCyclic(false);
		lv_pop_sex.setCurrentItem(0);
		lv_pop_sex.setViewAdapter(sexAdapter);
		iv_pop_birthday_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hanlder.handle(listArray[lv_pop_sex.getCurrentItem()],lv_pop_sex.getCurrentItem());
				popupWindow.dismiss();

			}
		});
		lv_pop_sex.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				sexAdapter = new DateArrayAdapter(context, listArray,lv_pop_sex.getCurrentItem());
				lv_pop_sex.setViewAdapter(sexAdapter);
			}
		});
		//		PopwindowTextAdapter popwindowTextAdapter = new PopwindowTextAdapter(context, list);
		//		lv_pop_sex.setAdapter(popwindowTextAdapter);
		//		lv_pop_sex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		//			@Override
		//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//				hanlder.handle(parent, view, position, id);
		//				popupWindow.dismiss();
		//				popupWindow = null;
		//			}
		//		});
		// 点击其他地方消失
		view_list.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		tv_list_cancel.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});

		return popupWindow;
	}
	public PopupWindow initPopuptWindowJobsClassify(final Context context,
													final View iv,BaseLoadingAdapter mAdapter,
													final HandlerItemCallBack2 hanlder) {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return null;
		}
		// 获取自定义布局文件pop.xml的视图
		final View popupWindow_view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_classify_list, null, false);
		// 创建PopupWindow实例,200,150分别是宽度和高度
		int hight = ScreenUtils.getScreenHeight(context);
		popupWindow = new PopupWindow(popupWindow_view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 这里是位置显示方式

		popupWindow.showAsDropDown(iv);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);

		final RecyclerView mRecyclerView = (RecyclerView)popupWindow_view.findViewById(R.id.recycler_view);
		//		View pop_view = popupWindow_view.findViewById(R.id.pop_view_sex);
		mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(context).setScrollEnabled(false));
		mRecyclerView.setHasFixedSize(true);
		mAdapter.setIsMultiType(true);
		mAdapter.setIsLoadMore(false);
		((PopJobsClassifyAdapter)mAdapter).setmOnItemClickLitener(new OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				hanlder.handle("",position);
				popupWindow.dismiss();
			}
		});
		if(mAdapter!=null)
			mRecyclerView.setAdapter(mAdapter);
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AnimationFade);

		// 点击其他地方消失
		//		popupWindow_view.setOnTouchListener(new OnTouchListener() {
		//			@Override
		//			public boolean onTouch(View v, MotionEvent event) {
		//				if (popupWindow != null && popupWindow.isShowing()) {
		//					if(!isTouchPointInView(mRecyclerView,(int)event.getX(),(int)event.getY())){
		//						popupWindow.dismiss();
		//					}
		//				}
		//				return false;
		//			}
		//		});

		return popupWindow;
	}

	/**
	 *
	 * @param context
	 * @param view
	 * @param hanlder
	 * @return
	 */
	public PopupWindow initPopuptWindowDistribute(final Context context, final View view,
										   final HandlerItemCallBack2 hanlder) {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return null;
		}
		// 获取自定义布局文件pop.xml的视图
		final View popupWindow_view = LayoutInflater.from(context).inflate(R.layout.popwindow_distribute, null, false);
		// 创建PopupWindow实例,200,150分别是宽度和高度
		popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// 这里是位置显示方式
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.AnimationFade);
		final View pop_view = (View) popupWindow_view.findViewById(R.id.pop_view);
		final TextView tv_pop_zixun = (TextView) popupWindow_view.findViewById(R.id.tv_pop_zixun);
		final TextView tv_pop_class = (TextView) popupWindow_view.findViewById(R.id.tv_pop_class);
		final TextView tv_pop_ask = (TextView) popupWindow_view.findViewById(R.id.tv_pop_ask);
		final TextView tv_pop_sale = (TextView) popupWindow_view.findViewById(R.id.tv_pop_sale);
		final TextView tv_pop_jingbiao = (TextView) popupWindow_view.findViewById(R.id.tv_pop_jingbiao);
		final TextView tv_pop_zhiwei = (TextView) popupWindow_view.findViewById(R.id.tv_pop_zhiwei);
		final ImageView iv_pop_close = (ImageView) popupWindow_view.findViewById(R.id.iv_pop_close);
		tv_pop_zixun.setOnClickListener(new OnClickListener() {//资讯
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","资讯发布");
				intent.putExtra("url", URL.DISTRIBUTE_ZIXUN);
				context.startActivity(intent);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}

			}
		});
		tv_pop_class.setOnClickListener(new OnClickListener() {//课堂
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","课堂发布");
				intent.putExtra("url", URL.DISTRIBUTE_CLASSROOM);
				context.startActivity(intent);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		tv_pop_ask.setOnClickListener(new OnClickListener() {//提问问题
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","提问问题");
				intent.putExtra("url", URL.DISTRIBUTE_ANSWER);
				context.startActivity(intent);

				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		tv_pop_sale.setOnClickListener(new OnClickListener() {//出售网店
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","出售网店");
				intent.putExtra("url", URL.DISTRIBUTE_TRANSFER);
				context.startActivity(intent);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		tv_pop_jingbiao.setOnClickListener(new OnClickListener() {//竞标发布
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","竞标发布");
				intent.putExtra("url", URL.DISTRIBUTE_BIDDING);
				context.startActivity(intent);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		tv_pop_zhiwei.setOnClickListener(new OnClickListener() {//职位发布
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","职位发布");
				intent.putExtra("url", URL.DISTRIBUTE_RECRUIT);
				context.startActivity(intent);
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		iv_pop_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
			}
		});
		pop_view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});

		return popupWindow;
	}
}
