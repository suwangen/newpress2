package com.shensou.newpress.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.mine.ForgetPwdActivity;
import com.shensou.newpress.bean.LoginGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.RegexUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.io.IOException;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by fangzhenjian on 2016/2/4 0004.
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.login_edt_account)
    EditText edtAccount;//账户
    @Bind(R.id.login_edt_pwd)
    EditText edtPwd;//密码
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.tv_login_type_accont)
    TextView tvLoginTypeAccont;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private UserInfoXML mUserInfoXML;
    //    private PushInfoXML pushInfoXML;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        //        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initToolBar();
        initView();
    }

    private void initToolBar() {

    }

    private void initView() {

        mUserInfoXML = UserInfoXML.getInstance(this);
        toolbarTitle.setText("");
        right.setText("注册");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            edtAccount.setText(mUserInfoXML.getusername());
            edtPwd.setText(mUserInfoXML.getPwd());
        }else {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
    @OnClick({R.id.login_btn_login, R.id.login_tv_forget_pwd, R.id.back, R.id.right,
            R.id.iv_login_qq,R.id.iv_login_wechat
    })
    public void onClick(View view) {
        Intent intent = null;
        String mobile = edtAccount.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.iv_login_wechat:

                UMShareAPI.get(context).doOauthVerify((Activity) context, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_login_qq:
                UMShareAPI.get(context).doOauthVerify((Activity) context, SHARE_MEDIA.QQ, authListener);

                break;
            case R.id.right:
                intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.login_btn_login://登录

                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showMyLongToast("请输入手机号码");
                    return;
                }
                if (!checkMobile(mobile)) {
                    return;
                }

                login(mobile, pwd,"","");
                break;
            case R.id.login_tv_forget_pwd:
                //忘记密码
                intent = new Intent(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            ToastUtil.showMyLongToast("成功了");
            UMShareAPI.get(context).getPlatformInfo((Activity) context, platform, authListener2);
            //            tv_auth_data.setText(data.toString());
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.showMyLongToast("失败：" + t.getMessage());
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.showMyLongToast("取消了" );
        }
    };
    UMAuthListener authListener2 = new UMAuthListener() {
        /**
         * @desc 读取数据授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            //            openid = data.get("openid");
            //            screen_name = data.get("screen_name");
            //            iconurl = data.get("iconurl");
            Log.e("swg",data.toString());

            threeLogin(data.get("openid"),data.get("screen_name"),data.get("screen_name"),data.get("iconurl"));
//            edtAccount.setText(data.get("openid"));
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.showMyLongToast("失败：" + t.getMessage() );
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.showMyLongToast("取消了");
        }
    };
    /**
     * @return void    返回类型
     * @Description: TODO(校验手机号码)
     * param     设定文件
     */
    private boolean checkMobile(String mobile) {
        //校验手机号码
        String msg = RegexUtils.checkMobile(mobile);
        if (!msg.equals("")) {
            ToastUtil.showMyLongToast(msg);
            return false;
        } else
            return true;
    }

    /**
     * 登陆
     * param articleId
     */
    private void login(final String mobile, final String pwd,String qqlogin,String sinaUid) {
        //        loading.start();
        showProgressDialog();
        RequestBody requestBody = null;
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if(!TextUtils.isEmpty(qqlogin)){
            builder.add("qqlogin", qqlogin);
        }
        if(!TextUtils.isEmpty(sinaUid)){
            builder.add("sinaUid", sinaUid);
        }
        if(!TextUtils.isEmpty(mobile)){
            builder.add("username", mobile);
        }
        if(!TextUtils.isEmpty(pwd)){
            builder.add("password", pwd);
        }
        requestBody =  builder
                .build();



        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.POST_LOGIN)
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
                try {
                    LoginGson baseGson = JsonUtils.deserialize(json, LoginGson.class);
                    ToastUtil.showMyLongToast(baseGson.getGuiboweb());
                    if (baseGson.getResult().equals("1")) {
                        mUserInfoXML.setlogin_json(json);
                        mUserInfoXML.setPwd(pwd);
                        mUserInfoXML.setusername(mobile);
                        mUserInfoXML.setuserid(baseGson.getUserid());
                        mUserInfoXML.setis_change_statue("2");
                        finish();
                    } else {
                        dismissProgressDialog();
                    }

                } catch (Exception e) {

                }
            }
        });

    }

    /**
     * 登陆
     * param articleId
     */
    private void threeLogin(String openid,String username,String nickname,String head_img) {
        //        loading.start();
        showProgressDialog();
        RequestBody requestBody = null;
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if(!TextUtils.isEmpty(openid)){
            builder.add("openid", openid);
        }
        if(!TextUtils.isEmpty(username)){
            builder.add("username", username);
        }
        if(!TextUtils.isEmpty(nickname)){
            builder.add("nickname", nickname);
        }
        if(!TextUtils.isEmpty(head_img)){
            builder.add("head_img", head_img);
        }
        requestBody =  builder
                .build();



        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.POST_TREELOGIN)
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
                Log.e("swg", json);
                try {
                    LoginGson baseGson = JsonUtils.deserialize(json, LoginGson.class);
                    ToastUtil.showMyLongToast(baseGson.getGuiboweb());
                    if (baseGson.getResult().equals("1")) {
                        mUserInfoXML.setlogin_json(json);
//                        mUserInfoXML.setPwd("");
//                        mUserInfoXML.setusername(mobile);
                        mUserInfoXML.setuserid(baseGson.getUserid());
                        mUserInfoXML.setis_change_statue("2");
                        finish();
                    } else {
                        dismissProgressDialog();
                    }

                } catch (Exception e) {

                }
            }
        });

    }
}
