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
 * BuyerSkuCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSkuCategory
 * @author MyEclipse Persistence Tools
 */

public class BuyerSkuCategoryDAO extends JpaDaoSupport implements
		IBuyerSkuCategoryDAO {
	// property constants
	public static final String CATEGORY_NAME = "categoryName";
	public static final String CATEGORY_DESCR = "categoryDescr";
	public static final String CATEGORY_ID = "categoryId";
	

	/**
	 * Perform an initial save of a previously unsaved BuyerSkuCategory entity.
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
	 * BuyerSkuCategoryDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuCategory entity) {
		logger.info("saving BuyerSkuCategory instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerSkuCategory entity. This operation must be
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
	 * BuyerSkuCategoryDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuCategory entity) {
		logger.info("deleting BuyerSkuCategory instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSkuCategory.class,
					entity.getCategoryId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerSkuCategory entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuCategory entity parameter
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
	 * entity = BuyerSkuCategoryDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to update
	 * @return BuyerSkuCategory the persisted BuyerSkuCategory entity instance,
	 *         may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuCategory update(BuyerSkuCategory entity) {
		logger.info("updating BuyerSkuCategory instance");
		try {
			BuyerSkuCategory result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSkuCategory findById(Integer id) {
		logger.info("finding BuyerSkuCategory instance with id: " + id);
		try {
			BuyerSkuCategory instance = getJpaTemplate().find(
					BuyerSkuCategory.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSkuCategory entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuCategory property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuCategory> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuCategory> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerSkuCategory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerSkuCategory model where model."
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

	public List<BuyerSkuCategory> findByCategoryName(Object categoryName) {
		return findByProperty(CATEGORY_NAME, categoryName);
	}

	public List<BuyerSkuCategory> findByCategoryDescr(Object categoryDescr) {
		return findByProperty(CATEGORY_DESCR, categoryDescr);
	}
	
	public List<BuyerSkuCategory> findByCategoryId(Object categoryId) {
		return findByProperty(CATEGORY_ID, categoryId);
	}

	/**
	 * Find all BuyerSkuCategory entities.
	 * 
	 * @return List<BuyerSkuCategory> all BuyerSkuCategory entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuCategory> findAll() {
		logger.info("finding all BuyerSkuCategory instances");
		try {
			final String queryString = "select model from BuyerSkuCategory model";
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

	public static IBuyerSkuCategoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerSkuCategoryDAO) ctx.getBean("BuyerSkuCategoryDAO");
	}
}