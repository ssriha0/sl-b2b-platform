package com.newco.marketplace.buyeroutboundnotification.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ERRORCODES")
public class ErrorCodes {
	
	@XStreamImplicit(itemFieldName="ERRORCODE")
	private List<ErrorCode> errorCode;

	public List<ErrorCode> getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(List<ErrorCode> errorCode) {
		this.errorCode = errorCode;
	}

}
