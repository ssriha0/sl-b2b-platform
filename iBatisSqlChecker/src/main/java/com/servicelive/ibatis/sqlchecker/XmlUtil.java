package com.servicelive.ibatis.sqlchecker;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author svanloon
 *
 */
public class XmlUtil {

	/**
	 * This method takes a Document and changes it to a String.
	 * 
	 * @param doc
	 * @return String
	 * @throws TransformerException
	 */
	public String convertW3CDocumentToString(org.w3c.dom.Document doc) throws TransformerException {
		if(doc == null) {
			return "";
		}

		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		String xml = writer.toString();
		return xml;
	}

	public Document createDocument(URL url) throws IOException, ParserConfigurationException, SAXException {
		System.out.println(url.getPath());
		return createDocument(url.openStream());
	}

	/**
	 * This method takes an inputStream and converts it to a Document.  It's very useful for reading in the request.getInputStream().
	 * 
	 * @param is
	 * @return Document
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public Document createDocument(InputStream is) throws IOException, ParserConfigurationException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		return doc;
	}
	
	/**
	 * This method evaluates an XPath Expression (e.g. ///widgets/widget) and then returns the result as a NodeList
	 * 
	 * @param pDoc Is the dom document that you are working with.
	 * @param xPathExpression This is an xPath expression that should evaluate to a NodeList
	 * @return NodeList is the results of the xPath expression on the document.
	 * @throws XPathExpressionException 
	 */
	public NodeList evaluateXPathAsNodeList(Document pDoc, String xPathExpression) throws XPathExpressionException {
		if(xPathExpression == null || xPathExpression.trim().equals("")) {
			return null;
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(xPathExpression);
		NodeList nodes = (NodeList) expr.evaluate(pDoc, XPathConstants.NODESET);
		return nodes;
	}

	/**
	 * This method evaluates an XPath Expression (e.g. //widgets/widget) and then returns the result as a Node
	 * 
	 * @param pDoc Is the dom document that you are working with.
	 * @param xPathExpression This is an xPath expression that should evaluate to a Node
	 * @return Node is the results of the xPath expression on the document.
	 * @throws XPathExpressionException 
	 */
	public Node evaluateXPathAsNode(Document pDoc, String xPathExpression) throws XPathExpressionException {
		if(xPathExpression == null || xPathExpression.trim().equals("")) {
			return null;
		}

		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile(xPathExpression);
		Node node = (Node) expr.evaluate(pDoc, XPathConstants.NODE);
		return node;
	}
}
