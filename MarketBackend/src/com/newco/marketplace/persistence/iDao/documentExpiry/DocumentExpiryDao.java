package com.newco.marketplace.persistence.iDao.documentExpiry;

import java.util.List;

import com.newco.marketplace.dto.vo.ExpiryDetailsVO;
import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.exception.core.DataServiceException;


public interface DocumentExpiryDao 
{ 
	public  List<ExpiryDetailsVO> processFirmsCredentials()throws DataServiceException ;
	public void makeOutOfCompliant(List<Integer> credentialIds) throws DataServiceException;
	public Integer updateAuditHistory(AuditHistoryVO auditHistoryVO)throws DataServiceException ;
	public boolean checkIfnotificationAlreadySent(ExpiryDetailsVO expiryDetailsVO) throws DataServiceException;
	public void addVendorAuditNotes(List<ExpiryDetailsVO> detailsVoList) throws DataServiceException;
	public void addAuditNotice(List<ExpiryDetailsVO> detailsVoList) throws DataServiceException;
	public List<ExpiryDetailsVO> getProviderCredentialDetails()throws DataServiceException ;
	public void makeProviderCredOutOfcompliant(List<Integer> credentialIds)throws DataServiceException ;
	public void updateReasoncodeHistory(AuditHistoryVO auditHistoryVO)throws DataServiceException ;
	public void updateNotificationType(List<Integer> credentialIds)throws DataServiceException;
	//deletes notifications which have already expired
	public void deleteNotificationEntry() throws DataServiceException;
	
}
 