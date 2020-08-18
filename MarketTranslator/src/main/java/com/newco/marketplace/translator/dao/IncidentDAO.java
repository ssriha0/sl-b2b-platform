package com.newco.marketplace.translator.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public class IncidentDAO extends JpaDaoSupport implements IIncidentDAO {

	// property constants
	public static final String MODIFIED_BY = "modifiedBy";
	
	@SuppressWarnings("unchecked")
	public List<Incident> findAll() {
		logger.info("finding all Incident instances");
		try {
			final String queryString = "select model from Incident model";
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

	public Incident findById(Integer id) {
		logger.info("finding Incident instance with id: " + id);
		try {
			Incident instance = getJpaTemplate().find(Incident.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	public List<Incident> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	@SuppressWarnings("unchecked")
	public List<Incident> findByProperty(String propertyName, final Object value) {
		logger.info("finding Incident instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from Incident model where model." + propertyName + "= :propertyValue";
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
	
	public Incident findByClientIncidentID(final String clientIncidentID, final Client client) {
		logger.info("finding Incident instance with client: " + client.getName() + ", clientIncidentID: " + clientIncidentID);
		try {
			final String queryString = "select model from Incident model where model.clientIncidentID = :clientIncidentID and model.client.clientID = :clientID";
			return (Incident) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("clientIncidentID", clientIncidentID);
					query.setParameter("clientID", client.getClientID());
					return query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void save(Incident entity) {
		logger.info("saving Incident instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public Incident update(Incident entity) {
		logger.info("updating Incident instance");
		try {
			Incident result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public Incident findLatestByClientIncidentIdWithTestSuffix(final Client client, final String clientIncidentId, 
			final String testSuffix) {
		logger.info("finding latest Incident entity with client: " + client.getName() + ", clientIncidentID: " + clientIncidentId + " and testSuffix: " + testSuffix);
		try {
			final String queryString =
				"select model from Incident model " +
				"where model.clientIncidentID like :clientIncidentID\n" +
				"and model.client.clientID = :clientID" +
				"order by model.incidentID desc";
			return (Incident) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("clientIncidentID", clientIncidentId + testSuffix + "%");
					query.setParameter("clientID", client.getClientID());
					query.setMaxResults(1);
					return query.getSingleResult();
				}
			});
		}
		catch (EmptyResultDataAccessException e) {
			logger.info("No Incident entity found for client: " + client.getName() + ", clientIncidentID: " + clientIncidentId + " and testSuffix: " + testSuffix);
			return null;
		}
		catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Incident> findByClientIncidentIds(final Client client,
			final List<String> clientIncidentIds) {
		StringBuilder sb = new StringBuilder(clientIncidentIds.size() * 12);
		for (String s : clientIncidentIds) sb.append(s).append(", ");
		if (sb.length()> 2) sb.delete(sb.length() - 2, sb.length()); // remove trailing ", "
		logger.info("finding Incident instance with client: " + client.getName() + ", clientIncidentIDs: " + sb.toString());
		try {
			final String queryString = "select model from Incident model where model.clientIncidentID IN (:clientIncidentIDs) and model.client.clientID = :clientID";
			@SuppressWarnings("unchecked")
			List<Incident> resultList = (List<Incident>) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("clientIncidentIDs", clientIncidentIds);
					query.setParameter("clientID", client.getClientID());
					return query.getResultList();
				}
			});
			return resultList;
		}
		catch (EmptyResultDataAccessException e) {
			logger.info("No Incident entities found for client: " + client.getName() + ", clientIncidentIDs: " + sb.toString());
			return Collections.emptyList();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
