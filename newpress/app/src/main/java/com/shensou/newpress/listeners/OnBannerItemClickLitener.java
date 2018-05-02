package com.shensou.newpress.listeners;


import com.shensou.newpress.bean.PagerItem;

/**
 * Created by fangzhenjian on 2016/2/23 0023.
 * 广告轮播图
 */
public interface OnBannerItemClickLitener {

    void bannerClick(int position, PagerItem pagerItem);
}
