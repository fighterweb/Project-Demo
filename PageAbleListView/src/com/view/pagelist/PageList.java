package com.view.pagelist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * @author ��Ф��
 * 
 */
public class PageList extends Activity implements OnScrollListener {

	private static final String TAG = "PageList";
	//private listViewAdapter adapter = new listViewAdapter();
	PageAdapter mAdapter; 
	ListView mListView;
	ProgressBar mProgressBar;
	private LayoutParams WClayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);
	private int lastItem;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Find Views
		mListView = (ListView) findViewById(R.id.listView1);
		/**
		 * ��mLinear��Ϊload-progress��������
		 */
		LinearLayout mLinear = new LinearLayout(this);
		mLinear.setOrientation(LinearLayout.HORIZONTAL);
		mProgressBar = new ProgressBar(this);
		mProgressBar.setPadding(0, 0, 15, 0);
		mLinear.addView(mProgressBar, WClayoutParams);

		TextView textView = new TextView(this);
		textView.setText("������...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		mLinear.addView(textView, FFlayoutParams);
		
		mLinear.setGravity(Gravity.CENTER);

		registerForContextMenu(mListView);
		// ��load-progress�ӽ�ListView�������
		mListView.addFooterView(mLinear);
		mAdapter = new PageAdapter();
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(this);

	}

	public void onScroll(AbsListView v, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount - 1;
		System.out.println("lastItem:" + lastItem);
		Log.i(TAG, "lastItem:" + lastItem);

	}

	public void onScrollStateChanged(AbsListView v, int state) {
		if (lastItem == mAdapter.count
				&& state == OnScrollListener.SCROLL_STATE_IDLE) {
			// ���ListView�����һ��պõ�������������������
			// ��ִ�з��´��룺
			mAdapter.count += 10; // ��������������������ټ�10
			mAdapter.notifyDataSetChanged(); // ˢ��������
			Log.i(TAG, "lastItem:" + lastItem);
		}
	}

	/**
	 * Ҫ������������ʾ���ݡ�
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
			TextView view = new TextView(PageList.this);
			view.setText("Item " + pos);
			Log.i(TAG, "entry: " + pos);
			view.setTextSize(20f);
			view.setHeight(80);
			return view;
		}
	}
}
