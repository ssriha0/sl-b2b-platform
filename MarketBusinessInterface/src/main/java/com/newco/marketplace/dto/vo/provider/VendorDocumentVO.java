/**
 * 
 */
package com.newco.marketplace.dto.vo.provider;

import java.util.Date;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.webservices.base.CommonVO;

/**
 * This Vo represent vendor_document table
 * @author hoza
 *
 */
public class VendorDocumentVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer vendorId;
	private Integer documentId;
	private Integer categoryId;
	private Boolean primaryInd;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String createdBy;
	private DocumentVO docDetails; //using dto.vo cuz it being used in the Reso
	
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	 * @return the pimaryInd
	 */
	public Boolean getPrimaryInd() {
		return primaryInd;
	}
	/**
	 * @param pimaryInd the pimaryInd to set
	 */
	public void setPrimaryInd(Boolean pimaryInd) {
		this.primaryInd = pimaryInd;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
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

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the docDetails
	 */
	public DocumentVO getDocDetails() {
		return docDetails;
	}
	/**
	 * @param docDetails the docDetails to set
	 */
	public void setDocDetails(DocumentVO docDetails) {
		this.docDetails = docDetails;
	}
	
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	

}
