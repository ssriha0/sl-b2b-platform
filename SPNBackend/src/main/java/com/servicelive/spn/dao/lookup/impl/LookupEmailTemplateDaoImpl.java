package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupEmailTemplateDao;

/**
 * 
 * @author svanloo
 *
 */
@Transactional
public class LookupEmailTemplateDaoImpl extends AbstractBaseDao implements LookupEmailTemplateDao {

	/**
	 * 
	 */
	public LookupEmailTemplateDaoImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List <LookupEmailTemplate> findAll ( int... rowStartIdxAndCount) throws Exception {
		return (List <LookupEmailTemplate>) super.findAll("LookupEmailTemplate", rowStartIdxAndCount);
	}

	@SuppressWarnings("unchecked")
	public List<LookupEmailTemplate> findByLookupSPNWorkflowState(LookupSPNWorkflowState state) throws Exception {
		return (List<LookupEmailTemplate>) super.findByProperty(LookupEmailTemplate.class.getName(), "workflowState", state);
	}
}
