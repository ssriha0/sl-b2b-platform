package com.newco.marketplace.persistence.iDao.audit;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.AuditEmailVo;

public interface IAuditEmailDao {
	

	public String getEmailAddressFromUserName (String userName)throws DataServiceException;
	public String getEmailAddressFromVendorId (Integer vendorId) throws DataServiceException;
	public String getEmailAddressFromResourceId (Integer resourceId) throws DataServiceException;
	public String getReasonCodeDescription (String reasonCode) throws DataServiceException;
	public AuditEmailVo getResourceName (int resourceId) throws DataServiceException;

	public AuditEmailVo getSpecificResourceCredential (AuditEmailVo auditEmailVo) throws DataServiceException;
	public AuditEmailVo getSpecificVendorCredential (AuditEmailVo auditEmailVo) throws DataServiceException;

	////Adding this for EMAIL change in Addressing Name for rebranding
	public AuditEmailVo getResourceNamePrimeContact (int resourceId) throws DataServiceException;
	////ENd of changes for Rebranding

}
