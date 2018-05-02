package com.shensou.newpress.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.text.DecimalFormat;

/**
 * @ClassName: CarSmallImage 
 * @Description: TODO(自定义一个车辆图片高度/宽度=1/2) 
 * @author shadow 
 * @date 2014-12-18 下午5:59:03 
 *
 */
public class ScaleImageView extends ImageView {

	private float scale=0.5f;//默认比例是宽比高2:1
	private String width="1";
	private String height="1";
	
	public ScaleImageView(Context context) {
		super(context);
	}
	 public ScaleImageView(Context context, float width, float height) {
	        super(context);
	        scale=height/width;
	    }
    public ScaleImageView(Context context, int horizontalSpacing, int verticalSpacing) {
        super(context);
    }


    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.shensou.newpress", "my_height");
        String width=attrs.getAttributeValue("http://schemas.android.com/apk/res/com.shensou.newpress", "my_width");
        
        DecimalFormat df=new DecimalFormat("0.00");
        String result=df.format((float)Integer.parseInt(height)/Integer.parseInt(width));
        scale = Float.parseFloat(result);
    }
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int mWidth = MeasureSpec.getSize(widthMeasureSpec);
		int mHeight = (int) (mWidth*scale);
		setMeasuredDimension(mWidth, mHeight);
	}
}
