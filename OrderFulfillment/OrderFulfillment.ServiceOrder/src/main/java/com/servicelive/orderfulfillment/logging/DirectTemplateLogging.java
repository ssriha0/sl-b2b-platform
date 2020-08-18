package com.servicelive.orderfulfillment.logging;

public class DirectTemplateLogging extends TemplateLogging {

	DirectTemplateLogging(int actionId, String template){
		this.setTemplateResolver(new DirectTemplateResolver(template));
		this.setActionId(actionId);
	}
}
