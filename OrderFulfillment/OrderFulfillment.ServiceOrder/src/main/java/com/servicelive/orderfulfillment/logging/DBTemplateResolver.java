package com.servicelive.orderfulfillment.logging;

import org.apache.commons.lang.Validate;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.ITemplateDao;
import com.servicelive.orderfulfillment.domain.Template;

public class DBTemplateResolver implements ITemplateResolver {

	private int templateId;
	private ITemplateDao templateDao;
	
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getTemplateId() {
		return templateId;
	}

	public String getTemplate() throws ServiceOrderException {
		
		Validate.notNull(templateDao,"template dao must not be null");
		
		Template template = this.templateDao.getTemplateById(this.templateId);
		
		Validate.notNull(template,"template " + templateId + " not found");
		
		return template.getSource();
	}
	
	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}

}
