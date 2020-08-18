package com.newco.marketplace.webservices.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ShcOrderSku entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcOrderSku
 * @author MyEclipse Persistence Tools
 */

public class ShcOrderSkuDAO extends JpaDaoSupport implements IShcOrderSkuDAO {
	// property constants
	public static final String SKU = "sku";
	public static final String ADD_ON_IND = "addOnInd";
	public static final String INITIAL_BID_PRICE = "initialBidPrice";
	public static final String PRICE_RATIO = "priceRatio";
	public static final String FINAL_PRICE = "finalPrice";
	public static final String RETAIL_PRICE = "retailPrice";
	public static final String SELLING_PRICE = "sellingPrice";
	public static final String SERVICE_FEE = "serviceFee";
	public static final String CHARGE_CODE = "chargeCode";
	public static final String COVERAGE = "coverage";
	public static final String TYPE = "type";
	public static final String PERMIT_SKU_IND = "permitSkuInd";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String SHC_ORDER = "shcOrder";
	public static final String QTY = "qty";
	public static final String DESCRIPTION = "description";
	public static final String STATUS = "status";
	
	
	/**
	 * Perform an initial save of a previously unsaved ShcOrderSku entity. All
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
	 * ShcOrderSkuDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcOrderSku entity) {
		logger.info("saving ShcOrderSku instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcOrderSku entity. This operation must be performed
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
	 * ShcOrderSkuDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcOrderSku entity) {
		logger.info("deleting ShcOrderSku instance");
		try {
			entity = getJpaTemplate().getReference(ShcOrderSku.class,
					entity.getShcOrderSkuId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcOrderSku entity and return it or a copy of
	 * it to the sender. A copy of the ShcOrderSku entity parameter is returned
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
	 * entity = ShcOrderSkuDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to update
	 * @returns ShcOrderSku the persisted ShcOrderSku entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcOrderSku update(ShcOrderSku entity) {
		logger.info("updating ShcOrderSku instance");
		try {
			ShcOrderSku result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcOrderSku findById(Integer id) {
		logger.info("finding ShcOrderSku instance with id: " + id);
		try {
			ShcOrderSku instance = getJpaTemplate().find(ShcOrderSku.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcOrderSku entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrderSku property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrderSku> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ShcOrderSku> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcOrderSku instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcOrderSku model where model."
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

	public List<ShcOrderSku> findBySku(Object sku) {
		return findByProperty(SKU, sku);
	}
	
	public List<ShcOrderSku> findByAddOnInd(Object addOnInd) {
		return findByProperty(ADD_ON_IND, addOnInd);
	}

	public List<ShcOrderSku> findByInitialBidPrice(Object initialBidPrice) {
		return findByProperty(INITIAL_BID_PRICE, initialBidPrice);
	}

	public List<ShcOrderSku> findByPriceRatio(Object priceRatio) {
		return findByProperty(PRICE_RATIO, priceRatio);
	}

	public List<ShcOrderSku> findByFinalPrice(Object finalPrice) {
		return findByProperty(FINAL_PRICE, finalPrice);
	}

	public List<ShcOrderSku> findByRetailPrice(Object retailPrice) {
		return findByProperty(RETAIL_PRICE, retailPrice);
	}

	public List<ShcOrderSku> findBySellingPrice(Object sellingPrice) {
		return findByProperty(SELLING_PRICE, sellingPrice);
	}
	
	public List<ShcOrderSku> findByServiceFee(Object serviceFee) {
		return findByProperty(SERVICE_FEE, serviceFee);
	}

	public List<ShcOrderSku> findByChargeCode(Object chargeCode) {
		return findByProperty(CHARGE_CODE, chargeCode);
	}

	public List<ShcOrderSku> findByCoverage(Object coverage) {
		return findByProperty(COVERAGE, coverage);
	}

	public List<ShcOrderSku> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<ShcOrderSku> findByPermitSkuInd(Object permitSkuInd) {
		return findByProperty(PERMIT_SKU_IND, permitSkuInd);
	}

	public List<ShcOrderSku> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}
	
	public List<ShcOrderSku> findByShcOrder(Object shcOrder) {
		return findByProperty(SHC_ORDER, shcOrder
		);
	}

	public List<ShcOrderSku> findByQty(Object qty) {
		return findByProperty(QTY, qty);
	}
	
	public List<ShcOrderSku> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}
	
	public List<ShcOrderSku> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	/**
	 * Find all ShcOrderSku entities.
	 * 
	 * @return List<ShcOrderSku> all ShcOrderSku entities
	 */
	@SuppressWarnings("unchecked")
	public List<ShcOrderSku> findAll() {
		logger.info("finding all ShcOrderSku instances");
		try {
			final String queryString = "select model from ShcOrderSku model";
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

	public static IShcOrderSkuDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcOrderSkuDAO) ctx.getBean("ShcOrderSkuDAO");
	}

	
}