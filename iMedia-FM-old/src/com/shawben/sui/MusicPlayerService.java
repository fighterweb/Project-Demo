package com.shawben.sui;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicPlayerService extends Service {

	private String Path="/sdcard/rain.mp3";
	MediaPlayer mp;
	/*
	 * Service �Ĵ���
	 * */
	@Override
	public void onCreate()
	{
		Log.i("Service֪ͨ��","service�����ɹ�");
		mp = new MediaPlayer();
		try{
    		mp.setDataSource(Path);
    		mp.prepare();
    	}catch(Exception e )
    	{
    		
    		e.printStackTrace();
    	}
		super.onCreate();
		
	}
	/** 
     * ���û�����bindService����ʱ�ᴥ���÷��� ����һ��IBinder����
     * ���ǿ���ͨ���ö��󣬶�service�� ��ĳЩ���ݽ��з��� 
     */  
	@Override
	public IBinder onBind(Intent intent)
	{
		Log.i("Service֪ͨ��","service�󶨳ɹ�");
		return new MyBinder();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        
		//mp.start();
		Log.i("���ط���", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mp.stop();
		mp.release();
		Log.i("֪ͨ��","service������");
		
	}
	@Override  
    public void onRebind(Intent intent) {  
        Log.i("֪ͨ", "service���°󶨳ɹ���");  
        super.onRebind(intent);  
    } 
	/*����һ�����Ժ���*/
	private int count = 100;
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	/**********���¿�ʼ��Ϊ���ֲ��ſ��Ƶĺ���*************/
	public void	prepare(){
  		try {
			mp.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
  	}
	public void startMusic()
	{
		mp.start();//��ʼ��������
	}
	public void pauseMusic()
	{
		mp.pause();//��ͣ��������
	}
	public void stopMusic()
	{
		mp.stop();//ֹͣ��������
	}
	public void resetMusic()
	{
		mp.reset();
	}
	public void setDataSource(String path)
	{
		try {	  			
			mp.setDataSource(path);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**************���Ƿָ���****************************/
	public class MyBinder extends Binder
	{
		/*
		 * 
		 * ����һ�����˵�service����
		 * 
		 * */
		MusicPlayerService getMyService()
		{
			return MusicPlayerService.this;
		}
	}
}
