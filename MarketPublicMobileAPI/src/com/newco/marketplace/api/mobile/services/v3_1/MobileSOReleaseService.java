package com.newco.marketplace.api.mobile.services.v3_1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSORequest;
import com.newco.marketplace.api.mobile.beans.releaseServiceOrder.MobileReleaseSOResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


/**
 * This class would act as a Servicer class for Provider Release Service Order
 * 
 * @author Infosys
 * @version 1.0
 * @Date 2015/04/16
 */
@APIResponseClass(MobileReleaseSOResponse.class)
@APIRequestClass(MobileReleaseSORequest.class)
public class MobileSOReleaseService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(MobileSOReleaseService.class);

	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericMapper mobileMapper;
	private INewMobileGenericBO newMobileBO;
	private OFHelper ofHelper;
	private IActivityLogHelper activityLogHelper;
	private IServiceOrderBO serviceOrderBo;
	protected INotificationService notificationService;
	private IMobileGenericBO mobileGenericBO;
	
	/**
	 * 
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		MobileReleaseSORequest request;
		MobileReleaseSOResponse releaseResponse = new MobileReleaseSOResponse();
		MobileSOReleaseVO releaseVO=null;
		ProcessResponse processResponse = null;
		//SecurityContext securityContextForFirm=null;
		//SecurityContext securityContextForProvider=null;
		SecurityContext securityContext=null;
		try{
			request  = (MobileReleaseSORequest) apiVO.getRequestFromPostPut();
			securityContext = getSecurityContextForVendorAdmin(Integer.parseInt(apiVO.getProviderId()));
			//securityContextForProvider=getSecurityContextForVendor(Integer.parseInt(apiVO.getProviderId()));
			
			if(null != securityContext){
				releaseVO = mobileMapper.mapReleaseRequestToVO(request,apiVO.getSOId(),Integer.parseInt(apiVO.getProviderId()),apiVO.getProviderResourceId(),apiVO.getRoleId());
				releaseResponse=mobileValidator.validateReleaseRequest(releaseVO,releaseResponse);
				
				if(releaseResponse.getResults() == null){
					processResponse=releaseServiceOrder(releaseVO, securityContext);
					activityLogHelper.markBidsFromProviderAsExpired(apiVO.getSOId(), null);
					
					//if valid rc
					if (processResponse.getCode() == ServiceConstants.VALID_RC){
						InHomeRescheduleVO rescheuleVo= null;
						try {
							rescheuleVo= getServiceDateInfo(apiVO.getSOId());
						} catch (BusinessServiceException e) {
							LOGGER.error("Exception in getting service date informations for the service order"+ e);
						}
						
						sendInHomeNotification(rescheuleVo);
						//SL-21848
						/*Map<String, Object> param = new HashMap<String, Object>();
						param.put("soId", apiVO.getSOId());
						param.put("vendorId", Integer.parseInt(apiVO.getProviderId()));
						param.put("resourceID", Integer.parseInt(apiVO.getProviderId()));
						param.put("userName", securityContext.getUsername());
						param.put("acceptSource", "App");
						
						LOGGER.info("Map param valus to update the estimation to declined:"+param);
						
						mobileGenericBO.updateSOEstimateAsDeclined(param);*/
						
						
					}
					
					//else needed or not,if needed then error message should be there or not 
					//response will be created
					releaseResponse=mobileMapper.createReleaseResponse(releaseResponse,processResponse);
				}
			}
			else{
				Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
				releaseResponse.setResults(results);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("Exception Occured in MobileSOReleaseService-->execute()--> Exception-->", ex);
			Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			releaseResponse.setResults(results);
		}	
		return releaseResponse;
	}
    
	/**
     * @Description Method to release the service order if the request is valid
     * @param releaseRequestVO
     * @param securityContext
     * @return ProcessResponse
     * @throws BusinessServiceException 
     */
	private ProcessResponse releaseServiceOrder(MobileSOReleaseVO releaseRequestVO,SecurityContext securityContext)  {
		String name = "";
		Integer id = null;
		List<Integer> providerList = new ArrayList<Integer>();
		SOElementCollection soec = new SOElementCollection();
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		String reasonCodeDesc = null;
		try {
			releaseRequestVO = newMobileBO.fetchAssignmentTypeAndStatus(releaseRequestVO);
			reasonCodeDesc = newMobileBO.fetchReasonDesc(releaseRequestVO.getReason());
			releaseRequestVO.setReasonDesc(reasonCodeDesc);
		} catch (Exception e) {
			LOGGER.error("Error fetching assignment type and status", e);
		}
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		if(StringUtils.isNotBlank(releaseRequestVO.getComments())){
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,releaseRequestVO.getComments()));
		}
		else{
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_COMMENT,MPConstants.EMPTY_STRING));
		}
		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_REASON,releaseRequestVO.getReasonDesc()));
		
	    if(releaseRequestVO.getStatusId().equals(MPConstants.RELEASE_ACCEPTED_STATUS)){
	       parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,MPConstants.RELEASE_ACTION_ID_ACCEPTED));
	    }else if(releaseRequestVO.getStatusId().equals(MPConstants.RELEASE_ACTIVE_STATUS)){
	       parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,MPConstants.RELEASE_ACTION_ID_ACTIVE));
	    }else if(releaseRequestVO.getStatusId().equals(MPConstants.RELEASE_PROBLEM_STATUS)){
	       parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ACTION_ID,MPConstants.RELEASE_ACTION_ID_PROBLEM));
	    }
	    parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_ACTION_PERFORMED_IND,MPConstants.RELEASE_ACTION_PERFORMED_ID));
		Identification identity = OFMapper.createProviderIdentFromSecCtx(securityContext);
		
		//fetch the reschedule details
		String reschedulePeriod = rescheduleInfo(releaseRequestVO);
		
		if(StringUtils.isNotBlank(releaseRequestVO.getAssignmentType()) && ((MPConstants.RELEASE_FIRM.equals(releaseRequestVO.getAssignmentType())|| 
				(null != releaseRequestVO.getReleaseByFirmInd() && releaseRequestVO.getReleaseByFirmInd() == true)))){
			try{
				providerList = newMobileBO.fetchProviderList(releaseRequestVO.getSoId(),releaseRequestVO.getFirmId());
			}catch (Exception e) {
				LOGGER.error("Error fetching routed providers for firm", e);
			}
			//fetches the name
			try{
	    		name = serviceOrderBo.getVendorBusinessName(securityContext.getCompanyId());
	    	}catch (Exception e) {
	    		LOGGER.error("error fetching business name of firm", e);
			}            		
			//fetches the id
			id = securityContext.getCompanyId();
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_NAME,name));
        	parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ID,id.toString()));
        	parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_CHANGED_SERVICE_DATE_INFO, reschedulePeriod));
			soec = createSOElementForProviderList(providerList,releaseRequestVO);
			response = ofHelper.runOrderFulfillmentProcess(releaseRequestVO.getSoId(), SignalType.PROVIDER_FIRM_RELEASE_ORDER, soec, identity, parameters);
		}
		else{
			name = releaseRequestVO.getReleaseByName();
    		id = releaseRequestVO.getReleaseResourceId();
    		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_ID,id.toString()));
			parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_RELEASE_NAME,name));
        	parameters.add(new Parameter(OrderfulfillmentConstants.RELEASE_CHANGED_SERVICE_DATE_INFO, reschedulePeriod));
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = createReleaseElement((long)releaseRequestVO.getReleaseResourceId(), releaseRequestVO.getComments(),Integer.parseInt(releaseRequestVO.getReason()));
			response = ofHelper.runOrderFulfillmentProcess(releaseRequestVO.getSoId(), SignalType.PROVIDER_RELEASE_ORDER, routedProvider,identity,parameters);
		}
		ProcessResponse processResponse = OFMapper.mapProcessResponse(response);
		return processResponse;
	}

	
	/**
	 * @Description Method to create a collection of elements for the providerList if SO is released at firm level
	 * @param providerList
	 * @param releaseRequestVO
	 * @return
	 */
	private SOElementCollection createSOElementForProviderList(List<Integer> providerList,MobileSOReleaseVO releaseRequestVO){
		SOElementCollection soElementCollection = new SOElementCollection();
		for(Integer providerId :providerList){
			Long id = (long)providerId;
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
			routedProvider.setProviderResourceId(id);
			routedProvider.setProviderResponse(ProviderResponseType.RELEASED_BY_FIRM);
			if(releaseRequestVO.getReason().equals("-2")){
				routedProvider.setProviderRespReasonId(null);
			} 
			else {
				routedProvider.setProviderRespReasonId(Integer.parseInt(releaseRequestVO.getReason()));
			}
			routedProvider.setProviderRespComment(releaseRequestVO.getComments());
			Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
			routedProvider.setProviderRespDate(providerRespDate);
			soElementCollection.addElement(routedProvider);
		}
		return soElementCollection;
	}
	
	/**
	 * @Description Method to create an element for the provider if SO is released at resource level
	 * @param resourceId
	 * @param providerComment
	 * @param reasonCode
	 * @return RoutedProvider
	 */
	private com.servicelive.orderfulfillment.domain.RoutedProvider createReleaseElement(Long resourceId, String providerComment,Integer reasonCode){
		com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
		routedProvider.setProviderResourceId(resourceId);
		routedProvider.setProviderResponse(ProviderResponseType.RELEASED);
		if(reasonCode.equals(-2)){
			routedProvider.setProviderRespReasonId(null);
		}
		else {
			routedProvider.setProviderRespReasonId(reasonCode);
		}
		routedProvider.setProviderRespComment(providerComment);
		Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		routedProvider.setProviderRespDate(providerRespDate);
		return routedProvider;
	}
	
	private void sendInHomeNotification(InHomeRescheduleVO rescheuleVO){
		boolean isServicedateUpdated=false;
		isServicedateUpdated = compareServiceDateInfo(rescheuleVO);
		if(isServicedateUpdated){
			rescheuleVO.setSoId(rescheuleVO.getSoId());
			rescheuleVO.setBuyerId(rescheuleVO.getBuyerId());
			try {
				insertNotificationForInHomeReschedule(rescheuleVO,rescheuleVO.getBuyerId());
			} catch (Exception e) {
				LOGGER.error("Exception in Notifying NPS for Reschedule for inhome order"+e);
			}
		}
	}
	
	/**@Description:This method will be called before and after calling signal for releasing service order
	 * to check service time window is updated,if yes notify NPS incase of inhome orders
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public InHomeRescheduleVO getServiceDateInfo(String soId) throws BusinessServiceException{
		InHomeRescheduleVO serviceDateInfo = new InHomeRescheduleVO();
		serviceDateInfo.setSoId(soId);
		try{
			serviceDateInfo = notificationService.getSoDetailsForReschedule(serviceDateInfo);
		}catch(Exception e){
			LOGGER.error("Exception in getting service date informations for the service order"+ e);
			throw new BusinessServiceException(e.getMessage());
		}
		return serviceDateInfo;
	}
	
	/**@Description:Validate an existing reschedule request placed by buyer is exists or not
	 * @param rescheuleVo
	 * @return
	 */
    private boolean compareServiceDateInfo(InHomeRescheduleVO rescheuleVo) {
		boolean isUpdated=false;
		if(null!= rescheuleVo && null != rescheuleVo.getBuyerRescheduleServiceDate1()){
			isUpdated=true;
			rescheuleVo.setReleasIndicator(1);
		}
		return isUpdated;
	}
    
    /**@Description:This will insert the details into outboundnotification table for inhome
	 * @param rescheuleVoAfter 
	 * @throws Exception 
	 * 
	 */
	private void insertNotificationForInHomeReschedule(InHomeRescheduleVO rescheuleVo,Integer buyerId) throws Exception{
		boolean isEligibleForNPSNotification=false;
		isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(buyerId,rescheuleVo.getSoId());
		if(isEligibleForNPSNotification){
			notificationService.insertNotification(rescheuleVo);
			//R12.0 SL-20408: invoke Service Operations API on provider release after buyer reschedule
			rescheuleVo.setReleaseFlag(true);
			//SLT-4048:Flag to decide whether to send  Reschedule event to NPS through Platform
			if(BuyerOutBoundConstants.ON.equalsIgnoreCase(BuyerOutBoundConstants.INHOME_OUTBOUND_STOP_RESCHEDULE_EVENT_FLAG_INPLATFORM)){
			notificationService.insertNotificationServiceOperationsAPI(rescheuleVo.getSoId(),rescheuleVo);
			}
		}
	}
	
	private String rescheduleInfo(MobileSOReleaseVO releaseRequestVO) {
		RescheduleVO rescheduleVO = null;
        HashMap<String, Object> rescheduleStartDate = null;
		HashMap<String, Object> rescheduleEndDate = null;
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		StringBuffer reschedulePeriod = new StringBuffer();
		
        try{
        	rescheduleVO = serviceOrderBo.getBuyerRescheduleInfo(releaseRequestVO.getSoId());
    	
        	if(null != rescheduleVO && null != rescheduleVO.getRescheduleServiceStartDate()){
        		if (null != rescheduleVO.getRescheduleServiceTime1()) {
        			rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVO.getRescheduleServiceStartDate(),
        					rescheduleVO.getRescheduleServiceTime1(), rescheduleVO.getServiceLocnTimeZone());
        			if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
        				reschedulePeriod.append("<br/>Service date has been updated");
        				if(null != rescheduleVO.getRescheduleServiceEndDate() && null != rescheduleVO.getRescheduleServiceTime2()){
        					reschedulePeriod.append(", from");
        				}
        				reschedulePeriod.append(" " + formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
        				reschedulePeriod.append(" ");
        				reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
        			}
        		}
        		if(null != rescheduleVO.getRescheduleServiceEndDate() && null != rescheduleVO.getRescheduleServiceTime2()){
        			rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVO.getRescheduleServiceEndDate(),
        					rescheduleVO.getRescheduleServiceTime2(), rescheduleVO.getServiceLocnTimeZone());
        			if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
        				reschedulePeriod.append(" to ");
        				reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
        				reschedulePeriod.append(" ");
        				reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
        			}
        		}
        		if(null != rescheduleStartDate){
        			reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+
        					(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, rescheduleVO.getServiceLocnTimeZone()));
        		}
        	}
        
        }catch (Exception ee) {
        	LOGGER.error("error fetching routed providers for firm", ee);
		}
		return reschedulePeriod.toString();
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			LOGGER.error("exception in formatDate()"+ e);
		}
		return formattedDate;
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			LOGGER.info("Parse Exception SODetailsDelegateImpl.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+getDSTTimezone(timeZone)+")";
	    }
	    return "("+getStandardTimezone(timeZone)+")";
   }
	
	public String getDSTTimezone(String timeZone) {
		
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EDT";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "ADT";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CDT";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MDT";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PDT";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HADT";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CEDT";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKDT";
		}
		return timeZone;
	}

	public String getStandardTimezone(String timeZone) {
		if ("EST5EDT".equals(timeZone)) {
			timeZone = "EST";
		}
		if ("AST4ADT".equals(timeZone)) {
			timeZone = "AST";
		}
		if ("CST6CDT".equals(timeZone)) {
			timeZone = "CST";
		}
		if ("MST7MDT".equals(timeZone)) {
			timeZone = "MST";
		}
		if ("PST8PDT".equals(timeZone)) {
			timeZone = "PST";
		}
		if ("HST".equals(timeZone)) {
			timeZone = "HAST";
		}
		if ("Etc/GMT+1".equals(timeZone)) {
			timeZone = "CET";
		}
		if ("AST".equals(timeZone)) {
			timeZone = "AKST";
		}
		if ("Etc/GMT-9".equals(timeZone)) {
			timeZone = "PST-7";
		}
		if ("MIT".equals(timeZone)) {
			timeZone = "PST-3";
		}
		if ("NST".equals(timeZone)) {
			timeZone = "PST-4";
		}
		if ("Etc/GMT-10".equals(timeZone)) {
			timeZone = "PST-6";
		}
		if ("Etc/GMT-11".equals(timeZone)) {
			timeZone = "PST-5";
		}
		return timeZone;
	}

	public NewMobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}

	public void setMobileValidator(NewMobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}

	public NewMobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}

	public void setMobileMapper(NewMobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}

	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}

	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}

	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}

	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	
	

}