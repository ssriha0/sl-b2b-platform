/**
 * 
 */
package com.newco.marketplace.business.iBusiness.systemgeneratedemail;

import java.util.List;
import java.util.Set;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EmailDataVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.EventTemplateVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOContactDetailsVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOLoggingVO;
import com.newco.marketplace.dto.vo.systemgeneratedemail.SOScheduleDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author lprabha
 *
 */
public interface ISystemGeneratedBO {
	public List<SOLoggingVO> getSOLoggingDetails(Set<Integer> buyerIds, Set<Integer> actionIds, Long counterValue,
			Long maxValue) throws BusinessServiceException;

	public List<EventTemplateVO> getBuyerEventTemplateDetails() throws BusinessServiceException;
	
	public List<EmailDataVO> getEmailDataForBuyer(Set<Integer> buyerIds) throws BusinessServiceException;
	
	public List<EmailDataVO> getEmailDataForBuyerEvent(Set<Integer> buyerEventIds) throws BusinessServiceException;
	
	public List<EmailDataVO> getEmailParameterForTemplate(Set<Integer> templateIds) throws BusinessServiceException;
	
	public SOContactDetailsVO getServiceOrderContactDetails(String soId) throws BusinessServiceException;
	
	public List<SOScheduleDetailsVO> getServiceOrderScheduleDetails(String soId) throws BusinessServiceException;
	
	public ProviderDetailsVO getProviderDetailsForVendorId(Integer vendorId) throws BusinessServiceException;

	public void addAlertListToQueue(List<AlertTask> alertTasks);
	
	//SLT-1790
	public String encryptSoId(String soId);

	public void processSystemGeneratedEmail() throws Exception;

	public void processReminderEmail() throws Exception;

}
