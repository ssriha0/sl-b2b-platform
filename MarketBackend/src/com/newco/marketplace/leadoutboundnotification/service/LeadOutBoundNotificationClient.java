package com.newco.marketplace.leadoutboundnotification.service;

import java.util.HashMap;
import java.util.Map;


import com.newco.marketplace.leadoutboundnotification.vo.ExceptionCodesEnum;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetails;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetailsError;
import com.newco.marketplace.leadoutboundnotification.beans.ResponseLeadDetailsVerazip;
import com.newco.marketplace.leadoutboundnotification.constatns.LeadOutBoundConstants;
import com.newco.marketplace.leadoutboundnotification.vo.LeadResponseVO;
import com.servicelive.SimpleRestClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class LeadOutBoundNotificationClient {
	
	private ILeadOutBoundNotificationService leadOutBoundNotificationService;
	private String leadOutBoundBaseUrl;
	private String leadOutBoundHeader;
	
	public LeadResponseVO createResponseFromNPS(String request) throws Exception{
		
		String header = leadOutBoundHeader;
		Map<String,String> parameterMap = null;
		if(null!= header && header.trim().length()!=0){
			parameterMap  = getHttpHeaders(header, request);
		}
		
		SimpleRestClient simpleRestClient = new SimpleRestClient(leadOutBoundBaseUrl,null,null,false);
		LeadResponseVO leadResponseVO = simpleRestClient.sPost(parameterMap);
		if(null != leadResponseVO){
		
			/* Check if the response code contains any of the following
			 *
			 * 401 Unauthorized 
			 * 403 Forbidden
			 * 404 Not Found
			 * 405 Method Not Allowed
			 * 406 Not Acceptable
			 * 407 Proxy Authentication Required - similar to 401
			 * 408 Request Timeout
			 * 500 Internal Server Error
			 * 502 Bad Gateway
			 * 503 Service Unavailable
			 * 504 Gateway Timeout
			 * 505 HTTP Version Not Supported
			 * */
		
			if(ExceptionCodesEnum.containsValue(leadResponseVO.getStatusCode())){
				// Throw custom exception ?			
				throw new Exception(leadResponseVO.getStatusCode()+LeadOutBoundConstants.SEQ_HYP+ leadResponseVO.getStatusText());
			}
		}
		return leadResponseVO;
	}
	
	public Object deserializeSuccessResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        ResponseLeadDetails response = (ResponseLeadDetails) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	public Object deserializeErrorResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        ResponseLeadDetailsError response = (ResponseLeadDetailsError) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	public Object deserializeVerazipResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        ResponseLeadDetailsVerazip response = (ResponseLeadDetailsVerazip) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	private Map<String,String> getHttpHeaders(String parameters, String request){
		HashMap<String,String> map = new HashMap<String,String>();
		String[] splittedValue= parameters.split(",");
		
		for(int count = 0;count< splittedValue.length;count++){
			String value = splittedValue[count];
			String[] againSplitted= value.split("::");
			map.put(againSplitted[0], againSplitted[1]);
		}
		request = request.replaceAll("&", "&amp;");
		map.put("XML", request);
		return map;
	}
	
	public ILeadOutBoundNotificationService getLeadOutBoundNotificationService() {
		return leadOutBoundNotificationService;
	}
	public void setLeadOutBoundNotificationService(
			ILeadOutBoundNotificationService leadOutBoundNotificationService) {
		this.leadOutBoundNotificationService = leadOutBoundNotificationService;
	}

	public String getLeadOutBoundBaseUrl() {
		return leadOutBoundBaseUrl;
	}

	public void setLeadOutBoundBaseUrl(String leadOutBoundBaseUrl) {
		this.leadOutBoundBaseUrl = leadOutBoundBaseUrl;
	}

	public String getLeadOutBoundHeader() {
		return leadOutBoundHeader;
	}

	public void setLeadOutBoundHeader(String leadOutBoundHeader) {
		this.leadOutBoundHeader = leadOutBoundHeader;
	}
	
}
