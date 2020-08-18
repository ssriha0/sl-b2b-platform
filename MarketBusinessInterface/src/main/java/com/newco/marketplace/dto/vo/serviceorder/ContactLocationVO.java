package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;

import com.newco.marketplace.dto.vo.LocationVO;
import com.sears.os.vo.SerializableBaseVO;

public class ContactLocationVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9020803549224672062L;

	private LocationVO buyerPrimaryLocation;
	private Contact buyerPrimaryContact;
	private List<LocationVO> listLocation;
	private List<Contact> listContact;
	private Double maxSpendLimitPerSO;
	
	public List<LocationVO> getListLocation() {
		return listLocation;
	}
	public void setListLocation(List<LocationVO> listLocation) {
		this.listLocation = listLocation;
	}
	public List<Contact> getListContact() {
		return listContact;
	}
	public void setListContact(List<Contact> listContact) {
		this.listContact = listContact;
	}
	public LocationVO getBuyerPrimaryLocation() {
		return buyerPrimaryLocation;
	}
	public void setBuyerPrimaryLocation(LocationVO buyerPrimaryLocation) {
		this.buyerPrimaryLocation = buyerPrimaryLocation;
	}
	public Contact getBuyerPrimaryContact() {
		return buyerPrimaryContact;
	}
	public void setBuyerPrimaryContact(Contact buyerPrimaryContact) {
		this.buyerPrimaryContact = buyerPrimaryContact;
	}
	public Double getMaxSpendLimitPerSO() {
		return maxSpendLimitPerSO;
	}
	public void setMaxSpendLimitPerSO(Double maxSpendLimitPerSO) {
		this.maxSpendLimitPerSO = maxSpendLimitPerSO;
	}
}
