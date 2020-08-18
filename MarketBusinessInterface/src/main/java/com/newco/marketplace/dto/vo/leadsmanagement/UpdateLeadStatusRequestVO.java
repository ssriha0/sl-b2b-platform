package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

public class UpdateLeadStatusRequestVO {

	private String leadId;
	private String status;
  	public String firmId;
	public String firmStatus;
	public Integer statusId;
	public Integer firmStatusId;
	public Date modifiedDate;
	public String modifiedBy;
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFirmStatus() {
		return firmStatus;
	}
	public void setFirmStatus(String firmStatus) {
		this.firmStatus = firmStatus;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Integer getFirmStatusId() {
		return firmStatusId;
	}
	public void setFirmStatusId(Integer firmStatusId) {
		this.firmStatusId = firmStatusId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	  public String getFirmId() {
			return firmId;
		}
		public void setFirmId(String firmId) {
			this.firmId = firmId;
		}
}
