package com.shensou.newpress.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.fragment.HelpFragment;

import java.util.ArrayList;
import java.util.List;

public class HelpPagerAdapter extends FragmentStatePagerAdapter {

	private List<HelpFragment> fragments = new ArrayList<>();
	private List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> userChannelLists;
	private final FragmentManager fm;

	public HelpPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm = fm;
	}

	public HelpPagerAdapter(FragmentManager fm, List<HelpFragment> fragments, List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> userChannelLists) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm = fm;
		this.fragments=fragments;
		this.userChannelLists=userChannelLists;
	}



	public void appendList(ArrayList<HelpFragment> fragment) {
		fragments.clear();
		if (!fragments.containsAll(fragment) && fragment.size() > 0) {
			fragments.addAll(fragment);
		}
		notifyDataSetChanged();
	}
	
	public void setChannelItemLists(List<YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean> userChannelLists){
		this.userChannelLists=userChannelLists;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return userChannelLists.get(position).getTitle();
	}
//	public View getTabView(int position){
//		View view=LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.item_text_horizontal_listview,null);
//		TextView tv= (TextView)view.findViewById(R.id.item_text);
//		tv.setText(userChannelLists.get(position).getTitle());
//		return view;
//	}
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void setFragments(ArrayList<HelpFragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
//		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (fragments.size() <= position) {
			position = position % fragments.size();
		}
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

}
