/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 10-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.api.beans.so.OfferReasonCodes;
import com.newco.marketplace.api.beans.so.offer.CounterOfferResponse;
import com.newco.marketplace.api.beans.so.offer.CreateCounterOfferRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOCreateConditionalOfferMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOScheduleDate;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class is a service class for executing the Create Conditional Offer request
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOCreateConditionalOfferService extends BaseService {
	private Logger logger = Logger
			.getLogger(SOCreateConditionalOfferService.class);
	private IServiceOrderBO serviceOrderBo = null;
	private IOrderGroupBO orderGrpBO = null;
	private SOCreateConditionalOfferMapper conditionalOfferMapper;
	SimpleDateFormat sdfTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	private OFHelper ofHelper;

	/**
	 * Constructor
	 */

	public SOCreateConditionalOfferService() {
		super(PublicAPIConstant.CREATE_PRO_CONDITIONAL_OFFER_XSD,
				PublicAPIConstant.COUNTER_OFFER_PRO_RESPONSE_XSD,
				PublicAPIConstant.COUNTER_OFFER_PRO_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_PRO,
				CreateCounterOfferRequest.class, 
				CounterOfferResponse.class);
		super.addMoreClass(OfferReasonCodes.class);
		super.addMoreClass(CounterOfferResources.class);
	}

	/**
	 * This method dispatches the createConditional Offer
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering SOCreateConditionalOfferService.execute()");
		ProcessResponse processResponse = null;
		CreateCounterOfferRequest createCounterOfferRequest = (CreateCounterOfferRequest) apiVO
				.getRequestFromPostPut();
		String soId = (String) apiVO.getSOId();
		String groupId = (String) apiVO.getSOId();
		Results results = null;
		SecurityContext securityContext = null;
		boolean isError = false;
		boolean isInside = false;
		Timestamp conditionalExpirationDate = null;
		CounterOfferResponse offerResponse = new CounterOfferResponse();
		Integer  providerResourceId = new Integer( createCounterOfferRequest.getIdentification().getId());
		String providerId = apiVO.getProviderId();
		Map<String, Object> reqMap = (Map<String, Object>)apiVO.getProperties().get(PublicAPIConstant.REQUEST_MAP);
		String groupInd = "0";
		if(null != reqMap && null != reqMap.get(PublicAPIConstant.GROUP_IND_PARAM)){
			groupInd = reqMap.get(PublicAPIConstant.GROUP_IND_PARAM).toString();
		}
		try {
			securityContext = getSecurityContextForVendorAdmin(new Integer(providerId));
			
			
		} catch (NumberFormatException nme) {
			logger
					.error("SOCreateConditionalOfferService.execute(): Number Format Exception "
							+ "occurred for providerResourceId: " + providerResourceId, nme);
		} catch (Exception exception) {
			logger
					.error("SOCreateConditionalOfferService.execute(): Exception occurred while "
							+ "accessing security context using providerResourceId" + providerResourceId, exception);
		}
		
			List<ServiceOrderSearchResultsVO> serviceOrders = null;
			ServiceOrder so = null;
			try {
				//check whether grouped order
				if(PublicAPIConstant.GROUPED_SO_IND.equals(groupInd)){
					serviceOrders = orderGrpBO.getServiceOrdersForGroup(groupId);
					soId = serviceOrders.get(0).getSoId();
				}
				//check whether it is a CAR routed SO
				boolean isCARrouted = serviceOrderBo.isCARroutedSO(soId);
				if (isCARrouted) {
					results = Results.getError(ResultsCode.SO_CONDITIONAL_CAR_SO.getMessage(), ResultsCode.SO_CONDITIONAL_CAR_SO.getCode());
					offerResponse.setResults(results);
					isError = true;
				}
				so = serviceOrderBo.getServiceOrder(soId);
				
			} catch (Exception ex) {
				results = Results.getError(ex.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				offerResponse.setResults(results);
				return offerResponse;
			}

			
				if (OrderConstants.ROUTED_STATUS == so.getWfStateId()) {
					
					//Added for SL-19728: Non Funded Buyer
					if(serviceOrderBo.checkNonFunded(soId) && (createCounterOfferRequest.getSpendLimit()!=null))
					{
						
							results = Results
									.getError(
											ResultsCode.SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED
													.getMessage(),
											ResultsCode.SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED
													.getCode());
							offerResponse.setResults(results);
							isError = true;
						
	
						
					}
					else
					{					
					List<RoutedProvider> routedProviderList = so
							.getRoutedResources();

					for (RoutedProvider routedProvider : routedProviderList) {
						
						if (providerResourceId.equals(
										routedProvider.getResourceId())) {
								
								
							if (createCounterOfferRequest.getServiceDateTime1() == null
									&& createCounterOfferRequest.getSpendLimit() == null) {
								results = Results
										.getError(
												ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
														.getMessage(),
												ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
														.getCode());
								offerResponse.setResults(results);
								isError = true;
							}
		
							if ((createCounterOfferRequest.getSpendLimit() != null)
									&& (new Double(createCounterOfferRequest
											.getSpendLimit()) < 0.00)) {
								results = Results
										.getError(
												ResultsCode.SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT
														.getMessage(),
												ResultsCode.SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT
														.getCode());
								offerResponse.setResults(results);
								isError = true;
							} 
		
							if (createCounterOfferRequest.getOfferExpirationDate() == null) {
								results = Results
										.getError(
												ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
														.getMessage(),
												ResultsCode.SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED
														.getCode());
								offerResponse.setResults(results);
								isError = true;
							}
							if (!isError) {
								try {
									if(createCounterOfferRequest.getServiceDateTime1()
											== null && createCounterOfferRequest.getServiceDateTime2() != null){
										results = Results.getError(
												ResultsCode.SO_CONDITIONAL_OFFER_PROVIDE_START_DATE
														.getMessage(),
												ResultsCode.SO_CONDITIONAL_OFFER_PROVIDE_START_DATE
														.getCode());
										offerResponse.setResults(results);
										return offerResponse;
									}
									//to set serviceDate1, startTime, serviceDate2, endTime
									createCounterOfferRequest = mapServiceOrder(createCounterOfferRequest);
									Double spendlimit=null;
									//set spend limit
									if(null != createCounterOfferRequest.getSpendLimit()){
										spendlimit = new Double(createCounterOfferRequest.getSpendLimit());
									}
									//if resource list is null, place counter offer for resource from Identification
									if(null == createCounterOfferRequest.getResourceIds() || createCounterOfferRequest.getResourceIds().isEmpty()){
										createCounterOfferRequest.getResourceIds().add(providerResourceId);
									}
									//set expiration date
									conditionalExpirationDate = new Timestamp(
											TimeUtils
													.combineDateAndTime(
															Timestamp
																	.valueOf(createCounterOfferRequest
																			.getConditionalExpirationDate()
																			.toString()),
															createCounterOfferRequest
																	.getConditionalExpirationTime(),
															so
																	.getServiceLocationTimeZone())
													.getTime());
									
									//SL-15642:calling OF
									//for grouped orders
									if(PublicAPIConstant.GROUPED_SO_IND.equals(groupInd)){
										if(ofHelper.isNewGroup(groupId)){
										    List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = createConditionalOffer(createCounterOfferRequest, providerId, conditionalExpirationDate);
										    SOElementCollection routedProviders = new SOElementCollection();
										    routedProviders.addAllElements(firmRoutedProviders);
										    Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);
										    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.CREATE_GROUP_CONDITIONAL_OFFER, routedProviders, identification);
										    return mapOFResponse(response);
										}
										processResponse = orderGrpBO
											.processCreateConditionalOfferForGroup(groupId,
												new Integer(providerResourceId),
												securityContext.getCompanyId(),
												createCounterOfferRequest
														.getServiceDate1(),
												createCounterOfferRequest
														.getServiceDate2(),
												createCounterOfferRequest
														.getServiceTimeStart(),
												createCounterOfferRequest
														.getServiceTimeEnd(),
												conditionalExpirationDate,spendlimit,
												createCounterOfferRequest.getReasonCodes(),
												securityContext);
									}
									else{
										if(ofHelper.isNewSo(soId)){
											//creating RoutedProvider object for each resourceId
											List<com.servicelive.orderfulfillment.domain.RoutedProvider> firmRoutedProviders = createConditionalOffer(createCounterOfferRequest, providerId, conditionalExpirationDate);
											SOElementCollection routedProviders = new SOElementCollection();
											routedProviders.addAllElements(firmRoutedProviders);
											Identification identification = OFMapper.createProviderIdentFromSecCtx(securityContext);
											OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CREATE_CONDITIONAL_OFFER, routedProviders, identification);
											return mapOFResponse(response);
										}
										
										processResponse = serviceOrderBo
												.processCreateConditionalOffer(soId,
														new Integer(providerResourceId),
														securityContext.getCompanyId(),
														createCounterOfferRequest
																.getServiceDate1(),
														createCounterOfferRequest
																.getServiceDate2(),
														createCounterOfferRequest
																.getServiceTimeStart(),
														createCounterOfferRequest
																.getServiceTimeEnd(),
														conditionalExpirationDate,spendlimit,
														createCounterOfferRequest.getReasonCodes(),
														securityContext,
														createCounterOfferRequest.getResourceIds());
										
									}
									offerResponse = conditionalOfferMapper
									.mapSOCreateConditionalOfferMapper(processResponse);
									isInside=true;
									break;
									
								} catch (Exception ex) {
									logger.error("SOCreateConditionalOfferService.execute(): DataException occured"
															+ " while mapping create conditional  offer: "
															+ ex.getMessage(), ex);
									results = Results
											.getError(ex.getMessage(),
													ResultsCode.INTERNAL_SERVER_ERROR
															.getCode());
									offerResponse.setResults(results);
									return offerResponse;
								}
							}else{
								return offerResponse;
							}
						}

					}
					if(!isInside){
						results = Results.getError(
								ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH
										.getMessage(),
								ResultsCode.SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH
										.getCode());
						offerResponse.setResults(results);
					}
			      }	
				} else {
					results = Results.getError(
							ResultsCode.SO_CONDITIONAL_OFFER_STATUS_NOTMATCH
									.getMessage(),
							ResultsCode.SO_CONDITIONAL_OFFER_STATUS_NOTMATCH
									.getCode());
					offerResponse.setResults(results);
				}

					

		return offerResponse;
	}

	/**
	 * This method maps the createCounterOfferRequest for OF
	 * @param createCounterOfferRequest
	 * @return createCounterOfferRequest
	 */
	private List<com.servicelive.orderfulfillment.domain.RoutedProvider> createConditionalOffer(CreateCounterOfferRequest createCounterOfferRequest, String providerId, Timestamp conditionalExpirationDate) {
		logger.info("Start -- SOCreateConditionalOfferService.createConditionalOffer()");
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvList = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
		for(Integer resourceId : createCounterOfferRequest.getResourceIds()){
			//setting RoutedProvider object for each resource
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
			routedProvider.setProviderResourceId((long)resourceId);
			routedProvider.setVendorId(Integer.parseInt(providerId));
			routedProvider.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);
			routedProvider.setOfferExpirationDate(conditionalExpirationDate);
					
			if(null != createCounterOfferRequest.getServiceDate1()){
				routedProvider.setSchedule(new SOScheduleDate());
				routedProvider.getSchedule().setServiceDate1(createCounterOfferRequest.getServiceDate1());
				routedProvider.getSchedule().setServiceTimeStart(createCounterOfferRequest.getServiceTimeStart());
				//should not be setting end date without start date
				if(null != createCounterOfferRequest.getServiceDate2()){
					routedProvider.getSchedule().setServiceDate2(createCounterOfferRequest.getServiceDate1());
					routedProvider.getSchedule().setServiceTimeEnd(createCounterOfferRequest.getServiceTimeEnd());
				}
			}
			
			if (null != createCounterOfferRequest.getServiceDate1() && null != createCounterOfferRequest.getSpendLimit())
				routedProvider.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
			else if (null != createCounterOfferRequest.getSpendLimit())
				routedProvider.setProviderRespReasonId(OrderConstants.SPEND_LIMIT);
			else if (null != createCounterOfferRequest.getServiceDate1())
				routedProvider.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE);
			
			//setting couter offer reasons 
			for(Integer reason : createCounterOfferRequest.getReasonCodes()){
				SOCounterOfferReason counterOfferReason = new SOCounterOfferReason();
				counterOfferReason.setResponseReasonId((long)reason);
				counterOfferReason.setProviderResponse(routedProvider.getProviderResponse());
				counterOfferReason.setProviderResponseReasonId(routedProvider.getProviderRespReasonId());
				counterOfferReason.setRoutedProvider(routedProvider);
				routedProvider.getCounterOffers().add(counterOfferReason);
			}
			routedProvList.add(routedProvider);
		}
		logger.info("End -- SOCreateConditionalOfferService.createConditionalOffer()");
		return routedProvList;
	}

	/**
	 * This method maps the createCounterOfferRequest
	 * 
	 * @param createCounterOfferRequest
	 *            CreateCounterOfferRequest
	 * @return createCounterOfferRequest
	 */
	public CreateCounterOfferRequest mapServiceOrder(
			CreateCounterOfferRequest createCounterOfferRequest) {
		logger.info("Entering mapServiceOrder()");
		if (null != createCounterOfferRequest.getServiceDateTime1()) {
		createCounterOfferRequest.setServiceDate1((new Timestamp(DateUtils
				.defaultFormatStringToDate(
						createCounterOfferRequest.getServiceDateTime1())
				.getTime())));
		// setting the servicestartTime
		Date serviceStartTime = null;
		try {
			serviceStartTime = CommonUtility.sdfToDate
					.parse(createCounterOfferRequest.getServiceDateTime1());
		} catch (ParseException e) {
			logger.error("Exception Occurred while setting Service "
					+ "Start Time");
		}

		String serviceStartTimeStr = sdfTime.format(serviceStartTime);
		serviceStartTimeStr = serviceStartTimeStr.substring(11,
				serviceStartTimeStr.length());
		createCounterOfferRequest.setServiceTimeStart(serviceStartTimeStr);


		if (null != createCounterOfferRequest.getServiceDateTime2()) {
			createCounterOfferRequest.setServiceDate2(new Timestamp(DateUtils
					.defaultFormatStringToDate(
							createCounterOfferRequest.getServiceDateTime2())
					.getTime()));
			// setting the serviceEndTime
			Date serviceEndDate = null;
			try {
				serviceEndDate = CommonUtility.sdfToDate
						.parse(createCounterOfferRequest.getServiceDateTime2());
			} catch (ParseException e) {
				logger.error("Parse Exception Occurred while "
						+ "setting ServiceEndDate");
			}
			String serviceEndTimeStr = sdfTime.format(serviceEndDate);
			serviceEndTimeStr = serviceEndTimeStr.substring(11,
					serviceEndTimeStr.length());
			createCounterOfferRequest.setServiceTimeEnd(serviceEndTimeStr);
		}
		}
		if (null != createCounterOfferRequest.getOfferExpirationDate()) {
			createCounterOfferRequest
					.setConditionalExpirationDate(new Timestamp(DateUtils
							.defaultFormatStringToDate(
									createCounterOfferRequest
											.getOfferExpirationDate())
							.getTime()));
			// setting the serviceEndTime
			Date offerExpirationDate = null;
			try {
				offerExpirationDate = CommonUtility.sdfToDate
						.parse(createCounterOfferRequest
								.getOfferExpirationDate());
			} catch (ParseException e) {
				logger.error("Parse Exception Occurred while "
						+ "setting ServiceEndDate");
			}
			String offerExpirationTimeStr = sdfTime.format(offerExpirationDate);
			offerExpirationTimeStr = offerExpirationTimeStr.substring(11,
					offerExpirationTimeStr.length());
			createCounterOfferRequest
					.setConditionalExpirationTime(offerExpirationTimeStr);
		}
		logger.info("Leaving mapServiceOrder()");
		return createCounterOfferRequest;

	}
	
	private IAPIResponse mapOFResponse(OrderFulfillmentResponse response){
		CounterOfferResponse counterOfferResponse = new CounterOfferResponse();
		Results results = new Results();
		if (response.getErrors().isEmpty()) {
			results = Results.getSuccess(ResultsCode.COUNTER_OFFER_SUBMITTED.getMessage());
		} else {
			results = Results.getError(ResultsCode.COUNTER_OFFER_ERROR.getMessage(), ResultsCode.COUNTER_OFFER_ERROR.getCode());
		}
		counterOfferResponse.setResults(results);
		return counterOfferResponse;
    }

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public void setConditionalOfferMapper(
			SOCreateConditionalOfferMapper conditionalOfferMapper) {
		this.conditionalOfferMapper = conditionalOfferMapper;
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

