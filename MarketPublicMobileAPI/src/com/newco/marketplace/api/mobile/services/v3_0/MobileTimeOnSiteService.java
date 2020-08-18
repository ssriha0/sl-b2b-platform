package com.newco.marketplace.api.mobile.services.v3_0;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.v3_0.MobileTimeOnSiteRequest;
import com.newco.marketplace.api.mobile.beans.v3_0.MobileTimeOnSiteResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileTimeOnSiteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileTimeOnSiteValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.MobileSOOnsiteVisitVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.ServiceOrder;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This class would act as a Service for Time on Site
 * 
 * @author Infosys
 * @version 1.0
 */
// @Namespace("http://www.servicelive.com/namespaces/soTimeOnSite")
// @ExcludeValidation
@APIRequestClass(MobileTimeOnSiteRequest.class)
@APIResponseClass(MobileTimeOnSiteResponse.class)
public class MobileTimeOnSiteService extends BaseService {
	private final Logger logger = Logger.getLogger(MobileTimeOnSiteService.class);
	private IManageScheduleBO manageScheduleBO;
	private IMobileSOActionsBO mobileSOActionsBO;
	private MobileTimeOnSiteValidator timeOnSiteValidator;
	private MobileTimeOnSiteMapper timeOnSiteMapper;
	private OFHelper ofHelper;
	private IRelayServiceNotification relayNotificationService;
	
	//added for R12.0 to Update NPS with the sub-status 'Provider On-site' when the provider check-in from mobile
	protected INotificationService notificationService;
	public MobileTimeOnSiteService() {
		// calling the BaseService constructor to initialize
		super();
	}

	/**
	 * Logic for handing time on site API request
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside MobileTimeOnSiteService.execute()");		
		long startTime = System.currentTimeMillis();
		
		// declaring variables
		MobileSOOnsiteVisitVO onsiteVisitVO = null;
		boolean isError = false;
		MobileTimeOnSiteResponse mobileTimeonSiteResponse = null;

		// get the so Id from the apiVO
		String soId = apiVO.getSOId();
		

		SecurityContext securityContext = getSecurityContextForVendor(apiVO
				.getProviderResourceId());
		String userName="";
		Integer entityId= 0;
		Integer roleId=0;
		String createdBy="";
		Integer tripNo = 0;
		

		if(securityContext!=null){
			userName = securityContext.getUsername();
			entityId = securityContext.getVendBuyerResId();
			roleId = securityContext.getRoleId();
			LoginCredentialVO lvRoles = securityContext.getRoles();
			createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
		}
		
		try {
			
			// get the service order for the soId
			ServiceOrder so = manageScheduleBO.fetchServiceOrderForVisit(soId);

			if (null != so){
				
				// get the request object from apiVO
				MobileTimeOnSiteRequest mobileTimeonSiteRequest = (MobileTimeOnSiteRequest) apiVO
						.getRequestFromPostPut();

				// get the event type from the request
				String eventType = mobileTimeonSiteRequest.getEventType();
				Double latitude = mobileTimeonSiteRequest.getLatitude();
				Double longitude = mobileTimeonSiteRequest.getLongitude();
				Integer currentTripNo =  mobileTimeonSiteRequest.getCurrentTripNo();
				String reasonCode = mobileTimeonSiteRequest.getReasonCode();
				
				logger.info("ReasonCode: "+ reasonCode);
				logger.info("Current Trip Number: "+ currentTripNo);	

				Date currentDateTimeZone = getDateinGMT(new Date());
				
				Date checkInDateTimeZone = null;
				Date checkOutDateTimeZone = null;
				
				// removed GMT conversion from checkin/checkout time as now we will get times in GMT
				if (PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
					checkInDateTimeZone = getDateFromString(mobileTimeonSiteRequest.getCheckInTime());
				}else if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE.equals(eventType)) {
					checkOutDateTimeZone = getDateFromString(mobileTimeonSiteRequest.getCheckOutTime());
				}
	
				// perform basic validation on the Time on Site request
				mobileTimeonSiteResponse = validateTimeOnSiteRequest(so, eventType,
						apiVO.getProviderResourceId(), currentDateTimeZone, checkInDateTimeZone, checkOutDateTimeZone, latitude,longitude,
						currentTripNo,reasonCode);
	
				// return response object if not null, i.e. error condition
				if (null != mobileTimeonSiteResponse) {
					mobileTimeonSiteResponse.setSoId(soId);
					return mobileTimeonSiteResponse;
				}
	
				Integer providerId = apiVO.getProviderResourceId();
	
				// if event type is 'ARRIVAL'
				if (PublicMobileAPIConstant.EVENT_TYPE_ARRIVAL.equals(eventType)) {
					isError = false;
					long arrivalStart = System.currentTimeMillis();
					
					//R14_0 validate if check out date is greater than current date 
					mobileTimeonSiteResponse = timeOnSiteValidator
							.validateCheckInCheckOutDate(eventType, currentDateTimeZone, checkInDateTimeZone);
					if (null != mobileTimeonSiteResponse) {
						mobileTimeonSiteResponse.setSoId(soId);
						return mobileTimeonSiteResponse;
					}
					
					// map the SO Onsite Visit VO for persisting
					onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
							onsiteVisitVO, so, mobileTimeonSiteRequest,
							currentDateTimeZone, checkInDateTimeZone);
	
					// change status of SO using ofHelper if status is 'ACCEPTED'
					if (ofHelper.isNewSo(soId)) {
						long startOF = System.currentTimeMillis();
						Identification id = getProviderIdForOF(so, providerId);
	
						SOOnSiteVisit onSiteVisit = timeOnSiteMapper
								.mapSOOnsiteVisitForOF(onsiteVisitVO);
	
						OrderFulfillmentResponse response = ofHelper
								.runOrderFulfillmentProcess(soId,
										SignalType.ON_SITE_VISIT, onSiteVisit, id);
						isError = response.isError();
						
						logger.info("Onsite visit Id ::"+onSiteVisit.getVisitId());
						
						long endOF = System.currentTimeMillis();
						logger.info("Total time taken for marking arrival using OF in ms :::"+(endOF-startOF));
					} else {
						logger.error("MobileTimeOnSiteService.execute(): SO not OF enabled.");
						isError = true;
					}
	
					if (isError) {
						// set the error code as internal server error
						mobileTimeonSiteResponse = MobileTimeOnSiteResponse
								.getInstanceForError(
										ResultsCode.INTERNAL_SERVER_ERROR, soId);
	
						// return the response object
						return mobileTimeonSiteResponse;
					}
					
					long arrivalEnd = System.currentTimeMillis();
					logger.info("Total time taken for marking arrival in ms :::"+(arrivalEnd-arrivalStart));
					
					// Create OR Update the trip					
					tripNo = manageScheduleBO.fetchLatestValidTripForSO(so,createdBy,onsiteVisitVO);
				}
				// else if event type is 'DEPARTURE'
				else if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE
						.equals(eventType)) {
					
					
					long departStart = System.currentTimeMillis();
					Integer currentTripId = null;
					if(0!=currentTripNo){
						currentTripId = manageScheduleBO.validateTrip(currentTripNo,soId);
						if(null==currentTripId){
							mobileTimeonSiteResponse = MobileTimeOnSiteResponse
							.getInstanceForError(
									ResultsCode.TIMEONSITE_INVALID_TRIP, soId);

							// return the response object
							return mobileTimeonSiteResponse;
						}
						
					}
					MobileSOOnsiteVisitVO latestArrival = manageScheduleBO
							.fetchLatestArrivalForSOId(so.getSoId());
	
					//R14_0 validate if check out date is greater than current date 
					mobileTimeonSiteResponse = timeOnSiteValidator
							.validateCheckInCheckOutDate(eventType, currentDateTimeZone, checkOutDateTimeZone);
					if (null != mobileTimeonSiteResponse) {
						mobileTimeonSiteResponse.setSoId(soId);
						return mobileTimeonSiteResponse;
					}
					
					mobileTimeonSiteResponse = timeOnSiteValidator
							.validateLatestArrival(latestArrival,
									currentDateTimeZone, checkOutDateTimeZone);
	
					if (null != mobileTimeonSiteResponse) {
						mobileTimeonSiteResponse.setSoId(soId);
						return mobileTimeonSiteResponse;
					}
	
					if (null != latestArrival
							&& latestArrival.getDepartureDate() == null) {
						// map the SO Onsite Visit VO for persisting
						onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
								latestArrival, so, mobileTimeonSiteRequest,
								currentDateTimeZone, checkOutDateTimeZone);
						// Update SO_ONSITE_VISIT
						manageScheduleBO.updateSoOnsiteVisit(onsiteVisitVO);
					} else {
						// map the SO Onsite Visit VO for persisting
						onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
								onsiteVisitVO, so, mobileTimeonSiteRequest,
								currentDateTimeZone, checkOutDateTimeZone);
						// insert into SO_ONSITE_VISIT
						long visitId = manageScheduleBO.insertSoOnsiteVisitId(onsiteVisitVO);
						onsiteVisitVO.setVisitId(visitId);
					}
					
					// Senting Notification for Relay Services
					Integer buyerId = so.getBuyerId();
					boolean relayServicesNotifyFlag = relayNotificationService.isRelayServicesNotificationNeeded(buyerId, soId);
					logger.info("calling relay webhook event for checkout: " + relayServicesNotifyFlag);
					logger.debug("calling relay webhook event for checkout: " + relayServicesNotifyFlag + " " + soId);
					if (relayServicesNotifyFlag) {
						relayNotificationService.sentNotificationRelayServices(MPConstants.TIME_ONSITE_API_EVENT, soId);
					} 
					
					/**
					 *  TODO Implement following validations
					 *  1. Check if the current trip is OPEN with a valid check-in
					 */
					
					if(0!=currentTripNo && null!=currentTripId){
						// R12.3 - SL-20673 Close the current trip 
						//New Trip creation logic deleted
						tripNo = manageScheduleBO.closeCurrentTrip(so,createdBy,onsiteVisitVO,currentTripNo);
					}
					long departEnd = System.currentTimeMillis();
					logger.info("Total time taken for marking departure in ms :::"+(departEnd-departStart));
					
				}
			
				// else invalid event
				else {
					// set the error code as invalid event type
					mobileTimeonSiteResponse = MobileTimeOnSiteResponse
							.getInstanceForError(
									ResultsCode.TIMEONSITE_INVALID_EVENTTYPE, soId);
	
					// return the response object
					return mobileTimeonSiteResponse;
				}
	
				// return the success message
				mobileTimeonSiteResponse = new MobileTimeOnSiteResponse(soId,
						Results.getSuccess(),tripNo);
				
				
				//update substatus in so_hdr
				updateSOSubStatus(soId,eventType,roleId,userName,entityId,createdBy,so.getBuyerId());
			} 
		}catch (Exception exception) {
			logger.error("MobileTimeOnSiteService.execute(): exception due to "
					+ exception);
			// set the error code as internal server error
			mobileTimeonSiteResponse = MobileTimeOnSiteResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR,
							soId);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving MobileTimeOnSiteService.execute()");
		}
		
		long endTime = System.currentTimeMillis();
		logger.info("Time taken for execute method for add time on site :::"+(endTime- startTime));
		// return the response object
		return mobileTimeonSiteResponse;
	}
	
	private Date getDateFromString(String dateTime) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if(StringUtils.isNotBlank(dateTime)){
			try {
				date = sdf.parse(dateTime);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}

	private void updateSOSubStatus(String soId,String eventType, Integer roleId, String userName, Integer entityId, String createdBy, Integer buyerId){
		try {
			manageScheduleBO.updateSOSubStatus(soId, eventType);
			if(MPConstants.EVENT_TYPE_ARRIVAL.equals(eventType)){
				//R12.0 Update NPS with the sub-status 'Provider On-site' when the provider check-in from mobile START
				//fetching so status
				Integer wfStateId=manageScheduleBO.getSOStatus(soId);
				Integer subStatusIdChanged= MPConstants.PROVIDER_ON_SITE_SUBSTATUS_ID;;
				boolean isEligibleForNPSNotification=false;
				//call validation method for NPS notification
				try {
					isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,soId);
				} 
				catch (BusinessServiceException e) {
					logger.info("Exception in validatiing nps notification eligibility"+ e.getMessage());
				}
				if(isEligibleForNPSNotification){
					//Call Insertion method
					try {
						notificationService.insertNotification(soId,wfStateId,subStatusIdChanged,null);
					 }catch (BusinessServiceException e){
						logger.info("Caught Exception while insertNotification",e);				
						}
				}
				//R12.0 Update NPS with the sub-status 'Provider On-site' when the provider check-in from mobile END
				historyLogging(roleId,soId,userName,entityId,MPConstants.JOB_ON_SITE,createdBy);		
			}

		} catch (BusinessServiceException e) {
			logger.info("MobileTimeOnSiteService.execute():Error while updating substatus due to"+e);
		}
	}

	/**
	 * This method generates the Identification object for provider to invoke OF
	 * 
	 * @param so
	 * @param providerId
	 * @return
	 */
	private Identification getProviderIdForOF(ServiceOrder so,
			Integer providerId) {
		// getting the security context for the provider Id
		SecurityContext securityContext = BaseService
				.getSecurityContextForVendor(providerId);

		// preparing the Id for order fulfillment process
		Identification id = new Identification();
		id.setEntityType(EntityType.PROVIDER);
		id.setUsername(securityContext.getUsername());
		id.setRoleId(securityContext.getRoleId());
		// SL-18992 Setting the company id to fix production issue
		// related with IVR
		if (null != so.getAcceptedVendorId()) {
			id.setCompanyId(so.getAcceptedVendorId().longValue());
		} else {
			id.setCompanyId(null);
		}
		id.setResourceId(providerId.longValue());

		return id;
	}

	/**
	 * This method validates the parameters for Time on Site request
	 * 
	 * @param so
	 * @param eventType
	 * @param providerId
	 * @param currentDate
	 * @return
	 * @throws BusinessServiceException
	 */
	private MobileTimeOnSiteResponse validateTimeOnSiteRequest(ServiceOrder so,
			String eventType, Integer providerId, Date currentDate, Date checkInDateTimeZone, Date checkOutDateTimeZone, Double latitude,Double longitude,
			Integer curTripNo,String reasonCode)
			throws BusinessServiceException {

		// validate SO status
		MobileTimeOnSiteResponse mobileTimeonSiteResponse = timeOnSiteValidator
				.validateSOStatus(so, eventType);

		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}
		// validate accepted date
		mobileTimeonSiteResponse = timeOnSiteValidator.validateAcceptedDate(so,
				eventType, currentDate, checkInDateTimeZone);

		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}

		//validate latitude and longitude
		mobileTimeonSiteResponse = timeOnSiteValidator.validateLatitudeAndLongitude(latitude,longitude); 
		
		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}
		
		// validate currentTripNo and reasonCode for departure		
		mobileTimeonSiteResponse = timeOnSiteValidator.validateTripAndReasonCode
			(eventType,curTripNo,reasonCode); 

		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}
		//R14.0 validate resource permission
		mobileTimeonSiteResponse = timeOnSiteValidator.validateResourcePermission(providerId, so.getAcceptedResourceId());
		return mobileTimeonSiteResponse;
	}
	
	
	/**
	 * @param roleId
	 * @param soId
	 * @param userName
	 * @param entityId
	 * @param desc
	 * @param createdBy
	 */
	private void historyLogging(Integer roleId, String soId, String userName,
			Integer entityId,String desc,String createdBy) {
		try{
			Date d=new Date(System.currentTimeMillis());
			Timestamp date = new Timestamp(d.getTime());
			ProviderHistoryVO hisVO=new ProviderHistoryVO();
			hisVO.setSoId(soId);
			hisVO.setActionId(MPConstants.SUBSTATUS_ACTION_ID);
			hisVO.setDescription(MPConstants.SUBSTATUS_DESCRIPTION+" "+desc);
			hisVO.setCreatedDate(date);
			hisVO.setModifiedDate(date);
			hisVO.setCreatedBy(createdBy);
			hisVO.setRoleId(roleId);
			hisVO.setModifiedBy(userName);
			hisVO.setEnitityId(entityId);
			mobileSOActionsBO.historyLogging(hisVO);
		}
		catch (Exception ex) {
			logger.error("Exception Occured in MobileTimeOnSiteService-->historyLogging()");

		}
		
		
	}

	/**
	 * This method converts the given date to GMT or given timezone format
	 * 
	 * @param timeZoneDate
	 *            Date
	 * @param TimeZone
	 *            String
	 * @return Date
	 */
	private Date getDateinGMT(Date timeZoneDate) {
		Calendar cal = Calendar.getInstance();
		
		TimeZone timeZone = cal.getTimeZone();

		Timestamp ts = new Timestamp(timeZoneDate.getTime());

		Date gmtDate = convertTimeToGMT(ts, timeZone.getID());
		// Added two minute
		 addMinutes(gmtDate, 2);
		return gmtDate;
	}
	


	private Date convertTimeToGMT(Timestamp tm, String zone){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = null;
        int year = 0;
        int month = 0;
        int dayOfMonth = 0;
        int hours = 0;
        int min = 0;
        int sec = 0;
        Calendar calendar = null;
        try {
        	logger.info("input Time Stamp "+tm);
        	logger.info("input Time zone "+zone);

        	
            dateTime = sdf.format(tm.getTime());
            year = Integer.parseInt(dateTime.substring(0, 4));
            month = Integer.parseInt(dateTime.substring(5, 7));
            dayOfMonth = Integer.parseInt(dateTime.substring(8, 10));
    
            hours = Integer.parseInt(dateTime.substring(11, 13));
            min = Integer.parseInt(dateTime.substring(14, 16));
            sec = Integer.parseInt(dateTime.substring(17, 19));
            calendar = new GregorianCalendar(year, month-1, dayOfMonth,
                    hours, min, sec);
            TimeZone fromTimeZone = TimeZone.getTimeZone(zone);
            TimeZone toTimeZone = TimeZone.getTimeZone("GMT");
            logger.info(calendar.getTime());
            logger.info(fromTimeZone);

            calendar.setTimeZone(fromTimeZone);
            calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
            if (fromTimeZone.inDaylightTime(calendar.getTime())) {
                calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
                logger.info("Changed daylight saving: " + calendar);
            }
            calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        } catch (Exception e) {
            logger.info("Exception in convertTimeToGMT method" + e);
        }
        logger.info("output Time Stamp "+calendar.getTime());
        return calendar.getTime();
     
	}
	private static final long MINUTE_IN_MILLISECONDS = 1000*60;
	private static void addMinutes(Date dt, int mins)
	   {
	       if(mins != 0)
	       {
	           long tempMs = dt.getTime() + mins*MINUTE_IN_MILLISECONDS;
	           dt.setTime(tempMs);
	       }
	   }

	/**
	 * This method checks for the SO ID pattern using regex
	 * 
	 * @param so
	 * @return
	 */
/*	private boolean validateSORegexPattern(String so) {
		if (so == null)
			return false;
		Pattern p = Pattern.compile("\\d{3}-\\d{4}-\\d{4}-\\d{2}");
		Matcher m = p.matcher(so);
		boolean b = m.matches();
		return b;
	}*/

	/**
	 * @return the manageScheduleBO
	 */
	public IManageScheduleBO getManageScheduleBO() {
		return manageScheduleBO;
	}

	/**
	 * @param manageScheduleBO
	 *            the manageScheduleBO to set
	 */
	public void setManageScheduleBO(IManageScheduleBO manageScheduleBO) {
		this.manageScheduleBO = manageScheduleBO;
	}

	public MobileTimeOnSiteValidator getTimeOnSiteValidator() {
		return timeOnSiteValidator;
	}

	public void setTimeOnSiteValidator(MobileTimeOnSiteValidator timeOnSiteValidator) {
		this.timeOnSiteValidator = timeOnSiteValidator;
	}

	public MobileTimeOnSiteMapper getTimeOnSiteMapper() {
		return timeOnSiteMapper;
	}

	public void setTimeOnSiteMapper(MobileTimeOnSiteMapper timeOnSiteMapper) {
		this.timeOnSiteMapper = timeOnSiteMapper;
	}

	/**
	 * @return the ofHelper
	 */
	public OFHelper getOfHelper() {
		return ofHelper;
	}

	/**
	 * @param ofHelper
	 *            the ofHelper to set
	 */
	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	/**
	 * @return the notificationService
	 */
	public INotificationService getNotificationService() {
		return notificationService;
	}

	/**
	 * @param notificationService the notificationService to set
	 */
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
}