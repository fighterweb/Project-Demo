package com.web.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MyWebView extends Activity{
	final String TAG = "START VIDEO";
	WebView mWebView;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webview);
        // Find view here.
        mWebView = (WebView)findViewById(R.id.webView1);
        WebSettings mWebSettings = mWebView.getSettings();
		// 加上这句话才能使用javascript方法
		mWebSettings.setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new Object(){
			
			@SuppressWarnings("unused")
			// The function must be public.
			public void showVideo(int num){
				
				Intent mIntent = new Intent();
				mIntent.setClass(MyWebView.this, VideoActivity.class);
				Bundle mBundle = new Bundle();
				
				if(num == 0){
					
					Log.i("START THE VIDEO","SHOW THE MESSAGE HERE");
					mBundle.putInt("VIDEO_ID", 0);

				}else if(num == 1){
					
					mBundle.putInt("VIDEO_ID", 1);
				}	
					mIntent.putExtras(mBundle);
					startActivity(mIntent);
			}
		},"imedia");
		//mWebView.loadUrl("http://192.168.1.136/imedia-fm/alpha/main.html");
        Bundle bundle = this.getIntent().getExtras();
        
        mWebView.loadUrl(bundle.getString("KEY_ADRESS"));       
	}
}
