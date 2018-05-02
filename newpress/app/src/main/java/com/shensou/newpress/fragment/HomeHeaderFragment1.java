package com.shensou.newpress.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shensou.newpress.R;
import com.shensou.newpress.adapter.HomeBannerPageAdapter;
import com.shensou.newpress.bean.BannerGson;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.PagerItem;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.listeners.OnBannerItemClickLitener;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ScreenUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.widget.banner.CirclePageIndicator;
import com.shensou.newpress.widget.banner.InfiniteViewPager;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class HomeHeaderFragment1 extends BaseFragment implements OnBannerItemClickLitener {

    @Bind(R.id.banner_pager)
    InfiniteViewPager mViewPager;
    @Bind(R.id.banner_indicator)
    CirclePageIndicator mIndicator;
    @Bind(R.id.bannerlayout)
    LinearLayout mBannerLayout;


    private View parentView;


    private HomeBannerPageAdapter mPagerAdapter;
    private List<PagerItem> mBannerLists;
    private UserInfoXML mUserInfoXML;
    private String id;
    private String url= URL.MAIN_BANNNER;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_home_header1, null);

        ButterKnife.bind(this, parentView);

        setBannerHeight();
        init();
        getBanner();
        return parentView;
    }

    public void setAdvId(String id){
        this.id = id;
    }
    private void init() {


        mUserInfoXML=UserInfoXML.getInstance(getActivity());
//        if(mUserInfoXML.getRole().equals("1")){
//            news_rel_start.setVisibility(View.GONE);
//        }else{
//            news_rel_start.setVisibility(View.VISIBLE);
//        }
        mBannerLists=new ArrayList<>();
        mPagerAdapter=new HomeBannerPageAdapter(getActivity());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setAutoScrollTime(3000);
        mIndicator.setViewPager(mViewPager);
        mPagerAdapter.setOnBannerItemClickLitener(this);

    }


    private void setBannerHeight(){
        int screenWidth = ScreenUtils.getDisplayWidth(getActivity());
        int width = (int) screenWidth ;//屏幕宽度
        int height = (int) ((width )*300/567);//高度设为宽度的
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        mBannerLayout.setLayoutParams(layoutParams);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @Override
    public void bannerClick(int position, PagerItem pagerItem) {
//        ToastUtil.showMyLongToast(position+"");
//        Intent intent=new Intent(getActivity(), WebDetailActivity.class);
//        intent.putExtra("url",pagerItem.getWebUrl());
//        intent.putExtra("title",pagerItem.getName());
//        startActivity(intent);
    }



    /**
     * 获取轮播图广告
     */
    private void getBanner(){
        RequestBody requestBody = new FormEncodingBuilder()
                .add("type", id)
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .tag(this)
                .url(url)
                .build();
//        Request request=new Request.Builder().get().tag(this).url(url).build();
        DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                dismissProgressDialog();
                ToastUtil.showMyShortToast(R.string.network_anomaly);
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                BaseGson baseGson = JsonUtils.deserialize(new String(response), BaseGson.class);

                if (baseGson.getCode().equals("200")) {

                    BannerGson result = JsonUtils.deserialize(new String(response), BannerGson.class);

                    if (result.getResponse()!=null&&result.getResponse().size() == 0) {
                        return;
                    }
                    List<BannerGson.ResponseBean> lists = result.getResponse();
                    int count = lists.size();
                    for (int i = 0; i < count; i++) {
                        BannerGson.ResponseBean bannerDetail = lists.get(i);
                        PagerItem pagerItem = new PagerItem();
                        pagerItem.setImageUrl(bannerDetail.getAdv_img());
                        pagerItem.setName(bannerDetail.getAdv_title());
                        pagerItem.setId(bannerDetail.getAdv_title());
                        pagerItem.setWebUrl(bannerDetail.getAdv_url());
                        mBannerLists.add(pagerItem);
                    }
                    mPagerAdapter.setDataList(mBannerLists);
                    mViewPager.startAutoScroll();
                    if (count == 1) {
                        //只有一张广告禁止滑动
                        mViewPager.setPagingEnabled(false);
                    }
                }
//                else if (baseGson.getInfo().equals("无广告图")) {
//                    NoBannerGson result = JsonUtils.deserialize(new String(response), NoBannerGson.class);
//                    NoBannerGson.NoBanner bannerDetail = result.getData().get(0);
//                    PagerItem pagerItem = new PagerItem();
//                    pagerItem.setImageUrl(bannerDetail.getDefault_content());
//                    mBannerLists.add(pagerItem);
//                    mPagerAdapter.setDataList(mBannerLists);
//                    mViewPager.startAutoScroll();
//                    mViewPager.setPagingEnabled(false);
//                }
                else {
                    ToastUtil.showMyShortToast(baseGson.getMsg());
                }

            }
        });
    }



    private void  initBusinessItem(List<BannerGson.ResponseBean> mLists){
//        final BrandAdapter mAdapter=new BrandAdapter(mLists,getActivity());
//        mBrandRecycleView.setLayoutManager(new FullyLinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//        mBrandRecycleView.setAdapter(mAdapter);
//        mAdapter.setmOnItemClickLitener(new OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent=new Intent(getActivity(), WebDetailActivity.class);
//                intent.putExtra("url",mAdapter.getItem(position).getAdv_url());
//                intent.putExtra("title",mAdapter.getItem(position).getAdv_title());
//                startActivity(intent);
//            }
//        });
    }

}
