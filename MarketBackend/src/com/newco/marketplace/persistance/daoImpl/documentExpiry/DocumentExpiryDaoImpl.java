
package com.newco.marketplace.persistance.daoImpl.documentExpiry;

import java.util.ArrayList;
import java.util.List;


import com.newco.marketplace.dto.vo.ExpiryDetailsVO;
import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;


import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.documentExpiry.DocumentExpiryDao;
import com.sears.os.dao.impl.ABaseImplDao;


public class DocumentExpiryDaoImpl extends ABaseImplDao implements DocumentExpiryDao 
{
	@SuppressWarnings("unchecked")
	public List<ExpiryDetailsVO> processFirmsCredentials()throws DataServiceException  {
		List<ExpiryDetailsVO> details = new ArrayList<ExpiryDetailsVO>();
  		details = (List<ExpiryDetailsVO>)queryForList("documentExpiry.fetchFirmDetails");
		return details;
	}
	

	public  void makeOutOfCompliant(List<Integer> credentialIds) throws DataServiceException
	{
		update("documentExpiry.makeOutOfCompliant",credentialIds);
    }
	
	public Integer updateAuditHistory(AuditHistoryVO auditHistoryVO) throws DataServiceException
    {
		Integer auditTaskHistoryId = Integer.valueOf(0);
		try{
			 if(0< getCountOfAuditTaskReasonCode(auditHistoryVO.getAuditTaskId())){
				 /*Update audit_tassk_reason_cd table if there is an entry exists
				  * for the given audit_task_id.
				  * */
				 update("documentExpiry.updateAuditTaskReasonCode",auditHistoryVO);
			 }else{
				 	/* Insert into audit_task_reason_cd tables with the current alert_task_id
					 * reason code.
					 * */
					insert("documentExpiry.insertAuditTaskReasonCode",auditHistoryVO);
			 }
			
			/* Update audit_task_history table. The trigger on this table will update
			 * audit_task_reason_cd_history table
			 * */
			auditTaskHistoryId = (Integer)insert("documentExpiry.updateAuditHistory",auditHistoryVO);
		}catch(DataServiceException ex){
			logger.error(ex);
			throw ex;
		}catch(Exception ex){
			logger.error(ex);
			throw new DataServiceException(ex.getMessage(),ex);
		}
		return auditTaskHistoryId;
    }
	
    public boolean checkIfnotificationAlreadySent(ExpiryDetailsVO expiryDetailsVO) throws DataServiceException
    {
    	Integer count=(Integer) queryForObject("documentExpiry.checkNotificationSent", expiryDetailsVO);
    	if(count>0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    
    public void addVendorAuditNotes(List<ExpiryDetailsVO> detailsVoList) throws DataServiceException
    {
    	batchInsert("documentExpiry.addVendorAuditNotes", detailsVoList);
    }
    
    public void addAuditNotice(List<ExpiryDetailsVO> detailsVoList) throws DataServiceException
    {
    	batchInsert("documentExpiry.addAuditNotice", detailsVoList);
    	
    }
    
    @SuppressWarnings("unchecked")
   public List<ExpiryDetailsVO> getProviderCredentialDetails()throws DataServiceException{
    	List<ExpiryDetailsVO> details = new ArrayList<ExpiryDetailsVO>();
  		details = (List<ExpiryDetailsVO>)queryForList("documentExpiry.fetchProviderCredentialDetails");
		return details;
    }
    
    public void makeProviderCredOutOfcompliant(List<Integer> credentialIds)throws DataServiceException{
    	update("documentExpiry.makeProviderCredOutOfCompliant",credentialIds);
    }
    
	public void updateReasoncodeHistory(AuditHistoryVO auditHistoryVO)throws DataServiceException {
	
		insert("documentExpiry.updateReasoncodeHistory", auditHistoryVO);
	}
	
	public void updateNotificationType(List<Integer> notificationIds)throws DataServiceException{
		update("documentExpiry.updateNotificationType",notificationIds);
	}


	public void deleteNotificationEntry() throws DataServiceException {
		//deletes notifications which have expired
		delete("documentExpiry.deleteNotification",null);
		
		//Commenting the code for R11_0 Recertification Batch
		/*//deletes re-certification notifications which have expired
		delete("documentExpiry.deleteRecertificationNotification",null);*/
		
		//deletes notifications whose expiration date is changed
		List<Integer> notificationIds = (List<Integer>)queryForList("documentExpiry.fetchExpirationChangedDetails");	
		if(null != notificationIds && notificationIds.size()>0){
			delete("documentExpiry.deleteNotificationDetails",notificationIds);
		}
		notificationIds.removeAll(notificationIds);
		
		//deletes notifications whose credentials were removed
		notificationIds = (List<Integer>)queryForList("documentExpiry.fetchCredRemovedDetails");	
		if(null != notificationIds && notificationIds.size()>0){
			delete("documentExpiry.deleteNotificationDetails",notificationIds);
		}
	}
	
	public Integer getCountOfAuditTaskReasonCode(int auditTaskId ) throws DataServiceException {
		Integer count = Integer.valueOf(0);
		try{
			count = (Integer) queryForObject("documentExpiry.checkForAuditTaskResonCodeEntry", auditTaskId);
		}catch(Exception e){
			logger.error("Errorr while fteching the count of auditTask reason code entry."+e.getMessage());
			throw new DataServiceException(e.getMessage(), e);
		}
    	return count;
	}
}
