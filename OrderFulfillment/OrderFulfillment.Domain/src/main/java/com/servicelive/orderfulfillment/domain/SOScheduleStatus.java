package com.servicelive.orderfulfillment.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_schedule")
@XmlRootElement()
public class SOScheduleStatus{


	/*
	 * Unfortunately JPA does not allow a PrimaryKey Object to participate in 
	 * Entity Associations. As noted by Christian Bauer & King (pg. 281)
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -4759749672925739358L;

	@Id @Column(name="so_id", unique=true, nullable=false)
	private String soId;

	@Column(name = "source")
	private String source;
	
	@Column(name = "schedule_status_id")
	private Integer scheduleStatusId;
	
	@Column(name = "resp_reason_id")
	private Integer respReasonId;
	
	@Column(name = "vendor_id")
	private Integer vendorId;
	
	@Column(name = "customer_appt_confirm_ind")
	private Integer custApptConfirmInd; 
	
	@Column(name ="expected_time_of_arrival")
	private String eta;
	
	@Column(name ="created_date")
	private Date createdDate;
	
	@Column(name ="modified_date")
	private Date modifiedDate;
	
	@Column(name ="modified_by")
	private String modifiedBy;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getScheduleStatusId() {
		return scheduleStatusId;
	}

	public void setScheduleStatusId(Integer scheduleStatusId) {
		this.scheduleStatusId = scheduleStatusId;
	}

	public Integer getRespReasonId() {
		return respReasonId;
	}

	public void setRespReasonId(Integer respReasonId) {
		this.respReasonId = respReasonId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getCustApptConfirmInd() {
		return custApptConfirmInd;
	}

	public void setCustApptConfirmInd(Integer custApptConfirmInd) {
		this.custApptConfirmInd = custApptConfirmInd;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
