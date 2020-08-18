package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.alert.AlertTemplateMappingVo;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.AuditTemplateMappingVo;
import com.newco.marketplace.vo.provider.TemplateVo;

public interface ITemplateDao {

	public List<AuditTemplateMappingVo> getAuditEmailTemplateMappings () throws DBException;
	public List<AlertTemplateMappingVo> getAlertEmailTemplateMappings () throws DBException;
	public TemplateVo getTemplate (String templateName) throws DBException; 
	public TemplateVo getTemplate (Integer templateId) throws DBException;
	
}
