/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.offer.AcceptCounterOfferRequest;
import com.newco.marketplace.api.beans.so.offer.CounterOfferResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOAcceptOfferMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.provider.IResourceBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for executing the Accept Service Order request
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOAcceptOfferService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOAcceptOfferService.class);
	private IServiceOrderBO serviceOrderBO;
	private SOAcceptOfferMapper acceptOfferMapper;
	private IResourceBO resourceBO;
	private IProviderSearchBO provSearchObj;

	/**
	 * Constructor
	 */

	public SOAcceptOfferService() {
		super(PublicAPIConstant.ACCEPT_OFFER_XSD,
				PublicAPIConstant.COUNTER_OFFER_RESPONSE_XSD,
				PublicAPIConstant.COUNTER_OFFER_NAMESPACE,
				PublicAPIConstant.DOCUMENT_SCHEMAS,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				AcceptCounterOfferRequest.class, CounterOfferResponse.class);
		
	}

	@Override
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		logger.info("Entering SOAcceptOfferService.execute()");
		Integer buyerId =apiVO.getBuyerIdInteger();
		AcceptCounterOfferRequest request = (AcceptCounterOfferRequest)apiVO.getRequestFromPostPut();
		Results results = null;
		SecurityContext securityContext = null;
		CounterOfferResponse offerResponse = new CounterOfferResponse();
		try {
			securityContext = super.getSecurityContextForBuyerAdmin(buyerId);
		} catch(NumberFormatException nme){
			logger.error("SOAcceptOfferService.execute(): Number Format Exception " +
					 "occurred for Buyer Id: " + buyerId, nme);
		}
		catch (Exception exception) {
			logger.error("SOAcceptOfferService.execute():  Exception occurred while " +
					 "accessing security context with Buyer Id: " + buyerId, exception);
		}
		
		logger.info("Getting routedProviderList for the service order");
		String soId = apiVO.getSOId();
		Integer providerResourceId = Integer.parseInt(request.getProviderResource());
		List<RoutedProvider> routedProviderList = serviceOrderBO.getAllProviders
		( apiVO.getSOId(), null);
		for (RoutedProvider routedProvider : routedProviderList) {
			if (providerResourceId.equals(routedProvider.getResourceId())) {
				logger.info("routedProvider.getProviderRespId() = " + routedProvider.getProviderRespId());
				if (OrderConstants.CONDITIONAL_OFFER.equals(routedProvider
						.getProviderRespId())) {
					ProcessResponse processResponse = serviceOrderBO.acceptConditionalOffer(
							soId,
							routedProvider.getResourceId(),
							routedProvider.getVendorId(),
							routedProvider.getProviderRespReasonId(),
							routedProvider.getConditionalChangeDate1(),
							routedProvider.getConditionalChangeDate2(),
							routedProvider.getConditionalStartTime(),
							routedProvider.getConditionalEndTime(),
							routedProvider.getConditionalSpendLimit(),
							securityContext.getCompanyId(), 
							true,
							securityContext);
					try {
						offerResponse = acceptOfferMapper.mapSOAcceptOfferMapper(processResponse);
						break;
					} catch (DataException ex) {
						logger.error("SOAcceptOfferService.execute(): DataException occured while mapping accept offer: " + ex.getMessage(), ex);


						results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
						offerResponse.setResults(results);
						return offerResponse;
					}
				}
			}
		}
		
		if (null == offerResponse.getResults()) {
			logger.error("SOAcceptOfferService.execute(): No matching records found (offerResponse.getResults() returned null)");
			
			results = Results.getError(ResultsCode.COUNTER_OFFER_ERROR.getMessage(),
									   ResultsCode.COUNTER_OFFER_ERROR.getCode());
			offerResponse.setResults(results);
		}
		logger.info("Leaving SOAcceptOfferService.execute()");
		return offerResponse;
	}


	public void setAcceptOfferMapper(SOAcceptOfferMapper acceptOfferMapper) {
		this.acceptOfferMapper = acceptOfferMapper;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

    @Override
    protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
        com.servicelive.orderfulfillment.domain.ServiceOrder so = new com.servicelive.orderfulfillment.domain.ServiceOrder();
		AcceptCounterOfferRequest request = (AcceptCounterOfferRequest)apiVO.getRequestFromPostPut();
        Long acceptedProviderId = new Long(request.getProviderResource());
		
		VendorResource vendor;
		try {
			vendor = provSearchObj.getVendorFromResourceId(acceptedProviderId.intValue());
		} catch (BusinessServiceException bse) {
        	logger.error("Error happened while getting the Vendor Id ", bse);
        	return getErrorResponse();
		}

        so.setAcceptedProviderId(vendor.getVendorId().longValue());
        so.setAcceptedProviderResourceId(acceptedProviderId);
        so.setSoTermsCondId(null);
        so.setProviderSOTermsCondInd(1);
        so.setProviderTermsCondDate(Calendar.getInstance().getTime());
        SecurityContext securityContext = getSecCtxtForBuyerAdmin(apiVO.getBuyerIdInteger());
        Contact vendorContact;
        try {
            vendorContact = resourceBO.getVendorResourceContact(acceptedProviderId.intValue());
        } catch (BusinessServiceException bse) {
        	logger.error("Error happened while getting the provider state ", bse);
        	return getErrorResponse();
        }
        SOContact contact = acceptOfferMapper.mapContact(vendorContact);
        so.addContact(contact);
        SOLocation soLocation = acceptOfferMapper.mapLocation(vendorContact);
        so.addLocation(soLocation);
        
        OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, vendorContact.getStateCd());
        ofRequest.setElement(so);
        ofRequest.setIdentification(OFMapper.createBuyerIdentFromSecCtx(securityContext));
        if(securityContext.getRoleId().intValue() != 5){
	        try {
	        	Integer providerResourceId = Integer.parseInt(request.getProviderResource());        
				ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(apiVO.getSOId());	
				List<RoutedProvider> routedProviderList = serviceOrderBO.getAllProviders( apiVO.getSOId(), null);
				for (RoutedProvider routedProvider : routedProviderList) {
					if (providerResourceId.equals(routedProvider.getResourceId())) {
						logger.info("routedProvider.getProviderRespId() = " + routedProvider.getProviderRespId());
						if (OrderConstants.CONDITIONAL_OFFER.equals(routedProvider
								.getProviderRespId())) {
							serviceOrder.setIncreaseSpendLimit(routedProvider.getConditionalSpendLimit());	
						}
					}
				}
				Double  buyerMaxTransactionLimit   = serviceOrderBO.getBuyerMaxTransactionLimit(securityContext.getVendBuyerResId(),apiVO.getBuyerIdInteger());
				securityContext.setMaxSpendLimitPerSO(buyerMaxTransactionLimit.doubleValue());
				if (isOrderPriceAboveMaxTransactionLimit(serviceOrder,securityContext, false)){
					Results results = Results.getError(ResultsCode.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR.getMessage(), ResultsCode.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR.getCode());
					CounterOfferResponse offerResponse = new CounterOfferResponse();
			    	offerResponse.setResults(results);
			    	return offerResponse;			
				}
			
	        }catch (Exception e) {
				logger.error("SOAcceptOfferService.execute(): Exception occurred while " +
						 "accessing max transaction limit with Buyer id: " , e);
			}
        }
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(apiVO.getSOId(), SignalType.ACCEPT_CONDITIONAL_OFFER, ofRequest);
        return acceptOfferMapper.mapSOAcceptOfferMapper(response);
    }

    private CounterOfferResponse getErrorResponse(){
    	Results results = Results.getError(ResultsCode.COUNTER_OFFER_ERROR.getMessage(),
				   ResultsCode.COUNTER_OFFER_ERROR.getCode());
    	CounterOfferResponse offerResponse = new CounterOfferResponse();
    	offerResponse.setResults(results);
    	return offerResponse;
    }
    
	public void setResourceBO(IResourceBO bo) {
		this.resourceBO = bo;
	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

}
