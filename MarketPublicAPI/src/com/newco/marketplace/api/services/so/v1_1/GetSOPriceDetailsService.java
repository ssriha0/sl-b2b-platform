package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOPriceHistoryMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.common.properties.IApplicationProperties;

public class GetSOPriceDetailsService extends BaseService{
	
	private Logger logger = Logger.getLogger(GetSOPriceDetailsService.class);
	private IServiceOrderBO serviceOrderBO;
    private IApplicationProperties applicationProperties;
	private SOPriceHistoryMapper priceHistoryMapper ;

	public SOPriceHistoryMapper getPriceHistoryMapper() {
		return priceHistoryMapper;
	}

	public void setPriceHistoryMapper(SOPriceHistoryMapper priceHistoryMapper) {
		this.priceHistoryMapper = priceHistoryMapper;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public GetSOPriceDetailsService() {
		super(null,
				PublicAPIConstant.SO_PRICE_HISTORY_XSD,
				PublicAPIConstant.PRICEHISTORY_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.PRICEHISTORY_NAMESPACE_SCHEMALOCATION,
				null, SOPriceHistoryResponse.class);
	}

	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering execute method");
		SOPriceHistoryResponse priceHistoryResponse = new SOPriceHistoryResponse();
		List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
		Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
		String soIds = (String) apiVO.getProperty(PublicAPIConstant.SO_ID_LIST);
		int buyerId = apiVO.getBuyerIdInteger();
		
		SecurityContext securityContext = getSecurityContextForBuyerAdmin(buyerId);
		try{
			//for getting the list of soIds			
			List<String> soIdList = new ArrayList<String>();
			if (!StringUtils.isBlank(soIds)) {
				StringTokenizer strTok = new StringTokenizer(soIds,PublicAPIConstant.SO_SEPERATOR, false);
				int noOfTokens = strTok.countTokens();
				for (int i = 1; i <= noOfTokens; i++) {
					String soId = new String(strTok.nextToken());
					soIdList.add(soId);
				}
			}
			
			
			// Validate the total number of service orders allowed per request
			Integer defaultSoCount =  Integer.parseInt(
					applicationProperties.getPropertyFromDB(MPConstants.DEFAULT_API_SO_LIMIT));
			
			if(null!= defaultSoCount){ // If the database property has been set
				if(defaultSoCount<soIdList.size()){
					Results invalidResponse=new Results();
					//for testing
					invalidResponse = Results.getError(
	        				"Total number of service orders provided in the request exceeds the maximum number of allowed service orders per request.",
	        				ResultsCode.FAILURE.getCode());
					priceHistoryResponse.setResults(invalidResponse);
					return priceHistoryResponse;
				}
			}
			
			//for getting the list of info levels
			String infoLevels = requestMap.get(PublicAPIConstant.INFO_LEVEL);
			Integer originalInfolevel = 0;
			Integer infolevel = 0;
			/*
			 * Info level - There will be maximum one value at a time.
			 * 0 - This is the default value. Only the current price  
			 * 1 - Service order level price history
			 * 2 - Task level price history. This includes the service order level 
			 *     history as well
			 * Anything other than these or no values present - Default value is 0 
			 */
			if(StringUtils.isNotBlank(infoLevels)){
				try{
					infolevel = Integer.parseInt(infoLevels);
				}catch (Exception e) {
					// Parser Exception
					infolevel = 0;
				}
			}
			// If not 1,2 - default to 0
			if(!OrderConstants.CURRENT_PRICE_INFO.equals(infolevel)&&
					!OrderConstants.SO_LEVEL_PRICE_HISTORY.equals(infolevel) && 
					!OrderConstants.TASK_LEVEL_PRICE_HISTORY.equals(infolevel)){
				infolevel = 0;
			}
			
			Map<String,Results> invalidOrders = new HashMap<String, Results>();
			
			//storing the original infoLevel
			originalInfolevel = infolevel;

			for(String soId : soIdList){
				ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
				logger.debug("size of service orders ::"+soId);
				// Assumption : service order object will never be null
				if(null==serviceOrder){ // Service order is not valid
					
					Results invalidResponse=new Results();
					invalidResponse = Results.getError(
							ResultsCode.SERVICE_ORDER_DOES_NOT_EXIST.getMessage()+":"+soId,ResultsCode.FAILURE.getCode());
					invalidOrders.put(soId, invalidResponse);
					serviceOrder = new ServiceOrder();
					serviceOrder.setSoId(soId);
					serviceOrders.add(serviceOrder);
				}else{
					if(null!=serviceOrder.getBuyerId() && !serviceOrder.getBuyerId().equals(securityContext.getCompanyId())){
						Results invalidResponse=new Results();
						invalidResponse = Results.getError(
								ResultsCode.NOT_AUTHORISED_BUYER_ID.getMessage()+":"+soId,ResultsCode.FAILURE.getCode());
						invalidOrders.put(soId, invalidResponse);
						serviceOrders.add(serviceOrder);
					}else{
						// If service order is priced at order level and the
						
						// info level is task level, reset that to order level
						
						if(serviceOrder.getPriceType().equals(OrderConstants.SO_LEVEL_PRICING)
								&& OrderConstants.TASK_LEVEL_PRICE_HISTORY.equals(infolevel)){
							infolevel = OrderConstants.SO_LEVEL_PRICE_HISTORY;
						}
						else{
							infolevel = originalInfolevel;
						}
						serviceOrder = serviceOrderBO.getServiceOrderPriceDetails(serviceOrder, infolevel);
						serviceOrders.add(serviceOrder);
					}
				}
			}
			logger.debug("size of service orders ::"+serviceOrders.size());
			logger.debug("size of invalidOrders  ::"+invalidOrders.size());
			infolevel = originalInfolevel;
			priceHistoryResponse = priceHistoryMapper.mapPriceHistory(serviceOrders, infolevel, invalidOrders);
		}catch (Exception e) {
			logger.error("Exception-->" + e.getMessage(), e);
			priceHistoryResponse = new SOPriceHistoryResponse();
			Results invalidResponse=new Results();
			invalidResponse = Results.getError(
					ResultsCode.GENERIC_ERROR.getMessage(),ResultsCode.FAILURE.getCode());
			priceHistoryResponse.setResults(invalidResponse);
		}
		
		return priceHistoryResponse;
		
	}

	
}
