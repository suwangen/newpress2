package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.bean.VisitPeopleGson;
import com.shensou.newpress.listeners.OnItemClickLitener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class DianzhuangAdapter extends BaseLoadingAdapter<VisitPeopleGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public DianzhuangAdapter(List<VisitPeopleGson.ResponseBean> list, Context context) {

        super(list, context);
        mContext = context;
        mLists = list;

    }

    private List<VisitPeopleGson.ResponseBean> mLists;

    public void setDatas(List<VisitPeopleGson.ResponseBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public VisitPeopleGson.ResponseBean getItem(int position) {
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
                .inflate(R.layout.item_dianzhuang_list, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder holder = (ViewHolder) viewHolder;

        //        ImageLoadProxy.displayImageWithLoadingPicture(mLists.get(position).getThum(),
        //                holder.iv_item_deyu_photo, R.drawable.default_load_img);
        holder.itemDianzhuangTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_dianzhuang_title)
        TextView itemDianzhuangTitle;
        @Bind(R.id.item_dianzhuang_km)
        TextView itemDianzhuangKm;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
