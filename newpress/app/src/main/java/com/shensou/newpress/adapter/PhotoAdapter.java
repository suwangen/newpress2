package com.shensou.newpress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.utils.ImageLoadProxy;
import com.shensou.newpress.utils.ScreenUtils;

import java.util.List;


@SuppressLint("ResourceAsColor")
public class PhotoAdapter extends BaseAdapter
{
	private int width;
	private Context mContext;
	private List<String> mList;
	private HandlerCallBackRemovePhoto handlerCallBackRemovePhoto;
	public PhotoAdapter(Context context,List<String> list,HandlerCallBackRemovePhoto handlerCallBackRemovePhoto)
	{
		this.mContext = context;
		this.mList = list;
		this.handlerCallBackRemovePhoto = handlerCallBackRemovePhoto;
		width= ScreenUtils.getScreenWidth(mContext);
	}
	
	@Override
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}
	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public String getItem(int position)
	{
		return mList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;
		if(convertView==null){
			 holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_photo, null);
			
			holder.iv_item_photo=(ImageView)convertView.findViewById(R.id.iv_item_photo);
			holder.iv_item_add=(TextView)convertView.findViewById(R.id.iv_item_add);
			holder.iv_item_delete=(TextView)convertView.findViewById(R.id.iv_item_delete);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.iv_item_add.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				handlerCallBackRemovePhoto.addListener(position);
				return false;
			}
		});
		if(position==mList.size()-1){
			holder.iv_item_add.setVisibility(View.VISIBLE);
			holder.iv_item_photo.setVisibility(View.GONE);
			holder.iv_item_delete.setVisibility(View.GONE);
		}else{
			ImageLoadProxy.displayImageWithLoadingPicture(mList.get(position),
					holder.iv_item_photo, R.drawable.default_head);
			holder.iv_item_add.setVisibility(View.GONE);
			holder.iv_item_photo.setVisibility(View.VISIBLE);
			holder.iv_item_delete.setVisibility(View.VISIBLE);
			holder.iv_item_delete.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					mList.remove(position);
					handlerCallBackRemovePhoto.removeListener();
					return false;
				}
			});
		}
		
		return convertView;
	}

	class ViewHolder
	{
		private ImageView iv_item_photo;
		private TextView iv_item_add;
		private TextView iv_item_delete;
	}
	public interface HandlerCallBackRemovePhoto{
		void removeListener();
		void addListener(int position);
	}
}
