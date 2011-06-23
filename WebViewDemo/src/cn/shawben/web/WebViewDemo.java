package cn.shawben.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class WebViewDemo extends Activity {
	/** Called when the activity is first created. */
	private WebView mWebView;
	private Button mButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setupViews();
	}

	private void setupViews() {
		// TODO Auto-generated method stub
		mWebView = (WebView) findViewById(R.id.webview);
		WebSettings mWebSettings = mWebView.getSettings();
		// ������仰����ʹ��javascript����
		mWebSettings.setJavaScriptEnabled(true);
		// ���ӽӿڷ���,��htmlҳ�����
		mWebView.addJavascriptInterface(new Object() {
			// �����Ҷ�����һ���򿪵�ͼӦ�õķ���
			@SuppressWarnings("unused")
			public void startMap() {
				Intent mIntent = new Intent();
//				 ComponentName component = new ComponentName(
//				 "com.google.android.apps.maps",
//				 "com.google.android.maps.MapsActivity");
//				 mIntent.setComponent(component);
//				 startActivity(mIntent);
				mIntent.setClass(WebViewDemo.this, VideoActivity.class);
				startActivity(mIntent);
			}
			@SuppressWarnings("unused")
			public void showVideo() {
				Log.i("START THE VIDEO","SHOW THE MESSAGE HERE");
				Intent mIntent = new Intent();
				mIntent.setClass(WebViewDemo.this, VideoActivity.class);
				startActivity(mIntent);
			}
		}, "imedia");
		// ����ҳ��
		//mWebView.loadUrl("file:///android_asset/alpha/main.html");
		mWebView.loadUrl("http://192.168.1.136/imedia-fm/alpha/main.html");
		mButton = (Button) findViewById(R.id.button);
		// ��button����¼���Ӧ,ִ��JavaScript��fillContent()����
		mButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				mWebView.loadUrl("javascript:fillContent()");
				
			}
		});
	}

	private void quitDialog() {
		new AlertDialog.Builder(this).setMessage("Do you want to exit?")
				.setPositiveButton("ok", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						WebViewDemo.this.finish();

					}
				}).setNegativeButton("return", null).show();
	}

	@Override
	public void onBackPressed() {
		// ���ﴦ���߼����룬���ע�⣺�÷�����������2.0����°��sdk
		// Log.i("main����","���ء�����");
		quitDialog();
		return;
	}
}