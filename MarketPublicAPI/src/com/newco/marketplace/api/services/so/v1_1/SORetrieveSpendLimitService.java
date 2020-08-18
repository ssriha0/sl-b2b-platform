/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 10-Jul-2012	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.retrieve.SOSpendLimitHistory;
import com.newco.marketplace.api.beans.so.retrieve.SpendLimitHistoryResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SORetrieveMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSpendLimitHistoryVO;

/**
 * This class would act as a Servicer class for Retrieve 
 * spendLimitIncreaseData for a SO.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SORetrieveSpendLimitService  extends BaseService{
       private Logger logger = Logger.getLogger(SORetrieveSpendLimitService.class);
       private IServiceOrderBO serviceOrderBO;
       private SORetrieveMapper retrieveMapper;
      
	 public SORetrieveSpendLimitService(){
		  super(null, 
				PublicAPIConstant.SOGETSPENDLIMITRESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_SPENDLIMIT_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				null, SpendLimitHistoryResponse.class);
	}
	 
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		SpendLimitHistoryResponse soSpendLimitResponse=new SpendLimitHistoryResponse();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		try{
			int buyerId = apiVO.getBuyerIdInteger();
			SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
			// Tokenizing the SO IDs
			String soIds = (String) apiVO.getProperty(PublicAPIConstant.SO_ID_LIST);
			List<String> soIdList = new ArrayList<String>();
			StringTokenizer soIdStr = new StringTokenizer(soIds, PublicAPIConstant.SO_SEPERATOR);
			while (soIdStr.hasMoreTokens()) {
				String soId = soIdStr.nextToken();				
				if(!soIdList.contains(soId)){
					soIdList.add(soId);
				}
			}
			// Get the response
			String responseForSpendLimit = requestMap
					.get(PublicAPIConstant.retrieveSOForSpendLimit.RESPONSE_FILTER);			
			List<SOSpendLimitHistory> soSpendLimitHistoryList = new ArrayList<SOSpendLimitHistory>();
			int soCount=0;
			DecimalFormat priceFormatter = new DecimalFormat("#0.00");
			Results invalidResponse=new Results();
			if(StringUtils.isBlank(responseForSpendLimit)){
				 responseForSpendLimit=PublicAPIConstant.retrieveSOForSpendLimit.THIN_RESPONSE;
			}
			if (StringUtils.isNotEmpty(responseForSpendLimit)) {
				if (responseForSpendLimit
						.equals(PublicAPIConstant.retrieveSOForSpendLimit.THIN_RESPONSE)
						|| responseForSpendLimit
								.equals(PublicAPIConstant.retrieveSOForSpendLimit.VERBOSE_RESPONSE)) {
				            HashMap <String,ServiceOrderSpendLimitHistoryVO> spendLimitIncreaseMap =new HashMap<String,ServiceOrderSpendLimitHistoryVO>();
				            spendLimitIncreaseMap = serviceOrderBO.getSpendLimitForAPI(soIdList);
							for (String soId : soIdList) {
									SOSpendLimitHistory soSpendLimitHistory=new SOSpendLimitHistory();
									
								    ServiceOrderSpendLimitHistoryVO voHistory=spendLimitIncreaseMap.get(soId);
						               if(null == voHistory){
						            	   invalidResponse = Results.getError(
						           				ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST
						           						.getMessage()+":"+soId, ResultsCode.FAILURE
						           						.getCode()); 
						            	   soSpendLimitHistory.setResults(invalidResponse);
						            	   }
						               else if(null!=voHistory.getBuyerId() && !voHistory.getBuyerId().equals(securityContext.getCompanyId())){
						            	   invalidResponse = Results.getError(
						           				ResultsCode.NOT_AUTHORISED_BUYER_ID.getMessage()+":"+soId,
						           				ResultsCode.FAILURE.getCode());
						            	   soSpendLimitHistory.setResults(invalidResponse);
						            	   
						            	   
						               }else if (null == voHistory.getHistoryVOlist() || voHistory.getHistoryVOlist().size() == 0) {
						            	   invalidResponse = Results.getError(ResultsCode.SO_SPENDLIMIT_NOT_AVAILABLE.getMessage(),
						           				ResultsCode.FAILURE.getCode());
						            	   if(null != voHistory.getSpendLimitLabor() && null != voHistory.getSpendLimitParts()){
						            	         Double currentPrice=voHistory.getSpendLimitLabor()+voHistory.getSpendLimitParts();
						            	         String currentPriceAfterFormatting=priceFormatter.format(currentPrice);
						            	         soSpendLimitHistory.setCurrentPrice(currentPriceAfterFormatting);
						            	          }
						            	soSpendLimitHistory.setSoId(soId);
						            	soSpendLimitHistory.setResults(invalidResponse);
						    			 } else{
						            	   soSpendLimitHistory.setSoId(soId);
						            	   soCount++;
						            	   retrieveMapper.getResponseForSOSpendLimitHistory(spendLimitIncreaseMap,soSpendLimitHistory,responseForSpendLimit);
						               }
								    
								    
								    soSpendLimitHistoryList.add(soSpendLimitHistory);
								    
								   }
							 soSpendLimitResponse=retrieveMapper.mapResponseForSpendLimit(soSpendLimitHistoryList,soCount);
							}else{
			         invalidResponse= Results.getError(
			                             ResultsCode.SO_SPENDLIMIT_INVALID_SPENDLIMIT_RESPONSE.getMessage(),
			                             ResultsCode.FAILURE.getCode());
                      soSpendLimitResponse.setResults(invalidResponse);
                      return soSpendLimitResponse;
				
				}
			}
		}
		catch (Exception e) {
				logger.error("Exception-->" + e.getMessage(), e);
		 }
		logger.info("Exiting execute method for spendLimit");
		return soSpendLimitResponse;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}
	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	public SORetrieveMapper getRetrieveMapper() {
		return retrieveMapper;
	}
	public void setRetrieveMapper(SORetrieveMapper retrieveMapper) {
		this.retrieveMapper = retrieveMapper;
	}
}
