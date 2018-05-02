package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.CommonGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
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


/**
 * Created by fangzhenjian on 2016/3/3 0003.
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
    @Bind(R.id.find_pwd_btn_security_code)
    TextView find_pwd_btn_security_code;
    @Bind(R.id.find_pwd_edt_phone)
    EditText find_pwd_edt_phone;
    @Bind(R.id.find_pwd_edt_code)
    EditText find_pwd_edt_code;
    @Bind(R.id.find_pwd_edt_pwd)
    EditText find_pwd_edt_pwd;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private String mobile;
    private String code;
    private int totalCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forget_pwd);
        setContentView(R.layout.activity_find_pwd2);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
//        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initToolbar();
        init();
    }

    private void initToolbar(){
        tvTitle.setText(R.string.find_pwd);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
    }

    private void init(){
//        getCodeFragment=new GetCodeFragment();
//        getCodeFragment.setGetCodeListener(this);
//        verifyMobileFragment=new VerifyMobileFragment();
//        verifyMobileFragment.setVerifyMobileListener(this);
//        resetPwdFragment=new ResetPwdFragment();
//
//        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.pwd_content,getCodeFragment).commit();
        find_pwd_edt_phone.addTextChangedListener(edtChangeListener);
    }

    @OnClick({R.id.back, R.id.find_pwd_btn_security_code,R.id.find_pwd_btn_submit})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                super.onBackPressed();
                break;
            case R.id.find_pwd_btn_security_code:
                mobile=find_pwd_edt_phone.getText().toString().trim();

                if(checkMobile(mobile)){

                    getSecurityCode(mobile);
                }
                break;
            case R.id.find_pwd_btn_submit:
                mobile=find_pwd_edt_phone.getText().toString().trim();
                if(!checkMobile(mobile)){
                    return;
                }
                code=find_pwd_edt_code.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showMyShortToast(R.string.input_security_code);
                    return;
                }
                if(TextUtils.isEmpty(find_pwd_edt_pwd.getText().toString().trim())){
                    showToast("请输入新密码");
                    return;
                }
                if(!RegexUtils.checkPassWord(find_pwd_edt_pwd.getText().toString().trim())){
                    showToast("请输入6-18位数字或字母的密码");
                    return;
                }

//                if(TextUtils.isEmpty(find_pwd_edt_pwd_again.getText().toString().trim())){
//                    showToast("请输入确认密码");
//                    return;
//                }
//                if(!RegexUtils.checkPassWord(find_pwd_edt_pwd_again.getText().toString().trim())){
//                    showToast("请输入6-18位数字或字母的确认密码");
//                    return;
//                }
//                if(!find_pwd_edt_pwd.getText().toString().trim().equals(find_pwd_edt_pwd_again.getText().toString().trim())){
//                    showToast("两次密码输入不一致，请重新输入！");
//                    find_pwd_edt_pwd_again.setText("");
//                    return;
//                }
                change(find_pwd_edt_pwd.getText().toString(),code);
                break;
        }

    }
    /**
     * 校验密码
     */
    private boolean checkPwd(String pwd,String newPwd){
        //校验手机号码
        String msg = RegexUtils.checkPassWord(pwd, newPwd);
        if(!msg.equals("")){
            ToastUtil.showMyLongToast(msg);
            return false;
        }else
            return true;
    }
    /**
     * 验证手机短信验证码
     */
    private void verificationCode( final String phone,String code) {
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("mobile",phone).add("code",code)
                .build();
        final Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.VERIFICATION_CODE)
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
                    CommonGson result=JsonUtils.deserialize(new String(response), CommonGson.class);

                    if(result.getCode().equals("200")){
//                        VerifyMobile(mobile,result.getResponse());
                        change(find_pwd_edt_pwd.getText().toString(),result.getResponse());
                    }else{
                        ToastUtil.showMyLongToast(result.getMsg());
                        if(result.getMsg().contains("短信验证失败")){
                            find_pwd_edt_code.setText("");
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
    }
    /**
     * 重置密码
     */
    private void change(String pwd ,String code) {
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("mobile",mobile)
                .add("spassword",pwd)
                .add("code",code)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.RESET_PWD)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                try {
                    BaseGson result= JsonUtils.deserialize(new String(response), BaseGson.class);
                    ToastUtil.showMyLongToast(result.getMsg());
                    if(result.getCode().equals("200")){
                        finish();
                    }
                } catch (Exception e) {

                }
            }
        });
    }
    /**
     * @Description: TODO(校验手机号码)
     * param     设定文件
     * @return void    返回类型
     */
    private boolean checkMobile(String mobile){
        //校验手机号码
        String msg = RegexUtils.checkMobile(mobile);
        if(!msg.equals("")){
            ToastUtil.showMyLongToast(msg);
            return false;
        }else
            return true;
    }
    //手机号码变化监听
    private TextWatcher edtChangeListener=new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            //当手机校验成功时颜色状态变化
            if(totalCount<=0) {
                String mobile = find_pwd_edt_phone.getText().toString().trim();
                if (mobile.length() == 11) {
                    if (RegexUtils.MobileIsRight(mobile)) {
                        find_pwd_btn_security_code.setClickable(true);
                        find_pwd_btn_security_code.setBackgroundResource(R.drawable.shape_out_blue_inner_blue);
                        find_pwd_btn_security_code.setTextColor(getResources().getColor(R.color.text_color_white));
                    }
                } else {
//					btnCode.setText(null);
                    find_pwd_btn_security_code.setClickable(false);
                    find_pwd_btn_security_code.setBackgroundResource(R.drawable.shape_out_grey_inner_grey);
                    find_pwd_btn_security_code.setTextColor(getResources().getColor(R.color.text_color_gray));
                    if (TextUtils.isEmpty(mobile)) {
                        find_pwd_btn_security_code.setClickable(true);
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
     * 获取手机短信验证码
     */
    private void getSecurityCode(final String phone) {
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("mobile",phone)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.GET_PWD_MOBILECODE)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                try {
                    BaseGson result= JsonUtils.deserialize(response, BaseGson.class);
                    ToastUtil.showMyLongToast(result.getMsg());
                    totalCount = 60;

                    if(result.getCode().equals("200")){
                        find_pwd_btn_security_code.setClickable(false);
                        find_pwd_btn_security_code.setTextColor(getApplicationContext().getResources().getColor(R.color.text_color_gray));
                        find_pwd_btn_security_code.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.shape_out_grey_inner_grey));
                        TimerTaskUtils.getInstance().startTimer(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(totalCount>0){
                                            find_pwd_btn_security_code.setText("短信已发送"+"("+totalCount+"s)");
                                        }else{
                                            TimerTaskUtils.getInstance().cancelTimer();
                                            if (RegexUtils.MobileIsRight(find_pwd_edt_phone.getText().toString().trim())) {
                                                find_pwd_btn_security_code.setClickable(true);
                                                find_pwd_btn_security_code.setText(getApplicationContext().getResources().getString(R.string.get_security_code));
                                                find_pwd_btn_security_code.setBackgroundResource(R.drawable.shape_out_blue_inner_blue);
                                                find_pwd_btn_security_code.setTextColor(getResources().getColor(R.color.text_color_white));

                                            }else{
                                                find_pwd_btn_security_code.setClickable(false);
                                                find_pwd_btn_security_code.setTextColor(getApplicationContext().getResources().getColor(R.color.text_color_gray));
                                                find_pwd_btn_security_code.setText(getApplicationContext().getResources().getString(R.string.get_security_code));
                                                find_pwd_btn_security_code.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.shape_out_grey_inner_grey));
                                            }
                                        }
                                        totalCount--;
                                    }
                                });
                            }
                        },0,1000);
                    }

                } catch (Exception e) {

                }
            }
        });
    }

}
