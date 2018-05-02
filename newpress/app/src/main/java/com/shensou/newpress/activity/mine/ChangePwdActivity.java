package com.shensou.newpress.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.bean.CommonGson;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.RegexUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/12 0012.
 */
public class ChangePwdActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.pwd_edt_inital)
    EditText edtInital;
    @Bind(R.id.pwd_edt_new)
    EditText edtNew;
    @Bind(R.id.pwd_edt_confirm)
    EditText edtConfirm;
//    @Bind(R.id.loading)
//    RotateLoading loading;

    private String mOldPwdStr;//原密码
    private String mNewPwdStr;//新密码
    private String mAgainNewPwdStr;//再次输入新密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar(){
        tvTitle.setText(R.string.passward_change);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
    }

    private void init(){

    }

    @OnClick({R.id.back, R.id.pwd_btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pwd_btn_confirm:
                mOldPwdStr=edtInital.getText().toString();
                mNewPwdStr=edtNew.getText().toString().trim();
                mAgainNewPwdStr=edtConfirm.getText().toString().trim();
                if(TextUtils.isEmpty(mOldPwdStr)){
                    showToast("请输入旧密码");
                    return;
                }
                if(!RegexUtils.checkPassWord(mOldPwdStr)){
                    showToast("请输入6-18位数字或字母的旧密码");
                    return;
                }

                if(TextUtils.isEmpty(mNewPwdStr)){
                    showToast("请输入新密码");
                    return;
                }
                if(!RegexUtils.checkPassWord(mNewPwdStr)){
                    showToast("请输入6-18位数字或字母的新密码");
                    return;
                }

                if(TextUtils.isEmpty(mAgainNewPwdStr)){
                    showToast("请输入确认密码");
                    return;
                }
                if(!RegexUtils.checkPassWord(mAgainNewPwdStr)){
                    showToast("请输入6-18位数字或字母的确认密码");
                    return;
                }
                if(!mNewPwdStr.equals(mAgainNewPwdStr)){
                    showToast("两次密码输入不一致，请重新输入！");
                    edtConfirm.setText("");
                    return;
                }
                if(mNewPwdStr.equals(mOldPwdStr)){
                    showToast("您的新密码与原密码重复，请更换新密码");
                    edtNew.setText("");
                    edtConfirm.setText("");
                    return;
                }

                changePwd( mOldPwdStr, mAgainNewPwdStr);
                break;
        }
    }

    /**
     * 修改用户密码
     * @param oldpassword
     * @param newpassword
     */
    private void changePwd( String oldpassword,String newpassword){
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("newpassword",newpassword).add("oldpassword",oldpassword)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.CHANGE_PWD)
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
                    CommonGson result = JsonUtils.deserialize(new String(response), CommonGson.class);
                    ToastUtil.showMyLongToast(result.getMsg());
                    if(result.getMsg().contains("填入旧密码错误")){
                        edtInital.setText("");
                    }else if(result.getMsg().contains("修改密码")){

                    }
                    if (result.getCode().equals("200")) {
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        });
    }
}
