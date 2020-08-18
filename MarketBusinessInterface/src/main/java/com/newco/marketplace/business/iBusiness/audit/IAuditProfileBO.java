package com.newco.marketplace.business.iBusiness.audit;

import java.util.List;

import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IAuditProfileBO {
	
	public void updateWFStatusAndReasonCodes(AuditVO auditVo, boolean isBatchProcess)throws AuditException;

	public String getVendorCurrentStatus(Integer vendorId) throws BusinessServiceException;
	
	public String getResourceStatus(Integer resourceId ) throws BusinessServiceException;
	
	public String getResourceCredentialStatus(Integer resourceId) throws BusinessServiceException;
	
	public String getVendorCredentialStatus(Integer vendorId) throws BusinessServiceException;
	
	public AuditVO getAuditWfStateReasonCd (AuditVO auditVO) throws AuditException;
	
	public ProcessResponse addVendorNote(VendorNotesVO vendorNotesVO) throws BusinessServiceException;
	
	public List<VendorResource> retrieveResourceForApproval() throws BusinessServiceException;
	
	public List<VendorHdr> retrieveCompaniesForApproval() throws BusinessServiceException;
	
	public String getResourceCredentialStatus(Integer resourceId, Integer keyId) throws BusinessServiceException;
	
	public String getVendorCredentialStatus(Integer vendorId, Integer keyId) throws BusinessServiceException;
	
	//R16_0: SL-21003 method added to fetch the reason code of a resource credential
	public String getResourceCredentialReasonCd(Integer resourceId, Integer keyId) throws BusinessServiceException;
	
	//R16_0: SL-21003 method added to fetch the reason code of a vendor credential
	public String getVendorCredentialReasonCd(Integer vendorId, Integer keyId) throws BusinessServiceException;
	
	public void handleVendorResourceUpdateError(AuditVO auditVO);
	public void handleVendorUpdateError(AuditVO auditVO);
}
