package com.service.test;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class XMLContentHandler extends DefaultHandler{

	private ImageCache imgCache;
	private List<ImageCache> imgCaches;
	private String preTag;
	final String TAG = "XML CONTENT HANDLER";
	@Override
	public void characters(char[] ch, int start, int length)throws 
	SAXException {
		// START
		 Log.i(TAG,"解析到文件的元素值"+new String(ch,start,length));
		// END
		if(imgCache!= null)
		{
			// START
			 Log.i(TAG,"imgCache不等于空");
			// END
			if("name".equals(preTag)){
				imgCache.setName(new String(ch,start,length));
			}
			else if ("hot".equals(preTag)){
				imgCache.setHot(new Integer(new String(ch,start,length)));
			}
			else if("category".equals(preTag)){
				imgCache.setCategory(new String(ch,start,length));
			}
			else if("link".equals(preTag)){
				imgCache.setLink(new String(ch,start,length));
			}
			else if("path".equals(preTag)){
				imgCache.setPath(new String(ch,start,length));
			}

		}
		
	}
	@Override
	public void endElement(String uri, String localName, String qName)throws 
	SAXException{
		// START
		 Log.i(TAG,"解析到结束标志："+localName);
		// END
		if("images".equals(localName)&&imgCache != null){
			// START
			 Log.i(TAG,"ADD imgCache");
			// END
			imgCaches.add(imgCache);
			imgCache = null;
			
		}
		preTag = null;
	}
	@Override
	public void startDocument() throws SAXException{
		imgCaches = new ArrayList<ImageCache>();
		// START
		 Log.i(TAG,"开始解析XML文件");
		// END
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws 
	SAXException{
		if("images".equals(localName)){
			
			imgCache = new ImageCache();
			//imgCache.setId(new Integer(attributes.getValue(0)));
		}
		preTag = localName;
		// START
		 Log.i(TAG,"解析到开始标志："+localName);
		// END
	}
	public List<ImageCache> getImageCache(){
		return imgCaches;
	}
}
