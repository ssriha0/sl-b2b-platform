/**
 *
 */
package com.newco.marketplace.dto.vo.audit;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author hoza
 *
 */
public class AuditNotesVO extends MPBaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Date modifiedDate;
	private String auditor;
	private String description;
	private Integer vendorId;
	private Integer id;
	private Integer resourceId; //notes are on vendor level so if the resource is given we will pull the

	/* (non-Javadoc)
	 * @see com.sears.os.vo.ABaseVO#toString()
	 */
	@Override
	public String toString() {
		 return ("<AuditNotesVO>"+"   vendorId: " + vendorId +
                 "   auditor: " + auditor +
                 "   description: " + description +
                 "   modifiedDate: "+ modifiedDate +
                 "	 id: " + id +
                 "</AuditNotesVO>");
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
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}

	/**
	 * @param auditor the auditor to set
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}



}
