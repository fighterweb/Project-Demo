package com.view.pagelist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * ��������ListView����ʾ������
 * 
 * @author ��Ф��
 */
public class PageAdapter extends BaseAdapter{
	
	int count = 12;// һҳ12��
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// ������������������
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
	 * @param position ���б����λ�ã���0��ʼ��
	 * @param convertView �б�ÿһ����ʾ��View,ͨ��retrun��viewҲ��convertView��
	 * @param parent �Ǹ����壬ʹ�������������ListView||GridView||Spinner��
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
