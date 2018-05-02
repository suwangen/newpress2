package com.shensou.newpress.activity.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.activity.LoginActivity;
import com.shensou.newpress.bean.UpdateGson;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.fragment.CircleTabFragment;
import com.shensou.newpress.fragment.MineFragment;
import com.shensou.newpress.fragment.MsgTabFragment;
import com.shensou.newpress.fragment.UserHomeFragment;
import com.shensou.newpress.gobal.LiverActivityManager;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.permission.AfterPermissionGranted;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.PopWindowUtil;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.utils.VersionUtils;
import com.squareup.okhttp.Request;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class MainActivity extends BaseActivity {

    public static final int REQUEST_STORAGE_PERMISSION = 0X01;
    @Bind(R.id.home_iv_home)
    ImageView mIvHome;
    @Bind(R.id.home_tv_home)
    TextView mTvHome;
    @Bind(R.id.home_iv_mine)
    ImageView mIvMine;
    @Bind(R.id.home_tv_mine)
    TextView mTvMine;
    @Bind(R.id.home_ll_distribute)
    LinearLayout home_ll_distribute;

    @Bind(R.id.home_iv_find)
    ImageView home_iv_find;
    @Bind(R.id.home_tv_find)
    TextView home_tv_find;
    @Bind(R.id.main_tab2)
    LinearLayout rel_tree;
    @Bind(R.id.home_iv_circle)
    ImageView homeIvCircle;
    @Bind(R.id.home_tv_circle)
    TextView homeTvCircle;

    private UserHomeFragment userhomeFragment;
    private MsgTabFragment msgTabFragment;
    private CircleTabFragment circleFragment;
    private MineFragment mineFragment;
    private long exitTime;

    private Fragment mFragment;//当前显示的Fragment
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean activeTypeBean;

    private int tab = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        initData();
//        rel_tree.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));
        //        rel_tree.setPadding(0,0,0,Tools.getNavigationBarHeight(context));
        //        checkUpdate();
        needStoragePermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mUserInfoXML.getis_change_statue().equals("2")){
            if(userhomeFragment.getFragmentList()!=null&&userhomeFragment.getFragmentList().size()>0){
                userhomeFragment.getFragmentList().get(0).onRefresh();//webview.reload()才能更新local Storage数据
            }
        }
    }

    private void initData() {
        mIvHome.setSelected(true);
        mTvHome.setSelected(true);

        //        fragments = new ArrayList<>();

        mineFragment = new MineFragment();

        msgTabFragment = MsgTabFragment.newInstance();
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_BOTTOM_NEWS_TAB);
        activeTypeBean.setTitle("消息");
        msgTabFragment.setRefreshThingsBean(activeTypeBean);

        userhomeFragment = new UserHomeFragment().newInstance();

        circleFragment = CircleTabFragment.newInstance();
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_BOTTOM_CIRCLE_TAB);
        activeTypeBean.setTitle("圈子");
        circleFragment.setRefreshThingsBean(activeTypeBean);

        mFragment = userhomeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.tab_content, userhomeFragment).commit();
    }

    /**
     * 需要存储权限
     *
     * @param
     */
    public void needStoragePermissions() {
        String[] perms = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PermissionUtils.hasPermissions(this, perms)) {
        } else {
            PermissionUtils.requestPermissions(this, getString(R.string.rationale_write), REQUEST_STORAGE_PERMISSION, perms);
        }
    }

    /**
     * 获取权限
     */
    @AfterPermissionGranted(UserHomeFragment.REQUEST_PERMISSION)
    private void onPermissionGranted() {
        //               driverFragment.needLocationPermissions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showMyLongToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                LiverActivityManager.getAppManager().AppExit();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 检查版本更新
     */
    public void checkUpdate() {
        Request request = new Request.Builder().get().tag(this)
                .url(URL.CHECK_UPDATE)
                .build();
        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                ToastUtil.showMyShortToast(R.string.network_anomaly);
                dismissProgressDialog();

            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                try {
                    UpdateGson result = JsonUtils.deserialize(response, UpdateGson.class);
                    if (result.getCode().equals("200")) {
                        if (!VersionUtils.getVersion(MainActivity.this).equals(result.getData().getName())) {
                            //版本号相同则不弹框提示
                            VersionUtils.showUpdateDialog(MainActivity.this, result.getData().getName(), result.getData().getContent(), result.getData().getPath());
                        }

                    } else
                        ToastUtil.showMyLongToast(result.getMsg());
                } catch (Exception e) {

                }
            }
        });
    }

    @OnClick({R.id.home_ll_home, R.id.home_ll_mine, R.id.home_ll_find
            , R.id.home_ll_distribute, R.id.home_ll_circle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_ll_home:

                if (!mTvHome.isSelected()) {
                    tab = 0;
                    mTvHome.setSelected(true);
                    mIvHome.setSelected(true);
                    home_iv_find.setSelected(false);
                    home_tv_find.setSelected(false);
                    mIvMine.setSelected(false);
                    mTvMine.setSelected(false);

                    homeIvCircle.setSelected(false);
                    homeTvCircle.setSelected(false);

                    switchFragment(userhomeFragment);
                }
                break;
            case R.id.home_ll_find:
                if (!home_iv_find.isSelected()) {
                    tab = 1;
                    mIvMine.setSelected(false);
                    mTvMine.setSelected(false);
                    home_iv_find.setSelected(true);
                    home_tv_find.setSelected(true);
                    mTvHome.setSelected(false);
                    mIvHome.setSelected(false);
                    homeIvCircle.setSelected(false);
                    homeTvCircle.setSelected(false);
                    switchFragment(msgTabFragment);
                }

                break;
            case R.id.home_ll_distribute://发布
                new PopWindowUtil().initPopuptWindowDistribute(context, home_ll_distribute, new PopWindowUtil.HandlerItemCallBack2() {
                    @Override
                    public void handle(String s, int position) {

                    }
                });
                break;
            case R.id.home_ll_circle://圈子
                if (!homeIvCircle.isSelected()) {
                    tab = 3;
                    mIvMine.setSelected(false);
                    mTvMine.setSelected(false);
                    home_iv_find.setSelected(false);
                    home_tv_find.setSelected(false);
                    mTvHome.setSelected(false);
                    mIvHome.setSelected(false);
                    homeIvCircle.setSelected(true);
                    homeTvCircle.setSelected(true);
                    switchFragment(circleFragment);

                }
                break;
            case R.id.home_ll_mine:
                if (!mIvMine.isSelected()) {
                    tab = 4;
                    mIvMine.setSelected(true);
                    mTvMine.setSelected(true);
                    home_iv_find.setSelected(false);
                    home_tv_find.setSelected(false);
                    mTvHome.setSelected(false);
                    mIvHome.setSelected(false);
                    homeIvCircle.setSelected(false);
                    homeTvCircle.setSelected(false);
                    switchFragment(mineFragment);
                    if (Tools.checkUserLogin(getApplication())) {
                        mineFragment.getUserInfo(mUserInfoXML.getuserid());
                    }else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                break;

        }
    }

    private void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if (mFragment != fragment) {
            //判断切换的Fragment是否已经添加过
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment)
                        .add(R.id.tab_content, fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                if(tab==3&&mUserInfoXML.getis_change_statue().equals("2")){
                    circleFragment.onRefresh();
                }
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

}
