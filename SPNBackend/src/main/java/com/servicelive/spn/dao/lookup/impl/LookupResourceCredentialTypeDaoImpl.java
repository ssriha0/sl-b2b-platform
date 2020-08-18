package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupResourceCredentialType;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupResourceCredentialTypeDao;

/**
 * @author Carlos Garcia
 *
 */
@Repository ("lookupResourceCredentialTypeDao")
public class LookupResourceCredentialTypeDaoImpl extends AbstractBaseDao implements LookupResourceCredentialTypeDao {

	@SuppressWarnings("unchecked")
	public List<LookupResourceCredentialType> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupResourceCredentialType> )super.findAll("LookupResourceCredentialType", rowStartIdxAndCount);
	}

	public LookupResourceCredentialType findById(Integer id) throws Exception {
		return (LookupResourceCredentialType) super.findById(LookupResourceCredentialType.class, id);
	}

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupStates> findByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception {
		return (List<LookupStates>) super.findByProperty("LookupResourceCredentialType",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupResourceCredentialType> getResourceCredentialTypes() throws Exception
	{
		return findAll();
	}
}
