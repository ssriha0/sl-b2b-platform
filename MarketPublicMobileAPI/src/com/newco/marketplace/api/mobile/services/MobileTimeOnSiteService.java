package com.newco.marketplace.api.mobile.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.ExcludeValidation;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.MobileTimeOnSiteRequest;
import com.newco.marketplace.api.mobile.beans.MobileTimeOnSiteResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.MobileTimeOnSiteMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IManageScheduleBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.api.utils.validators.MobileTimeOnSiteValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.TimeUtils;
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
	private Logger logger = Logger.getLogger(MobileTimeOnSiteService.class);
	private IManageScheduleBO manageScheduleBO;
	private IMobileSOActionsBO mobileSOActionsBO;
	private MobileTimeOnSiteValidator timeOnSiteValidator;
	private MobileTimeOnSiteMapper timeOnSiteMapper;
	private OFHelper ofHelper;
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
		String soId = (String) apiVO.getSOId();

		SecurityContext securityContext = getSecurityContextForVendor(apiVO
				.getProviderResourceId());
		String userName="";
		Integer entityId= 0;
		Integer roleId=0;
		String createdBy="";
		

		if(securityContext!=null){
			userName = securityContext.getUsername();
			entityId = securityContext.getVendBuyerResId();
			roleId = securityContext.getRoleId();
			LoginCredentialVO lvRoles = securityContext.getRoles();
			createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
		}
		
		try {
			// get the request object from apiVO
			MobileTimeOnSiteRequest mobileTimeonSiteRequest = (MobileTimeOnSiteRequest) apiVO
					.getRequestFromPostPut();

			
			
			// get the event type from the request
			String eventType = mobileTimeonSiteRequest.getEventType();
			Double latitude = mobileTimeonSiteRequest.getLatitude();
			Double longitude = mobileTimeonSiteRequest.getLongitude();
			
			Date currentDateTimeZone = new Date();
			
			// get the service order for the soId
			ServiceOrder so = manageScheduleBO.fetchServiceOrderForVisit(soId);

			if (null != so){

			// convert the currentDate to above timezone.
			if (null != so.getServiceLocationTimeZone() ){
				currentDateTimeZone = getDateinGMT(new Date(),
					so.getServiceLocationTimeZone());
			}
			// perform basic validation on the Time on Site request
			mobileTimeonSiteResponse = validateTimeOnSiteRequest(so, eventType,
					apiVO.getProviderResourceId().toString(), currentDateTimeZone,latitude,longitude);

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
				
				// map the SO Onsite Visit VO for persisting
				onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
						onsiteVisitVO, so, mobileTimeonSiteRequest,
						currentDateTimeZone);

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
			}
			// else if event type is 'DEPARTURE'
			else if (PublicMobileAPIConstant.EVENT_TYPE_DEPARTURE
					.equals(eventType)) {
				long departStart = System.currentTimeMillis();
				MobileSOOnsiteVisitVO latestArrival = manageScheduleBO
						.fetchLatestArrivalForSOId(so.getSoId());

				mobileTimeonSiteResponse = timeOnSiteValidator
						.validateLatestArrival(latestArrival,
								currentDateTimeZone);

				if (null != mobileTimeonSiteResponse) {
					mobileTimeonSiteResponse.setSoId(soId);
					return mobileTimeonSiteResponse;
				}

				if (null != latestArrival
						&& latestArrival.getDepartureDate() == null) {
					// map the SO Onsite Visit VO for persisting
					onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
							latestArrival, so, mobileTimeonSiteRequest,
							currentDateTimeZone);
					// Update SO_ONSITE_VISIT
					manageScheduleBO.updateSoOnsiteVisit(onsiteVisitVO);
				} else {
					// map the SO Onsite Visit VO for persisting
					onsiteVisitVO = timeOnSiteMapper.mapSOOnsiteVisitVO(
							onsiteVisitVO, so, mobileTimeonSiteRequest,
							currentDateTimeZone);
					// insert into SO_ONSITE_VISIT
					manageScheduleBO.insertSoOnsiteVisit(onsiteVisitVO);
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
					Results.getSuccess());
			//update substatus in so_hdr
			updateSOSubStatus(soId,eventType,roleId,userName,entityId,createdBy,so.getBuyerId());
		} 
		}catch (Exception exception) {
			logger.error("MobileTimeOnSiteService.execute(): exception due to "
					+ exception.getMessage());
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
			String eventType, String providerId, Date currentDate,Double latitude,Double longitude)
			throws BusinessServiceException {

		// validate SO status
		MobileTimeOnSiteResponse mobileTimeonSiteResponse = timeOnSiteValidator
				.validateSOStatus(so, eventType);

		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}

		// validate accepted date
		mobileTimeonSiteResponse = timeOnSiteValidator.validateAcceptedDate(so,
				eventType, currentDate);

		if (null != mobileTimeonSiteResponse) {
			return mobileTimeonSiteResponse;
		}

		//validate latitude and longitude
	
		mobileTimeonSiteResponse = timeOnSiteValidator.validateLatitudeAndLongitude(latitude,longitude); 

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
		// TODO Auto-generated method stub
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
	private static Date getDateinGMT(Date timeZoneDate, String TimeZone) {
		// if time zone for SO is null set to default time zone
		if (null == TimeZone || StringUtils.isBlank(TimeZone)) {
			TimeZone = PublicMobileAPIConstant.TIME_ON_SITE_DEFAULT_TIMEZONE;
		}

		Timestamp ts = new Timestamp(timeZoneDate.getTime());

		Date gmtDate = (Timestamp) TimeUtils.convertToGMT(ts, TimeZone);
		return gmtDate;
	}

	/**
	 * This method checks for the SO ID pattern using regex
	 * 
	 * @param so
	 * @return
	 */
	private boolean validateSORegexPattern(String so) {
		if (so == null)
			return false;
		Pattern p = Pattern.compile("\\d{3}-\\d{4}-\\d{4}-\\d{2}");
		Matcher m = p.matcher(so);
		boolean b = m.matches();
		return b;
	}

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
	
	
}