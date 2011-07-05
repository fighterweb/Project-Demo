package com.shawben.sui;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyButtonBarView extends GridView{

	public MyButtonBarView(Context context)
	{
		super(context);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	//��д��onTouchEvent�ص����� 
	switch(event.getAction()){
	//����
	case MotionEvent.ACTION_DOWN:
	System.out.println("ACTION_DOWN");
	return super.onTouchEvent(event);
	//����
	case MotionEvent.ACTION_MOVE:
	System.out.println("ACTION_MOVE");
	break;
	//�뿪
	case MotionEvent.ACTION_UP:
	System.out.println("ACTION_UP");
	return super.onTouchEvent(event);
	}
	//ע�⣺����ֵ��false
	return false;
	}
}
