package com.newco.marketplace.search.spellchecker.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;

import com.newco.marketplace.search.solr.bo.SkillTreeSearchBO;
import com.newco.marketplace.search.spellchecker.ISpellChecker;
import com.newco.marketplace.search.spellchecker.SpellCheckResponseDto;
import com.servicelive.common.util.BoundedBufferedReader;

public class YahooSpellChecker  implements ISpellChecker{
	private Logger logger = Logger.getLogger(YahooSpellChecker.class);
	private boolean isProxySet;
	private String yahooServiceAppID;
	
	private String yahooServiceURL;
	private HttpClient httpClient;
	private HostConfiguration hostconfig;

	public YahooSpellChecker(String proxyHost,Integer proxyPort, boolean isProxySet, 
			String yahooAppID, Long yahooServiceTimeOut, String yahooServiceURL) {
		this.isProxySet = isProxySet;
		this.yahooServiceAppID = yahooAppID;
		this.yahooServiceURL = yahooServiceURL;
		httpClient = new HttpClient();
		HttpClientParams params = httpClient.getParams();
    	params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, yahooServiceTimeOut);
    	if(isProxySet) {
	    	hostconfig = new HostConfiguration();
			hostconfig.setProxy(proxyHost,proxyPort);
    	}
	}
	/**
	 * This method is responsible for calling yahoo spellchecker service.
	 * @param query
	 * @param requestString
	 * @return
	 * @throws Exception
	 */
	public SpellCheckResponseDto checkSpell(String requestString,  int requestedsuggestion) {
		String collationWord = null;
		int statusCode = 0;
		SpellCheckResponseDto dto = new SpellCheckResponseDto(true);

		GetMethod method = new GetMethod(yahooServiceURL);
		try {
			method.setQueryString(URIUtil.encodeQuery(requestString + "?appid="+yahooServiceAppID));
			if(isProxySet){
				statusCode = httpClient.executeMethod(hostconfig,method);
			} else {
				statusCode = httpClient.executeMethod(method);
			}

			logger.info("yahoo spell check status code: " + statusCode);
			if(statusCode == 200) {
				InputStream rstream = null;
				BoundedBufferedReader br = null;
				String line;
				try {
					rstream = method.getResponseBodyAsStream();
					br = new BoundedBufferedReader(new InputStreamReader(rstream));
					while ((line = br.readLine()) != null) {
						line.trim();
						if(line.contains("suggestion")){
							collationWord = line.substring(line.indexOf(">") + 1, line.indexOf("</"));
							dto.setCorrect(false);
							dto.getSuggestions().add(collationWord);
						}
					}
					logger.info("yahoo spell check: collation word : " +  collationWord);
				} 
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally {
					if(br != null) {
						br.close();
					}
					if(rstream != null) {
						rstream.close();
					}
				}
			} 
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

}
