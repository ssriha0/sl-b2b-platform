package com.newco.marketplace.api.mobile.services.clientv2_0;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.inhomeoutboundnotification.vo.InhomeResponseVO;
import com.newco.marketplace.leadoutboundnotification.vo.ExceptionCodesEnum;
import com.servicelive.SimpleRestClient;

public class LPNClient {
	private static final Logger logger = Logger.getLogger(LPNClient.class);	
	
	public InhomeResponseVO createResponseFromLPN(String request, String url, String header) throws Exception{
		logger.info("createResponseFromLPN: Begin");
		Map<String,String> parameterMap = null;
		// construct parameter map from header.
		if(StringUtils.isNotBlank(header)){
			parameterMap  = getHttpHeaders(header);
		}
		
		SimpleRestClient simpleRestClient = new SimpleRestClient(url,null,null,false);
		// Calling postMethod in SimpleRestClient to call the web service.
		InhomeResponseVO inHomeResponseVO = simpleRestClient.postMethodLpn(request, parameterMap);
		
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
		logger.info("createResponseFromLPN: End");
		return inHomeResponseVO;
		
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
