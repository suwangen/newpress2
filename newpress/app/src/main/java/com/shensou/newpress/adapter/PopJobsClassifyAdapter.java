package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.bean.MenberTimeLengthGson;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.utils.CompoundDrawablesUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * popwindow分类
 * Created by Administrator on 2016/8/18 0018.
 */
public class PopJobsClassifyAdapter extends BaseLoadingAdapter<MenberTimeLengthGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;


    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public PopJobsClassifyAdapter(List<MenberTimeLengthGson.ResponseBean> list, Context context) {

        super(list, context);
        mContext = context;
        mLists = list;

    }

    private List<MenberTimeLengthGson.ResponseBean> mLists;

    public void setDatas(List<MenberTimeLengthGson.ResponseBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public MenberTimeLengthGson.ResponseBean getItem(int position) {
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
                .inflate(R.layout.item_pop_classify, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {

        final ViewHolder holder = (ViewHolder) viewHolder;
        if(position==mLists.size()-1){
            holder.item_line.setVisibility(View.GONE);
        }else {
            holder.item_line.setVisibility(View.VISIBLE);
        }
        if(mLists.get(position).isChoose()){
            holder.item_text.setCompoundDrawables(null,null, CompoundDrawablesUtil.showDrawable(mContext,R.drawable.dagou_red),null);
        }else {
            holder.item_text.setCompoundDrawables(null,null,null,null);
        }
        holder.item_text.setText(mLists.get(position).getTime_up());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_text)
        TextView item_text;
        @Bind(R.id.item_line)
        View item_line;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
