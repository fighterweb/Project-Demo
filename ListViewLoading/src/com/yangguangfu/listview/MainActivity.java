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
 * @author 阿福
 *
 */
public class MainActivity extends ListActivity implements OnScrollListener {
	
	private static final String TAG = "MainActivity";
	private listViewAdapter adapter = new listViewAdapter();
	private int lastItem = 0;
	/**
	 * 设置布局显示为目标有多大就多大
	 */
    private LayoutParams WClayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    /**
	 * 设置布局显示目标最大化
	 */
    private LayoutParams FFlayoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
    
	private ProgressBar progressBar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "onCreate(Bundle savedInstanceState)" );
	    //线性布局
		LinearLayout layout = new LinearLayout(this);
	   //设置布局 水平方向
		layout.setOrientation(LinearLayout.HORIZONTAL);
		 //进度条
		progressBar = new ProgressBar(this);
		 //进度条显示位置
		progressBar.setPadding(0, 0, 15, 0);
		
		layout.addView(progressBar, WClayoutParams);
		
		TextView textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		
		layout.addView(textView, FFlayoutParams);
		layout.setGravity(Gravity.CENTER);
		
		LinearLayout loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, WClayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);
		
		//得到一个ListView用来显示条目
		ListView listView = getListView();
		//添加到脚页显示
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
   * 要用用于生成显示数据
   * @author 阿福
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
			view.setText("阿福播放器 " + pos);
			Log.i(TAG, "entry: " + pos);
			view.setTextSize(20f);
			view.setHeight(80);
			return view;
		}
	}
}