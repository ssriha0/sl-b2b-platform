package com.servicelive.spn.dao.common.impl;

import java.util.List;

import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.common.ApplicationPropertiesDao;

public class ApplicationPropertiesDaoImpl extends AbstractBaseDao implements ApplicationPropertiesDao {

	@SuppressWarnings("unchecked")
	public List<ApplicationProperties> findAll(int... rowStartIdxAndCount) throws Exception {
		return (List<ApplicationProperties>) super.findAll(ApplicationProperties.class.getSimpleName(), rowStartIdxAndCount);
	}

	public ApplicationProperties findById(String id) throws Exception {
		return (ApplicationProperties) super.findById(ApplicationProperties.class, id);
	}


	@SuppressWarnings("unchecked")
	public List<ApplicationProperties> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return (List<ApplicationProperties>) super.findByProperty(ApplicationProperties.class.getSimpleName(), propertyName, value, rowStartIdxAndCount);
	}
}
