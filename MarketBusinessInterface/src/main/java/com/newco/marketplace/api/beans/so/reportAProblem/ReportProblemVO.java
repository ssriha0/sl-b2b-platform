package com.newco.marketplace.api.beans.so.reportAProblem;

import com.newco.marketplace.api.mobile.common.ResultsCode;


public class ReportProblemVO {
	private String soId;
	private Integer resourceId;
	private Integer firmId;
	private String problemDescriptionComments;
	private Integer problemReasonCode;
	private String problemReasonCodeDescription;
	private String resolutionComments;
	// Result of validation
	private ResultsCode validationCode;
	// Differentiate reportAProblem/Issue Resolution.
	private String type;
	private String reAssignReason;
	private Integer roleId;


	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public Integer getProblemReasonCode() {
		return problemReasonCode;
	}

	public void setProblemReasonCode(Integer problemReasonCode) {
		this.problemReasonCode = problemReasonCode;
	}

	public String getProblemReasonCodeDescription() {
		return problemReasonCodeDescription;
	}

	public void setProblemReasonCodeDescription(String problemReasonCodeDescription) {
		this.problemReasonCodeDescription = problemReasonCodeDescription;
	}

	public String getProblemDescriptionComments() {
		return problemDescriptionComments;
	}

	public void setProblemDescriptionComments(String problemDescriptionComments) {
		this.problemDescriptionComments = problemDescriptionComments;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getReAssignReason() {
		return reAssignReason;
	}

	public void setReAssignReason(String reAssignReason) {
		this.reAssignReason = reAssignReason;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	

}
