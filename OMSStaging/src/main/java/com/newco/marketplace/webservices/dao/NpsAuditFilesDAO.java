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
 * NpsAuditFiles entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.NpsAuditFiles
 * @author MyEclipse Persistence Tools
 */

public class NpsAuditFilesDAO extends JpaDaoSupport implements
		INpsAuditFilesDAO {
	// property constants
	public static final String FILE_NAME = "fileName";
	public static final String NUM_SUCCESS = "numSuccess";
	public static final String NUM_FAILURE = "numFailure";

	/**
	 * Perform an initial save of a previously unsaved NpsAuditFiles entity. All
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
	 * NpsAuditFilesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditFiles entity) {
		logger.info("saving NpsAuditFiles instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent NpsAuditFiles entity. This operation must be
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
	 * NpsAuditFilesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditFiles entity) {
		logger.info("deleting NpsAuditFiles instance");
		try {
			entity = getJpaTemplate().getReference(NpsAuditFiles.class,
					entity.getAuditFileId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved NpsAuditFiles entity and return it or a copy
	 * of it to the sender. A copy of the NpsAuditFiles entity parameter is
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
	 * entity = NpsAuditFilesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to update
	 * @return NpsAuditFiles the persisted NpsAuditFiles entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditFiles update(NpsAuditFiles entity) {
		logger.info("updating NpsAuditFiles instance");
		try {
			NpsAuditFiles result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public NpsAuditFiles findById(Integer id) {
		logger.info("finding NpsAuditFiles instance with id: " + id);
		try {
			NpsAuditFiles instance = getJpaTemplate().find(NpsAuditFiles.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all NpsAuditFiles entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditFiles property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditFiles> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditFiles> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding NpsAuditFiles instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from NpsAuditFiles model where model."
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

	public List<NpsAuditFiles> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<NpsAuditFiles> findByNumSuccess(Object numSuccess) {
		return findByProperty(NUM_SUCCESS, numSuccess);
	}

	public List<NpsAuditFiles> findByNumFailure(Object numFailure) {
		return findByProperty(NUM_FAILURE, numFailure);
	}

	/**
	 * Find all NpsAuditFiles entities.
	 * 
	 * @return List<NpsAuditFiles> all NpsAuditFiles entities
	 */
	@SuppressWarnings("unchecked")
	public List<NpsAuditFiles> findAll() {
		logger.info("finding all NpsAuditFiles instances");
		try {
			final String queryString = "select model from NpsAuditFiles model";
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

	public static INpsAuditFilesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (INpsAuditFilesDAO) ctx.getBean("NpsAuditFilesDAO");
	}
}