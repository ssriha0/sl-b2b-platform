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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A part associated with an incident
 * @author gjackson
 *
 */
@MappedSuperclass
public abstract class AbstractIncidentPart {
	
	/**
	 * What kind of part - hard drive, memory,etc
	 */
	private String classCode;
	private String classComments;
	private Date createdDate;
	private IncidentEvent incidentEvent;
	/**
	 * Unique identifier for an incident part
	 */
	private Integer incidentPartID;
	private String OEMNumber;
	private String partComments;
	private String partNumber;
	
	/**
	 * Min Constructor
	 */
	public AbstractIncidentPart() {
		// intentionally blank
	}
	
	/**
	 * Full Contructor
	 * @param incidentPartID
	 * @param incident
	 * @param classCode
	 * @param classComments
	 * @param partNumber
	 * @param number
	 * @param partComments
	 * @param createdDate
	 */
	public AbstractIncidentPart(Integer incidentPartID, IncidentEvent incidentEvent,
			String classCode, String classComments, String partNumber,
			String number, String partComments, Date createdDate) {
		super();
		this.incidentPartID = incidentPartID;
		this.incidentEvent = incidentEvent;
		this.classCode = classCode;
		this.classComments = classComments;
		this.partNumber = partNumber;
		OEMNumber = number;
		this.partComments = partComments;
		this.createdDate = createdDate;
	}

	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_part_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getIncidentPartID() {
		return incidentPartID;
	}

	public void setIncidentPartID(Integer incidentPartID) {
		this.incidentPartID = incidentPartID;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "incident_id", unique = false, nullable = false, insertable = true, updatable = true)
	public IncidentEvent getIncidentEvent() {
		return incidentEvent;
	}

	public void setIncidentEvent(IncidentEvent incidentEvent) {
		this.incidentEvent = incidentEvent;
	}

	@Column(name = "class_code", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(name = "class_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getClassComments() {
		return classComments;
	}

	public void setClassComments(String classComments) {
		this.classComments = classComments;
	}

	@Column(name = "part_number", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Column(name = "oem_number", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getOEMNumber() {
		return OEMNumber;
	}

	public void setOEMNumber(String number) {
		OEMNumber = number;
	}


	@Column(name = "part_comments", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getPartComments() {
		return partComments;
	}

	public void setPartComments(String partComments) {
		this.partComments = partComments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
