package com.shensou.newpress.widget.ptr;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 下拉控件
 * Created by LinXin on 2016/8/10 15:05.
 */
public class FMPtrFrameLayout extends PtrFrameLayout {
    public FMPtrFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public FMPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FMPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        disableWhenHorizontalMove(true);
        FMPtrHeader header = new FMPtrHeader(context);
        setHeaderView(header);
        addPtrUIHandler(header);
        setEnabledNextPtrAtOnce(true);
    }
}
