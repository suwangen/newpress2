package com.shensou.newpress.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shensou.newpress.R;
import com.shensou.newpress.bean.MyOrderGson;
import com.shensou.newpress.listeners.OnItemClickLitener;
import com.shensou.newpress.widget.dialog.DialogUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 *
 * Created by Administrator on 2016/8/18 0018.
 */
public class HelpAdapter extends BaseLoadingAdapter<MyOrderGson.ResponseBean> {
    private static final int ITEM_VIEW_TYPE_HEADER = 12;
    private static final int ITEM_VIEW_TYPE_ITEM = 13;

    private OnItemClickLitener mOnItemClickLitener;
    private Context mContext;
    private DialogUtil dialogUtil;

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public HelpAdapter(List<MyOrderGson.ResponseBean> list, Context context) {

        super(list, context);
        mContext = context;
        mLists = list;

    }

    private List<MyOrderGson.ResponseBean> mLists;

    public void setDatas(List<MyOrderGson.ResponseBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public MyOrderGson.ResponseBean getItem(int position) {
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
                .inflate(R.layout.item_help, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindData(final RecyclerView.ViewHolder viewHolder,final int position) {


        final ViewHolder holder = (ViewHolder) viewHolder;
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private OrderCallback orderCallback;
    public interface OrderCallback{
        void deleteOrCancelOrder(String order_id);//删除或者取消订单的回调
        void pay(int position);//支付回调
        void applyDrawback(int position);//申请退款回调
        void confirmGetGoods(String order_id);//确认收货
    }
    public void setOrderCallback(OrderCallback orderCallback){
        this.orderCallback =orderCallback;
    }
}
