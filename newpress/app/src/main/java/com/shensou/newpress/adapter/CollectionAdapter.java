package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.bean.CollectionArticleGson;
import com.shensou.newpress.listeners.OnItemClickLitener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class CollectionAdapter extends BaseLoadingAdapter<CollectionArticleGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public CollectionAdapter(List<CollectionArticleGson.ResponseBean> list, Context context) {

        super(list, context);
        mContext=context;
        mLists=list;

    }

    private List<CollectionArticleGson.ResponseBean> mLists;

    public void setDatas(List<CollectionArticleGson.ResponseBean> lists){
        mLists=lists;
        notifyDataSetChanged();
    }

    public CollectionArticleGson.ResponseBean getItem(int position){
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

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_collection, parent, false);
            return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder holder= (ViewHolder) viewHolder;

//        ImageLoadProxy.displayImageWithLoadingPicture(mLists.get(position).getThum(),
//                holder.iv_item_deyu_photo, R.drawable.default_load_img);
        holder.item_notice_title.setText(mLists.get(position).getTitle());
        holder.item_notice_time.setText(mLists.get(position).getCreate_time());
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
        @Bind(R.id.item_notice_title)
        TextView item_notice_title;
        @Bind(R.id.item_notice_time)
        TextView item_notice_time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
