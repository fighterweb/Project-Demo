package cn.shawben.download;

import java.util.List;

import cn.shawben.download.AsyncImageLoader.ImageCallback;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


public class ImageGridViewAdapter extends ArrayAdapter<ImageInfo>{
	private ListView mListView;
	private AsyncImageLoader asyncImageLoader;
	int count = 6;
	public ImageGridViewAdapter(Activity activity, 
			List<ImageInfo> imageAndTexts, ListView listView) {
        super(activity, 0, imageAndTexts);
        this.mListView = listView;
        asyncImageLoader = new AsyncImageLoader();
    }
	// 适配器数据项的数量
	@Override
	public int getCount(){
		return count;
	}
	public View getView(int position, View convertView, ViewGroup parent){
		Activity activity = (Activity) getContext();
		// Inflate the views from XML
		View rowView = convertView;
		ViewCache viewCache;
		if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.adapter_layout_for_imagedownloader,
            		null);
            viewCache = new ViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ViewCache)rowView.getTag();
        }
		//注意这里
		ImageInfo imageInfo = getItem(position);
		// Load the image and set it on the ImageView
		String imageUrl = imageInfo.getImageUrl();
		ImageView imageView = viewCache.getImageView();
        imageView.setTag(imageUrl);
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
        		new ImageCallback(){

			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				// TODO Auto-generated method stub
				ImageView imageViewByTag = 
					(ImageView) mListView.findViewWithTag(imageUrl);
				 if (imageViewByTag != null) {
                     imageViewByTag.setImageDrawable(imageDrawable);
                 }
			}});
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.icon);
            Log.e("Adapter", "null");
        }else{
            imageView.setImageDrawable(cachedImage);
        }
        
		return rowView;
	}
}
