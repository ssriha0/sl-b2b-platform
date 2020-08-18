package com.servicelive.domain.spn.network;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNDocumentState;

/**
 * 
 * @author svanloon
 *
 */
@Entity
@Table ( name = "spnet_uploaded_document_state")
public class SPNUploadedDocumentState extends LoggableBaseDomain {
	private static final long serialVersionUID = 20090402L;

	@EmbeddedId
	private SPNUploadedDocumentStatePk spnUploadedDocumentStatePk;

	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
	@JoinColumn(name = "doc_state_id", unique = false, nullable = false, insertable = true, updatable = true)
	private LookupSPNDocumentState spnDocumentState;

	
	@Column (name = "comments", unique = false, nullable = true, insertable = true, updatable = true, length=150)
	private String comments;

	@Column (name = "page_no", unique = false, nullable = true, insertable = true, updatable = true, length=3)
	private Integer pageNo;

	@Column (name = "audited_by", unique = false, nullable = true, insertable = true, updatable = true, length=50)
	private String auditedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "audited_date", unique = false, nullable = true, insertable = true, updatable = true)
	private Date auditedDate;

	/**
	 * @return the spnUploadedDocumentStatePk
	 */
	public SPNUploadedDocumentStatePk getSpnUploadedDocumentStatePk() {
		return spnUploadedDocumentStatePk;
	}

	/**
	 * @param spnUploadedDocumentStatePk the spnUploadedDocumentStatePk to set
	 */
	public void setSpnUploadedDocumentStatePk(
			SPNUploadedDocumentStatePk spnUploadedDocumentStatePk) {
		this.spnUploadedDocumentStatePk = spnUploadedDocumentStatePk;
	}

	/**
	 * @return the spnDocumentState
	 */
	public LookupSPNDocumentState getSpnDocumentState() {
		return spnDocumentState;
	}

	/**
	 * @param spnDocumentState the spnDocumentState to set
	 */
	public void setSpnDocumentState(LookupSPNDocumentState spnDocumentState) {
		this.spnDocumentState = spnDocumentState;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the auditedBy
	 */
	public String getAuditedBy() {
		return auditedBy;
	}

	/**
	 * @param auditedBy the auditedBy to set
	 */
	public void setAuditedBy(String auditedBy) {
		this.auditedBy = auditedBy;
	}

	/**
	 * @return the auditedDate
	 */
	public Date getAuditedDate() {
		return auditedDate;
	}

	/**
	 * @param auditedDate the auditedDate to set
	 */
	public void setAuditedDate(Date auditedDate) {
		this.auditedDate = auditedDate;
	}

}
