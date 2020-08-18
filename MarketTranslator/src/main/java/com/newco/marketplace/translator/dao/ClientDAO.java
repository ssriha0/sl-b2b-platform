package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class ClientDAO extends JpaDaoSupport implements	IClientDAO {
	
	// property constants
	public static final String MODIFIED_BY = "modifiedBy";

	@SuppressWarnings("unchecked")
	public List<Client> findAll() {
		logger.info("finding all Client instances");
		try {
			final String queryString = "select model from Client model";
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

	public Client findById(Integer id) {
		logger.info("finding Client instance with id: " + id);
		try {
			Client instance = getJpaTemplate().find(Client.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<Client> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<Client> findByProperty(String propertyName, final Object value) {
		logger.info("finding Client instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from Client model where model." + propertyName + "= :propertyValue";
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

	public void save(Client entity) {
		logger.info("saving Client instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public Client update(Client entity) {
		logger.info("updating Client instance");
		try {
			Client result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Client findByClientName(final String name) {
		logger.info("finding Client instance with name: " + name);
		try {
			final String queryString = "select model from Client model where model.name = :clientName";
			return (Client) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("clientName", name);
					return query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by client name failed", re);
			throw re;
		}
	}

}
