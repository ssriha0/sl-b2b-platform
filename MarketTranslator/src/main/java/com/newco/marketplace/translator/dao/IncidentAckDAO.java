package com.newco.marketplace.translator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * IncidentAck entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.IncidentAck
 * @author MyEclipse Persistence Tools
 */

public class IncidentAckDAO extends JpaDaoSupport implements IIncidentAckDAO {
	// property constants
	public static final String ACK_FILE_NAME = "ackFileName";
	public static final String INCIDENT_STATUS = "incidentStatus";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved IncidentAck entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IncidentAckDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(IncidentAck entity) {
		logger.info("saving IncidentAck instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent IncidentAck entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IncidentAckDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(IncidentAck entity) {
		logger.info("deleting IncidentAck instance");
		try {
			entity = getJpaTemplate().getReference(IncidentAck.class,
					entity.getIncidentAckId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved IncidentAck entity and return it or a copy of
	 * it to the sender. A copy of the IncidentAck entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = IncidentAckDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to update
	 * @return IncidentAck the persisted IncidentAck entity instance, may not be
	 *         the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public IncidentAck update(IncidentAck entity) {
		logger.info("updating IncidentAck instance");
		try {
			IncidentAck result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public IncidentAck findById(Integer id) {
		logger.info("finding IncidentAck instance with id: " + id);
		try {
			IncidentAck instance = getJpaTemplate().find(IncidentAck.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all IncidentAck entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the IncidentAck property to query
	 * @param value
	 *            the property value to match
	 * @return List<IncidentAck> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<IncidentAck> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding IncidentAck instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from IncidentAck model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
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

	public List<IncidentAck> findByAckFileName(Object ackFileName) {
		return findByProperty(ACK_FILE_NAME, ackFileName);
	}

	public List<IncidentAck> findByIncidentStatus(Object incidentStatus) {
		return findByProperty(INCIDENT_STATUS, incidentStatus);
	}

	public List<IncidentAck> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all IncidentAck entities.
	 * 
	 * @return List<IncidentAck> all IncidentAck entities
	 */
	@SuppressWarnings("unchecked")
	public List<IncidentAck> findAll() {
		logger.info("finding all IncidentAck instances");
		try {
			final String queryString = "select model from IncidentAck model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public static IIncidentAckDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IIncidentAckDAO) ctx.getBean("IncidentAckDAO");
	}
}