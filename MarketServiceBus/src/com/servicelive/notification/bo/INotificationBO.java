package com.servicelive.notification.bo;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.esb.integration.BusinessException;
import com.servicelive.esb.integration.domain.Customer;
import com.servicelive.esb.integration.domain.InhomePart;
import com.servicelive.notification.vo.OutboundNotificationVO;

public interface INotificationBO {

	
	/**get the value of inhome_outbound_flag
	 * @return
	 * @throws DataServiceException
	 */
	public String getOutBoundFlag() throws BusinessException;

	
	//SL-21246: Code change starts
    
	public List <String> getErrorList() throws BusinessException;
	    
	//code change ends
	
	
	/**insert in-home details
	 * @param outBoundNotificationVO
	 * @throws DataServiceException
	 */
	public void insertInHomeOutBoundNotification(
			OutboundNotificationVO outBoundNotificationVO)throws BusinessException;

	/**get the part details of SO
	 * @param soId
	 * @return
	 */
	public List<InhomePart> getPartDetails(String soId)throws BusinessException;

	/**get customer info for SO
	 * @param soId
	 * @return
	 */
	public Customer getCustomerDetails(String soId)throws BusinessException;

	/**get nps inactive ind for the SO
	 * @param soId
	 * @return
	 */
	public Integer getNPSInactiveInd(String soId)throws BusinessException;

	/**get the message for status change
	 * @param wfStateId
	 * @return
	 * @throws BusinessException
	 */
	public String getMessageForStatus(Integer wfStateId)throws BusinessException;

	/**get the correlation ID
	 * @return
	 * @throws BusinessException
	 */
	public String getCorrelationId()throws BusinessException;

	/**
	 * @param soId
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getAllMessagesForSo(String soId)throws BusinessException;

	/**get the value of inhome_serial_number_flag
	 * @return
	 * @throws BusinessException
	 */
	public String getSerialNumberFlag() throws BusinessException;

	/**SL-21241
	 * get the jobCode for a main category
	 * @param primarySkillCatId
	 * @return
	 * @throws BusinessException
	 */
	public String getJobCodeForMainCategory(Integer primarySkillCatId) throws BusinessException;
	

}