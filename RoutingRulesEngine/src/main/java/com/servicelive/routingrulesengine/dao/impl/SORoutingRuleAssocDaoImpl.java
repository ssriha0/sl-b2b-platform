package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.so.BuyerSkuTaskAssoc;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.routingrulesengine.dao.SORoutingRuleAssocDao;


/**
 * 
 *
 */
public class SORoutingRuleAssocDaoImpl extends AbstractBaseDao implements SORoutingRuleAssocDao {

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public SORoutingRuleAssoc findBySoId(String soId) {
		List<SORoutingRuleAssoc> list = (List<SORoutingRuleAssoc>) super.findByProperty("SORoutingRuleAssoc", "soId", soId);
		if(list.isEmpty()) {
			return null;
		}
		return list.iterator().next();
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public void update(SORoutingRuleAssoc entity) throws Exception{
		super.update(entity);
		super.getEntityManager().flush();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(SORoutingRuleAssoc entity) throws Exception{
		super.delete(entity);
	}
	
	//SL 15642 Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
	@Transactional(propagation=Propagation.SUPPORTS)
	public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance)
	{
		String hql = null;
		Query query = null;
		try{
			hql = "SELECT COUNT(so_id) FROM so_workflow_controls WHERE so_id ='"+soId+"'";
			query = getEntityManager().createNativeQuery(hql);
			Integer  retValue =(Integer)query.getSingleResult();
			if(retValue==0)
			{
				//Inserting a new entry in  so_workflow_controls for a particular so id 
							hql = "INSERT INTO so_workflow_controls(so_id,method_of_acceptance) VALUES(:soId,:methodOfAcceptance)";
									 query = getEntityManager().createNativeQuery(hql);
									 query.setParameter("soId", soId);
									 query.setParameter("methodOfAcceptance",methodOfAcceptance);
				
			}
			else
			{
				//Updating a already existing entry in  so_workflow_controls for a particular so id 
							hql = "UPDATE  so_workflow_controls SET  method_of_acceptance=:methodOfAcceptance WHERE so_id = :soId";
									 query = getEntityManager().createNativeQuery(hql);
									 query.setParameter("methodOfAcceptance",methodOfAcceptance);
									 query.setParameter("soId", soId);
									 query.executeUpdate();
				
			}
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}
	
	
	//SL 15642 Method to enter type of routing in so_workflow_controls table on associating a rule id with so
		@Transactional(propagation=Propagation.SUPPORTS)
		public void setMethodOfRoutingOfSo(String soId,String methodOfRouting)
		{
			String hql = null;
			Query query = null;
			try{
				hql = "SELECT COUNT(so_id) FROM so_workflow_controls WHERE so_id ='"+soId+"'";
				query = getEntityManager().createNativeQuery(hql);
				Integer  retValue =(Integer)query.getSingleResult();
				if(retValue==0)
				{
					//Inserting a new entry in  so_workflow_controls for a particular so id 
								hql = "INSERT INTO so_workflow_controls(so_id,method_of_routing) VALUES(:soId,:methodOfRouting)";
										 query = getEntityManager().createNativeQuery(hql);
										 query.setParameter("soId", soId);
										 query.setParameter("methodOfRouting",methodOfRouting);
					
				}
				else
				{
					//Updating a already existing entry in  so_workflow_controls for a particular so id 
								hql = "UPDATE  so_workflow_controls SET  method_of_routing=:methodOfRouting WHERE so_id = :soId";
										 query = getEntityManager().createNativeQuery(hql);
										 query.setParameter("methodOfRouting",methodOfRouting);
										 query.setParameter("soId", soId);
										 query.executeUpdate();
					
				}
			}
			catch (NoResultException e) {
				logger.info(e.getMessage());
			}
		}
	
}
