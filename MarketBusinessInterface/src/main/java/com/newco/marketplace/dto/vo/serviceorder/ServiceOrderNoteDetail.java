package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderNoteDetail extends CommonVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4747283017341782915L;
	private Integer noteId;
	private String noteTypeDs;
	private String note;
	private Integer refNoteId;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public String getNoteTypeDs() {
		return noteTypeDs;
	}
	public void setNoteTypeDs(String noteTypeDs) {
		this.noteTypeDs = noteTypeDs;
	}
	public Integer getRefNoteId() {
		return refNoteId;
	}
	public void setRefNoteId(Integer refNoteId) {
		this.refNoteId = refNoteId;
	}
	

}
