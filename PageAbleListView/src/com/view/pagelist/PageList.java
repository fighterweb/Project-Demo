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
 * @author 林肖斌
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
		 * 用mLinear做为load-progress的容器。
		 */
		LinearLayout mLinear = new LinearLayout(this);
		mLinear.setOrientation(LinearLayout.HORIZONTAL);
		mProgressBar = new ProgressBar(this);
		mProgressBar.setPadding(0, 0, 15, 0);
		mLinear.addView(mProgressBar, WClayoutParams);

		TextView textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		mLinear.addView(textView, FFlayoutParams);
		
		mLinear.setGravity(Gravity.CENTER);

		registerForContextMenu(mListView);
		// 将load-progress加进ListView的最底行
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
			// 如果ListView的最后一项，刚好等于适配器里的总数据项，
			// 就执行发下代码：
			mAdapter.count += 10; // 让适配器里的总数据项再加10
			mAdapter.notifyDataSetChanged(); // 刷新适配器
			Log.i(TAG, "lastItem:" + lastItem);
		}
	}

	/**
	 * 要用用于生成显示数据。
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
