package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Data access object (DAO) for domain model class LuServiceTypeTemplate.
 * @see .LuServiceTypeTemplate
  * @author MyEclipse Persistence Tools 
 */

public class LuServiceTypeTemplateDAO extends JpaDaoSupport implements ILuServiceTypeTemplateDAO {
	//property constants
	public static final String NODE_ID = "nodeId";
	public static final String DESCR = "descr";
	public static final String SORT_ORDER = "sortOrder";
    
    public LuServiceTypeTemplate findById(Integer id) {
    	logger.info("finding LuServiceTypeTemplate instance with id: " + id);
        try {
        	JpaTemplate jpaTemplate = getJpaTemplate();
            LuServiceTypeTemplate instance = jpaTemplate.find(LuServiceTypeTemplate.class, id);
            return instance;
        } catch (RuntimeException re) {
        	logger.error("findById failed", re);
            throw re;
        }
    }
    
    @SuppressWarnings("unchecked")
	public LuServiceTypeTemplate findByTemplateIdAndNodeId(final Integer nodeId, final String descr) {
    	logger.info("finding LuServiceTypeTemplate instance with node id: " + nodeId + " and descr: " + descr);
        try {
			final String queryString = "select model from LuServiceTypeTemplate model where model.nodeId = :nodeId and model.descr = :descr";
			List<LuServiceTypeTemplate> templatesList = (List<LuServiceTypeTemplate>) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("nodeId", nodeId);
					query.setParameter("descr", descr);
					return query.getResultList();
				}
			});
			return templatesList.get(0);
        } catch (RuntimeException re) {
        	logger.error("findByTemplateIdAndNodeId failed", re);
            throw re;
        }
    }
}