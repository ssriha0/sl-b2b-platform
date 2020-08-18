package com.newco.marketplace.api.beans.so;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author ndixit
 *
 */
@XStreamAlias("contactList")
public class ContactList {
	
	@XStreamImplicit(itemFieldName="contactList")
	List<Contact> contactList = new ArrayList<Contact>();

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

}
