package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao;

public class RoutingRuleErrorDaoImpl extends AbstractBaseDao implements
		RoutingRuleErrorDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao#save(com.servicelive.domain.routingrules.RoutingRuleError)
	 *      This method saves the RoutingRuleError details to the
	 *      routing_rule_error table
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void save(RoutingRuleError routingRuleError) {
		try {
			getEntityManager().persist(routingRuleError);
			flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao#deletePersistentErrors(java.lang.Integer)
	 *      deletes the persistent errors of a given rule
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deletePersistentErrors(Integer routingRuleHdrId) {
		String hql = "delete from RoutingRuleError ruleError where ruleError.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId and ruleError.errorType = :errorType";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("routingRuleHdrId", routingRuleHdrId);
		query.setParameter("errorType", "Persistent");
		try {
			query.executeUpdate();
		} catch (NoResultException e) {

		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleErrorDao#deleteNonPersistentErrors(java.lang.Integer)
	 *      deletes the persistent errors of a given rule
	 */
	public void deleteNonPersistentErrors(Integer routingRuleHdrId)
	{
		String hql = "delete from RoutingRuleError ruleError where ruleError.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId and ruleError.errorType = :errorType";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("routingRuleHdrId", routingRuleHdrId);
		query.setParameter("errorType", RoutingRulesConstants.NON_PERSISTENT_ERROR);
		try {
			query.executeUpdate();
		} catch (NoResultException e) {

		}
	}
	
	/**
	 *  Return persistent conflicts for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleError> getRoutingRulesConflictsForRuleId(
			Integer routingRuleId) {
		String hql = "select ruleError from RoutingRuleError ruleError where "
				+ "ruleError.conflictInd = :conflictInd and "
				+ "ruleError.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId and "
				+ "ruleError.errorType = :persistent order by ruleError.conflictingRuleId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("routingRuleHdrId", routingRuleId);
		query.setParameter("conflictInd", true);
		query.setParameter("persistent","Persistent");
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleError> routingRuleConflictList = query
					.getResultList();
			return routingRuleConflictList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
