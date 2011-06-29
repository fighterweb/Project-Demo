package com.yangguangfu.listview;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
/**
 * 
 * @author ����
 *
 */
public class ButtonLoadingActivity extends ListActivity    {
	private static final String TAG = "ButtonLoadingActivity";
       
    private  listViewAdapter adapter = new listViewAdapter();
    /**
	 * ���ò�����ʾΪĿ���ж��Ͷ��
	 */
    private LayoutParams WClayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    /**
	 * ���ò�����ʾĿ�����
	 */
    private LayoutParams FFlayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
    
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             Log.i(TAG, "onCreate(Bundle savedInstanceState):" );
              LinearLayout layout = new LinearLayout(this);
              layout.setOrientation(LinearLayout.HORIZONTAL);
             
              Button button = new Button(this);
              button.setText("�������������...");
              button.setGravity(Gravity.CENTER_VERTICAL);
               
              layout.addView(button,FFlayoutParams);
              layout.setGravity(Gravity.CENTER);
              LinearLayout loadingLayout = new LinearLayout(this);
              loadingLayout.addView(layout,WClayoutParams);
              loadingLayout.setGravity(Gravity.CENTER);
               
               
              ListView listView = getListView();
               
              listView.addFooterView(loadingLayout);
               
              button.setOnClickListener(new Button.OnClickListener()   
              {         @Override      
                 public void onClick(View v)  
              {         
                  adapter.count += 5;  
                  Log.i(TAG, "setOnClickListener:" +  adapter.count);
                  adapter.notifyDataSetChanged();
                  }      
              });  
             
          setListAdapter(adapter);  
     }


    public void onScrollStateChanged(AbsListView v, int s) {
    	 Log.i(TAG, "onScrollStateChanged(AbsListView v, int s)");
    }     

    class listViewAdapter extends BaseAdapter {
        int count = 10; /* starting amount */

        public int getCount() { return count; }
        public Object getItem(int pos) { return pos; }
        public long getItemId(int pos) { return pos; }

        public View getView(int pos, View v, ViewGroup p) {
                 TextView textView = new TextView(ButtonLoadingActivity.this);
                 textView.setHeight(80);
                 textView.setTextSize(20);
                 textView.setText("���������� " + pos);
                 Log.i(TAG, "getView:pos:" + pos);
                return textView;
         }
     }



}