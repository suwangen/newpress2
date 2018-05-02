package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.adapter.HelpPagerAdapter;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.fragment.HomeHeaderFragment1;
import com.shensou.newpress.fragment.HelpFragment;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.widget.CustomViewPager;
import com.shensou.newpress.widget.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的订单
 * Created by fangzhenjian on 2016/2/4 0004.
 */
public class HelpActivity extends BaseActivity {

    @Bind(R.id.news_pager1)
    CustomViewPager customViewPager;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private View parentView;

    private UserInfoXML mUserInfoXML;
    private int p =0;
    private String uid;

    private List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> hlList;
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean activeTypeBean;
    private String top_id;
    private List<HelpFragment> fragmentList;
    private HelpFragment helpFragment;
    private HelpPagerAdapter helpPagerAdapter;

    private HomeHeaderFragment1 homeHeaderFragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initView();
    }


    private void initView() {
        toolbar_title.setText("帮助");
        mUserInfoXML = UserInfoXML.getInstance(context);
        uid = mUserInfoXML.getUid();
        homeHeaderFragment1 = new HomeHeaderFragment1();
        homeHeaderFragment1.setAdvId("help");
        getSupportFragmentManager().beginTransaction().add(R.id.home_header, homeHeaderFragment1).commit();
        hlList = new ArrayList<>();
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("充电介绍");
        hlList.add(activeTypeBean);
        activeTypeBean = new YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean();
        activeTypeBean.setId("10");//订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成;
        activeTypeBean.setTitle("电池维护");
        hlList.add(activeTypeBean);

        initFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back
    })
    public void onClick(View view) {
        Intent intent =null;
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;

        }
    }


    /**
     * 删除或者取消订单的时候更新其他fragment对应order_id的订单数据
     * @param order_id
     */
    public void refreshDataByOrderId(String order_id){
        for(int i=0;i<fragmentList.size();i++){
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    fragmentList.get(i).getList().remove(j);
                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 付款成功后更新其他fragment对应order_id的订单数据
     * @param order_id
     */

    public void refreshOrderStatueByOrderId(String order_id){
        for(int i=0;i<2;i++){//只要判断全部、未付款 两个列表
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    //订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成
                    fragmentList.get(i).getList().get(j).setOrder_state("20");
                    if(i==1){//待付款
                        if(fragmentList.get(2).getList()!=null){//付款成功直接加入待发货
                            fragmentList.get(2).getList().add(0,fragmentList.get(i).getList().get(j));
                            fragmentList.get(2).getAdapter().notifyDataSetChanged();
                        }
                        fragmentList.get(i).getList().remove(j);
                    }
                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 确认收货
     * @param order_id
     */
    public void refreshOrderStatueConfirmGetGoods(String order_id){
        for(int i=0;i<fragmentList.size();i++){//只要判断待发货、已完成 两个列表
            for(int j=0;fragmentList.get(i).getList()!=null&&j<fragmentList.get(i).getList().size();j++){
                if(fragmentList.get(i).getList().get(j).getOrder_id().equals(order_id)){
                    //订单状态：0(已取消)10:待付款;20:待发货;30:待收货;40:已完成
                    fragmentList.get(i).getList().get(j).setOrder_state("40");
                    if(i==3){//待发货
                        if(fragmentList.get(4).getList()!=null){//确认收货后直接加入已完成
                            fragmentList.get(4).getList().add(0,fragmentList.get(i).getList().get(j));
                            fragmentList.get(4).getAdapter().notifyDataSetChanged();
                        }
                        fragmentList.get(i).getList().remove(j);
                    }

                    fragmentList.get(i).getAdapter().notifyDataSetChanged();
                }
            }
        }
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
            helpFragment = HelpFragment.newInstance();
            helpFragment.setRefreshThingsBean(hlList.get(i));
            fragmentList.add(helpFragment);
        }
        //        customViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        helpPagerAdapter = new HelpPagerAdapter(getSupportFragmentManager(),fragmentList,hlList);
        customViewPager.setAdapter(helpPagerAdapter);
        customViewPager.setPageTransformer(true, new DepthPageTransformer());
        tablayout.setupWithViewPager(customViewPager);
        tablayout.setTabMode(TabLayout.MODE_FIXED);

        customViewPager.setCurrentItem(0);

    }
}
