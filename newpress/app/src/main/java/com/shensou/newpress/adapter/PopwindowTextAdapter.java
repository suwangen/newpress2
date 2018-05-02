package com.shensou.newpress.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shensou.newpress.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PopwindowTextAdapter extends BaseAdapter
{
	private Context mContext;
	private List<String> mList;
	public PopwindowTextAdapter(Context context,List<String> list )
	{
		this.mContext = context;
		this.mList = list;
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

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_text, null);

			ButterKnife.bind(holder, convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

	    holder.item_text.setText(mList.get(position));
		return convertView;
	}

	class ViewHolder
	{
		@Bind(R.id.item_text)
		TextView item_text;
		@Bind(R.id.item_line)
		View item_line;
	}

}
