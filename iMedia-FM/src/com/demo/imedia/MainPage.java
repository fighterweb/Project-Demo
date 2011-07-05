package com.demo.imedia;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainPage extends ActivityGroup{
	ViewFlipper fliper;
	Button buttonHome;
	Button buttonMovie;
	Button buttonTV;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.framework);
		
		FindViews();
		AddViews();
		BindListener();
	}

	private void AddViews() {
		// TODO Auto-generated method stub
		Intent intent = null;
		intent = new Intent(MainPage.this,HomePage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View ActivityA = getLocalActivityManager().startActivity(  
                "subActivity", intent).getDecorView();
		fliper.addView(ActivityA, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		intent = new Intent(MainPage.this,MoviePage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View ActivityB = getLocalActivityManager().startActivity(  
                "subActivity", intent).getDecorView();
		fliper.addView(ActivityB, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		intent = new Intent(MainPage.this,TVPage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		View ActivityC = getLocalActivityManager().startActivity(  
                "subActivity", intent).getDecorView();
		fliper.addView(ActivityC, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}

	private void FindViews() {
		// TODO Auto-generated method stub
		fliper = (ViewFlipper)findViewById(R.id.pages_container);
		buttonHome = (Button)findViewById(R.id.button_home_page);
		buttonMovie = (Button)findViewById(R.id.button_movie_page);
		buttonTV = (Button)findViewById(R.id.button_tv_page);
	}
	private void BindListener() {
		// TODO Auto-generated method stub
		
	}
}