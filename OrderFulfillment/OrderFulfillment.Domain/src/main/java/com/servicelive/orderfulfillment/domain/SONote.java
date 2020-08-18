package com.servicelive.orderfulfillment.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_notes")
@XmlRootElement()
public class SONote extends SOChild {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607371121739495695L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private Long noteId;
	
	@Transient
	private Date deletedDate;

	@Column(name = "note_subject")
	private String subject;

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "note")
	private String note;

	@Column(name = "created_by_name")
	private String createdByName;

	@Column(name = "note_type_id")
	private Integer noteTypeId;

	@Column(name = "entity_id")
	private Long entityId;

	@Column(name = "private_ind")
	private boolean privateId = false;
	
	//SL-19050
	//Read indicator for so notes
	@Column(name = "read_ind")
	private Integer readInd;

    @Transient
    private boolean sendEmail = false;
    
    @Transient
    private boolean isMARnote = false;

    public SONote() {
		super();
	}

	public SONote(String note, String subject) {
		super();
		this.note = note;
		this.subject = subject;
	}
	
	public Integer getReadInd() {
		return readInd;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public Long getEntityId() {
		return entityId;
	}

	public String getNote() {
		return note;
	}

	public Long getNoteId() {
		return noteId;
	}

	public Integer getNoteTypeId() {
		return noteTypeId;
	}

	public boolean isPrivate() {
		return privateId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getSubject() {
		return subject;
	}

	@XmlElement
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	@XmlElement
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	@XmlElement
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	@XmlElement
	public void setNote(String note) {
		this.note = note;
	}

	@XmlElement
	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	@XmlElement
	public void setNoteTypeId(Integer noteTypeId) {
		this.noteTypeId = noteTypeId;
	}

	@XmlElement
	public void setPrivate(boolean privateId) {
		this.privateId = privateId;
	}

	@XmlElement
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@XmlElement
	public void setSubject(String subject) {
		this.subject = subject;
	}

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object aThat){
        if ( this == aThat ) return true;
        if ( !(aThat instanceof SONote) ) return false;

        SONote o = (SONote)aThat;
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
	public SONote copy() {
		SONote note = new SONote();
		note.createdByName = this.createdByName;
		note.deletedDate = this.deletedDate;
		note.entityId = this.entityId;
		note.noteTypeId = this.noteTypeId;
		note.privateId = this.privateId;
		note.roleId = this.roleId;
		note.subject = this.subject;
		note.note = this.note;
		note.readInd  = this.readInd;
 		return note;
	}

	@XmlElement
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @XmlElement
	public void setReadInd(Integer readInd) {
		this.readInd = readInd;
	}
    
    public boolean isSendEmail() {
        return sendEmail;
    }

	public boolean isMARnote() {
		return isMARnote;
	}

	public void setMARnote(boolean isMARnote) {
		this.isMARnote = isMARnote;
	}
}
