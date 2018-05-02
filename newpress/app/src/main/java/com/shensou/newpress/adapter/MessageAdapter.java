package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.bean.MessageGson;
import com.shensou.newpress.utils.TimeUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class MessageAdapter extends BaseLoadingAdapter<MessageGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public MessageAdapter(List<MessageGson.ResponseBean> list, Context context) {

        super(list, context);
        mContext=context;
        mLists=list;

    }

    private List<MessageGson.ResponseBean> mLists;

    public void setDatas(List<MessageGson.ResponseBean> lists){
        mLists=lists;
        notifyDataSetChanged();
    }

    public MessageGson.ResponseBean getItem(int position){
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
                    .inflate(R.layout.item_message, parent, false);
            return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder, int position) {


        final ViewHolder holder= (ViewHolder) viewHolder;
        holder.tv_item_message_content.setText(mLists.get(position).getDescription());
        holder.tv_item_message_time.setText(TimeUtil.timeStamp2Date(mLists.get(position).getCreate_time(),"yyyy.MM.dd"));
        holder.itemView.setBackgroundResource(R.drawable.selector_list_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
       @Bind(R.id.tv_item_message_time)
       TextView tv_item_message_time;
       @Bind(R.id.tv_item_message_content)
       TextView tv_item_message_content;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
