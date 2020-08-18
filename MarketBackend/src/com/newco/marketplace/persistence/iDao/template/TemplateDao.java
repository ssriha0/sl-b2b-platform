package com.newco.marketplace.persistence.iDao.template;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.Template;

public interface TemplateDao {

	public Template query(Template template) throws DataServiceException;
	
	public Template getTemplateById(Integer templateId) throws DataServiceException;
}
