package com.shensou.newpress.activity.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.adapter.PopJobsClassifyAdapter;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.MenberTimeLengthGson;
import com.shensou.newpress.bean.OrderGson;
import com.shensou.newpress.bean.WeiXinPay;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.Constants;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.PopWindowUtil;
import com.shensou.newpress.utils.ToastUtil;
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
 * 会员充值
 */
public class MenberRechargeActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
    @Bind(R.id.tv_withdraw_money)
    TextView tv_withdraw_money;
    @Bind(R.id.tv_menber_time_length)
    TextView tv_menber_time_length;

    private String uid;//用户id
    private String money;//

    public final static String WEIXIN_PAY_RECEIVER="RECEIVER.ACTION.WEIXIN_PAY";
    private IWXAPI api;//微信
    private PayReceiver mReceiver;
    private String order_sn;
    private String time_id;
    private PopJobsClassifyAdapter popJobsClassifyAdapter;
    private List<MenberTimeLengthGson.ResponseBean> timeLengthList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menber_recharge);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tvTitle.setText("会员充值");
        uid = mUserInfoXML.getUid();

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.registerApp(Constants.WEIXIN_APP_ID);
        mReceiver=new PayReceiver();
        IntentFilter intentFilter=new IntentFilter(WEIXIN_PAY_RECEIVER);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, intentFilter);
        timeLengthList = new ArrayList<>();
        popJobsClassifyAdapter = new PopJobsClassifyAdapter(timeLengthList,context);

    }

    @OnClick({R.id.back,R.id.tv_menber_time_length,R.id.btn_submit})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                super.onBackPressed();
                break;
            case R.id.tv_menber_time_length:
                if(timeLengthList!=null&&timeLengthList.size()<=0){
                    getMenberChoose(v);
                }else {
                    new PopWindowUtil().initPopuptWindowJobsClassify(context, v, popJobsClassifyAdapter, new PopWindowUtil.HandlerItemCallBack2() {
                        @Override
                        public void handle(String s, int position) {
                            tv_menber_time_length.setText(timeLengthList.get(position).getTime_up());
                            time_id = timeLengthList.get(position).getId();
                            for(int i=0;i<timeLengthList.size();i++){
                                if(i==position){
                                    timeLengthList.get(i).setChoose(true);
                                }else {
                                    timeLengthList.get(i).setChoose(false);
                                }
                            }
                        }
                    });
                }
                break;
            case R.id.btn_submit:
                money = tv_withdraw_money.getText().toString();
                if(TextUtils.isEmpty(money)){
                    ToastUtil.showMyLongToast("请输入金额");
                    return;
                }
                recharge(mUserInfoXML.getUid(),money);
//                Intent intent = new Intent(RechargeActivity.this, PayCenterActivity.class);
//                startActivity(intent);
//                recharge(uid,money, TimeUtil.timeStamp());
                break;

        }

    }
    /**
     * 会员充值
     * @param uid
     * @param money
     */
    private void recharge( String uid,String money) {
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("uid",uid)
                .add("time_id",time_id)
                .add("money",money)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.ORDER_MEMBERPAY)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {

                try {
                    BaseGson result= JsonUtils.deserialize(response, BaseGson.class);
                    ToastUtil.showMyLongToast(result.getMsg());

                    if(result.getCode().equals("200")){
                        OrderGson orderGson= JsonUtils.deserialize(response, OrderGson.class);
                        order_sn = orderGson.getResponse().getOrder_sn();
                        postWeiXinPay();
                    }else{
                        dismissProgressDialog();
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 会员充值下拉选项
     */
    private void getMenberChoose(final View v) {
        showProgressDialog();
        RequestBody requestBody = new FormEncodingBuilder()
                .build();

        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.USERS_GETTIMELENGTH)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String json) {
                dismissProgressDialog();
                Log.e("json", json);
                if(timeLengthList!=null&&timeLengthList.size()>0)
                    timeLengthList.clear();
                try {
                    MenberTimeLengthGson baseGson = JsonUtils.deserialize(json, MenberTimeLengthGson.class);
                    if (baseGson.getCode().equals("200")) {
                        timeLengthList.addAll(baseGson.getResponse());
                        popJobsClassifyAdapter.setDatas(timeLengthList);
                        new PopWindowUtil().initPopuptWindowJobsClassify(context, v, popJobsClassifyAdapter, new PopWindowUtil.HandlerItemCallBack2() {
                            @Override
                            public void handle(String s, int position) {
                                tv_menber_time_length.setText(timeLengthList.get(position).getTime_up());
                                time_id = timeLengthList.get(position).getId();
                                for(int i=0;i<timeLengthList.size();i++){
                                    if(i==position){
                                        timeLengthList.get(i).setChoose(true);
                                    }else {
                                        timeLengthList.get(i).setChoose(false);
                                    }
                                }
                            }
                        });
                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(MenberRechargeActivity.this);
            int errCode=intent.getIntExtra("errCode",-3);
            if (errCode==0){
                //                builder.setTitle(R.string.app_tip);
                builder.setMessage("支付成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MenberRechargeActivity.this.setResult(4);
                        finish();
                    }
                });
            }else{
                builder.setMessage("支付失败，请重试");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        //                        intent.putExtra("order_id",order_id);
                        //                        startActivity(intent);
                        //                        finish();
                    }
                });
            }


            builder.show();
        }
    }
    /**
     * 微信支付下预订单
     */
    private void postWeiXinPay() {

        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("uid", mUserInfoXML.getUid())
                .add("record_sn",order_sn)
                .build();
        final Request request=new Request.Builder().post(requestBody).tag(this).url(URL.WXPAY_MEMBERPAY).build();

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
}
