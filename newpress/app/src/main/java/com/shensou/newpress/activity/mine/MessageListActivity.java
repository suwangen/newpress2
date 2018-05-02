package com.shensou.newpress.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.adapter.BaseLoadingAdapter;
import com.shensou.newpress.adapter.MessageAdapter;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.bean.MessageGson;
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
 * Created by fangzhenjian on 2016/2/4 0004.
 */
public class MessageListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    private View parentView;

    private UserInfoXML mUserInfoXML;
    private int p =0;
    private MessageAdapter mAdapter;
    private List<MessageGson.ResponseBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        toolbar_title.setText("消息");
        mUserInfoXML=UserInfoXML.getInstance(context);
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MessageAdapter(mList, context);
        mAdapter.setIsMultiType(true);
        mRecyclerView.setAdapter(mAdapter);
        //        recyclerHeader.attachTo(mRecyclerView);

        mAdapter.setIsLoadMore(true);
        mAdapter.setMoreListerner(new BaseLoadingAdapter.OnLoadMoreListerner() {
            @Override
            public void loadMore() {
                p++;
                getRongyuArticleList();
            }
        });
        mAdapter.setmOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MessageListActivity.this, ActivityDetailActivity.class);
                intent.putExtra("title","详情");
                intent.putExtra("url","http://jmjx.plwx.com/index.php/Home/Doubt/get_Push/push_id/"+mList.get(position).getId());
                startActivity(intent);
            }
        });
        getRongyuArticleList();
    }

    @Override
    public void onRefresh() {
        p=0;
        mAdapter.setIsLoadMore(true);
        getRongyuArticleList();
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

    private void getRongyuArticleList() {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("p", p + "")
                .add("uid",UserInfoXML.getInstance(context).getUid())
                .build();

        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.PUST_GET_WATCH)
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
                }
                Log.e("json", json);
                try {
                    MessageGson baseGson = JsonUtils.deserialize(json, MessageGson.class);
                    if (baseGson.getCode().equals("200")) {
                        if (baseGson.getResponse().size() < 10) {
                            //停止加载更多
                            mAdapter.setIsLoadMore(false);
                            if (p != 0) {
                                ToastUtil.showMyShortToast("没有更多了");
                            }
                        }

                        mList.addAll(baseGson.getResponse());
                        mAdapter.setDatas(mList);

                    } else {
                        ToastUtil.showMyShortToast(baseGson.getMessage());
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
