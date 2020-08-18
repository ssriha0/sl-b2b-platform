package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_schedule_history")
@XmlRootElement()
public class SOScheduleHistory extends SOChild{

/**
	 * 
	 */
	private static final long serialVersionUID = -739742038593626719L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_schedule_history_id")
	private Integer soScheduleHistoryId;

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

	public Integer getSoScheduleHistoryId() {
		return soScheduleHistoryId;
	}

	public void setSoScheduleHistoryId(Integer soScheduleHistoryId) {
		this.soScheduleHistoryId = soScheduleHistoryId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

}
