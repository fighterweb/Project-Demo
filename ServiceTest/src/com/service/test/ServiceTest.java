package com.service.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServiceTest extends Activity {
	//String path = "http://shawbenwiki.github.com/iMedia-FM/MySQL.xml";
	// String path = "http://shawbenwiki.github.com/iMedia-FM/XML.xml";
	//String path = "http://192.168.1.92/WebSite1/MySQL.aspx";
	String parameter = "?page=";
	String path = "http://192.168.1.230:8080/iMediafmsvr02/MyTestServlet"+parameter+"2";
	final String TAG = "SERVICE TEST";
	List<ImageCache> imgCaches;
	Button buttonTest;
	TextView txtXML;
	TextView txtRun;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Find View here.
        buttonTest = (Button)findViewById(R.id.button_test);
        txtXML = (TextView)findViewById(R.id.txt_xml);
        txtRun = (TextView)findViewById(R.id.txt_runing_info);
        // Bind Button Listener.
        buttonTest.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Thread thread = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						mHandler.sendMessage(msg);
					}});
				thread.start();// Start thread here.
				txtRun.setText("START TO READ THE XML!");
			}});    
        
    }
    Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		readTheXML();
    	}
    };
	private void readTheXML() {
		
		String data = null;
		// Create a Http Client here.
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// Get Request.
		HttpGet getRequest = new HttpGet(path);
		// Get Response
		try {
			HttpResponse response = httpClient.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != HttpStatus.SC_OK){
				txtRun.setText("Get Response error!");
				// START
				 Log.e(TAG,"Get Response error!");
				// END
			}
			// Get entity
			HttpEntity entity = response.getEntity();
			if(entity != null){
				txtRun.setText("Get Entity Successfull");
				InputStream in = null;
				in = entity.getContent();
				imgCaches = SAXXmlService.xmlOpera(in);
				// print the data.
				for(ImageCache img: imgCaches){
					// START
					 Log.i(TAG,img.getName());
					// END
					data = data + "Name=" + img.getName() + " Hot="+ img.getHot() + "\n";			
				}
				txtXML.setText(data);
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}