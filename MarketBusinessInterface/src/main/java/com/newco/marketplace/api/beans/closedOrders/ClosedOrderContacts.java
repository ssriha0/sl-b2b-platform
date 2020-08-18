package com.newco.marketplace.api.beans.closedOrders;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of contacts 
 * @author Infosys
 *
 */
@XStreamAlias("contacts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedOrderContacts {
	
	@XStreamImplicit(itemFieldName="contact")
	private List<ClosedOrderContact> contact;

	public List<ClosedOrderContact> getContact() {
		return contact;
	}

	public void setContact(List<ClosedOrderContact> contact) {
		this.contact = contact;
	}

	

}
