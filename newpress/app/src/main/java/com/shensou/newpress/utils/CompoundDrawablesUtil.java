package com.shensou.newpress.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class CompoundDrawablesUtil {
	public static Drawable showDrawable(Context context,int drawableId){
		Drawable drawable = context.getResources().getDrawable(drawableId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
		return drawable;
	}
}
