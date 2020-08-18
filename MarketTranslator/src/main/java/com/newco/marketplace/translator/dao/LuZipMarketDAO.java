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
 * LuZipMarket entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.LuZipMarket
 * @author MyEclipse Persistence Tools
 */

public class LuZipMarketDAO extends JpaDaoSupport implements ILuZipMarketDAO {
	// property constants
	public static final String MARKET_ID = "marketId";

	/**
	 * Perform an initial save of a previously unsaved LuZipMarket entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuZipMarketDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuZipMarket entity) {
		logger.info("saving LuZipMarket instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LuZipMarket entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuZipMarketDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuZipMarket entity) {
		logger.info("deleting LuZipMarket instance");
		try {
			entity = getJpaTemplate().getReference(LuZipMarket.class,
					entity.getZip());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LuZipMarket entity and return it or a copy of
	 * it to the sender. A copy of the LuZipMarket entity parameter is returned
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
	 * entity = LuZipMarketDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to update
	 * @returns LuZipMarket the persisted LuZipMarket entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuZipMarket update(LuZipMarket entity) {
		logger.info("updating LuZipMarket instance");
		try {
			LuZipMarket result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public LuZipMarket findById(String id) {
		logger.info("finding LuZipMarket instance with id: " + id);
		try {
			LuZipMarket instance = getJpaTemplate().find(LuZipMarket.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all LuZipMarket entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuZipMarket property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuZipMarket> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LuZipMarket> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding LuZipMarket instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from LuZipMarket model where model."
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

	public List<LuZipMarket> findByMarketId(Object marketId) {
		return findByProperty(MARKET_ID, marketId);
	}

	/**
	 * Find all LuZipMarket entities.
	 * 
	 * @return List<LuZipMarket> all LuZipMarket entities
	 */
	@SuppressWarnings("unchecked")
	public List<LuZipMarket> findAll() {
		logger.info("finding all LuZipMarket instances");
		try {
			final String queryString = "select model from LuZipMarket model";
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

	public static ILuZipMarketDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ILuZipMarketDAO) ctx.getBean("LuZipMarketDAO");
	}
}