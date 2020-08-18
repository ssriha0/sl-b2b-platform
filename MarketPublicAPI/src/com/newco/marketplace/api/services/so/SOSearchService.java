/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-May-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.search.SOSearchRequest;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.mappers.so.SOSearchMapper;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.SearchRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;

/**
 * This class is a service class for searching Service Orders
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOSearchService {
	private Logger logger = Logger.getLogger(SOSearchService.class);
	private XStreamUtility conversionUtility;
	private SOSearchMapper searchMapper;
	private SecurityProcess securityProcess;
	private IServiceOrderBO serviceOrderBO;

	/**
	 * This method is for searching service orders.
	 * 
	 * @param searchOrderRequest
	 *            String
	 * @throws Exception
	 *             Exception
	 * @return String
	 */
	public String dispatchSearchServiceRequest(String soSearchRequest)
			throws Exception {
		logger.info("Inside dispatchSearchServiceRequest--->Start");
		String searchResponseXml = null;
		SOSearchRequest searchRequest = new SOSearchRequest();
		searchRequest = conversionUtility
				.getSearchRequestObject(soSearchRequest);
		SecurityContext securityContext = securityProcess.getSecurityContext(
				searchRequest.getIdentification().getUsername(), searchRequest
						.getIdentification().getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new BusinessServiceException("SecurityContext is null");

		} else {
			SearchRequestVO searchRequestVO = new SearchRequestVO();
			logger.info("Calling SOSearchMapper for mapping request object");
			searchRequestVO = searchMapper.mapServiceOrder(searchRequest,
					securityContext);
			List<ServiceOrder> serviceOrderSearchResults = new ArrayList<ServiceOrder>();
			logger.info("Calling ServiceOrderBO for SO Search");
			serviceOrderSearchResults = serviceOrderBO
					.getSearchResultSet(searchRequestVO);
			SOSearchResponse soSearchResponse = new SOSearchResponse();
			logger
					.info("Calling SOSearchMapper for mapping Search results to Response object");
			soSearchResponse = searchMapper
					.mapResponseSearch(serviceOrderSearchResults);
			logger
					.info("Calling searchXstreamResponse function in XStreamUtility for converting response "
							+ "object to response xml");
			searchResponseXml = conversionUtility
					.getSearchResponseXML(soSearchResponse);

		}
		return searchResponseXml;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setSearchMapper(SOSearchMapper searchMapper) {
		this.searchMapper = searchMapper;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
}