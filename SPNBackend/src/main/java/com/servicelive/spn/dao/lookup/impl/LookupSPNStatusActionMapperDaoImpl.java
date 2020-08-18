package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNStatusActionMapperDao;

public class LookupSPNStatusActionMapperDaoImpl extends AbstractBaseDao implements LookupSPNStatusActionMapperDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LookupSPNStatusActionMapper> findAll(int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSPNStatusActionMapper>) super.findAll("LookupSPNStatusActionMapper", rowStartIdxAndCount);
	}

	@Transactional
	public LookupSPNStatusActionMapper findByModifierStateAndActionType(String modifiedBy, String wfState, String actionType) {
		String hql = "from LookupSPNStatusActionMapper l where l.actionType = :actionType and l.lookupSPNWorkflowState.id = :wfState and  l.lookupSPNStatusActionModifier.id = :modifiedBy";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("modifiedBy", modifiedBy);
		query.setParameter("wfState", wfState);
		query.setParameter("actionType", actionType);
		return (LookupSPNStatusActionMapper) query.getSingleResult();
	}
}
