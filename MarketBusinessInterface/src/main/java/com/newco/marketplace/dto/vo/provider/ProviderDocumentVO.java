/**
 * 
 */
package com.newco.marketplace.dto.vo.provider;

import java.util.Date;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.webservices.base.CommonVO;

/**
 * This Vo represent vendor_resource_document table
 * @author hoza
 *
 */
public class ProviderDocumentVO extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer resourceId;
	private Integer documentId;
	private Boolean reviewedInd;
	private Boolean primaryInd;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBY;
	private DocumentVO docDetails; //using dto.vo cuz it being used in the Reso
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
	 * @return the reviewedInd
	 */
	public Boolean getReviewedInd() {
		return reviewedInd;
	}
	/**
	 * @param reviewedInd the reviewedInd to set
	 */
	public void setReviewedInd(Boolean reviewedInd) {
		this.reviewedInd = reviewedInd;
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
	/**
	 * @return the modifiedBY
	 */
	public String getModifiedBY() {
		return modifiedBY;
	}
	/**
	 * @param modifiedBY the modifiedBY to set
	 */
	public void setModifiedBY(String modifiedBY) {
		this.modifiedBY = modifiedBY;
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
	

}
