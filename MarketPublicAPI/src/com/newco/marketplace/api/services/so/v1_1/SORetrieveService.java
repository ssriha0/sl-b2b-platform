/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Apr-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.retrieve.SOGetResponse;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SORetrieveMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;


/**
 * This class would act as a Servicer class for Retrieve Service Order
 * 
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(SORetrieveRequest.class)
@APIResponseClass(SOGetResponse.class)
public class SORetrieveService extends BaseService {

	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SORetrieveService.class);
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;
	private SORetrieveMapper retrieveMapper;

	/**
	 * This method is for creating buyer account.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */
	public SORetrieveService() {
		super(PublicAPIConstant.REQUEST_XSD_v1_1,
				PublicAPIConstant.SOGETRESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				SORetrieveRequest.class, SOGetResponse.class);
	}

	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		SOGetResponse response = new SOGetResponse();
		ServiceOrder serviceOrder = null;
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		try {
			boolean fullResponse = true; 
			// Get the response filters
			String responseFilter = requestMap.get(PublicAPIConstant.retrieveSO.RESPONSE_FILTER);
			List<String> responseFilters = new ArrayList<String>();
			if (!StringUtils.isBlank(responseFilter)) {
				StringTokenizer strTok = new StringTokenizer(responseFilter,PublicAPIConstant.SEPARATOR, false);
				int noOfProfileIds = strTok.countTokens();
				for (int i = 1; i <= noOfProfileIds; i++) {
					String filter = new String(strTok.nextToken());
					// See if the filter is valid and already present
					if(!responseFilters.contains(filter)
							&& PublicAPIConstant.filters().containsKey(filter)){
							responseFilters.add(filter);
						}
				}
			}else{
				fullResponse = false;
			}				
			if(null != responseFilters && responseFilters.size()< PublicAPIConstant.filters().size()){
				fullResponse = false;
			}	
			String soId = apiVO.getSOId();
			if(fullResponse){
				serviceOrder = serviceOrderBO.getServiceOrderForAPI(soId);
			}else{
				// Get whatever is required.
				serviceOrder = serviceOrderBO.getServiceOrder(soId,responseFilters);
			}
			int buyerId = apiVO.getBuyerIdInteger();
			SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);	
			if (null == serviceOrder) {
				Results results = Results.getError(ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST.getMessage()+":"+
						soId, ResultsCode.FAILURE.getCode());
				response.setResults(results);
			}else if ((serviceOrder.getBuyer().getBuyerId().intValue() != securityContext
					.getCompanyId().intValue())) {
				Results results = Results.getError(ResultsCode.NOT_AUTHORISED_BUYER_ID
						.getMessage(), ResultsCode.FAILURE.getCode());
				response.setResults(results);
			}else {
				response = retrieveMapper.adaptRequest(serviceOrder,responseFilters,apiVO.getLocale());
			}
		} catch (Exception e) {
			logger.error("Exception-->" + e.getMessage(), e);
		}
		logger.info("Exiting execute method");
		return response;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public SecurityProcess getSecurityProcess() {
		return securityProcess;
	}

	public SORetrieveMapper getRetrieveMapper() {
		return retrieveMapper;
	}

	public void setRetrieveMapper(SORetrieveMapper retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}

}