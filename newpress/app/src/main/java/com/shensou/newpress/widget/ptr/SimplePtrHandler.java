package com.shensou.newpress.widget.ptr;

import android.view.View;

/**
 * 简单控制PtrHandler
 */
public class SimplePtrHandler implements PtrHandler{

    private final View mRecyclerView;

    /**
     * 设置是否可刷新
     */
    private boolean isCanDoRefresh = true;
    public SimplePtrHandler(View mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return isCanDoRefresh && PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }

    public void setCanDoRefresh(boolean canDoRefresh) {
        isCanDoRefresh = canDoRefresh;
    }
}