package com.shensou.newpress.utils;

import java.text.DecimalFormat;

public class DigitalFormatUtil {
	/**
	 * 返回两个小数点
	 * @param digitals
	 * @return
	 */
    public static String getTwoPoint(double digitals){
    	DecimalFormat myformat = new DecimalFormat("0.00");
    	return myformat.format(digitals);
    }
	public static String getTwoPoint(float digitals){
    	DecimalFormat myformat = new DecimalFormat("0.00");
    	return myformat.format(digitals);
    }

	/**
	 * @param digitals
	 * @param num 从后往前每隔num位插入逗号
	 * @return
	 */
	public static String addFlag(String digitals,int num){
		StringBuilder str =new StringBuilder(digitals);

		int last = str.length();
		for( int i=last-num; i >=1 ; i -= num )
		{
			str.insert(i, ',');
		}
		return str.toString();
	}
}
