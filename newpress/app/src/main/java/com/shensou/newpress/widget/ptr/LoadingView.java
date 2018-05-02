package com.shensou.newpress.widget.ptr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.shensou.newpress.R;
import com.shensou.newpress.utils.ScreenUtils;


/**
 * 再加视图
 * Created by LinXin on 2016/12/28 9:08.
 */
public class LoadingView extends View implements Runnable {

    private static final int INTERVAL = 150;

    private int colorS, colorN;
    private int dotSize, dotMargin, dotNum = 3, dotWidth;
    private Paint mPaint;
    private int current;
    private boolean isRunning = false;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        colorS = ContextCompat.getColor(context, R.color.main_color_pink);
        colorN = ContextCompat.getColor(context, R.color.bg_yellow);
        dotSize = ScreenUtils.dip2px(context, 8);
        dotMargin = ScreenUtils.dip2px(context, 3);
        dotWidth = dotSize * dotNum + dotMargin * (dotNum - 1);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int left = centerX - dotWidth / 2;
        int radius = dotSize / 2;
        for (int i = 0; i < dotNum; i++) {
            if (i == current) {
                mPaint.setColor(colorS);
            } else {
                mPaint.setColor(colorN);
            }
            int cx = left + (dotMargin + dotSize) * i + radius;
            canvas.drawCircle(cx, centerY, radius, mPaint);
        }
    }

    @Override
    public void run() {
        if (!isRunning)
            return;
        current++;
        if (current == dotNum) {
            current = 0;
        }
        postInvalidate();
        postDelayed(this, INTERVAL);
    }

    public void start() {
        isRunning = true;
        postDelayed(this, INTERVAL);
    }

    public void stop() {
        isRunning = false;
        removeCallbacks(this);
    }

    public void reset() {
        current = 0;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Drawable backDrawable = getBackground();
        int desiredWidth = dotWidth;
        int desiredHeight = dotSize;
        if (backDrawable != null) {
            desiredWidth = Math.max(desiredWidth, backDrawable.getIntrinsicWidth());
            desiredHeight = Math.max(desiredHeight, backDrawable.getIntrinsicHeight());
        }
        int width = getCustomViewSize(widthMode, widthSize, desiredWidth);
        int height = getCustomViewSize(heightMode, heightSize, desiredHeight);
        if (width < 0) {
            width = 0;
        }
        if (height < 0) {
            height = 0;
        }
        setMeasuredDimension(width, height);
    }

    private int getCustomViewSize(int mode, int size, int desiredSize) {
        int finalSize;
        if (mode == MeasureSpec.EXACTLY) {
            finalSize = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            finalSize = Math.min(desiredSize, size);
        } else {
            finalSize = desiredSize;
        }
        return finalSize;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }
}
