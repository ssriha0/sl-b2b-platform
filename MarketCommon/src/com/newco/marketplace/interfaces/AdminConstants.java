/**
 * 
 */
package com.newco.marketplace.interfaces;

/**
 * @author zizrale
 *
 */
public interface AdminConstants {
    public static final String AUDITOR_QUEUE_BUSINESS_BEAN = "auditorQueueBusinessBean";
    
    // status types
    public static final String COMPANY_BACKGROUND_CHECK = "Company Background Check";
    public static final String COMPANY_CREDENTIAL = "Company Credential";
    public static final String COMPANY_PROFILE = "Company Profile";
    public static final String TEAM_MEMBER_BACKGROUND_CHECK = "Team Member Background Check";
    public static final String TEAM_MEMBER_CREDENTIAL = "Team Member Credential";
    public static final String TEAM_MEMBER = "Team Member";
    public static final String OUT_OF_COMPLIANCE = "Out of Compliance";
    public static final String TEAM_MEMBER_APPROVED = "Approved (market ready)";
    public static final String SERVICE_LIVE_APPROVED = "ServiceLive Approved";
    // key types
	public static final String VENDOR_ID = "vendor_id";
	public static final String VENDOR_CRED_ID = "vendor_cred_id";
	public static final String RESOURCE_ID = "resource_id";
	public static final String RESOURCE_CRED_ID = "resource_cred_id";
	public static final String DOCUMENT_ID = "document_id";
	
	// used to see if a link needs to be displayed - if status starts with Pending
	public static final String PENDING = "pending";
	public static final String SYSTEM_USER = "SYSTEM";
	
	public static final String NUMBER_OF_APPROVED_RESOURCES = "number_of_approved_resources";
	public static final String NUMBER_OF_UNAPPROVED_RESOURCES = "number_of_unapproved_resources";
    
	public static final Integer TEAM_MEMBER_APPROVED_ID = 6;
	public static final Integer COMPANY_APPROVED_ID = 34;
	public static final Integer AUDIT_LINK_ID_TEAM_MEMBER = 2;
	public static final Integer AUDIT_LINK_ID_COMPANY = 1;
	public static final Integer AUDIT_LINK_ID_PROVIDER = 2;
}
