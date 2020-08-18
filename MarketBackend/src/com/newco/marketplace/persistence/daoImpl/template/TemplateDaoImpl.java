package com.newco.marketplace.persistence.daoImpl.template;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.template.TemplateDao;
import com.newco.marketplace.webservices.base.Template;
import com.sears.os.dao.impl.ABaseImplDao;

public class TemplateDaoImpl extends ABaseImplDao implements TemplateDao {
 
     public Template query(Template template) throws DataServiceException {
    	return (Template)queryForObject("template.query", template);
   }
     
     public Template getTemplateById(Integer templateId) throws DataServiceException{
    	 return (Template)queryForObject("getTemplateById.query", templateId);
     } 
}
