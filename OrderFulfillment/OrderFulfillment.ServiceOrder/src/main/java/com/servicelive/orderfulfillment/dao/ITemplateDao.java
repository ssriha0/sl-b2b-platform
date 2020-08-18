package com.servicelive.orderfulfillment.dao;

import com.servicelive.orderfulfillment.domain.Template;

public interface ITemplateDao {

	public Template query(Template template);
	public Template getTemplateById(Integer templateId);

}
