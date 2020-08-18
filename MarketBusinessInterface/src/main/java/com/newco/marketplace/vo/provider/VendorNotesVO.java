/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.sql.Date;

/**
 * @author ksharm6
 *
 */
public class VendorNotesVO extends BaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4658823003312339708L;
	private int noteId;
	private int vendorId;
	private String note;
	private String modifiedBy;
	private java.sql.Timestamp createdDate;
	private Date modifiedDate;
	/**
	 * @return the createdDate
	 */
	public java.sql.Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(java.sql.Timestamp createdDate) {
		
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the noteId
	 */
	public int getNoteId() {
		return noteId;
	}
	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	/**
	 * @return the vendorId
	 */
	public int getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	} 

}
