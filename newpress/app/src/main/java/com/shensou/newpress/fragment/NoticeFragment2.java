package com.shensou.newpress.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.adapter.BaseLoadingAdapter;
import com.shensou.newpress.adapter.NoticeAdapter;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.NoticeGson;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class NoticeFragment2 extends BaseFragment  implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.back)
    ImageView back;
    private View parentView;

    private UserInfoXML mUserInfoXML;

    private int p =0;
    private NoticeAdapter mAdapter;
    private List<NoticeGson.ResponseBean> mList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_notice2, null);

        ButterKnife.bind(this, parentView);
        initView();
        return parentView;
    }


    private void initView(){
        toolbar_title.setText("通知");
        back.setVisibility(View.GONE);
        mUserInfoXML=UserInfoXML.getInstance(getActivity());



        mList = new ArrayList<>();
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //设置第一项独占一行
                return mAdapter.isHeader(position)?gridLayoutManager.getSpanCount():1;
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new NoticeAdapter(mList, getContext(),getActivity().getSupportFragmentManager().beginTransaction());
        mAdapter.setIsMultiType(true);
        mRecyclerView.setAdapter(mAdapter);
        //        recyclerHeader.attachTo(mRecyclerView);

        mAdapter.setIsLoadMore(true);
        mAdapter.setMoreListerner(new BaseLoadingAdapter.OnLoadMoreListerner() {
            @Override
            public void loadMore() {
                p++;
                getList();
            }
        });
        mAdapter.setmOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position!=0){
                    Intent intent = new Intent(getContext(),ActivityDetailActivity.class);
                    intent.putExtra("url","http://jmjx.plwx.com/index.php/Home/Article/tongzhi/id/"+mList.get(position).getId()+".html");
                    intent.putExtra("title",toolbar_title.getText().toString());
                    startActivity(intent);
                }

            }
        });
        getList();
    }

    @Override
    public void onRefresh() {
        p=0;
        mAdapter.setIsLoadMore(true);
        getList();
    }
    private void stopRefresh(){
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {

        }
    }

    private void getList() {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("p", p + "")
                .build();

        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.ARTICLE_GETNEWTARTICLE)
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
                if(p==0&&mList!=null){
                    mList.clear();
                    mList.add(new NoticeGson.ResponseBean());
                }
                Log.e("json", json);
                try {
                    BaseGson baseGson = JsonUtils.deserialize(json, BaseGson.class);
                    if (baseGson.getCode().equals("200")) {
                        NoticeGson result = JsonUtils.deserialize(json, NoticeGson.class);
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
//                        ToastUtil.showMyShortToast(baseGson.getMsg());
                        mAdapter.setIsLoadMore(false);
                        mAdapter.setDatas(mList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
