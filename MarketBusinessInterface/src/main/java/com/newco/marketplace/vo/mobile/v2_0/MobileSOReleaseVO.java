package com.newco.marketplace.vo.mobile.v2_0;

import com.newco.marketplace.api.beans.Results;

public class MobileSOReleaseVO {
	
	//private String soId;
	//private String groupId;
	/*private String reason;
	private String reasonDesc;
	private String reasonCommentDesc;	
	private Resource resourceList;
	private Boolean releaseByFirmInd;
	private Results results;*/
	
	private String soId;
	private String firmId;
	private String reason;
	private String comments;	
	private Boolean releaseByFirmInd;
	private Integer resourceId;
	private Integer statusId;
	private String assignmentType;
	private String releaseByName;
	
	private Integer releaseResourceId;
	private String reasonDesc;
	private Integer roleId;

	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	public Results results;
	
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Boolean getReleaseByFirmInd() {
		return releaseByFirmInd;
	}
	public void setReleaseByFirmInd(Boolean releaseByFirmInd) {
		this.releaseByFirmInd = releaseByFirmInd;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public String getReleaseByName() {
		return releaseByName;
	}
	public void setReleaseByName(String releaseByName) {
		this.releaseByName = releaseByName;
	}
	public Integer getReleaseResourceId() {
		return releaseResourceId;
	}
	public void setReleaseResourceId(Integer releaseResourceId) {
		this.releaseResourceId = releaseResourceId;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
}
