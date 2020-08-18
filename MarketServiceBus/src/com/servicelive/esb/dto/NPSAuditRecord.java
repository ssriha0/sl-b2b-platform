package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AuditRecord")
public class NPSAuditRecord implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("SalesCheck")
	private NPSSalesCheck npsSalesCheck;
	
	@XStreamAlias("ServiceUnitNumber")
	private String serviceUnitNumber;
	
	@XStreamAlias("ServiceOrderNumber")
	private String serviceOrderNumber;
	
	@XStreamAlias("Process")
	private String processId;
	
	@XStreamAlias("ReturnCode")
	private String returnCode;
	
	@XStreamAlias("Messages")
	private NPSAuditMessages messages;

	public NPSSalesCheck getNpsSalesCheck() {
		return npsSalesCheck;
	}

	public void setNpsSalesCheck(NPSSalesCheck npsSalesCheck) {
		this.npsSalesCheck = npsSalesCheck;
	}

	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}

	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public NPSAuditMessages getMessages() {
		return messages;
	}

	public void setMessages(NPSAuditMessages messages) {
		this.messages = messages;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}


}
