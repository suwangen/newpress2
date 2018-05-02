package com.shensou.newpress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shensou.newpress.R;
import com.shensou.newpress.listeners.OnBannerItemClickLitener;
import com.shensou.newpress.bean.PagerItem;
import com.shensou.newpress.utils.ImageLoadProxy;
import com.shensou.newpress.widget.ScaleImageView;
import com.shensou.newpress.widget.banner.InfinitePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23 0023.
 */
public class HomeBannerPageAdapter extends InfinitePagerAdapter {

    public void setOnBannerItemClickLitener(OnBannerItemClickLitener onBannerItemClickLitener) {
        this.onBannerItemClickLitener = onBannerItemClickLitener;
    }

    private OnBannerItemClickLitener onBannerItemClickLitener;

    private  LayoutInflater mInflater;
    private  Context mContext;
    private List<PagerItem> mList;



    public HomeBannerPageAdapter(Context context){
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDataList(List<PagerItem> list) {
//        if (list == null || list.size() == 0)
//            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.adapter_home_banner_item, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        final PagerItem item = mList.get(position);
        holder.position=position;
        ImageLoadProxy.displayImageWithLoadingPicture(item.getImageUrl(), holder.image, R.drawable.default_load_img);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickLitener!=null)
                    onBannerItemClickLitener.bannerClick(position,item);
            }
        });
        return view;
    }


    private static class ViewHolder{
        int position;
        ScaleImageView image;


        public ViewHolder(View view){
            image= (ScaleImageView) view.findViewById(R.id.banner_img);
        }

    }
}
