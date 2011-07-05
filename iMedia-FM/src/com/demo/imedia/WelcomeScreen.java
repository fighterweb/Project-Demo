package com.demo.imedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class WelcomeScreen extends Activity {
	final static String TAG = "WelcomeScreen";
	Button buttonStart;
	Button buttonNext;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.overlay);
		// Find the views here.
		FindView();
		// Bind Listener here.
		BindListener();
	
	}
	private void FindView() {
		// TODO Auto-generated method stub
		buttonStart = (Button)findViewById(R.id.button_showhow);
		buttonNext = (Button)findViewById(R.id.button_show_next);
	}
	private void BindListener() {
		// TODO Auto-generated method stub
		buttonNext.setOnClickListener(showMainPage);
	}
	// goes to main page.
	private Button.OnClickListener showMainPage = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(WelcomeScreen.this, MainPage.class);
			startActivity(intent);
		}
	};
}
