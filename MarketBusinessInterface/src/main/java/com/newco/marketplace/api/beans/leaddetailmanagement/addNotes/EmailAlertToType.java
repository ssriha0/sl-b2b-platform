package com.newco.marketplace.api.beans.leaddetailmanagement.addNotes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class to store information on the new note type
 * @author Infosys
 *
 */

@XStreamAlias("emailAlertTos")
public class EmailAlertToType {
	
	@XStreamAlias("emailAlertTo1")
	private String emailAlertTo1;
	
	@XStreamAlias("emailAlertTo2")
	private String emailAlertTo2;

	public String getEmailAlertTo1() {
		return emailAlertTo1;
	}

	public void setEmailAlertTo1(String emailAlertTo1) {
		this.emailAlertTo1 = emailAlertTo1;
	}

	public String getEmailAlertTo2() {
		return emailAlertTo2;
	}

	public void setEmailAlertTo2(String emailAlertTo2) {
		this.emailAlertTo2 = emailAlertTo2;
	}
	
	
}
