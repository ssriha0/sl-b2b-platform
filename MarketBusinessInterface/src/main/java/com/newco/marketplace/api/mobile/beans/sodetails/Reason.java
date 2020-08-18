package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing Reason information.
 * @author Infosys
 *
 */

@XStreamAlias("reason")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reason {
	
	@XStreamAlias("resonCodeId")
	private String resonCodeId;
	
	@XStreamAlias("reasonCode")
	private String reasonCode;

	/**
	 * @return the resonCodeId
	 */
	public String getResonCodeId() {
		return resonCodeId;
	}

	/**
	 * @param resonCodeId the resonCodeId to set
	 */
	public void setResonCodeId(String resonCodeId) {
		this.resonCodeId = resonCodeId;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	
}
