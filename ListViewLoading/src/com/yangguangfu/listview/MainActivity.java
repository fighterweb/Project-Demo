package com.yangguangfu.listview;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout.LayoutParams;
/**
 * 
 * @author ����
 *
 */
public class MainActivity extends ListActivity implements OnScrollListener {
	
	private static final String TAG = "MainActivity";
	private listViewAdapter adapter = new listViewAdapter();
	private int lastItem = 0;
	/**
	 * ���ò�����ʾΪĿ���ж��Ͷ��
	 */
    private LayoutParams WClayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    /**
	 * ���ò�����ʾĿ�����
	 */
    private LayoutParams FFlayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
    
	private ProgressBar progressBar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "onCreate(Bundle savedInstanceState)" );
	    //���Բ���
		LinearLayout layout = new LinearLayout(this);
	   //���ò��� ˮƽ����
		layout.setOrientation(LinearLayout.HORIZONTAL);
		 //������
		progressBar = new ProgressBar(this);
		 //��������ʾλ��
		progressBar.setPadding(0, 0, 15, 0);
		
		layout.addView(progressBar, WClayoutParams);
		
		TextView textView = new TextView(this);
		textView.setText("������...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		
		layout.addView(textView, FFlayoutParams);
		layout.setGravity(Gravity.CENTER);
		
		LinearLayout loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, WClayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);
		
		//�õ�һ��ListView������ʾ��Ŀ
		ListView listView = getListView();
		//��ӵ���ҳ��ʾ
		listView.addFooterView(loadingLayout);
		//
		registerForContextMenu(listView);
		//
		setListAdapter(adapter);
		listView.setOnScrollListener(this);
	}

	public void onScroll(AbsListView v, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1;
		System.out.println("lastItem:" + lastItem);
		Log.i(TAG, "lastItem:" + lastItem);
		
	}

	public void onScrollStateChanged(AbsListView v, int state) {
		if (lastItem == adapter.count
				&& state == OnScrollListener.SCROLL_STATE_IDLE) {
			adapter.count += 10;
			adapter.notifyDataSetChanged();
			Log.i(TAG, "lastItem:" + lastItem);
		}
	}
  /**
   * Ҫ������������ʾ����
   * @author ����
   *
   */
	class listViewAdapter extends BaseAdapter {
		int count = 10;

		public int getCount() {
			Log.i(TAG, "count:" + count);
			return count;
		}

		public Object getItem(int pos) {
			Log.i(TAG, "pos:" + pos);
			return pos;
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			TextView view = new TextView(MainActivity.this);
			view.setText("���������� " + pos);
			Log.i(TAG, "entry: " + pos);
			view.setTextSize(20f);
			view.setHeight(80);
			return view;
		}
	}
}