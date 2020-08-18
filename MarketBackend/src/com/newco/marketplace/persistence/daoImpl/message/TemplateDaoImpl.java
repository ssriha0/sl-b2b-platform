package com.newco.marketplace.persistence.daoImpl.message;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.Template;
import com.sears.os.dao.impl.ABaseImplDao;

public class TemplateDaoImpl extends ABaseImplDao implements TemplateDao {
 
     public Template query(Template template) throws DataServiceException {
    	return (Template)queryForObject("template.query", template);
   }
}
