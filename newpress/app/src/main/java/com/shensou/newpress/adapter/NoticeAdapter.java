package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.fragment.HomeHeaderFragment1;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.bean.NoticeGson;
import com.shensou.newpress.utils.ImageLoadProxy;
import com.shensou.newpress.utils.TimeUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class NoticeAdapter extends BaseLoadingAdapter<NoticeGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;
    private FragmentTransaction ft;
    private HomeHeaderFragment1 homeHeaderFragment1;
    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public NoticeAdapter(List<NoticeGson.ResponseBean> list, Context context,FragmentTransaction ft) {

        super(list, context);
        mContext=context;
        mLists=list;
        this.ft=ft;
        homeHeaderFragment1 = new HomeHeaderFragment1();
        homeHeaderFragment1.setAdvId("notice");
    }

    private List<NoticeGson.ResponseBean> mLists;

    public void setDatas(List<NoticeGson.ResponseBean> lists){
        mLists=lists;
        notifyDataSetChanged();
    }

    public NoticeGson.ResponseBean getItem(int position){
        return mLists.get(position);
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getDisplayType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateDisplayHolder(ViewGroup parent, int viewType) {
        if (viewType==ITEM_VIEW_TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.store_header, parent, false);
            return new LikeViewHolder(v);
        }else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notice, parent, false);
            return new ViewHolder(v);
        }
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_notice, parent, false);
//            return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {
        if (isHeader(position)){
            if (!homeHeaderFragment1.isAdded()){
                ft.add(R.id.home_header2, homeHeaderFragment1).commit();
            }
            return;
        }

        final ViewHolder holder= (ViewHolder) viewHolder;
        ImageLoadProxy.displayImageWithLoadingPicture(mLists.get(position).getThum(),
                holder.iv_item_notice_photo, R.drawable.default_load_img);
        holder.item_notice_title.setText(mLists.get(position).getTitle());
        holder.item_notice_time.setText("发布时间："+ TimeUtil.timeStamp2Date(mLists.get(position).getModified_time(),"yyyy.MM.dd"));
        holder.itemView.setBackgroundResource(R.drawable.selector_list_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });
//        if (isHeader(position)){
//            holder.likeLayout.setVisibility(View.VISIBLE);
//        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.iv_item_notice_photo)
        ImageView iv_item_notice_photo;
        @Bind(R.id.item_notice_title)
        TextView item_notice_title;
        @Bind(R.id.item_notice_time)
        TextView item_notice_time;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class LikeViewHolder extends RecyclerView.ViewHolder{



        LikeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
