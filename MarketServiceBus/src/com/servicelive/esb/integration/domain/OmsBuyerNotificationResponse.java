package com.servicelive.esb.integration.domain;

import java.util.ArrayList;
import java.util.List;

public class OmsBuyerNotificationResponse extends BuyerNotificationResponse {
	private String processId;
	private String salesCheckNumber;
	private String salesCheckDate;
	private String npsStatus;
	private String serviceOrderNumber;
	private String serviceUnitNumber;
	private String serviceLiveOrderId;
	
	private List<OmsBuyerNotificationResponseMessage> messages;
	
	public OmsBuyerNotificationResponse(Long buyerNotificationResponseId, Long transactionId, Long buyerNotificationId,
			String returnCode, Boolean responseStatusSuccess, String responseMessage,
			String processId, String salesCheckNumber, String salesCheckDate, String npsStatus,
			String serviceOrderNumber, String serviceUnitNumber, String serviceLiveOrderId) {
		
		this.setBuyerNotificationResponseId(buyerNotificationResponseId); 
		this.setTransactionId(transactionId);
		this.setBuyerNotificationId(buyerNotificationId);
		this.setReturnCode(returnCode);
		this.setResponseStatusSuccess(responseStatusSuccess);
		this.setResponseMessage(responseMessage);
		
		this.processId = processId;
		this.salesCheckNumber = salesCheckNumber;
		this.salesCheckDate = salesCheckDate;
		this.npsStatus = npsStatus;
		this.serviceOrderNumber = serviceOrderNumber;
		this.serviceUnitNumber = serviceUnitNumber;
		this.serviceLiveOrderId = serviceLiveOrderId;
	}
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getSalesCheckNumber() {
		return salesCheckNumber;
	}
	public void setSalesCheckNumber(String salesCheckNumber) {
		this.salesCheckNumber = salesCheckNumber;
	}
	public String getSalesCheckDate() {
		return salesCheckDate;
	}
	public void setSalesCheckDate(String salesCheckDate) {
		this.salesCheckDate = salesCheckDate;
	}
	
	public void addMessage(OmsBuyerNotificationResponseMessage message) {
		if (messages == null) {
			messages = new ArrayList<OmsBuyerNotificationResponseMessage>();
		}
		messages.add(message);
	}

	public void setMessages(List<OmsBuyerNotificationResponseMessage> messages) {
		this.messages = messages;
	}

	public List<OmsBuyerNotificationResponseMessage> getMessages() {
		return messages;
	}

	public void setNpsStatus(String npsStatus) {
		this.npsStatus = npsStatus;
	}

	public String getNpsStatus() {
		return npsStatus;
	}

	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	public String getServiceOrderNumber() {
		return serviceOrderNumber;
	}

	public void setServiceUnitNumber(String serviceUnitNumber) {
		this.serviceUnitNumber = serviceUnitNumber;
	}

	public String getServiceUnitNumber() {
		return serviceUnitNumber;
	}

	public void setServiceLiveOrderId(String serviceLiveOrderId) {
		this.serviceLiveOrderId = serviceLiveOrderId;
	}

	public String getServiceLiveOrderId() {
		return serviceLiveOrderId;
	}
}
