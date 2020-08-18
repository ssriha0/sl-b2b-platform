package com.newco.marketplace.web.delegatesImpl.auditor;

import java.util.HashMap;

import com.newco.marketplace.business.iBusiness.audit.IAuditProfileBO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICommonAuditorDelegate;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class AuditorApprovalDelegateImpl implements ICommonAuditorDelegate{

	private IAuditProfileBO auditorBusinessService;
	
	public ProcessResponse addAuditorNote(VendorNotesVO vendorNote) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return getAuditorBusinessService().addVendorNote(vendorNote);
	}

	public void approveProviderCredential(AuditVO auditVO) throws BusinessServiceException {
		try {
			getAuditorBusinessService().updateWFStatusAndReasonCodes(auditVO, Boolean.FALSE);
		} catch (AuditException e) {
			throw new BusinessServiceException( e );
		}
	}

	public void approveProviderOrServicePro(AuditVO auditVO) throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			getAuditorBusinessService().updateWFStatusAndReasonCodes(auditVO, Boolean.FALSE);
			
		} catch (AuditException e) {
			throw new BusinessServiceException(e);
		}
	}

	public void approveTeamMemberCredential(AuditVO auditVO) throws BusinessServiceException {
		// TODO Auto-generated method stub
		
	}

	public AjaxResultsDTO addAuditorNoteWithAJAXResponse(HashMap noteMap) throws BusinessServiceException {
		AjaxResultsDTO aJaxResponse = new AjaxResultsDTO();
		VendorNotesVO note = new VendorNotesVO();
		note.setNote( (String)noteMap.get(SOConstants.NOTE));
		
		note.setVendorId((Integer)noteMap.get("VENDOR_ID"));
		note.setModifiedBy((String)noteMap.get("AUDITOR_ID"));
		ProcessResponse x = addAuditorNote(note);
		if(x.getCode() != null &&
		   x.getCode().equals("00")){
			aJaxResponse.setActionState(0);
			aJaxResponse.setResultMessage(x.getMessages().get(0));
		}else{
			aJaxResponse.setActionState(1);
			aJaxResponse.setResultMessage(x.getMessages().get(0));
		}
		return aJaxResponse;
	}

	public AjaxResultsDTO approveProviderCredentialWithAJAXResponse(AuditVO auditVO, boolean resource) throws BusinessServiceException {
		AjaxResultsDTO aJaxResponse = new AjaxResultsDTO();
		try {
			approveProviderCredential(auditVO);
			aJaxResponse.setActionState(0);
			String newStatus = null;
			if(resource)
			{
				 newStatus = getResourceCredentialStatus(auditVO.getResourceId());
			}
			else
			{
				newStatus = getVendorCredentialStatus(auditVO.getVendorId());
			}
			
			aJaxResponse.setResultMessage("Operation Successful");
			aJaxResponse.setAddtionalInfo2(newStatus);
			aJaxResponse.setAddtionalInfo3("provider_service_pro_cred");
		} catch (BusinessServiceException e) {
			aJaxResponse.setActionState(1);
			aJaxResponse.setResultMessage("Operation Failed");
			
		}
		return aJaxResponse;
	}

	public AjaxResultsDTO approveProviderServiceProWithAJAXResponse(AuditVO auditVO, boolean resource) throws BusinessServiceException {
		AjaxResultsDTO aJaxResponse = new AjaxResultsDTO();
		try {
			approveProviderOrServicePro(auditVO);
			aJaxResponse.setActionState(0);
			String newStatus = null;
			if(resource)
			{
				 newStatus = getResourceCurrentStatus(auditVO.getResourceId());
			}
			else
			{
				newStatus = getVendorCurrentStatus(auditVO.getVendorId());
			}
			aJaxResponse.setResultMessage("Operation Successful");
			aJaxResponse.setAddtionalInfo2(newStatus);
			aJaxResponse.setAddtionalInfo3("provider_service_pro");
		} catch (BusinessServiceException e) {
			e.printStackTrace();
			aJaxResponse.setActionState(1);
			aJaxResponse.setResultMessage("Operation Failed");
		}
		return aJaxResponse;
	}

	public AjaxResultsDTO approveTeamMemberCredentialWithAJAXResponse(AuditVO auditVO) throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public IAuditProfileBO getAuditorBusinessService() {
		return auditorBusinessService;
	}

	public void setAuditorBusinessService(IAuditProfileBO auditorBusinessService) {
		this.auditorBusinessService = auditorBusinessService;
	}

	public String getVendorCurrentStatus(Integer vendorId) throws BusinessServiceException {
		return getAuditorBusinessService().getVendorCurrentStatus(vendorId);	
	}
	
	
	public String getResourceCredentialStatus(Integer resourceId) throws BusinessServiceException {
		return getAuditorBusinessService().getResourceCredentialStatus(resourceId);
	}

	public String getVendorCredentialStatus(Integer vendorId) throws BusinessServiceException {
		return getAuditorBusinessService().getVendorCredentialStatus(vendorId);
	}

	public String getResourceCurrentStatus(Integer resourceId) throws BusinessServiceException {
		try {
			return getAuditorBusinessService().getResourceStatus(resourceId);
		} catch (BusinessServiceException e) {
			 throw new BusinessServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ICommonAuditorDelegate#getResourceCredentialStatus(java.lang.Integer, java.lang.Integer)
	 */
	public String getResourceCredentialStatus(Integer resourceId, Integer keyId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return getAuditorBusinessService().getResourceCredentialStatus(resourceId, keyId);
	}
	
	public String getVendorCredentialStatus(Integer vendorId, Integer keyId) throws BusinessServiceException {
		return getAuditorBusinessService().getVendorCredentialStatus(vendorId, keyId);
	}
	
	public String getResourceCredentialReasonCd(Integer resourceId, Integer keyId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return getAuditorBusinessService().getResourceCredentialReasonCd(resourceId, keyId);
	}
	public String getVendorCredentialReasonCd(Integer resourceId, Integer keyId)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return getAuditorBusinessService().getVendorCredentialReasonCd(resourceId, keyId);
	}
	
}
