package com.newco.marketplace.search.spellchecker.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.newco.marketplace.search.spellchecker.ISpellChecker;
import com.newco.marketplace.search.spellchecker.SpellCheckResponseDto;


public class JazzySpellChecker implements ISpellChecker {
	private String serverUrl;
	private URL url;
	HttpClient httpClient = new HttpClient();

	public JazzySpellChecker(String serverUrl) {		
		try {
			
			url = new URL(serverUrl);
			this.serverUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/spell/check/" ;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public SpellCheckResponseDto checkSpell(String word, int requestedsuggestion)  {
		try {
			HttpMethod method = new GetMethod(serverUrl + URLEncoder.encode(word ,"UTF-8"));
			method.setQueryString("?count=" + requestedsuggestion);
			
			System.out.println("URL is:" + url);
			int statusCode = httpClient.executeMethod(method);
			System.out.println("statusCode: " + statusCode);
			InputStream rstream = null;
			BufferedReader br = null;

			if(statusCode == 200) {
				rstream = method.getResponseBodyAsStream();
				SpellCheckResponseDto dto  = getSuggestions(rstream);
				System.out.println("");
				return dto;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


	private SpellCheckResponseDto getSuggestions(InputStream is) {
			ArrayList<String> suggestions = new ArrayList<String> ();
			SpellCheckResponseDto dto = new SpellCheckResponseDto(false);
			try {
			   DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			   Document doc = builder.parse(is);

			   NodeList nodes = doc.getElementsByTagName("spelling");
		       Element element = (Element) nodes.item(0);
		       
		       Node correctAttributeNode = element.getAttributeNode("correct");
		       boolean flag = Boolean.valueOf(correctAttributeNode.getNodeValue());
		       dto.setCorrect(flag);
		       if(!flag) {
			       NodeList suggestionList = element.getElementsByTagName("word");
			       for (int j=0; j<suggestionList.getLength(); j++) {
			    	   Element line = (Element) suggestionList.item(j);
			    	   suggestions.add(getCharacterDataFromElement(line));
			    	   System.out.println("suggestions : " + getCharacterDataFromElement(line));
			       }
		       }
			}
			catch (Exception e) {
				System.out.println("Error in reading xml response from SpellChecking Service: " + e.getMessage());
			}
			dto.setSuggestions(suggestions);
			return dto;
	}


	private String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

}
