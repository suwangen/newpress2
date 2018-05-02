package com.shensou.newpress.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.adapter.HomeTopTabPagerAdapter;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.PxAndDpUtil;
import com.shensou.newpress.widget.CustomViewPager;
import com.shensou.newpress.widget.DepthPageTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class UserHomeFragment extends BaseFragment {
    @Bind(R.id.news_pager1)
    CustomViewPager customViewPager;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    public static final int REQUEST_PERMISSION = 0X01;

    public static final int REQUEST_STORAGE_PERMISSION = 0X01;
    private int p = -1;//分页

    private UserInfoXML mUserInfoXML;
    private List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> hlList;
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean activeTypeBean;
    private String top_id;
    private List<HomeTopTabFragment> fragmentList;
    private HomeTopTabFragment homeTopTabFragment;
    private HomeTopTabPagerAdapter homeTopTabPagerAdapter;

    public static UserHomeFragment newInstance() {
        UserHomeFragment fragment = new UserHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public List<HomeTopTabFragment> getFragmentList(){
        return fragmentList;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homeuser, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @OnClick({})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
        }
    }

    private void initView() {
        mUserInfoXML = UserInfoXML.getInstance(getContext());
        hlList = new ArrayList<>();
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_ZIXUN_LIST);
        activeTypeBean.setTitle("资讯");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_CLASS_LIST);
        activeTypeBean.setTitle("课堂");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_ANSWER_LIST);
        activeTypeBean.setTitle("问答");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_TRANSFER_LIST);
        activeTypeBean.setTitle("转让");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_BIDDING_LIST);
        activeTypeBean.setTitle("竞标");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId(URL.HOME_TOP_RECRUIT_LIST);
        activeTypeBean.setTitle("招聘");
        hlList.add(activeTypeBean);

        initFragment();
    }

    private void initData() {

    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {

        if(fragmentList!=null){
            fragmentList.clear();
        }else{
            fragmentList = new ArrayList<>();
        }

        int count = hlList.size();
        for (int i = 0; i < count; i++) {
            homeTopTabFragment = HomeTopTabFragment.newInstance();
            homeTopTabFragment.setRefreshThingsBean(hlList.get(i));
            fragmentList.add(homeTopTabFragment);
        }
        //        customViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        homeTopTabPagerAdapter = new HomeTopTabPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList,hlList);
        customViewPager.setAdapter(homeTopTabPagerAdapter);
        customViewPager.setPageTransformer(true, new DepthPageTransformer());
        tablayout.setupWithViewPager(customViewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                {

                    try {
                        //拿到tabLayout的mTabStrip属性
                        Field mTabStripField = tablayout.getClass().getDeclaredField("mTabStrip");
                        mTabStripField.setAccessible(true);

                        LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tablayout);

                        int dp10 = PxAndDpUtil.dip2px(getContext(), 10);

                        for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                            View tabView = mTabStrip.getChildAt(i);
                            //                            Log.e("swg",tabView.getLayoutParams().width+"--"+i);
                            //拿到tabView的mTextView属性
                            Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                            mTextViewField.setAccessible(true);

                            TextView mTextView = (TextView) mTextViewField.get(tabView);
                            tabView.setPadding(0, 0, 0, 0);
                            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams)new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            param.leftMargin = 0;
                            param.rightMargin = 0;
                            mTextView.setPadding(0,0,0,0);
                            mTextView.setLayoutParams(param);
                            //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                            int width = 0;
                            width = mTextView.getWidth();

                            if (width == 0) {
                                mTextView.measure(0, 0);
                                width = mTextView.getMeasuredWidth();
                            }

                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                            params.width = width;
                            params.leftMargin = dp10;
                            params.rightMargin = dp10;
                            tabView.setLayoutParams(params);

                            tabView.invalidate();
                        }

                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        customViewPager.setCurrentItem(0);

    }
    /**
     * 需要存储权限
     *
     * @param
     */
    public void needStoragePermissions() {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PermissionUtils.hasPermissions(getActivity(), perms)) {

        } else {
            PermissionUtils.requestPermissions(this, getString(R.string.rationale_write), REQUEST_STORAGE_PERMISSION, perms);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
