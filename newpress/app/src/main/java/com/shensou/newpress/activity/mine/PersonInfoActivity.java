package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.activity.LoginActivity;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.HeadGson;
import com.shensou.newpress.bean.UserInfoGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.widget.dialog.SelectHeadDialog;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.shensou.newpress.R.id.info_rl_bank;
import static com.shensou.newpress.R.id.info_rl_birth;
import static com.shensou.newpress.R.id.info_rl_nick_name;
import static com.shensou.newpress.R.id.info_rl_place;
import static com.shensou.newpress.R.id.info_rl_sex;
import static com.shensou.newpress.R.id.info_rl_sign;
import static com.shensou.newpress.R.id.info_tv_nick_name;

/**
 * Created by fangzhenjian on 2016/3/11 0011.
 * 个人资料
 */
public class PersonInfoActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView tvTitle;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.info_iv_head)
    CircleImageView ivHead;
    @Bind(info_tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.info_tv_email)
    TextView tvEmail;
    @Bind(R.id.info_tv_mobile)
    TextView tvMobile;

    @Bind(R.id.info_tv_sex)
    TextView info_tv_sex;
    @Bind(R.id.info_tv_sign)
    TextView info_tv_sign;
    @Bind(R.id.info_tv_birth)
    TextView info_tv_birth;
    @Bind(R.id.info_tv_place)
    TextView info_tv_place;
    @Bind(R.id.info_tv_bank)
    TextView info_tv_bank;
    @Bind(R.id.info_ll)
    LinearLayout layout;
    @Bind(R.id.layout_phone_and_pwd)
    LinearLayout phoneLayout;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
//    @Bind(R.id.loading)
//    RotateLoading loading;


    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp"+ Calendar.getInstance().getTimeInMillis()+".jpg";//temp file
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap


    /* 请求码 */
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int RESULT_REQUEST_CODE = 2;
    public static final int REQUEST_CODE_CAMERA = 18;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initToolBar();
        init();
        getUserInfo(mUserInfoXML.getuserid());
    }


    private void initToolBar(){
        tvTitle.setText(R.string.personal_info);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
    }
    private void init(){
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.back, R.id.info_rl_head, info_rl_nick_name, R.id.info_rl_mobile, R.id.info_tv_change_pwd,  R.id.info_btn_login_out,  R.id.info_rl_email
    , info_rl_sex, info_rl_birth, info_rl_place, info_rl_bank, info_rl_sign})
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.info_rl_head:
                //修改头像
                showHeadDialog();
                break;
            case info_rl_nick_name:
                //修改昵称
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("text",tvNickName.getText().toString());
                startActivityForResult(intent,8);
                break;
            case R.id.info_rl_email:
                //修改邮箱
//                intent=new Intent(this,ChangeEmailActivity.class);
//                startActivity(intent);
                break;
            case R.id.info_rl_mobile:

                break;
            case R.id.info_tv_change_pwd:
                //修改密码
                intent=new Intent(this,ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.info_btn_login_out:
                //退出登录
                logout(mUserInfoXML.getuserid());
                break;
            case info_rl_sign://签名
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",0);
                intent.putExtra("text",info_tv_sign.getText().toString());
                startActivityForResult(intent,8);
                break;
            case info_rl_sex://性别
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("text",info_tv_sex.getText().toString());
                startActivityForResult(intent,8);
                break;
            case info_rl_birth://生日
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",3);
                intent.putExtra("text",info_tv_birth.getText().toString());
                startActivityForResult(intent,8);
                break;
            case info_rl_place://所在地
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",4);
                intent.putExtra("text",info_tv_place.getText().toString());
                startActivityForResult(intent,8);
                break;
            case info_rl_bank://所在银行
                intent=new Intent(this,ChangeNickNameActivity.class);
                intent.putExtra("type",5);
                intent.putExtra("text",info_tv_bank.getText().toString());
                startActivityForResult(intent,8);
                break;
        }
    }

    /**
     * 退出登录
     *
     * @param token
     *            手机号
     * @param
     */
    private void logout(String token) {
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("token", token)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.LOGOUT)
                .build();

        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                Log.e("退出登录",response);
                try {
                    dismissProgressDialog();
                    BaseGson result = JsonUtils.deserialize(new String(response),
                            BaseGson.class);
                    ToastUtil.showMyLongToast(result.getMsg());
                    if (result.getCode().equals("200")) {
                        UserInfoXML.getSharedEditor(PersonInfoActivity.this)
                                .clear().commit();
                        finish();
                    }

                } catch (Exception e) {

                }
            }
        });

    }





    private void uploadHead(File file) {
        showProgressDialog();
        MultipartBuilder mb = new MultipartBuilder();
        mb.type(MultipartBuilder.FORM);
        mb.addFormDataPart("token", mUserInfoXML.getuserid());
        mb.addFormDataPart("download", file.getName(), RequestBody.create(null, file));
        RequestBody requestBody = mb.build();


        DOkHttp.getInstance().uploadPost2ServerProgress(this, URL.CHANGE_HEAD, requestBody, new DOkHttp.MyCallBack() {

            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                Log.e("修改头像",response);
                dismissProgressDialog();
                HeadGson result = JsonUtils.deserialize(new String(response),
                        HeadGson.class);
                ToastUtil.showMyLongToast(result.getMsg());
                if (result.getCode().equals("200")) {
                    mUserInfoXML.setUser_avatar(result.getResponse().getPath());

                }
            }
        }, new DOkHttp.UIchangeListener() {

            @Override
            public void progressUpdate(long bytesWrite, long contentLength, boolean done) {


            }
        });

    }

    /**
     * 获取用户资料
     * param token
     */
    private void getUserInfo(String token){
        showProgressDialog();
        RequestBody requestBody=new FormEncodingBuilder()
                .add("token", token)
                .build();
        Request request=new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(URL.GET_USER_INFO)
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
                    BaseGson baseGson = JsonUtils.deserialize(
                            new String(response), BaseGson.class);
                    if (baseGson.getCode().equals("200")) {
                        UserInfoGson result = JsonUtils.deserialize(
                                new String(response), UserInfoGson.class);
//                        SaveInfo.safeUserInfo(PersonInfoActivity.this,
//                                result.getData());
//                        updateUI(result.getData());
                    }
                    else if (baseGson.getCode().equals("-1")) {
                        UserInfoXML.getSharedEditor(PersonInfoActivity.this)
                                .clear().commit();
                        //token失效
                        Intent intent = new Intent(PersonInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ToastUtil.showMyLongToast(baseGson.getMsg());
                        finish();
                    }
                    else {
                        ToastUtil.showMyLongToast(baseGson.getMsg());
                    }

                } catch (Exception e) {

                }
            }
        });


    }

//    private void updateUI(UserInfoGson.UserInfoDetail detail){
//        layout.setVisibility(View.VISIBLE);
//        info_tv_sign.setText(detail.getNumber());
//        tvEmail.setText(detail.getEmail());
//        tvNickName.setText(detail.getNickname());
//        info_tv_sex.setText(detail.getSex());
//        info_tv_birth.setText(TimeUtil.timeStamp2Date(detail.getBirthday(),"yyyy-MM-dd"));
//        info_tv_place.setText(detail.getLocation());
//        info_tv_bank.setText(detail.getBank());
//
//        if(!TextUtils.isEmpty(detail.getMobile())) {
//            tvMobile.setText(Tools.encryptPhoneNum(detail.getMobile()));
//        }
//        ImageLoadProxy.displayImageWithLoadingPicture(detail.getLogo(),
//                ivHead, R.drawable.default_load_img);
//    }

    public void showHeadDialog(){
        final SelectHeadDialog dialog=new SelectHeadDialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle(R.string.select_head);
        dialog.setOnSelectHeadClickListener(new SelectHeadDialog.OnSelectHeadClick() {
            @Override
            public void onSelectHeadClick(int id) {
                switch (id) {
                    case R.id.slect_head_take_photo:
                        if (Tools.hasSdcard()) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        }
                        dialog.dismiss();
                        break;
                    case R.id.slect_head_album:
                        //从相册选择
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setType("image/*");
//                        intent.putExtra("crop", "true");
//                        intent.putExtra("aspectX", 1);
//                        intent.putExtra("aspectY", 1);
//                        intent.putExtra("outputX", 600);
//                        intent.putExtra("outputY", 600);
//                        intent.putExtra("scale", true);
//                        intent.putExtra("return-data", false);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                        intent.putExtra("noFaceDetection", true); // no face detection
                        startActivityForResult(intent, IMAGE_REQUEST_CODE);
                        dialog.dismiss();
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==8&&resultCode==9){
            getUserInfo(mUserInfoXML.getuserid());
            return;
        }
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA://拍照完执行的截取的方法
                    cropImageUri(imageUri, 600, 600);
                    break;

                case RESULT_REQUEST_CODE://拍照返回的截取完的图片
                    if(imageUri != null){
                        Bitmap bitmap = decodeUriAsBitmap(imageUri);
                        ivHead.setImageBitmap(bitmap);
                        try {
                            Log.e("adsfa",imageUri.toString());
                            File file = new File(new URI(imageUri.toString()));
//                            FileOutputStream fos = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                            fos.flush();
//                            fos.close();
                            if(file!=null){
                                uploadHead(file);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;

                case IMAGE_REQUEST_CODE://相册返回并截取完的图片
                    if (data!=null){

                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(data.getData(), "image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 600);
                        intent.putExtra("outputY", 600);
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.putExtra("return-data", false);
                        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                        intent.putExtra("noFaceDetection", true); // no face detection
                        startActivityForResult(intent, RESULT_REQUEST_CODE);
                    }

//
                    break;
            }
        }


    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    private void cropImageUri(Uri uri, int outputX, int outputY){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
}
