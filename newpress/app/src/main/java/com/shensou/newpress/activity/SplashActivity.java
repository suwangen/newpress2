package com.shensou.newpress.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.home.MainActivity;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.Constants;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.permission.AfterPermissionGranted;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.SpUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @desc 启动屏
 * Created by devilwwj on 16/1/23.
 */
public class SplashActivity extends BaseActivity  {
    @Bind(R.id.et_invite_code)
    EditText et_invite_code;

    public static final int REQUEST_STORAGE_PERMISSION=0X01;
    private String inviteCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());//崩溃信息收集
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, Constants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
//        if (!isFirstOpen) {
//            Intent intent = new Intent(this, WelcomeGuideActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        enterHomeActivity();

//        needLocationPermissions();
    }

    private void enterHomeActivity() {
        Intent intent;
        if (Tools.checkUserLogin(getApplicationContext())) {
            intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @OnClick({R.id.tv_splash_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_splash_open:
                inviteCode = et_invite_code.getText().toString().trim();
                if(TextUtils.isEmpty(inviteCode)){
                    ToastUtil.showMyLongToast("请输入邀请码");
                    return;
                }
                getCode(inviteCode);

                break;
        }
    }
    /**
     * 需要定位权限
     * @param
     */
    public void needLocationPermissions() {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PermissionUtils.hasPermissions(this, perms)) {

        } else {
            PermissionUtils.requestPermissions(this, getString(R.string.rationale_write), REQUEST_STORAGE_PERMISSION, perms);
        }
    }

    /**
     *获取定位权限后定位
     */
    @AfterPermissionGranted(REQUEST_STORAGE_PERMISSION)
    private void onPermissionGranted() {

    }


    private void getCode(String code){
        //        loading.start();
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("code", code)
                .build();

        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.POST_CODE)
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
                    dismissProgressDialog();
                    Log.e("response", response);
                    BaseGson baseGson = JsonUtils.deserialize(response, BaseGson.class);

                    if(baseGson.getCode().equals("200")){
                        Intent intent = new Intent(context,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtil.showMyLongToast(baseGson.getMsg());
                    }
                } catch (Exception e) {

                }
            }
        });
    }
}
