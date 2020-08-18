package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * 
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: ServiceOrderNote.java,v $
 * Revision 1.16  2008/04/26 00:40:08  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.14.12.1  2008/04/23 11:41:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.15  2008/04/23 05:17:05  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.14  2008/02/14 23:44:31  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.13.4.1  2008/02/08 02:32:06  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.13  2008/01/24 02:13:50  mhaye05
 * replaced List of noteIds with List of noteTypeIds
 *
 * Revision 1.12  2008/01/24 00:27:54  mhaye05
 * added attribute List<Integer> noteIds
 *
 */
public class ServiceOrderNote extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607371121739495695L;
	private String soId;
	private Long noteId;
	private Date createdDate;
	private Date deletedDate;
	private String subject;
	private Integer roleId;
	private String note;
	private String createdByName;
	private Date modifiedDate;
	private String modifiedBy;
	private Integer noteTypeId;
	private Integer entityId;
	private int privateId = -1;
	private List<Integer> noteTypeIds;
	private boolean emptyNoteAllowed;
	
	//SL-19050
	//Indicators added for fetching read_ind,buyer_id 
	//and whether the user is provider or not from DB
	private Integer readInd;
	private Long buyerId;
	private Integer providerInd;

	

	public Integer getProviderInd() {
		return providerInd;
	}

	public void setProviderInd(Integer providerInd) {
		this.providerInd = providerInd;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}



	public Integer getReadInd() {
		return readInd;
	}

	public void setReadInd(Integer readInd) {
		this.readInd = readInd;
	}

	public int getPrivateId() {
		return privateId;
	}

	public void setPrivateId(int privateId) {
		this.privateId = privateId;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createDate) {
		this.createdDate = createDate;
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

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ServiceOrderNote(String note, String subject) {
		super();
		this.note = note;
		this.subject = subject;
	}

	public ServiceOrderNote() {
		super();
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getNoteTypeId() {
		return noteTypeId;
	}

	public void setNoteTypeId(Integer noteTypeId) {
		this.noteTypeId = noteTypeId;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public List<Integer> getNoteTypeIds() {
		return noteTypeIds;
	}

	public void setNoteTypeIds(List<Integer> noteTypeIds) {
		this.noteTypeIds = noteTypeIds;
	}

	/**
	 * @return the emptyNoteAllowed
	 */
	public boolean isEmptyNoteAllowed() {
		return emptyNoteAllowed;
	}

	/**
	 * @param emptyNoteAllowed the emptyNoteAllowed to set
	 */
	public void setEmptyNoteAllowed(boolean emptyNoteAllowed) {
		this.emptyNoteAllowed = emptyNoteAllowed;
	}



	
}
