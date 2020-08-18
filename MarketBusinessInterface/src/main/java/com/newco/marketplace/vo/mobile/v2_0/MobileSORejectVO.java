package com.newco.marketplace.vo.mobile.v2_0;

import com.newco.marketplace.api.beans.Results;

/**
 * This class would act as a Value Object class for Provider Reject Service Order
 * 
 * @author Infosys
 * @version 1.0
 * @Date 2015/04/16
 */
public class MobileSORejectVO {
	
	private String soId;
	private String groupId;
	private Integer reasonId;
	private String reasonDesc;
	private String reasonCommentDesc;	
	private ResourceIds resourceList;
	private Boolean releaseByFirmInd;
	private Integer roleId;
	private Integer resourceId;
	private String firmId;

	private Results results;
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getReasonCommentDesc() {
		return reasonCommentDesc;
	}
	public void setReasonCommentDesc(String reasonCommentDesc) {
		this.reasonCommentDesc = reasonCommentDesc;
	}
	
	public ResourceIds getResourceList() {
		return resourceList;
	}
	public void setResourceList(ResourceIds resourceList) {
		this.resourceList = resourceList;
	}
	public Boolean getReleaseByFirmInd() {
		return releaseByFirmInd;
	}
	public void setReleaseByFirmInd(Boolean releaseByFirmInd) {
		this.releaseByFirmInd = releaseByFirmInd;
	}
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	} 
	
	
	


}
