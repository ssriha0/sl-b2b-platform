/**
 * 
 */
package com.newco.marketplace.business.businessImpl.systemgeneratedemail;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.business.iBusiness.systemgeneratedemail.ISystemGeneratedBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.survey.BuyerSurveyConfigVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ActionVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.BuyerEventVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailPropertyKeyEnum;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventTemplateVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOContactDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOLoggingVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOScheduleDetailsVO;
import com.newco.marketplace.exception.EmailNotFoundException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.dao.systemgeneratedemail.ISystemGeneratedEmailDao;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.SurveyCryptographyUtil;
import com.newco.marketplace.util.constants.SystemGeneratedEmailConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.servicelive.common.exception.DataServiceException;

/**
 * @author lprabha
 *
 */
public class SystemGeneratedEmailBOImpl implements ISystemGeneratedBO {
	
	private static final Logger logger = Logger.getLogger(SystemGeneratedEmailBOImpl.class.getName());
	private ISystemGeneratedEmailDao iSystemGeneratedEmailDao;
	private AlertDaoImpl alertDao;
	private EmailTemplateBOImpl emailTemplateBOImpl;
	private SurveyCryptographyUtil surveyCryptographyUtil;
	private ExtendedSurveyDAO extendedSurveyDAO;
	private StringBuffer recordsFailed = new StringBuffer();

	// Buyer specific data
	Set<Integer> buyerIds = new HashSet<Integer>();
	Set<Integer> actionIds = new HashSet<Integer>();
	// buyerId, buyerEventIds
	Map<Integer, Set<Integer>> buyerEventIds = new HashMap<Integer, Set<Integer>>();
	Set<Integer> templateIds = new HashSet<Integer>();
	// event_id, actions
	Map<Integer, List<ActionVO>> eventActions = new HashMap<Integer, List<ActionVO>>();

	// buyer_id, buyer_events
	Map<Integer, List<BuyerEventVO>> buyersEvents = new HashMap<Integer, List<BuyerEventVO>>();
	// buyer_event_id, template_id
	Map<Integer, Integer> buyerEventTemplates = new HashMap<Integer, Integer>();
	// buyer_id, buyer_email_data
	Map<Integer, List<EmailDataVO>> buyerEmailData = new HashMap<Integer, List<EmailDataVO>>();
	// buyer_event_id, buyer_event_email_data
	Map<Integer, List<EmailDataVO>> buyerEventEmailData = new HashMap<Integer, List<EmailDataVO>>();
	// template_id, template_Parameters
	Map<Integer, List<EmailDataVO>> templateParameters = new HashMap<Integer, List<EmailDataVO>>();
	// buyer_id, buyerSurveyType
	Map<Integer, String> buyerSurveyType = new HashMap<Integer, String>();
	Map<Integer, List<Integer>> buyerSurveyId=new HashMap<Integer, List<Integer>>();

	@Override
	public void processSystemGeneratedEmail() throws Exception {
		logger.info("processSystemGeneratedEmail --> starts");
		
		// Fetch CounterValue - data processed till now
		Long counterValue = getCounterValue();
		
		// Fetch MaxValue - data to which processed during this batch run.
		Long maxValue = getMaxValue();
		
		if(counterValue < maxValue){
			// Fetch buyerEvents buyerEmailData buyerEventEmailData
			fetchBuyerSpecificData();

			if (null != buyerIds && !buyerIds.isEmpty()) {
				logger.info("\n ProcessSystemGeneratedEmail for Buyers : " + buyerIds.toString()+" \n For data from : "+counterValue +" to : "+maxValue);
				// Fetch SO Details
				List<SOLoggingVO> soLoggingVOs = getSOLoggingDetails(buyerIds, actionIds, counterValue, maxValue);
				
				if (null != soLoggingVOs && !soLoggingVOs.isEmpty()) {
					List<AlertTask> alertTasks = new ArrayList<AlertTask>();
					
					// Filter ServiceOrderDetails based on configured Buyer Events and trigger Email
					for (SOLoggingVO serviceOrderDetail : soLoggingVOs) {
						if (null != serviceOrderDetail.getBuyerId()) {
							List<BuyerEventVO> buyerEvents = buyersEvents.get(serviceOrderDetail.getBuyerId());
							if (null != buyerEvents && !buyerEvents.isEmpty()) {
								buyerEvents: for (BuyerEventVO buyerEvent : buyerEvents) {
									EventVO event = buyerEvent.getEventVO();
									if (null != event) {
										List<ActionVO> buyerActions = event.getActions();
										if (null != buyerActions && !buyerActions.isEmpty()) {
											for (ActionVO buyerAction : buyerActions) {
												if (buyerAction.getActionId().equals(serviceOrderDetail.getActionId())) {
													AlertTask alertTask = null;
													// For reschedule accept with action ID 19 check the change comment 
													// as this is used by accept/reject/cancel reschedule
													if (buyerAction.getActionId().equals(19)
															&& (null == serviceOrderDetail.getLoggingChangeComment()
																	|| !serviceOrderDetail.getLoggingChangeComment()
																			.toLowerCase().contains("reschedule request was accepted"))) {
														break;
													}
													// Actions with status and substatus
													if ((null != buyerAction.getWfStateId()
															&& null != buyerAction.getSoSubstatusId())) {
														if (null != serviceOrderDetail.getSoSubStatusId()
																&& serviceOrderDetail.getSoSubStatusId()
																		.equals(buyerAction.getSoSubstatusId())
																&& serviceOrderDetail.getWfStateId()
																		.equals(buyerAction.getWfStateId())) {
															alertTask = populateAlertTaskData(serviceOrderDetail,
																	buyerEvent.getBuyerEventId(), event.getEventName());
															alertTasks.add(alertTask);
															break buyerEvents;
														}
														// Actions with status
													} else if (null != buyerAction.getWfStateId()) {
														if (null != serviceOrderDetail.getWfStateId() && serviceOrderDetail
																.getWfStateId().equals(buyerAction.getWfStateId())) {
															alertTask = populateAlertTaskData(serviceOrderDetail,
																	buyerEvent.getBuyerEventId(), event.getEventName());
															alertTasks.add(alertTask);
															break buyerEvents;
														}
														// Actions without status and sub status
													} else {
														alertTask = populateAlertTaskData(serviceOrderDetail,
																buyerEvent.getBuyerEventId(), event.getEventName());
														alertTasks.add(alertTask);
														break buyerEvents;
													}
												}
											}
										}
									}
								}
							}
						}
					}
					if (null != alertTasks && !alertTasks.isEmpty()) {
						addAlertListToQueue(alertTasks);
					}
				}
			}
			updateSystemGeneratedEmailCounter(maxValue);
			
			// SLT-2329
			if(recordsFailed.length() > 0){
				sendSystemGeneratedEmailFailureAlert(SystemGeneratedEmailConstants.SGE_BATCH, recordsFailed);
			}
			clearData();
		}
		logger.info("processSystemGeneratedEmail --> ends");
	}

	/**
	 * Map Buyer Events - buyerEvents and Buyer Event templates -
	 * buyerEventTemplates from EventTemplateVO
	 * 
	 * @throws BusinessServiceException
	 * 
	 */
	private void fetchBuyerSpecificData() throws BusinessServiceException {
		logger.info("fetchBuyerSpecificData --> starts");
		try {
			List<EventTemplateVO> buyerEventTemplateVOs = getBuyerEventTemplateDetails();
			if (null != buyerEventTemplateVOs && !buyerEventTemplateVOs.isEmpty()) {
				loadEventActions(buyerEventTemplateVOs);
				loadBuyerEventDetails(buyerEventTemplateVOs);
				loadTemplateParameters(templateIds);
			}
		} catch (BusinessServiceException e) {
			logger.error("Error on fetching Buyer Event and Buyer Event Template Details from db " + e);
			throw e;
		}
		logger.info("fetchBuyerSpecificData --> ends");
	}

	@Override
	public void processReminderEmail() throws Exception {

		// Fetch Buyer details enrolled for Reminder service
		fetchBuyerSpecificDataforReminderService();
		if(null != buyerIds && !buyerIds.isEmpty()){
			logger.info("processReminderEmail for Buyers : " + buyerIds.toString());
			// Fetch ServiceOrder Details due the next day
			List<SOLoggingVO> soLoggingVOs = getSODetailsForNextDayService(buyerIds);
			
			if (null != soLoggingVOs && !soLoggingVOs.isEmpty()) {
				List<AlertTask> alertTasks = new ArrayList<AlertTask>();
				Set<String> processedOrders = new HashSet<String>();
				
				// Filter ServiceOrderDetails based on configured Buyer Events and trigger Email
				for (SOLoggingVO serviceOrderDetail : soLoggingVOs) {
					if(null != serviceOrderDetail.getBuyerId() 
							&& null != buyersEvents.get(serviceOrderDetail.getBuyerId()) 
							&& !buyersEvents.get(serviceOrderDetail.getBuyerId()).isEmpty()){
						processedOrders.add(serviceOrderDetail.getServiceOrderNo());
						AlertTask alertTask = populateAlertTaskData(serviceOrderDetail,
								buyersEvents.get(serviceOrderDetail.getBuyerId()).get(0).getBuyerEventId(), 
								SystemGeneratedEmailConstants.REMINDER_SERVICE);
						alertTasks.add(alertTask);
					}
				}
				if (null != alertTasks && !alertTasks.isEmpty()) {
					addAlertListToQueue(alertTasks);
					logger.info("processReminderEmail for Orders : " + processedOrders.toString());
				}
			}
		}	
		
		// SLT-2329
		if(recordsFailed.length() > 0){
			sendSystemGeneratedEmailFailureAlert(SystemGeneratedEmailConstants.REMINDER_BATCH, recordsFailed);
		}
		clearData();
	}

	/**
	 * Method to fetch the service order where service date is within 24hrs
	 * @param Set<Integer> buyerIds
	 * @return List<SOLoggingVO>
	 */
	private List<SOLoggingVO> getSODetailsForNextDayService(
			Set<Integer> buyerIds) throws BusinessServiceException {
		logger.info("Start getSODetailsForNextDayService");
		try {
			return iSystemGeneratedEmailDao.getSOIdsForNextDayService(buyerIds);
		} catch (DataServiceException e) {
			logger.error("Error in fetching SODetails For NextDay Service :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * 
	 * @throws BusinessServiceException
	 */
	private void fetchBuyerSpecificDataforReminderService() throws BusinessServiceException {
		logger.info("fetchBuyerSpecificDataforReminderService --> starts");
		try {
			List<EventTemplateVO> buyerEventTemplateVOs = getBuyerEventTemplateDetailsForReminderService();
			if (null != buyerEventTemplateVOs && !buyerEventTemplateVOs.isEmpty()) {
				loadBuyerEventDetails(buyerEventTemplateVOs);
				loadTemplateParameters(templateIds);
			}
		} catch (BusinessServiceException e) {
			logger.error("Error on fetchBuyerSpecificDataforReminderService " + e);
			throw e;
		}

		logger.info("fetchBuyerSpecificDataforReminderService --> ends");
	}

	/**
	 * Load eventActions
	 * 
	 * @param buyerEventTemplateVOs
	 */
	private void loadEventActions(List<EventTemplateVO> buyerEventTemplateVOs) {
		for (EventTemplateVO eventTemplateVO : buyerEventTemplateVOs) {
			actionIds.add(eventTemplateVO.getActionId());
			ActionVO action = new ActionVO();
			action.setActionId(eventTemplateVO.getActionId());
			action.setWfStateId(eventTemplateVO.getWfStateId());
			action.setSoSubstatusId(eventTemplateVO.getSoSubStatusId());
			List<ActionVO> actionVOs = eventActions.get(eventTemplateVO.getEventId());
			if (null == actionVOs)
				actionVOs = new ArrayList<ActionVO>();
			actionVOs.add(action);
			eventActions.put(eventTemplateVO.getEventId(), actionVOs);
		}
	}

	/**
	 * Load buyersEvents buyerEventTemplates
	 * 
	 * @param buyerEventTemplateVOs
	 */
	private void loadBuyerEventDetails(List<EventTemplateVO> buyerEventTemplateVOs) {
		for (EventTemplateVO eventTemplateVO : buyerEventTemplateVOs) {
			buyerIds.add(eventTemplateVO.getBuyerId());
			if (null != buyerEventIds && null != buyerEventIds.get(eventTemplateVO.getBuyerId())
					&& buyerEventIds.get(eventTemplateVO.getBuyerId()).contains(eventTemplateVO.getBuyerEventId()))
				continue;
			Set<Integer> buyerEventIDs = buyerEventIds.get(eventTemplateVO.getBuyerId());
			if (null == buyerEventIDs)
				buyerEventIDs = new HashSet<Integer>();
			buyerEventIDs.add(eventTemplateVO.getBuyerEventId());
			buyerEventIds.put(eventTemplateVO.getBuyerId(), buyerEventIDs);
			templateIds.add(eventTemplateVO.getTemplateId());
			BuyerEventVO buyerEventVO = new BuyerEventVO();
			buyerEventVO.setBuyerEventId(eventTemplateVO.getBuyerEventId());
			EventVO eventVO = new EventVO();
			eventVO.setEventId(eventTemplateVO.getEventId());
			eventVO.setEventName(eventTemplateVO.getEventName());
			eventVO.setActions(eventActions.get(eventTemplateVO.getEventId()));
			buyerEventVO.setEventVO(eventVO);
			List<BuyerEventVO> buyerEventsVO = buyersEvents.get(eventTemplateVO.getBuyerId());
			if (null == buyerEventsVO)
				buyerEventsVO = new ArrayList<BuyerEventVO>();
			buyerEventsVO.add(buyerEventVO);
			buyersEvents.put(eventTemplateVO.getBuyerId(), buyerEventsVO);
			buyerEventTemplates.put(eventTemplateVO.getBuyerEventId(), eventTemplateVO.getTemplateId());
		}
	}

	/**
	 * Load TemplateParameters
	 * 
	 * @param templateIDs
	 * @throws BusinessServiceException
	 */
	private void loadTemplateParameters(Set<Integer> templateIDs) throws BusinessServiceException {
		try {
			List<EmailDataVO> buyerEventEmailData = getEmailParameterForTemplate(templateIDs);
			for (EmailDataVO emailData : buyerEventEmailData) {
				List<EmailDataVO> emailDataVOs = templateParameters.get(emailData.getTemplateId());
				if (null == emailDataVOs)
					emailDataVOs = new ArrayList<EmailDataVO>();
				emailDataVOs.add(emailData);
				templateParameters.put(emailData.getTemplateId(), emailDataVOs);
			}
		} catch (BusinessServiceException e) {
			logger.error("Error on fetching Buyer Event Email data Details from db " + e);
			throw e;
		}
	}

	/**
	 * Populate alert task
	 * 
	 * @param serviceOrderDetail
	 * @param buyerEventId
	 * @param eventName
	 * @return
	 * @throws Exception
	 */
	private AlertTask populateAlertTaskData(SOLoggingVO serviceOrderDetail, Integer buyerEventId, String eventName)
			throws Exception {
		try{
			AlertTask alertTask = null;

			Integer templateId = buyerEventTemplates.get(buyerEventId);

			if (null != templateId) {
				List<EmailDataVO> emailData = new ArrayList<EmailDataVO>();

				// Populate Buyer specific Template data
				List<EmailDataVO> buyerEmailData = fetchBuyerEmailData(serviceOrderDetail.getBuyerId());
				if (!CollectionUtils.isEmpty(buyerEmailData)) {
					emailData = buyerEmailData;
				}else{
					logger.error("Header Footer Email Configurations are not available for Buyer : " + serviceOrderDetail.getBuyerId());
				}
				
				// Populate Buyer Event specific Template data
				List<EmailDataVO> buyerEventEmailData = fetchBuyerEventEmailData(buyerEventId);
				if (!CollectionUtils.isEmpty(buyerEventEmailData)) {
					emailData.addAll(buyerEventEmailData);
				}else{
					logger.error("Event specific configurations are not available for Buyer : " + serviceOrderDetail.getBuyerId() 
							+ " event : " + eventName);
				}
				
				// Populate email parameter data
				List<EmailDataVO> emailtemplateParameters = templateParameters.get(templateId);
				if (!CollectionUtils.isEmpty(emailtemplateParameters)) {
					List<EmailDataVO> emailDataForEmailParameter = populateEmailDataForEmailParameter(
							emailtemplateParameters, serviceOrderDetail);
					if (!CollectionUtils.isEmpty(emailDataForEmailParameter))
						emailData.addAll(emailDataForEmailParameter);
				}else{
					logger.error(
							"No template parameters for event : " + eventName);
				}
				
				// Populate alert task data
				HashMap<String, Object> vContext = new AOPHashMap();
				String msg = "Populated data for ID : "+serviceOrderDetail.getSoLoggingId()+" event : "+eventName;
				for (EmailDataVO emailDatum : emailData) {
					if (emailDatum.getParamKey().equals(SystemGeneratedEmailConstants.EMAIL)) {
						vContext.put(AOPConstants.AOP_USER_EMAIL, emailDatum.getParamValue());
						
					} else {
						vContext.put(emailDatum.getParamKey(), emailDatum.getParamValue());
					}
				}
				vContext.put(AOPConstants.AOP_TEMPLATE_ID, templateId);
				vContext.put(SystemGeneratedEmailConstants.MODIFIED_BY, SystemGeneratedEmailConstants.SGE);
				alertTask = emailTemplateBOImpl.getEmailAlertTask(vContext);
				msg+= " for : "+alertTask.getAlertTo();
				logger.info(msg);
			} else {
				logger.error("Template not configured for buyer : "  
						+serviceOrderDetail.getBuyerId() + " event : " + eventName);
				throw new Exception();
			}
			return alertTask;
		} catch (EmailNotFoundException e) {
			logger.error("Skipping Record due to no email info for so_logging_id : " 
					+ serviceOrderDetail.getSoLoggingId(), e);
		}
		catch (Exception e) {
			recordsFailed.append("<li>").append(serviceOrderDetail.getSoLoggingId())
				.append("<br/>").append(e).append("</li>");
			logger.error("Skipping Record due to error while processing record with so_logging_id : "
					+ serviceOrderDetail.getSoLoggingId(), e);
		}
		return null;
	}

	/**
	 * Fetch populated buyer email data or fetch from db
	 * 
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	private List<EmailDataVO> fetchBuyerEmailData(Integer buyerId) throws BusinessServiceException {
		List<EmailDataVO> emaildata = buyerEmailData.get(buyerId);
		if (null == emaildata)
			loadBuyerEmailData(buyerId);
		return buyerEmailData.get(buyerId);
	}

	/**
	 * Load BuyerEmailData for a buyer
	 * 
	 * @param buyerIDs
	 * @throws BusinessServiceException
	 */
	private void loadBuyerEmailData(Integer buyerId) throws BusinessServiceException {
		try{
			boolean setDimension = false;
			List<EmailDataVO> buyerEmailDataVOs = getEmailDataForBuyer(
					new HashSet<Integer>(Arrays.asList(buyerId)));
			if(null != buyerEmailDataVOs && !buyerEmailDataVOs.isEmpty()){
				for (EmailDataVO emailData : buyerEmailDataVOs) {
					// Populate image logo url
					if (emailData.getParamKey().equals(EmailPropertyKeyEnum.BUYERLOGO.getKey())) {
						emailData.setParamValue(
								getLink(SystemGeneratedEmailConstants.LOGO_URL) + emailData.getParamValue());
						// Populate image dimensions from database configuration
						setDimension = true;
						break;
					}
				}
				if (setDimension)
					populateImageDimensions(buyerEmailDataVOs);
			
				buyerEmailData.put(buyerId, buyerEmailDataVOs);
			}	
		} catch (BusinessServiceException e) {
			logger.error("Error on fetching Buyer Email data Details from db " + e);
			throw e;
		}
	}

	/**
	 * Populate image height and width details from database configuration.
	 * 
	 * @param buyerEmailDataVOs
	 */
	private void populateImageDimensions(List<EmailDataVO> buyerEmailDataVOs) {
		try{
			EmailDataVO imageHeight = new EmailDataVO();
			imageHeight.setParamKey(SystemGeneratedEmailConstants.MAX_HEIGHT);
			imageHeight.setParamValue(PropertiesUtils.getPropertyValue(SystemGeneratedEmailConstants.EMAIL_LOGO_MAX_HEIGHT));
			buyerEmailDataVOs.add(imageHeight);
			EmailDataVO imageWidth = new EmailDataVO();
			imageWidth.setParamKey(SystemGeneratedEmailConstants.MAX_WIDTH);
			imageWidth.setParamValue(PropertiesUtils.getPropertyValue(SystemGeneratedEmailConstants.EMAIL_LOGO_MAX_WIDTH));
			buyerEmailDataVOs.add(imageWidth);
		}catch (Exception e) {
			logger.error("Error populating ImageDimensions : " + e);
			throw e;
		}
		
	}

	/**
	 * Fetch populated buyer event email data or fetch from db
	 * 
	 * @param buyerEventId
	 * @return
	 * @throws BusinessServiceException
	 */
	private List<EmailDataVO> fetchBuyerEventEmailData(Integer buyerEventId) throws BusinessServiceException {
		List<EmailDataVO> emaildata = buyerEventEmailData.get(buyerEventId);
		if (null == emaildata)
			loadBuyerEventEmailData(new HashSet<Integer>(Arrays.asList(buyerEventId)));
		return buyerEventEmailData.get(buyerEventId);
	}

	/**
	 * Load BuyerEventEmailData
	 * 
	 * @param buyerEventIDs
	 * @throws BusinessServiceException
	 */
	private void loadBuyerEventEmailData(Set<Integer> buyerEventIDs) throws BusinessServiceException {
		try {
			if (null != buyerEventIDs && !buyerEventIDs.isEmpty()) {
				List<EmailDataVO> buyerEventEmailDataVOs = getEmailDataForBuyerEvent(buyerEventIDs);
				for (EmailDataVO emailData : buyerEventEmailDataVOs) {
					List<EmailDataVO> emailDataVOs = buyerEventEmailData.get(emailData.getBuyerEventId());
					if (null == emailDataVOs)
						emailDataVOs = new ArrayList<EmailDataVO>();
					emailDataVOs.add(emailData);
					buyerEventEmailData.put(emailData.getBuyerEventId(), emailDataVOs);
				}
			}
		} catch (BusinessServiceException e) {
			logger.error("Error on fetching Buyer Event Email data Details from db " + e);
			throw e;
		}
	}
	
	private List<EmailDataVO> populateAppoinmentDate(SOLoggingVO serviceOrderDetails){
		try{
			List<EmailDataVO> emailData = new ArrayList<>();
			HashMap<String, Object> startDateTimeMap=TimeUtils.convertGMTToGivenTimeZone(serviceOrderDetails.getServiceDate1(), serviceOrderDetails.getServiceDateStartTime(),
					serviceOrderDetails.getTimeZone(), "MMMM d, yyyy");
			String date = (String)startDateTimeMap.get(OrderConstants.GMT_DATE);
			String time = (String)startDateTimeMap.get(OrderConstants.GMT_TIME);
			if(serviceOrderDetails.getServiceDate2()!=null 
					&& !serviceOrderDetails.getServiceDateType().equals(SystemGeneratedEmailConstants.SCHEDULE_TYPE_1)){
				HashMap<String, Object> endDateTimeMap=TimeUtils.convertGMTToGivenTimeZone(serviceOrderDetails.getServiceDate2(), serviceOrderDetails.getServiceDateEndTime(),
						serviceOrderDetails.getTimeZone(), "MMMM d, yyyy");
				date = date.equals((String)endDateTimeMap.get(OrderConstants.GMT_DATE))?date:date +" - "+(String)endDateTimeMap.get(OrderConstants.GMT_DATE);
				time = time +" - "+(String)endDateTimeMap.get(OrderConstants.GMT_TIME)+" "+(String)endDateTimeMap.get(OrderConstants.TIME_ZONE);
			}else{
				time= time+" "+(String)startDateTimeMap.get(OrderConstants.TIME_ZONE);
			}
			emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.DATE_SERVICE, date));
			emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.TIME_SERVICE, time));
			return emailData;
		}catch (Exception e) {
			logger.error("Error populating AppoinmentDate : " + e);
			throw e;
		}
	}
	
	private List<EmailDataVO> populateTimeSlots(SOLoggingVO serviceOrderDetails) throws BusinessServiceException {
		try{
			List<EmailDataVO> emailData = new ArrayList<>();
			switch (serviceOrderDetails.getServiceDateType()) {
			case SystemGeneratedEmailConstants.SCHEDULE_TYPE_1:
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.TIME_SLOTS0,
						populateTimeSlot(serviceOrderDetails.getServiceDate1(),
								serviceOrderDetails.getServiceDateStartTime(),null,
								null, serviceOrderDetails.getTimeZone())));
			break;
			case SystemGeneratedEmailConstants.SCHEDULE_TYPE_2:
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.TIME_SLOTS0,
						populateTimeSlot(serviceOrderDetails.getServiceDate1(),
								serviceOrderDetails.getServiceDateStartTime(), serviceOrderDetails.getServiceDate2(),
								serviceOrderDetails.getServiceDateEndTime(), serviceOrderDetails.getTimeZone())));
			break;
			case SystemGeneratedEmailConstants.SCHEDULE_TYPE_3:
				List<SOScheduleDetailsVO> sOScheduleDetailsVO = getServiceOrderScheduleDetails(
						serviceOrderDetails.getServiceOrderNo());
				for (int i = 0; i < sOScheduleDetailsVO.size(); i++) {
					SOScheduleDetailsVO sOScheduleDetailVO = sOScheduleDetailsVO.get(i);
					emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.TIME_SLOTS + i,
							populateTimeSlot(sOScheduleDetailVO.getServiceStartDate(),
									sOScheduleDetailVO.getServiceStartTime(), sOScheduleDetailVO.getServiceEndDate(),
									sOScheduleDetailVO.getServiceEndTime(), serviceOrderDetails.getTimeZone())));
				}
			break;
			default:
				break;
			} 
			return emailData;
		}catch (Exception e) {
			logger.error("Error populating TimeSlots : " + e);
			throw e;
		}
	}
	
	private String populateTimeSlot(Timestamp serviceDate1, String serviceDateStartTime,
			Timestamp serviceDate2, String serviceDateEndTime, String timeZone) throws BusinessServiceException {
		try{
			StringBuffer result = new StringBuffer();
			HashMap<String, Object> startDateTimeMap=TimeUtils.convertGMTToGivenTimeZone(serviceDate1, serviceDateStartTime,
					timeZone, "EEEE, MMMM d, yyyy");
			String date = (String)startDateTimeMap.get(OrderConstants.GMT_DATE);
			String time = (String)startDateTimeMap.get(OrderConstants.GMT_TIME);
			result.append(date).append(" ").append(time);
			if(serviceDate2!=null ){
				HashMap<String, Object> endDateTimeMap=TimeUtils.convertGMTToGivenTimeZone(serviceDate2, serviceDateEndTime,
						timeZone, "EEEE, MMMM d, yyyy");
				if(date.equals((String)endDateTimeMap.get(OrderConstants.GMT_DATE))){
					date = date.equals((String)endDateTimeMap.get(OrderConstants.GMT_DATE))?date:date +" - "+(String)endDateTimeMap.get(OrderConstants.GMT_DATE);
					result.append(" - ").append((String)endDateTimeMap.get(OrderConstants.GMT_TIME));
				}else{
					result.append(" ").append((String)endDateTimeMap.get(OrderConstants.TIME_ZONE)).append(" - ").append((String)endDateTimeMap.get(OrderConstants.GMT_DATE))
					.append(" ").append((String)endDateTimeMap.get(OrderConstants.GMT_TIME));
				}
			}
			return result.append(" ").append((String)startDateTimeMap.get(OrderConstants.TIME_ZONE)).toString();
		} catch (Exception e) {
			logger.error("Error populating TimeSlot : " + e);
			throw e;
		}
	}
	
	/**
	 * Populate Data for template parameters.
	 * 
	 * @param parameterForTemplate
	 * @param serviceOrderDetails
	 * @return
	 * @throws BusinessServiceException
	 */
	private List<EmailDataVO> populateEmailDataForEmailParameter(List<EmailDataVO> parameterForTemplate, 
			SOLoggingVO serviceOrderDetails) throws Exception {
		List<EmailDataVO> emailData = new ArrayList<EmailDataVO>();
		Boolean loadProviderData = true;
		emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.SO_ID, serviceOrderDetails.getServiceOrderNo()));
		//SLT-3782
		if(null!=serviceOrderDetails.getBuyerId() && null!=serviceOrderDetails.getSoTitle()){
		 if(serviceOrderDetails.getBuyerId().equals(SystemGeneratedEmailConstants.RELAY_SERVICES_BUYER)){
				String soTitle = serviceOrderDetails.getSoTitle();
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.SO_TITLE, soTitle.substring(0, soTitle.lastIndexOf(" "))));
			}
			else{
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.SO_TITLE, serviceOrderDetails.getSoTitle()));
			}
		}
		try{
			SOContactDetailsVO contactDetails = getServiceOrderContactDetails(serviceOrderDetails.getServiceOrderNo());
			if (null != contactDetails && null != contactDetails.getEmail() &&  !contactDetails.getEmail().isEmpty()) {
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.FIRST_NAME, contactDetails.getFirstName()));
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.LAST_NAME, contactDetails.getLastName()));
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.EMAIL, contactDetails.getEmail()));
			}else{
				logger.error("Empty email id for record : " +serviceOrderDetails.getSoLoggingId());
				throw new EmailNotFoundException("Empty email id for record : " +serviceOrderDetails.getSoLoggingId());
			}
		} catch (BusinessServiceException e) {
			logger.error("Error in fetching Contact Details :" + e.getMessage());
			throw e;
		}
		
		for (EmailDataVO templateParam : parameterForTemplate) {
			switch (templateParam.getParamKey()) {
			case SystemGeneratedEmailConstants.DATE_SERVICE:
			case SystemGeneratedEmailConstants.TIME_SERVICE:
				emailData.addAll(populateAppoinmentDate(serviceOrderDetails));
				break;
			case SystemGeneratedEmailConstants.TIME_SLOTS:
				emailData.addAll(populateTimeSlots(serviceOrderDetails));
				break;
			case SystemGeneratedEmailConstants.TIME_SLOT:				
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.TIME_SLOT, populateTimeSlot(serviceOrderDetails.getServiceDate1(), serviceOrderDetails.getServiceDateStartTime(),
						serviceOrderDetails.getServiceDate2(), serviceOrderDetails.getServiceDateEndTime(), serviceOrderDetails.getTimeZone())));
				break;
			case SystemGeneratedEmailConstants.PROVIDER_FIRM_NAME:
			case SystemGeneratedEmailConstants.PROVIDER_PHONENUMBER:
			case SystemGeneratedEmailConstants.PROVIDER_RATING:
				try {
					if(loadProviderData){
						ProviderDetailsVO providerDetails = getProviderDetailsForVendorId(
								serviceOrderDetails.getAcceptedVendorId());
						if (providerDetails != null) {
							emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.PROVIDER_FIRM_NAME,
									providerDetails.getProviderFirmName()));
							String phoneNo = providerDetails.getProviderPhoneNumber();
							if (StringUtils.isNotEmpty(phoneNo) && phoneNo.length() == 10) {
								//provider's phone number should be in XXX-XXX-XXXX format
								emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.PROVIDER_PHONENUMBER,
										new StringBuilder(phoneNo).insert(3, '-').insert(7, '-').toString()));
							} else {
								logger.error("Provider Phone number is invalid : " + phoneNo);
							}
							// provider ratings should be mapped in 0.00,0.25,0.50 so on
							emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.PROVIDER_RATING,
									providerDetails.getProviderRating() != null
											? new DecimalFormat("#0.00").format(Double.valueOf(new DecimalFormat("##.")
													.format(Double.valueOf(providerDetails.getProviderRating() * 4))) / 4)
											+ "" : ""));
						}
						loadProviderData = false;
					}
				} catch (BusinessServiceException e) {
					logger.error("Error in fetching Provider Details :" + e.getMessage());
				}
				break;
			case SystemGeneratedEmailConstants.SURVEY_LINK:
				emailData.add(new EmailDataVO(SystemGeneratedEmailConstants.SURVEY_LINK, populateSurveyLink(serviceOrderDetails)));
				break;
			default:
				break;
			}
		}
		return emailData;
	}

	/**
	 * Add to Alert Task table.
	 */
	@Override
	public void addAlertListToQueue(List<AlertTask> alertTasks) {
		logger.info("Start SystemGeneratedEmailBOImpl :: addAlertTaskListToQueue");
		try {
			alertDao.addAlertListToQueue(alertTasks);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.error("AlertAdvice-->addAlertListToQueue from SystemGenerated Email -->DataServiceException-->", e);
		}
	}
	
	/**
	 * Fetch CounterValue from database
	 * 
	 * @throws BusinessServiceException
	 */
	private Long getCounterValue()
			throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:getCounterValue");
		try {
			return iSystemGeneratedEmailDao.getCounterValue();
		} catch (DataServiceException e) {
			logger.error("Error in fetching CounterValue :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	/**
	 * Fetch MaxValue from database
	 * 
	 * @throws BusinessServiceException
	 */
	private Long getMaxValue()
			throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:getMaxValue");
		try {
			return iSystemGeneratedEmailDao.getMaxValue();
		} catch (DataServiceException e) {
			logger.error("Error in fetching MaxValue :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch SOLogging List from database
	 * 
	 * @throws BusinessServiceException
	 */
	@Override
	public List<SOLoggingVO> getSOLoggingDetails(Set<Integer> buyerIds, Set<Integer> actionIds, 
			Long counterValue, Long maxValue) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:getSOLoggingDetails");
		try {
			return iSystemGeneratedEmailDao.getSOLoggingDetails(buyerIds, actionIds, counterValue, maxValue);
		} catch (DataServiceException e) {
			logger.error("Error in fatching SOLoggingDetails :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch EventTemplate List from database
	 *
	 */
	@Override
	public List<EventTemplateVO> getBuyerEventTemplateDetails() throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:getBuyerEventTemplateDetails");
		try {
			return iSystemGeneratedEmailDao.getEventTemplateDetailsForBuyer();
		} catch (DataServiceException e) {
			logger.error("Error in fatching EventTemplateDetails :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	/**
	 * Fetch Buyer EventTemplate List from database for Reminder Service
	 *
	 */
	private List<EventTemplateVO> getBuyerEventTemplateDetailsForReminderService() throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:getBuyerEventTemplateDetailsForReminderService");
		try {
			return iSystemGeneratedEmailDao.getEventTemplateDetailsForBuyerReminderService();
		} catch (DataServiceException e) {
			logger.error("Error in fatching EventTemplateDetails :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch EmailData List from database
	 */
	@Override
	public List<EmailDataVO> getEmailDataForBuyer(Set<Integer> buyerIds) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchEmailDataForBuyer");
		try {
			return iSystemGeneratedEmailDao.getEmailDataForBuyer(buyerIds);
		} catch (DataServiceException e) {
			logger.error("Error in fatching EmailDataForBuyer :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch BuyerEventEmailData List from database
	 */
	@Override
	public List<EmailDataVO> getEmailDataForBuyerEvent(Set<Integer> buyerEventIds) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchEmailDataForBuyerEvent");
		try {
			return iSystemGeneratedEmailDao.getEmailDataForBuyerEvent(buyerEventIds);
		} catch (DataServiceException e) {
			logger.error("Error in fatching EmailDataForBuyerEvent :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch EmailParameter List from database
	 */
	@Override
	public List<EmailDataVO> getEmailParameterForTemplate(Set<Integer> templateIds) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchEmailParameterForTemplate");
		try {
			return iSystemGeneratedEmailDao.getEmailParameterForTemplate(templateIds);
		} catch (DataServiceException e) {
			logger.error("Error in fatching EmailParameterForTemplate :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch ServiceOrderContact List from database
	 */
	@Override
	public SOContactDetailsVO getServiceOrderContactDetails(String soId) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchServiceOrderContactDetails");
		try {
			return iSystemGeneratedEmailDao.getContactDetailsForServiceOrder(soId);
		} catch (DataServiceException e) {
			logger.error("Error in fatching ContactDetails :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch ScheduleDetail List from database
	 */
	@Override
	public List<SOScheduleDetailsVO> getServiceOrderScheduleDetails(String soId) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchServiceOrderScheduleDetails");
		try {
			return iSystemGeneratedEmailDao.getScheduleDetailsForServiceOrder(soId);
		} catch (DataServiceException e) {
			logger.error("Error in fatching ScheduleDetails :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Fetch ProviderDetail from database
	 */
	@Override
	public ProviderDetailsVO getProviderDetailsForVendorId(Integer vendorId) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:fetchProviderDetailsForVendorId");
		try {
			return iSystemGeneratedEmailDao.getProviderDetailsForVendorId(vendorId);
		} catch (DataServiceException e) {
			logger.error("Error in fatching ProviderDetail :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * Insert SystemGeneratedEmailLog in database
	 */
	private void updateSystemGeneratedEmailCounter(Long systemGeneratedEmailCounter) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl:updateSystemGeneratedEmailCounter");
		try {
			iSystemGeneratedEmailDao.updateSystemGeneratedEmailCounter(systemGeneratedEmailCounter);
			logger.info("Updated CounterValue to --> "+systemGeneratedEmailCounter);
		} catch (DataServiceException e) {
			logger.error("Error in updateSystemGeneratedEmailCounter :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
	
	// SLT-1790 : Encrypt SOID
	public String encryptSoId(String soId) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(soId);
		cryptographyVO.setKAlias((MPConstants.ENCRYPTION_KEY_FOR_SOID));
		CryptographyVO encryptedSoId = surveyCryptographyUtil.encryptKey(cryptographyVO);
		return encryptedSoId.getResponse();

	}


	/**
	 * SLT-1569 - Populate survey link. base url + Encrypted (SoId|surveyoption)
	 * 
	 * @param serviceOrderDetails
	 * @return
	 * @throws BusinessServiceException
	 */
	private String populateSurveyLink(SOLoggingVO serviceOrderDetails) throws BusinessServiceException {
		try{
			StringBuilder surveyLink = new StringBuilder();
			fetchBuyerSurveyType(serviceOrderDetails.getBuyerId());
			surveyLink.append(getLink(SystemGeneratedEmailConstants.SURVEY_URL))
			.append(SystemGeneratedEmailConstants.SURVEY_URL_KEY)
			.append(encryptSoId(getKeyForEncryption(serviceOrderDetails)))
			.append(SystemGeneratedEmailConstants.SURVEY_URL_RATING);
			return surveyLink.toString();
		} catch (Exception e) {
			logger.error("Error populating SurveyLink : " + e);
			throw e;
		}
	}

	/**
	 * Get Survey Link from application_properties
	 * 
	 * @return
	 */
	private String getLink(String linkType) {
		try {
			 return PropertiesUtils.getPropertyValue(linkType);
		} catch (Exception e) {
			logger.error("Exception Occurred while fetching Survey Link " + e);
		}
		return null;
	}

	/**
	 * Fetch Buyer Survey option for the buyer.
	 * 
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	private String fetchBuyerSurveyType(Integer buyerId) throws BusinessServiceException {
		if (null == buyerSurveyType.get(buyerId))
			loadBuyerSurveyType(buyerId);
		return buyerSurveyType.get(buyerId);
	}

	/*
	 * Load survey option details configured by the buyer
	 * 
	 * @param Set<Integer>buyerIds
	 * 
	 */
	private void loadBuyerSurveyType(Integer buyerId) throws BusinessServiceException {
		try {
			getBuyerSurveyOptions(buyerId);
		} catch (BusinessServiceException e) {
			logger.error("Error in fetching Buyer Survey Options :" + e.getMessage());
			throw e;
		}
	}

	/**
	 * Retrieve Buyer survey config Details from db
	 * 
	 * @param buyerIds
	 * @return
	 * @throws BusinessServiceException
	 */
	private void getBuyerSurveyOptions(Integer buyerId) throws BusinessServiceException {
		logger.debug("Start SystemGeneratedEmailBOImpl.getBuyerSurveyOptions");
		List<BuyerSurveyConfigVO> buyerSurveyConfigVOList = new ArrayList<BuyerSurveyConfigVO>();
		List<Integer> surveyIds = new ArrayList<Integer>();
		try {
			buyerSurveyConfigVOList = extendedSurveyDAO.getBuyerSurveyConfigDetails(buyerId);
			if (!CollectionUtils.isEmpty(buyerSurveyConfigVOList)) {
				for (BuyerSurveyConfigVO buyerSurveyConfigVO : buyerSurveyConfigVOList) {
					buyerSurveyType.put(buyerSurveyConfigVO.getBuyerId(), buyerSurveyConfigVO.getSurveyTitle());
					surveyIds.add(buyerSurveyConfigVO.getSurveyId());
				}
				buyerSurveyId.put(buyerId, surveyIds);
			}else{
				logger.error("Survey option is not configured for buyer : "+buyerId);
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("Error in fetching Buyer Survey Options :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	/*
	 * For encrypting soId,surveyType and Survey Id into a single value
	 * 
	 */

	private String getKeyForEncryption(SOLoggingVO serviceOrderDetails) {
		String surveyIds= getSurveyIds(serviceOrderDetails.getBuyerId());
		return serviceOrderDetails.getServiceOrderNo().concat("|")
				.concat(buyerSurveyType.get(serviceOrderDetails.getBuyerId())).concat("|").concat(surveyIds);
	}
		
	private String getSurveyIds(Integer buyerId){
		StringBuilder surveyIds =new StringBuilder();
		if(!CollectionUtils.isEmpty(buyerSurveyId) 
				&& null != buyerSurveyId.get(buyerId)){
			for(Integer surveyId : buyerSurveyId.get(buyerId)){
				if(surveyIds.length()==0)
				 surveyIds= surveyIds.append(surveyId.toString());
				else
					surveyIds=surveyIds.append("_").append(surveyId.toString());
			}
		}
		return surveyIds.toString();
	}
	
	/**
	 * Clear static data which loads value for each scheduler trigger
	 */
	private void clearData() {
		buyerIds.clear();
		actionIds.clear();
		buyerEventIds.clear();
		templateIds.clear();
		eventActions.clear();
		buyersEvents.clear();
		buyerEventTemplates.clear();
		buyerEmailData.clear();
		buyerEventEmailData.clear();
		templateParameters.clear();
		buyerSurveyType.clear();
		recordsFailed.delete(0, recordsFailed.length());
	}

	// SLT-2329 : Send Alert email for failed records
	private void sendSystemGeneratedEmailFailureAlert(String sgeBatch, StringBuffer recordsFailed) throws BusinessServiceException {
		emailTemplateBOImpl.sendGenericEmailWithServiceLiveLogo(
				getAlertEmailValue(SystemGeneratedEmailConstants.ALERT_RECIPIENT), 
				AlertConstants.SERVICE_LIVE_MAILID,
				String.format(SystemGeneratedEmailConstants.RECORD_FAILED_EMAIL_SUBJECT, 
						SystemGeneratedEmailConstants.SGE_BATCH), 
				String.format(SystemGeneratedEmailConstants.RECORD_FAILED_EMAIL_BODY, 
						SystemGeneratedEmailConstants.SGE_BATCH, recordsFailed));
	}
	
	// SLT-2329 : Fetch email id to send alert email for failure records
	private String getAlertEmailValue(String appKey) throws BusinessServiceException {
		logger.debug("Start getAlertEmailValue");
		try {
			return iSystemGeneratedEmailDao.getAlertEmailValue(appKey);
		} catch (DataServiceException e) {
			logger.error("Error in getAlertEmailValue :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
		
	/**
	 * @return the iSystemGeneratedEmailDao
	 */
	public ISystemGeneratedEmailDao getiSystemGeneratedEmailDao() {
		return iSystemGeneratedEmailDao;
	}

	/**
	 * @param iSystemGeneratedEmailDao
	 *            the iSystemGeneratedEmailDao to set
	 */
	public void setiSystemGeneratedEmailDao(ISystemGeneratedEmailDao iSystemGeneratedEmailDao) {
		this.iSystemGeneratedEmailDao = iSystemGeneratedEmailDao;
	}

	/**
	 * @return the alertDao
	 */
	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}

	/**
	 * @param alertDao
	 *            the alertDao to set
	 */
	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}

	/**
	 * @return the emailTemplateBOImpl
	 */
	public EmailTemplateBOImpl getEmailTemplateBOImpl() {
		return emailTemplateBOImpl;
	}

	/**
	 * @param emailTemplateBOImpl
	 *            the emailTemplateBOImpl to set
	 */
	public void setEmailTemplateBOImpl(EmailTemplateBOImpl emailTemplateBOImpl) {
		this.emailTemplateBOImpl = emailTemplateBOImpl;
	}

	
	public SurveyCryptographyUtil getSurveyCryptographyUtil() {
		return surveyCryptographyUtil;
	}

	public void setSurveyCryptographyUtil(SurveyCryptographyUtil surveyCryptographyUtil) {
		this.surveyCryptographyUtil = surveyCryptographyUtil;
	}

	public ExtendedSurveyDAO getExtendedSurveyDAO() {
		return extendedSurveyDAO;
	}

	public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
		this.extendedSurveyDAO = extendedSurveyDAO;
	}

}
