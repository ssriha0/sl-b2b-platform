package com.servicelive.orderfulfillment.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.servicelive.orderfulfillment.domain.Template;

public class TemplateDao implements ITemplateDao {

	private EntityManager em;

    @PersistenceContext()
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }

	public Template getTemplateById(Integer templateId) {
		return em.find(Template.class,templateId);
	}

	public Template query(Template template) {
		return em.find(Template.class, template);
	}

}
