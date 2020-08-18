package com.newco.marketplace.inhomeoutboundnotification.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateResponse;
import com.newco.marketplace.inhomeoutboundnotification.beans.SendMessageResponse;
import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.leadoutboundnotification.vo.ExceptionCodesEnum;
import com.servicelive.SimpleRestClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class InHomeOutBoundNotificationClient {
	
	private static final Logger LOGGER = Logger.getLogger(InHomeOutBoundNotificationClient.class.getName());	
	
	public InhomeResponseVO createResponseFromNPS(String request, String url, String header) throws Exception{
		
		LOGGER.info("InHomeOutBoundNotificationClient.createResponseFromNPS:Begin");
		Map<String,String> parameterMap = null;
		// construct parameter map from header.
		if(StringUtils.isNotBlank(header)){
			parameterMap  = getHttpHeaders(header);
		}
		
		SimpleRestClient simpleRestClient = new SimpleRestClient(url,null,null,false);
		// Calling postMethod in SimpleRestClient to call the web service.
		InhomeResponseVO inHomeResponseVO = simpleRestClient.postMethod(request, parameterMap);
		
		if(null != inHomeResponseVO){
		
			/* Check if the response code contains any of the following
			 *
			 * 400 Bad Request
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
		
			if(ExceptionCodesEnum.containsValue(inHomeResponseVO.getStatusCode())){
				// Throw custom exception ?			
				throw new Exception(inHomeResponseVO.getStatusCode()+InHomeNPSConstants.SEQ_HYP+ inHomeResponseVO.getStatusText());
			}
		}
		LOGGER.info("InHomeOutBoundNotificationClient.createResponseFromNPS:End");
		return inHomeResponseVO;
	}
	
	// Mapping the response xml to SendMessageResponse class.
	public Object deserializeSendMessageResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        SendMessageResponse response = (SendMessageResponse) xstreamResponse.fromXML(responseXml);
        return response;
    } 
	
	// Mapping the response xml to OrderUpdateResponse class.
	public Object deserializeOrderUpdateResponse(String responseXml, Class<?> classz){
        XStream xstreamResponse = new XStream(new DomDriver());
        xstreamResponse.processAnnotations(classz);
        OrderUpdateResponse response = (OrderUpdateResponse) xstreamResponse.fromXML(responseXml);
        return response;
    } 
		
	//construct parameter map
	private Map<String,String> getHttpHeaders(String parameters){
		HashMap<String,String> map = new HashMap<String,String>();
		String[] splittedValue= parameters.split(",");
		
		for(int count = 0;count< splittedValue.length;count++){
			String value = splittedValue[count];
			String[] againSplitted = value.split(":");
			map.put(againSplitted[0], againSplitted[1]);
		}
		return map;
	}
	
}
