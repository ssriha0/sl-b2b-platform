package com.newco.marketplace.business.iBusiness.provider;

public interface IAuditStates {
    public final String VENDOR = "Company Profile";                   
    public final String RESOURCE = "Team Member";                 
    public final String RESOURCE_BACKGROUND_CHECK = "Team Member Background Check";
    public final String RESOURCE_CREDENTIAL = "Team Member Credential"; 
    public final String VENDOR_CREDENTIAL = "Company Credential";        
    public final String VENDOR_BACKGROUND_CHECK = "Company Background Check";
    
    public final String VENDOR_INCOMPLETE = "Incomplete";                   
    public final String VENDOR_PENDING_APPROVAL = "Pending Approval";
    public final String VENDOR_APPROVED_MR = "Approved (market ready)";
    public final String VENDOR_PENDING_FIELD_MANAGER_APPROVAL = "Pending Field Manager Approval";
    public final String VENDOR_PENDING_COMPLIANCE_APPROVAL = "Pending Compliance Approval";
    public final String VENDOR_OUT_OF_COMPLIANCE = "Out of Compliance";
    public final String VENDOR_DECLINED = "Declined";
    public final String VENDOR_TEMPERORILY_SUSPENDED = "Temporarily Suspended";
    public final String VENDOR_TERMINATED = "Terminated";

    public final String RESOURCE_INCOMPLETE = "Incomplete";                 
    public final String RESOURCE_PENDING_APPROVAL = "Pending Approval";                 
    public final String RESOURCE_OUT_OF_COMPLIANCE = "Out of Compliance";
    public final String RESOURCE_DECLINED = "Declined";
    public final String RESOURCE_APPROVED_MR = "Approved (market ready)";
    public final String RESOURCE_TEMPORARILY_SUSPENDED = "Temporarily Suspended";
    public final String RESOURCE_TERMINATED = "Terminated";
    
    public final String RESOURCE_BACKGROUND_CHECK_INCOMPLETE = "Not Started";
    public final String RESOURCE_BACKGROUND_CHECK_PENDING_SUBMISSION = "Pending Submission";
    public final String RESOURCE_BACKGROUND_CHECK_INPROCESS = "In Process";
    public final String RESOURCE_BACKGROUND_CHECK_CLEAR = "Clear";
    public final String RESOURCE_BACKGROUND_CHECK_ADVERSE_FINDINGS = "Not Cleared";
        
    public final String RESOURCE_CREDENTIAL_PENDING_APPROVAL = "Pending Approval"; 
    public final String RESOURCE_CREDENTIAL_APPROVED = "Approved";
    public final String RESOURCE_CREDENTIAL_OUT_OF_COMPLIANCE = "Out of Compliance";
    
    public final String VENDOR_CREDENTIAL_PENDING_APPROVAL = "Pending Approval";        
    public final String VENDOR_CREDENTIAL_APPROVED = "Approved";
    public final String VENDOR_CREDENTIAL_OUT_OF_COMPLIANCE = "Out of Compliance";
    
    public final String VENDOR_BACKGROUND_CHECK_INCOMPLETE = "Incomplete";
    public final String VENDOR_BACKGROUND_CHECK_INPROCESS = "In Process";
    public final String VENDOR_BACKGROUND_CHECK_PASSED = "Passed";
    public final String VENDOR_BACKGROUND_CHECK_FAILED = "Failed";    
    public final String VENDOR_BACKGROND_CHECK_OUT_OF_COMPLIANCE = "Out of Compliance";
    
    public final int TRUE = 1;
    public final int FALSE = 0;
    
}//auditStatesInterface
