package com.newco.marketplace.api.beans.leaddetailmanagement.addNotes;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class to store information on the new note type
 * @author Infosys
 *
 */

@XStreamAlias("emailAlert")
public class EmailAlertType {
	
	@XStreamAlias("emailAlertInd")
	private Boolean emailAlertInd;
	
	@XStreamAlias("emailAlertTos")
	private String emailAlertTos;

	public Boolean getEmailAlertInd() {
		return emailAlertInd;
	}

	public void setEmailAlertInd(Boolean emailAlertInd) {
		this.emailAlertInd = emailAlertInd;
	}

	public String getEmailAlertTos() {
		return emailAlertTos;
	}

	public void setEmailAlertTos(String emailAlertTos) {
		this.emailAlertTos = emailAlertTos;
	}
	
	

}
