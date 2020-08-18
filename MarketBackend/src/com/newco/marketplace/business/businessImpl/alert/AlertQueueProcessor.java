package com.newco.marketplace.business.businessImpl.alert;

public abstract class AlertQueueProcessor {
	public abstract void sendMessage(String fromAddress,String toAddress,String bodyMessage, String subject, String ccAddress, String bccAddress);
	public abstract boolean subscribe(String address,String message);
}
