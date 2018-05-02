package com.shensou.newpress.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.shensou.newpress.R;
import com.shensou.newpress.gobal.LiverActivityManager;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.widget.dialog.LoadingDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;




/**
 * Created by Administrator on 2016/2/3 0003.
 */
public class BaseActivity extends AppCompatActivity implements PermissionUtils.PermissionCallbacks {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    private AlertDialog progressDialog;
    protected UserInfoXML mUserInfoXML;
    protected  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // 竖屏锁定
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // 添加创建的activity
        LiverActivityManager.getAppManager().addActivity(this);
        mUserInfoXML=UserInfoXML.getInstance(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs)
    {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT))
        {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT))
        {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT))
        {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }
    // 重新onDestroy方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ImageLoaderUtils.getInstance().stop(); // 停止加载图片
        LiverActivityManager.getAppManager().finishActivity(this);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//        MobclickAgent.onResume(this);//统计时长
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    /**
     * 显示dialog
     *
     * param msg 显示内容
     */
    public void showProgressDialog() {
        try {

            if (progressDialog == null) {

                progressDialog = new LoadingDialog(this);
                progressDialog.setCanceledOnTouchOutside(false);
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 隐藏dialog
     */
    public void dismissProgressDialog() {
        try {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message){

//        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        ToastUtil.showMyShortToast(message);
    }

    public void showToast(int strId){
        String message=getResources().getString(strId);
        ToastUtil.showMyLongToast(message);
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    public void toLoginActivity(Context context){
        UserInfoXML.getSharedEditor(context)
                .clear().commit();
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG = BaseActivity.class.getCanonicalName();

    private static final int PERMANENTLY_DENIED_REQUEST_CODE = 428;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        Log.d(TAG, perms.size() + " permissions granted.");
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        Log.e(TAG, perms.size() + " permissions denied.");
        if (PermissionUtils.somePermissionsPermanentlyDenied(this, perms)) {
            PermissionUtils.onPermissionsPermanentlyDenied(this,
                    getString(R.string.rationale),
                    getString(R.string.rationale_title),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    PERMANENTLY_DENIED_REQUEST_CODE);
        }
    }

}
