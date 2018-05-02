package com.shensou.newpress.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.mine.MyOrderActivity;
import com.shensou.newpress.adapter.BaseLoadingAdapter;
import com.shensou.newpress.adapter.MyOrderAdapter;
import com.shensou.newpress.bean.MyOrderGson;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.widget.dialog.DialogPasswardUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shensou.newpress.R.id.recycler_view;


/**
 * 我的订单
 */
public class MyOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final int REQUEST_PERMISSION=0X01;

    public static final int REQUEST_STORAGE_PERMISSION = 0X01;
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean freshThings;
    private List<MyOrderGson.ResponseBean> mList;
    private int p =0;
    private MyOrderAdapter mAdapter;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.lin_search)
    LinearLayout lin_search;
    @Bind(recycler_view)
    RecyclerView mRecyclerView;
    private int clickPos;



    private String pay_sn;
    private String pay_status;
    private String password;
    private DialogPasswardUtil dialogPasswardUtil;
    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        ButterKnife.bind(this, view);
        //        Tools.setStatusBarTranslucent(lin,getActivity());
        init();
        return view;
    }


    @Override
    public void onRefresh() {
        p=0;
        if(mAdapter!=null){//这个页面比较特殊，需加这个判断，因为(viewpager中)fragment有可能还没初始化，我就要调用这个方法，此时mAdapter=null
            mAdapter.setIsLoadMore(true);
            getLList(freshThings.getId()+"");
        }

    }
    private void stopRefresh(){
        if (mSwipeRefreshLayout!=null&&mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    public void setRefreshThingsBean(YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean bean){
        freshThings = bean;
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({
    })
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {
        }
    }

    private void init() {
        lin_search.setVisibility(View.GONE);
        mList = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);

        //        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(null);
        mAdapter = new MyOrderAdapter(mList, getContext());
        mAdapter.setIsMultiType(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setIsLoadMore(true);
        mAdapter.setOrderCallback(new MyOrderAdapter.OrderCallback() {
            @Override
            public void deleteOrCancelOrder(String order_id) {
                ((MyOrderActivity)getActivity()).refreshDataByOrderId(order_id);
            }
            @Override
            public void pay(final int position) {
                pay_sn = mList.get(position).getPay_sn();
                pay_status = mList.get(position).getPay_status();
                if(pay_status.equals("1")){//微信支付
                    ((MyOrderActivity)getActivity()).postWeiXinPay(pay_sn,mList.get(position).getOrder_id());
                }else if(pay_status.equals("5")||pay_status.equals("4")){//会员卡支付、钱包支付
                    dialogPasswardUtil = new DialogPasswardUtil(getContext(), new DialogPasswardUtil.CallBackConfirm2() {
                        @Override
                        public void confirm(String pwd) {
                            dialogPasswardUtil.dialogDismiss();
                            password = pwd;
                            if(pay_status.equals("5")){//会员卡支付
//                                ((MyOrderActivity)getActivity()).postMenberCardPay(pay_sn,password,mList.get(position).getOrder_id());
                            }else {//钱包支付
//                                ((MyOrderActivity)getActivity()).postWalletPay(pay_sn,password,mList.get(position).getOrder_id());
                            }

                        }
                        @Override
                        public void  cancel() {

                        }
                    });
                }
            }
            @Override
            public void applyDrawback(int position) {
            }
            @Override
            public void confirmGetGoods(String order_id) {
                ((MyOrderActivity)getActivity()).refreshOrderStatueConfirmGetGoods(order_id);
            }
        });
        mAdapter.setMoreListerner(new BaseLoadingAdapter.OnLoadMoreListerner() {
            @Override
            public void loadMore() {
                p++;
                getLList(freshThings.getId()+"");
            }
        });
        mAdapter.setmOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                clickPos = position;
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                intent.putExtra("order_id",mList.get(position).getOrder_id());
//                startActivityForResult(intent,3);
            }
        });
        getLList(freshThings.getId()+"");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==3&&resultCode==4){//删除或者取消订单
            ((MyOrderActivity)getActivity()).refreshDataByOrderId(mList.get(clickPos).getOrder_id());
        }else if(requestCode==3&&resultCode==5){//付款成功
            ((MyOrderActivity)getActivity()).refreshOrderStatueByOrderId(mList.get(clickPos).getOrder_id());
        }else if(requestCode==3&&resultCode==6){//申请退款成功
            ((MyOrderActivity)getActivity()).refreshDataByOrderId(mList.get(clickPos).getOrder_id());
        }else if(requestCode==3&&resultCode==7){//确认收货成功
            ((MyOrderActivity)getActivity()).refreshOrderStatueConfirmGetGoods(mList.get(clickPos).getOrder_id());
        }

    }
    public List<MyOrderGson.ResponseBean> getList(){
          return mList;
    }
    public MyOrderAdapter getAdapter(){
        return mAdapter;
    }
    /**
     * 需要存储权限
     *
     * @param
     */
    public void needStoragePermissions() {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PermissionUtils.hasPermissions(getActivity(), perms)) {

        } else {
            PermissionUtils.requestPermissions(this, getString(R.string.rationale_write), REQUEST_STORAGE_PERMISSION, perms);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /**
     * 获取列表
     * order_state 订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;50:退款售后
     */
    private void getLList(String id) {
        RequestBody requestBody = null;
        if(TextUtils.isEmpty(id)){
            requestBody = new FormEncodingBuilder()
                    .add("uid", "565")
                    .add("p", p+"")
                    .build();
        }else {
            requestBody = new FormEncodingBuilder()
                    .add("order_state", id)
                    .add("uid", "565")
                    .add("p", p+"")
                    .build();
        }


        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.APPOINTMENT_ORDERLIST)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                ToastUtil.showMyShortToast(R.string.network_anomaly);
                mAdapter.setIsLoadMore(false);
                stopRefresh();
            }

            @Override
            public void onResponse(String json) {
                stopRefresh();
                if(p==0&&mList!=null){
                    mList.clear();
                }
                Log.e("json", json);
                if(json==null||json.length()<=0||json.charAt(0)!='{'){
                    return;
                }
                try {
                    MyOrderGson result = JsonUtils.deserialize(json, MyOrderGson.class);
                    if (result.getCode().equals("200")) {
                        mList.addAll(result.getResponse());
                        mAdapter.setDatas(mList);
                        if (result.getResponse().size() < 10) {
                            //停止加载更多
                            mAdapter.setIsLoadMore(false);
                            if (p != 0) {
                                ToastUtil.showMyShortToast("没有更多了");
                            }
                        }else{
                            mAdapter.setIsLoadMore(true);
                        }



                    } else {
//                        ToastUtil.showMyShortToast(result.getMsg());
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
