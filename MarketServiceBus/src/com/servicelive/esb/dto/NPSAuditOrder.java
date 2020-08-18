package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.webservices.dao.NpsAuditOrderMessages;

public class NPSAuditOrder implements Serializable{
	
	/** generated serialVersionUID */
	private static final long serialVersionUID = -1676922568702521698L;

	private String serviceOrderNumber;
	
	private String serviceUnitNumber;
	
	private String slSoId;
	
	private String npsStatus;
	
	private Integer stagingOrderId;
	
	private Integer processId;
	
	private Integer returnCode;
	
	private NPSSalesCheck salesCheck;
	
	private List<NPSMessageInfo> messages;
	
	private List<NpsAuditOrderMessages>  npsAuditOrderMessages;

	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}


	public NPSSalesCheck getSalesCheck() {
		return salesCheck;
	}

	public void setSalesCheck(NPSSalesCheck salesCheck) {
		this.salesCheck = salesCheck;
	}

	public List<NPSMessageInfo> getMessages() {
		return messages;
	}

	public void setMessages(List<NPSMessageInfo> messages) {
		this.messages = messages;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getNpsStatus() {
		return npsStatus;
	}

	public void setNpsStatus(String npsStatus) {
		this.npsStatus = npsStatus;
	}

	public Integer getStagingOrderId() {
		return stagingOrderId;
	}

	public void setStagingOrderId(Integer stagingOrderId) {
		this.stagingOrderId = stagingOrderId;
	}

	public List<NpsAuditOrderMessages> getNpsAuditOrderMessages() {
		return npsAuditOrderMessages;
	}

	public void setNpsAuditOrderMessages(
			List<NpsAuditOrderMessages> npsAuditOrderMessages) {
		this.npsAuditOrderMessages = npsAuditOrderMessages;
	}

	public String getSlSoId() {
		return slSoId;
	}

	public void setSlSoId(String slSoId) {
		this.slSoId = slSoId;
	}


}
