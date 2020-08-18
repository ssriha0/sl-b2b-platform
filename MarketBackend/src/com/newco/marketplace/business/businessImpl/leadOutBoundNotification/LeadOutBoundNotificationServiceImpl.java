package com.newco.marketplace.business.businessImpl.leadOutBoundNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.leadoutboundnotification.beans.RequestLeadDetails;
import com.newco.marketplace.leadoutboundnotification.constatns.LeadOutBoundConstants;
import com.newco.marketplace.leadoutboundnotification.service.ILeadOutBoundNotificationService;
import com.newco.marketplace.leadoutboundnotification.vo.LeadNotificationStatusEnum;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutBoundNotificationVO;
import com.newco.marketplace.leadoutboundnotification.vo.LeadOutboundDetailsVO;
import com.newco.marketplace.persistence.iDao.leadOutBoundNotification.LeadOutBoundNotificationDao;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.thoughtworks.xstream.XStream;

import org.apache.log4j.Logger;

public class LeadOutBoundNotificationServiceImpl implements ILeadOutBoundNotificationService{
	
	private static final Logger LOGGER = Logger.getLogger(LeadOutBoundNotificationServiceImpl.class.getName());
	private LeadOutBoundNotificationDao leadOutBoundNotificationDao;
	
	public List<LeadOutBoundNotificationVO> fetchRecords(Integer noOfRetries, Integer recordsProcessingLimit) throws BusinessServiceException {
		List<LeadOutBoundNotificationVO> notificationVOList = null;
		List<String> leadIds = new ArrayList<String>();
		try {			
			notificationVOList = leadOutBoundNotificationDao.fetchRecords(noOfRetries, recordsProcessingLimit);
			if(null != notificationVOList && !notificationVOList.isEmpty()){
				for(LeadOutBoundNotificationVO records : notificationVOList){
					if(null != records.getLeadId()){
						leadIds.add(records.getLeadId());
					}
				}
				if(null != leadIds && !leadIds.isEmpty()){
					leadOutBoundNotificationDao.updateStatus(leadIds);
				}
			}
		} 
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in fetching the VOobject due to"
							+ dse.getMessage(), dse);
		}
		return notificationVOList;
	}
	
	public List<LeadOutBoundNotificationVO> fetchFailedRecords() throws BusinessServiceException {
		List<LeadOutBoundNotificationVO> failureList = null;
		try {
			failureList = leadOutBoundNotificationDao.fetchFailedRecords();
		}
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in fetching the List due to"
							+ dse.getMessage(), dse);
		}
		return failureList;
	}
	
	public void setEmailIndicator(List<String> failureList) throws BusinessServiceException{
		try {
			leadOutBoundNotificationDao.setEmailIndicator(failureList);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
	}
	
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException {
		try {
			return leadOutBoundNotificationDao.getPropertyFromDB(appKey);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting app key value due to"
							+ dse.getMessage(), dse);
		}	
	}
	
	public Integer fetchRecordsCount(Integer noOfRetries) throws BusinessServiceException{
		Integer count = null;
		try{			
			count = leadOutBoundNotificationDao.fetchRecordsCount(noOfRetries);
		}catch(DataServiceException dse){
			LOGGER.info("Exception occurred in fetchRecordsCount: "+dse.getMessage());
			throw new BusinessServiceException("Exception in fetching the count due to"
					+ dse.getMessage(), dse);
		}
		return count;
	}
	
	public void updateNotification(LeadOutBoundNotificationVO leadOutBoundNotificationVO)
			throws BusinessServiceException {
		try {
			leadOutBoundNotificationDao.updateNotification(leadOutBoundNotificationVO);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating data " + dse.getMessage(), dse);
		}
	}
	
	public void insertLeadOutBoundDetails(String leadId) throws BusinessServiceException {
		try {	
			RequestLeadDetails requestLeadDetails = new RequestLeadDetails();
			LeadOutBoundNotificationVO leadOutBoundNotificationVO = new LeadOutBoundNotificationVO();
			LeadOutboundDetailsVO leadOutboundDetailsVO = leadOutBoundNotificationDao.fetchLeadDetails(leadId);
			if(null != leadOutboundDetailsVO){
				String result = validateLeadDetails(leadOutboundDetailsVO);
				requestLeadDetails = mapLeadDetails(leadOutboundDetailsVO);
				//Model number should be a random value for each api request. So putting sl_lead_id as model number
				requestLeadDetails.setModelNum(leadId);
		
				XStream xstream = new XStream();
				xstream.processAnnotations(RequestLeadDetails.class);
				String createRequestXml = (String) xstream.toXML(requestLeadDetails);
				
				leadOutBoundNotificationVO.setLeadId(leadId);
				leadOutBoundNotificationVO.setXml(createRequestXml);
				leadOutBoundNotificationVO.setCreatedDate(new Date());
				leadOutBoundNotificationVO.setModifiedDate(new Date());
				leadOutBoundNotificationVO.setRetryCount(-1);
				
				if(result.equals("success")){
					leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.WAITING.name());
					leadOutBoundNotificationVO.setException(null);
				}
				else{
					leadOutBoundNotificationVO.setStatus(LeadNotificationStatusEnum.ERROR.name());
					leadOutBoundNotificationVO.setException(result);
				}
				leadOutBoundNotificationDao.insertLeadOutBoundDetails(leadOutBoundNotificationVO);
			}
		} 
		catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in fetching data due to"
							+ dse.getMessage(), dse);
		}	
		catch(Exception e){
			LOGGER.error("Exception in fetching data " + e.getMessage(), e);
		}
	}

	private RequestLeadDetails mapLeadDetails(LeadOutboundDetailsVO leadOutboundDetailsVO) throws DataServiceException {
		RequestLeadDetails requestLeadDetails = new RequestLeadDetails();
		try {
			requestLeadDetails.setCustNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setCustFirstName(leadOutboundDetailsVO.getCustFirstName());
			requestLeadDetails.setCustMiddleName(leadOutboundDetailsVO.getCustMiddleName());
			if(null == requestLeadDetails.getCustMiddleName()){
				requestLeadDetails.setCustMiddleName(LeadOutBoundConstants.NO_DATA);
			}
			requestLeadDetails.setCustLastName(leadOutboundDetailsVO.getCustLastName());
			requestLeadDetails.setAddress1(leadOutboundDetailsVO.getAddress1());
			requestLeadDetails.setAddress2(leadOutboundDetailsVO.getAddress2());
			if(null == requestLeadDetails.getAddress2()){
				requestLeadDetails.setAddress2(LeadOutBoundConstants.NO_DATA);
			}
			requestLeadDetails.setAptNum(leadOutboundDetailsVO.getAptNum());
			if(null == requestLeadDetails.getAptNum()){
				requestLeadDetails.setAptNum(LeadOutBoundConstants.NO_DATA);
			}
			requestLeadDetails.setCity(leadOutboundDetailsVO.getCity());
			requestLeadDetails.setState(leadOutboundDetailsVO.getState());
			requestLeadDetails.setZip(leadOutboundDetailsVO.getZip());
			requestLeadDetails.setZipExt(leadOutboundDetailsVO.getZipExt());
			if(null == requestLeadDetails.getZipExt()){
				requestLeadDetails.setZipExt(LeadOutBoundConstants.NO_DATA);
			}
			requestLeadDetails.setPhoneNum(leadOutboundDetailsVO.getPhoneNum());
			requestLeadDetails.setAlternatePhoneNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddress(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressApt(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressCity(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressState(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressZip(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressZipExt(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairPhoneNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setRepairAddressCrossStreet(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setItemSuffix(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setMerchandiseCode(leadOutboundDetailsVO.getMerchandiseCode());
			requestLeadDetails.setBrand(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setSerialNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setDivision(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setStockNumber(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setCoverageCode(LeadOutBoundConstants.COVERAGE_CODE);
			requestLeadDetails.setPurchasedDate(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setSearsPurchase(LeadOutBoundConstants.SEARS_PURCHASE);
			requestLeadDetails.setServiceRequest(leadOutboundDetailsVO.getServiceRequest());
			requestLeadDetails.setSpecialInstructions(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setServiceScheduleDate(convertDate(leadOutboundDetailsVO.getServiceScheduleDate()));
			requestLeadDetails.setServiceScheduleFromTime(convertTime(leadOutboundDetailsVO.getServiceScheduleFromTime()));
			requestLeadDetails.setServiceScheduleToTime(convertTime(leadOutboundDetailsVO.getServiceScheduleToTime()));
			requestLeadDetails.setEmergencyServiceFlag(LeadOutBoundConstants.EMERGENCY_SERVICE_FLAG);
			requestLeadDetails.setPromotema(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setDoNotPromotemaReason(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setPurchaseOrderNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setPaymentMethod(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setAccountNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setExpirationDate(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setPromoteOther(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setDoNotPromoteOtherReason(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setThirdPartyId(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setThirdPartyAuthId(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setThirdPartyContractNum(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setThirdPartyContractExpDate(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setGeoCode(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setCountyCode(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setVerazipState(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setCountyName(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setInOutCityLimits(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setResubmitSoInd(LeadOutBoundConstants.RESUBMIT_SO_IND);
			requestLeadDetails.setCapacityArea(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setCapacityNeeded(LeadOutBoundConstants.NO_DATA);
			requestLeadDetails.setWorkArea(LeadOutBoundConstants.NO_DATA);
			
			requestLeadDetails.setServiceUnitNum(getPropertyFromDB(MPConstants.NPS_NOTIFICATION_LEADS_SERVICE_UNIT_NUMBER));
		}
		catch(Exception e){
			LOGGER.error("Exception in fetching data " + e.getMessage(), e);
		}
		return requestLeadDetails;
	}

	private String validateLeadDetails(LeadOutboundDetailsVO leadOutboundDetailsVO) {
		StringBuilder error = new StringBuilder(LeadOutBoundConstants.VALIDATION_MESSAGE);
		if(null == leadOutboundDetailsVO.getCustFirstName() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getCustFirstName())){
			error.append("Customer First Name, ");
		}
		if(null == leadOutboundDetailsVO.getCustLastName() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getCustLastName())){
			error.append("Customer Last Name, ");
		}
		if(null == leadOutboundDetailsVO.getAddress1() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getAddress1())){
			error.append("Street Address, ");
		}
		if(null == leadOutboundDetailsVO.getCity() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getCity())){
			error.append("City, ");
		}
		if(null == leadOutboundDetailsVO.getState() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getState())){
			error.append("State, ");
		}
		if(null == leadOutboundDetailsVO.getZip() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getZip())){
			error.append("Zip Code, ");
		}
		if(null == leadOutboundDetailsVO.getPhoneNum() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getPhoneNum())){
			error.append("Phone Number, ");
		}
		if(null == leadOutboundDetailsVO.getServiceRequest() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getServiceRequest())){
			if(null == leadOutboundDetailsVO.getSecondaryProjects() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getSecondaryProjects())){
				error.append("Project Description, ");
			}
			else{
				leadOutboundDetailsVO.setServiceRequest(leadOutboundDetailsVO.getSecondaryProjects());
			}
		}
		//Setting service schedule time as default from and to time if preferred time is not present in database
		if(null == leadOutboundDetailsVO.getServiceScheduleFromTime() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getServiceScheduleFromTime())){
			leadOutboundDetailsVO.setServiceScheduleFromTime(LeadOutBoundConstants.SERVICE_DEFAULT_FROM_TIME);
		}
		if(null == leadOutboundDetailsVO.getServiceScheduleToTime() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getServiceScheduleToTime())){
			leadOutboundDetailsVO.setServiceScheduleToTime(LeadOutBoundConstants.SERVICE_DEFAULT_TO_TIME);
		}
		
		if(null == leadOutboundDetailsVO.getMerchandiseCode() || org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getMerchandiseCode())){
			error.append("Merchandise Code. ");
		}
		//Setting service schedule date as current date if preferred date is not present in database or is a past date
		if(null == leadOutboundDetailsVO.getServiceScheduleDate() || 
				org.apache.commons.lang.StringUtils.isBlank(leadOutboundDetailsVO.getServiceScheduleDate()) ||
				(!validateDate(leadOutboundDetailsVO.getServiceScheduleDate()))){
			Date result = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setTimeZone(TimeZone.getTimeZone("EST5EDT")); // Default EST
			String setDate = formatter.format(result);
			leadOutboundDetailsVO.setServiceScheduleDate(setDate);
		}
		/*else if(!validateDate(leadOutboundDetailsVO.getServiceScheduleDate())){
			Date result = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			leadOutboundDetailsVO.setServiceScheduleDate(formatter.format(result));
		}*/
		String result = error.toString();
		if(result.equalsIgnoreCase(LeadOutBoundConstants.VALIDATION_MESSAGE)){
			return "success";
		}
		else{
			result = result.substring(0, error.toString().length()-2);
			return result;
		}
	}
	
	/**
	 * Validate if the date received is before current date in EST because NPS 
	 * uses EST for all calculations
	 * @param date
	 * @return
	 */
	private boolean validateDate(String date) {
		Date result = null;
		Date check = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    formatter.setTimeZone(TimeZone.getTimeZone("EST5EDT")); 
		try {
			result = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
			check = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(formatter.format(new Date()));
		} catch (ParseException e) {
			LOGGER.error("Exception in parsing date " + e.getMessage(), e);
		}
		if(result.before(check)){
			return false;
		}
		return true;
	}
	
	public String convertDate(String date){
		Date result = null;
		try {
			result = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			LOGGER.error("Exception in parsing date " + e.getMessage(), e);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return(formatter.format(result));
	}
	
	public String convertTime(String time){
		Date result = null;
		try {
			result = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).parse(time);
		} catch (ParseException e) {
			try{
				result = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(time);
			} catch(ParseException pe){
				LOGGER.error("Exception in parsing date " + pe.getMessage(), pe);
			}
		}
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
		return(formatter.format(result));
	}
	
	public LeadOutBoundNotificationDao getLeadOutBoundNotificationDao() {
		return leadOutBoundNotificationDao;
	}

	public void setLeadOutBoundNotificationDao(
			LeadOutBoundNotificationDao leadOutBoundNotificationDao) {
		this.leadOutBoundNotificationDao = leadOutBoundNotificationDao;
	}
}
