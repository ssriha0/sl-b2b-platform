package com.newco.marketplace.leadoutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("warning")
public class Warning {
	
	@XStreamAlias("warningerrorcode")
	private String warningErrorCode;
	
	@XStreamAlias("warningerrordescription")
	private String  warningErrorDescription;

	public String getWarningErrorCode() {
		return warningErrorCode;
	}

	public void setWarningErrorCode(String warningErrorCode) {
		this.warningErrorCode = warningErrorCode;
	}

	public String getWarningErrorDescription() {
		return warningErrorDescription;
	}

	public void setWarningErrorDescription(String warningErrorDescription) {
		this.warningErrorDescription = warningErrorDescription;
	}
}
