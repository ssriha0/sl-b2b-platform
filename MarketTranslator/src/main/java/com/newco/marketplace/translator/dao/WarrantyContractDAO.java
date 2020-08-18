package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class WarrantyContractDAO extends JpaDaoSupport implements IWarrantyContractDAO {

	// property constants
	public static final String MODIFIED_BY = "modifiedBy";
	
	@SuppressWarnings("unchecked")
	public List<WarrantyContract> findAll() {
		logger.info("finding all WarrantyContract instances");
		try {
			final String queryString = "select model from WarrantyContract model";
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

	public WarrantyContract findById(Integer id) {
		logger.info("finding Client instance with id: " + id);
		try {
			WarrantyContract instance = getJpaTemplate().find(WarrantyContract.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<WarrantyContract> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<WarrantyContract> findByProperty(String propertyName, final Object value) {
		logger.info("finding WarrantyContract instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from WarrantyContract model where model." + propertyName + "= :propertyValue";
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

	public void save(WarrantyContract entity) {
		logger.info("saving WarrantyContract instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public WarrantyContract update(WarrantyContract entity) {
		logger.info("updating WarrantyContract instance");
		try {
			WarrantyContract result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public WarrantyContract findByClientAndContract(final Client client, final String contractNumber) {
		try {
			final String queryString = "select model from WarrantyContract model where client_id = :client and contract_number = :contractNumber";
			return (WarrantyContract) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("client", client.getClientID());
					query.setParameter("contractNumber", contractNumber);
					return query.getSingleResult();
				}
			});
		}
		catch (RuntimeException re) {
			logger.error("Error finding client warranty", re);
			throw re;
		}
	}

}
