package com.newco.marketplace.translator.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractIncidentAck entity provides the base persistence definition of the
 * IncidentAck entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractIncidentAck implements java.io.Serializable {

	private static final long serialVersionUID = 3112637746119777461L;
	
	// Fields

	private Integer incidentAckId;
	private Incident incident;
	private Date receivedDateTime;
	private String ackFileName;
	private String incidentStatus;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractIncidentAck() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractIncidentAck(Integer incidentAckId, Incident incident,
			Date receivedDateTime, String ackFileName,
			String incidentStatus) {
		this.incidentAckId = incidentAckId;
		this.incident = incident;
		this.receivedDateTime = receivedDateTime;
		this.ackFileName = ackFileName;
		this.incidentStatus = incidentStatus;
	}

	/** full constructor */
	public AbstractIncidentAck(Integer incidentAckId, Incident incident,
			Date receivedDateTime, String ackFileName,
			String incidentStatus, Date createdDate,
			Date modifiedDate, String modifiedBy) {
		this.incidentAckId = incidentAckId;
		this.incident = incident;
		this.receivedDateTime = receivedDateTime;
		this.ackFileName = ackFileName;
		this.incidentStatus = incidentStatus;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_ack_id", unique = true, nullable = false)
	public Integer getIncidentAckId() {
		return this.incidentAckId;
	}

	public void setIncidentAckId(Integer incidentAckId) {
		this.incidentAckId = incidentAckId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "incident_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Incident getIncident() {
		return this.incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "received_date_time", nullable = false, length = 19)
	public Date getReceivedDateTime() {
		return this.receivedDateTime;
	}

	public void setReceivedDateTime(Date receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}

	@Column(name = "ack_file_name", nullable = false)
	public String getAckFileName() {
		return this.ackFileName;
	}

	public void setAckFileName(String ackFileName) {
		this.ackFileName = ackFileName;
	}

	@Column(name = "incident_status", nullable = false, length = 50)
	public String getIncidentStatus() {
		return this.incidentStatus;
	}

	public void setIncidentStatus(String incidentStatus) {
		this.incidentStatus = incidentStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}