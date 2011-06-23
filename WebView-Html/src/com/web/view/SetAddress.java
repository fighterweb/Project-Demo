package com.web.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetAddress extends Activity {
	EditText editAdress;
	Button   buttonGo;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Find views here.
        editAdress = (EditText)findViewById(R.id.editText_address);
        buttonGo = (Button)findViewById(R.id.button_go);
        // Bind Listen here.
        buttonGo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SetAddress.this, MyWebView.class);
				Bundle bundle = new Bundle();
				bundle.putString("KEY_ADRESS", editAdress.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}});
        
    }
}