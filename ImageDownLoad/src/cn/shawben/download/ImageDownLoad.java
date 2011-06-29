package cn.shawben.download;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class ImageDownLoad extends Activity implements OnScrollListener{
	final String TAG = "ImageDownLoad";
	ProgressBar mProgressBar;
	private LayoutParams WClayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);
	private int lastItem;
	List<ImageInfo> list = new ArrayList<ImageInfo>();
	
	ImageGridViewAdapter mAdapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView listView = (ListView)findViewById(R.id.myGridView);
        
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
		registerForContextMenu(listView);
		
		listView.addFooterView(mLinear);
		
		listView.setOnScrollListener(this);
		
		addImageForListView();
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home1.png"));
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home2.png"));
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home3.png"));
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home4.png"));
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home5.png"));
//        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home6.png"));
        
        mAdapter = new ImageGridViewAdapter(this,list,listView);
        listView.setAdapter(mAdapter);
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
			mAdapter.count += 6; // ��������������������ټ�6,���������adapter�������һ��
			addImageForListView();
			mAdapter.notifyDataSetChanged(); // ˢ��������
			Log.i(TAG, "lastItem:" + lastItem);
		}
	}
	public void addImageForListView(){
		
		list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home1.png"));
        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home2.png"));
        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home3.png"));
        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home4.png"));
        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home5.png"));
        list.add(new ImageInfo("http://d3o76j76sx0r0h.cloudfront.net/home/img_home6.png"));
	}
}