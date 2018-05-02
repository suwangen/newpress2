package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.adapter.MyOrderPagerAdapter;
import com.shensou.newpress.bean.WeiXinPay;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.fragment.MyOrderFragment;
import com.shensou.newpress.gobal.Constants;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.widget.CustomViewPager;
import com.shensou.newpress.widget.DepthPageTransformer;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 * Created by fangzhenjian on 2016/2/4 0004.
 */
public class MyOrderActivity extends BaseActivity {

    @Bind(R.id.news_pager1)
    CustomViewPager customViewPager;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private View parentView;

    private UserInfoXML mUserInfoXML;
    private int p =0;
    private String uid;

    private List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> hlList;
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean activeTypeBean;
    private String top_id;
    private List<MyOrderFragment> fragmentList;
    private MyOrderFragment myOrderFragment;
    private MyOrderPagerAdapter myOrderPagerAdapter;

    public final static String WEIXIN_PAY_RECEIVER="RECEIVER.ACTION.WEIXIN_PAY";
    private IWXAPI api;//微信
    private PayReceiver mReceiver;
    private String order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initView();
    }


    private void initView() {
        toolbar_title.setText("我的订单");
        mUserInfoXML = UserInfoXML.getInstance(context);
        uid = mUserInfoXML.getUid();
        hlList = new ArrayList<>();
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("全部");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("10");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("已完成");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("20");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("未完成");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("30");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("待评论");
        hlList.add(activeTypeBean);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.registerApp(Constants.WEIXIN_APP_ID);
        mReceiver=new PayReceiver();
        IntentFilter intentFilter=new IntentFilter(WEIXIN_PAY_RECEIVER);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, intentFilter);
        initFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null){
            unregisterReceiver(mReceiver);
        }
    }

    @OnClick({R.id.back
    })
    public void onClick(View view) {
        Intent intent =null;
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;

        }
    }


    /**
     * 删除或者取消订单的时候更新其他fragment对应order_id的订单数据
     * @param order_id
     */
    public void refreshDataByOrderId(String order_id){
        for(int i=0;i<fragmentList.size();i++){
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    fragmentList.get(i).getList().remove(j);
                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 付款成功后更新其他fragment对应order_id的订单数据
     * @param order_id
     */

    public void refreshOrderStatueByOrderId(String order_id){
        for(int i=0;i<2;i++){//只要判断全部、未付款 两个列表
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    //订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成
                    fragmentList.get(i).getList().get(j).setOrder_state("20");
                    if(i==1){//待付款
                        if(fragmentList.get(2).getList()!=null){//付款成功直接加入待发货
                            fragmentList.get(2).getList().add(0,fragmentList.get(i).getList().get(j));
                            fragmentList.get(2).getAdapter().notifyDataSetChanged();
                        }
                        fragmentList.get(i).getList().remove(j);
                    }
                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 确认收货
     * @param order_id
     */
    public void refreshOrderStatueConfirmGetGoods(String order_id){
        for(int i=0;i<fragmentList.size();i++){//只要判断待发货、已完成 两个列表
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    //订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成
                    fragmentList.get(i).getList().get(j).setOrder_state("40");
                    if(i==3){//待发货
                        if(fragmentList.get(4).getList()!=null){//确认收货后直接加入已完成
                            fragmentList.get(4).getList().add(0,fragmentList.get(i).getList().get(j));
                            fragmentList.get(4).getAdapter().notifyDataSetChanged();
                        }
                        fragmentList.get(i).getList().remove(j);
                    }

                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {

        if(fragmentList!=null){
            fragmentList.clear();
        }else{
            fragmentList = new ArrayList<>();
        }

        int count = hlList.size();
        for (int i = 0; i < count; i++) {
            myOrderFragment = MyOrderFragment.newInstance();
            myOrderFragment.setRefreshThingsBean(hlList.get(i));
            fragmentList.add(myOrderFragment);
        }
        //        customViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        myOrderPagerAdapter = new MyOrderPagerAdapter(getSupportFragmentManager(),fragmentList,hlList);
        customViewPager.setAdapter(myOrderPagerAdapter);
        customViewPager.setPageTransformer(true, new DepthPageTransformer());
        tablayout.setupWithViewPager(customViewPager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);

        customViewPager.setCurrentItem(0);

    }


    /**
     * 微信支付下预订单
     */
    public void postWeiXinPay(String pay_sn,String order_id) {
        this.order_id = order_id;
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("token", mUserInfoXML.getuserid())
                .add("pay_sn",pay_sn)
                .build();
        final Request request=new Request.Builder().post(requestBody).tag(this).url(URL.WXPAY_GETINDEX).build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyLongToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String json) {
                dismissProgressDialog();
                Log.e("json", json);
                try {
                    WeiXinPay result = JsonUtils.deserialize(json, WeiXinPay.class);
                    if (result.getCode()==200) {

                        WeiXinPay.WeiXinData data=result.getResponse();
                        PayReq req = new PayReq();
                        //                            req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId			= data.getAppid();
                        req.partnerId		= data.getPartnerid();
                        req.prepayId		= data.getPrepayid();
                        req.nonceStr		= data.getNoncestr();
                        req.timeStamp		= data.getTimestamp();
                        req.packageValue	= "Sign=WXPay";
                        req.sign			= data.getSign();
                        req.extData			= "app data"; // optional
                        //                            ToastUtil.Short("正常调起支付");
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.registerApp(Constants.WEIXIN_APP_ID);
                        api.sendReq(req);


                    } else if (result.getCode()==-1) {
                        ToastUtil.showMyLongToast(result.getMsg());
                    }else {
                        ToastUtil.showMyLongToast(result.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        });
    }
    /**
     * 微信支付结果提示
     */
    public class PayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            int errCode=intent.getIntExtra("errCode",-3);
            if (errCode==0){
                //                builder.setTitle(R.string.app_tip);
                builder.setMessage("支付成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refreshOrderStatueByOrderId(order_id);
                        dialog.dismiss();
                    }
                });
            }else{
                builder.setMessage("支付失败，请重试");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }


            builder.show();
        }
    }
}
