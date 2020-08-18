package com.newco.marketplace.aop.dispatcher;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;

public interface Dispatcher {
	
	public boolean sendAlert(AlertTask task, String payload);
	
	public boolean sendAlert(AlertTask task, String payload, String fileName);

}
