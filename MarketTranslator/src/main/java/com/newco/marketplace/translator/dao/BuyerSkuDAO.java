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
 * BuyerSku entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSku
 * @author MyEclipse Persistence Tools
 */

public class BuyerSkuDAO extends JpaDaoSupport implements IBuyerSkuDAO {
	// property constants
	public static final String SKU = "sku";
	public static final String PRICE_TYPE = "priceType";
	public static final String RETAIL_PRICE = "retailPrice";
	public static final String BID_PRICE = "bidPrice";
	public static final String BILLING_PRICE = "billingPrice";
	public static final String BID_MARGIN = "bidMargin";
	public static final String BILLING_MARGIN = "billingMargin";
	public static final String BID_PRICE_SCHEMA = "bidPriceSchema";
	public static final String BILLING_PRICE_SCHEMA = "billingPriceSchema";
	public static final String SKU_DESCRIPTION = "skuDescription";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String ORDERITEM_TYPE = "orderitemType";

	/**
	 * Perform an initial save of a previously unsaved BuyerSku entity. All
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
	 * BuyerSkuDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSku entity) {
		logger.info("saving BuyerSku instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerSku entity. This operation must be performed
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
	 * BuyerSkuDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSku entity) {
		logger.info("deleting BuyerSku instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSku.class,
					entity.getSkuId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerSku entity and return it or a copy of it
	 * to the sender. A copy of the BuyerSku entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
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
	 * entity = BuyerSkuDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to update
	 * @return BuyerSku the persisted BuyerSku entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSku update(BuyerSku entity) {
		logger.info("updating BuyerSku instance");
		try {
			BuyerSku result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSku findById(Integer id) {
		logger.info("finding BuyerSku instance with id: " + id);
		try {
			BuyerSku instance = getJpaTemplate().find(BuyerSku.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSku entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSku property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSku> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSku> findByProperty(String propertyName, final Object value) {
		logger.info("finding BuyerSku instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from BuyerSku model where model."
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

	public List<BuyerSku> findBySku(Object sku) {
		return findByProperty(SKU, sku);
	}

	public List<BuyerSku> findByPriceType(Object priceType) {
		return findByProperty(PRICE_TYPE, priceType);
	}

	public List<BuyerSku> findByRetailPrice(Object retailPrice) {
		return findByProperty(RETAIL_PRICE, retailPrice);
	}

	public List<BuyerSku> findByBidPrice(Object bidPrice) {
		return findByProperty(BID_PRICE, bidPrice);
	}

	public List<BuyerSku> findByBillingPrice(Object billingPrice) {
		return findByProperty(BILLING_PRICE, billingPrice);
	}

	public List<BuyerSku> findByBidMargin(Object bidMargin) {
		return findByProperty(BID_MARGIN, bidMargin);
	}

	public List<BuyerSku> findByBillingMargin(Object billingMargin) {
		return findByProperty(BILLING_MARGIN, billingMargin);
	}

	public List<BuyerSku> findByBidPriceSchema(Object bidPriceSchema) {
		return findByProperty(BID_PRICE_SCHEMA, bidPriceSchema);
	}

	public List<BuyerSku> findByBillingPriceSchema(Object billingPriceSchema) {
		return findByProperty(BILLING_PRICE_SCHEMA, billingPriceSchema);
	}

	public List<BuyerSku> findBySkuDescription(Object skuDescription) {
		return findByProperty(SKU_DESCRIPTION, skuDescription);
	}

	public List<BuyerSku> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<BuyerSku> findByOrderitemType(Object orderitemType) {
		return findByProperty(ORDERITEM_TYPE, orderitemType);
	}

	/**
	 * Find all BuyerSku entities.
	 * 
	 * @return List<BuyerSku> all BuyerSku entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSku> findAll() {
		logger.info("finding all BuyerSku instances");
		try {
			final String queryString = "select model from BuyerSku model";
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
	
	public BuyerSku findBySkuAndBuyerID(final String sku, final Integer buyerID) {
		try {
			final String queryString = "select model from BuyerSku model where sku = :sku and buyer_id = :buyerID";
			return (BuyerSku) getJpaTemplate().execute(new JpaCallback() {
				public BuyerSku doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("sku", sku);
					query.setParameter("buyerID", buyerID);
					return (BuyerSku) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			//logger.error("find by sku and buyer ID failed", re);
			return null;
		}
	}
	

	public static IBuyerSkuDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IBuyerSkuDAO) ctx.getBean("BuyerSkuDAO");
	}

	public List<BuyerSku> findAddOnsBySkuCategory(final BuyerSkuCategory skuCategory) {
		try{	
			final String queryString = "select bsku from BuyerSku bsku " 
			            	+ "inner join  bsku.buyerSkuAttributeses attr "
			            	+ " where attr.id.attributeType in ('ADDON') "
			                + " and bsku.buyerSkuCategory = :skuCategory ";
            		
			@SuppressWarnings("unchecked")
			List<BuyerSku> resultList = (List<BuyerSku>) getJpaTemplate().execute(new JpaCallback() {
				public List<BuyerSku> doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("skuCategory", skuCategory);

					@SuppressWarnings("unchecked")
					List<BuyerSku> resultList = (List<BuyerSku>) query.getResultList();
					return resultList;
				}
			});
			return resultList;
		} catch (RuntimeException re) {
			//logger.error("find by sku and buyer ID failed", re);
			return null;
		}
	}
}