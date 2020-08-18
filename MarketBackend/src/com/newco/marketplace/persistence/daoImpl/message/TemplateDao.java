package com.newco.marketplace.persistence.daoImpl.message;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.Template;

public interface TemplateDao {

	public Template query(Template template)
    	throws DataServiceException;
}
