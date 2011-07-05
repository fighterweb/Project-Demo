package com.shawben.sui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/*
 * ����XML�ļ���ҵ����*/
public class SAXXmlService {

	
	public static List<ImageCache> xmlOpera(InputStream instream){
		//�½���������
		SAXParserFactory saxparseFac = SAXParserFactory.newInstance();
		try{
			//�½�������
			SAXParser saxPar =saxparseFac.newSAXParser();
			//�½�xml�ļ�������
			XMLContentHandler xmlHandler = new XMLContentHandler();
			
			saxPar.parse(instream, xmlHandler);
			return xmlHandler.getImageCache();
		}catch (ParserConfigurationException e){
			
			e.printStackTrace();
			return null;

		}catch(SAXException e) {
			e.printStackTrace();
			return null;

		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	} 
}
