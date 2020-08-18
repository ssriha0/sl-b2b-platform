package com.newco.marketplace.leadoutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("errorreply")	
public class ResponseLeadDetailsError {
	
	@XStreamAlias("transactionid")	
	private String transactionId;
	
	@XStreamAlias("returncode")	
	private String returnCode;
	
	@XStreamAlias("returndescription")	
	private String returnDescription;
		
	@XStreamImplicit(itemFieldName="error")
	private List<LeadsError> error;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnDescription() {
		return returnDescription;
	}

	public void setReturnDescription(String returnDescription) {
		this.returnDescription = returnDescription;
	}

	public List<LeadsError> getError() {
		return error;
	}

	public void setError(List<LeadsError> error) {
		this.error = error;
	}
	
}
