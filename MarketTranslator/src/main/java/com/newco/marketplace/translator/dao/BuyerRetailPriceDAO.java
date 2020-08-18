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
 * BuyerRetailPrice entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerRetailPrice
 * @author MyEclipse Persistence Tools
 */

public class BuyerRetailPriceDAO extends JpaDaoSupport implements
		IBuyerRetailPriceDAO {
	// property constants
	public static final String STORE_NO = "storeNo";
	public static final String SKU = "sku";
	public static final String RETAIL_PRICE = "retailPrice";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved BuyerRetailPrice entity.
	 * All subsequent persist actions of this entity should use the #update()
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
	 * BuyerRetailPriceDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerRetailPrice entity) {
		logger.info("saving BuyerRetailPrice instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerRetailPrice entity. This operation must be
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
	 * BuyerRetailPriceDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerRetailPrice entity) {
		logger.info("deleting BuyerRetailPrice instance");
		try {
			entity = getJpaTemplate().getReference(BuyerRetailPrice.class,
					entity.getRetailPriceId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerRetailPrice entity and return it or a
	 * copy of it to the sender. A copy of the BuyerRetailPrice entity parameter
	 * is returned when the JPA persistence mechanism has not previously been
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
	 * entity = BuyerRetailPriceDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to update
	 * @returns BuyerRetailPrice the persisted BuyerRetailPrice entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerRetailPrice update(BuyerRetailPrice entity) {
		logger.info("updating BuyerRetailPrice instance");
		try {
			BuyerRetailPrice result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerRetailPrice findById(Integer id) {
		logger.info("finding BuyerRetailPrice instance with id: " + id);
		try {
			BuyerRetailPrice instance = getJpaTemplate().find(
					BuyerRetailPrice.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerRetailPrice entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerRetailPrice property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerRetailPrice> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerRetailPrice> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerRetailPrice instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerRetailPrice model where model."
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

	public List<BuyerRetailPrice> findByStoreNo(Object storeNo) {
		return findByProperty(STORE_NO, storeNo);
	}

	public List<BuyerRetailPrice> findBySku(Object sku) {
		return findByProperty(SKU, sku);
	}

	public List<BuyerRetailPrice> findByRetailPrice(Object retailPrice) {
		return findByProperty(RETAIL_PRICE, retailPrice);
	}

	public List<BuyerRetailPrice> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all BuyerRetailPrice entities.
	 * 
	 * @return List<BuyerRetailPrice> all BuyerRetailPrice entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerRetailPrice> findAll() {
		logger.info("finding all BuyerRetailPrice instances");
		try {
			final String queryString = "select model from BuyerRetailPrice model";
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
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.dao.IBuyerRetailPriceDAO#findByStoreNoAndSKU(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public BuyerRetailPrice findByStoreNoAndSKU(final String storeNo, final String sku, final Integer buyerID,final String defaultStroreNo) throws Exception{
		try {
			final String hql = "select model from BuyerRetailPrice model where model.sku = :sku and model.storeNo in (:storeNo,:defaultStroreNo) and model.buyer.buyerId = :buyerID";
			return (BuyerRetailPrice) getJpaTemplate().execute(new JpaCallback() {
				public BuyerRetailPrice doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(hql);
					query.setParameter("sku", sku);
					query.setParameter("storeNo", storeNo);
					query.setParameter("defaultStroreNo", defaultStroreNo);
					query.setParameter("buyerID", buyerID);
					//There might be two records in the following order, 1. result with storeNo passed in 2. result with default store no.Always get the first result. 
					return (BuyerRetailPrice)query.getResultList().get(0);	
				}
			});
		} catch (Exception re) {
			logger.error("findByStoreNoAndSKU failed SKU: "+sku+" store: "+storeNo+" buyerID: "+buyerID);
			throw re;
		}
	}

	public static IBuyerRetailPriceDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerRetailPriceDAO) ctx.getBean("BuyerRetailPriceDAO");
	}
}