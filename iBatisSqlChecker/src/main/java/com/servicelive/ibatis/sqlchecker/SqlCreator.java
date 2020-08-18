package com.servicelive.ibatis.sqlchecker;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SqlCreator {

	private static final XmlUtil xmlUtil = new XmlUtil();

	public List<Query> findCompleteSelect(Document doc) throws XPathExpressionException {
		List<Query> queries = new ArrayList<Query>();
		String xPathExpression = "//select";
		NodeList nodeList = xmlUtil.evaluateXPathAsNodeList(doc, xPathExpression);
		for(int i = 0; i < nodeList.getLength(); i++ ) {
			Node node = nodeList.item(i);
			String id = "not set";
			if(node instanceof Element) {
				Element elem = (Element) node;
				id = elem.getAttribute("id");
			}
			String sql = processNode(node, doc, false);
			Query query = new Query();
			query.setSql(sql);
			query.setId(id);
			queries.add(query);
		}
		return queries;
	}

	private String processChildNodes(NodeList nodeList, Document doc) throws XPathExpressionException {
		StringBuilder sb = new StringBuilder();
		for(int j = 0; j < nodeList.getLength(); j++) {
			Node node = nodeList.item(j);
			String sqlFragment = processNode(node, doc, sb.toString().trim().length() > 0);
			sb.append(sqlFragment);
		}
		return sb.toString();
	}

	private String processNode(Node parentNode, Document doc, boolean prepend) throws XPathExpressionException {
		String nodeName = parentNode.getNodeName();
		int nodeType = parentNode.getNodeType();

		if(nodeType == 1) {
			if(nodeName.equals("select")) {
				return processChildNodes(parentNode.getChildNodes(), doc);
			}

			if( nodeName.equals("include")) {
				if(parentNode instanceof Element) {
					Element e = (Element) parentNode;
					String refid = e.getAttribute("refid");
					Node node = xmlUtil.evaluateXPathAsNode(doc, "//sql[@id='" + refid + "']");
					if(node == null) {
						int i = refid.indexOf("."); 
						if(i > -1) {
							refid = refid.substring(i + 1);
						}
						node = xmlUtil.evaluateXPathAsNode(doc, "//sql[@id='" + refid + "']");
					}
					if(node == null) {
						throw new RuntimeException("Couldn't find refid = " + refid);						
					}
					return processChildNodes(node.getChildNodes(), doc);
				}
				throw new RuntimeException("Can't process file's include");
			}

			if(nodeName.equals("isNotNull")
				|| nodeName.equals("isNull")
				|| nodeName.equals("isParameterPresent")
				|| nodeName.equals("iterate")
				|| nodeName.equals("isNotEmpty")
				|| nodeName.equals("isGreaterThan")
				|| nodeName.equals("isPropertyAvailable")
				|| nodeName.equals("isEqual")
				|| nodeName.equals("dynamic")
				|| nodeName.equals("isLessEqual")
				|| nodeName.equals("isNotEqual")
				|| nodeName.equals("isEmpty")
				|| nodeName.equals("isNotPropertyAvailable")
			) {

				StringBuilder sb = new StringBuilder();

				String prependValue = null;
				String openValue = null;
				String closeValue = null;

				if(parentNode instanceof Element) {
					Element e = (Element) parentNode;
					prependValue = e.getAttribute("prepend");
					openValue = e.getAttribute("open");
					closeValue = e.getAttribute("close");
				}

				if(prepend) {
					if(prependValue != null && !prependValue.trim().equals("")) {
						sb.append(prependValue);
						sb.append(" ");
					}
				}

				if(openValue != null && !openValue.trim().equals("")) {
					sb.append(openValue);
				}
				
				sb.append(processChildNodes(parentNode.getChildNodes(), doc));

				if(closeValue != null && !closeValue.trim().equals("")) {
					sb.append(closeValue);
				}

				return sb.toString();
			}
		}

		if(nodeType == 4 && nodeName.equals("#cdata-section")) {
			String content = parentNode.getTextContent();
			return content;
			/*
			content = content.trim();
			if(content.equals("=")
				|| content.equals("<=")
				|| content.equals(">=")
				|| content.equals(">")
				|| content.equals("<")
				|| content.equals("<>")
			) {
				return content;
			}
			throw new RuntimeException("Couldn't handle cdata = " + content);
			*/
		}

		if(nodeType == 3 && nodeName.equals("#text")) {
			return parentNode.getTextContent();
		}

		if(nodeType == 8 && nodeName.equals("#comment")) {
			return "";
		}

		throw new RuntimeException("nodeName = " +nodeName+ ", nodeType = " + parentNode.getNodeType() + ", content = " + parentNode.getTextContent());
		
	}

}
