package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing signature information.
 * @author Infosys
 *
 */
@XStreamAlias("signature")
public class Signature {
	
	@XStreamAlias("documentId")
	private Integer documentId;
	
	@XStreamAlias("name")
	private String name;

	@XStreamAlias("customerEmail")
	private String customerEmail;
 
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private String resourceInd;
	
	@XStreamOmitField
	private String docPath;

	@XStreamOmitField
	private String pdfStatus;
	
	@XStreamOmitField
	private String currentDate;
	
	@XStreamOmitField
	private Integer retryCount;
	
	@XStreamOmitField
	private String errorReason; 
	
	@XStreamOmitField
	private Integer docCategoryId;
	
	@XStreamOmitField
	private Integer emailSentInd;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getResourceInd() {
		return resourceInd;
	}

	public void setResourceInd(String resourceInd) {
		this.resourceInd = resourceInd;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getPdfStatus() {
		return pdfStatus;
	}

	public void setPdfStatus(String pdfStatus) {
		this.pdfStatus = pdfStatus;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public Integer getDocCategoryId() {
		return docCategoryId;
	}

	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	public Integer getEmailSentInd() {
		return emailSentInd;
	}

	public void setEmailSentInd(Integer emailSentInd) {
		this.emailSentInd = emailSentInd;
	}
	
	
}
