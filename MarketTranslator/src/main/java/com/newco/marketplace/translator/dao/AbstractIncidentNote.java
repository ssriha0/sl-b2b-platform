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

@MappedSuperclass
public abstract class AbstractIncidentNote {
	
	private Integer incidentNoteID;
	private Incident incident;
	private Date createdDate;
	private String note;
	private String source;
	
	public AbstractIncidentNote() {
		// intentionally blank
	}
	public AbstractIncidentNote(Incident incident, Date createdDate,
			String note, String source) {
		super();
		this.incident = incident;
		this.createdDate = createdDate;
		this.note = note;
		this.source = source;
	}
	
	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_note_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getIncidentNoteID() {
		return incidentNoteID;
	}
	
	public void setIncidentNoteID(Integer incidentNoteID) {
		this.incidentNoteID = incidentNoteID;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "incident_id", unique = false, nullable = false, insertable = true, updatable = true)
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	@Temporal (TemporalType.TIMESTAMP)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "note", unique = false, nullable = false, insertable = true, updatable = true, length = 512)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "note_source", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
