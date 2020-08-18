package com.newco.marketplace.webservices.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ShcOrderAddOn entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcOrderAddOn
 * @author MyEclipse Persistence Tools
 */

public class ShcOrderAddOnDAO extends JpaDaoSupport implements
		IShcOrderAddOnDAO {
	// property constants
	public static final String RETAIL_PRICE = "retailPrice";
	public static final String MARGIN = "margin";
	public static final String QTY = "qty";
	public static final String MISC_IND = "miscInd";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ShcOrderAddOn entity. All
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
	 * ShcOrderAddOnDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderAddOn entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcOrderAddOn entity) {
		logger.info("saving ShcOrderAddOn instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcOrderAddOn entity. This operation must be
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
	 * ShcOrderAddOnDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderAddOn entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcOrderAddOn entity) {
		logger.info("deleting ShcOrderAddOn instance");
		try {
			entity = getJpaTemplate().getReference(ShcOrderAddOn.class,
					entity.getShcOrderAddOnId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcOrderAddOn entity and return it or a copy
	 * of it to the sender. A copy of the ShcOrderAddOn entity parameter is
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
	 * entity = ShcOrderAddOnDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderAddOn entity to update
	 * @returns ShcOrderAddOn the persisted ShcOrderAddOn entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcOrderAddOn update(ShcOrderAddOn entity) {
		logger.info("updating ShcOrderAddOn instance");
		try {
			ShcOrderAddOn result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcOrderAddOn findById(Integer id) {
		logger.info("finding ShcOrderAddOn instance with id: " + id);
		try {
			ShcOrderAddOn instance = getJpaTemplate().find(ShcOrderAddOn.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcOrderAddOn entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrderAddOn property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrderAddOn> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ShcOrderAddOn> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcOrderAddOn instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcOrderAddOn model where model."
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

	public List<ShcOrderAddOn> findByRetailPrice(Object retailPrice) {
		return findByProperty(RETAIL_PRICE, retailPrice);
	}

	public List<ShcOrderAddOn> findByMargin(Object margin) {
		return findByProperty(MARGIN, margin);
	}

	public List<ShcOrderAddOn> findByQty(Object qty) {
		return findByProperty(QTY, qty);
	}

	public List<ShcOrderAddOn> findByMiscInd(Object miscInd) {
		return findByProperty(MISC_IND, miscInd);
	}

	public List<ShcOrderAddOn> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ShcOrderAddOn entities.
	 * 
	 * @return List<ShcOrderAddOn> all ShcOrderAddOn entities
	 */
	@SuppressWarnings("unchecked")
	public List<ShcOrderAddOn> findAll() {
		logger.info("finding all ShcOrderAddOn instances");
		try {
			final String queryString = "select model from ShcOrderAddOn model";
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
	
	/**
	 * Find all ShcOrderAddon entities for a given orderid.
	 * 
	 * @return List<ShcOrderTransaction> all ShcOrderTransaction entities
	 */
	public List<ShcOrderAddOn> findByShcOrderId(final Integer shcOrderId) {
		logger.info("finding ShcOrderAddOn instance with OrderId:" + shcOrderId);
		try {
			final String queryString = "select model from ShcOrderAddOn model where model.shcOrder.shcOrderId" 
			 						+ "= :shcOrderId";
			@SuppressWarnings("unchecked")
			List<ShcOrderAddOn> addons = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("shcOrderId", shcOrderId);					
					return query.getResultList();
				}
			});
			return addons;
		}
		catch(EmptyResultDataAccessException ex){
			logger.info("No ShcOrder entiry found for OrderId:" + shcOrderId );
			return null;
		
		}
		catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public static IShcOrderAddOnDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcOrderAddOnDAO) ctx.getBean("ShcOrderAddOnDAO");
	}
}