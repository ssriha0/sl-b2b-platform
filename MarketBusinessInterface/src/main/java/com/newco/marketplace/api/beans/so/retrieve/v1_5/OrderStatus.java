package com.newco.marketplace.api.beans.so.retrieve.v1_5;

import com.newco.marketplace.api.annotation.MaskedValue;
import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing order status information.
 * @author Infosys
 *
 */
@XStreamAlias("orderstatus")
public class OrderStatus {

	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("buyerSoId")
	private String buyerSoId;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("substatus")
	private String substatus;
	
	@MaskedValue("1970-01-01T01:00:00")
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@OptionalParam
	@XStreamAlias("postedDate")
	private String postedDate;
	
	@OptionalParam
	@XStreamAlias("acceptedDate")
	private String acceptedDate;
	
	@OptionalParam
	@XStreamAlias("activeDate")
	private String activeDate;
	
	@OptionalParam
	@XStreamAlias("completedDate")
	private String completedDate;
	
	@OptionalParam
	@XStreamAlias("closedDate")
	private String closedDate;
	
	@OptionalParam
	@XStreamAlias("problemDescription")
	private String problemDescription;
	
	@OptionalParam
	@XStreamAlias("title")
	private String title;
	
	@OptionalParam
	@XStreamAlias("priceModel")
	private String priceModel;
	
	public String getPriceModel() {
		return priceModel;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public String getBuyerSoId() {
		return buyerSoId;
	}

	public void setBuyerSoId(String buyerSoId) {
		this.buyerSoId = buyerSoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubstatus() {
		return substatus;
	}

	public void setSubstatus(String substatus) {
		this.substatus = substatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public String getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
