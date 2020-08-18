package com.newco.marketplace.api.mobile.services.v3_0;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.vo.CounterOfferVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.core.BusinessServiceException;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOScheduleDate;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


@APIRequestClass(CounterOfferRequest.class)
@APIResponseClass(CounterOfferResponse.class)
public class CounterOfferService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(CounterOfferService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	private OFHelper ofHelper;
	private OFMapper ofMapper;
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.api.mobile.service.BaseService#execute(com.newco.marketplace.api.common.APIRequestVO)
	 */
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		CounterOfferResponse response = new CounterOfferResponse();
		final CounterOfferRequest request = (CounterOfferRequest) apiVO.getRequestFromPostPut();
		ProcessResponse pResponse = new ProcessResponse();
		
		SecurityContext securityContext = null;
		
		String soId = (String) apiVO.getSOId();
		//final String groupId = (String) apiVO.getSOId();
		
		int roleId = apiVO.getRoleId();
		final String firmId = apiVO.getProviderId();
		final Integer  providerResourceId = apiVO.getProviderResourceId();
		
		Results results = null;
		//get the securityContext
		try {
			securityContext = getSecurityContextForVendorAdmin(Integer.valueOf(firmId));
			
			
		} catch (NumberFormatException numEx) {
			LOGGER
					.error("CounterOfferService.execute(): Number Format Exception "
							+ "occurred for providerResourceId: " + providerResourceId, numEx);
		} catch (Exception ex) {
			LOGGER
					.error("CounterOfferService.execute(): Exception occurred while "
							+ "accessing security context using providerResourceId" + providerResourceId, ex);
		}
		
		ServiceOrder so = null;
		
		try{
			so = mobileGenericBO.getServiceOrder(soId);
			// Request to VO mapper
			final CounterOfferVO counterOfferVO=mobileGenericMapper.mapCounterOfferRequest(request,soId,so,providerResourceId, firmId/*, groupInd, groupId*/,roleId);
			//counterOfferValidation
			response = mobileGenericValidator.validateCounterOffer(counterOfferVO, response);
			
			results = response.getResults();
			response.setSoId(soId);
			//validation is true
			if(null!= results && null != results.getError() && results.getError().size()>0){
				response.setResults(results);
			}else{
				pResponse = createCounterOffer(counterOfferVO,securityContext);
				response = mobileGenericMapper.mapCounterOfferResponse(response, pResponse);
			}
			
		}catch (Exception ex) {
			LOGGER.info("Severe Exception occured while creating a counter offer for soId:"+soId);
			LOGGER.info(ex.getMessage());
			results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		
		return response;
	}
	
	/**
	 * R14.0 BO method for invoking the counterOffer OF signal
	 * Grouped order, call signal CREATE_GROUP_CONDITIONAL_OFFER, 
	 * which internally calls the CREATE_CONDITIONAL_OFFER, corresponding to each SO in the group
	 * Non grouped order, call signal CREATE_CONDITIONAL_OFFER
	 * 
	 * @param CounterOfferVO
	 * @param SecurityContext
	 * @return ProcessResponse
	 */
	public ProcessResponse createCounterOffer(final CounterOfferVO offerVO, final SecurityContext securityContext) throws BusinessServiceException{

		OrderFulfillmentResponse ofResponse = null;
		//create mapper object for OF
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvidersList = createConditionalOfferMapper(offerVO);
		SOElementCollection soeCollection = new SOElementCollection();
		soeCollection.addAllElements(routedProvidersList);
		 Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);

		/*if(PublicAPIConstant.GROUPED_SO_IND.equals(offerVO.getGroupInd())){
			LOGGER.info("Grouped order OF flow CREATE_GROUP_CONDITIONAL_OFFER starts...");
			ofResponse = ofHelper.runOrderFulfillmentGroupProcess(offerVO.getGroupId(), SignalType.CREATE_GROUP_CONDITIONAL_OFFER, soeCollection, identification);
			LOGGER.info("Grouped order OF flow CREATE_GROUP_CONDITIONAL_OFFER ends...");
		}else{*/
			LOGGER.info("Single service order OF flow CREATE_CONDITIONAL_OFFER starts...");
			ofResponse = ofHelper.runOrderFulfillmentProcess(offerVO.getSoId(), SignalType.CREATE_CONDITIONAL_OFFER, soeCollection, identification);
			LOGGER.info("Single service order OF flow CREATE_CONDITIONAL_OFFER ends...");
		//}

		if(ofResponse == null){
			throw new BusinessServiceException("OF response is null...");
		}
		return OFMapper.mapProcessResponse(ofResponse);
	}
	
	/**
	 * @param offerVO
	 * @return
	 */
	private List<com.servicelive.orderfulfillment.domain.RoutedProvider> createConditionalOfferMapper(CounterOfferVO offerVO) {
		LOGGER.info("Start -- CounterOfferService.createConditionalOffer()");
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvList = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
		
		List<Integer> resourceIdList = new ArrayList<Integer>();
		if(null!=offerVO.getResourceIds() 
				&& null != offerVO.getResourceIds().getResourceId() 
				&& offerVO.getResourceIds().getResourceId().size()>0){
			resourceIdList = offerVO.getResourceIds().getResourceId();
		for(Integer resourceId : resourceIdList){
			//setting RoutedProvider object for each resource
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
			routedProvider.setProviderResourceId((long)resourceId);
			routedProvider.setVendorId(Integer.parseInt(offerVO.getFirmId()));
			routedProvider.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);
			routedProvider.setOfferExpirationDate(offerVO.getConditionalExpirationDate());
					
			if(null != offerVO.getServiceDate1()){
				routedProvider.setSchedule(new SOScheduleDate());
				routedProvider.getSchedule().setServiceDate1(offerVO.getServiceDate1());
				routedProvider.getSchedule().setServiceTimeStart(offerVO.getServiceTimeStart());
				//should not be setting end date without start date
				if(null != offerVO.getServiceDate2()){
					routedProvider.getSchedule().setServiceDate2(offerVO.getServiceDate2());
					routedProvider.getSchedule().setServiceTimeEnd(offerVO.getServiceTimeEnd());
				}
			}
			
			if (null != offerVO.getServiceDate1() && null != offerVO.getSpendLimit()){
				routedProvider.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
				//setting the increased spend limit
				routedProvider.setIncreaseSpendLimit(new BigDecimal(offerVO.getSpendLimit()));
			}
			else if (null != offerVO.getSpendLimit()){
				routedProvider.setProviderRespReasonId(OrderConstants.SPEND_LIMIT);
				//setting the increased spend limit
				routedProvider.setIncreaseSpendLimit(new BigDecimal(offerVO.getSpendLimit()));
			}
			else if (null != offerVO.getServiceDate1())
				routedProvider.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE);
			
			//setting couter offer reasons 
			List<Integer> reasonList = new ArrayList<Integer>();
			if(null!=offerVO.getReasonCodes() 
					&& null != offerVO.getReasonCodes().getReasonCode()
					&& offerVO.getReasonCodes().getReasonCode().size()>0){
				reasonList = offerVO.getReasonCodes().getReasonCode();
			for(Integer reason : reasonList){
				SOCounterOfferReason counterOfferReason = new SOCounterOfferReason();
				counterOfferReason.setResponseReasonId((long)reason);
				counterOfferReason.setProviderResponse(routedProvider.getProviderResponse());
				counterOfferReason.setProviderResponseReasonId(routedProvider.getProviderRespReasonId());
				counterOfferReason.setRoutedProvider(routedProvider);
				routedProvider.getCounterOffers().add(counterOfferReason);
			}
			routedProvList.add(routedProvider);
		}
	}
		LOGGER.info("End -- CounterOfferService.createConditionalOffer()");
		}
		return routedProvList;
		
	}

	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}

	public void setMobileGenericValidator(
			final MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(final IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(final MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public OFMapper getOfMapper() {
		return ofMapper;
	}

	public void setOfMapper(OFMapper ofMapper) {
		this.ofMapper = ofMapper;
	}
	

}
