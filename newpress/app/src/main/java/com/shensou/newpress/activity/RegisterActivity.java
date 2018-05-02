package com.shensou.newpress.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.RegistGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.RegexUtils;
import com.shensou.newpress.utils.TimerTaskUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shensou.newpress.R.id.register_btn_security_code;

/**
 * Created by fangzhenjian on 2016/2/4 0004.
 */
public class RegisterActivity extends BaseActivity {


    @Bind(R.id.toolbar_title)
    TextView tvTitle;
    @Bind(R.id.right)
    TextView right;
    //    @Bind(R.id.toolbar)
    //    Toolbar toolbar;
    @Bind(R.id.register_edt_phone)
    EditText edtPhone;
    @Bind(R.id.register_btn_security_code)
    TextView btnCode;
    @Bind(R.id.register_edt_code)
    EditText edtCode;
    @Bind(R.id.register_edt_pwd)
    EditText edtPwd;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private int totalCount = 0;
    //    @Bind(R.id.loading)
    //    RotateLoading loading;

    //    private PushInfoXML pushInfoXML;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
//        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initToolBar();
        initView();
    }

    private void initToolBar() {
        //        toolbar.setTitle("");
        //        setSupportActionBar(toolbar);
        tvTitle.setText("");
        right.setText("登录");
        //        btnCode.setClickable(false);

    }

    private void initView() {
        edtPhone.addTextChangedListener(edtChangeListener);
        //        pushInfoXML=PushInfoXML.getInstance(this);

    }


    @OnClick({R.id.back, R.id.register_btn_submit, R.id.right, R.id.register_btn_security_code})
    public void onClick(View view) {
        String mobile = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right:
                //登录
                finish();
                break;
            case register_btn_security_code:
                //获取验证码
                if (!RegexUtils.MobileIsRight(mobile)) {
                    ToastUtil.showMyLongToast(RegexUtils.checkMobile(mobile));
                    return;
                }
                getCode(mobile);
                break;
            case R.id.register_btn_submit:
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showMyLongToast("请输入您的手机号");
                    return;
                }
                if (!RegexUtils.MobileIsRight(mobile)) {
                    ToastUtil.showMyLongToast(RegexUtils.checkMobile(mobile));
                    return;
                }
//                if (TextUtils.isEmpty(code)) {
//                    ToastUtil.showMyLongToast(R.string.input_security_code);
//                    return;
//                }

                if (TextUtils.isEmpty(pwd)) {
                    showToast("请输入密码");
                    return;
                }
                if (!RegexUtils.checkPassWord(pwd)) {
                    showToast("请输入6-18位数字或字母的密码");
                    return;
                }

//                if (TextUtils.isEmpty(confirmPwd)) {
//                    showToast("请输入确认密码");
//                    return;
//                }
//                if (!RegexUtils.checkPassWord(confirmPwd)) {
//                    showToast("请输入6-18位数字或字母的确认密码");
//                    return;
//                }
//
//                if (!pwd.equals(confirmPwd)) {
//                    ToastUtil.showMyLongToast("两次密码输入不一致，请重新输入！");
//                    edtConfrimPwd.setText("");
//                    return;
//                }

                postRegister( mobile, code, pwd);
                break;
        }
    }


    //手机号码变化监听
    private TextWatcher edtChangeListener = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            //当手机校验成功时颜色状态变化
            if (totalCount <= 0) {
                String mobile = edtPhone.getText().toString().trim();
                if (mobile.length() == 11) {
                    if (RegexUtils.MobileIsRight(mobile)) {
                        btnCode.setClickable(true);
                        btnCode.setBackgroundResource(R.drawable.shape_out_blue_inner_blue);
                        btnCode.setTextColor(getResources().getColor(R.color.text_color_white));
                    }
                } else {
                    //					btnCode.setText(null);
                    btnCode.setClickable(false);
                    btnCode.setBackgroundResource(R.drawable.shape_out_grey_inner_grey);
                    btnCode.setTextColor(getResources().getColor(R.color.text_color_gray));
                    if (TextUtils.isEmpty(mobile)) {
                        btnCode.setClickable(true);
                    }
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };


    /**
     * @param mobile
     * @param code
     * @param pwd
     */
    private void postRegister(String mobile, String code,final String pwd) {
        showProgressDialog();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("username", mobile)
                .add("password", pwd)
//                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.POST_REGISTER)
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
                    RegistGson baseGson = JsonUtils.deserialize(response, RegistGson.class);
                    ToastUtil.showMyLongToast(baseGson.getGuiboweb());
                    if (baseGson.getResult().equals("1")){
                        UserInfoXML.getInstance(context).setusername(baseGson.getUsername());
                        UserInfoXML.getInstance(context).setPwd(pwd);
                        setResult(2);
                        finish();
                    }


                } catch (Exception e) {

                }
            }
        });


    }

    /**
     * 注册
     * param articleId
     */
    private void getCode(String mobile) {
        //        loading.start();
        showProgressDialog();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("phone", mobile)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.GET_CODE)
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
                    ToastUtil.showMyLongToast(baseGson.getMsg());
                    totalCount = 60;

                    if (baseGson.getCode().equals("200")) {
                        btnCode.setClickable(false);
                        btnCode.setTextColor(getApplicationContext().getResources().getColor(R.color.text_color_gray));
                        btnCode.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.shape_out_grey_inner_grey));
                        TimerTaskUtils.getInstance().startTimer(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (totalCount > 0) {
                                            btnCode.setText("短信已发送" + "(" + totalCount + "s)");
                                        } else {
                                            TimerTaskUtils.getInstance().cancelTimer();

                                            if (RegexUtils.MobileIsRight(edtPhone.getText().toString().trim())) {
                                                btnCode.setClickable(true);
                                                btnCode.setText(getApplicationContext().getResources().getString(R.string.get_security_code));
                                                btnCode.setBackgroundResource(R.drawable.shape_out_blue_inner_blue);
                                                btnCode.setTextColor(getResources().getColor(R.color.text_color_white));

                                            } else {
                                                btnCode.setClickable(false);
                                                btnCode.setTextColor(getApplicationContext().getResources().getColor(R.color.text_color_gray));
                                                btnCode.setText(getApplicationContext().getResources().getString(R.string.get_security_code));
                                                btnCode.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.shape_out_grey_inner_grey));
                                            }
                                        }
                                        totalCount--;
                                    }
                                });
                            }
                        }, 0, 1000);
                    }
                    //                    if(baseGson.getMsg().contains("账号已存在，请直接登录")){
                    //                        edtPhone.setText("");
                    //                    }
                } catch (Exception e) {

                }
            }
        });
    }

}
