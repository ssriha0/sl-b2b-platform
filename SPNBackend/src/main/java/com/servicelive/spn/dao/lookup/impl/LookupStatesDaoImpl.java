package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupStatesDao;
import com.servicelive.domain.lookup.LookupStates;

/**
 *
 */
@Repository ("lookupStatesDao")
public class LookupStatesDaoImpl extends AbstractBaseDao implements LookupStatesDao {

	@SuppressWarnings("unchecked")
	public List<LookupStates> findAll(int... rowStartIdxAndCount) throws Exception {
		List <LookupStates> orderList = (List <LookupStates>) super.findAllOrderByDesc("LookupStates", "state_name", rowStartIdxAndCount);
		return orderList;
	}

	public LookupStates findById(Integer id) throws Exception {
		return (LookupStates) super.findById(LookupStates.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupStates> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return (List<LookupStates>) super.findByProperty("LookupStates",propertyName,value, rowStartIdxAndCount);
	}

	@SuppressWarnings("unchecked")
	public List<LookupStates> findStatesNotBlackedOut() throws Exception {
		String hql = "from LookupStates l where l.blackoutInd = :blackoutInd";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("blackoutInd", Boolean.FALSE);
		List<LookupStates> states = query.getResultList();
		return states;
	}
}
