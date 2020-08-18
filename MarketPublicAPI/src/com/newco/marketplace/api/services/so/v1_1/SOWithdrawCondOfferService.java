/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 10-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.withdrawOffer.WithdrawCondOfferResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.ordermanagement.v1_1.OrderManagementMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for executing the Create Conditional Offer request
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOWithdrawCondOfferService extends BaseService {
	private Logger logger = Logger
			.getLogger(SOWithdrawCondOfferService.class);
	private IServiceOrderBO serviceOrderBo;
	private IOrderGroupBO orderGrpBO;
	private OrderManagementMapper omMapper ;
	private OFHelper ofHelper = new OFHelper();

	/**
	 * Constructor
	 */

	public SOWithdrawCondOfferService() {
		super(null,
				PublicAPIConstant.WITHDRAW_OFFER_PRO_RESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.OM_RESOURCES_SCHEMAS_V1_0,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				null,WithdrawCondOfferResponse.class);
	}

	/**
	 * This method withdraws Conditional Offer
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering SOWithdrawCondOfferService.execute()");
		ProcessResponse processResponse = null;
		SecurityContext securityContext = null;
		Results results = null;
		boolean isInside = false;
		
		WithdrawCondOfferResponse offerResponse = new WithdrawCondOfferResponse();
		Map<String, String> reqMap = apiVO.getRequestFromGetDelete();
		String groupInd = "0";
		if(null != reqMap && null != reqMap.get(PublicAPIConstant.GROUP_IND_PARAM)){
			groupInd = reqMap.get(PublicAPIConstant.GROUP_IND_PARAM);
		}
		String firmId = apiVO.getProviderId();	
		String soId = apiVO.getSOId();
		String groupId = apiVO.getSOId();
		Integer resourceId = apiVO.getProviderResourceId();
		Integer providerRespId = 2;
		
		try {
			securityContext = getSecurityContextForVendorAdmin(new Integer(firmId));			
			
		} catch (NumberFormatException nme) {
			logger.error("SOWithdrawCondOfferService.execute(): Number Format Exception "
							+ "occurred for providerResourceId: " + resourceId, nme);
		} catch (Exception exception) {
			logger.error("SOWithdrawCondOfferService.execute(): Exception occurred while "
							+ "accessing security context using providerResourceId" + resourceId, exception);
		}
		
		List<ServiceOrderSearchResultsVO> serviceOrders = null;
		ServiceOrder so = null;
		try {
			//check whether grouped order
			if(PublicAPIConstant.GROUPED_SO_IND.equals(groupInd)){
				serviceOrders = orderGrpBO.getServiceOrdersForGroup(groupId);
				soId = serviceOrders.get(0).getSoId();
			}
			so = serviceOrderBo.getServiceOrder(soId);
			
		} catch (Exception ex) {
			results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			offerResponse.setResults(results);
			return offerResponse;
		}
		//validations
		//whether so is in posted state
		if (so.getWfStateId() == OrderConstants.ROUTED_STATUS) {
			List<RoutedProvider> routedProviderList = so.getRoutedResources();
			//whether so is posted to the resource
			for (RoutedProvider routedProvider : routedProviderList) {
				if (resourceId.intValue() == routedProvider.getResourceId().intValue()) {				
						try{
							//check whether a counter offer is placed by the resource for this so
							if(null == routedProvider.getProviderRespId() || 2 != routedProvider.getProviderRespId()){
								results = Results.getError(ResultsCode.SO_WITHDRAW_NO_COUNTER_OFFER.getMessage(),
														   ResultsCode.SO_WITHDRAW_NO_COUNTER_OFFER.getCode());
								offerResponse.setResults(results);
								return offerResponse;
							}
							//for grouped orders
							if(PublicAPIConstant.GROUPED_SO_IND.equals(groupInd)){
								if(ofHelper.isNewGroup(groupId)){
								    com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
								    ofRoutedProvider.setProviderResourceId((long)resourceId);
								    Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);
								    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.WITHDRAW_GROUP_CONDITIONAL_OFFER, ofRoutedProvider, identification);
								    return mapOFResponse(response);
								}
								processResponse = orderGrpBO.withdrawGroupedConditionalAcceptance(groupId, resourceId, 
																					providerRespId, securityContext);
							}
							else{
								//withdraw offer for OF orders
								if(ofHelper.isNewSo(soId)){
									//creating RoutedProvider object for resourceId
									com.servicelive.orderfulfillment.domain.RoutedProvider ofRoutedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
									ofRoutedProvider.setProviderResourceId((long)resourceId);
									Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);
									OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.WITHDRAW_CONDITIONAL_OFFER, ofRoutedProvider, identification);
									return mapOFResponse(response);
								}
								//for legacy orders
								processResponse = serviceOrderBo.withdrawConditionalAcceptance(soId, resourceId, 
																				providerRespId, securityContext);
							}
							//map response to offerResponse
							offerResponse = omMapper.mapSOWithdrawCondOfferResponse(processResponse);
							isInside=true;
							break;
						} 
						catch (Exception ex) {
							logger.error("SOWithdrawCondOfferService.execute(): DataException occured"
									+ " while withdraw conditional  offer: "+ ex.getMessage(), ex);
							results = Results.getError(ex.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
							offerResponse.setResults(results);
							return offerResponse;
						}
				}
			}
			if(!isInside){
				results = Results.getError(ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH.getMessage(),
										   ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH.getCode());
				offerResponse.setResults(results);
			}
		}
		else {
			results = Results.getError(ResultsCode.SO_WITHDRAW_OFFER_STATUS_NOTMATCH.getMessage(),
									   ResultsCode.SO_WITHDRAW_OFFER_STATUS_NOTMATCH.getCode());
			offerResponse.setResults(results);
		}
		return offerResponse;
	}
	
	private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
		WithdrawCondOfferResponse withdrawResponse = new WithdrawCondOfferResponse();
		Results results = new Results();
		if (response.getErrors().isEmpty()) {
			results = Results.getSuccess(ResultsCode.WITHDRAW_OFFER_SUBMITTED.getMessage());
		} else {
			results = Results.getError(ResultsCode.WITHDRAW_OFFER_ERROR.getMessage(), ResultsCode.WITHDRAW_OFFER_ERROR.getCode());
		}
		withdrawResponse.setResults(results);
		return withdrawResponse;
    }

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public OrderManagementMapper getOmMapper() {
		return omMapper;
	}

	public void setOmMapper(OrderManagementMapper omMapper) {
		this.omMapper = omMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IOrderGroupBO getOrderGrpBO() {
		return orderGrpBO;
	}

	public void setOrderGrpBO(IOrderGroupBO orderGrpBO) {
		this.orderGrpBO = orderGrpBO;
	}

	
}
