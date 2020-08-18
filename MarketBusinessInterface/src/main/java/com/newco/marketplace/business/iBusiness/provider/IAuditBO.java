package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;

import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;

/**
 * @author KSudhanshu
 *
 */
public interface IAuditBO extends IAuditStates{
	
    public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException ;
    public void auditVendorHeader(int vendorId, String state) ;
    public void auditVendorHeaderByUserId(String userName, String state) ;
    public void auditVendorHeader(AuditVO auditVO,boolean vendorApprovalFlag) throws AuditException ;
    public void auditVendorResource(int resourceId, String state) ;
    public void auditVendorResource(AuditVO auditVO) throws AuditException ;
    public void auditVendorCredentials(int vendorId, int vendorCredId, String state) ;
    public void auditVendorCredentials(AuditVO auditVO) throws AuditException ;
    public void auditResourceCredentials(int resourceId, int resourceCredId, String state) ;
    public void auditResourceCredentials(AuditVO auditVO) throws AuditException ;
    public void auditResourceBackgroundCheck(int resourceId, String state) throws AuditException ;
    public void auditResourceBackgroundCheck(AuditVO auditVO) throws AuditException ;
 
    public List<WorkflowStateVO> getWorkflowStates() ;
    public String getVendorComplianceStatus(int vendorId) throws AuditException ;
    public String submitVendorForCompliance(int vendorId) throws AuditException ;
    public String getVendorResourceStatus(String resourceId) throws AuditException;
    public int updateStatusVendorResource(int resourceId, String state) throws AuditException;
    public String getVendorHeaderStatus(String vendorId) throws AuditException;
    
   // R11_0
    public void auditResourceBackgroundReCheck(int resourceId, String state,String recertificationIndParam) throws AuditException ;
   //sl-21142
	public void saveVendorOrResourceCredReasonCd(AuditVO auditVO2) throws AuditException;
	public void deleteVendorOrResourceCredReasonCd(AuditVO auditVO) throws AuditException;
}
