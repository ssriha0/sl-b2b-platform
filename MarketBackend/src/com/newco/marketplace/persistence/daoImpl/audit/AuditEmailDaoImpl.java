package com.newco.marketplace.persistence.daoImpl.audit;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.audit.IAuditEmailDao;
import com.newco.marketplace.vo.provider.AuditEmailVo;

public class AuditEmailDaoImpl extends MPBaseDaoImpl implements IAuditEmailDao {

	public String getEmailAddressFromUserName(String userName)
	    throws DataServiceException {
		String rvString = (String) queryForObject(
		        "auditEmail.getEmailAddressFromUserName", userName);
		if (null == rvString) { throw new DataServiceException(
		        "[AuditDaoImpl]Select query returned null for method getEmailAddressFromUserName"); }
		return (rvString);
	} //getEmailAddress
	
	public String getEmailAddressFromVendorId(Integer vendorId)
	    throws DataServiceException {
		String rvString = (String) queryForObject(
		        "auditEmail.getEmailAddressFromVendorId", vendorId);
		if (null == rvString) { throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getEmailAddressFromVendorId."); }
		return (rvString);
	} //getEmailAddress
	
	public String getEmailAddressFromResourceId(Integer resourceId)
	    throws DataServiceException {
		String rvString = (String) queryForObject(
		        "auditEmail.getEmailAddressFromResourceId", resourceId);
		if (null == rvString) {
		    throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getEmailAddressFromResourceId.");
		}
		return (rvString);
	} //getEmailAddress
	
	public String getReasonCodeDescription(String reasonCode)
	    throws DataServiceException {
		String rvString = (String) queryForObject(
		        "auditEmail.getReasonDescription", reasonCode);
		if (null == rvString) { throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getReasonCodeDescription."); }
		return (rvString);
	}
	
	public AuditEmailVo getResourceName(int reasonCode)
	    throws DataServiceException {
		AuditEmailVo auditEmailVo = (AuditEmailVo) queryForObject(
		        "auditEmail.getResourceName", reasonCode);
		if (null == auditEmailVo) { throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getResourceName."); }
		return (auditEmailVo);
	}
	
	public AuditEmailVo getSpecificResourceCredential(AuditEmailVo auditEmailVo)
	    throws DataServiceException {
		AuditEmailVo auditEmailVoResp = (AuditEmailVo) queryForObject(
		        "auditEmail.getSpecificResourceCredential", auditEmailVo);
		if (null == auditEmailVoResp) { throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getResourceName."); }
		return (auditEmailVoResp);
	}
	
	public AuditEmailVo getSpecificVendorCredential(AuditEmailVo auditEmailVo)
	    throws DataServiceException {
		AuditEmailVo auditEmailVoResp = (AuditEmailVo) queryForObject(
		        "auditEmail.getSpecificVendorCredential", auditEmailVo);
		if (null == auditEmailVoResp) { throw new DataServiceException(
		        "[AuditDaoImpl] Select query returned null for method getResourceName."); }
		return (auditEmailVoResp);
	}
	
	
	//Adding this for EMAIL change in Addressing Name for rebranding
	public AuditEmailVo getResourceNamePrimeContact(int reasonCode)
	        throws DataServiceException {
	    
	    AuditEmailVo auditEmailVo = (AuditEmailVo) queryForObject(
	            "auditEmail.getResourceNamePrimeContact", reasonCode);
	    if (null == auditEmailVo) { throw new DataServiceException(
	            "[AuditDaoImpl] Select query returned null for method getResourceNamePrimeContact."); }
	   //return (auditEmailVo);
	    if (null == auditEmailVo) { throw new DataServiceException(
	            "[AuditDaoImpl] Select query returned null for method getResourceNamePrimeContact."); }
	
	    return (auditEmailVo);
	}
	//ENd of changes for Rebranding


}
