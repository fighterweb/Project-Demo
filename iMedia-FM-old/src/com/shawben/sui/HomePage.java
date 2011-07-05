package com.shawben.sui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

public class HomePage extends Activity implements  
OnTouchListener, android.view.GestureDetector.OnGestureListener{

	private ViewFlipper vfliper_home;//����home������
	private Button button_back_home;
	private Button button_home;
	private Button button_play_left;//��ߵ�play��ť
	private RelativeLayout hide_layout;
	private LinearLayout hide_linear;	
	private LinearLayout home_layout;//����home�Ĳ���
	private Button button_recent;
	private GridView view_imgGrid;//��ʾcacheͼƬ�Ĵ���
	private GridView view_imgList;//��ʾͼƬ�б�Ĵ���
	private GridView view_msgGrid;//��ʾ��Ϣ�Ĵ���
	private ImageButton imgBtn;
	private ImageButton imgBtnLeft;
	private GestureDetector mGestureDetector;//ע������ʶ��
	private ViewFlipper flip;//����������ұ���Ƶ�б����Ϣ
	private ViewFlipper flip_left;//���ڣ���������Ƶ�б���Ϣ
	private SimpleAdapter myMsgAdapter;//����ͼƬ������
	private ProgressBar loadImgCache,pBar_loading; //����CacheͼƬ����Ȧ
	List<Bitmap> mapCache,mapCache2;//ͼƬ������mapCache 
	ImgCacheAdapter myImgCache,myImgCache2;	//ͼƬ������
	List<ImageCache> imgCaches;//��Ŵ�XML�ϻ�ȡ����Ϣ
	private Bitmap mBmp;//���������ȡ��һ��ͼƬ��������
	private LinearLayout loadingLayout;//the loading
	private VideoView mVideoView;//��Ƶ���Ŵ���
	private MediaController mMediaController;//���ſ���
	private TextView warning_info;//��ȡ����sd����ͼƬ�ľ�����Ϣ
	int width_imgCache;
	int iCount,loadingCount;//��������progressBar
	private boolean readfiles=true; //�ж�xml�ļ��Ƿ����
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        //��Ϣ����������
        myMsgAdapter = new SimpleAdapter(this,getMsgInfo(),R.layout.list_msg,
        			   new String[]{"title","info","img"},
        			   new int[]{R.id.title,R.id.info,R.id.img});
        //����cacheͼƬ�Ŀ��
        width_imgCache = ((((this.getWindowManager().getDefaultDisplay().getWidth())-35)*54/100)-30)/3;
        
        findView();
        bindListener();
        //��ʼ����Ϣ��ʾ�����������
        view_msgGrid.setNumColumns(0);
        view_msgGrid.setVerticalSpacing(5);       
        view_msgGrid.setAdapter(myMsgAdapter);
        //��ʼ��ͼƬ�б�
        view_imgList.setNumColumns(2);       
        view_imgList.setVerticalSpacing(9);
              
        mapCache = new ArrayList<Bitmap>();
        mapCache2 = new ArrayList<Bitmap>();
        
        
        view_imgGrid.setVerticalSpacing(3);
        view_imgGrid.setHorizontalSpacing(8);
        view_imgGrid.setNumColumns(3);
        
       
        
        mapCache.clear();
		readXML(1);
        myImgCache = new ImgCacheAdapter(HomePage.this,mapCache,width_imgCache,36); 
        view_imgGrid.setAdapter(myImgCache);
        view_imgGrid.setOnItemClickListener(new MyItemClick());
           
   	 	//���������ͼƬ����Ϣ
        view_imgList.setOnItemClickListener(new ItemClickEvent());
        
        //���Ƽ����
        mGestureDetector = new GestureDetector(this);
        
        hide_layout.setVisibility(View.GONE);
		hide_linear.setVisibility(View.GONE);
		
		loadImgCache.setVisibility(View.INVISIBLE);
		flip.setDisplayedChild(0);
		
		//��ʾ����ͼƬ�б�
		showImageList();
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
	 	
	}

	private void showImageList() {
		// TODO Auto-generated method stub
		if(readfiles == true){			
			List<ImageInfo> list = new ArrayList<ImageInfo>();
			for(ImageCache img : imgCaches){
				if("IMGLIST".equals(img.getCategory())){
				list.add(new ImageInfo(img.getLink()));
				}
				Log.i("showImageList","coming here");
			}
			view_imgList.setAdapter(new ImageGridViewAdapter(this,list,view_imgList));
			loadingLayout.setVisibility(View.GONE);
		}
	}

	public void findView(){
		
		vfliper_home = (ViewFlipper)findViewById(R.id.vfliper_home);
		button_home = (Button)findViewById(R.id.homeBtn);
		button_recent = (Button)findViewById(R.id.recentButton_home);	
		hide_layout = (RelativeLayout)findViewById(R.id.to_hide_layout_home);
		hide_linear = (LinearLayout)findViewById(R.id.linear_hide_home);
		home_layout = (LinearLayout)findViewById(R.id.myhome_layout);		
		view_imgGrid = (GridView)findViewById(R.id.tv_grid_home);		
		view_imgList = (GridView)findViewById(R.id.tv_list_home);		
		view_msgGrid = (GridView)findViewById(R.id.msg_home);
		imgBtn = (ImageButton)findViewById(R.id.img_content);
		imgBtnLeft = (ImageButton)findViewById(R.id.img_content_left);
		flip = (ViewFlipper)findViewById(R.id.vfliper_content);
		flip_left = (ViewFlipper)findViewById(R.id.vflipLeft);
		loadImgCache = (ProgressBar)findViewById(R.id.imgCacheLoading);
		pBar_loading = (ProgressBar)findViewById(R.id.loadingBar);
		button_play_left = (Button)findViewById(R.id.btn_content_play_left);
		loadingLayout = (LinearLayout)findViewById(R.id.fullscreen_loading_indicator);
		mVideoView = (VideoView)findViewById(R.id.view_videoPlayer);
		button_back_home = (Button)findViewById(R.id.button_back_home);
		warning_info = (TextView)findViewById(R.id.txt_error_read_files);
		flip.setOnTouchListener(this);
		flip.setLongClickable(true);
		
	}
	//�ұ�ͼƬ�б��Item��Ӧ����
	class ItemClickEvent implements OnItemClickListener{
    	
    	@Override
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
    	{
    		flip.setInAnimation(getApplicationContext(),R.anim.slide_left_in);
			flip.setOutAnimation(getApplicationContext(),R.anim.slide_left_out);			
    		flip.setDisplayedChild(1);
    	}
    }
	//���ͼƬ�б��Item��Ӧ����
	class MyItemClick implements OnItemClickListener{
		
		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
		{
			flip_left.setInAnimation(getApplicationContext(),R.anim.slide_right_in);
			flip_left.setOutAnimation(getApplicationContext(),R.anim.slide_right_out);
			flip_left.setDisplayedChild(1);
		}
	}
	//FLIPPER ʶ�����ƻ���
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
            float velocityY) {  
        // TODO Auto-generated method stub  
        if (e2.getX()-e1.getX() > 100) {  
            //�һ� 
            showNextView();  
        } else if (e1.getX() - e2.getX() > 100) {  
            //�� 
            showPreviousView();  
        }  
        return false;  
    }  
	public void showNextView(){
		//��������뻬���Ķ���
	}	
	public void showPreviousView(){
		//��������뻬���Ķ���
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {  
        // TODO Auto-generated method stub  
        return mGestureDetector.onTouchEvent(event);  
    }  
	@Override
	public boolean onDown(MotionEvent arg0) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
	@Override
	public void onLongPress(MotionEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,  
            float distanceY) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
    @Override
	public void onShowPress(MotionEvent e) {  
        // TODO Auto-generated method stub  
          
    }  
    @Override
	public boolean onSingleTapUp(MotionEvent e) {  
        // TODO Auto-generated method stub  
        return false;  
    }  
	public void bindListener(){
		
		button_home.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v){
				
				if(hide_layout.getVisibility() == View.GONE)
        		{
					TranslateAnimation animation = new TranslateAnimation(0, 0, -62, 0);
					
					//animation.setFillAfter(true);
					animation.setDuration(300);
					//animation.setStartOffset(250);
					//animation.setAnimationListener(new RemoveAnimationListener());
					
					hide_layout.setVisibility(View.VISIBLE);
					hide_linear.setVisibility(View.VISIBLE);
					
					home_layout.startAnimation(animation);

        		}
        		else 
        		{
        			TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -64);
					
        			//animation.setFillAfter(true);
					animation.setDuration(300);
					//animation.setStartOffset(250);
					animation.setAnimationListener(new RemoveAnimationListener());
														
					//Log.i("����","����");
					home_layout.startAnimation(animation);
														
        		}
				
			}
		});
		
		button_recent.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v){
				
				//ִ��˳��һ�����ܴ�
				loadImgCache.setVisibility(View.VISIBLE);
				mapCache.clear();
				//����һ���߳�
				Thread mThread = new Thread(new Runnable(){
					
					@Override
					public void run(){
						
						for(int i = 0; i<10; i++)
						{
							try{
								iCount = (i+1)*5;
								//˯һ����
								Thread.sleep(100);
								
								if(i == 9)
								{
									Message msg = new Message();
									msg.what = 0;
									mHandler.sendMessage(msg);
									break;
									
								}
								else
								{
									Message msg = new Message();
									msg.what = 1;
									mHandler.sendMessage(msg);
								}
								
								
							}catch(Exception e){
								e.printStackTrace();
							}
						}						
					}
				});
				
				//�����߳�
				mThread.start();	       
			}
		});
		imgBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v){
				
				flip.setInAnimation(getApplicationContext(),R.anim.slide_right_in);
				flip.setOutAnimation(getApplicationContext(),R.anim.slide_right_out);
				flip.setDisplayedChild(0);
			}
		});
		imgBtnLeft.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				flip_left.setInAnimation(getApplicationContext(),R.anim.slide_left_in);
				flip_left.setOutAnimation(getApplicationContext(),R.anim.slide_left_out);
				flip_left.setDisplayedChild(0);
			}
		});
		button_play_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.i("����ButtonOnClick","����ButtonOnClick");
//				vfliper_home.setDisplayedChild(1);
//				//��ʼ������Ƶ
//				mVideoView.setVideoURI(mUri);
//		    	mVideoView.start();
		    	Intent intent = new Intent(HomePage.this,VideoPlayer.class);
		    	startActivity(intent);       
			}
		});
		button_back_home.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vfliper_home.setDisplayedChild(0);
				mVideoView.stopPlayback();
			}
			
		});
	}
	//���� һ��Handler
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			//����Ϣ��0��ʾ�߳̽���������ԲȦ����
			//����Ϣ��1��ʾ�̼߳�������
			switch(msg.what)
			{
				case 0:
					loadImgCache.setVisibility(View.INVISIBLE);	
					readXML(1);		
			        myImgCache = new ImgCacheAdapter(HomePage.this,mapCache,width_imgCache,35); 
			        view_imgGrid.setAdapter(myImgCache);
					break;
				case 1:
					loadImgCache.setProgress(iCount);
					break;
				case 3:
					hide_layout.setVisibility(View.GONE);
					hide_linear.setVisibility(View.GONE);
					break;
			}
		} 
	};
	 private class RemoveAnimationListener implements AnimationListener{
	    	//�÷����ڵ���Ч��ִ�н���֮�󱻵���
			@Override
			public void onAnimationEnd(Animation animation) 
			{
				
				hide_layout.setVisibility(View.GONE);
				hide_linear.setVisibility(View.GONE);
				System.out.println("end");
				//Log.i("����","����");
				
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				//Log.i("����","�ز�");
				System.out.println("repeat");
			}

			@Override
			public void onAnimationStart(Animation animation) {
				
				//Log.i("����","��ʼ");
				System.out.println("start");
			
			}
	    	
	    }
	 //Handler-��Ӧ��ʼ���ұ�ͼƬ���߳�
	 //���̰߳�ͼƬ��ȡ������ʱ����Message����Handler���գ�
	 private Handler imgHandler = new Handler()
	 {
		 @Override
		public void handleMessage(Message msg)
		 {
			 switch(msg.what)
			 {	

			 	 case 0: 
			 		 	
			 		 	//dismiss the loading
			 		 	loadingLayout.setVisibility(View.GONE);
			 		 	//��ͼƬװ����������myImgCache������ʾ����
			 		 	myImgCache2 = new ImgCacheAdapter(HomePage.this,mapCache2,80,80);
			 		 	view_imgList.setAdapter(myImgCache2);
			 		 	
			 		 	break;
			 	 case 1:
			 			//���ｫBitMap����������
			 			
			 		 	pBar_loading.setProgress(loadingCount);
			 			Log.i("��Ǽ���Bitmap","��Ǽ���Bitmap");
			 			break;
			 }
		 }
	 };
	//��ʼ���ұ�ͼƬ�б�
	public void initImageList()
	{
		loadingCount=1;
		//�½�һ���߳�
		Thread th = new Thread(new Runnable(){	
			@Override
			public void run(){
				int times = 0;
				for(ImageCache img : imgCaches){
					times++;
					if("IMGLIST".equals(img.getCategory())){
						loadingCount++;
						//�����￪ʼ��ȡͼƬ
						//����Clientʵ��
						DefaultHttpClient httpClient = new DefaultHttpClient();
						//�������ӵ�--img.getLink
						HttpGet getRequest = new HttpGet(img.getLink());
						try {
							//���ӷ�����
							HttpResponse response = httpClient.execute(getRequest);
							final int statusCode = response.getStatusLine().getStatusCode();
							if (statusCode != HttpStatus.SC_OK) {
					    		Log.w("ͼƬ�����̣߳�", "Error " + statusCode + " while retrieving bitmap from net");		
					    	}
							//ȡ�����ݼ�¼
							HttpEntity entity = response.getEntity();
							if(entity != null){
								InputStream is = null;
								//ȡ����������
								is = entity.getContent();
								mBmp = BitmapFactory.decodeStream(is);
								mapCache2.add(mBmp);
								//������Ϣ��Handler����
								Message msg = new Message();
								msg.what =1;
								imgHandler.sendMessage(msg);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//������ֹ
							getRequest.abort();
					    	Log.e("ͼƬ�����̣߳�", "Error while retrieving bitmap from net");
						}
					}//END--if("IMGLIST".equals(img.getCategory()))
					else if (times == imgCaches.size()){	
						Log.i("���imgCaches",String.valueOf(imgCaches.size()));
						//�����߳̽�������Ϣ
						Message msg = new Message();
						msg.what = 0;
						imgHandler.sendMessage(msg);
					}
				}//END--for(ImageCache img : imgCaches)
			
			}
		});
		th.start();
		//Log.i("�����߳�11","�����߳�");
	}
	public void readXML(int tag){
		//��sdcard�ж�ȡXML�ļ�
		File mf = new File(android.os.Environment.getExternalStorageDirectory()+"/tvCache/XMLforImg.xml");
		String fPath = mf.getAbsolutePath();
		File mfile = new File(fPath);
		InputStream in = null;
		//�ж�XML�ļ��Ƿ����
		if(mfile.exists()){
			try {
				in  = new BufferedInputStream(new FileInputStream(fPath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			imgCaches = SAXXmlService.xmlOpera(in);
			
			if(imgCaches == null){
				
				Log.i("MyXMLread","imgCaches is NULL");
			}	
			//Log.i("MyXMLread",String.valueOf(imgCaches.size()));
			if(1 == tag){
				
				for(ImageCache img : imgCaches){
					if(img.getHot() == 1){
	
						 File file=new File(img.getPath());
						 if(file.exists()){
							 
							 readBitmap(img.getPath(),1);
						 }
						 else{
							 //���ͼƬ��Դ�����ڶ�ȡerror.png
							 readBitmap("/sdcard/tvCache/error.png",1);
						 }
						
					}
				
				}
			}
		}else{
			//��ʾ������Ϣ
			readfiles = false;
			warning_info.setVisibility(View.VISIBLE);
		}
	}
	public void readBitmap(String path ,int tag){
		
		/*
		 * ��ȡpath�е�ͼƬ�����������mapCache��������
		 * */
		if(tag == 1)
		{
			Bitmap bmp = BitmapFactory.decodeFile(path);
			mapCache.add(bmp);
		}
		else if (tag == 2)
		{
			
//			Bitmap bmp = BitmapFactory.decodeFile(path);
//			mapCache2.add(bmp);
		}
		
	}
	
	private List<Map<String, Object>> getMsgInfo() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "Mark");
		map.put("info", "How are you?");
		map.put("img", R.drawable.contact);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "Andy");
		map.put("info", "I am Andy.");
		map.put("img", R.drawable.contact4);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "Mr.lin");
		map.put("info", "�����");
		map.put("img", R.drawable.contact2);
		list.add(map);
		
		map = new HashMap<String,Object>();
		map.put("title", "10086");
		map.put("info", "����ֻ���ͣ�������ֵ��");
		map.put("img", R.drawable.contact5);
		list.add(map);
		
		return list;
	}
	@Override
    public void onBackPressed() {
		// ���ﴦ���߼����룬�÷�����������2.0����°��sdk
		// ����������������ø�����Ӧ���ص��¼�
    	getParent().onBackPressed();
    	return;
    }
}
