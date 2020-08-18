/**
 * 
 */
package com.newco.marketplace.persistence.dao.systemgeneratedemail;

import java.util.List;
import java.util.Set;

import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventTemplateVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOContactDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOLoggingVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOScheduleDetailsVO;
import com.servicelive.common.exception.DataServiceException;

/**
 * @author lprabha
 *
 */
public interface ISystemGeneratedEmailDao {

	
	public List<SOLoggingVO> getSOLoggingDetails(Set<Integer> buyerIds, Set<Integer> actionIds, 
			Long counterValue, Long maxValue) throws DataServiceException;

	public List<EventTemplateVO> getEventTemplateDetailsForBuyer() throws DataServiceException;
	
	public List<EmailDataVO> getEmailDataForBuyer(Set<Integer> buyerIds) throws DataServiceException;
	
	public List<EmailDataVO> getEmailDataForBuyerEvent(Set<Integer> buyerEventIds) throws DataServiceException;
	
	public List<EmailDataVO> getEmailParameterForTemplate(Set<Integer> templateIds) throws DataServiceException;
	
	public SOContactDetailsVO getContactDetailsForServiceOrder(String soId) throws DataServiceException;
	
	public List<SOScheduleDetailsVO> getScheduleDetailsForServiceOrder(String soId) throws DataServiceException;
	
	public ProviderDetailsVO getProviderDetailsForVendorId(Integer vendorId) throws DataServiceException;
	
	public boolean updateSystemGeneratedEmailCounter(Long systemGeneratedEmailCounter) throws DataServiceException;

	public List<EventTemplateVO> getEventTemplateDetailsForBuyerReminderService() throws DataServiceException;

	public List<SOLoggingVO> getSOIdsForNextDayService(Set<Integer> buyerIds) throws DataServiceException;

	public Long getCounterValue() throws DataServiceException;

	public Long getMaxValue() throws DataServiceException;

	public String getAlertEmailValue(String appkey) throws DataServiceException;

}
