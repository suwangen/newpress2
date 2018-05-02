package com.shensou.newpress.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.LoginActivity;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.activity.mine.MessageListActivity;
import com.shensou.newpress.activity.mine.PersonInfoActivity;
import com.shensou.newpress.activity.mine.SettingActivity;
import com.shensou.newpress.activity.mine.WalletActivity;
import com.shensou.newpress.bean.HeadGson;
import com.shensou.newpress.bean.UserInfoGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.ImageLoadProxy;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.SaveInfo;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.widget.AutoToolbar;
import com.shensou.newpress.widget.dialog.DialogUtilCertificationUtil;
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

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class MineFragment extends BaseFragment {
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.back)
    ImageView back;

    @Bind(R.id.mine_iv_head)
    CircleImageView mine_iv_head;
    @Bind(R.id.tv_mine_fragment_username)
    TextView tv_mine_fragment_username;
    @Bind(R.id.toolbar)
    AutoToolbar toolbar;
    @Bind(R.id.lin_fragment_mine_photo)
    LinearLayout linFragmentMinePhoto;

    private View parentView;

    private UserInfoXML mUserInfoXML;

    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp" + Calendar.getInstance().getTimeInMillis() + ".jpg";//temp file
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
    /* 请求码 */
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int RESULT_REQUEST_CODE = 2;
    public static final int REQUEST_CODE_CAMERA = 18;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_mine, null);

        ButterKnife.bind(this, parentView);
        initView();
        return parentView;
    }


    private void initView() {
        toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.bg_white));
        toolbar_title.setText("我的");
        toolbar_title.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
        back.setVisibility(View.INVISIBLE);
        mUserInfoXML = UserInfoXML.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserMessage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.lin_fragment_mine_photo,R.id.right,R.id.lin_personal_info
    ,R.id.lin_mine_fragment_focus,R.id.lin_mine_fragment_distribute
            ,R.id.lin_mine_fragment_setting,R.id.lin_mine_fragment_baojia_order
            ,R.id.lin_mine_fragment_my_order,R.id.lin_mine_fragment_my_wallet
    })
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lin_fragment_mine_photo:
                new DialogUtilCertificationUtil(getContext(), new DialogUtilCertificationUtil.CallBackConfirm2() {
                    @Override
                    public void confirm(String num) {

                    }
                });
//                //修改头像
//                showHeadDialog();
                //                intent = new Intent(getContext(), PhotoViewAvtivity.class);
                //                startActivity(intent);
                break;
            case R.id.lin_personal_info://个人信息
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(), PersonInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_mine_fragment_distribute://我的发布
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(),ActivityDetailActivity.class);
                intent.putExtra("title","我的发布");
                intent.putExtra("url",URL.USER_RELEASE);
                startActivity(intent);
                break;
            case R.id.lin_mine_fragment_focus://我的关注
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(),ActivityDetailActivity.class);
                intent.putExtra("title","我的关注");
                intent.putExtra("url",URL.USER_FOLLOW);
                startActivity(intent);
                break;

            case R.id.lin_mine_fragment_baojia_order://报价订单
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(),ActivityDetailActivity.class);
                intent.putExtra("title","报价订单");
                intent.putExtra("url",URL.USER_OFFERORDER);
                startActivity(intent);
                break;
            case R.id.lin_mine_fragment_my_order://我的订单
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(),ActivityDetailActivity.class);
                intent.putExtra("title","我的订单");
                intent.putExtra("url",URL.USER_MJORDER);
                startActivity(intent);
                break;
            case R.id.right:
                intent = new Intent(getContext(), MessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_mine_fragment_my_wallet://我的钱包
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(), WalletActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_mine_fragment_setting:
                if(!Tools.checkUserLogin(getContext())){
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取用户资料
     * param token
     */
    public void getUserInfo(String userid) {

        RequestBody requestBody = new FormEncodingBuilder()
                .add("userid", userid)
                .build();
        Request request = new Request.Builder()
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
                dismissProgressDialog();
                Log.e("swg", response);
                try {
                    UserInfoGson baseGson = JsonUtils.deserialize(
                            new String(response), UserInfoGson.class);
                    if (baseGson.getResult().equals("1")) {
                        UserInfoGson result = JsonUtils.deserialize(
                                new String(response), UserInfoGson.class);
                        SaveInfo.safeUserInfo(getActivity(),
                                result);
                        tv_mine_fragment_username.setText(result.getUsername());
                        setUserMessage();
                    }
//                    else if (baseGson.getCode().equals("-1")) {
//                        UserInfoXML.getSharedEditor(getActivity())
//                                .clear().commit();
//                        //token失效
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
//                        ToastUtil.showMyLongToast(baseGson.getMsg());
//                    }
                    else {
//                        ToastUtil.showMyLongToast(baseGson.get());
                    }

                } catch (Exception e) {

                }
            }
        });


    }

    //根据用户是否登录显示UI
    private void setUserMessage() {
        ImageLoadProxy.displayImageWithLoadingPicture(mUserInfoXML.getUser_avatar(),
                mine_iv_head, R.drawable.default_head);
        tv_mine_fragment_username.setText(mUserInfoXML.getusername());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA://拍照完执行的截取的方法
                    cropImageUri(imageUri, 600, 600);
                    break;

                case RESULT_REQUEST_CODE://拍照返回的截取完的图片
                    if (imageUri != null) {
                        Bitmap bitmap = decodeUriAsBitmap(imageUri);
                        mine_iv_head.setImageBitmap(bitmap);
                        try {
                            Log.e("adsfa", imageUri.toString());
                            File file = new File(new URI(imageUri.toString()));
                            //                            FileOutputStream fos = new FileOutputStream(file);
                            //                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            //                            fos.flush();
                            //                            fos.close();
                            if (file != null) {
                                uploadHead(file);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;

                case IMAGE_REQUEST_CODE://相册返回并截取完的图片
                    if (data != null) {

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

    public void showHeadDialog() {
        final SelectHeadDialog dialog = new SelectHeadDialog(getContext());
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

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void cropImageUri(Uri uri, int outputX, int outputY) {
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

    private void uploadHead(File file) {
        showProgressDialog();
        MultipartBuilder mb = new MultipartBuilder();
        mb.type(MultipartBuilder.FORM);
//        mb.addFormDataPart("token", mUserInfoXML.getToken());
        mb.addFormDataPart("image", file.getName(), RequestBody.create(null, file));
        RequestBody requestBody = mb.build();


        DOkHttp.getInstance().uploadPost2ServerProgress(getContext(), URL.CHANGE_HEAD, requestBody, new DOkHttp.MyCallBack() {

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
                    ImageLoadProxy.displayImageWithLoadingPicture(mUserInfoXML.getUser_avatar(),
                            mine_iv_head, R.drawable.default_head);
                }
            }
        }, new DOkHttp.UIchangeListener() {

            @Override
            public void progressUpdate(long bytesWrite, long contentLength, boolean done) {


            }
        });

    }
}
