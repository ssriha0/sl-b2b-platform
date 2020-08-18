package com.newco.marketplace.web.delegates;

import java.util.HashMap;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ICommonAuditorDelegate {

	public void approveProviderOrServicePro(AuditVO auditVO)
			throws BusinessServiceException;

	public ProcessResponse addAuditorNote( VendorNotesVO vendorNote ) throws BusinessServiceException;

	public void approveProviderCredential(AuditVO auditVO)
			throws BusinessServiceException;

	public void approveTeamMemberCredential(AuditVO auditVO)
			throws BusinessServiceException;
	
	public String getResourceCredentialStatus(Integer resourceId) throws BusinessServiceException;
	
	public String getResourceCredentialStatus(Integer resourceId, Integer keyId) throws BusinessServiceException;
	//R16_0: SL-21003 method added to fetch the reason code of a resource credential
	public String getResourceCredentialReasonCd(Integer resourceId, Integer keyId) throws BusinessServiceException;

	public String getVendorCredentialStatus(Integer vendorId) throws BusinessServiceException;
	
	public String getVendorCredentialStatus(Integer vendorId, Integer keyId) throws BusinessServiceException;
	//R16_0: SL-21003 method added to fetch the reason code of a vendor credential
	public String getVendorCredentialReasonCd(Integer vendorId, Integer keyId) throws BusinessServiceException;
	
	String getVendorCurrentStatus(Integer vendorId) throws BusinessServiceException;
	
	String getResourceCurrentStatus(Integer resourceId) throws BusinessServiceException;

	public AjaxResultsDTO approveProviderServiceProWithAJAXResponse(
			AuditVO auditVO, boolean resource) throws BusinessServiceException;

	public AjaxResultsDTO addAuditorNoteWithAJAXResponse( HashMap noteMap )
			throws BusinessServiceException;

	public AjaxResultsDTO approveProviderCredentialWithAJAXResponse(
			AuditVO auditVO, boolean resource) throws BusinessServiceException;

	public AjaxResultsDTO approveTeamMemberCredentialWithAJAXResponse(
			AuditVO auditVO) throws BusinessServiceException;

}
