package com.newco.marketplace.dto.vo;

import java.util.Date;

/**
 * VO for the document type table to store the value retrieved 
 * from DataBase
 * @author SL Dev
 */
public class BuyerDocumentTypeVO {
	private int buyerCompDocId;
	private int buyerId;
	private String documentTitle;
	private int mandatoryInd;
	private String source;
	private int sourceId;
	private int deleteInd;
	private Boolean nonDeletable;
	private String modifiedBy;
	private Date modifiedDate;
	
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getDeleteInd() {
		return deleteInd;
	}
	public void setDeleteInd(int deleteInd) {
		this.deleteInd = deleteInd;
	}
	public int getBuyerCompDocId() {
		return buyerCompDocId;
	}
	public void setBuyerCompDocId(int buyerCompDocId) {
		this.buyerCompDocId = buyerCompDocId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public int getMandatoryInd() {
		return mandatoryInd;
	}
	public void setMandatoryInd(int mandatoryInd) {
		this.mandatoryInd = mandatoryInd;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public Boolean getNonDeletable() {
		return nonDeletable;
	}
	public void setNonDeletable(Boolean nonDeletable) {
		this.nonDeletable = nonDeletable;
	}
		
}
