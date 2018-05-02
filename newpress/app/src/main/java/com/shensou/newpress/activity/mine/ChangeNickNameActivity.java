package com.shensou.newpress.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.bean.ChooseAddressGson;
import com.shensou.newpress.bean.CommonGson;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.PopWindowUtil;
import com.shensou.newpress.utils.TimeUtil;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.widget.pickerview.CharacterPickerWindow;
import com.shensou.newpress.widget.pickerview.OptionsWindowHelper;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shensou.newpress.R.id.nick_edt_new;


/**
 * Created by Administrator on 2016/3/11 0011.
 */
public class ChangeNickNameActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.nick_tv_current)
    TextView tvCurrentName;
    @Bind(R.id.nick_tv_current_)
    TextView nick_tv_current_;
    @Bind(nick_edt_new)
    EditText edtNewName;
    @Bind(R.id.nick_tv_used)
    TextView tvUsed;
    @Bind(R.id.nick_tv_new)
    TextView nick_tv_new;
//    @Bind(R.id.loading)
//    RotateLoading loading;

    private int type;
    private String text;
    private String nickName;
    private List list;
    private String strProvinceId;
    private String strCityId;
    private String strCountryId;
    private String selectAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nick_name);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type",1);
        text = getIntent().getStringExtra("text");
        initToolBar();
        init();
    }

    private void initToolBar(){
        switch (type){
            case 1:
                tvTitle.setText("修改详细地址");
                nick_tv_current_.setText("当前详细地址");
                nick_tv_new.setText("输入新详细地址");
                edtNewName.setHint("输入新详细地址");
                break;
            case 0:
                tvTitle.setText("修改生日日期");
                nick_tv_current_.setText("当前生日日期");
                nick_tv_new.setText("输入新生日日期");
                edtNewName.setHint("输入新生日日期");
                edtNewName.setClickable(false);
                edtNewName.setFocusable(false);
                break;
        }
        tvCurrentName.setText(text);
    }

    private void init(){
        mUserInfoXML= UserInfoXML.getInstance(this);
    }

    @OnClick({R.id.back, R.id.lin, nick_edt_new,R.id.nick_btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.nick_btn_confirm:
                nickName=edtNewName.getText().toString().trim();
                if(type==0){
                    nickName = TimeUtil.date2TimeStamp(nickName,"yyyy.MM.dd");
                }
                changeNickName(mUserInfoXML.getUid(),nickName);
                break;
            case R.id.lin:
            case nick_edt_new:
                if(type==2){
                    list = new ArrayList<String>();
                    list.add("男");
                    list.add("女");
                    new PopWindowUtil().initPopuptWindowSex(getApplicationContext(), view, new PopWindowUtil.HandlerItemCallBack2() {
                        @Override
                        public void handle(String s, int position) {
                            edtNewName.setText(s);
                        }
                    });
                }else if(type==0){
                    new PopWindowUtil().initPopuptWindowBirthDate(getApplicationContext(), view, new PopWindowUtil.HandlerItemCallBack2() {
                        @Override
                        public void handle(String s, int position) {
                            edtNewName.setText(s);
                        }
                    });
            }else if(type == 4){

                    getArea(view);
                }

                break;
        }
    }

    /**
     * 获取地区
     */
    private void getArea(final View view){
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
//                .add("token", mUserInfoXML.getToken())
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.GET_AREA)
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
                    final ChooseAddressGson baseGson = JsonUtils.deserialize(response, ChooseAddressGson.class);
                    if (baseGson.getCode().equals("200")) {
                        final CharacterPickerWindow window = OptionsWindowHelper.builder(ChangeNickNameActivity.this, baseGson.getResponse(), new OptionsWindowHelper.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int option1, int option2, int option3) {
                                ChooseAddressGson.ResponseEntity province = baseGson.getResponse().get(option1);
                                ChooseAddressGson.ResponseEntity.CityListEntity city = baseGson.getResponse().get(option1).getCity_list().get(option2);
                                ChooseAddressGson.ResponseEntity.CityListEntity.CountyListEntity area = baseGson.getResponse().get(option1).getCity_list().get(option2).getCounty_list().get(option3);
                                selectAddress = province.getTitle() + city.getTitle() + area.getTitle();
                                strProvinceId = province.getArea_id();
                                strCityId = city.getArea_id();
                                strCountryId = area.getArea_id();
                                edtNewName.setText(selectAddress);
                            }
                        });
                        window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    } else if(baseGson.getCode().equals("-1")){
                        ToastUtil.showMyLongToast(baseGson.getMsg());
                    }

                } catch (Exception e) {

                }
            }
        });
    }
    /**
     * 修改昵称
     * param articleId
     */
    private void changeNickName(String uid, final String value){
        showProgressDialog();
        String url = "";
        String pramname = "";
        url = URL.CHANGE_NUMBER;
        switch (type){
            case 0:
                pramname = "birthday";

                break;
            case 1:
                pramname = "address";
                break;
        }
        RequestBody requestBody=new FormEncodingBuilder()
                .add("uid", uid)
                .add("token", mUserInfoXML.getuserid())
                .add(pramname,value)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(url)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                Log.e("swg",response);
                try {
                    dismissProgressDialog();
                    CommonGson baseGson= JsonUtils.deserialize(response, CommonGson.class);
                    ToastUtil.showMyLongToast(baseGson.getMsg());

                    if (baseGson.getCode().equals("200")){
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        });
    }
}
