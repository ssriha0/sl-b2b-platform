package com.servicelive.service.dataTokenization;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




public class HSAuthServiceClient {
	private static final Logger LOGGER = Logger.getLogger(HSAuthServiceClient.class);	
	

	/**Method to invoke HS authorization service by simple rest client
	 * createResponseFromClient.
	 * 
	 * @param request
	 * @param url
	 * @param header
	 * 
	 * @return HomeServiceResponseVO
	 * 
	 * @throws Exception 
	 */
	public HomeServiceResponseVO createResponseFromClient(String request, String url, String header) throws Exception{
		LOGGER.info("createResponseFromClient: Begin");
		HomeServiceResponseVO homeServiceResponseVO = null;
		Map<String,String> parameterMap = null;
		// construct parameter map from header.
		if(StringUtils.isNotBlank(header)){
			parameterMap  = getHttpHeaders(header);
		}
		SimpleRestClient simpleRestClient = new SimpleRestClient(url,null,null,false);
		try{
		    // Calling postMethod in SimpleRestClient to call the web service.
		    homeServiceResponseVO = simpleRestClient.postMethod(request, parameterMap);
			if(null != homeServiceResponseVO){
			   LOGGER.info("homeServiceResponseVO ResponseXML:"+ homeServiceResponseVO.getResponseXml());
			}else{
				throw new Exception("Exception in invoking Home Service Web service from the Client");
			}
		}catch (Exception e) {
			LOGGER.error("Exception in invoking Home Service Web service from the Client"+ e);
			throw new Exception(e);
		}
		LOGGER.info("createResponseFromClient: End");
		return homeServiceResponseVO;
		
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
