package com.servicelive.orderfulfillment.logging;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.springframework.core.io.Resource;

import com.servicelive.orderfulfillment.common.ServiceOrderException;

public class SpringTemplateResolver implements ITemplateResolver {

	private Resource velocityTemplate;
	
	public String getTemplate() throws ServiceOrderException {
		
		Validate.notNull(velocityTemplate,"velocity template must not be null");

		try {
			return IOUtils.toString(velocityTemplate.getInputStream());
		} catch (IOException e) {
			throw new ServiceOrderException("Unable to obtain template: " + velocityTemplate.getFilename(),e);
		}
	}

	public void setVelocityTemplate(Resource messageTemplate) {
		this.velocityTemplate = messageTemplate;
	}
}
