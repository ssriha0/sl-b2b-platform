package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class IncidentNoteDAO extends JpaDaoSupport implements IIncidentNoteDAO {

	// property constants
	public static final String MODIFIED_BY = "modifiedBy";
	
	@SuppressWarnings("unchecked")
	public List<IncidentNote> findAll() {
		logger.info("finding all IncidentNote instances");
		try {
			final String queryString = "select model from IncidentNote model";
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

	public IncidentNote findById(Integer id) {
		logger.info("finding IncidentNote instance with id: " + id);
		try {
			IncidentNote instance = getJpaTemplate().find(IncidentNote.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<IncidentNote> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<IncidentNote> findByProperty(String propertyName, final Object value) {
		logger.info("finding IncidentNote instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from IncidentNote model where model." + propertyName + "= :propertyValue";
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

	public void save(IncidentNote entity) {
		logger.info("saving IncidentNote instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public IncidentNote update(IncidentNote entity) {
		logger.info("updating IncidentNote instance");
		try {
			IncidentNote result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

}
