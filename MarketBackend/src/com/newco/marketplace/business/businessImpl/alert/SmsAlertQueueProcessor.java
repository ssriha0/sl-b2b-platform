package com.newco.marketplace.business.businessImpl.alert;

import com.servicelive.common.util.SmsAlert;

public class SmsAlertQueueProcessor extends AlertQueueProcessor{

	  @Override
	public void sendMessage(String from, String to, String message, String subject, String cc, String bcc){
		  SmsAlert.sendMessage(to, cc, bcc, message);
		  
	  }
	public boolean subscribe(String address,String message){
		  return SmsAlert.createSubscription(address, message);
	  }

}
