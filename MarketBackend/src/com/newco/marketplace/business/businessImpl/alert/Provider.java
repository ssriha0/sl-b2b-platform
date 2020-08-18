/**
 * 
 */
package com.newco.marketplace.business.businessImpl.alert;

import com.newco.marketplace.dto.vo.alert.ContactAlertPreferencesVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;

/**
 * @author sahmad7
 *
 */
public class Provider {
	private Integer id;		//change to vendorResourceId
	private Contact contact;
	private ContactAlertPreferencesVO preferences;
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ContactAlertPreferencesVO getPreferences() {
		return preferences;
	}
	public void setPreferences(ContactAlertPreferencesVO preferences) {
		this.preferences = preferences;
	}

}
