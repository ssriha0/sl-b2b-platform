package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of contacts 
 * @author Infosys
 *
 */
@XStreamAlias("contacts")
public class Contacts {
		
	
	@XStreamAlias("buyerSupportResID")
	private String buyerSupportResID;
	
	@XStreamImplicit(itemFieldName="contact")
	private List<Contact> contactList;

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public String getBuyerSupportResID() {
		return buyerSupportResID;
	}

	public void setBuyerSupportResID(String buyerSupportResID) {
		this.buyerSupportResID = buyerSupportResID;
	}

}
