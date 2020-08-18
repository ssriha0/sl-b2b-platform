package com.newco.marketplace.api.mobile.services.v3_0;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.vo.CounterOfferVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/22
 * Service class for fetching response 0f WithdrawCounterOffer
 */
@APIRequestClass(WithdrawCounterOfferRequest.class)
@APIResponseClass(WithdrawCounterOfferResponse.class)
public class WithdrawCounterOfferService  extends BaseService{
	
	private static final Logger LOGGER = Logger.getLogger(WithdrawCounterOfferService.class);
	private MobileGenericValidator mobileGenericValidator;
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	private OFHelper ofHelper;
	
	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		WithdrawCounterOfferResponse response = new WithdrawCounterOfferResponse();
		final WithdrawCounterOfferRequest request = (WithdrawCounterOfferRequest) apiVO.getRequestFromPostPut();
		ProcessResponse pResponse = new ProcessResponse();
		
		String soId = (String) apiVO.getSOId();
		//final String firmId = apiVO.getProviderId();
		//final Integer  providerResourceId = apiVO.getProviderResourceId();
		
		Results results = null;
		
		ServiceOrder so = null;
		
		try{
			so = mobileGenericBO.getServiceOrder(soId);
			// Request to VO mappers
			final CounterOfferVO withdrawCounterOfferVO = mobileGenericMapper.mapWithdrawCounterOfferRequest(apiVO,request,so);
			//counterOfferValidation
			response = mobileGenericValidator.validateWithdrawCounterOffer(withdrawCounterOfferVO, response);
			
			results = response.getResults();
			response.setSoId(withdrawCounterOfferVO.getSoId());
			//validation is true
			if(null!= results && null != results.getError() && results.getError().size()>0){
				response.setResults(results);
			}else{
				pResponse = withdrawCounterOffer(withdrawCounterOfferVO);
				response = mobileGenericMapper.mapCounterOfferResponse(response, pResponse);
			}
			
		}catch (Exception ex) {
			LOGGER.info("Severe Exception occured while creating a counter offer for soId:"+soId);
			ex.printStackTrace();
			LOGGER.info(ex.getMessage());
			results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		return response;
	}

	/**
	 * R14.0 BO method for invoking the withdrawCounterOffer OF signal
	 * Grouped order, call signal WITHDRAW_GROUP_CONDITIONAL_OFFER_RESOURCE_LIST,
	 * which internally calls the WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST, corresponding to each SO in the group
	 * Non grouped order, call signal WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST
	 * 
	 * @param CounterOfferVO
	 * @param SecurityContext
	 * @return ProcessResponse
	 */
	public ProcessResponse withdrawCounterOffer(final CounterOfferVO offerVO) throws BusinessServiceException{

		
		OrderFulfillmentResponse ofResponse = null;
		SecurityContext securityContext = null;
		//get the securityContext
		try {
			securityContext = getSecurityContextForVendorAdmin(Integer.valueOf(offerVO.getFirmId()));
		} catch (NumberFormatException numEx) {
			LOGGER.error("CounterOfferService.execute(): Number Format Exception occurred for providerResourceId: " + offerVO.getProviderResourceId(), numEx);
		} catch (Exception ex) {
			LOGGER.error("CounterOfferService.execute(): Exception occurred while accessing security context using providerResourceId" + offerVO.getProviderResourceId(), ex);
		}
				
		//create mapper object for OF
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvidersList = mobileGenericMapper.withdrawConditionalOfferMapper(offerVO);
		SOElementCollection soeCollection = new SOElementCollection();
		soeCollection.addAllElements(routedProvidersList);
		Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);

		LOGGER.info("Single service order OF flow WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST starts...");
		ofResponse = ofHelper.runOrderFulfillmentProcess(offerVO.getSoId(), SignalType.WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST, soeCollection, identification);
		LOGGER.info("Single service order OF flow WITHDRAW_CONDITIONAL_OFFER_RESOURCE_LIST ends...");

		if(ofResponse == null){
			throw new BusinessServiceException("OF response is null...");
		}
		return OFMapper.mapProcessResponse(ofResponse);
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

}
