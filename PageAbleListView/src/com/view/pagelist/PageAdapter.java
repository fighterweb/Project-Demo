package com.view.pagelist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 用来生成ListView上显示的数据
 * 
 * @author 林肖斌
 */
public class PageAdapter extends BaseAdapter{
	
	int count = 12;// 一页12行
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// 适配器的数据项数量
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/**
	 * @param position 是列表项的位置，从0开始。
	 * @param convertView 列表每一项显示的View,通常retrun的view也是convertView。
	 * @param parent 是父窗体，使用这个适配器的ListView||GridView||Spinner。
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView txt = new TextView(parent.getContext());	
		txt.setText("Page ListView"+position);
		txt.setTextSize(20f);
		txt.setHeight(80);
		return txt;
	}

}
