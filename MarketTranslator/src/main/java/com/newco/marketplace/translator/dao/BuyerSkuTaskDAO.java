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
 * BuyerSkuTask entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSkuTask
 * @author MyEclipse Persistence Tools
 */

public class BuyerSkuTaskDAO extends JpaDaoSupport implements IBuyerSkuTaskDAO {
	// property constants
	public static final String TASK_NAME = "taskName";
	public static final String TASK_COMMENTS = "taskComments";
	public static final String BUYER_SKU = "buyerSku";

	/**
	 * Perform an initial save of a previously unsaved BuyerSkuTask entity. All
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
	 * BuyerSkuTaskDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTask entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuTask entity) {
		logger.info("saving BuyerSkuTask instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerSkuTask entity. This operation must be performed
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
	 * BuyerSkuTaskDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTask entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuTask entity) {
		logger.info("deleting BuyerSkuTask instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSkuTask.class,
					entity.getSkuTaskId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerSkuTask entity and return it or a copy of
	 * it to the sender. A copy of the BuyerSkuTask entity parameter is returned
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
	 * entity = BuyerSkuTaskDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTask entity to update
	 * @return BuyerSkuTask the persisted BuyerSkuTask entity instance, may not
	 *         be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuTask update(BuyerSkuTask entity) {
		logger.info("updating BuyerSkuTask instance");
		try {
			BuyerSkuTask result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSkuTask findById(Integer id) {
		logger.info("finding BuyerSkuTask instance with id: " + id);
		try {
			BuyerSkuTask instance = getJpaTemplate().find(BuyerSkuTask.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSkuTask entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuTask property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuTask> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTask> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerSkuTask instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerSkuTask model where model."
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

	public List<BuyerSkuTask> findByTaskName(Object taskName) {
		return findByProperty(TASK_NAME, taskName);
	}

	public List<BuyerSkuTask> findByTaskComments(Object taskComments) {
		return findByProperty(TASK_COMMENTS, taskComments);
	}

	/**
	 * Find all BuyerSkuTask entities.
	 * 
	 * @return List<BuyerSkuTask> all BuyerSkuTask entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTask> findAll() {
		logger.info("finding all BuyerSkuTask instances");
		try {
			final String queryString = "select model from BuyerSkuTask model";
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

	public static IBuyerSkuTaskDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerSkuTaskDAO) ctx.getBean("BuyerSkuTaskDAO");
	}
}