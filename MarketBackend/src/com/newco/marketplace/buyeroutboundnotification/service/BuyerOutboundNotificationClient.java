package com.newco.marketplace.buyeroutboundnotification.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.buyerOutBoundNotification.BuyerOutBoundNotificationServiceImpl;
import com.newco.marketplace.buyeroutboundnotification.beans.ResponseMsgBody;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.vo.ExceptionCodesEnum;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestVO;
import com.newco.marketplace.buyeroutboundnotification.vo.ResponseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BuyerOutboundNotificationClient {
	
	// Create the logger
	private static final Logger LOGGER = Logger.getLogger(BuyerOutBoundNotificationServiceImpl.class.getName());
	private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;

	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}

	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}
	
	/**
	 * Split the header parameters and get the map
	 * @param parameters
	 * @return
	 */
	private Map<String,String> getHttpHeaders(String parameters){
		HashMap<String,String> map = new HashMap<String,String>();
		String[] splittedValue= parameters.split(",");
		
		for(int count = 0;count< splittedValue.length;count++){
			String value = splittedValue[count];
			String[] againSplitted= value.split(":");
			map.put(againSplitted[0], againSplitted[1]);
		}
		return map;
	}

	/**
	 * Call API and get the response
	 * @param msgBody
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ResponseVO createResponseFromNPS(String request)throws Exception{
		RequestVO requestVO = new RequestVO();
		try {
			
			// Get the API details from the database table
			requestVO = buyerOutBoundNotificationService.getWebServiceDetails(BuyerOutBoundConstants.SEARS_RI_BUYER_ID.intValue());
			 
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String path=requestVO.getUrl();
		Map<String,String> parameterMap = null;
		if(null!= requestVO.getHttpHeaders() && requestVO.getHttpHeaders().trim().length()!=0){
			parameterMap  = getHttpHeaders(requestVO.getHttpHeaders());
		}
		
		int statusCode = 0;
		String statusValue = null;
		String responseFromNPS = null;
		
        RequestEntity requestEntity = new StringRequestEntity(request, "application/xml", null); 
        HttpClient httpclient = new HttpClient();
        
        // POST
		if(BuyerOutBoundConstants.POST.equalsIgnoreCase(requestVO.getMethod())){
			PostMethod postMethod = new PostMethod(path);
			postMethod.setRequestEntity(requestEntity);
			postMethod.setContentChunked(true);
			
			// Set the HTTP header parameters
			if(null!=parameterMap){
				Iterator iterator = parameterMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry pairs = (Map.Entry)iterator.next();
					postMethod.setRequestHeader((String)pairs.getKey(), (String)pairs.getValue());
				}
			}
			httpclient.executeMethod(postMethod);
			responseFromNPS = postMethod.getResponseBodyAsString(); 
			statusCode = postMethod.getStatusCode();
			statusValue = postMethod.getStatusText();
		}
		// PUT
		else if(BuyerOutBoundConstants.PUT.equalsIgnoreCase(requestVO.getMethod())){
			PutMethod putMethod = new PutMethod(path);
			putMethod.setRequestEntity(requestEntity);
		    putMethod.setContentChunked(true);   
		    
		    // Set the HTTP header parameters
			if(null!=parameterMap){
				Iterator iterator = parameterMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry pairs = (Map.Entry)iterator.next();
					putMethod.setRequestHeader((String)pairs.getKey(), (String)pairs.getValue());
				}
			}
			
        	httpclient.executeMethod(putMethod);
            responseFromNPS = putMethod.getResponseBodyAsString(); 
            statusCode = putMethod.getStatusCode();
            statusValue = putMethod.getStatusText();
		}else if(BuyerOutBoundConstants.GET.equalsIgnoreCase(requestVO.getMethod())){
			// This is just for testing GET
			GetMethod getMethod = new GetMethod(path);
			if(null!=parameterMap){
				Iterator iterator = parameterMap.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry pairs = (Map.Entry)iterator.next();
					getMethod.setRequestHeader((String)pairs.getKey(), (String)pairs.getValue());
				}
			}
			httpclient.executeMethod(getMethod);
		    responseFromNPS = getMethod.getResponseBodyAsString();
		    statusCode = getMethod.getStatusCode();
		    statusValue = getMethod.getStatusText();
		}
		
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
		
		if(ExceptionCodesEnum.containsValue(statusCode)){
			// Throw custom exception ?			
			throw new Exception(statusCode+BuyerOutBoundConstants.SEQ_HYP+ statusValue);
		}
		
		
		// MOCKING UP THE RESPONSE FROM NPS. TODO Remove this before deployment
		// responseFromNPS = buyerOutBoundNotificationService.getMockedUpData("NPS_Response_XML");
		// responseFromNPS = responseFromNPS + buyerOutBoundNotificationService.getMockedUpData("NPS_Response_XML_1");
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setResponseXml(responseFromNPS);
		responseVO.setStatusCode(statusCode);
		responseVO.setStatusText(statusValue);
        return responseVO;
	}
	private String serializeRequest(Object request, Class<?> classz){
        XStream xstreamRequest = new XStream(new DomDriver());
        xstreamRequest.processAnnotations(classz);
        String requestXml  = xstreamRequest.toXML(request).toString();
        return requestXml;
       
} 
	public Object deserializeResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        ResponseMsgBody response = (ResponseMsgBody) xstreamResponse.fromXML(responseXml);
        return response;
       
} 
}
