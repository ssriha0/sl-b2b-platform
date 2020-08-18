package com.newco.marketplace.dto.vo.wallet;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;


public class WalletControlDocumentVO extends CommonVO {

  /**
	 * 
	 */
	private static final long serialVersionUID = 5533274513440529160L;
	
  private Integer id;
  private Integer documentId ;
  private String documentName;
  private Integer entityWalletControlId ;
  private Integer docCategoryId ;
  private Date createdDate;
  private Date  modifiedDate;
  private String modifiedBy;
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
 * @return the documentId
 */
public Integer getDocumentId() {
	return documentId;
}
/**
 * @param documentId the documentId to set
 */
public void setDocumentId(Integer documentId) {
	this.documentId = documentId;
}
/**
 * @return the entityWalletControlId
 */
public Integer getEntityWalletControlId() {
	return entityWalletControlId;
}
/**
 * @param entityWalletControlId the entityWalletControlId to set
 */
public void setEntityWalletControlId(Integer entityWalletControlId) {
	this.entityWalletControlId = entityWalletControlId;
}
/**
 * @return the docCategoryId
 */
public Integer getDocCategoryId() {
	return docCategoryId;
}
/**
 * @param docCategoryId the docCategoryId to set
 */
public void setDocCategoryId(Integer docCategoryId) {
	this.docCategoryId = docCategoryId;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public Date getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
}
public String getModifiedBy() {
	return modifiedBy;
}
public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}
public String getDocumentName() {
	return documentName;
}
public void setDocumentName(String documentName) {
	this.documentName = documentName;
}  
}
