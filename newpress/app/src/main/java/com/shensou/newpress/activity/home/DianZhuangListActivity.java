package com.shensou.newpress.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.adapter.BaseLoadingAdapter;
import com.shensou.newpress.adapter.DianzhuangAdapter;
import com.shensou.newpress.bean.VisitPeopleGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shensou.newpress.R.id.swipeRefreshLayout;

/**
 * 电桩列表
 */
public class DianZhuangListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private View parentView;

    private UserInfoXML mUserInfoXML;
    private int p =0;
    private DianzhuangAdapter mAdapter;
    private List<VisitPeopleGson.ResponseBean> mList;
    private int clickPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianzhuang_list);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        initView();
        mSwipeRefreshLayout.setPadding(0,0,0,Tools.getNavigationBarHeight(context));//虚拟底部按键高度
    }


    private void initView() {
        mUserInfoXML=UserInfoXML.getInstance(context);
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new DianzhuangAdapter(mList, context);
        mAdapter.setIsMultiType(true);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setIsLoadMore(true);
        mAdapter.setMoreListerner(new BaseLoadingAdapter.OnLoadMoreListerner() {
            @Override
            public void loadMore() {
                p++;
                getVisitList();
            }
        });
        mAdapter.setmOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                clickPos = position;
            }
        });
        getVisitList();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==4){
//            mList.get(clickPos).setIs_collect("1");
            mAdapter.notifyItemChanged(clickPos);
        }
    }
    @Override
    public void onRefresh() {
        p=0;
//        mRecyclerView.setLoadingMoreEnabled(true);
        mAdapter.setIsLoadMore(true);
        getVisitList();
    }
    private void stopRefresh(){
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    private void getVisitList() {
        showProgressDialog();
        RequestBody requestBody = null;
        FormEncodingBuilder  formEncodingBuilder =new FormEncodingBuilder();

        requestBody = formEncodingBuilder
                .add("p", p + "")
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.APPINTERFACE_GETPILE)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
                mAdapter.setIsLoadMore(false);
                stopRefresh();
            }

            @Override
            public void onResponse(String json) {
                dismissProgressDialog();
                stopRefresh();
                if (p == 0 && mList != null) {
                    mList.clear();
                }
                Log.e("json", json);
                try {
                    VisitPeopleGson result = JsonUtils.deserialize(json, VisitPeopleGson.class);
                    if (result.getCode().equals("200")) {
                        if (result.getResponse().size() < 10) {
                            //停止加载更多
                            mAdapter.setIsLoadMore(false);
                            if (p != 0) {
                                ToastUtil.showMyShortToast("没有更多了");
                            }
                        }

                        mList.addAll(result.getResponse());
                        mAdapter.setDatas(mList);

                    } else {
                        ToastUtil.showMyShortToast(result.getMsg());
                        mAdapter.setIsLoadMore(false);
                        mAdapter.setDatas(mList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //    }

    }

}
