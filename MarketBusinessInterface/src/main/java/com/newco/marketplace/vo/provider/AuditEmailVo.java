package com.newco.marketplace.vo.provider;

import java.util.HashMap;
import java.util.Map;

public class AuditEmailVo extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 2787664023495113689L;
	private String to = null;
	private String from = null;
	private String subject = null;
	private String message = null;

	private String firstName = null;
	private String lastName = null;
	private String middleName = null;

	private String resFirstName=null;
	private String resLastName=null;

	private String logoPath;

	private HashMap<String, String> reasonMap = null;
	private String reasonDescription = "";
	private String reasonDescriptionParsed = "";

	private AuditTemplateMappingVo templateMapping = null;
	private AuditProfileVO auditProfileVO= null;

	private Integer vendorId = null;
	private Integer resourceId = null;
	private Integer credentialId = null;

	private String workflowEntity = null;
	private String targetStateName = "";
	private Integer targetStateId = null;

	// fields required by templates.
	private String resourceName = "";
	private String auditCredentialType = "";
	private String credentialCategory = "";
	private String credentialType = "";
	private String credentialName = "";
	private String credentialNumber = "";

	//company name
	private String businessName = null;
	
	//Change in Email Name address - Rebranding
	private String firstNameLastName = "";
	
	private String  reviewComments;
	
	public String getFirstNameLastName() {
		return firstNameLastName;
	}
	public void setFirstNameLastName(String firstNameLastName) {
		this.firstNameLastName = firstNameLastName;
	}
	//End of Change in Email Name address - Rebranding



	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public AuditEmailVo (AuditProfileVO auditProfileRequest) {
		super();
		setAuditProfileVO(auditProfileRequest);
	}

	public AuditEmailVo () {
		super();
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public HashMap<String, String> getReasonMap() {
		if (reasonMap == null) {
			reasonMap = new HashMap<String, String>();
		}
		return reasonMap;
	}

	public void setReasonMap(HashMap<String, String> reasonMap) {
		this.reasonMap = reasonMap;
	}

	public void addReason (String reasonCode, String reasonDesc) {
		getReasonMap().put(reasonCode, reasonDesc);
		StringBuffer sb = new StringBuffer (reasonDescription);
		sb.append("\n " + reasonDesc + " \n");
		reasonDescription = sb.toString();
	}//addReason 

	public String getReasonDescription() {
		try{
            StringBuffer rvSB = new StringBuffer();
            for (Map.Entry<String, String> entry : reasonMap.entrySet()) {
                if (!entry.getValue().equals("N//A")) {
                    rvSB.append("\n " + entry.getValue() + " \n");
                }
            }// getReasonDescription()
            reasonDescription = rvSB.toString();
            return (reasonDescription);
        }
		catch(Throwable t){
			return getReasonDescriptionLogger();
		}
	}// getReasonDescription
	public AuditTemplateMappingVo getTemplateMapping() {
		return templateMapping;
	}
	
	public String getReasonDescriptionLogger(){
		  return (reasonDescription);
	}
	
	public void setTemplateMapping(AuditTemplateMappingVo templateMapping) {
			this.templateMapping = templateMapping;
	}
	public AuditProfileVO getAuditProfileVO() {
		return auditProfileVO;
	}
	public void setAuditProfileVO(AuditProfileVO auditProfileVO) {
		this.auditProfileVO = auditProfileVO;
		//this.targetStateId = auditProfileRequest.getApForm().getWfStatusId();
		//this.targetStateName = auditProfileRequest.getApForm().getWfStatusId();
	}
	public String getWorkflowEntity() {
		return workflowEntity;
	}
	public void setWorkflowEntity(String workflowEntity) {
		this.workflowEntity = workflowEntity;
	}
	public String getTargetStateName() {
		return targetStateName;
	}
	public void setTargetStateName(String targetStateName) {
		this.targetStateName = targetStateName;
	}
	public Integer getTargetStateId() {
		return targetStateId;
	}
	public void setTargetStateId(Integer targetStateId) {
		this.targetStateId = targetStateId;
	}

	@Override
	public String toString () {
		return (" \n<AuditEmailVO> " + " | " +
				auditProfileVO + " | " + to
				+ " | " + from +
				" | " + message +

				" | " + reasonDescription +
				" | " + firstNameLastName
				+ " | " + templateMapping +
				" | " + workflowEntity + " | "
				+ targetStateName +
				" | " + targetStateId + " | SUBJECT"
				+subject +
				" | NAME  "
				+ resourceName
				+ "   " +  " | "
				+ " vendor Id    "
				+ vendorId
				+ " | resourceId  "
				+ resourceId
				+ " | credential Id   "
				+ credentialId
				+ " | credential Number  \n  "
				+ credentialNumber
				+"</AuditEmailVO> \n");
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String name) {
		this.resourceName = name;
	}
	public String getAuditCredentialType() {
		return auditCredentialType;
	}
	public void setAuditCredentialType(String auditCredentialType) {
		this.auditCredentialType = auditCredentialType;
	}
	public String getCredentialName() {
		return credentialName;
	}
	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}
	public String getCredentialType() {
		return credentialType;
	}
	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getCredentialId() {
		return credentialId;
	}
	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}
	public String getCredentialCategory() {
		return credentialCategory;
	}
	public void setCredentialCategory(String credentialCategory) {
		this.credentialCategory = credentialCategory;
	}
	public String getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	public String getResFirstName() {
		return resFirstName;
	}
	public void setResFirstName(String resFirstName) {
		this.resFirstName = resFirstName;
	}
	public String getResLastName() {
		return resLastName;
	}
	public void setResLastName(String resLastName) {
		this.resLastName = resLastName;
	}
	public String getReasonDescriptionParsed() {
		return reasonDescriptionParsed;
	}
	public void setReasonDescriptionParsed(String reasonDescriptionParsed) {
		this.reasonDescriptionParsed = reasonDescriptionParsed;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getReviewComments() {
		return reviewComments;
	}
	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}
}
