package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupServiceTypeDao;

/**
 * 
 * 
 *
 */
@Transactional
public class LookupServiceTypeDaoImpl extends AbstractBaseDao implements LookupServiceTypeDao {

	public LookupServiceType getServiceTypeFromId(Integer serviceTypeId) throws Exception
	{
		LookupServiceType serviceType = findById(serviceTypeId);
		return serviceType;
	}
	
	public List<LookupServiceType> getServiceTypeFromSkillNodeId(LookupSkills skillNodeId) throws Exception
	{
		List<LookupServiceType> serviceTypes = findByProperty("skillNodeId", skillNodeId);
		return serviceTypes;
	}
	
	@SuppressWarnings("unchecked")
	public List <LookupServiceType> findAll ( int... rowStartIdxAndCount) {
		return ( List <LookupServiceType> )super.findAll("LookupServiceType", rowStartIdxAndCount);
	}

	public LookupServiceType findById(Integer id)  throws RuntimeException {
		return ( LookupServiceType) super.findById(LookupServiceType.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupServiceType> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) {
		return ( List<LookupServiceType>) super.findByProperty("LookupServiceType",propertyName,value, rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupServiceTypeDao#getListFromSkillIds(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<LookupServiceType> getListFromSkillIds(List<Integer> skillIds)
			throws Exception {
		Query query = getEntityManager().createQuery("from LookupServiceType o where o.id in (:skillIds) order by o.id");
		query.setParameter("skillIds", skillIds);
		return query.getResultList();
	}
}
