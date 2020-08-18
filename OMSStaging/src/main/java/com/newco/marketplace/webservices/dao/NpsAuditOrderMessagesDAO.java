package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * NpsAuditOrderMessages entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.NpsAuditOrderMessages
 * @author MyEclipse Persistence Tools
 */

public class NpsAuditOrderMessagesDAO extends JpaDaoSupport implements
		INpsAuditOrderMessagesDAO {
	// property constants

	/**
	 * Perform an initial save of a previously unsaved NpsAuditOrderMessages
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
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
	 * NpsAuditOrderMessagesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditOrderMessages entity) {
		logger.info("saving NpsAuditOrderMessages instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent NpsAuditOrderMessages entity. This operation must be
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
	 * NpsAuditOrderMessagesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditOrderMessages entity) {
		logger.info("deleting NpsAuditOrderMessages instance");
		try {
			entity = getJpaTemplate().getReference(NpsAuditOrderMessages.class,
					entity.getAuditOrderMessageId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved NpsAuditOrderMessages entity and return it or
	 * a copy of it to the sender. A copy of the NpsAuditOrderMessages entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#merge(Object)
	 * EntityManager#merge} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = NpsAuditOrderMessagesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to update
	 * @return NpsAuditOrderMessages the persisted NpsAuditOrderMessages entity
	 *         instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditOrderMessages update(NpsAuditOrderMessages entity) {
		logger.info("updating NpsAuditOrderMessages instance");
		try {
			NpsAuditOrderMessages result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public NpsAuditOrderMessages findById(Integer id) {
		logger.info("finding NpsAuditOrderMessages instance with id: " + id);
		try {
			NpsAuditOrderMessages instance = getJpaTemplate().find(
					NpsAuditOrderMessages.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all NpsAuditOrderMessages entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditOrderMessages property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditOrderMessages> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditOrderMessages> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding NpsAuditOrderMessages instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from NpsAuditOrderMessages model where model."
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

	/**
	 * Find all NpsAuditOrderMessages entities.
	 * 
	 * @return List<NpsAuditOrderMessages> all NpsAuditOrderMessages entities
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditOrderMessages> findAll() {
		logger.info("finding all NpsAuditOrderMessages instances");
		try {
			final String queryString = "select model from NpsAuditOrderMessages model";
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

	public static INpsAuditOrderMessagesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (INpsAuditOrderMessagesDAO) ctx
				.getBean("NpsAuditOrderMessagesDAO");
	}
}