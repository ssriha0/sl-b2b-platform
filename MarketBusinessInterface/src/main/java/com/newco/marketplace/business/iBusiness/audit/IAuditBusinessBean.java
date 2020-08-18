/*
 ** AuditBusinessBean.java     1.0     2007/06/05
 */
package com.newco.marketplace.business.iBusiness.audit;

import java.util.List;

import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;


public interface IAuditBusinessBean  extends AuditStatesInterface {
    public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException ;

    public void auditVendorHeader(int vendorId, String state);

    public void auditVendorHeaderByUserId(String userName, String state);

    public void auditVendorHeader(AuditVO auditVO) throws AuditException ;
    public void auditVendorResource(int resourceId, String state) ;    
    public void auditVendorResource(AuditVO auditVO) throws AuditException ;
    public void auditVendorCredentials(int vendorId, int vendorCredId, String state) ;
    public void auditVendorCredentials(AuditVO auditVO) throws AuditException ;
    public void auditResourceCredentials(int resourceId, int resourceCredId, String state) ;
    public void auditResourceCredentials(AuditVO auditVO) throws AuditException ;
    public void auditResourceBackgroundCheck(int resourceId, String state) throws AuditException ;
    public void auditResourceBackgroundCheck(AuditVO auditVO) throws AuditException ;
    public AuditVO getResourceAudit(AuditVO auditVO) throws AuditException ;
    public AuditVO getVendorAudit(AuditVO auditVO) throws AuditException ;
    public List<WorkflowStateVO> getWorkflowStates() ;
    public String getVendorComplianceStatus(int vendorId) throws AuditException ;
    public String submitVendorForCompliance(int vendorId) throws AuditException ;
}// end class
