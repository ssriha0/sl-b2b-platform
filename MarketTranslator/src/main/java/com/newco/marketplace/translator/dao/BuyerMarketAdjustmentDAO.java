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
 * BuyerMarketAdjustment entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerMarketAdjustment
 * @author MyEclipse Persistence Tools
 */

public class BuyerMarketAdjustmentDAO extends JpaDaoSupport implements
		IBuyerMarketAdjustmentDAO {
	// property constants
	public static final String ADJUSTMENT = "adjustment";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved BuyerMarketAdjustment
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
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
	 * BuyerMarketAdjustmentDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerMarketAdjustment entity) {
		logger.info("saving BuyerMarketAdjustment instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerMarketAdjustment entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * BuyerMarketAdjustmentDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerMarketAdjustment entity) {
		logger.info("deleting BuyerMarketAdjustment instance");
		try {
			entity = getJpaTemplate().getReference(BuyerMarketAdjustment.class,
					entity.getMarketAdjustmentId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerMarketAdjustment entity and return it or
	 * a copy of it to the sender. A copy of the BuyerMarketAdjustment entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = BuyerMarketAdjustmentDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to update
	 * @returns BuyerMarketAdjustment the persisted BuyerMarketAdjustment entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerMarketAdjustment update(BuyerMarketAdjustment entity) {
		logger.info("updating BuyerMarketAdjustment instance");
		try {
			BuyerMarketAdjustment result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerMarketAdjustment findById(Integer id) {
		logger.info("finding BuyerMarketAdjustment instance with id: " + id);
		try {
			BuyerMarketAdjustment instance = getJpaTemplate().find(
					BuyerMarketAdjustment.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerMarketAdjustment entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerMarketAdjustment property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerMarketAdjustment> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerMarketAdjustment> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerMarketAdjustment instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerMarketAdjustment model where model."
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

	public List<BuyerMarketAdjustment> findByAdjustment(Object adjustment) {
		return findByProperty(ADJUSTMENT, adjustment);
	}

	public List<BuyerMarketAdjustment> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all BuyerMarketAdjustment entities.
	 * 
	 * @return List<BuyerMarketAdjustment> all BuyerMarketAdjustment entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerMarketAdjustment> findAll() {
		logger.info("finding all BuyerMarketAdjustment instances");
		try {
			final String queryString = "select model from BuyerMarketAdjustment model";
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
	
	public BuyerMarketAdjustment findAdjustmentByZip(final String zip) {
		logger.info("finding all BuyerMarketAdjustment instances");
		try {
			final String queryString = "select bma from BuyerMarketAdjustment bma, LuZipMarket luZip "
				+ "inner join bma.luMarket luMarket "
				+ "where luMarket.marketId = luZip.marketId and luZip.zip = :zip";
			return (BuyerMarketAdjustment) getJpaTemplate().execute(new JpaCallback() {
				public BuyerMarketAdjustment doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("zip", zip);
					return (BuyerMarketAdjustment) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.info("The method BuyerMarketAdjustmentDAO.findAdjustmentByZip(zip:"+zip+") failed. Exception: " + re.getMessage());
			throw re;
		}
	}

	public static IBuyerMarketAdjustmentDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerMarketAdjustmentDAO) ctx
				.getBean("BuyerMarketAdjustmentDAO");
	}
}