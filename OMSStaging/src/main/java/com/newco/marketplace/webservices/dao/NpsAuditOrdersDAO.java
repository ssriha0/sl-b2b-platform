package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * NpsAuditOrders entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.NpsAuditOrders
 * @author MyEclipse Persistence Tools
 */

public class NpsAuditOrdersDAO extends JpaDaoSupport implements
		INpsAuditOrdersDAO {
	// property constants
	public static final String PROCESS_ID = "processId";
	public static final String RETURN_CODE = "returnCode";
	public static final String STAGING_ORDER_ID = "stagingOrderId";

	/**
	 * Perform an initial save of a previously unsaved NpsAuditOrders entity.
	 * All subsequent persist actions of this entity should use the #update()
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
	 * NpsAuditOrdersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditOrders entity) {
		logger.info("saving NpsAuditOrders instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent NpsAuditOrders entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * NpsAuditOrdersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditOrders entity) {
		logger.info("deleting NpsAuditOrders instance");
		try {
			entity = getJpaTemplate().getReference(NpsAuditOrders.class,
					entity.getAuditOrderId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved NpsAuditOrders entity and return it or a copy
	 * of it to the sender. A copy of the NpsAuditOrders entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = NpsAuditOrdersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to update
	 * @return NpsAuditOrders the persisted NpsAuditOrders entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditOrders update(NpsAuditOrders entity) {
		logger.info("updating NpsAuditOrders instance");
		try {
			NpsAuditOrders result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public NpsAuditOrders findById(Integer id) {
		logger.info("finding NpsAuditOrders instance with id: " + id);
		try {
			NpsAuditOrders instance = getJpaTemplate().find(
					NpsAuditOrders.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all NpsAuditOrders entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditOrders property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditOrders> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditOrders> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding NpsAuditOrders instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from NpsAuditOrders model where model."
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

	public List<NpsAuditOrders> findByProcessId(Object processId) {
		return findByProperty(PROCESS_ID, processId);
	}

	public List<NpsAuditOrders> findByReturnCode(Object returnCode) {
		return findByProperty(RETURN_CODE, returnCode);
	}

	public List<NpsAuditOrders> findByStagingOrderId(Object stagingOrderId) {
		return findByProperty(STAGING_ORDER_ID, stagingOrderId);
	}

	/**
	 * Find all NpsAuditOrders entities.
	 * 
	 * @return List<NpsAuditOrders> all NpsAuditOrders entities
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditOrders> findAll() {
		logger.info("finding all NpsAuditOrders instances");
		try {
			final String queryString = "select model from NpsAuditOrders model";
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

	public static INpsAuditOrdersDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (INpsAuditOrdersDAO) ctx.getBean("NpsAuditOrdersDAO");
	}
}