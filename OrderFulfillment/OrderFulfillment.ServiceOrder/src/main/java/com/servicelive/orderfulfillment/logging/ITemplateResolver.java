package com.servicelive.orderfulfillment.logging;

import com.servicelive.orderfulfillment.common.ServiceOrderException;

public interface ITemplateResolver {

	public String getTemplate() throws ServiceOrderException;
}
