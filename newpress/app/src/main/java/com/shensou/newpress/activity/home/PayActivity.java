package com.shensou.newpress.activity.home;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.bean.WeiXinPay;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.Constants;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.widget.dialog.DialogPasswardUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商店会员卡支付
 */
public class PayActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.iv_ac_visit_people_photo)
    ImageView ivAcVisitPeoplePhoto;
    @Bind(R.id.tv_order_confirm_money)
    TextView tv_order_confirm_money1;
    @Bind(R.id.tv_order_confirm_wechat)
    TextView tvOrderConfirmWechat;
    @Bind(R.id.tv_order_confirm_zhifubao)
    TextView tvOrderConfirmZhifubao;
    @Bind(R.id.tv_order_confirm_wallet)
    TextView tvOrderConfirmWallet;
    @Bind(R.id.tv_order_confirm_commit)
    TextView tvOrderConfirmCommit;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private View parentView;

    private int p = 0;
    private String uid;
    private String pay_type;//支付方式 1微信 2支付宝 3信用卡 4钱包 5会员卡

    public final static String WEIXIN_PAY_RECEIVER="RECEIVER.ACTION.WEIXIN_PAY";
    private IWXAPI api;//微信
    private PayReceiver mReceiver;
    private String pay_sn;
    private String order_id;
    private DialogPasswardUtil dialogPasswardUtil;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initView();
    }


    private void initView() {

        toolbar_title.setText("支付");
        uid = mUserInfoXML.getUid();
//        ImageLoadProxy.displayImageWithLoadingPicture(thum,
//                ivAcVisitPeoplePhoto, R.drawable.default_load_img);

        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.registerApp(Constants.WEIXIN_APP_ID);
        mReceiver=new PayReceiver();
        IntentFilter intentFilter=new IntentFilter(WEIXIN_PAY_RECEIVER);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver, intentFilter);
    }

    @OnClick({R.id.back, R.id.tv_order_confirm_wechat, R.id.tv_order_confirm_zhifubao
            , R.id.tv_order_confirm_wallet,R.id.tv_order_confirm_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_order_confirm_commit://提交
                if(TextUtils.isEmpty(pay_type)){
                    ToastUtil.showMyLongToast("请选择支付方式");
                    return;
                }
//                if(pay_type.equals("4")&&mUserInfoXML.getis_pay_pwd().equals("0")){//0没有设置过密码 1有
//                    ToastUtil.showMyLongToast("请先设置支付密码");
//                    Intent payIntent = new Intent(context, SetPayPwdActivity.class);
//                    startActivity(payIntent);
//                    return;
//                }
//                commitOrder();
                break;
            case R.id.tv_order_confirm_wechat://微信
                pay_type = "1";
                tvOrderConfirmWechat.setSelected(true);
                tvOrderConfirmZhifubao.setSelected(false);
                tvOrderConfirmWallet.setSelected(false);
                break;
            case R.id.tv_order_confirm_zhifubao://支付宝
                pay_type = "2";
                tvOrderConfirmWechat.setSelected(false);
                tvOrderConfirmZhifubao.setSelected(true);
                tvOrderConfirmWallet.setSelected(false);
                break;
            case R.id.tv_order_confirm_wallet://yesno1钱包
                pay_type = "4";
                tvOrderConfirmWechat.setSelected(false);
                tvOrderConfirmZhifubao.setSelected(false);
                tvOrderConfirmWallet.setSelected(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    private void commitOrder() {
//
//        RequestBody requestBody = null;
//        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
//        requestBody = formEncodingBuilder
//                .add("uid", uid)
//                .add("id", id)
//                .add("pay_type", pay_type)
//                .add("money", sale_money).build();
//
//
//        final Request request = new Request.Builder()
//                .post(requestBody)
//                .tag(this)
//                .url(URl.USERCARD_BUYCARD)
//                .build();
//
//        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                dismissProgressDialog();
//                ToastUtil.showMyShortToast(R.string.network_anomaly);
//            }
//
//            @Override
//            public void onResponse(String json) {
//                dismissProgressDialog();
//                Log.e("json", json);
//                try {
//                    OrderCommitGson result = JsonUtils.deserialize(json, OrderCommitGson.class);
//                    ToastUtil.showMyShortToast(result.getMsg());
//                    if (result.getCode().equals("200")) {
//                        pay_sn = result.getResponse().getPay_sn();
//                        order_id = result.getResponse().getOrder_id();
//                        if(pay_type.equals("1")){
//                            postWeiXinPay();
//                        }else if(pay_type.equals("4")){
//                            dialogPasswardUtil = new DialogPasswardUtil(context, new DialogPasswardUtil.CallBackConfirm2() {
//                                @Override
//                                public void confirm(String pwd) {
//                                    password = pwd;
//                                    postWalletPay();
//
//                                }
//                                @Override
//                                public void  cancel() {
////                                    Intent intent = new Intent(context, OrderDetailActivity.class);
////                                    intent.putExtra("order_id",order_id);
////                                    startActivity(intent);
////                                    finish();
//                                }
//                            });
//                        }
//
//                    } else {
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        //    }
//
//    }
//    /**
//     * 钱包支付
//     */
//    private void postWalletPay() {
//
//        showProgressDialog();
//        RequestBody requestBody=new FormEncodingBuilder()
//                .add("token", mUserInfoXML.getToken())
//                .add("pay_sn",pay_sn)
//                .add("password",password)
//                .build();
//        final Request request=new Request.Builder().post(requestBody).tag(this).url(URl.WALLET_BUYCARD).build();
//
//        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                dismissProgressDialog();
//                ToastUtil.showMyLongToast(R.string.network_anomaly);
//            }
//
//            @Override
//            public void onResponse(String json) {
//                dismissProgressDialog();
//                Log.e("json", json);
//                try {
//                    BaseGson result = JsonUtils.deserialize(json, BaseGson.class);
//                    ToastUtil.showMyLongToast(result.getMsg());
//                    if (result.getCode().equals("200")) {
//                        dialogPasswardUtil.dialogDismiss();
//                        finish();
//                    } else if (result.getCode().equals("-1")) {
//
//                    }else {
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//        });
//    }
    /**
     * 微信支付下预订单
     */
    private void postWeiXinPay() {

        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("pay_sn",pay_sn)
                .build();
        final Request request=new Request.Builder().post(requestBody).tag(this).url(URL.WXPAY_BUYCARD).build();

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
                        ToastUtil.showMyShortToast(result.getMsg());
                    }else {
                        ToastUtil.showMyShortToast(result.getMsg());
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
            int errCode=intent.getIntExtra("errCode",-3);
            if (errCode==0){
                //                builder.setTitle(R.string.app_tip);
                builder.setMessage("支付成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
                        finish();
                    }
                });
            }


            builder.show();
        }
    }
}
