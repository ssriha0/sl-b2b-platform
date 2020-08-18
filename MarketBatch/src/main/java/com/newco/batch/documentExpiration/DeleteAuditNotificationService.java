package com.newco.batch.documentExpiration;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.documentExpiry.DocumentExpiryDao;

public class DeleteAuditNotificationService {
	
	private static final Logger logger = Logger.getLogger(DeleteAuditNotificationService.class.getName());

	private DocumentExpiryDao documentExpiryDao;

	public DocumentExpiryDao getDocumentExpiryDao() {
		return documentExpiryDao;
	}

	public void setDocumentExpiryDao(DocumentExpiryDao documentExpiryDao) {
		this.documentExpiryDao = documentExpiryDao;
	}
	
	public  void processDeleteNotification() throws BusinessServiceException
	{
		try{
			//deletes notifications from audit_cred_expiry_notification table
			documentExpiryDao.deleteNotificationEntry();
			
		}
		catch(DataServiceException exception){
			logger.error("eror in deleteNotificationEntry() of DeleteAuditNotificationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}
}
