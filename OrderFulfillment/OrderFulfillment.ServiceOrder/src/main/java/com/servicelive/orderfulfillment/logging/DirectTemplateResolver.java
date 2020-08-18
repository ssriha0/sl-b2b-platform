package com.servicelive.orderfulfillment.logging;

import com.servicelive.orderfulfillment.common.ServiceOrderException;

public class DirectTemplateResolver implements ITemplateResolver {

	private String template;
	
	DirectTemplateResolver(String template){
		this.template=template;
	}
	
	public void setTemplate(String template) { 
		this.template=template;
	}
	
	public String getTemplate() throws ServiceOrderException {
		return template;
	}

}
