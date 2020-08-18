package com.servicelive.notification.dao;

import java.util.List;

import com.servicelive.esb.integration.DataException;
import com.servicelive.esb.integration.domain.Customer;
import com.servicelive.esb.integration.domain.InhomePart;
import com.servicelive.notification.vo.OutboundNotificationVO;

public interface INotificationDao {

	/**get the value of inhome_outbound_flag
	 * @return
	 * @throws DataException
	 */
	public String getOutBoundFlag() throws DataException;
	
	
	//SL-21246: Code change starts
    
    public List <String> getErrorList() throws DataException;
    
    //Code change ends

	/**insert details into buyer_outbound_notification
	 * @param outBoundNotificationVO
	 * @throws DataException
	 */
	public void insertInHomeOutBoundNotification(OutboundNotificationVO outBoundNotificationVO) throws DataException;

	/**get the part details of SO
	 * @param soId
	 * @return
	 */
	public List<InhomePart> getPartDetails(String soId) throws DataException;

	/**get customer info for SO
	 * @param soId
	 * @return
	 */
	public Customer getCustomerDetails(String soId) throws DataException;

	/**get nps inactive ind for the SO 
	 * @param soId
	 * @return
	 * @throws DataException
	 */
	public Integer getNPSInactiveInd(String soId) throws DataException;

	/**get the message for status change
	 * @param wfStateId
	 * @return
	 * @throws DataException
	 */
	public String getMessageForStatusChange(Integer wfStateId)throws DataException;

	/**get the correlation ID
	 * @return
	 * throws DataException
	 */
	public String getCorrelationId()throws DataException;

	/**
	 * @param soId
	 * @return
	 * @throws DataException
	 */
	public List<String> getAllMessageForSo(String soId)throws DataException;
	
	/**get the value of inhome_serial_number_flag
	 * @return
	 * @throws DataException
	 */
	public String getSerialNumberFlag() throws DataException;

	
	/**SL-21241
	 * get the jobCode for a main category
	 * @param primarySkillCatId
	 * @return
	 * @throws DataException
	 */
	public String getJobCodeForMainCategory(Integer primarySkillCatId) throws DataException;


}
