package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "so_events")
public class SOEvent extends SOChild {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5333165160232381996L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long soEventId;
	
	@Column(name = "resource_id")
	private Long resourceId;
	
	@Column(name = "event_type_id")
	private Integer eventTypeId;
	
	@Column(name = "event_reason_code")
	private Integer eventReasonCode;
	
	@Column(name = "process_ind")
	private String processInd;
	
	@Column(name = "logical_delete")
	private Integer logicalDelete;

	public Long getSoEventId() {
		return soEventId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public Integer getEventTypeId() {
		return eventTypeId;
	}

	public Integer getEventReasonCode() {
		return eventReasonCode;
	}

	public String getProcessInd() {
		return processInd;
	}

	public Integer getLogicalDelete() {
		return logicalDelete;
	}

	@XmlElement()
	public void setSoEventId(Long soEventId) {
		this.soEventId = soEventId;
	}

	@XmlElement()
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@XmlElement()
	public void setEventTypeId(Integer eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	@XmlElement()
	public void setEventReasonCode(Integer eventReasonCode) {
		this.eventReasonCode = eventReasonCode;
	}

	@XmlElement()
	public void setProcessInd(String processInd) {
		this.processInd = processInd;
	}

	@XmlElement()
	public void setLogicalDelete(Integer logicalDelete) {
		this.logicalDelete = logicalDelete;
	}
	
}
