package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;

import com.newco.marketplace.dto.vo.LocationVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision$ $Author$ $Date$
 */
public class BuyerResource extends SerializableBaseVO {

	private static final long serialVersionUID = 4283378898514594209L;
	
	private Integer resourceId;
	private Integer buyerId;
	private Integer contactId;
	private String userName = null;
	private Integer locnId;
	private Integer companyRoleId;

	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	private LocationVO buyerResPrimaryLocation;
	private Contact buyerResContact;
	
	private Double maxSpendLimitPerSO;
	private Date termCondAcceptedDate;
	private Integer termCondId;
	private Integer termCondInd;
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getLocnId() {
		return locnId;
	}
	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}
	public Integer getCompanyRoleId() {
		return companyRoleId;
	}
	public void setCompanyRoleId(Integer companyRoleId) {
		this.companyRoleId = companyRoleId;
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
	public LocationVO getBuyerResPrimaryLocation() {
		return buyerResPrimaryLocation;
	}
	public void setBuyerResPrimaryLocation(LocationVO buyerPrimaryLocation) {
		this.buyerResPrimaryLocation = buyerPrimaryLocation;
	}
	public Contact getBuyerResContact() {
		return buyerResContact;
	}
	public void setBuyerResContact(Contact buyerContact) {
		this.buyerResContact = buyerContact;
	}
	public Double getMaxSpendLimitPerSO() {
		return maxSpendLimitPerSO;
	}
	public void setMaxSpendLimitPerSO(Double maxSpendLimitPerSO) {
		this.maxSpendLimitPerSO = maxSpendLimitPerSO;
	}
	public Date getTermCondAcceptedDate() {
		return termCondAcceptedDate;
	}
	public void setTermCondAcceptedDate(Date termCondAcceptedDate) {
		this.termCondAcceptedDate = termCondAcceptedDate;
	}
	public Integer getTermCondId() {
		return termCondId;
	}
	public void setTermCondId(Integer termCondId) {
		this.termCondId = termCondId;
	}
	public Integer getTermCondInd() {
		return termCondInd;
	}
	public void setTermCondInd(Integer termCondInd) {
		this.termCondInd = termCondInd;
	}
		
}
