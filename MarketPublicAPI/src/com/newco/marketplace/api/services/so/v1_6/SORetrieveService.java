/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 23-May-2014	KMSTRSUP   Infosys				2.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveRequest;
import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.so.retrieve.v1_5.RetrieveServiceOrder;
import com.newco.marketplace.api.beans.so.retrieve.v1_5.SOGetResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_5.SORetrieveMapperV1_5;
import com.newco.marketplace.api.utils.mappers.so.v1_6.SORetrieveMapperV1_6;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;

@APIRequestClass(SORetrieveRequest.class)
@APIResponseClass(SOGetResponse.class)
public class SORetrieveService extends BaseService {

	private XStreamUtility conversionUtility;
	private Logger logger = Logger.getLogger(SORetrieveService.class);
	private IServiceOrderBO serviceOrderBO;
	private SecurityProcess securityProcess;
	private SORetrieveMapperV1_6 retrieveMapper;

	
	public SORetrieveService() {
		super(null,
				PublicAPIConstant.SOGETRESPONSE_XSD_V1_5,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_5,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_5,
				SORetrieveRequest.class,SOGetResponse.class);
	}

	/**
	 * Implement the  logic .
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");

		ServiceOrder serviceOrder = null;
		List<RetrieveServiceOrder> soResponses = new ArrayList<RetrieveServiceOrder>();
		SOGetResponse soGetResponse = new SOGetResponse();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		try {
			// Tokenize the SO IDs
			String soIds = (String) apiVO.getProperty(PublicAPIConstant.SO_ID_LIST);

			List<String> soIdList = new ArrayList<String>();
			StringTokenizer soIdStr = new StringTokenizer(soIds, PublicAPIConstant.SO_SEPERATOR);
			while (soIdStr.hasMoreTokens()) {
				String soId = soIdStr.nextToken();				
				if(!soIdList.contains(soId)){
					soIdList.add(soId);
				}
			}
			logger.info("SO ID LIST:::::::" + soIdList.toString());
			logger.info("SO ID LIST SIZE:::::::" + soIdList.size());
			
			// Get the response filters
			String responseFilter = requestMap.get(PublicAPIConstant.retrieveSO.RESPONSE_FILTER);
			List<String> responseFilters = new ArrayList<String>();
			if (!StringUtils.isBlank(responseFilter)) {
				StringTokenizer strTok = new StringTokenizer(responseFilter, PublicAPIConstant.SEPARATOR,false);
				int noOfProfileIds = strTok.countTokens();
				for (int i = 1; i <= noOfProfileIds; i++) {
					String filter = new String(strTok.nextToken());
					if(!responseFilters.contains(filter)
							&& PublicAPIConstant.filters().containsKey(filter)){
							responseFilters.add(filter);
						}
					}
			}
			
			// Check if all the response filters are present				
			boolean fullResponse = true; 
			if(StringUtils.isBlank(responseFilter)
					|| (null != responseFilters && responseFilters.size()< PublicAPIConstant.filters().size())
					){
				fullResponse = false;
			}
			int listSize = soIdList.size();
			int buyerId = apiVO.getBuyerIdInteger();
			SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
			for (int index = 0; index < listSize; index++) {
				RetrieveServiceOrder response = new RetrieveServiceOrder();
				if(fullResponse){
					serviceOrder = serviceOrderBO.getServiceOrderForAPI(soIdList.get(index));
				}else{
					// Get whatever is required.
					serviceOrder = serviceOrderBO.getServiceOrder(soIdList.get(index),responseFilters);
				}
				if ((null != serviceOrder)
						&& (serviceOrder.getBuyer().getBuyerId().intValue() != securityContext
								.getCompanyId().intValue())) {

					Results results = Results.getError(
							ResultsCode.NOT_AUTHORISED_BUYER_ID.getMessage()+":"+soIdList.get(index),
							ResultsCode.FAILURE.getCode());
					response.setResults(results);

				} else if (null == serviceOrder) {
					Results results = Results.getError(
							ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST
									.getMessage()+":"+soIdList.get(index), ResultsCode.FAILURE
									.getCode());
					response.setResults(results);
				} else {

					List<ServiceOrderNote> serviceOrderNoteList = serviceOrder.getSoNotes();
					if(null!=serviceOrderNoteList && !serviceOrderNoteList.isEmpty()){
						List<ServiceOrderNote> updatedServciceOrderNoteList = new ArrayList<ServiceOrderNote>();
						for (ServiceOrderNote serviceOrderNote : serviceOrderNoteList) {
							if (((1==serviceOrderNote.getPrivateId())&&
									(3==serviceOrderNote.getRoleId()||
											5==serviceOrderNote.getRoleId()))||
											(0==serviceOrderNote.getPrivateId()))
								updatedServciceOrderNoteList.add(serviceOrderNote);
						}
						serviceOrder.setSoNotes(updatedServciceOrderNoteList);
					}
					response = retrieveMapper.adaptRequest(serviceOrder,responseFilters);
					/**SL-19206:Setting Accepted Firm Details in Response*/
					if(responseFilters.contains(PublicAPIConstant.ROUTEDPROVIDERS)){
						response = retrieveMapper.mapAcceptedFirmDetails(response,serviceOrder);
					}
					if(responseFilters.contains(PublicAPIConstant.ESTIMATE)){
						logger.info("Mapper ::: going to mapp estimate details ");
						response = retrieveMapper.mapEstimateDetails(response,serviceOrder);
					}
					if(responseFilters.contains(PublicAPIConstant.REVIEW)){
						logger.info("Mapper ::: going to mapp review details ");
						response = retrieveMapper.mapReviewDetails(response,serviceOrder.getReview());
					}
					
					if(responseFilters.contains(PublicAPIConstant.ADDONS)){
						logger.info("Mapper ::: going to mapp review details ");
						response = retrieveMapper.mapUpsellInfo(response,serviceOrder.getUpsellInfo());
					}					
					if(responseFilters.contains(PublicAPIConstant.SCOPEOFWORK)){
						logger.info("Mapper ::: going to map scopeofwork with sku details ");						
						response = retrieveMapper.mapScopeOfWorkWithSku(response,serviceOrder);
					}
				}
				soResponses.add(response);
			}
			soGetResponse = retrieveMapper.mapResponse(soResponses);
			
		} catch (Exception e) {
			logger.error("Exception-->" + e.getMessage(), e);

		}

		logger.info("Exiting execute method");
		return soGetResponse;
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

	public SORetrieveMapperV1_6 getRetrieveMapper() {
		return retrieveMapper;
	}

	public void setRetrieveMapper(SORetrieveMapperV1_6 retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}
}