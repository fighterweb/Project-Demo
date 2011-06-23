package com.web.view;

import android.app.Activity;
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
			private void showVideo(){
				Log.i(TAG,"Called here!");
			}
		},"imedia");
        Bundle bundle = this.getIntent().getExtras(); 
        mWebView.loadUrl(bundle.getString("KEY_ADRESS"));       
	}
}
