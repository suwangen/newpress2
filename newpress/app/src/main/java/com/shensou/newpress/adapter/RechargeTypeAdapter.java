package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.listeners.OnItemClickLitener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class RechargeTypeAdapter extends BaseLoadingAdapter<String> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public RechargeTypeAdapter(List<String> list, Context context) {

        super(list, context);
        mContext=context;
//        mLists=new ArrayList<>();
//        mLists.add(new NewsGson.DataEntity());
//        mLists.addAll(list);
        mLists=list;

    }

    private List<String> mLists;

    public void setDatas(List<String> lists){
        mLists=lists;
        notifyDataSetChanged();
    }

    public String getItem(int position){
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
                    .inflate(R.layout.item_recharge_type, parent, false);
            return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder holder= (ViewHolder) viewHolder;

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

        holder.tv_item_recharge_type.setText(mLists.get(position));

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.tv_item_recharge_type)
        TextView tv_item_recharge_type;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
