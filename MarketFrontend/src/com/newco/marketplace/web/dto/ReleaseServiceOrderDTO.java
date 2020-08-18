package com.newco.marketplace.web.dto;



/**
 * @author zizrale
 *
 */
public class ReleaseServiceOrderDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1890786775085999556L;
	private String soId;
	private Integer resourceId;
	private Integer releaseReasonCode;
	private String comment;
	private Integer statusId;
	private Integer role;
	private Integer providerRespId;
	private Integer vendorId;
	private Integer releaseFromFirmInd;
	private Integer reasonCode;
	private String reasonText;
	private String releaseByName;
	private Integer releaseById;
	
	public String getReleaseByName() {
		return releaseByName;
	}
	public void setReleaseByName(String releaseByName) {
		this.releaseByName = releaseByName;
	}
	public Integer getReleaseById() {
		return releaseById;
	}
	public void setReleaseById(Integer releaseById) {
		this.releaseById = releaseById;
	}
	public String getReasonText() {
		return reasonText;
	}
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public Integer getReleaseReasonCode() {
		return releaseReasonCode;
	}
	public void setReleaseReasonCode(Integer releaseReasonCode) {
		this.releaseReasonCode = releaseReasonCode;
	}
	public Integer getReleaseFromFirmInd() {
		return releaseFromFirmInd;
	}
	public void setReleaseFromFirmInd(Integer releaseFromFirmInd) {
		this.releaseFromFirmInd = releaseFromFirmInd;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getProviderRespId() {
		return providerRespId;
	}
	public void setProviderRespId(Integer providerRespId) {
		this.providerRespId = providerRespId;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

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
	
}
