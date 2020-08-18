package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractDocument entity provides the base persistence definition of the
 * Document entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractDocument implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500072273083597955L;
	private Integer documentId;
	private Integer docCategoryId;
	private String descr;
	private String title;
	private String fileName;
	private String format;
	private String source;
	private String keywords;
	private Date expireDate;
	private Date purgeDate;
	private String document;
	private Date createdDate;
	private Date modifiedDate;
	private Integer vendorId;
	private Integer roleId;
	private Integer entityId;
	private Byte deleteInd;
	private String docUrl;
	private String docPath;
	private Integer docSize;
	private Set<BuyerDocument> buyerDocuments = new HashSet<BuyerDocument>(0);

	// Constructors

	/** default constructor */
	public AbstractDocument() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractDocument(Integer documentId, Date createdDate,
			Date modifiedDate) {
		this.documentId = documentId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	/** full constructor */
	public AbstractDocument(Integer documentId, Integer docCategoryId,
			String descr, String title, String fileName, String format,
			String source, String keywords, Date expireDate, Date purgeDate,
			String document, Date createdDate, Date modifiedDate,
			Integer vendorId, Integer roleId, Integer entityId, Byte deleteInd,
			String docUrl, String docPath, Integer docSize,
			Set<BuyerDocument> buyerDocuments) {
		this.documentId = documentId;
		this.docCategoryId = docCategoryId;
		this.descr = descr;
		this.title = title;
		this.fileName = fileName;
		this.format = format;
		this.source = source;
		this.keywords = keywords;
		this.expireDate = expireDate;
		this.purgeDate = purgeDate;
		this.document = document;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.vendorId = vendorId;
		this.roleId = roleId;
		this.entityId = entityId;
		this.deleteInd = deleteInd;
		this.docUrl = docUrl;
		this.docPath = docPath;
		this.docSize = docSize;
		this.buyerDocuments = buyerDocuments;
	}

	// Property accessors
	@Id
	@Column(name = "document_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	@Column(name = "doc_category_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getDocCategoryId() {
		return this.docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	@Column(name = "descr", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "file_name", unique = false, nullable = true, insertable = true, updatable = true)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "format", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Column(name = "source", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "keywords", unique = false, nullable = true, insertable = true, updatable = true)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expire_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "purge_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getPurgeDate() {
		return this.purgeDate;
	}

	public void setPurgeDate(Date purgeDate) {
		this.purgeDate = purgeDate;
	}

	@Column(name = "document", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDocument() {
		return this.document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "vendor_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "role_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "entity_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	@Column(name = "delete_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public Byte getDeleteInd() {
		return this.deleteInd;
	}

	public void setDeleteInd(Byte deleteInd) {
		this.deleteInd = deleteInd;
	}

	@Column(name = "doc_url", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDocUrl() {
		return this.docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	@Column(name = "doc_path", unique = false, nullable = true, insertable = true, updatable = true)
	public String getDocPath() {
		return this.docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	@Column(name = "doc_size", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getDocSize() {
		return this.docSize;
	}

	public void setDocSize(Integer docSize) {
		this.docSize = docSize;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "document")
	public Set<BuyerDocument> getBuyerDocuments() {
		return this.buyerDocuments;
	}

	public void setBuyerDocuments(Set<BuyerDocument> buyerDocuments) {
		this.buyerDocuments = buyerDocuments;
	}

}