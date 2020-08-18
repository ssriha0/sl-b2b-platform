package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class IncidentEventDAO extends JpaDaoSupport implements IIncidentEventDAO {

	// property constants
	public static final String MODIFIED_BY = "modifiedBy";
	
	@SuppressWarnings("unchecked")
	public List<IncidentEvent> findAll() {
		logger.info("finding all IncidentEvent instances");
		try {
			final String queryString = "select model from IncidentEvent model";
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

	public IncidentEvent findById(Integer id) {
		logger.info("finding IncidentEvent instance with id: " + id);
		try {
			IncidentEvent instance = getJpaTemplate().find(IncidentEvent.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<IncidentEvent> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<IncidentEvent> findByProperty(String propertyName, final Object value) {
		logger.info("finding IncidentEvent instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from IncidentEvent model where model." + propertyName + "= :propertyValue";
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

	public void save(IncidentEvent entity) {
		logger.info("saving IncidentEvent instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}

	}

	public IncidentEvent update(IncidentEvent entity) {
		logger.info("updating IncidentEvent instance");
		try {
			IncidentEvent result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

}
