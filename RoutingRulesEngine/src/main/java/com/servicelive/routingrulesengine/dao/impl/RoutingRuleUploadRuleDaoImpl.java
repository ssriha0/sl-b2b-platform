package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.routingrules.RoutingRuleErrorCause;
import com.servicelive.domain.routingrules.RoutingRuleUploadRule;
import com.servicelive.routingrulesengine.dao.RoutingRuleUploadRuleDao;

public class RoutingRuleUploadRuleDaoImpl extends AbstractBaseDao implements RoutingRuleUploadRuleDao {
	
	/** 
	 * Save the upload rule details 
	 * @param routingRuleUploadRule
	 * @return 
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleUploadRule saveUploadRule(
			RoutingRuleUploadRule routingRuleUploadRule) {
		getEntityManager().persist(routingRuleUploadRule);
		return routingRuleUploadRule;
	}

	/** 
	 * to get list of RoutingRuleUploadRule for a set of uploadRuleIds
	 * @return routingRuleUploadRule
	 * @param List<RoutingRuleUploadRule>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleUploadRule> getUploadRules(List<Integer> uploadRuleIds)
	{
		String hql = "select uploadRule from RoutingRuleUploadRule uploadRule where uploadRule.routingRuleUploadRuleId in (:uploadRuleIds)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("uploadRuleIds", uploadRuleIds);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleUploadRule> uploadRuleList = query.getResultList();
			return uploadRuleList;
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	/**
	 * to get list of error cause
	 * @return List<RoutingRuleErrorCause>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleErrorCause> getErrorCause()
	{
		String hql = "select errorCause from RoutingRuleErrorCause errorCause ";
		Query query = getEntityManager().createQuery(hql);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleErrorCause> errorCause = query.getResultList();
			return errorCause;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void addUploadRule (Integer fileHdrId, Integer ruleId, String action){
		String queryString = "INSERT INTO routing_rule_upload_rule"
								+"(routing_rule_file_hdr_id,"
								+"routing_rule_hdr_id,"
								+"action,"
								+"status,"
								+"created_date,"
								+"modified_date,"
								+"modified_by)"
							+"VALUES (:fileHdrId,"
								+":ruleId,"
								+":action,"
								+"'Success',"
								+"NOW(),"
								+"NOW(),"
								+"'CAR Upload Tool')";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("fileHdrId", fileHdrId);
		query.setParameter("ruleId", ruleId);
		query.setParameter("action", action);
		query.executeUpdate();
		return;
	}
}
