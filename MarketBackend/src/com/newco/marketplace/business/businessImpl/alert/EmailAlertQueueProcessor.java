package com.newco.marketplace.business.businessImpl.alert;

public class EmailAlertQueueProcessor extends AlertQueueProcessor {

	
	@Override
	public void sendMessage(String fromAddress,String toAddress,String bodyMessage, String subject, String ccAddress, String bccAddress ){
		EmailAlert.sendMessage(fromAddress, toAddress, bodyMessage, subject, ccAddress, bccAddress);
	}
	public boolean subscribe(String address,String message){
		  return false;
	  }
}
