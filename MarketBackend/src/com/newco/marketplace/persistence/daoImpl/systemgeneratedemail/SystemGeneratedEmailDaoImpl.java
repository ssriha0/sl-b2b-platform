package com.newco.marketplace.persistence.daoImpl.systemgeneratedemail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventTemplateVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOContactDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOLoggingVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOScheduleDetailsVO;
import com.newco.marketplace.persistence.dao.systemgeneratedemail.ISystemGeneratedEmailDao;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.common.exception.DataServiceException;

public class SystemGeneratedEmailDaoImpl extends ABaseImplDao implements ISystemGeneratedEmailDao{

	@Override
	public Long getCounterValue() throws DataServiceException {
		logger.debug("start SystemGeneratedEmailDaoImpl::getCounterValue");
		try {
			Date start = new Date();
			Long counterValue = (Long) queryForObject(
					"getCounterValue.query", null);
			Date end = new Date();
			logger.debug("getCounterValue.query : " + (end.getTime() - start.getTime()) + " ms");
			return counterValue;
			
		} catch (DataAccessException e) {
			logger.error("Exception occured in getCounterValue.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public Long getMaxValue() throws DataServiceException {
		logger.debug("start SystemGeneratedEmailDaoImpl::getMaxValue");
		try {
			Date start = new Date();
			Long maxValue = (Long) queryForObject(
					"getMaxValue.query", null);
			Date end = new Date();
			logger.debug("getMaxValue.query : " + (end.getTime() - start.getTime()) + " ms");
			return maxValue;
			
		} catch (DataAccessException e) {
			logger.error("Exception occured in getMaxValue.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public List<SOLoggingVO> getSOLoggingDetails(Set<Integer> buyerIds, Set<Integer> actionIds, 
			Long counterValue, Long maxValue) throws DataServiceException {
		List<SOLoggingVO> sOLoggingVO = new ArrayList<SOLoggingVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getSOLoggingDetails.query");
		try {
		
			Date start = new Date();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("actionIds", new ArrayList<>(actionIds));
			params.put("buyerIds", new ArrayList<>(buyerIds));
			params.put("counterValue", counterValue);
			params.put("maxValue", maxValue);
			sOLoggingVO = (List<SOLoggingVO>) queryForList(
					"getSOLoggingDetails.query",params);
			Date end = new Date();
			logger.debug("getSOLoggingDetails.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getSOLoggingDetails.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return sOLoggingVO;
	}

	@Override
	public List<EventTemplateVO> getEventTemplateDetailsForBuyer() throws DataServiceException {
		List<EventTemplateVO> eventTemplateVO = new ArrayList<EventTemplateVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getEventTemplateDetailsForBuyer");
		try {
		
			Date start = new Date();
			eventTemplateVO = (List<EventTemplateVO>) queryForList(
					"getEventTemplateDetailsForBuyer.query");
			Date end = new Date();
			logger.debug("getEventTemplateDetailsForBuyer.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getEventTemplateDetailsForBuyer.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return eventTemplateVO;
	}
	
	@Override
	public List<EventTemplateVO> getEventTemplateDetailsForBuyerReminderService() throws DataServiceException {
		List<EventTemplateVO> eventTemplateVO = new ArrayList<EventTemplateVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getEventTemplateDetailsForBuyerReminderService");
		try {
		
			Date start = new Date();
			eventTemplateVO = (List<EventTemplateVO>) queryForList(
					"getEventTemplateDetailsForBuyerReminderService.query");
			Date end = new Date();
			logger.debug("getEventTemplateDetailsForBuyerReminderService.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getEventTemplateDetailsForBuyerReminderService.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return eventTemplateVO;
	}

	@Override
	public List<EmailDataVO> getEmailDataForBuyer(Set<Integer> buyerIds) throws DataServiceException {
		List<EmailDataVO> emailDataVO = new ArrayList<EmailDataVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getEmailDataForBuyer");
		try {
		
			Date start = new Date();
			emailDataVO = (List<EmailDataVO>) queryForList(
					"getEmailDataForBuyer.query", new ArrayList<>(buyerIds));
			Date end = new Date();
			logger.debug("getEmailDataForBuyer.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getEmailDataForBuyer.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return emailDataVO;
	}

	@Override
	public List<EmailDataVO> getEmailDataForBuyerEvent(Set<Integer> buyerEventIds) throws DataServiceException {
		List<EmailDataVO> emailDataVO = new ArrayList<EmailDataVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getEmailDataForBuyerEvent");
		try {
		
			Date start = new Date();
			emailDataVO = (List<EmailDataVO>) queryForList(
					"getEmailDataForBuyerEvent.query", new ArrayList<>(buyerEventIds));
			Date end = new Date();
			logger.debug("getEmailDataForBuyerEvent.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getEmailDataForBuyerEvent.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return emailDataVO;
	}

	@Override
	public List<EmailDataVO> getEmailParameterForTemplate(Set<Integer> templateIds) throws DataServiceException {
		List<EmailDataVO> emailTemplateParameterVO = new ArrayList<EmailDataVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getEmailParameterForTemplate");
		try {
		
			Date start = new Date();
			emailTemplateParameterVO = (List<EmailDataVO>) queryForList(
					"getEmailParameterForTemplate.query", new ArrayList<>(templateIds));
			Date end = new Date();
			logger.debug("getEmailParameterForTemplate.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getEmailParameterForTemplate.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return emailTemplateParameterVO;
	}

	@Override
	public SOContactDetailsVO getContactDetailsForServiceOrder(String soId) throws DataServiceException {
		logger.debug("start SystemGeneratedEmailDaoImpl::getContactDetailsForServiceOrder");
		try {
			Date start = new Date();
			SOContactDetailsVO sOContactDetailsVO = (SOContactDetailsVO) queryForObject(
					"getContactDetailsForServiceOrder.query", soId);
			Date end = new Date();
			logger.debug("getContactDetailsForServiceOrder.query : " + (end.getTime() - start.getTime()) + " ms");
			return sOContactDetailsVO;
			
		} catch (DataAccessException e) {
			logger.error("Exception occured in getContactDetailsForServiceOrder.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public List<SOScheduleDetailsVO> getScheduleDetailsForServiceOrder(String soId) throws DataServiceException {
		List<SOScheduleDetailsVO> sOScheduleDetailsVO = new ArrayList<SOScheduleDetailsVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getScheduleDetailsForServiceOrder");
		try {
		
			Date start = new Date();
			sOScheduleDetailsVO = (List<SOScheduleDetailsVO>) queryForList(
					"getScheduleDetailsForServiceOrder.query", soId);
			Date end = new Date();
			logger.debug("getScheduleDetailsForServiceOrder.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {
			logger.error("Exception occured in getScheduleDetailsForServiceOrder.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return sOScheduleDetailsVO;
	}

	@Override
	public ProviderDetailsVO getProviderDetailsForVendorId(Integer vendorId) throws DataServiceException {
		logger.debug("start SystemGeneratedEmailDaoImpl::getProviderDetailsForVendorId");
		try {
			Date start = new Date();
			ProviderDetailsVO providerDetailsVO = (ProviderDetailsVO) queryForObject(
					"getProviderDetailsForVendorId.query", vendorId);
			Date end = new Date();
			logger.debug("getProviderDetailsForVendorId.query : " + (end.getTime() - start.getTime()) + " ms");
			return providerDetailsVO;
			
		} catch (DataAccessException e) {
			logger.error("Exception occured in getProviderDetailsForVendorId.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public boolean updateSystemGeneratedEmailCounter(Long soLoggingId) throws DataServiceException {
		logger.debug("start SystemGeneratedEmailDaoImpl::updateSystemGeneratedEmailCounter");
		 try {
			 Date start = new Date();
			 update("updateSystemGeneratedEmailCounter.query", soLoggingId);
			 Date end = new Date();
			logger.debug("updateSystemGeneratedEmailCounter.query : " + (end.getTime() - start.getTime()) + " ms");
	        }catch (DataAccessException e) {
	        	logger.error("Exception occured in updateSystemGeneratedEmailCounter.query due to " + e.getMessage());
	            throw new DataServiceException("Exception occured at SystemGeneratedEmailDaoImpl-updateSystemGeneratedEmailCounter", e);
	        }
		 return true;
	}
	
	@Override
	public List<SOLoggingVO> getSOIdsForNextDayService(Set<Integer> buyerIds) throws DataServiceException {
		List<SOLoggingVO> SOLoggingVO =new ArrayList<SOLoggingVO>();
		logger.debug("start SystemGeneratedEmailDaoImpl::getSOIdsForNextDayService");
		try {
		
			Date start = new Date();
			SOLoggingVO = (List<SOLoggingVO>) queryForList(
					"getSODetailsForNextDayService.query", new ArrayList<>(buyerIds));
			Date end = new Date();
			logger.debug("getSODetailsForNextDayService.query : " + (end.getTime() - start.getTime()) + " ms");

		} catch (DataAccessException e) {

			logger.error("Exception occured in getSODetailsForNextDayService.query due to " + e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return SOLoggingVO;
	}

	// SLT-2329
	@Override
	public String getAlertEmailValue(String appkey) throws DataServiceException {
		String value = null;
		try{
			value=(String) queryForObject("getAlertEmailValue.query",appkey);
		}catch(Exception e){
			logger.error("Exception occured in getAlertEmailValue() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return value;
	}
		
}
