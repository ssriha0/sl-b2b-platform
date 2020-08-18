package com.servicelive.ibatis.sqlchecker;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.servicelive.ibatis.sqlchecker.XmlUtil;

public class XmlUtilTest {
	private Logger logger = Logger.getLogger(XmlUtilTest.class);
	private XmlUtil xmlUtil;
	
	@Test
	public void convertDocumentToString(){
		xmlUtil = new XmlUtil();
		String documentAsString = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try{
		   DocumentBuilder builder = dbf.newDocumentBuilder();
		   Document doc = builder.newDocument();
		   Element element = doc.createElement("root");
		   doc.appendChild(element);
		   Comment comment = doc.createComment("This is a comment");
		   doc.insertBefore(comment, element);
		   Element itemElement = doc.createElement("item");
		   element.appendChild(itemElement);
		   itemElement.setAttribute("myattr", "attrvalue");
		   itemElement.insertBefore(doc.createTextNode("text"), itemElement.getLastChild());
		   documentAsString = xmlUtil.convertW3CDocumentToString(doc);
           Assert.assertNotNull("Document is Null",documentAsString);
	    }catch (Exception e) {
			logger.error("Exception in converting document to string:"+ e.getMessage());
		}
	}

}
