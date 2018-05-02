package com.shensou.newpress.widget.ptr;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shensou.newpress.R;


/**
 * 男人帮刷新头部控件
 * Created by LinXin on 2016/8/10 16:01.
 */
public class FMPtrHeader extends FrameLayout implements PtrUIHandler {
    private LoadingView mLoadingView;
    private TextView mTextView;
    @StringRes
    private int mRefreshSuccessLabel;
    @StringRes
    private int mRefreshFailLabel;

    public FMPtrHeader(Context context) {
        super(context);
        initViews(context);
    }

    public FMPtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public FMPtrHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    protected void initViews(Context context) {
        mRefreshSuccessLabel = R.string.refresh_success;
        mRefreshFailLabel = R.string.refresh_failed;
        inflate(context, R.layout.layout_base_ptr_header, this);
        mTextView = (TextView) findViewById(R.id.layout_base_ptr_header_tv);
        mLoadingView = (LoadingView) findViewById(R.id.layout_base_ptr_header_loading_view);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mLoadingView.reset();
        mTextView.setText(R.string.pull_to_refresh_1);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mTextView.setText(R.string.pull_to_refresh_1);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mLoadingView.start();
        mTextView.setText(R.string.pull_to_refresh_3);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (isUnderTouch) {
            if (ptrIndicator.isOverOffsetToRefresh()) {
                mTextView.setText(R.string.pull_to_refresh_2);
            } else {
                mTextView.setText(R.string.pull_to_refresh_1);
            }
        }
    }

    @Override
    public void onUIRefreshSuccess(PtrFrameLayout frame, boolean success) {
        mLoadingView.stop();
        if (success) {
            mTextView.setText(mRefreshSuccessLabel);
        } else {
            mTextView.setText(mRefreshFailLabel);
        }
    }
}