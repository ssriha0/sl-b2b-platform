/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 21-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.timeonsite.SOTimeOnSiteRequest;
import com.newco.marketplace.api.beans.so.timeonsite.SOTimeOnSiteResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;


/**
 * This class is a service class for Time On Site for Service Order
 * 
 * 
 * @author Seshu
 * @version 1.0
 */
public class SOProviderTimeOnSiteService extends BaseService{

	private final Logger logger = Logger.getLogger(SOProviderTimeOnSiteService.class);
	private IOnSiteVisitBO siteVisitBO;
	private IServiceOrderBO serviceOrderBO;
	private IRelayServiceNotification relayNotificationService;
	private OFHelper ofHelper;
	
	private SOTimeOnSiteResponse timeOnSiteResponse;
	private static final String TIME_ON_SITE_XSD_REQUEST  = "timeOnSiteProviderRequest.xsd";
	private static final String TIME_ON_SITE_XSD_RESPONSE = "timeOnSiteProviderResponse.xsd";
	public static final String TIME_ON_SITE_NAMESPACE = "http://www.servicelive.com/namespaces/soTimeOnSiteResponse";
	public static final String TIME_ON_SITE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soTimeOnSiteResponse timeOnSiteProviderResponse.xsd";
	//UTC time format. The character "Z" denotes Greenwich Mean Time(GMT)
	private static final String DATE_TIME_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'hh:mm:ss'Z'";
	private static final String ARRIVAL_DEPARTURE_TIME_VALIDATION="Arrival date cannot be greater than departure date";
	private static final String ARRIVAL_CURRENT_TIME_VALIDATION="Arrival date cannot be greater than current date";
	private static final String DEPARTURE_CURRENT_TIME_VALIDATION="Departure date cannot be greater than current date";
	private static final String DATES_VALIDATION = "Invalid Arrival/Departure date values";
	/**
	 * Constructor
	 */

	public SOProviderTimeOnSiteService () {
		super (TIME_ON_SITE_XSD_REQUEST,
			   TIME_ON_SITE_XSD_RESPONSE, 
			   TIME_ON_SITE_NAMESPACE, 
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				TIME_ON_SITE_SCHEMALOCATION,	
				SOTimeOnSiteRequest.class,
				SOTimeOnSiteResponse.class);
		addRequiredURLParam(APIRequestVO.SOID, DataTypes.STRING);
	}	



	/**
	 * This method dispatches the time On Site for Service order request.
	 * 
	 * @param apiVO APIRequestVO
	 * @return IAPIResponse
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering SOProviderTimeOnSite.execute()");	
		SOTimeOnSiteRequest timeOnSiteRequest = new SOTimeOnSiteRequest();
		ArrayList<Object> argumentList = new ArrayList<Object>();
		String soId = (String) apiVO.getProperty(APIRequestVO.SOID);
		Integer providerId = apiVO.getProviderIdInteger();
		try {
			SecurityContext securityContext = null;
			timeOnSiteRequest = (SOTimeOnSiteRequest) apiVO.getRequestFromPostPut();
			Integer timeSiteId = timeOnSiteRequest.getTimeOnSiteId();

			SOOnsiteVisitVO soOnsiteVisitVO = getSOOnsiteVisitVO(timeOnSiteRequest, soId);
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soOnsiteVisitVO.getSoId());
			if (null != timeSiteId && timeSiteId.longValue() > 0) {
				soOnsiteVisitVO.setVisitId(timeSiteId.longValue());
				SOOnsiteVisitVO existingVisit = siteVisitBO.getTimeOnSiteResultBySoIDVisitID(soOnsiteVisitVO);
				if (null != existingVisit) {
					siteVisitBO.UpdateTimeOnSiteRow(soOnsiteVisitVO);
				} else {
					timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(ResultsCode.INVALID_TIME_ONSITE_ID.getMessage(),
							ResultsCode.INVALID_TIME_ONSITE_ID.getCode()));
					return timeOnSiteResponse;
				}

			} else {
			    //SLT1831
				if(ofHelper.isNewSo(soOnsiteVisitVO.getSoId())){
					Identification id = getProviderIdForOF(serviceOrder, providerId);
					securityContext = BaseService.getSecurityContextForVendor(providerId);
				    SOOnSiteVisit onSiteVisit = new SOOnSiteVisit();
				    onSiteVisit.setArrivalDate(soOnsiteVisitVO.getArrivalDate());
				    onSiteVisit.setArrivalInputMethod(soOnsiteVisitVO.getArrivalInputMethod());
				    onSiteVisit.setDeleteIndicator(soOnsiteVisitVO.getDeleteInd());
				    if (soOnsiteVisitVO.getDepartureCondition() != null)
				    	onSiteVisit.setDepartureCondition(soOnsiteVisitVO.getDepartureCondition().toString());
				    onSiteVisit.setDepartureDate(soOnsiteVisitVO.getDepartureDate());
				    onSiteVisit.setDepartureInputMethod(soOnsiteVisitVO.getDepartureInputMethod());
				    if(soOnsiteVisitVO.getDepartureResourceId() != null)
				    	onSiteVisit.setDepartureResourceId((long)soOnsiteVisitVO.getDepartureResourceId());
				    onSiteVisit.setIvrCreateDate(soOnsiteVisitVO.getIvrcreatedate());
				    if(soOnsiteVisitVO.getResourceId() != null)
				    	onSiteVisit.setResourceId((long)soOnsiteVisitVO.getResourceId());
				    if(soOnsiteVisitVO.getArrivalReason() != null) 
				    	onSiteVisit.setArrivalReason(soOnsiteVisitVO.getArrivalReason());
				    
				    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soOnsiteVisitVO.getSoId(), SignalType.ON_SITE_VISIT, onSiteVisit,id);
				    if (response.isError()){
						throw new BusinessServiceException(response.getErrorMessage());
					}

				}
				siteVisitBO.InsertVisitResult(soOnsiteVisitVO);
				if (serviceOrder.getWfStateId() == OrderConstants.ACCEPTED_STATUS) {
					serviceOrderBO.activateAcceptedSO(soOnsiteVisitVO.getSoId(), securityContext);
				}
			}

			String endDate = timeOnSiteRequest.getDepartureDateTime();
			if ((serviceOrder.getAcceptedDate().compareTo(
					DateUtils.getDateFromString(timeOnSiteRequest.getArrivalDateTime(), DATE_TIME_FORMAT_WITH_TIMEZONE)) > 0)
					|| (endDate != null && (serviceOrder.getAcceptedDate().compareTo(
							DateUtils.getDateFromString(endDate, DATE_TIME_FORMAT_WITH_TIMEZONE)) > 0))) {
				timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(ResultsCode.ACCEPTED_DATE_TIME_VALIDATION.getMessage(),
						ResultsCode.ACCEPTED_DATE_TIME_VALIDATION.getCode()));

			} else {
				timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getSuccess());
				
				// Senting Notification for Relay Services
				Integer buyerId = serviceOrder.getBuyerId();
				boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, soId);
				logger.info("calling relay webhook event for checkout: " + relayServicesNotifyFlag);
				logger.debug("calling relay webhook event for checkout: " + relayServicesNotifyFlag + " " + soId);
				if (relayServicesNotifyFlag) {
					relayNotificationService.sentNotificationRelayServices(MPConstants.TIME_ONSITE_API_EVENT, soId);
				} 
			}
		} catch(NumberFormatException nme) {
			logger.error("SOProviderTimeOnSite.execute(): Number Format Exception "
								+"occurred for providerId" + providerId);
			timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(
					ResultsCode.INVALID_RESOURCE_ID
					.getMessage(), ResultsCode.INVALID_RESOURCE_ID.getCode()));
		} catch(DataServiceException exp) {
			logger.error("SOProviderTimeOnSite.execute(): DataServiceException Exception "
					+"occurred for providerId:" + providerId);
			if(ARRIVAL_DEPARTURE_TIME_VALIDATION==exp.getMessage()){
			timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(
					ResultsCode.ARRIVAL_DEPARTURE_TIME_VALIDATION.getMessage(), 
					ResultsCode.ARRIVAL_DEPARTURE_TIME_VALIDATION.getCode()));
			}else
				if(ARRIVAL_CURRENT_TIME_VALIDATION==exp.getMessage()){
					timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(
							ResultsCode.ARRIVAL_CURRENT_TIME_VALIDATION.getMessage(), 
							ResultsCode.ARRIVAL_CURRENT_TIME_VALIDATION.getCode()));
					}
				else {
						timeOnSiteResponse = new SOTimeOnSiteResponse(Results.getError(
								ResultsCode.DEPARTURE_CURRENT_TIME_VALIDATION.getMessage(), 
								ResultsCode.DEPARTURE_CURRENT_TIME_VALIDATION.getCode()));
						}
		} catch (BusinessServiceException exp ) {
			timeOnSiteResponse = new SOTimeOnSiteResponse(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		if (logger.isInfoEnabled()) {
			logger.info("Exiting  SOProviderTimeOnSiteService.execute()");
		}	
		return timeOnSiteResponse;
	}
	 
	/**
	 * This method validates if arrival date/time is greater than departure date/time
	 * 
	 * @param arrivalDate Date
	 * @param departureDate Date
	 * @return boolean
	 */
	private String validateOnsiteDateTimes(Date arrivalDate, Date departureDate) {
		Date currentDate=new Date();
		if(departureDate != null && arrivalDate.compareTo(departureDate)>0){
			return ARRIVAL_DEPARTURE_TIME_VALIDATION; // if arrivalDate > departureDate
		}
		else if(arrivalDate.compareTo(currentDate)>0){
			return ARRIVAL_CURRENT_TIME_VALIDATION; // if arrivalDate > currentDate
		}
		else if(departureDate != null && departureDate.compareTo(currentDate)>0){
			return DEPARTURE_CURRENT_TIME_VALIDATION; // if departureDate > currentDate
		}
		else return PublicAPIConstant.FALSE;
	}
 
	/**
	 * This method converts the given date to GMT or given timezone format
	 * 
	 * @param timeZoneDate Date
	 * @param TimeZone String
	 * @return Date
	 */
	private static Date getDateinGMT(Date timeZoneDate,String TimeZone){
		Timestamp ts = new Timestamp( timeZoneDate.getTime());
		
		Date gmtDate= TimeUtils.convertToGMT(ts,TimeZone);
		return gmtDate;
		
	}	
	/**
	 * This method generates the Identification object for provider to invoke OF
	 * 
	 * @param so
	 * @param providerId
	 * @return
	 */
	private Identification getProviderIdForOF(ServiceOrder so,Integer providerId) {
		// getting the security context for the provider Id
		SecurityContext securityContext = BaseService.getSecurityContextForVendor(providerId);
		// preparing the Id for order fulfillment process
		Identification id = new Identification();
		id.setEntityType(EntityType.PROVIDER);
		id.setUsername(securityContext.getUsername());
		id.setRoleId(securityContext.getRoleId());		
		if (null != so.getAcceptedVendorId()) {
			id.setCompanyId(so.getAcceptedVendorId().longValue());
		} else {
			id.setCompanyId(null);
		}
		id.setResourceId(providerId.longValue());

		return id;
	 }

	/**
	 * This method prepares SOOnsiteVisitVO object based on request parameters
	 * 
	 * @param timeZoneDate SOTimeOnSiteRequest
	 * @param soId String
	 * @param providerResourceId Integer
	 * @return SOOnsiteVisitVO
	 */
	private SOOnsiteVisitVO getSOOnsiteVisitVO(SOTimeOnSiteRequest timeOnSiteRequest,String soId) throws 
			DataServiceException,BusinessServiceException{
		SOOnsiteVisitVO soOnsiteVisitVO= new SOOnsiteVisitVO();
		String arrivalDateTime = timeOnSiteRequest.getArrivalDateTime();
		String departureDateTime = timeOnSiteRequest.getDepartureDateTime();
		
		ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
		Integer providerResourceId =serviceOrder.getAcceptedResourceId();
		/*new Date(serviceOrder.getAcceptedDate().getTime()))) -- This is for future validations, 
		 * 	checking arrival & departure dates with so accepted date
		 */
		
		//execute the following only if arrival date is not null
		if(arrivalDateTime!=null){
			//validates arrival and departure dates
			Date stDate = DateUtils.getDateFromString(arrivalDateTime, DATE_TIME_FORMAT_WITH_TIMEZONE);
			Date endDate = null;
			
			if(departureDateTime != null){
				endDate = DateUtils.getDateFromString(departureDateTime, DATE_TIME_FORMAT_WITH_TIMEZONE);
			}
			
			String validDates=validateOnsiteDateTimes(stDate, endDate);
			if(PublicAPIConstant.FALSE!=validDates){	 
				throw (new DataServiceException(validDates));
			} else {
				soOnsiteVisitVO.setSoId(soId);
				soOnsiteVisitVO.setArrivalInputMethod(providerResourceId);
				
				//Find out the timezone at the service location. This would be determined based on zipcode.
				String timeZoneForSO = serviceOrder.getServiceLocationTimeZone(); 
				
				//convert he given arrival and departure dates&time to above timezone.
				soOnsiteVisitVO.setArrivalDate(getDateinGMT(DateUtils.getDateFromString(arrivalDateTime, DATE_TIME_FORMAT_WITH_TIMEZONE),timeZoneForSO));
	
				if(departureDateTime!= null && timeZoneForSO!= null){
					soOnsiteVisitVO.setDepartureDate(getDateinGMT(DateUtils.getDateFromString(departureDateTime, DATE_TIME_FORMAT_WITH_TIMEZONE),timeZoneForSO));
				}			
				SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				format.setTimeZone(TimeZone.getTimeZone("GMT"));
				Calendar cal = Calendar.getInstance();
				
				Date createdDate = cal.getTime();
				soOnsiteVisitVO.setCreatedDate(createdDate);
			}
		}else{
			throw (new DataServiceException(DATES_VALIDATION));
		}
		return soOnsiteVisitVO;	
	} 
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	
 	public IOnSiteVisitBO getSiteVisitBO() {
		return siteVisitBO;
	}

	public void setSiteVisitBO(IOnSiteVisitBO siteVisitBO) {
		this.siteVisitBO = siteVisitBO;
	}

	public SOTimeOnSiteResponse getTimeOnSiteResponse() {
		return timeOnSiteResponse;
	}

	public void setTimeOnSiteResponse(SOTimeOnSiteResponse timeOnSiteResponse) {
		this.timeOnSiteResponse = timeOnSiteResponse;
	}



	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}



	public void setRelayNotificationService(IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
	
	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
}
