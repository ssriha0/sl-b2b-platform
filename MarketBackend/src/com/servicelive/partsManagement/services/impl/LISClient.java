package com.servicelive.partsManagement.services.impl;


import java.net.SocketTimeoutException;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.PartDetailsDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/08/09
 * This class is created for order management Parts Management
 * 
 */
public class LISClient {

	private static final Logger LOGGER = Logger.getLogger(LISClient.class.getName());			
	/**
	 * method to get the LIS part details when passing the url
	 * @param url
	 * @param consumerKey
	 * @param partNum
	 * @return
	 */
	public PartDetailsDTO searchPartDetails(String url,String consumerKey,String partNum){
		LOGGER.info("Entering searchPartDetails in LISClient for part: "+partNum);
		PartDetailsDTO detailsDTO = new PartDetailsDTO();
		WebResource resource;
		MultivaluedMap<String,String> parameters = new MultivaluedMapImpl();
		Client client = Client.create();
		String lisResponse =null;
		try {
			//method to invoke the client to hit the API
			//String lisResponse  = getLisResponse(url,consumerKey,partNum);

			client.setConnectTimeout(30000);
			client.setReadTimeout(30000);
			resource = client.resource(url);
			// parameters.add("apikey",consumerKey);
			parameters.add("partNo",partNum);
			lisResponse =resource.queryParams(parameters).header(MPConstants.LIS_API_KEY,consumerKey).accept("application/xml").get(String.class);
			LOGGER.info("Response for part num:"+partNum);
			LOGGER.info(lisResponse);
			//method will deserialize the string response to PartDetailsDTO
			detailsDTO =(PartDetailsDTO)deserializeLisResponse(lisResponse,PartDetailsDTO.class);
		}
		catch(ClientHandlerException ce){
			LOGGER.error("error in LISClient:"+ce.getMessage());
			if (ce.getCause() instanceof SocketTimeoutException) {
				detailsDTO.setErrorMessage(MPConstants.LIS_TIMEOUT_ERROR);
			}
			detailsDTO.setErrorMessage(MPConstants.LIS_ERROR_RETURN);
			LOGGER.error("Destroying the client after exception while searching part:"+partNum);
			client.destroy();
			ce.printStackTrace();
			//throw ce;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in search part details method in LISClient"+e.getMessage());
			detailsDTO.setErrorMessage(MPConstants.LIS_ERROR_RETURN);
			LOGGER.error("Destroying the client after exception while searching part:"+partNum);
			client.destroy();
			e.printStackTrace();
		}				
		return detailsDTO;
	}
	
	public String searchPartDetailsString(String url,String consumerKey,String partNum, String acceptType,
			String modelNum,String modelId){
		LOGGER.info("Entering searchPartDetails in LISClient for part: "+partNum);
		PartDetailsDTO detailsDTO = new PartDetailsDTO();
		WebResource resource;
		MultivaluedMap<String,String> parameters = new MultivaluedMapImpl();
		Client client = Client.create();
		String lisResponse =null;
		try {
			//method to invoke the client to hit the API
			//String lisResponse  = getLisResponse(url,consumerKey,partNum);

			client.setConnectTimeout(30000);
			client.setReadTimeout(30000);
			resource = client.resource(url);
			// parameters.add("apikey",consumerKey);
			
			if(null!=partNum){
				parameters.add("partNo",partNum);
			}
			
			if(null!=modelNum){
				parameters.add("modelNo",modelNum);
			}
			
			if(null!=modelId){
				parameters.add("modelId",modelId);
			}
			
			lisResponse =resource.queryParams(parameters).header(MPConstants.LIS_API_KEY,consumerKey).accept(acceptType).get(String.class);
			// method will deserialize the string response to PartDetailsDTO
			// detailsDTO =(PartDetailsDTO)deserializeLisResponse(lisResponse,PartDetailsDTO.class);
		}
		catch(ClientHandlerException ce){
			LOGGER.error("error in LISClient:"+ce.getMessage());
			if (ce.getCause() instanceof SocketTimeoutException) {
				detailsDTO.setErrorMessage(MPConstants.LIS_TIMEOUT_ERROR);
			}
			detailsDTO.setErrorMessage(MPConstants.LIS_ERROR_RETURN);
			LOGGER.error("Destroying the client after exception while searching part:"+partNum);
			client.destroy();
			ce.printStackTrace();
			//throw ce;
		}
		catch (Exception e) {
			LOGGER.error("Error occured in search part details method in LISClient"+e.getMessage());
			detailsDTO.setErrorMessage(MPConstants.LIS_ERROR_RETURN);
			LOGGER.error("Destroying the client after exception while searching part:"+partNum);
			client.destroy();
			e.printStackTrace();
		}				
		return lisResponse;
	}

	private String getLisResponse(String url,String consumerKey,String partNum)throws ClientHandlerException , Exception {
		LOGGER.info("Entering getLisResponse of LISClient for part:"+partNum);
		WebResource resource;
		MultivaluedMap<String,String> parameters = new MultivaluedMapImpl();

		String response =null;
		Client client = Client.create();
		//client.setConnectTimeout(5000);
		resource = client.resource(url);
		parameters.add("apikey",consumerKey);
		parameters.add("partNo",partNum);
		response =resource.queryParams(parameters).accept("application/xml").get(String.class);
		return response;
	}

	private Object deserializeLisResponse(String responseXml, Class<?> classz){
		XStream xstreamResponse = new XStream(new DomDriver());
		xstreamResponse.processAnnotations(classz);
		PartDetailsDTO response = (PartDetailsDTO) xstreamResponse.fromXML(responseXml);
		return response;
	}
}


