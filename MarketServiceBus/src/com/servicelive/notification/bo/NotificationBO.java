package com.servicelive.notification.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.esb.integration.BusinessException;
import com.servicelive.esb.integration.domain.Customer;
import com.servicelive.esb.integration.domain.InhomePart;
import com.servicelive.notification.dao.INotificationDao;
import com.servicelive.notification.vo.OutboundNotificationVO;

public class NotificationBO implements INotificationBO {

	private INotificationDao notificationDao;
	
	private static final Logger logger = Logger.getLogger(NotificationBO.class);

	/**get the value of inhome_outbound_flag
	 * @return
	 * @throws BusinessException
	 */
	public String getOutBoundFlag() throws BusinessException{
		logger.info("Entering getOutBoundFlag");
		try{
			return notificationDao.getOutBoundFlag();
			
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getOutBoundFlag() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getOutBoundFlag() due to" + e.getMessage(), e);
		}
		
	}
	
	
	//SL-21246: Code change starts
    
    public List <String> getErrorList() throws BusinessException{
           logger.info("Entering getErrorList");
           try{
                  return notificationDao.getErrorList();
                  
           }catch(Exception e){
                  logger.error("Exception in NotificationBO.getErrorList() due to" + e.getMessage());
                  throw new BusinessException("Exception in NotificationBO.getErrorList() due to" + e.getMessage(), e);
           }
           
    }
    
    //code change ends
	
	
	
	/**insert in-home details
	 * @param outBoundNotificationVO
	 * @throws BusinessException
	 */
	public void insertInHomeOutBoundNotification(OutboundNotificationVO outBoundNotificationVO) throws BusinessException{
		logger.info("Entering insertInHomeOutBoundNotification");
		try{
			notificationDao.insertInHomeOutBoundNotification(outBoundNotificationVO);
			
		}catch(Exception e){
			logger.error("Exception in NotificationBO.insertInHomeOutBoundNotification() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getOutBoundFlag() due to" + e.getMessage(), e);
		}
	}
	
	/**get the part details of SO
	 * @param soId
	 * @return
	 */
	public List<InhomePart> getPartDetails(String soId) throws BusinessException {
		logger.info("Entering getPartDetails");
		List<InhomePart> partDetails = new ArrayList<InhomePart>();
		try{
			partDetails = notificationDao.getPartDetails(soId);
			return partDetails;
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getPartDetails() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getPartDetails() due to" + e.getMessage(), e);
		}
	}
	
	/**get customer info for SO
	 * @param soId
	 * @return
	 */
	public Customer getCustomerDetails(String soId) throws BusinessException {
		logger.info("Entering getCustomerDetails");
		Customer customer = new Customer();
		try{
			customer = notificationDao.getCustomerDetails(soId);
			return customer;
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getCustomerDetails() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getCustomerDetails() due to" + e.getMessage(), e);
		}
	}
	
	/**get nps inactive ind for the SO
	 * @param soId
	 * @return
	 */
	public Integer getNPSInactiveInd(String soId)throws BusinessException{
		logger.info("Entering getNPSInactiveInd");
		try{
			return notificationDao.getNPSInactiveInd(soId);
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getNPSInactiveInd() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getNPSInactiveInd() due to" + e.getMessage(), e);
		}
	}

	/**get the message for status change
	 * @param wfStateId
	 * @return
	 * @throws BusinessException
	 */
	public String getMessageForStatus(Integer wfStateId)throws BusinessException {
		String message="";
		try{
			message=notificationDao.getMessageForStatusChange(wfStateId);
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getMessageForStatus() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getMessageForStatus() due to" + e.getMessage(), e);	
		}
		return message;
	}
	
	/**get the correlation ID
	 * @return
	 * @throws BusinessException
	 */
	public String getCorrelationId() throws BusinessException {
		String seqNo = "";
		try{
			seqNo = notificationDao.getCorrelationId();
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getSeqNo() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getSeqNo() due to" + e.getMessage(), e);	
		}
		return seqNo;
	}
	
	public List<String> getAllMessagesForSo(String soId)throws BusinessException {
		List<String> messageList = null;
		try{
			messageList = notificationDao.getAllMessageForSo(soId);
		}catch (Exception e) {
			logger.error("Exception in NotificationBO.getAllMessagesForSo due to" + e);
			throw new BusinessException("Exception in NotificationBO.getAllMessagesForSo due to" + e.getMessage(), e);	
		}
		return messageList;
	}	
	
	/**get the value of inhome_serial_number_flag
	 * @return
	 * @throws BusinessException
	 */
	public String getSerialNumberFlag() throws BusinessException{
		logger.info("Entering getSerialNumberFlag");
		try{
			return notificationDao.getSerialNumberFlag();
			
		}catch(Exception e){
			logger.error("Exception in NotificationBO.getSerialNumberConstant() due to" + e.getMessage());
			throw new BusinessException("Exception in NotificationBO.getSerialNumberConstant() due to" + e.getMessage(), e);
		}
		
	}
	
	/**SL-21241
	 * get the jobCode for a main category
	 * @param primarySkillCatId
	 * @return
	 * @throws BusinessException
	 */
	public String getJobCodeForMainCategory(Integer primarySkillCatId) throws BusinessException{
		try{
			return notificationDao.getJobCodeForMainCategory(primarySkillCatId);
			
		}catch(Exception e){
			throw new BusinessException("Exception in NotificationBO.getJobCodeForMainCategory() due to" + e.getMessage(), e);
		}
	}
	
	
	public INotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(INotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	

	

}