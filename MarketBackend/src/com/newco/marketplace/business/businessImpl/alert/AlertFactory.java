package com.newco.marketplace.business.businessImpl.alert;


public class AlertFactory {

	public static AlertQueueProcessor getAlertQueueProcessor(String alertType){
		AlertQueueProcessor alertQueueProcessor = null;
		if(alertType.equals("1")) {
			alertQueueProcessor =  new EmailAlertQueueProcessor();
		}
		if(alertType.equals("2")||alertType.equals("6")){
			alertQueueProcessor =  new SmsAlertQueueProcessor();

		}
		return alertQueueProcessor;
	}
	
}
