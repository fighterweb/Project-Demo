package com.shawben.sui;

import com.shawben.sui.MusicPlayerService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MusicPage extends Activity{

	/*
	 * The Button of Music Control 
	 */
	private ImageButton mPlayBtn;
	//private ImageButton mPauseBtn;
	private ImageButton mStopBtn;
	private TextView musicInfo;
	public boolean mIsPaused=false;
	public boolean mIsPlaying=false;
	private MusicPlayerService mService;
	boolean mIsBound;
	
	private ServiceConnection mConnection = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName className, IBinder service){
			
			mService = ((MusicPlayerService.MyBinder)service).getMyService();
			Log.i("ActivityD֪ͨ��","Service���ӳɹ�");
			doUnbindService();
		}
		@Override
		public void onServiceDisconnected(ComponentName className)
		{
			mService = null;
		}
	};
	//��Service
	void doBindService()
	{
		
		getApplicationContext().bindService(new Intent(MusicPage.this, 
	            MusicPlayerService.class), mConnection, Context.BIND_AUTO_CREATE);
		Log.i("Activity֪ͨ��","����Service");
	    mIsBound = true;
	}
	//���Service
	void doUnbindService() {
	    if (mIsBound) {
	        // Detach our existing connection.
	        getApplicationContext().unbindService(mConnection);
	        mIsBound = false;
	        Log.i("ActivityD֪ͨ��","�����Service");
	    }
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        findViews();
        bindListener();
        doBindService();
        
	}
	public void findViews()
	{
		/*
         * The music panel Buttons
         * */
         mPlayBtn = (ImageButton)findViewById(R.id.mPlayBtn);
         //mPauseBtn = (ImageButton)findViewById(R.id.mPauseBtn);
         mStopBtn = (ImageButton)findViewById(R.id.mStopBtn);
         musicInfo = (TextView)findViewById(R.id.musicText);
	}
	public void bindListener()
	{
//////////Music Panel Buttons Event/////////////////////
    	mPlayBtn.setOnClickListener(new OnClickListener(){
    		
    		@Override
			public void onClick(View v)
    		{
    			
    			
    			
    			if(mIsPlaying)
    			{
    				try
    				{
    					mService.pauseMusic();
    					mIsPlaying = false;
    					mIsPaused = true;
    					mPlayBtn.setImageResource(R.drawable.start);
    					musicInfo.setText("Playing music");
    					return;
    				}catch(Exception e)
    				{
    					e.printStackTrace();
    					musicInfo.setText("�����쳣A");
    				}
    			}
    			else if(mIsPaused)
    			{
    				try{
    					
    					mService.startMusic();
    					mIsPlaying = true;
    					mIsPaused = false;
    					mPlayBtn.setImageResource(R.drawable.pause);
    					musicInfo.setText("Pause the music");
    					return;
    				}catch(Exception e){
    					e.printStackTrace();
    					musicInfo.setText("�����쳣B");
    				}
    			}
    			else
    			{
    				try{
    					mService.stopMusic();
    					mService.resetMusic();
    					mService.setDataSource("/sdcard/rain.mp3");
    					mService.prepare();
    					mService.startMusic();
    					mIsPlaying = true;
    					mIsPaused = false;
    					return;
    				}catch(Exception e)
    				{
    					e.printStackTrace();
    					musicInfo.setText("�����쳣c");
    				}
    				
    			}   			
    		}
    	});
//    	mPauseBtn.setOnClickListener(new OnClickListener(){
//    		public void onClick(View v)
//    		{
//    			//doBindService();
//    			
////    			int count = mService.getCount();
////    			Log.i("��ע�����������","count="+count);
//    		
//    		}
//    	});
    	/*ֹͣ���ŵİ�ť*/
    	mStopBtn.setOnClickListener(new OnClickListener(){
    		@Override
			public void onClick(View v)
    		{
    			//
    			try{
    				mService.stopMusic();			
    				mIsPlaying = false;
    				mIsPaused = false;
    				mPlayBtn.setImageResource(R.drawable.start);
    				musicInfo.setText("Stop the  music");
    				return;
    			} catch (Exception e){
    				e.printStackTrace();
    			}
    		}
    	});
	}
	@Override
    protected void onPause()
    {
    	super.onPause();
    	Log.i("ActivityD","Activity��ͣ");
    }
    //onResume
    @Override
    protected void onResume()
    {
    	super.onResume();
    	Log.i("ActivityD","Activity�ָ�");
    }
    //OnDestroy
    @Override
	protected void onDestroy()
    {
    	super.onDestroy();
    	Log.i("ActivityD","Activity�ݻ�");
    	//doUnbindService();
    }
}
