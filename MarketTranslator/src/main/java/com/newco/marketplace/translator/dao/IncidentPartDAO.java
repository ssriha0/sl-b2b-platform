package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class IncidentPartDAO extends JpaDaoSupport implements IIncidentPartDAO {
	
	// property constants
	public static final String MODIFIED_BY = "modifiedBy";
	
	@SuppressWarnings("unchecked")
	public List<IncidentPart> findAll() {
		logger.info("finding all IncidentPart instances");
		try {
			final String queryString = "select model from IncidentPart model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public IncidentPart findById(Integer id) {
		logger.info("finding IncidentPart instance with id: " + id);
		try {
			IncidentPart instance = getJpaTemplate().find(IncidentPart.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<IncidentPart> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<IncidentPart> findByProperty(String propertyName, final Object value) {
		logger.info("finding IncidentPart instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from IncidentPart model where model." + propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public void save(IncidentPart entity) {
		logger.info("saving IncidentPart instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public IncidentPart update(IncidentPart entity) {
		logger.info("updating IncidentPart instance");
		try {
			IncidentPart result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

}
